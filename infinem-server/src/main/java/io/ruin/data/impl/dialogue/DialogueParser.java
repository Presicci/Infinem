package io.ruin.data.impl.dialogue;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.Tuple;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/27/2023
 */
@Slf4j
public class DialogueParser {
    private int lineNumber;
    private final NPCDefinition npcDefinition;
    private final List<String> dialogueLines;
    private final DialogueParserSettings settings;
    private boolean recordDialogueLoop;

    public DialogueParser(NPCDefinition npcDefinition, List<String> dialogueLines, int lineNumber, DialogueParserSettings settings, boolean recordDialogueLoop) {
        this.npcDefinition = npcDefinition;
        this.dialogueLines = dialogueLines;
        this.lineNumber = lineNumber;
        this.settings = settings;
        this.recordDialogueLoop = recordDialogueLoop;
    }

    public DialogueParser(NPCDefinition npcDefinition, List<String> dialogueLines, int lineNumber, DialogueParserSettings settings) {
        this(npcDefinition, dialogueLines, lineNumber, settings, false);
    }

    public DialogueParser(NPCDefinition npcDefinition, List<String> dialogueLines, int lineNumber, boolean recordDialogueLoop) {
        this(npcDefinition, dialogueLines, lineNumber, null, recordDialogueLoop);
    }

    public DialogueParser(NPCDefinition npcDefinition, List<String> dialogueLines, int lineNumber) {
        this(npcDefinition, dialogueLines, lineNumber, null, false);
    }

    public DialogueParser(NPCDefinition npcDefinition, List<String> dialogueLines) {
        this(npcDefinition, dialogueLines, 0, null, false);
    }

    public Dialogue[] parseDialogue() {
        return parseDialogue(dialogueLines);
    }

    public Dialogue[] parseDialogue(List<String> dialogue) {
        lineNumber = 0;
        List<Dialogue> dialogues = new ArrayList<>();
        while(lineNumber < dialogue.size()) {
            if (dialogue.get(lineNumber).equals(">"))
                break;
            dialogues.add(parseLineWithCheck(dialogue));
            lineNumber++;
        }
        return dialogues.toArray(new Dialogue[0]);
    }

    private String matchingDelineator(String character) {
        switch (character) {
            default:
                return ")";
            case "/":
                return "\\";
            case "[":
                return "]";
            case "{":
                return "}";
        }
    }

    private Dialogue checkForSetting(String line, List<String> dialogue) {
        for (DialogueLoaderSetting setting : DialogueLoaderSetting.values()) {
            if (line.startsWith(setting.name())) {
                int leftIndex = lineNumber + 1;
                String settingChar = Character.toString(dialogue.get(leftIndex).charAt(0));
                int rightIndex = dialogue.size();
                for (int index = lineNumber; index < dialogue.size(); index++) {
                    if (dialogue.get(index).startsWith(matchingDelineator(settingChar))) {
                        dialogue.set(index, dialogue.get(index).substring(1));
                        rightIndex = index;
                        break;
                    }
                }
                try {
                    if (setting != DialogueLoaderSetting.RAND) {
                        String substring = line.substring(setting.name().length() + 1);
                        String arguments = "";
                        if (substring.contains(":")) {
                            String[] split = substring.split(":");
                            substring = split[0];
                            arguments = split[1];
                        }
                        int value = Integer.parseInt(substring);
                        List<Dialogue[]> dialogues = new DialogueParser(npcDefinition, dialogue.subList(leftIndex, rightIndex), 0, new DialogueParserSettings(setting, value), true).parseRandomDialogues(settingChar);
                        if (dialogues == null) {
                            error("predicate reliant setting but had an issue being read", dialogue);
                            return null;
                        }
                        if (dialogues.size() > 2) {
                            error("predicate reliant setting but has more than 2 dialogue sets denoted by '('", dialogue);
                            return null;
                        }
                        lineNumber = rightIndex - 1;
                        return new ConditionalDialogue(setting.getBiPredicate(), new Tuple<>(dialogues.get(0), dialogues.get(1)), value, arguments);
                    } else {
                        List<Dialogue[]> randomDialogues = new DialogueParser(npcDefinition, dialogue.subList(leftIndex, rightIndex), 0, true).parseRandomDialogues(settingChar);
                        lineNumber = rightIndex - 1;
                        return new ActionDialogue((player) -> {
                            Dialogue[] dialogues = Random.get(randomDialogues);
                            player.dialogue(dialogues);
                        });
                    }
                } catch (NumberFormatException ignored) {
                    error("predicate reliant setting without an integer value afterwards", dialogue);
                    return null;
                }
            }
        }
        return null;
    }

    protected Dialogue parseLineWithCheck(List<String> dialogue) {
        Dialogue d = parseLine(dialogue);
        if (settings != null && settings.isConsumerLine()) {
            d.setOnContinue(settings.getSetting().getBiConsumer(), settings.getConsumerValue());
            settings.setConsumerLine(false);
        }
        return d;
    }

