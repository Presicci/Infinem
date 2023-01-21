package io.ruin.data.impl;

import io.ruin.Server;
import io.ruin.api.utils.FileUtils;
import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

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
            parseDialogue(file);
        }
    }

    private static void parseDialogue(File file) {
        String[] dialogue;
        int npcId;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> strings = new ArrayList<>();
            br.lines().forEach(strings::add);
            dialogue = strings.toArray(new String[0]);
        } catch (IOException e) {
            System.err.println(file.getAbsolutePath() + " does not exist.");
            return;
        }
        try {
            npcId = Integer.parseInt(dialogue[0]);
        } catch (NumberFormatException e) {
            System.err.println(file.getAbsolutePath() + " has invalid npcId.");
            return;
        }
        NPCDef npcDef = NPCDef.get(npcId);
        List<Dialogue> dialogues = new ArrayList<>();
        for (int lineNumber = 1; lineNumber < dialogue.length; lineNumber++) {
            dialogues.add(parseLine(npcDef, dialogue, lineNumber));
        }
        Dialogue[] dialoguesArray = new Dialogue[dialogues.size()];
        dialoguesArray = dialogues.toArray(dialoguesArray);
        Dialogue[] finalDialoguesArray = dialoguesArray;
        NPCAction.register(npcId, "talk-to", ((player, npc) -> player.dialogue(finalDialoguesArray)));
    }

    private static Dialogue parseLine(NPCDef npcDef, String[] dialogue, int  lineNumber) {
        String line = dialogue[lineNumber];
        if (line.startsWith("Player:")) {
            return new PlayerDialogue(line.substring(8));
        }
        String npcName = npcDef.name;
        if (line.startsWith(npcName + ":")) {
            return new NPCDialogue(npcDef.id, line.substring(npcName.length() + 1));
        }
        System.err.println(npcDef.name + " dialogue has invalid line prefix.");
        return new MessageDialogue("");
    }


    /*private void readDialogue(Player player) {
        try(BufferedReader br = new BufferedReader(new FileReader("C://Users//mrben//Desktop//test.txt"))) {
            List<String> strings = new ArrayList<>();
            br.lines().forEach(strings::add);
            dialogue = strings.toArray(new String[0]);
        } catch (IOException ignored) {

        }
        npcId = Integer.parseInt(dialogue[0]);
        startDialogue(player);
    }



    private void startDialogue(Player player) {
        List<Dialogue> dialogues = new ArrayList<>();
        for (int index = 1; index < dialogue.length; index++) {
            dialogues.add(parseLine(index));
        }
        Dialogue[] dialoguesArray = new Dialogue[dialogues.size()];
        dialoguesArray = dialogues.toArray(dialoguesArray);
        player.dialogue(dialoguesArray);
    }*/
}
