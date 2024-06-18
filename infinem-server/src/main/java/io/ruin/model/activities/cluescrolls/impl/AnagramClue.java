package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.cluescrolls.StepType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;

import java.util.Collections;
import java.util.List;

public class AnagramClue extends Clue {

    private final String clue;

    private AnagramClue(String clue, ClueType type) {
        super(type, StepType.ANAGRAM);
        this.clue = clue.toUpperCase();
    }

    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, "The anagram reveals<br>who to speak to next:<br>" + clue);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    /**
     * Register
     */

    private static void register(int npcId, String clue, ClueType type) {
        register(npcId, new AnagramClue(clue, type));
    }

    private static void register(String npcName, String clue, ClueType type) {
        register(Collections.singletonList(npcName), clue, type);
    }

    private static void register(List<String> npcNames, String clue, ClueType type) {
        AnagramClue anagram = new AnagramClue(clue, type);
        for(String npcName : npcNames) {
            NPCDefinition.forEach(def -> {
                if(def.name.equalsIgnoreCase(npcName)) {
                    register(def.id, anagram);
                }
            });
        }
    }

    private static void register(int npcId, AnagramClue anagram) {
        NPCDefinition def = NPCDefinition.get(npcId);
        if(def.anagram != null)
            System.err.println(def.name + " has duplicate anagram clues set!");
        def.anagram = anagram;
    }

    static {
        // Easy
        register("Hans", "Snah", ClueType.EASY);
        register("Lowe", "El Ow", ClueType.EASY);
        register("Party Pete", "Peaty Pert", ClueType.EASY);
        // Medium
        register("Brundt the Chieftain", "Dt Run B", ClueType.MEDIUM);
        register("Zoo keeper", "Eek Zero Op", ClueType.MEDIUM);
        register("Saba", "A Bas", ClueType.MEDIUM);
        register("Baraek", "A Baker", ClueType.MEDIUM);
        register("Captain Tobias", "A Basic Anti Pot", ClueType.MEDIUM);
        register("Zenesha", "A Zen She", ClueType.HARD);
        register("Jaraah", "Aha Jar", ClueType.MEDIUM);
        register("Caroline", "Arc O Line", ClueType.MEDIUM);
        register("Oracle", "Are Col", ClueType.MEDIUM);
        register("Hickton", "Thickno", ClueType.MEDIUM);
        register("Sigli the Huntsman", "Unleash Night Mist", ClueType.MEDIUM);
        register("Taria", "Ratai", ClueType.MEDIUM);
        register("Recruiter", "Err Cure It", ClueType.MEDIUM);
        register("Gabooty", "Got A Boy", ClueType.MEDIUM);
        register("Otto Godblessed", "Goblets Odd Toes", ClueType.MEDIUM);
        register("Luthas", "Halt Us", ClueType.MEDIUM);
        register(490, "I Even", ClueType.MEDIUM);
        register("Kaylee", "Leakey", ClueType.MEDIUM);
        register("Femi", "Me if", ClueType.MEDIUM);
        register("Karim", "R Ak Mi", ClueType.MEDIUM);
        register(4626, "Ok Co", ClueType.MEDIUM);//Cook
        // Hard
        register("Prospector Percy", "Copper Ore Crypts", ClueType.HARD);
        register("Doomsayer", "Do Say More", ClueType.HARD);
        register("Gnome Coach", "C On Game Hoc", ClueType.HARD);
        register("Ramara du Croissant", "Arr! So I am a crust, and?", ClueType.HARD);
        register("Strange Old Man", "Dragons Lament", ClueType.HARD);
        register("Cap'n Izzy No-Beard", "O Birdz A Zany En Pc", ClueType.HARD);
        register("Professor Onglewip", "Profs Lose Wrong Pie", ClueType.HARD);
        register("Brother Omad", "Motherboard", ClueType.HARD);
        register("Martin Thwait", "Rat Mat Within", ClueType.HARD);
        register("Trader Stan", "Red Art Tans", ClueType.HARD);
        register("Odd Old Man", "Land Doomd", ClueType.HARD);
        // Elite
        register("Oneiromancer", "Career In Moon", ClueType.ELITE);
        register("Old Crone", "Cool Nerd", ClueType.ELITE); //Check after restart
        register("Mandrith", "Dr Hitman", ClueType.ELITE);
        register("Cam the Camel", "Machete Clam", ClueType.ELITE);
    }
}