    protected Dialogue parseLine(List<String> dialogue) {
        String line = dialogue.get(lineNumber);
        if (line.startsWith("#")) {     // Comment handling
            line = dialogue.get(++lineNumber);
        }
        if (line.startsWith(">") || line.startsWith("/") || line.startsWith("(")) {
            line = line.substring(1);
        }
        if (line.startsWith("*")) {
            if (settings == null) {
                error("use of '*' without settings object", dialogue);
            } else {
                line = line.substring(1);
                settings.setConsumerLine(true);
            }
        }
        if (line.startsWith("<")) {
            return parseOptions(line, dialogue);
        }
        Dialogue checkDialogue = checkForSetting(line, dialogue);
        if (checkDialogue != null) {
            return checkDialogue;
        }
        if (line.startsWith("Player:")) {
            return new PlayerDialogue(line.substring(8));
        }
        String npcName = npcDefinition.name.toLowerCase();
        if (line.toLowerCase().startsWith(npcName + ":")) {
            return new NPCDialogue(npcDefinition.id, line.substring(npcName.length() + 1));
        }
        for (DialogueLoaderAction action : DialogueLoaderAction.values()) {
            if (line.startsWith(action.name())) {
                if (action == DialogueLoaderAction.SHOP) {
                    String[] lineSegments = line.split(":");
                    if (lineSegments.length == 1) {
                        return new ActionDialogue((player) -> {
                            npcDefinition.shops.get(0).open(player);
                        });
                    } else {
                        try {
                            int shopIndex = Integer.parseInt(lineSegments[1]);
                            return new ActionDialogue((player) -> {
                                npcDefinition.shops.get(shopIndex).open(player);
                            });
                        } catch (NumberFormatException ignored) {
                            return new ActionDialogue((player) -> {
                                npcDefinition.shops.get(0).open(player);
                            });
                        }
                    }
                }
                if (action == DialogueLoaderAction.OTHERNPC) {
                    String[] lineSegments = line.split(":");
                    if (lineSegments.length < 3) {
                        error("improper syntax for OTHERNPC (OTHERNPC:NPCID:MESSAGE)", dialogue);
                        return new MessageDialogue("");
                    }
                    int npcId = -1;
                    try {
                        npcId = Integer.parseInt(lineSegments[1]);
                    } catch (NumberFormatException ignored) {
                        error("missing npcId for OTHERNPC action", dialogue);
                    }
                    return new NPCDialogue(npcId, lineSegments[2]);
                } else if (action == DialogueLoaderAction.ITEMDIALOGUE) {
                    String[] lineSegments = line.split(":");
                    if (lineSegments.length < 3) {
                        error("improper syntax for ITEMDIALOGUE (ITEMDIALOGUE:ITEMID:MESSAGE)", dialogue);
                        return new MessageDialogue("");
                    }
                    int itemId = -1;
                    try {
                        itemId = Integer.parseInt(lineSegments[1]);
                    } catch (NumberFormatException ignored) {
                        error("missing itemId for ITEMDIALOGUE action", dialogue);
                    }
                    return new ItemDialogue().one(itemId, lineSegments[2]);
                } else if (action == DialogueLoaderAction.TWOITEMDIALOGUE) {
                    String[] lineSegments = line.split(":");
                    if (lineSegments.length < 4) {
                        error("improper syntax for TWOITEMDIALOGUE (TWOITEMDIALOGUE:ITEMID:ITEMID:MESSAGE)", dialogue);
                        return new MessageDialogue("");
                    }
                    int itemId = -1;
                    int itemId2 = -1;
                    try {
                        itemId = Integer.parseInt(lineSegments[1]);
                        itemId2 = Integer.parseInt(lineSegments[2]);
                    } catch (NumberFormatException ignored) {
                        error("missing itemId for ITEMDIALOGUE action", dialogue);
                    }
                    return new ItemDialogue().two(itemId, itemId2, lineSegments[3]);
                } else if (action == DialogueLoaderAction.LASTOPTIONS) {
                    recordDialogueLoop = true;
                    int index = npcDefinition.optionDialogues == null ? 0 : npcDefinition.optionDialogues.size() - 1;
                    return new ActionDialogue((player) -> {
                        Dialogue loop = npcDefinition.optionDialogues.get(index);
                        if (loop != null)
                            player.dialogue(loop);
                    });
                } else if (action == DialogueLoaderAction.FIRSTOPTIONS) {
                    recordDialogueLoop = true;
                    return new ActionDialogue((player) -> {
                        if (npcDefinition.optionDialogues == null)
                            return;
                        Dialogue loop = npcDefinition.optionDialogues.get(npcDefinition.optionDialogues.size() - 1);
                        if (loop != null)
                            player.dialogue(loop);
                    });
                } else if (action == DialogueLoaderAction.MESSAGE) {
                    String[] splitLine = line.split(":");
                    if (splitLine.length != 2) {
                        error("MESSAGE action with improper length, needs to be 2", dialogue);
                    }
                    return new MessageDialogue(splitLine[1]);
                } else if (action == DialogueLoaderAction.ITEM) {
                    int itemId = -1;
                    try {
                        itemId = Integer.parseInt(line.substring(action.name().length() + 1));
                    } catch (NumberFormatException ignored) {
                        error("missing itemId for ITEM action", dialogue);
                    }
                    int finalItemId = itemId;
                    return new ItemDialogue().one(finalItemId, npcDefinition.name + " hands you " + ItemDefinition.get(finalItemId).descriptiveName + ".").consumer((player) -> {
                        player.getInventory().addOrDrop(finalItemId, 1);
                    });
                } else {
                    String finalLine = line;
                    return new ActionDialogue((player) -> {
                        if (action.name().length() + 1 < finalLine.length()) {
                            String substring = finalLine.substring(action.name().length() + 1);
                            if (substring.length() > 0)
                                player.putTemporaryAttribute(AttributeKey.DIALOGUE_ACTION_ARGUMENTS, substring);
                        }
                        action.getAction().accept(player);
                    });
                }
            }
        }
        error("invalid line prefix for " + npcName, dialogue);
        return new MessageDialogue("");
    }

