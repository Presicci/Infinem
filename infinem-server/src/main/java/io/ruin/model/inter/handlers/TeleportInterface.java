package io.ruin.model.inter.handlers;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.cache.ObjectID;
import io.ruin.data.impl.teleports;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.network.incoming.handlers.NPCActionHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author ReverendDread on 5/19/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class TeleportInterface {

    //The last catagory opened.
    @Expose @Setter @Getter private Catagory lastCatagory;
    //The last teleport used
    @Expose @Getter @Setter private Teleport lastTeleport;
    //The last teleport selected
    @Expose @Getter @Setter private Teleport selectedTeleport;
    //The player assosiated with the interface.
    @Setter @Getter private transient Player player;

    //Doing it this way incase we add more buttons in the future
    private static final int[] TEXT_COMPONENTS = {
            88, 89, 90, 170, 171, 172, 177, 178, 179, 184, 185, 186, 191, 192, 193, 198, 199, 200,
            205, 206, 207, 212, 213, 214, 219, 220, 221, 226, 227, 228, 233, 234, 235, 240, 241, 242,
            247, 248, 249, 254, 255, 256, 261, 262, 263, 268, 269, 270
    };
    private static final int[] BUTTON_COMPONENTS = {
            41, 42, 43, 167, 168, 169, 174, 175, 176, 181, 182, 183, 188, 189, 190, 195, 196, 197,
            202, 203, 204, 209, 210, 211, 216, 217, 218, 223, 224, 225, 230, 231, 232, 237, 238, 239,
            244, 245, 246, 251, 252, 253, 258, 259, 260, 265, 266, 267
    };
    private static final int[] TELEPORT_INFO = {
            157, 158, 159
    };
    //Teleport button component
    private static final int TELEPORT_BUTTON = 155;

    /**
     * Holds data about teleports.
     */
    @RequiredArgsConstructor @Getter
    public enum Teleport {
        /*
         * Monster Teles
         */
        ROCK_CRABS(0, Catagory.MONSTERS, "Rock Crabs", new Position(2676, 3711, 0), Difficulty.BEGINNER, false),
        SAND_CRABS(1, Catagory.MONSTERS, "Sand Crabs", new Position(1723, 3464, 0), Difficulty.BEGINNER, false),
        YAKS(2, Catagory.MONSTERS, "Yaks", new Position(2326, 3804, 0), Difficulty.BEGINNER, false),
        COWS(3, Catagory.MONSTERS, "Cows", new Position(3246, 3274, 0), Difficulty.BEGINNER, false),
        GOBLIN_VILLAGE(4, Catagory.MONSTERS, "Goblin Village", new Position(2956, 3501, 0), Difficulty.EASY, false),
        EXPERIMENTS(5, Catagory.MONSTERS, "Experiments", new Position(3550, 9932, 0), Difficulty.MEDIUM, false),
        BANDIT_CAMP(6, Catagory.MONSTERS, "Bandit Camp", new Position(3170, 2982, 0), Difficulty.HARD, false),
        BRINE_RAT(7, Catagory.MONSTERS, "Brine Rat Cavern", new Position(2696, 10121, 0), Difficulty.HARD, false),
        CHASM_OF_FIRE(8, Catagory.MONSTERS, "Chasm of Fire", new Position(1435, 3671, 0), Difficulty.HARD, false),
        CRASH_SITE(9, Catagory.MONSTERS, "Crash Site Cavern", new Position(2026, 5610, 0), Difficulty.HARD, false),
        JUNGLE_DEMONS(10, Catagory.MONSTERS, "Jungle Demons", new Position(2715, 9211, 1), Difficulty.HARD, false),
        KRAKEN_COVE(11, Catagory.MONSTERS, "Kraken Cove", new Position(2278, 3609, 0), Difficulty.HARD, false),
        MOURENER(12, Catagory.MONSTERS, "Mourener Tunnels", new Position(2029, 4636, 0), Difficulty.HARD, false),
        SLAYER_TOWER(13, Catagory.MONSTERS, "Slayer Tower", new Position(3428, 3534, 0), Difficulty.HARD, false),
        STRONGHOLD_SLAYER(14, Catagory.MONSTERS, "Stronghold Slayer Cave", new Position(2431, 3424, 0), Difficulty.HARD, false),
        TROLLPIT(15, Catagory.MONSTERS, "Trollheim Trollpit", new Position(2884, 3588, 0), Difficulty.HARD, false),

        /*
         * Dungeon Teles
         */
        LUMBY_CAVES(0, Catagory.DUNGEONS, "Lumbridge Swamp Caves", new Position(3168, 9572, 0), Difficulty.EASY, false),
        STRONGHOLD_OF_SECURITY(1, Catagory.DUNGEONS,"Stronghold Of Security", new Position(3081, 3421, 0), Difficulty.MEDIUM, false),
        VARROCK_SEWERS(2, Catagory.DUNGEONS, "Varrock Sewers", new Position(3237, 3459, 0), Difficulty.MEDIUM, false),
        ANCIENT_CAVERN(3, Catagory.DUNGEONS, "Ancient Cavern", new Position(1762, 5361, 0), Difficulty.HARD, false),
        ICE_DUNGEON(4, Catagory.DUNGEONS, "Asgarnian Ice Dungeon", new Position(3006, 9550, 0), Difficulty.HARD, false),
        BRIM_DUNG(5, Catagory.DUNGEONS, "Brimhaven Dungeon", new Position(2710, 9564, 0), Difficulty.HARD, false),
        CATACOMBS(6, Catagory.DUNGEONS, "Catacombs of Kourend", new Position(1665, 10049, 0), Difficulty.HARD, false),
        FREMMY_DUNG(7, Catagory.DUNGEONS, "Freminnik Slayer Dungeon", new Position(2805, 10001, 0), Difficulty.HARD, false),
        KARUULM(8, Catagory.DUNGEONS, "Karuulm Dungeon", new Position(1311, 10188, 0), Difficulty.HARD, false),
        MOSLEHARMLESS(9, Catagory.DUNGEONS, "Mos Le Harmless", new Position(3746, 9373, 0), Difficulty.MEDIUM, false),
        SMOKE_DUNGEON(10, Catagory.DUNGEONS, "Smoke Dungeon", new Position(3203, 9379, 0), Difficulty.HARD, false),
        SMOKE_DEVIL_DUNGEON(11, Catagory.DUNGEONS, "Smoke Devil Dungeon", new Position(2412, 3060, 0), Difficulty.HARD, false),
        TAVERLY(12, Catagory.DUNGEONS, "Taverly Dungeon", new Position(2884, 9799, 0), Difficulty.HARD, false),
        WATERBIRTH(13, Catagory.DUNGEONS, "Waterbirth Dungeon", new Position(2443, 10146, 0), Difficulty.HARD, false),
        WATERFALL(14, Catagory.DUNGEONS, "Waterfall Dungeon", new Position(2573, 9866, 0), Difficulty.HARD, false),

        /*
         * Boss Teles
         */
        SIRE(0, Catagory.BOSSES, "Abyssal Sire", new Position(3038, 4771, 0), Difficulty.HARD, false),
        RUNE_DRAGS(1, Catagory.BOSSES, "Rune Dragons", new Position(1568, 5075, 0), Difficulty.HARD, false),
        KRAKEN(2, Catagory.BOSSES, "Kraken", new Position(2292, 10008, 0), Difficulty.HARD, false),
        CERB(3, Catagory.BOSSES, "Cerberus", new Position(1310, 1252, 0), Difficulty.HARD, false),
        CORP(4, Catagory.BOSSES, "Corporeal Beast", new Position(2966, 4382, 2), Difficulty.HARD, false),
        KINGS(5, Catagory.BOSSES, "Dagannoth Kings", new Position(1912, 4367, 0), Difficulty.HARD, false),
        DEMONIC_GORILLAS(6, Catagory.BOSSES, "Demonic Gorillas", new Position(2026, 5610, 0), Difficulty.HARD, false),
        ELVARG(7, Catagory.BOSSES, "Elvarg", new Position(2844, 9635, 0), Difficulty.HARD, false),
        MOLE(8, Catagory.BOSSES, "Giant Mole", new Position(1764, 5179, 0), Difficulty.HARD, false),
        GOD_WARS(9, Catagory.BOSSES, "God Wars", new Position(2916, 3746, 0), Difficulty.HARD, false),
        KALPHITE_QUEEN(10, Catagory.BOSSES, "Kalphite Queen", new Position(3505, 9493, 0), Difficulty.HARD, false),
        LIZARDMAN(11, Catagory.BOSSES, "Lizardman Shammans", new Position(1445, 3699, 0), Difficulty.HARD, false),
        MITHRIL_DRAGONS(12, Catagory.BOSSES, "Mithril Dragons", new Position(1777, 5345, 1), Difficulty.HARD, false),
        THERMO(13, Catagory.BOSSES, "Thermonuclear Smoke Devils", new Position(2412, 3060, 0), Difficulty.HARD, false),
        VORKATH(14, Catagory.BOSSES, "Vorkath", new Position(2272, 4051, 0), Difficulty.HARD, false),
        ZULRAH(15, Catagory.BOSSES, "Zulrah", new Position(2198, 3057, 0), Difficulty.HARD, false),
        NECROMANCER(16, Catagory.BOSSES, "The Necromancer", new Position(2271, 5515, 0), Difficulty.EXPERT, false),

        /*
         * Minigame Teles
         */
        LMS(0, Catagory.MINIGAMES, "Last Man Standing", new Position(3400, 3180, 0), Difficulty.NA, false),
        BARROWS(1, Catagory.MINIGAMES, "Barrows", new Position(3565, 3313, 0), Difficulty.NA, false),
        DUEL_ARENA(2, Catagory.MINIGAMES, "Duel Arena", new Position(3367, 3265, 0), Difficulty.NA, false),
        FIGHT_CAVES(3, Catagory.MINIGAMES, "Fight Caves", new Position(2443, 5171, 0), Difficulty.NA, false),
        MOTHERLODE_MINE(4, Catagory.MINIGAMES, "Motherload Mine", new Position(3751, 5669, 0), Difficulty.NA, false),
        PURO_PURO(5, Catagory.MINIGAMES, "Puro Puro", new Position(2426, 4443, 0), Difficulty.NA, false),
        WARRIORS_GUILD(6, Catagory.MINIGAMES, "Warriors Guild", new Position(2880, 3546, 0), Difficulty.NA, false),
        WINTERTODT(7, Catagory.MINIGAMES, "Wintertodt", new Position(1630, 3957, 0), Difficulty.NA, false),
        RAIDS1(8, Catagory.MINIGAMES, "Chambers of Xeric", new Position(1254, 3558, 0), Difficulty.EXPERT, false),
        PEST_CONTROL(9, Catagory.MINIGAMES, "Pest Control", new Position(2659, 2676, 0), Difficulty.NA, false),
        INFERNO(10, Catagory.MINIGAMES, "Inferno", new Position(2496, 5116, 0), Difficulty.IMPOSSIBLE, false),

        /*
         * Skilling Teles
         */
        // Guilds
        COOKING_GUILD(0, Catagory.SKILLING, "Cooking Guild", new Position(3143, 3442, 0), Difficulty.NA, false),
        CRAFTING_GUILD(1, Catagory.SKILLING, "Crafting Guild", new Position(2933, 3291, 0), Difficulty.NA, false),
        FISHING_GUILD(2, Catagory.SKILLING, "Fishing Guild", new Position(2611, 3390, 0), Difficulty.NA, false),
        MINING_GUILD(3, Catagory.SKILLING, "Mining Guild", new Position(3022, 3337, 0), Difficulty.NA, false),
        WOODCUT_GUILD(4, Catagory.SKILLING, "Woodcutting Guild", new Position(1661, 3505, 0), Difficulty.NA, false),
        //Agility
        GNOME_STRONGHOLD(5, Catagory.SKILLING, "Gnome Stronghold Course", new Position(2471, 3437, 0), Difficulty.NA, false),
        DRAYNOR_COURSE(6, Catagory.SKILLING, "Draynor Rooftop Agility", new Position(3105, 3272, 0), Difficulty.NA, false),
        ALKHARID_COURSE(7, Catagory.SKILLING, "Al Kharid Agility", new Position(3281, 3197, 0), Difficulty.NA, false),
        VARROCK_COURSE(8, Catagory.SKILLING, "Varrock Agility", new Position(3224, 3415, 0), Difficulty.NA, false),
        BARB_COURSE(9, Catagory.SKILLING, "Barbarian Agility Course", new Position(2552, 3557, 0), Difficulty.NA, false),
        CANNIFIS_COURSE(10, Catagory.SKILLING, "Canifis Agility", new Position(3505, 3486, 0), Difficulty.NA, false),
        FALADOR_COURSE(11, Catagory.SKILLING, "Falador Agility", new Position(3037, 3339, 0), Difficulty.NA, false),
        SEERS_COURSE(12, Catagory.SKILLING, "Seers Village Agility", new Position(2728, 3485, 0), Difficulty.NA, false),
        RELLEKKA_COURSE(13, Catagory.SKILLING, "Rellekka Agility", new Position(2629, 3677, 0), Difficulty.NA, false),
        ARDOUGNE_COURSE(14, Catagory.SKILLING, "Ardougne Agility", new Position(2673, 3296, 0), Difficulty.NA, false),
        //Crafting
        FLAX_FIELD(15, Catagory.SKILLING, "Flax Fields", new Position(2741, 3442, 0), Difficulty.NA, false),
        POTTERY(16, Catagory.SKILLING, "Pottery Oven", new Position(3086, 3409, 0), Difficulty.NA, false),
        //Fishing
        AERIAL_FISHING( 17, Catagory.SKILLING, "Aerial Fishing", new Position(1368, 3633, 0), Difficulty.NA, false),
        BARB_VILLAGE(18, Catagory.SKILLING, "Barbarian Village", new Position(3102, 3427, 0), Difficulty.NA, false),
        CATHERBY_FISHING(19, Catagory.SKILLING, "Catherby Shores", new Position(2809, 3435, 0), Difficulty.NA, false),
        DRAYNOR_FISHING(20, Catagory.SKILLING, "Draynor Shores", new Position(3091, 3225, 0), Difficulty.NA, false),
        KOUREND_FISHING(21, Catagory.SKILLING, "Kourend Anglers", new Position(1829, 3782, 0), Difficulty.NA, false),
        KARAMJA_FISHING(22, Catagory.SKILLING, "Karamja Docks", new Position(2925, 3177, 0), Difficulty.NA, false),
        LUMB_FISHING(23, Catagory.SKILLING, "Lumbridge River", new Position(3240, 3251, 0), Difficulty.NA, false),
        BARB_FISHING(24, Catagory.SKILLING, "Barbarian Fishing", new Position(2503, 3488, 0), Difficulty.NA, false),
        PISCATORIS_FISHING(25, Catagory.SKILLING, "Piscatoris Fishing", new Position(2341, 3666, 0), Difficulty.NA, false),
        //Hunter
        SWAMP_LIZARDS(26, Catagory.SKILLING, "Swamp Lizards", new Position(3538, 3449, 0), Difficulty.NA, false),
        BLACK_CHINS(27, Catagory.SKILLING, "Black Chinchompas", new Position(3145, 3771, 0), Difficulty.NA, true),
        DESERT_HUNTER(28, Catagory.SKILLING, "Desert Hunter", new Position(3407, 3073, 0), Difficulty.NA, false),
        FELDIP_HILLS(29, Catagory.SKILLING, "Feldip Hunter", new Position(2603, 2932, 0), Difficulty.NA, false),
        RED_SALAMANDERS(30, Catagory.SKILLING, "Red Salamanders", new Position(2451, 3221, 0), Difficulty.NA, false),
        BLACK_SALAMANDERS(31, Catagory.SKILLING, "Black Salamanders", new Position(3302, 3668, 0), Difficulty.NA, true),
        JUNGLE_HUNTER(32, Catagory.SKILLING, "Jungle Hunter", new Position(2533, 2921, 0), Difficulty.NA, false),
        SNOW_HUNTER(33, Catagory.SKILLING, "Snow Hunter", new Position(2723, 3771, 0), Difficulty.NA, false),
        WOODLAND_HUNTER(34, Catagory.SKILLING, "Woodland Hunter", new Position(2341, 3597, 0), Difficulty.NA, false),
        //Mining
        DWARVEN_MINE(35, Catagory.SKILLING, "Dwarven Mine", new Position(3043, 9817, 0), Difficulty.NA, false),
        ESSENCE_MINE(36, Catagory.SKILLING, "Essence Mine", new Position(2911, 4832, 0), Difficulty.NA, false),
        DENSE_ESSENCE(37, Catagory.SKILLING, "Dense Essence", new Position(1761, 3851, 0), Difficulty.NA, false),
        GEM_MINE(38, Catagory.SKILLING, "Gem Mine", new Position(2825, 2997, 0), Difficulty.NA, false),
        JATIZO_MINE(39, Catagory.SKILLING, "Jatizo Mine", new Position(2406, 10190, 0), Difficulty.NA, false),
        //Thieving
        ARDY_MARKET(40, Catagory.SKILLING, "Ardougne Market", new Position(2661, 3305, 0), Difficulty.NA, false),
        DRAYNOR_MARKET(41, Catagory.SKILLING, "Draynor Market", new Position(3082, 3250, 0), Difficulty.NA, false),
        KELDAGRIM_MARKET(42, Catagory.SKILLING, "Keldagrim Market", new Position(2899, 10199, 0), Difficulty.NA, false),
        ROGUES_DEN(43, Catagory.SKILLING, "Rogues Den", new Position(3046, 4969, 1), Difficulty.NA, false),

        /*
         * Wilderness Teles
         */
        MAGE_BANK(0, Catagory.WILDERNESS, "Mage Bank", new Position(2539, 4717, 0), Difficulty.NA, true),
        WEST_DRAGONS(1, Catagory.WILDERNESS, "West Dragons", new Position(2994, 3624, 0), Difficulty.NA, true),
        CHAOS_TEMPLE(2, Catagory.WILDERNESS, "Chaos Temple", new Position(3236, 3638, 0), Difficulty.NA, true),
        REVENANT_CAVE(3, Catagory.WILDERNESS, "Revanent Caves", new Position(3069, 3651, 0), Difficulty.NA, true),
        EAST_DRAGONS(4, Catagory.WILDERNESS, "East Dragons", new Position(3343, 3663, 0), Difficulty.NA, true),
        GRAVEYARD(5, Catagory.WILDERNESS, "Graveyard of Shadows", new Position(3149, 3669, 0), Difficulty.NA, true),
        CEMETARY(6, Catagory.WILDERNESS, "Forgotten Cemetary", new Position(2968, 3739, 0), Difficulty.NA, true),
        RUINS(7, Catagory.WILDERNESS, "Demonic Ruins", new Position(3286, 3881, 0), Difficulty.NA, true),
        OBELISK1(8, Catagory.WILDERNESS, "Lv 13 Obelisk", new Position(3144, 3622, 0), Difficulty.NA, true),
        OBELISK2(9, Catagory.WILDERNESS, "Lv 19 Obelisk", new Position(3211, 3664, 0), Difficulty.NA, true),
        OBELISK3(10, Catagory.WILDERNESS, "Lv 35 Obelisk", new Position(3097, 3799, 0), Difficulty.NA, true),
        OBELISK4(11, Catagory.WILDERNESS, "Lv 44 Obelisk", new Position(2979, 3871, 0), Difficulty.NA, true),
        OBELISK5(12, Catagory.WILDERNESS, "Lv 50 Obelisk", new Position(3307, 3916, 0), Difficulty.NA, true),
        ;

        private final int slot; //0-29
        private final Catagory catagory;
        private final String text;
        private final Position position;
        private final Difficulty difficulty;
        private final boolean wilderness;

        /**
         * Gets all of the teleports retaining to a certain catagory.
         * @param catagory  The catagory.
         * @return  A list of teleports that match the catagory provided, or an empty list if none are found.
         */
        public static ArrayList<Teleport> forCatagory(Catagory catagory) {
            ArrayList<Teleport> teleports = Lists.newArrayList();
            for (Teleport teleport : Teleport.values()) {
                if (teleport.catagory.equals(catagory)) {
                    teleports.add(teleport);
                }
            }
            return teleports;
        }

    }

    /**
     * Catagory for teleports.
     */
    public enum Catagory {
        MONSTERS,
        DUNGEONS,
        BOSSES,
        MINIGAMES,
        SKILLING,
        WILDERNESS
    }

    private enum Difficulty {
        NA,
        BEGINNER,
        EASY,
        MEDIUM,
        HARD,
        EXPERT,
        IMPOSSIBLE
    }

    /**
     * Opens the teleport interface with the desired catagory.
     * @param catagory  The catagory.
     */
    public void sendInterface(Catagory catagory) {
        player.openInterface(InterfaceType.MAIN, Interface.TELEPORTS);
        player.getTeleports().setLastCatagory(catagory);
        player.getTeleports().setSelectedTeleport(null);
        sendTeleports();
        sendInfo(true);
    }

    /**
     * Opens the teleports interface with the last catagory used.
     */
    public void sendInterface() {
        player.openInterface(InterfaceType.MAIN, Interface.TELEPORTS);
        player.getTeleports().setLastCatagory(lastCatagory != null ? lastCatagory : Catagory.MONSTERS);
        player.getTeleports().setSelectedTeleport(selectedTeleport);
        sendTeleports();
        sendInfo(selectedTeleport == null);
    }

    /**
     * Sends the component information such as text and sets action listeners.
     */
    private void sendTeleports() {
        List<Teleport> teleports = Teleport.forCatagory(player.getTeleports().getLastCatagory());
        IntStream.of(TEXT_COMPONENTS).forEach(i -> {
            boolean hide = teleports.stream().noneMatch(teleport -> i == TEXT_COMPONENTS[teleport.getSlot()]);
            if (hide) {
                player.getPacketSender().sendString(Interface.TELEPORTS, i, "");
            }
        });
        IntStream.of(BUTTON_COMPONENTS).forEach(i -> {
            boolean hide = teleports.stream().noneMatch(teleport -> i == BUTTON_COMPONENTS[teleport.getSlot()]);
            player.getPacketSender().setHidden(Interface.TELEPORTS, i, hide);
        });
        teleports.forEach(t -> {
            player.getPacketSender().sendString(Interface.TELEPORTS, TEXT_COMPONENTS[t.getSlot()], t.getText());
            player.getPacketSender().setHidden(Interface.TELEPORTS, BUTTON_COMPONENTS[t.getSlot()], false);
        });
    }

    /**
     * Selects a catagory and sends the interface information.
     * @param index     The catagory ordinal
     */
    private void selectCatagory(int index) {
        Catagory catagory = Catagory.values()[index - 35];
        player.getTeleports().setLastCatagory(catagory);
        player.getTeleports().setSelectedTeleport(null);
        sendTeleports();
        sendInfo(true);
    }

    /**
     * Sends teleport information above the teleport button.
     * @param clear     If the info should be cleared.
     */
    private void sendInfo(boolean clear) {
        player.getPacketSender().sendString(Interface.TELEPORTS, TELEPORT_INFO[0], clear ? "" : "Difficulty: " + player.getTeleports().getSelectedTeleport().getDifficulty());
        player.getPacketSender().sendString(Interface.TELEPORTS, TELEPORT_INFO[1], clear ? "" : "Wilderness: " + getWilderness());
        player.getPacketSender().sendString(Interface.TELEPORTS, TELEPORT_INFO[2], clear ? "" : "Teleport Cost: Free");
    }

    /**
     * Selects a teleport.
     * @param component     The component id.
     */
    private void clickButton(int component) {
        List<Teleport> teleportList = Teleport.forCatagory(player.getTeleports().getLastCatagory());
        for (Teleport teleport : teleportList) {
            int button = BUTTON_COMPONENTS[teleport.getSlot()];
            if (button == component) {
                player.getTeleports().setSelectedTeleport(teleport);
                sendInfo(false);
            }
        }
    }

    /**
     * Teleports the player to the last selected teleport.
     */
    private void teleport() {
        teleports.teleport(player, player.getTeleports().getSelectedTeleport().getPosition());
        player.getTeleports().setLastTeleport(player.getTeleports().getSelectedTeleport());
        player.closeInterface(InterfaceType.MAIN);
    }

    private void teleportPrevious() {
        Teleport previousTeleport = player.getTeleports().getLastTeleport();
        if (previousTeleport != null) {
            teleports.teleport(player, player.getTeleports().getLastTeleport().getPosition());
        } else {
            player.sendMessage("You haven't teleported previously.");
        }
    }

    /**
     * Formats selected teleport into color wrapped varient.
     * @return
     */
    private String getWilderness() {
        Teleport selectedTeleport = player.getTeleports().getSelectedTeleport();
        boolean wilderness = selectedTeleport != null && selectedTeleport.wilderness;
        return wilderness ? Color.RED.wrap("Wild!") : Color.GREEN.wrap("Safe");
    }

    static {
        InterfaceHandler.register(Interface.TELEPORTS, (interfaceHandler -> {
            for (int component = 35; component < 41; component++) {
                int finalComponent = component;
                interfaceHandler.actions[component] = (SimpleAction) player -> player.getTeleports().selectCatagory(finalComponent);
            }
            for (int component : BUTTON_COMPONENTS) {
                int finalComponent = component;
                interfaceHandler.actions[component] = (SimpleAction) player -> player.getTeleports().clickButton(finalComponent);
            }
            interfaceHandler.actions[TELEPORT_BUTTON] = (SimpleAction) player -> player.getTeleports().teleport();
        }));
        ObjectAction.register(ObjectID.KRONOS_TELEPORTER, "teleport", (player, object) -> player.getTeleports().sendInterface());
        ObjectAction.register(ObjectID.KRONOS_TELEPORTER, "previous teleport", (player, object) -> player.getTeleports().teleportPrevious());
    }

}
