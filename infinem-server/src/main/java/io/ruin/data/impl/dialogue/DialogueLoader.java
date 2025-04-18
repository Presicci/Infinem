package io.ruin.data.impl.dialogue;

import io.ruin.Server;
import io.ruin.api.utils.FileUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.*;

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
            String lineOne = dialogue.get(0);
            if (lineOne.contains(",")) {
                String[] idStrings = lineOne.split(",");
                for (String string : idStrings) {
                    int npcId = Integer.parseInt(string);
                    if (npcId > 0) {
                        NPCDefinition npcDefinition = NPCDefinition.get(npcId);
                        readAndRegisterDialogue(dialogue.subList(1, dialogue.size()), npcDefinition);
                    }
                }
            } else {
                int npcId = Integer.parseInt(lineOne);
                if (npcId > 0) {
                    NPCDefinition npcDefinition = NPCDefinition.get(npcId);
                    readAndRegisterDialogue(dialogue.subList(1, dialogue.size()), npcDefinition);
                }
            }
        } catch (NumberFormatException ignored) {
            String name = file.getName().replace(".txt", "").replace("_", " ");
            NPCDefinition.forEach(def -> {
                if(def.name.equalsIgnoreCase(name)) {
                    readAndRegisterDialogue(dialogue, def);
                }
            });
        }
    }

    private static void readAndRegisterDialogue(List<String> dialogue, NPCDefinition npcDefinition) {
        String lineOne = dialogue.get(0);
        for (DialogueLoaderSetting setting : DialogueLoaderSetting.values()) {
            if (lineOne.startsWith(setting.name())) {
                if (setting == DialogueLoaderSetting.RAND) {
                    registerRandomDialogue(new DialogueParser(npcDefinition, dialogue, 1).parseRandomDialogues(false), npcDefinition.id);
                } else {
                    registerPredicateDialogue(setting, lineOne, dialogue, npcDefinition);
                }
                return;
            }
        }
        registerDialogue(new DialogueParser(npcDefinition, dialogue).parseDialogue(), npcDefinition.id);
    }

    private static void registerDialogue(Dialogue[] dialogue, int npcId) {
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(npc, dialogue);
        }));
    }

    private static void registerPredicateDialogue(DialogueLoaderSetting setting, String lineOne, List<String> dialogue, NPCDefinition npcDefinition) {
        int value;
        try {
            value = Integer.parseInt(lineOne.substring(setting.name().length() + 1));
        } catch (NumberFormatException ignored) {
            System.err.println(NPCDefinition.get(npcDefinition.id).name + " has predicate reliant setting without an integer value afterwards.");
            return;
        }
        List<Dialogue[]> dialogues = new DialogueParser(npcDefinition, dialogue, 1, new DialogueParserSettings(setting, value)).parseRandomDialogues(false);
        if (dialogues == null) {
            System.err.println(NPCDefinition.get(npcDefinition.id).name + " has predicate reliant setting but had an issue being read.");
            return;
        }
        if (dialogues.size() > 2) {
            System.err.println(NPCDefinition.get(npcDefinition.id).name + " has predicate reliant setting but has more than 2 dialogue sets denoted by '/'.");
            return;
        }
        NPCAction.register(npcDefinition.id, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(npc, dialogues.get(setting.getBiPredicate().test(player, value) ? 0 : 1));
        }));
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
            player.dialogue(npc, dialogues);
        }));
    }
}
