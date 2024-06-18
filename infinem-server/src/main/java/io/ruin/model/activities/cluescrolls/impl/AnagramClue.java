package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.cluescrolls.StepType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;

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
        BARAEK("Baraek", "A Baker", ClueType.MEDIUM, "How many stalls are there in Varrock Square?", 5),
        SABA("Saba", "A Bas", ClueType.MEDIUM),
        TOBIAS("Captain Tobias", "A Basic Anti Pot", ClueType.MEDIUM, "How many ships are there docked at Port Sarim currently?", 6),
        JARAAH("Jaraah", "Aha Jar", ClueType.MEDIUM),
        // A HEART
        CAROLINE("Caroline", "Arc O Line", ClueType.MEDIUM, "How many fishermen are there on the fishing platform?", 11),
        // AREA CHEF TREK
        ORACLE("Oracle", "Are Col", ClueType.MEDIUM, "If x is 15 and y is 3 what is 3x + y?", 48),
        // ARMCHAIR THE PELT
        // BAIL TRIMS
        // CALAMARI MADE MUD
        // CLASH ION
        BRUNDT("Brundt the Chieftain", "Dt Run B", ClueType.MEDIUM, "How many people are waiting for the next bard to perform?", 4),
        ZOO_KEEPER("Zoo keeper", "Eek Zero Op", ClueType.MEDIUM, "How many animals are in the Ardougne Zoo?", 40),
        // LOWE
        OTTO("Otto Godblessed", "Goblets Odd Toes", ClueType.MEDIUM, "How many types of dragon are there beneath the whirlpool's cavern?", 2),
        // GOBLIN KERN
        GABOOTY("Gabooty", "Got A Boy", ClueType.MEDIUM, "How many buildings in the village?", 11),
        LUTHAS("Luthas", "Halt Us", ClueType.MEDIUM),
        // HEORIC
        // HICK JET
        // HIS PHOR
        // I AM SIR
        // ICY FE
        // I DOOM ICON INN
        NIEVE(490, "I Even", ClueType.MEDIUM, "How many farming patches are there in Gnome stronghold?", 2),
        // KAY SIR
        // LAME T
        // LARK IN DOG
        KAYLEE("Kaylee", "Leakey", ClueType.MEDIUM, "How many chairs are there in the Rising Sun?", 18),
        // LOW LAG
        FEMI("Femi", "Me if", ClueType.MEDIUM),
        // NOD MED
        COOK(4626, "Ok Co", ClueType.MEDIUM, "How many cannons does Lumbridge Castle have?", 9),
        // PACINNG A TAIE
        // PEAK REFLEX
        // PEATY PERT
        // QUE SIR
        KARIM("Karim", "R Ak Mi", ClueType.MEDIUM, "I have 16 kebabs, I eat one myself and share the rest equally between 3 friends. How many do they have each?", 5),
        TARIA("Taria", "Ratai", ClueType.MEDIUM, "How many buildings are there in Rimmington?", 7),
        // R SLICER
        // SAND NUT
        // TAMED ROCKS
        HICKTON("Hickton", "Thickno", ClueType.MEDIUM, "How many ranges are there in Catherby?", 2),
        // UESNKRL NRIEDDO
        // VESTE

        // NOT ON WIKI
        SIGLI("Sigli the Huntsman", "Unleash Night Mist", ClueType.MEDIUM),
        RECRUITER("Recruiter", "Err Cure It", ClueType.MEDIUM),
        // Hard
        ZENESHA("Zenesha", "A Zen She", ClueType.HARD),
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

        AnagramClueData(String npcName, String clue, ClueType type, String challenge, int answer) {
            register(npcName, clue, type, challenge, answer);
        }

        AnagramClueData(int npcId, String clue, ClueType type, String challenge, int answer) {
            register(npcId, clue, type, challenge, answer);
        }
    }

    private final String clue;
    private String challenge;
    private int answer;

    AnagramClue(String clue, ClueType type) {
        super(type, StepType.ANAGRAM);
        this.clue = clue.toUpperCase();
    }

    AnagramClue(String clue, ClueType type, String challenge, int answer) {
        super(type, StepType.ANAGRAM);
        this.clue = clue.toUpperCase();
        this.challenge = challenge;
        this.answer = answer;
    }

    public static final String KEY = "ANAG_CHALLENGE";

    @Override
    public void open(Player player) {
        if (player.hasAttribute(KEY) && hasChallenge()) {
            player.openInterface(InterfaceType.MAIN, 203);
            player.getPacketSender().sendString(203, 2, challenge);
        } else {
            player.openInterface(InterfaceType.MAIN, 203);
            player.getPacketSender().sendString(203, 2, "The anagram reveals<br>who to speak to next:<br>" + clue);
        }
    }

    public boolean hasChallenge() {
        return challenge != null && !challenge.isEmpty();
    }

    public void challengeDialogue(Player player, NPC npc) {
        if (!hasChallenge()) return;
        if (player.hasAttribute(KEY)) {
            player.dialogue(
                    new NPCDialogue(npc, "Please enter the answer to the question."),
                    new ActionDialogue(() -> {
                        player.integerInput("Please enter the answer to the question", i -> {
                            if (i == answer) {
                                player.dialogue(
                                        new NPCDialogue(npc, "Correct!"),
                                        new ActionDialogue(() -> {
                                            player.removeAttribute(KEY);
                                            this.advance(player);
                                        }
                                ));
                            } else {
                                player.dialogue(new NPCDialogue(npc, "No, try again."));
                            }
                        });
                    })
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "I have a question for you."),
                    new ActionDialogue(() -> {
                        player.putAttribute(KEY, 1);
                        player.dialogue(new ItemDialogue().one(type.clueId, npc.getDef().name + " has given you a challenge scroll!"));
                    })
            );
        }
    }

    /**
     * Register
     */
    private static void register(int npcId, String clue, ClueType type) {
        register(npcId, new AnagramClue(clue, type));
    }

    private static void register(String npcName, String clue, ClueType type) {
        register(Collections.singletonList(npcName), new AnagramClue(clue, type));
    }

    private static void register(int npcId, String clue, ClueType type, String challenge, int answer) {
        register(npcId, new AnagramClue(clue, type, challenge, answer));
    }

    private static void register(String npcName, String clue, ClueType type, String challenge, int answer) {
        register(Collections.singletonList(npcName), new AnagramClue(clue, type, challenge, answer));
    }

    private static void register(List<String> npcNames, AnagramClue anagram) {
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
