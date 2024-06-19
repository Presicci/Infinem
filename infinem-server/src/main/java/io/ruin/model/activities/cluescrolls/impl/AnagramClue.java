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
        SEDRIDOR("Sedridor", "Char Game Disorder", ClueType.BEGINNER),
        APOTHECARY("Apothecary", "Carpet Ahoy", ClueType.BEGINNER),
        Doric("Doric", "I Cord", ClueType.BEGINNER),
        BRIAN("Brian", "In Bar", ClueType.BEGINNER),
        VERONICA("Veronica", "Rain Cove", ClueType.BEGINNER),
        GERTRUDE("Gertrude", "Rug Deter", ClueType.BEGINNER),
        HAIRDRESSER("Hairdresser", "Sir Share Red", ClueType.BEGINNER),
        FORTUNATO("Fortunato", "Taunt Roof", ClueType.BEGINNER),
        // Medium
        BARAEK("Baraek", "A Baker", ClueType.MEDIUM, "How many stalls are there in Varrock Square?", 5),
        SABA("Saba", "A Bas", ClueType.MEDIUM),
        TOBIAS("Captain Tobias", "A Basic Anti Pot", ClueType.MEDIUM, "How many ships are there docked at Port Sarim currently?", 6),
        JARAAH("Jaraah", "Aha Jar", ClueType.MEDIUM),
        ARETHA("Aretha", "A Heart", ClueType.MEDIUM, "32 - 5x = 22, what is x?", 2),
        CAROLINE("Caroline", "Arc O Line", ClueType.MEDIUM, "How many fishermen are there on the fishing platform?", 11),
        FATHER_AERECK("Father Aereck", "Area Chef Trek", ClueType.MEDIUM, "How many gravestones are in the church graveyard?", 19),
        ORACLE("Oracle", "Are Col", ClueType.MEDIUM, "If x is 15 and y is 3 what is 3x + y?", 48),
        CHARLIE_THE_TRAMP("Charlie the Tramp", "Armchair The Pelt", ClueType.MEDIUM, "How many coins would I have if I had 0 coins and attempted to buy 3 loaves of bread?", 0),
        BRIMSTAIL("Brimstail", "Bail Trims", ClueType.MEDIUM),
        MADAME_CALDARIUM("Madame Caldarium", "Calamari Made Mud", ClueType.MEDIUM, "What is 3(5-3)?", 6),
        NICHOLAS("Nicholas", "Clash Ion", ClueType.MEDIUM, "How many windows are in Tynan's shop?", 4),
        BRUNDT("Brundt the Chieftain", "Dt Run B", ClueType.MEDIUM, "How many people are waiting for the next bard to perform?", 4),
        ZOO_KEEPER("Zoo keeper", "Eek Zero Op", ClueType.MEDIUM, "How many animals are in the Ardougne Zoo?", 40),
        LOWE("Lowe", "El Ow", ClueType.MEDIUM),
        OTTO("Otto Godblessed", "Goblets Odd Toes", ClueType.MEDIUM, "How many types of dragon are there beneath the whirlpool's cavern?", 2),
        KING_BOLREN("King Bolren", "Goblin Kern", ClueType.MEDIUM),
        GABOOTY("Gabooty", "Got A Boy", ClueType.MEDIUM, "How many buildings in the village?", 11),
        LUTHAS("Luthas", "Halt Us", ClueType.MEDIUM),
        EOHRIC("Eohric", "Heoric", ClueType.MEDIUM, "King Arthur and Merlin sit down at the Round Table with 8 knights. How many degrees does each get?", 36),
        JETHICK("Jethick", "Hick Jet", ClueType.MEDIUM, "How many graves are there in the city graveyard?", 38),
        HORPHIS("Horphis", "His Phor", ClueType.MEDIUM, "On a scale of 1-10, how helpful is Logosia?", 1),
        MARISI("Marisi", "I Am Sir", ClueType.MEDIUM, "How many cities form the Kingdom of Great Kourend?", 5),
        //FYCIE("Fycie", "Icy Fe", ClueType.MEDIUM),
        DOMINIC("Dominic Onion", "I Doom Icon Inn", ClueType.MEDIUM, "How many reward points does a herb box cost?", 9500),
        NIEVE("Nieve", "I Even", ClueType.MEDIUM, "How many farming patches are there in Gnome stronghold?", 2),
        SIR_KAY("Sir Kay", "Kay Sir", ClueType.MEDIUM, "How many fountains are there within the grounds of Camelot castle.", 6),
        // LAME T - VARLAMORE
        KING_ROALD("King Roald", "Lark In Dog", ClueType.MEDIUM, "How many bookcases are there in the Varrock palace library?", 24),
        KAYLEE("Kaylee", "Leakey", ClueType.MEDIUM, "How many chairs are there in the Rising Sun?", 18),
        GALLOW("Gallow", "Low Lag", ClueType.MEDIUM, "How many vine patches can you find in this vinery?", 12),
        FEMI("Femi", "Me if", ClueType.MEDIUM),
        EDMOND("Edmond", "Nod Med", ClueType.MEDIUM, "How many pigeon cages are there around the back of Jerico's house?", 3),
        COOK(4626, "Ok Co", ClueType.MEDIUM, "How many cannons does Lumbridge Castle have?", 9),
        CAPTAIN_GINEA("Captain Ginea", "Pacinng A Taie", ClueType.MEDIUM, "1 soldier can deal with 6 lizardmen. How many soldiers do we need for an army of 678 lizardmen?", 113),
        FLAX_KEEPER("Flax keeper", "Peak Reflex", ClueType.MEDIUM, "If I have 1014 flax, and I spin a third of them into bowstring, how many flax do I have left?", 673),
        PARTY_PETE("Party Pete", "Peaty Pert", ClueType.MEDIUM),
        SQUIRE("Squire", "Que Sir", ClueType.MEDIUM, "White knights are superior to black knights. 2 white knights can handle 3 black knights. How many knights do we need for an army of 981 black knights?", 654),
        KARIM("Karim", "R Ak Mi", ClueType.MEDIUM, "I have 16 kebabs, I eat one myself and share the rest equally between 3 friends. How many do they have each?", 5),
        TARIA("Taria", "Ratai", ClueType.MEDIUM, "How many buildings are there in Rimmington?", 7),
        CLERRIS("Clerris", "R Slicer", ClueType.MEDIUM, "If I have 1,000 blood runes, and cast 131 ice barrage spells, how many blood runes do I have left?", 738),
        DUNSTAN("Dunstan", "Sand Nut", ClueType.MEDIUM, "How much smithing experience does one receive for smelting a blurite bar?", 8),
        DOCKMASTER("Dockmaster", "Tamed Rocks", ClueType.MEDIUM, "What is the cube root of 125?", 5),
        HICKTON("Hickton", "Thickno", ClueType.MEDIUM, "How many ranges are there in Catherby?", 2),
        DRUNKEN_SOLDIER("Drunken soldier", "Uesnkrl Nrieddo", ClueType.MEDIUM, "If 13 Shayzien Soldiers kill 46 Lizardmen each in a day, how many Lizardmen have they killed in total in a single day?", 598),
        // STEVE - Not using cause we have nieve

        // Hard
        BRAMBICKLE("Brambickle", "Baker Climb", ClueType.HARD, true),
        LUMBRIDGE_GUIDE(306, "Blue Grim Guided", ClueType.HARD, true),
        BOLKOY("Bolkoy", "By Look", ClueType.HARD, "How many flowers are there in the clearing below this platform?", 13),
        GNOME_COACH("Gnome Coach", "C On Game Hoc", ClueType.HARD, "How many gnomes on the Gnome ball field have red patches on their uniforms?", 6),
        PERCY("Prospector Percy", "Copper Ore Crypts", ClueType.HARD, "During a party, everyone shook hands with everyone else. There were 66 handshakes. How many people were at the party?", 12),
        //DAER_KRAND("Daer Krand", "Darn Drake", ClueType.HARD, true),
        DARK_MAGE("Dark mage", "Dekagram", ClueType.HARD, "How many rifts are found here in the abyss?", 13),
        DOOMSAYER("Doomsayer", "Do Say More", ClueType.HARD, "What is 40 divided by 1/2 plus 15?", 95),
        STRANGE_OLD_MAN("Strange Old Man", "Dragons Lament", ClueType.HARD, "One pipe fills a barrel in 1 hour while another pipe can fill the same barrel in 2 hours. How many minutes will it take to fill the take if both pipes are used?", 40),
        DRUNKEN_DWARF("Drunken Dwarf", "Dr Warden Funk", ClueType.HARD, true),
        OMAD("Brother Omad", "Motherboard", ClueType.HARD, "What is the next number? 12, 13, 15, 17, 111, 113, 117, 119, 123....?", 129),
        LAMMY_LANGLE("Lammy Langle", "My Mangle Lal", ClueType.HARD, true),
        IZZY_NO_BEARD("Cap'n Izzy No-Beard", "O Birdz A Zany En Pc", ClueType.HARD, "How many Banana Trees are there in the plantation?", 33),
        BROTHER_TRANQUILITY("Brother Tranquility", "Quit Horrible Tyrant", ClueType.HARD, "If I have 49 bottles of rum to share between 7 pirates, how many would each pirate get?", 7),
        MARTIN_THWAIT("Martin Thwait", "Rat Mat Within", ClueType.HARD, "How many natural fires burn in Rogue's Den?", 2),
        WISE_OLD_MAN(8052, "Slide Woman", ClueType.HARD, "How many bookcases are in the Wise Old Man's house?", 28),
        EVIL_DAVE("Evil Dave", "Veil Veda", ClueType.HARD, "What is 333 multiplied by 2?", 666),
        //AWOWOGEI("Awowogei", "Woo An Egg Kiwi", ClueType.HARD, "If I have 303 bananas, and share them between 31 friends evenly, only handing out full bananas. How many will I have left over?", 24),

        // Elite
        //AT HERG
        ONEIROMANCER("Oneiromancer", "Career In Moon", ClueType.ELITE, "How many Suqah inhabit Lunar isle?", 25),
        OLD_CRONE("Old Crone", "Cool Nerd", ClueType.ELITE, "What is the combined combat level of each species that live in Slayer tower?", 619), //Check after restart
        MANDRITH("Mandrith", "Dr Hitman", ClueType.ELITE, "How many scorpions live under the pit?", 20),
        //LADDER MEMO GUV
        CAM("Cam the Camel", "Machete Clam", ClueType.ELITE, "How many items can carry water in Gielinor?", 6),
        //MAJORS LAVA BADS AIR
        //NO OWNER
        //OUR OWN NEEDS
        //SNAKES SO I SAIL
        SIGLI("Sigli the Huntsman", "Unleash Night Mist", ClueType.ELITE),

        // Master
        //A Elf Knows
        //Brucie Catnap
        //Car If Ices
        //Ded War
        //Dim Tharn
        //Duo Plug
        //Forlun
        //Im N Zezim
        //Mal in Tau
        //Mold La Ran
        //Mus Kil Reader
        //Rip Maul
        //Rue Go
        //Slam Duster Grail
        //Ten Wigs On
        //Twenty Cure Iron
        ;

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

        AnagramClueData(String npcName, String clue, ClueType type, boolean puzzleBox) {
            register(npcName, clue, type, puzzleBox);
        }

        AnagramClueData(int npcId, String clue, ClueType type, boolean puzzleBox) {
            register(npcId, clue, type, puzzleBox);
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

    AnagramClue(String clue, ClueType type, boolean puzzleBox) {
        super(type, StepType.ANAGRAM, puzzleBox);
        this.clue = clue.toUpperCase();
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

    private static void register(int npcId, String clue, ClueType type, boolean puzzleBox) {
        register(npcId, new AnagramClue(clue, type, puzzleBox));
    }

    private static void register(String npcName, String clue, ClueType type, boolean puzzleBox) {
        register(Collections.singletonList(npcName), new AnagramClue(clue, type, puzzleBox));
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