    public List<Dialogue[]> parseRandomDialogues(boolean inner) {
        return parseRandomDialogues(inner ? "(" : "/");
    }

    public List<Dialogue[]> parseRandomDialogues(String settingChar) {
        List<Integer> optionLineNumbers = new ArrayList<>();
        int rightLimit = dialogueLines.size();
        for (int index = lineNumber; index < dialogueLines.size(); index++) {
            String line = dialogueLines.get(index);
            if (line.startsWith(settingChar)) {
                optionLineNumbers.add(index);
                if (settings!= null && settings.getSetting() != DialogueLoaderSetting.RAND && optionLineNumbers.size() >= 2) {
                    break;
                }
            }
        }
        if (optionLineNumbers.size() == 0) {
            System.err.println(NPCDefinition.get(npcDefinition.id).name + " has setting but has no options specified with open parentheses '('.");
            return null;
        }
        List<Dialogue[]> randomDialogues = new ArrayList<>();
        for (int index = 0; index < optionLineNumbers.size(); index++) {
            int leftBound = optionLineNumbers.get(index);
            int rightBound = index == optionLineNumbers.size() - 1 ? rightLimit : optionLineNumbers.get(index + 1);
            Dialogue[] parsedDialogue = parseDialogue(dialogueLines.subList(leftBound, rightBound));
            randomDialogues.add(parsedDialogue);
        }
        return randomDialogues;
    }

    /**
     * Loops through the options and gets their branching dialogue.
     * < denotes the opening of an option
     * > denotes the closing of an option
     * @return Complete OptionsDialogue
     */
    public Dialogue parseOptions(String line, List<String> dialogue) {
        List<Option> options = new ArrayList<>();
        while (line.startsWith("<")) {
            if (++lineNumber >= dialogue.size())
                break;
            Dialogue[] dialogues = parseOptionBranch(dialogue);
            options.add(new Option(line.substring(1), (player) -> player.dialogue(dialogues)));
            if (lineNumber >= dialogue.size())
                break;
            line = dialogue.get(lineNumber);
            if (line.startsWith(">")) {
                line = line.substring(1);
                if (line.trim().isEmpty() && dialogue.size() > lineNumber + 1 && dialogue.get(lineNumber + 1).startsWith("<")) {
                    line = dialogue.get(++lineNumber);
                }
            }

        }
        Dialogue finalDialogue = new OptionsDialogue(options);
        if (recordDialogueLoop) {
            if (npcDefinition.optionDialogues == null)
                npcDefinition.optionDialogues = new ArrayList<>();
            npcDefinition.optionDialogues.add(finalDialogue);
        }
        return finalDialogue;
    }

    /**
     * Parses the dialogue tree under an option.
     * @return Dialogue array containing option branch.
     */
    private Dialogue[] parseOptionBranch(List<String> dialogue) {
        List<Dialogue> branchDialogue = new ArrayList<>();
        String line = dialogue.get(lineNumber);
        while (!line.startsWith(">")) {
            branchDialogue.add(parseLineWithCheck(dialogue));
            if (++lineNumber >= dialogue.size())
                break;
            line = dialogue.get(lineNumber);
        }
        Dialogue[] dialoguesArray = new Dialogue[branchDialogue.size()];
        dialoguesArray = branchDialogue.toArray(dialoguesArray);
        return dialoguesArray;
    }

    protected void error(String issue, List<String> dialogue) {
        StringBuilder sb = new StringBuilder();
        sb.append(npcDefinition.name).append(" error on line: ").append(lineNumber + 1).append(". Error: ").append(issue).append("\n");
        for (String line : dialogue) {
            if (dialogue.indexOf(line) == lineNumber)
                sb.append("\u001B[35m").append(line).append("\u001B[0m\n");
            else
                sb.append("\u001B[34m").append(line).append("\u001B[0m\n");
        }
        sb.append("\u001B[0m");
        log.error(sb.toString());
    }
}
