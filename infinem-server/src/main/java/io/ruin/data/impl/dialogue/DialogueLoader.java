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
            String lineOne = dialogue.get(0);
            if (lineOne.contains(",")) {
                String[] idStrings = lineOne.split(",");
                for (String string : idStrings) {
                    int npcId = Integer.parseInt(string);
                    if (npcId > 0) {
                        NPCDef npcDef = NPCDef.get(npcId);
                        readAndRegisterDialogue(dialogue.subList(1, dialogue.size()), npcDef);
                    }
                }
            } else {
                int npcId = Integer.parseInt(lineOne);
                if (npcId > 0) {
                    NPCDef npcDef = NPCDef.get(npcId);
                    readAndRegisterDialogue(dialogue.subList(1, dialogue.size()), npcDef);
                }
            }
        } catch (NumberFormatException ignored) {
            String name = file.getName().replace(".txt", "").replace("_", " ");
            NPCDef.forEach(def -> {
                if(def.name.equalsIgnoreCase(name)) {
                    readAndRegisterDialogue(dialogue, def);
                }
            });
        }
    }

    private static void readAndRegisterDialogue(List<String> dialogue, NPCDef npcDef) {
        String lineOne = dialogue.get(0);
        for (DialogueLoaderSetting setting : DialogueLoaderSetting.values()) {
            if (lineOne.startsWith(setting.name())) {
                if (setting == DialogueLoaderSetting.RAND) {
                    registerRandomDialogue(new DialogueParser(npcDef, dialogue, 1).parseRandomDialogues(false), npcDef.id);
                } else {
                    registerPredicateDialogue(setting, lineOne, dialogue, npcDef);
                }
                return;
            }
        }
        registerDialogue(new DialogueParser(npcDef, dialogue).parseDialogue(), npcDef.id);
    }

    private static void registerDialogue(Dialogue[] dialogue, int npcId) {
        NPCAction.register(npcId, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(dialogue);
        }));
    }

    private static void registerPredicateDialogue(DialogueLoaderSetting setting, String lineOne, List<String> dialogue, NPCDef npcDef) {
        int value;
        try {
            value = Integer.parseInt(lineOne.substring(setting.name().length() + 1));
        } catch (NumberFormatException ignored) {
            System.err.println(NPCDef.get(npcDef.id).name + " has predicate reliant setting without an integer value afterwards.");
            return;
        }
        List<Dialogue[]> dialogues = new DialogueParser(npcDef, dialogue, 1, new DialogueParserSettings(setting, value)).parseRandomDialogues(false);
        if (dialogues == null) {
            System.err.println(NPCDef.get(npcDef.id).name + " has predicate reliant setting but had an issue being read.");
            return;
        }
        if (dialogues.size() > 2) {
            System.err.println(NPCDef.get(npcDef.id).name + " has predicate reliant setting but has more than 2 dialogue sets denoted by '('.");
            return;
        }
        NPCAction.register(npcDef.id, "talk-to", ((player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(dialogues.get(setting.getBiPredicate().test(player, value) ? 1 : 0));
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
            player.dialogue(dialogues);
        }));
    }
}
