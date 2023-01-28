package io.ruin.data.impl.dialogue;

import io.ruin.Server;
import io.ruin.api.utils.FileUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/21/2023
 */
public class DialogueLoader {

    public static void loadDialogues() throws IOException {
        List<File> unpackedFiles = new ArrayList<>();
        File searchFolder = FileUtils.get(Server.dataFolder.getAbsolutePath() + "/npcs/dialogue/");
        if(!searchFolder.exists()){
            System.err.println("Folder " + searchFolder + " was not found!");
            return;
        }
        Files.walk(searchFolder.toPath()).forEach(p -> {
            File unpackedFile = p.toAbsolutePath().toFile();
            if(unpackedFile.isDirectory() || !unpackedFile.getName().endsWith(".txt"))
                return;
            unpackedFiles.add(unpackedFile);
        });
        for (File file : unpackedFiles) {
            createDialogue(file);
        }
    }

    private static void createDialogue(File file) {
        List<String> dialogue = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(dialogue::add);
        } catch (IOException e) {
            System.err.println(file.getAbsolutePath() + " does not exist.");
            return;
        }
        try {
            int npcId = Integer.parseInt(dialogue.get(0));
            if (npcId > 0) {
                dialogue.remove(0);
                readAndRegisterDialogue(dialogue, npcId);
            }
        } catch (NumberFormatException ignored) {
            String name = file.getName().replace(".txt", "").replace("_", " ");
            NPCDef.forEach(def -> {
                if(def.name.equalsIgnoreCase(name)) {
                    readAndRegisterDialogue(dialogue, def.id);
                }
            });
        }
    }

    // Globally accessed line number in the dialogue
    private static int lineNumber = 0;

    // Vars to keep track of consumer designation
    private static boolean consumerLine = false;
    private static BiConsumer<Player, Integer> biConsumer;
    private static int consumerValue;

    private static void readAndRegisterDialogue(List<String> dialogue, int npcId) {
        String lineOne = dialogue.get(0);
        for (DialogueLoaderSetting setting : DialogueLoaderSetting.values()) {
            if (lineOne.startsWith(setting.name())) {
                if (setting == DialogueLoaderSetting.RAND) {
                    registerRandomDialogue(parseRandomDialogues(dialogue, npcId), npcId);
                    return;
                } else {
                    biConsumer = setting.getBiConsumer();
                    registerPredicateDialogue(setting, lineOne, dialogue, npcId);
                    return;
                }
            }
        }
        registerDialogue(parseDialogue(dialogue, npcId), npcId);
    }

    private static void registerDialogue(Dialogue[] dialogue, int npcId) {
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(dialogue);
        }));
    }

    private static void registerPredicateDialogue(DialogueLoaderSetting setting, String lineOne, List<String> dialogue, int npcId) {
        int value;
        try {
            value = Integer.parseInt(lineOne.substring(setting.name().length() + 1));
            consumerValue = value;
        } catch (NumberFormatException ignored) {
            System.err.println(NPCDef.get(npcId).name + " has predicate reliant setting without an integer value afterwards.");
            return;
        }
        List<Dialogue[]> dialogues = parseRandomDialogues(dialogue, npcId);
        if (dialogues == null) {
            System.err.println(NPCDef.get(npcId).name + " has predicate reliant setting but had an issue being read.");
            return;
        }
        if (dialogues.size() > 2) {
            System.err.println(NPCDef.get(npcId).name + " has predicate reliant setting but has more than 2 dialogue sets denoted by '('.");
            return;
        }
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(dialogues.get(setting.getBiPredicate().test(player, value) ? 1 : 0));
        }));
    }

    private static Dialogue[] parseDialogue(List<String> dialogue, int npcId) {
        lineNumber = 0;
        NPCDef npcDef = NPCDef.get(npcId);
        List<Dialogue> dialogues = new ArrayList<>();
        while(lineNumber < dialogue.size()) {
            if (dialogue.get(lineNumber).equals(">"))
                break;
            dialogues.add(parseLineAndCheckConsumer(npcDef, dialogue));
            lineNumber++;
        }
        return dialogues.toArray(new Dialogue[0]);
    }

    /**
     * Registers the data from parseRandomDialogues for the given npcId.
     * @param randomDialogues The result of parseRandomDialogues()
     * @param npcId The npcId being registered
     */
    private static void registerRandomDialogue(List<Dialogue[]> randomDialogues, int npcId) {
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            Dialogue[] dialogues = Random.get(randomDialogues);
            npc.faceTemp(player);
            player.dialogue(dialogues);
        }));
    }

    private static List<Dialogue[]> parseRandomDialogues(List<String> dialogue, int npcId) {
        List<Integer> optionLineNumbers = new ArrayList<>();
        for (int index = 1; index < dialogue.size(); index++) {
            String line = dialogue.get(index);
            if (line.startsWith("(")) {
                optionLineNumbers.add(index);
            }
        }
        if (optionLineNumbers.size() == 0) {
            System.err.println(NPCDef.get(npcId).name + " has setting but has no options specified with open parentheses '('.");
            return null;
        }
        List<Dialogue[]> randomDialogues = new ArrayList<>();
        for (int index = 0; index < optionLineNumbers.size(); index++) {
            int leftBound = optionLineNumbers.get(index);
            int rightBound = index == optionLineNumbers.size() - 1 ? dialogue.size() : optionLineNumbers.get(index + 1);
            Dialogue[] parsedDialogue = parseDialogue(dialogue.subList(leftBound, rightBound), npcId);
            randomDialogues.add(parsedDialogue);
        }
        return randomDialogues;
    }

    private static Dialogue parseLineAndCheckConsumer(NPCDef npcDef, List<String> dialogue) {
        Dialogue d = parseLine(npcDef, dialogue);
        if (consumerLine) {
            d.setOnContinue(biConsumer, consumerValue);
            consumerLine = false;
        }
        return d;
    }

    private static Dialogue parseLine(NPCDef npcDef, List<String> dialogue) {
        String line = dialogue.get(lineNumber);
        if (line.startsWith(">") || line.startsWith("(")) {
            line = line.substring(1);
        }
        if (line.startsWith("*")) {
            line = line.substring(1);
            consumerLine = true;
        }
        if (line.startsWith("<")) {
            return parseOptions(npcDef, dialogue);
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
                        System.err.println(npcDef.name + " has missing itemId for ITEM action on line " + (lineNumber + 1));
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
        System.err.println(npcDef.name + " dialogue has invalid line prefix. name:" + npcDef.name + ", line:" + (lineNumber + 1));
        return new MessageDialogue("");
    }

    /**
     * Loops through the options and gets their branching dialogue.
     * < denotes the opening of an option
     * > denotes the closing of an option
     * @return Complete OptionsDialogue
     */
    private static Dialogue parseOptions(NPCDef npcDef, List<String> dialogue) {
        List<Option> options = new ArrayList<>();
        String line = dialogue.get(lineNumber);
        while (line.startsWith("<")) {
            lineNumber++;
            Dialogue[] dialogues = parseOptionBranch(npcDef, dialogue);
            options.add(new Option(line.substring(1), (player) -> player.dialogue(dialogues)));
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
    private static Dialogue[] parseOptionBranch(NPCDef npcDef, List<String> dialogue) {
        List<Dialogue> branchDialogue = new ArrayList<>();
        String line = dialogue.get(lineNumber);
        while (!line.startsWith(">")) {
            branchDialogue.add(parseLineAndCheckConsumer(npcDef, dialogue));
            line = dialogue.get(++lineNumber);
        }
        Dialogue[] dialoguesArray = new Dialogue[branchDialogue.size()];
        dialoguesArray = branchDialogue.toArray(dialoguesArray);
        return dialoguesArray;
    }
}
