package io.ruin.model.skills.slayer;

import io.ruin.cache.EnumMap;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.slayer.master.Mazchna;
import io.ruin.model.skills.slayer.master.Vannaka;
import io.ruin.model.stat.StatType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public enum SlayerCreature {
    MONKEYS(1, 1, 1, "monkeys"),
    GOBLINS(2, 1, 1, "goblins"),
    RATS(3, 1, 1, "rats"),
    SPIDERS(4, 1, 1, "spiders"),
    BIRDS(5, 1, 1, "birds"),
    COWS(6, 1, 5, "cows"),
    SCORPIONS(7, 1, 7, "scorpions"),
    BATS(8, 1, 5, "bats"),
    WOLVES(9, 1, 20, "wolves"),
    ZOMBIES(10, 1, 10, "zombies"),
    SKELETONS(11, 1, 15, "skeletons"),
    GHOSTS(12, 1, 13, "ghosts"),
    BEARS(13, 1, 13, "bears"),
    HILL_GIANTS(14, 1, 25, "hill giants"),
    ICE_GIANT(15, 1, 50, "ice giants"),
    FIRE_GIANTS(16, 1, 65, "fire giants"),
    MOSS_GIANTS(17, 1, 40, "moss giants"),
    TROLLS(18, 1, 60, "trolls"),
    ICE_WARRIOR(19, 1, 45, "ice warriors"),
    OGRES(20, 1, 40, "ogres"),
    HOBGOBLIN(21, 1, 20, "hobgoblins"),
    DOGS(22, 1, 15, "dogs"),
    GHOULS(23, 1, 25, "ghouls"),
    GREEN_DRAGONS(24, 1, 52, "green dragons"),
    BLUE_DRAGONS(25, 1, 65, "blue dragons"),
    RED_DRAGONS(26, 1, 68, "red dragons", (p, s) -> Config.SEEING_RED.get(p) != 0),
    BLACK_DRAGONS(27, 1, 80, "black dragons"),
    LESSER_DEMONS(28, 1, 60, "lesser demons"),
    GREATER_DEMONS(29, 1, 75, "greater demons"),
    BLACK_DEMONS(30, 1, 80, "black demons"),
    HELLHOUNDS(31, 1, 75, "hellhounds"),
    SHADOW_WARRIORS(32, 1, 60, "shadow warriors"),
    WEREWOLVES(33, 1, 60, "werewolves"),
    VAMPYRES(34, 1, 35, "vampyres", (p, s) -> s.getNpcId() == Mazchna.MAZCHNA || s.getNpcId() == Vannaka.VANNAKA || Config.ACTUAL_VAMPYRE_SLAYER.get(p) != 0),
    DAGANNOTH(35, 1, 75, "dagannoth"),
    TUROTH(36, 55, 60, "turoth"),
    CAVE_CRAWLER(37, 10, 10, "cave crawlers"),
    BANSHEE(38, 15, 20, "banshees"),
    CRAWLING_HANDS(39, 5, 10, "crawling hands"),
    INFERNAL_MAGES(40, 45, 40, "infernal mages"),
    ABERRANT_SPECRES(41, 60, 65, "aberrant spectres"),
    ABYSSAL_DEMON(42, 85, 85, "abyssal demons"),
    BASILISKS(43, 40, 40, "basilisks", (p, s) -> Config.BASILOCKED.get(p) != 0),
    COCKATRICE(44, 25, 25, "cockatrice"),
    KURASK(45, 70, 65, "kurask"),
    GARGOYLE(46, 75, 80, "gargoyles"),
    PYREFIEND(47, 30, 25, "pyrefiends"),
    BLOODVELDS(48, 50, 50, "bloodveld"),
    DUST_DEVILS(49, 65, 70, "dust devils"),
    JELLIES(50, 52, 57, "jellies"),
    ROCKSLUG(51, 20, 20, "rockslugs"),
    NECHRYAEL(52, 80, 85, "nechryael"),
    KALPHITE(53, 1, 15, "kalphite"),
    EARTH_WARRIORS(54, 1, 35, "earth warriors"),
    OTHERWORLDLY_BEINGS(55, 1, 40, "otherworldly beings"),
    ELVES(56, 1, 70, "elves"),
    DWARVES(57, 1, 6, "dwarves"),
    BRONZE_DRAGONS(58, 1, 75, "bronze dragons"),
    IRON_DRAGONS(59, 1, 80, "iron dragons"),
    STEEL_DRAGONS(60, 1, 85, "steel dragons"),
    WALL_BEASTS(61, 35, 30, "wall beasts", (p, s) -> p.getStats().get(StatType.Defence).fixedLevel >= 5),
    CAVE_SLIMES(62, 17, 15, "cave slimes"),
    CAVE_BUGS(63, 7, 1, "cave bugs"),
    SHADES(64, 1, 30, "shades"),
    CROCODILES(65, 1, 50, "crocodiles"),
    DARK_BEASTS(66, 90, 90, "dark beasts"),
    MOGRES(67, 32, 30, "mogres"),
    LIZARDS(68, 22, 1, "lizards"),
    FEVER_SPIDERS(69, 42, 40, "fever spiders"),
    HARPIE_BUG_SWARMS(70, 33, 45, "harpie bug swarms", (p, s) -> p.getStats().get(StatType.Firemaking).fixedLevel >= 33),
    SEA_SNAKES(71, 1, 50, "sea snakes"),
    SKELETAL_WYVERN(72, 72, 70, "skeletal wyverns"),
    KILLERWATTS(73, 37, 50, "killerwatts"),
    MUTATED_ZYGOMITES(74, 57, 60, "mutated zygomites"),
    ICEFIENDS(75, 1, 20, "icefiends"),
    MINOTAURS(76, 1, 7, "minotaurs"),
    FLESH_CRAWLER(77, 1, 15, "fleshcrawlers"),
    CATABLEPON(78, 1, 35, "catablepon"),
    ANKOUS(79, 1, 40, "ankou"),
    CAVE_HORRORS(80, 58, 85, "cave horrors"),
    JUNGLE_HORRORS(81, 1, 65, "jungle horrors"),
    //GORAKS(82, 1, 1, "goraks"),    // UNUSED in osrs
    SUQAHS(83, 1, 85, "suqahs"),
    BRINE_RATS(84, 47, 45, "brine rats"),
    SCABARITES(85, 1, 85, "minions of scabaras"),
    TERROR_DOGS(86, 40, 60, "terror dogs"),
    MOLANISKS(87, 1, 50, "molanisks"),
    WATERFIENDS(88, 1, 75, "waterfiends"),
    SPIRITUAL_CREATURES(89, 63, 60, "spiritual creatures"),
    LIZARDMEN(90, 1, 45, "lizardmen", (p, s) -> Config.REPTILE_GOT_RIPPED.get(p) != 0),
    MAGIC_AXES(91, 1, 1, "magic axes"),
    CAVE_KRAKENS(92, 87, 80, "cave kraken"),
    MITHRIL_DRAGON(93, 1, 85, "mithril dragons", (p, s) -> Config.I_HOPE_YOU_MITH_ME.get(p) != 0),
    AVIANSIES(94, 1, 60, "aviansies", (p, s) -> Config.WATCH_THE_BIRDIE.get(p) != 0),
    SMOKE_DEVILS(95, 93, 85, "smoke devils"),
    TZHARR(96, 1, 1, "tzhaar", (p, s) -> Config.HOT_STUFF.get(p) != 0),
    //TZTOK_JAD(97, 1, 1, "TzTok-Jad"),    // UNUSED in osrs
    MAMMOTHS(99, 1, 1, "mammoths"),
    ROGUES(100, 1, 1, "rogues"),
    ENTS(101, 1, 1, "ents"),
    BANDITS(102, 1, 1, "bandits"),
    DARK_WARRIORS(103, 1, 1, "dark warriors"),
    LAVA_DRAGONS(104, 1, 1, "lava dragons"),
    //TZKAL_ZUK(105, 1, 1, "TzKal-Zuk"),    // UNUSED in osrs
    FOSSIL_ISLAND_WYVERNS(106, 66, 60, "fossil island wyverns", (p, s) -> Config.STOP_THE_WYVERN.get(p) == 0),
    REVENANTS(107, 1, 1, "revenants"),
    ADAMANT_DRAGONS(108, 1, 90, "adamant dragons"),
    RUNE_DRAGONS(109, 1, 95, "rune dragons"),
    CHAOS_DRUID(110, 1, 1, "chaos druids"),
    WYRMS(111, 62, 1, "wyrms"),
    DRAKES(112, 84, 1, "drakes"),
    HYDRAS(113, 95, 1, "hydras"),
    //TEMPLE_SPIDERS(114, 1, 1, "temple spiders"),    // UNUSED in osrs
    //UNDEAD_DRUIDS(115, 1, 1, "undead druids"),    // UNUSED in osrs
    //SULPHUR_LIZARD(116, 1, 1, "sulphur lizards"),    // UNUSED in osrs
    //BRUTAL_BLACK_DRAGONS(117, 1, 1, "brutal black dragons"),    // UNUSED in osrs
    //SAND_CRABS(118, 1, 1, "sand crabs"),    // UNUSED in osrs
    BLACK_KNIGHTS(119, 1, 1, "black knights"),
    PIRATES(120, 1, 1, "pirates"),
    SOURHOGS(121, 1, 30, "sourhogs"),

    BOSSES(98, 1, 1, "boss", (p, s) -> Config.LIKE_A_BOSS.get(p) != 0);

    private final int uid;
    private final int req;
    private final int cbreq;
    private final String category;

    public final BiFunction<Player, SlayerMaster, Boolean> canAssign;

    private static Map<Integer, SlayerCreature> lookup = null;

    SlayerCreature(int uid, int req, int cbreq, String category) {
        this.uid = uid;
        this.req = req;
        this.cbreq = cbreq;
        this.category = category;
        this.canAssign = null;
    }

    SlayerCreature(int uid, int req, int cbreq, String category, BiFunction<Player, SlayerMaster, Boolean> canAssign) {
        this.uid = uid;
        this.req = req;
        this.cbreq = cbreq;
        this.category = category;
        this.canAssign = canAssign;
    }

    /**
     * Checks if npc is this slayer creature.
     *
     * @param npc The npc being checked.
     * @return True if npc is this slayer creature.
     */
    public boolean contains(NPC npc) {
        if (!isSlayerCreature(npc) || npc.getCombat() == null || npc.getCombat().getInfo() == null)
            return false;
        for (String s : npc.getCombat().getInfo().slayer_tasks) {
            if (category.contains(s.toLowerCase()))
                return true;
        }

        return false;
    }

    /**
     * Gets a slayer creature by uid.
     *
     * @param uid The uid of the creature.
     * @return The SlayerCreature.
     */
    public static SlayerCreature lookup(int uid) {
        if (lookup == null) {
            Map<Integer, SlayerCreature> temp = new HashMap<>();

            for (SlayerCreature sc : values()) {
                temp.put(sc.uid, sc);
            }

            lookup = temp;
        }

        return lookup.get(uid);
    }

    public int getUid() {
        return uid;
    }

    public int getReq() {
        return req;
    }

    public int getCbreq() {
        return cbreq;
    }

    /**
     * Gets a tip for the provided task.
     *
     * @param task The task that needs a tip.
     * @return The tip.
     */
    public static String getTipFor(SlayerCreature task) {
        switch (task.getUid()) {
            case 1:
                return "Monkeys can be found on Karamja, Mos Le'Harmless, and the Ardougne Zoo. Don't let anyone know I told you that last one.";
            case 2:
                return "Goblins can be found at the Goblin Village, Stronghold of Security, and Lumbridge.";
            case 3:
                return "Rats can be found in the Taverley and Edgeville dungeons, and around Lumbridge.";
            case 4:
                return "Spiders can found in the Taverley and Edgeville dungeons, and are commonly found around Lumbridge, Varrock, and Falador. Antipoison is recommend (only if fighting the poisonous variants)";
            case 5:
                return "Birds are commonly found at the Lumbridge and Falador chicken coops.";
            case 6:
                return "Cows can be mercilessly slaughtered on the Lumbridge and Falador grazing land.";
            case 7:
                return "Scorpions can be found in the Dwarven mines and around the Karamja Volcano.";
            case 8:
                return "Bats can be found in the Taverley dungeon and outside of the Mage Arena. Be aware of other players outside the Mage Arena though, as that is a very popular dueling ground.";
            case 9:
                return "Wolves can be found on the White Wolf Mountain and at the Wilderness Agility course. Be aware of other players at the agility course.";
            case 10:
                return "Zombies can be found in the Edgeville Dungeon and the Draynor sewer.";
            case 11:
                return "Skeletons can be found in the Edgeville, Taverley, and Waterfall dungeons, as well as the Catacombs of Kourend.";
            case 12:
                return "Ghosts can be found in the Taverley dungeon and the Catacombs of Kourend.";
            case 13:
                return "Bears can be found around the Ardougne mines and low-to-mid level Wilderness. Beware of other players in the wilderness.";
            case 14:
                return "Hill Giants can be found in the Edgeville and Taverley dungeons, and northeast of the Chaos Temple in the Wilderness. Beware of other players in the wilderness.";
            case 15:
                return "Ice Giants can be found in the Asgarnian Ice Caves and north-west the level 44 Obelisk in the wilderness. Beware of other players in the wilderness.";
            case 16:
                return "Fire Giants can be found in the Brimhaven and Waterfall dungeons, as well as the Catacombs of Kourend.";
            case 17:
                return "Moss Giants can be found in the Brimhaven dungeon, Varrock sewers, and the Catacombs of Kourend.";
            case 18:
                return "Trolls can be found in and around Troll Stronghold and Death Plateau, as well as south of Mount Quidamortem.";
            case 19:
                return "Ice Warriors can be found in the Asgarnian Ice dungeon and by the level 44 Obelisk inside the wilderness. Beware of other players in the wilderness.";
            case 20:
                return "Ogres can be found west of Yanille and in the Feldip Hills.";
            case 21:
                return "Hobgoblins can be found in the Asgarnian Ice dungeon as well as wandering the land west of the crafting guild and north-east of Hosidius.";
            case 22:
                return "Dogs can be found in Ardougne, McGrubor's Wood, and the Brimhaven Dungeon.";
            case 23:
                return "Ghouls can be found west of Canifis.";
            case 24:
                return "Green Dragons can be found throughout the wilderness, including south of the lava maze, south of Venenatis, and west of the Dark Warriors' Fortress. An Anti-dragon shield or Antifire is recommended.";
            case 25:
                return "Blue Dragons can be found in the Taverley, Isle of Souls, and Corsair Cove dungeons. An Anti-dragon shield or Antifire is recommended.";
            case 26:
                return "Red Dragons can be found in the Brimhaven Dungeon and the brutal variant can be found in the Catacombs of Kourend.";
            case 27:
                return "Black Dragons can be found in the Taverley and Corsair Cove dungeons. An Anti-dragon shield or Antifire is recommended.";
            case 28:
                return "Lesser demons can be found scattered throughout the wilderness, in Taverley dungeon, on Crandor, as well as in the Catacombs of Kourend.";
            case 29:
                return "Greater Demons can be found in the Brimhaven dungeon and in the wilderness at the Demonic Ruins, as well as the Catacombs of Kourend.";
            case 30:
                return "Black Demons can be found in the Taverley, Edgeville, and Brimhaven dungeons, as well as the Catacombs of Kourend.";
            case 31:
                return "Hell Hounds can be found in the Taverley dungeon, as well as the Catacombs of Kourend.";
            case 32:
                return "Shadow Warriors can be found in the Legends' Guild basement.";
            case 33:
                return "Werewolves can be found in God Wars Dungeon. Rumor has it that the denizens of Canifis are known to shift into werewolves when provoked.";
            case 34:
                return "Vampyres can be found in the God Wars Dungeon, the Haunted Woods, and near the Abandoned Mine.";
            case 35:
                return "Dagannoths can be found on Waterbirth Island and in the Catacombs of Kourend.";
            case 36:
                return "Turoths can be found in the Fremennik Slayer Dungeon. These monsters can only be damaged with leaf-bladed weapons, broad arrows/bolts, and magic dart.";
            case 37:
                return "Cave Crawlers can be found in the Fremennik Slayer Dungeon. Anti poison is highly recommended.";
            case 38:
                return "Banshees can be found in the Slayer Tower and the Catacombs of Kourend. Earmuffs should be worn to avoid their deafening screams.";
            case 39:
                return "Crawling Hands can be found in the Slayer Tower.";
            case 40:
                return "Infernal Mages can be found in the Slayer Tower.";
            case 41:
                return "Aberrant spectres can be found in the Slayer Tower. A Nose peg should be worn to withstand their stench.";
            case 42:
                return "Abyssal Demons can be found in the Slayer Tower and the Catacombs of Kourend.";
            case 43:
                return "Basilisks can be found in the Fremennik Slayer Cave. A mirror shield is required to block their terrifying gaze.";
            case 44:
                return "Cockatrices can be found in the Fremennik Slayer Dungeon. A mirror shield is required to block their terrifying gaze.";
            case 45:
                return "Kurasks can be found in the Fremennik Slayer Dungeon. These monsters can only be damaged with leaf-bladed weapons, broad arrows/bolts, and magic dart.";
            case 46:
                return "Gargoyles can be found in the Slayer Tower. A Rock Hammer is required to kill these monsters.";
            case 47:
                return "Pyrefiends can be found in the Fremennik Slayer and Smoke dungeons. Magic-resistant armour is recommended.";
            case 48:
                return "Bloodvelds can be found in the Slayer Tower and the Catacombs of Kourend. Magic-resistant armour is recommended.";
            case 49:
                return "Dust Devils can be found in the Smoke Dungeon and the Catacombs of Kourend. A facemask is required to avoid being disoriented by these monsters.";
            case 50:
                return "Jellies can be found in the Fremennik Slayer Dungeon. Magic-resistant armour is recommended.";
            case 51:
                return "Rockslugs can be found in the Fremennik Slayer Dungeon. A bag of salt is required to finish off these monsters.";
            case 52:
                return "Nechryaels can be found in the Slayer Tower and the Catacombs of Kourend.";
            case 53:
                return "Kalphites can be found in the Kalphite lair. They are weak to attacks from the Keris, and the stronger variants are capable of poisoning.";
            case 54:
                return "Earth Warriors can be found in the northern part of Edgeville Dungeon. Be aware of other players at the in the wilderness part of the dungeon.";
            case 55:
                return "Otherworldly Beings can be found in Zanaris.";
            case 56:
                return "Elves can be found in Lletya, the Iorwerth Camp, and Prifddinas.";
            case 57:
                return "Dwarves can be found in the Dwarven Mine, Taverley dungeon, and south of Ice Mountain.";
            case 58:
                return "Bronze Dragons can be found in Brimhaven Dungeon and the Catacombs of Kourend. An Anti-dragon shield or anti-fire is recommended, as well as a stab or magic weapon.";
            case 59:
                return "Iron Dragons can be found in Brimhaven Dungeon and the Catacombs of Kourend. An Anti-dragon shield or anti-fire is recommended, as well as a stab or magic weapon.";
            case 60:
                return "Steel Dragons can be found in Brimhaven Dungeon and the Catacombs of Kourend. An Anti-dragon shield or anti-fire is recommended, as well as a stab or magic weapon.";
            case 61:
                return "Wall Beasts can be found in the Lumbridge Swamp Caves. Be sure to wear a Spiny helmet when approaching them.";
            case 62:
                return "Cave Slimes can be found in the Lumbridge Swamp Caves and the Dorgesh-Kaan South Dungeon.";
            case 63:
                return "Cave Bugs can be found in the Lumbridge Swamp Caves and the Dorgesh-Kaan South Dungeon.";
            case 64:
                return "Shades can be found in Mort'ton and the Catacombs of Kourend.";
            case 65:
                return "Crocodiles can be found along the River Elid and on the southern shore of the Kharidian Desert.";
            case 66:
                return "Dark Beasts can be found in the Mourner Tunnels. Good armour/weapon is recommended when fighting these monsters.";
            case 67:
                return "Mogres can be found at Mudskipper point. Be sure to bring fishing explosives to lure them out.";
            case 68:
                return "Lizards can be found in the Kharidian Desert. Be sure to bring Ice coolers to finish them off.";
            case 69:
                return "Fever Spiders can be found on Braindeath Island in the brewery basement. Make sure to wear slayer gloves to not get damaged repeatedly.";
            case 70:
                return "Harpie Bug Swarms can be found on Karamja. They can not be damaged unless you have have a lit bug lantern equipped.";
            case 71:
                return "Sea Snakes can be found in the Miscellania & Etceteria Dungeon.";
            case 72:
                return "Skeletal Wyverns can be found in the Asgarnian Ice Dungeon. Protect from Range, an elemental shield, and good armour/weapon is suggested when fighting these monsters.";
            case 73:
                return "Killerwatts can be found in the Killerwatt plane, which can be accesses via the portal machine atop Draynor Manor. Be sure to wear Insulated boots while fighting them, or their attacks will hit much higher.";
            case 74:
                return "Zygomites can be found on Fossil Island adn Zanaris. Make sure to bring Fungicide spray to finish them off.";
            case 75:
                return "Icefiends can be found in the God Wars Dungeon and on Ice Mountain.";
            case 76:
                return "Minotaurs can be found on the 1st level of the Stronghold of Security.";
            case 77:
                return "Flesh Crawlers can be found on the 2nd level of the Stronghold of Security.";
            case 78:
                return "Catablepons can be found on the 3rd level of the Stronghold of Security.";
            case 79:
                return "Ankous can be found on the 4th level of the Stronghold of Security and at the Forgotten Cemetery, as well as the Catacombs of Kourend.";
            case 80:
                return "Cave Horrors can be found at Mos Le'Harmless Caves.";
            case 81:
                return "Jungle Horrors can be found on Mos Le'Harmless.";
            //case 82:
            //return "Goraks";
            case 83:
                return "Suqahs can be found on Lunar Isle.";
            case 84:
                return "Brine Rats can be found in the Brine Rat Cavern, which is in the north-eastern region of the Fremennik Province.";
            case 85:
                return "Scabarites can be found in the dungeon under Sophanem or the dungeon east of the Agility Pyramid.";
            case 86:
                return "Terror Dogs can be found in Tarn's Lair.";
            case 87:
                return "Molanisks can be found in the Dorgesh-Kaan South Dungeon. Make sure to ring a Slayer bell to lure them off the walls.";
            case 88:
                return "Waterfiends can be found in the Ancient Cavern or the Kraken Cove.";
            case 89:
                return "Spiritual Creatures can be found in the God Wars Dungeon.";
            case 90:
                return "Lizardmen can be found in the Lizardmen Settlement and Lizardman canyon.";
            case 91:
                return "Magic axes can be found in Taverley Dungeon and the Catacombs of Kourend, as well as through a locked gate at the hut east of mage bank. A lockpick is required to get into the hut.";
            case 92:
                return "Cave Krakens can be found in the Kraken Cove. A Magic weapon is recommended and melee will not work for these monsters.";
            case 93:
                return "Mithril Dragons can be found in the Ancient Cavern. An Anti-dragon shield or anti-fire is recommended, as well as a stab or magic weapon.";
            case 94:
                return "Aviansies can be found in God Wars Dungeon. They can only be attacked with magic or ranged.";
            case 95:
                return "Smoke Devils can be found in the Smoke Devil Dungeon. A facemask is required to avoid being disoriented by these monsters.";
            case 96:
                return "Tzhaar monsters can be found in Mor Ul Rek, beneath the Karamja Volcano.";
            //case 97:
            //return "Jad";
            case 98:
                return "This is a boss task. Head to the bosses lair to confront the challenge.";
            //case 99:
            //return "Mammoths";
            //case 100:
            //return "Rogue";
            //case 102:
            //return "Bandits";
            //case 103:
            //return "Dark Warriors";
            //case 104:
            //return "Lava dragons are a strong breed located north-east of black chinchompas. A form of anti-dragon shield is strongly recommended.";
            //case 105:
            //return "Zuk";
            case 106:
                return "Fossil Island Wyverns can be found in the Wyvern Cave on fossil island. Protect from Range, an elemental shield, and good armour/weapon is suggested when fighting these monsters.";
            //case 107:
            // return "Revenants";
            case 108:
                return "Adamant Dragons can be found in the Lithkren Vault. An Anti-dragon shield or anti-fire is recommended, as well as a stab or magic weapon.";
            case 109:
                return "Rune Dragons can be found in the Lithkren Vault. An Anti-dragon shield or anti-fire is recommended, as well as a stab or magic weapon.";
            //case 110:
            //return "Chaos Druids";
            case 111:
                return "Wyrms can be found in the Karuulm Slayer Dungeon. Magic-resistant armour is recommended and boots of stone are required to fight this monster.";
            case 112:
                return "Drakes can be found in the Karuulm Slayer Dungeon. Boots of stone are required to fight this monster.";
            case 113:
                return "Hydras can be found in the Karuulm Slayer Dungeon. Boots of stone are required to fight this monster.";
            case 121:
                return "Sourhogs can be found in the Sourhog Cave, which is located east of Draynor Manor. Wear some Reinforced goggles or their spit will burn your eyes.";
            default:
                return "I haven't managed to collect data for that NPC yet";
        }
    }

    /**
     * Gets a tip for the provided task.
     *
     * @param task The task that needs a tip.
     * @return The tip.
     */
    public static String getTipForWilderness(SlayerCreature task) {
        switch (task.getUid()) {
            case 4:
                return "Spiders can be found in the Lava Maze Dungeon, Edgeville Dungeon, and north-west of Lava Dragon Isle.";
            case 7:
                return "Scorpions can be found at the Scorpion Pit, Lava Maze, and west of the Air Obelisk.";
            case 10:
                return "Zombies can be found in Graveyard of Shadows and the Eastern Ruins.";
            case 11:
                return "Skeletons can be found in the Bone Yard, Edgeville Dungeon, and Southern Wilderness mine.";
            case 13:
                return "Bears can be found north-west of the Ferox Enclave, west of the Graveyard of Shadows, and south of the Western Ruins.";
            case 14:
                return "Hill Giants can be found near the Boneyard Hunter area and in the Deep Wilderness Dungeon.";
            case 15:
                return "Ice Giants can be found among the trees on the Frozen Waste Plateau and in the Wilderness Slayer Cave.";
            case 16:
                return "Fire Giants can be found inside the Deep Wilderness Dungeon.";
            case 17:
                return "Moss Giants can be found south-east of the Lava Maze.";
            case 19:
                return "Ice Warriors can only be found on the barrens of the Frozen Waste Plateau.";
            case 24:
                return "Green Dragons can be located west of the Dark Warrior's Fort, south-west of the Bone Yard, and in the Wilderness Slayer Cave.";
            case 27:
                return "Black Dragons can be found in the Lava Maze Dungeon and the Wilderness Slayer Cave. An Anti-dragon shield or anti-fire is recommended.";
            case 28:
                return "Lesser demons can be found outside the King Black Dragon's Lair, south of the Demonic Ruins, and the in the Wilderness Slayer Cave.";
            case 29:
                return "Greater Demons can be found in the Demonic Ruins, Lava Maze Dungeon, and Wilderness Slayer Cave.";
            case 30:
                return "Black Demons can be found in the Edgeville Wilderness Dungeon and the Wilderness Slayer Cave.";
            case 31:
                return "Hellhounds can be found in the Wilderness Slayer Cave and north-east of the Deserted Keep.";
            case 42:
                return "Abyssal Demons can be found in the Wilderness Slayer Cave.";
            case 48:
                return "Bloodvelds can be found in the Wilderness God Wars Dungeon. Magic-resistant armour is recommended.";
            case 49:
                return "Dust Devils can be found in the Wilderness Slayer Cave. A facemask is required to avoid being disoriented by these monsters.";
            case 50:
                return "Jellies can be found in the Wilderness Slayer Cave. Magic-resistant armour is recommended.";
            case 52:
                return "Nechryaels can be found in the Wilderness Slayer Cave.";
            case 54:
                return "Earth Warriors can be found in the Wilderness Edgeville Dungeon.";
            case 79:
                return "Ankou can be found in Forgotten Cemetery and the Wilderness Slayer Cave.";
            case 89:
                return "Spiritual Creatures can be found in the Wilderness God Wars Dungeon.";
            case 91:
                return "Magic axes can be found in a hut east of Mage Bank. A lockpick is required to enter.";
            case 94:
                return "Aviansies can be found in Wilderness God Wars Dungeon. They can only be attacked with magic or ranged.";
            case 98:
                return "This is a boss task. Head to the bosses lair to confront the challenge.";
            case 99:
                return "Mammoths can be found west of the Chaos Temple.";
            case 100:
                return "Rogues can be found in the Rogues' Castle and south-west of the Mage Arena.";
            case 101:
                return "Ents can be found in the forests north and east of the Chaos Temple. Remember to bring an axe!";
            case 102:
                return "Bandits can be found in the Bandit Camp.";
            case 103:
                return "Dark Warriors can be found in Dark Warriors' Fortress.";
            case 104:
                return "Lava dragons are located on the Lava Dragon Isle. An Anti-dragon shield or anti-fire is recommended.";
            case 107:
                return "Revenants can be found in the Revenant Caves.";
            case 110:
                return "Chaos Druids can be found in the Wilderness Edgeville Dungeon and the Chaos Temple.";
            case 119:
                return "Black Knights can be found north of Venenatis and in the Lava Maze.";
            case 120:
                return "Pirates can be found in the Pirates' Hideout. A lockpick is required to enter.";
            default:
                return "I don't have much knowledge on this subject for you.";
        }
    }

    /**
     * Gets the name for the task for the provided uid from enummaps.
     *
     * @param player The player with the task.
     * @param uid    The uid of the slayer creature.
     * @return The task name.
     */
    public static String taskName(Player player, int uid) {
        String result = "";
        int bossTask = Slayer.getBossTask(player);
        if (bossTask != 0) {
            result = EnumMap.get(1174).strings().get(bossTask);
        }
        result = EnumMap.get(693).strings().get(uid);
        return result == null ? "null" : result;
    }

    public static boolean isSlayerCreature(NPC npc) {
        if (npc.getDef().combatInfo == null || npc.getCombat().getInfo().slayer_tasks == null || npc.getCombat().getInfo().slayer_tasks.length <= 0)
            return false;
        return true;
    }
}