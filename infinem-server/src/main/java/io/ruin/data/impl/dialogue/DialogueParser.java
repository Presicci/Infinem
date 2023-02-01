package io.ruin.data.impl.dialogue;

import io.ruin.api.utils.Tuple;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
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
    private final NPCDef npcDef;
    private final List<String> dialogueLines;
    private final DialogueParserSettings settings;

    public DialogueParser(NPCDef npcDef, List<String> dialogueLines, int lineNumber, DialogueParserSettings settings) {
        this.npcDef = npcDef;
        this.dialogueLines = dialogueLines;
        this.lineNumber = lineNumber;
        this.settings = settings;
    }

    public DialogueParser(NPCDef npcDef, List<String> dialogueLines, int lineNumber) {
        this(npcDef, dialogueLines, lineNumber, null);
    }

    public DialogueParser(NPCDef npcDef, List<String> dialogueLines) {
        this(npcDef, dialogueLines, 0, null);
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

    private Dialogue checkForSetting(List<String> dialogue) {
        String line = dialogue.get(lineNumber);
        for (DialogueLoaderSetting setting : DialogueLoaderSetting.values()) {
            if (line.startsWith(setting.name())) {
                System.out.println(lineNumber + 1 + ": " + line);
                if (setting == DialogueLoaderSetting.RAND) {
                    error("RAND setting mid file", dialogue);
                } else {
                    int leftIndex = lineNumber + 1;
                    int rightIndex = dialogue.size();
                    for (int index = lineNumber; index < dialogue.size(); index++) {
                        if (dialogue.get(index).startsWith(")")) {
                            dialogue.set(index, dialogue.get(index).substring(1));
                            rightIndex = index;
                            break;
                        }
                    }
                    System.out.println(leftIndex + "-" + rightIndex);
                    try {
                        int value = Integer.parseInt(line.substring(setting.name().length() + 1));
                        List<Dialogue[]> dialogues = new DialogueParser(npcDef, dialogue.subList(leftIndex, rightIndex), 0, new DialogueParserSettings(setting, value)).parseRandomDialogues(true);
                        if (dialogues == null) {
                            error("predicate reliant setting but had an issue being read", dialogue);
                            return null;
                        }
                        if (dialogues.size() > 2) {
                            error("predicate reliant setting but has more than 2 dialogue sets denoted by '('", dialogue);
                            return null;
                        }
                        lineNumber = rightIndex - 1;
                        return new ConditionalDialogue(setting.getBiPredicate(), new Tuple<>(dialogues.get(0), dialogues.get(1)), value);
                    } catch (NumberFormatException ignored) {
                        error("predicate reliant setting without an integer value afterwards", dialogue);
                        return null;
                    }
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
        Dialogue checkDialogue = checkForSetting(dialogue);
        if (checkDialogue != null) {
            return checkDialogue;
        }
        if (line.startsWith("Player:")) {
            return new PlayerDialogue(line.substring(8));
        }
        String npcName = npcDef.name;
        if (line.startsWith(npcName + ":")) {
            return new NPCDialogue(npcDef.id, line.substring(npcName.length() + 1));
        }
        for (DialogueLoaderAction action : DialogueLoaderAction.values()) {
            if (line.startsWith(action.name())) {
                if (action == DialogueLoaderAction.SHOP) {
                    return new ActionDialogue((player) -> {
                        npcDef.shops.get(0).open(player);
                    });
                }
                if (action == DialogueLoaderAction.ITEM) {
                    int itemId = -1;
                    try {
                        itemId = Integer.parseInt(line.substring(action.name().length() + 1));
                    } catch (NumberFormatException ignored) {
                        error("missing itemId for ITEM action", dialogue);
                    }
                    int finalItemId = itemId;
                    return new ItemDialogue().one(finalItemId, npcDef.name + " hands you " + ItemDef.get(finalItemId).descriptiveName + ".").consumer((player) -> {
                        player.getInventory().addOrDrop(finalItemId, 1);
                    });
                } else {
                    String finalLine = line;
                    return new ActionDialogue((player) -> {
                        action.getAction().accept(player);
                        if (finalLine.length() > action.name().length() + 1)
                            player.dialogue(new MessageDialogue(finalLine.substring(action.name().length() + 1)));
                    });
                }
            }
        }
        error("invalid line prefix", dialogue);
        return new MessageDialogue("");
    }

    public List<Dialogue[]> parseRandomDialogues(boolean inner) {
        List<Integer> optionLineNumbers = new ArrayList<>();
        int rightLimit = dialogueLines.size();
        for (int index = lineNumber; index < dialogueLines.size(); index++) {
            String line = dialogueLines.get(index);
            if (line.startsWith("(") && inner) {
                optionLineNumbers.add(index);
                if (settings!= null && settings.getSetting() != DialogueLoaderSetting.RAND && optionLineNumbers.size() >= 2) {
                    break;
                }
            }
            if (line.startsWith("/") && !inner) {
                optionLineNumbers.add(index);
                if (settings!= null && settings.getSetting() != DialogueLoaderSetting.RAND && optionLineNumbers.size() >= 2) {
                    break;
                }
            }
        }
        if (optionLineNumbers.size() == 0) {
            System.err.println(NPCDef.get(npcDef.id).name + " has setting but has no options specified with open parentheses '('.");
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
            if (line.startsWith(">"))
                line = line.substring(1);
        }
        return new OptionsDialogue(options);
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
        sb.append(npcDef.name).append(" error on line: ").append(lineNumber + 1).append(". Error: ").append(issue).append("\n");
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
