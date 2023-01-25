package io.ruin.data.impl;

import io.ruin.Server;
import io.ruin.api.utils.FileUtils;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
                parseDialogue(dialogue, npcId);
                return;
            }
        } catch (NumberFormatException ignored) {}
        NPCDef.forEach(def -> {
            String name = file.getName().replace(".txt", "").replace("_", " ");
            if(def.name.equalsIgnoreCase(name)) {
                parseDialogue(dialogue, def.id);
            }
        });
    }

    private static int lineNumber = 0;

    private static void parseDialogue(List<String> dialogue, int npcId) {
        lineNumber = 0;
        NPCDef npcDef = NPCDef.get(npcId);
        List<Dialogue> dialogues = new ArrayList<>();
        for (int line = lineNumber; line < dialogue.size(); line++) {
            dialogues.add(parseLine(npcDef, dialogue));
        }
        Dialogue[] dialoguesArray = new Dialogue[dialogues.size()];
        dialoguesArray = dialogues.toArray(dialoguesArray);
        Dialogue[] finalDialoguesArray = dialoguesArray;
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(finalDialoguesArray);
        }));
    }

    private static Dialogue parseLine(NPCDef npcDef, List<String> dialogue) {
        String line = dialogue.get(lineNumber);
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
                return new ActionDialogue(action.getAction());
            }
        }
        System.err.println(npcDef.name + " dialogue has invalid line prefix. name:" + npcDef.name);
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
            options.add(new Option(line, (player) -> player.dialogue(parseOptionBranch(npcDef, dialogue))));
            line = dialogue.get(lineNumber);
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
            branchDialogue.add(parseLine(npcDef, dialogue));
            line = dialogue.get(++lineNumber);
        }
        Dialogue[] dialoguesArray = new Dialogue[branchDialogue.size()];
        dialoguesArray = branchDialogue.toArray(dialoguesArray);
        return dialoguesArray;
    }
}
