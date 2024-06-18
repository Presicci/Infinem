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

    private enum AnagramClueData {
        // Beginner
        RANAEL("Ranael", "An Earl", ClueType.BEGINNER),
        SEDRIDOR("Archmage Sedridor", "Char Game Disorder", ClueType.BEGINNER),
        APOTHECARY("Apothecary", "Carpet Ahoy", ClueType.BEGINNER),
        Doric("Doric", "I Cord", ClueType.BEGINNER),
        BRIAN("Brian", "In Bar", ClueType.BEGINNER),
        VERONICA("Veronica", "Rain Cove", ClueType.BEGINNER),
        GERTRUDE("Gertrude", "Rug Deter", ClueType.BEGINNER),
        HAIRDRESSER("Hairdresser", "Sir Share Red", ClueType.BEGINNER),
        FORTUNATO("Fortunato", "Taunt Roof", ClueType.BEGINNER),
        // Easy
        HANS("Hans", "Snah", ClueType.EASY),
        LOWE("Lowe", "El Ow", ClueType.EASY),
        PARTY_PETE("Party Pete", "Peaty Pert", ClueType.EASY),
        // Medium
        BRUNDT("Brundt the Chieftain", "Dt Run B", ClueType.MEDIUM),
        ZOO_KEEPER("Zoo keeper", "Eek Zero Op", ClueType.MEDIUM),
        SABA("Saba", "A Bas", ClueType.MEDIUM),
        BARAEK("Baraek", "A Baker", ClueType.MEDIUM),
        TOBIAS("Captain Tobias", "A Basic Anti Pot", ClueType.MEDIUM),
        ZENESHA("Zenesha", "A Zen She", ClueType.HARD),
        JARAAH("Jaraah", "Aha Jar", ClueType.MEDIUM),
        CAROLINE("Caroline", "Arc O Line", ClueType.MEDIUM),
        ORACLE("Oracle", "Are Col", ClueType.MEDIUM),
        HICKTON("Hickton", "Thickno", ClueType.MEDIUM),
        SIGLI("Sigli the Huntsman", "Unleash Night Mist", ClueType.MEDIUM),
        TARIA("Taria", "Ratai", ClueType.MEDIUM),
        RECRUITER("Recruiter", "Err Cure It", ClueType.MEDIUM),
        GABOOTY("Gabooty", "Got A Boy", ClueType.MEDIUM),
        OTTO("Otto Godblessed", "Goblets Odd Toes", ClueType.MEDIUM),
        LUTHAS("Luthas", "Halt Us", ClueType.MEDIUM),
        NIEVE(490, "I Even", ClueType.MEDIUM),
        KAYLEE("Kaylee", "Leakey", ClueType.MEDIUM),
        FEMI("Femi", "Me if", ClueType.MEDIUM),
        KARIM("Karim", "R Ak Mi", ClueType.MEDIUM),
        COOK(4626, "Ok Co", ClueType.MEDIUM),//Cook
        // Hard
        PERCY("Prospector Percy", "Copper Ore Crypts", ClueType.HARD),
        DOOMSAYER("Doomsayer", "Do Say More", ClueType.HARD),
        GNOME_COACH("Gnome Coach", "C On Game Hoc", ClueType.HARD),
        RAMARA("Ramara du Croissant", "Arr! So I am a crust, and?", ClueType.HARD),
        STRANGE_OLD_MAN("Strange Old Man", "Dragons Lament", ClueType.HARD),
        IZZY_NO_BEARD("Cap'n Izzy No-Beard", "O Birdz A Zany En Pc", ClueType.HARD),
        ONGLEWIP("Professor Onglewip", "Profs Lose Wrong Pie", ClueType.HARD),
        OMAD("Brother Omad", "Motherboard", ClueType.HARD),
        MARTIN_THWAIT("Martin Thwait", "Rat Mat Within", ClueType.HARD),
        STAN("Trader Stan", "Red Art Tans", ClueType.HARD),
        ODD_OLD_MAN("Odd Old Man", "Land Doomd", ClueType.HARD),
        // Elite
        ONEIROMANCER("Oneiromancer", "Career In Moon", ClueType.ELITE),
        OLD_CRONE("Old Crone", "Cool Nerd", ClueType.ELITE), //Check after restart
        MANDRITH("Mandrith", "Dr Hitman", ClueType.ELITE),
        CAM("Cam the Camel", "Machete Clam", ClueType.ELITE);

        AnagramClueData(String npcName, String clue, ClueType type) {
            register(npcName, clue, type);
        }

        AnagramClueData(int npcId, String clue, ClueType type) {
            register(npcId, clue, type);
        }
    }

    private final String clue;

    AnagramClue(String clue, ClueType type) {
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
}
