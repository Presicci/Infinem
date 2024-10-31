package io.ruin.model.map.object.actions.impl.transportation;

import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum FairyRing { //todo add favorite option

    /* 'a' combinations */
    MUDSKIPPER_POINT(13, new int[]{0, 0, 3}, new Position(2996, 3114, 0), "Asgarnia: Mudskipper Point", 3),
    ARDOUGNE_ISLANDS(15, new int[]{0, 0, 2}, new Position(2700, 3247, 0), "Islands: South-east of Ardougne", 2),
    // Varlamore: Avium Savannah
    DORGESH_KAAN_CAVE(21, new int[]{0, 3, 3}, new Position(2735, 5221, 0), "Dungeons: Dorgesh-Kaan cave", 15),
    KANDARIN_SLAYER_CAVE(23, new int[]{0, 3, 2}, new Position(2780, 3613, 0), "Kandarin: Slayer cave near Rellekka", 14),
    PENGUIN_ISLANDS(25, new int[]{0, 3, 1}, new Position(2500, 3896, 0), "Islands: Penguins", 13),
    NECROPOLIS(27, new int[]{0, 2, 0}, new Position(3284, 2706, 0), "Kharidian Desert: Necropolis", 8),
    PISCATORIS_HUNTER_AREA(29, new int[]{0, 2, 3}, new Position(2319, 3619, 0), "Kandarin: Piscatoris Hunter area", 11),
    HOSIDIUS_VINERY(31, new int[]{0, 2, 2}, new Position(1826, 3540, 0), "Great Kourend: Hosidius Vinery", 10),
    FELDIP_HUNTER_AREA(33, new int[]{0, 2, 1}, new Position(2571, 2956, 0), "Feldip Hills: Jungle Hunter area", 9),
    LIGHTHOUSE(35, new int[]{0, 1, 0}, new Position(2503, 3636, 0), "Islands: Lighthouse", 4),
    HAUNTED_WOODS(37, new int[]{0, 1, 3}, new Position(3597, 3495, 0), "Morytania: Haunted Woods", 7),
    ABYSSAL_AREA(39, new int[]{0, 1, 2}, new Position(3059, 4875, 0), "Other Realms: Abyss", 6),
    MCGRUBOR_WOODS(41, new int[]{0, 1, 1}, new Position(2644, 3495, 0), "Kandarin: McGrubor's Wood", 5),

    /* 'b' combinations */
    ISLAND_SALVE(43, new int[]{3, 0, 0}, new Position(3410, 3324, 0), "Islands: River Salve", 48),
    DESERT(45, new int[]{3, 0, 3}, new Position(3251, 3095, 0), "Kharidian Desert: Near the Kalphite Hive", 51),
    ARDOUGNE_ZOO(49, new int[]{3, 0, 1}, new Position(2635, 3266, 0), "Kandarin: Ardougne Zoo unicorns", 49),
    ISLE_OF_SOULS(51, new int[]{3, 3, 0}, new Position(2267, 2976, 0), "Islands: Isle of Souls", 60),
    FISHER_KING(55, new int[]{3, 3, 2}, new Position(0, 0, 0), "Other Realms: Fisher King", 62),
    ZUL_ANDRA(57, new int[]{3, 3, 1}, new Position(2150, 3071, 0), "Islands: Near Zul-Andra", 61),
    CASTLE_WARS(59, new int[]{3, 2, 0}, new Position(2385, 3035, 0), "Feldip Hills: South of Castle Wars", 56),
    ENCHANTED_VALLEY(61, new int[]{3, 2, 3}, new Position(3041, 4532, 0), "Other Realms: Enchanted Valley", 59),
    MORYTANIA_MYRE(63, new int[]{3, 2, 2}, new Position(3469, 3431, 0), "Morytania: Mort Myre", 58),
    ZANARIS(65, new int[]{3, 2, 1}, new Position(2412, 4434, 0), "Other Realms: Zanaris", 57),
    TZHAAR(67, new int[]{3, 1, 0}, new Position(2437, 5126, 0), "Dungeons: TzHaar area", 52),
    //YUBIUSK(69, new int[]{}, new Position(3574, 4372, 0), "Other Realms: Yu'biusk", 55),
    LEGENDS_GUILD(71, new int[]{3, 1, 2}, new Position(2740, 3351, 0), "Kandarin: Legends' Guild", 54),
    MOUNT_QUIDAMORTEM(73, new int[]{3, 1, 1}, new Position(1295, 3493, 0), "Kebos Lowlands: South of Mount Quidamortem", 53),

    /* 'c' combinations */
    MISCELLANIA(75, new int[]{2, 0, 0}, new Position(2513, 3884, 0), "Islands: Miscellania", 32),
    YANILLE(77, new int[]{2, 0, 3}, new Position(2528, 3127, 0), "Kandarin: North-west of Yanille", 35),
    FARMING_GUILD(106, new int[]{2, 0, 2}, new Position(1302, 3762, 0), "Kebos Lowlands: South of Mount Karuulm", 34),
    ARCEUUS_LIBRARY(81, new int[]{2, 0, 1}, new Position(1639, 3868, 0), "Great Kourend: Arceuus Library", 33),
    SINCLAIR_MANSION(87, new int[]{2, 3, 2}, new Position(2705, 3576, 0), "Kandarin: Sinclair Mansion (east)", 46),
    COSMIC_PLANE(91, new int[]{2, 2, 0}, new Position(2075, 4848, 0), "Other Realms: Cosmic Entity's plane", 40),
    KARAMJA(95, new int[]{2, 2, 2}, new Position(2801, 3003, 0), "Karamja: Tai Bwo Wannai Village", 42),
    CANIFIS(97, new int[]{2, 2, 1}, new Position(3447, 3470, 0), "Morytania: Canifis", 41),
    ISLAND_DRAYNOR(99, new int[]{2, 1, 0}, new Position(3082, 3206, 0), "Islands: South of Draynor Village", 36),
    APE_ATOLL(103, new int[]{2, 1, 2}, new Position(2740, 2738, 0), "Islands: Ape Atoll", 38),
    HAZELMERE(105, new int[]{2, 1, 1}, new Position(2682, 3081, 0), "Islands: Hazelmere's home", 37),


    /* 'd' combinations */
    ABBY_NEXUS(107, new int[]{1, 0, 0}, new Position(3037, 4763, 0), "Other Realms: Abyssal Nexus", 16),
    PLAYER_OWNED_HOUSE(109, new int[]{1, 0, 3}, new Position(3200, 3200, 0), "Player Owned House", 19),
    GORAK_PLANE(111, new int[]{1, 0, 2}, new Position(3038, 5348, 0), "Other Realms: Goraks' plane", 18),
    WIZARD_TOWER(113, new int[]{1, 0, 1}, new Position(3108, 3149, 0), "Misthalin: Wizards' Tower", 17),
    TOWER_OF_LIFE(115, new int[]{1, 3, 0}, new Position(2658, 3230, 0), "Kandarin: Tower of Life", 28),
    CHASM_OF_FIRE(119, new int[]{1, 3, 2}, new Position(1455, 3658, 0), "Great Kourend: Chasm of Fire", 30),
    GNOME_GLIDER_KARAMAJA(123, new int[]{1, 2, 0}, new Position(2900, 3111, 0), "Karamja: Gnome Glider", 24),
    EDGEVILLE(127, new int[]{1, 2, 2}, new Position(3129, 3496, 0), "Misthalin: Edgeville", 26),
    POLAR_HUNTER(129, new int[]{1, 2, 1}, new Position(2744, 3719, 0), "Kandarin: Polar Hunter area", 25),
    NARDAH(133, new int[]{1, 1, 3}, new Position(3423, 3016, 0), "Kharidian Desert: North of Nardah", 23),
    POISON_WASTE(135, new int[]{1, 1, 2}, new Position(2213, 3099, 0), "Islands: Poison Waste", 22),
    HOLLOWS_HIDEOUT(137, new int[]{1, 1, 1}, new Position(3447, 9824, 0), "Dungeons: Myreque hideout under The Hollows", 21);

    private static final HashMap<Integer, FairyRing> COMPONENTS;

    static {
        COMPONENTS = new HashMap<>();
        for (FairyRing fairyRing : values()) {
            COMPONENTS.put(fairyRing.childId, fairyRing);
        }
    }

    public final int childId;
    public final int[] varpbitValues;
    public final Position position;
    public final String targetName;
    public final int lastDestination;

    FairyRing(int childId, int[] varpbitValues, Position position, String targetName, int lastDestination) {
        this.childId = childId;
        this.varpbitValues = varpbitValues;
        this.position = position;
        this.targetName = targetName;
        this.lastDestination = lastDestination;
    }

    public String getCode() {
        StringBuilder sb = new StringBuilder();
        switch (varpbitValues[0]) {
            case 0:
                sb.append("A");
                break;
            case 1:
                sb.append("D");
                break;
            case 2:
                sb.append("C");
                break;
            case 3:
                sb.append("B");
                break;
        }
        sb.append(" ");
        switch (varpbitValues[1]) {
            case 0:
                sb.append("I");
                break;
            case 1:
                sb.append("L");
                break;
            case 2:
                sb.append("K");
                break;
            case 3:
                sb.append("J");
                break;
        }
        sb.append(" ");
        switch (varpbitValues[2]) {
            case 0:
                sb.append("P");
                break;
            case 1:
                sb.append("S");
                break;
            case 2:
                sb.append("R");
                break;
            case 3:
                sb.append("Q");
                break;
        }
        return sb.toString().toUpperCase();
    }

    public static void teleport(Player player, FairyRing entry, GameObject fairyRing) {
        Item dramenStaff = player.getEquipment().findFirst(772, 9084);
        boolean hasFairyMushroom = player.getEquipment().hasId(25102) || player.getInventory().hasId(25102);
        if(dramenStaff == null && !hasFairyMushroom && !AreaReward.FAIRY_RING.hasReward(player)) {
            player.sendFilteredMessage("The fairy ring only works for those who wield fairy magic.");
            return;
        }

        if (entry != null && (entry == FISHER_KING || entry.position.equals(0, 0))) {
            player.sendMessage("This area is currently not accessible.");
            return;
        }
        if (entry == PLAYER_OWNED_HOUSE) {
            if (player.house == null) {
                player.sendMessage("You don't have a house to teleport to.");
                return;
            }
            if (player.house.getFairyRingPosition() == null) {
                player.sendMessage("Your house doesn't have a fairy ring to teleport to.");
                return;
            }
        }
        player.startEvent(event -> {
            player.lock();
            if (fairyRing != null && !player.isAt(fairyRing.x, fairyRing.y) && fairyRing.id != Buildable.SPIRIT_TREE_AND_FAIRY_RING.getBuiltObjects()[0]) { // don't walk through the spirit treel ol
                player.stepAbs(fairyRing.x, fairyRing.y, StepType.FORCE_WALK);
                event.delay(1);
            }
            player.closeInterface(InterfaceType.MAIN);
            player.closeInterface(InterfaceType.INVENTORY);
            if(entry != null)
                Config.FAIRY_RING_LAST_DESTINATION.set(player, entry.lastDestination);
            event.delay(1);
            player.getPacketSender().sendClientScript(2157);
            player.animate(3265, 30);
            player.graphics(569);
            event.delay(3);
            resetCombinationPanel(player);
            player.getTaskManager().doLookupByUUID(338, 1); // Use a Fairy Ring
            if (entry == FairyRing.ZANARIS)
                player.getTaskManager().doLookupByUUID(332, 1); // Enter Zanaris
            if(entry != null) {
                if (entry == PLAYER_OWNED_HOUSE) {
                    player.house.buildAndEnter(player, player.house.getFairyRingPosition(), false);
                    return;
                } else {
                    player.getMovement().teleport(entry.position);
                }
                if (player.hasTemporaryAttribute("FAIRYFLIGHT_RING"))  // Used so we can track last teleport for Fairy's Flight
                    player.putTemporaryAttribute("FAIRY_LAST_TELE", entry.position);
            }
            player.animate(3266);
            player.getPacketSender().sendClientScript(4079);
            event.delay(1);
            if(entry == null)
                player.dialogue(new PlayerDialogue("Wow, fairy magic sure is useful, I hardly moved at all!"));
            player.unlock();
        });
    }

    public static void openCombinationPanel(Player player, GameObject fairyRing) {
        Item dramenStaff = player.getEquipment().findFirst(772, 9084);
        boolean hasFairyMushroom = player.getEquipment().hasId(25102) || player.getInventory().hasId(25102);
        if(dramenStaff == null && !hasFairyMushroom && !AreaReward.FAIRY_RING.hasReward(player)) {
            player.sendFilteredMessage("The fairy ring only works for those who wield fairy magic.");
            return;
        }
        if (fairyRing != null)  // Used so we can track last teleport for Fairy's Flight
            player.removeTemporaryAttribute("FAIRYFLIGHT_RING");
        player.fairyRing = fairyRing;
        player.openInterface(InterfaceType.MAIN, Interface.FAIRY_RING_COMBINATION);
        openLog(player);
    }

    private static void openLog(Player player) {
        player.closeInterface(InterfaceType.INVENTORY);
        List<Integer> favorites = getFavorites(player);
        if (player.unlockedFairyRingTeleports != null) {
            for (FairyRing ring : player.unlockedFairyRingTeleports)
                if (ring != null) {
                    if (favorites.contains(ring.ordinal())) {
                        continue;
                    }
                    player.getPacketSender().sendString(Interface.FAIRY_RING_TRAVEL_LOG, ring.childId, ring.targetName.isEmpty() ? "" : "<br>" + ring.targetName);
                }
        }
        for (int i = 0; i < 4; i++) {
            if (i >= favorites.size()) {
                continue;
            }
            FairyRing fav = FairyRing.values()[favorites.get(i)];
            if (fav == null) {
                continue;
            }
            player.getPacketSender().sendString(381, 140 + i, "<br>" + fav.targetName);
            player.getPacketSender().sendString(381, 144 + i, fav.getCode());
        }
        player.openInterface(InterfaceType.INVENTORY, Interface.FAIRY_RING_TRAVEL_LOG);
    }

    private static void resetCombinationPanel(Player player) {
        Config.FAIRY_RING_LEFT.set(player, 0);
        Config.FAIRY_RING_RIGHT.set(player, 0);
        Config.FAIRY_RING_MIDDLE.set(player, 0);
    }

    public static List<Integer> getFavorites(Player player) {
        List<Integer> list = player.getAttributeOrDefault("FR_FAVORITES", new ArrayList<>());
        List<Integer> favorites = new ArrayList<>(list.size());
        for (Number number : list) {
            favorites.add(number.intValue());
        }
        return favorites;
    }

    public static void addFavourite(Player player, FairyRing ring) {
        List<Integer> favorites = getFavorites(player);
        if (favorites.size() >= 4) {
            player.sendMessage("You already have 4 favourites.");
            return;
        }
        if (favorites.contains(ring.ordinal())) {
            return;
        }
        player.sendMessage("You have added " + ring.getCode() + " to your favourites.");
        favorites.add(ring.ordinal());
        player.putAttribute("FR_FAVORITES", favorites);
        openLog(player);
    }

    public static void removeFavourite(Player player, int componentId, int option) {
        List<Integer> favorites = getFavorites(player);
        int index = -1;
        if (option == 1) {
            if (componentId >= 148 && componentId <= 151) {
                index = componentId - 148;
            }
        } else if (option == 2) {
            if (componentId >= 140 && componentId <= 143) {
                index = componentId - 140;
            }
        }
        if (index == -1) {
            return;
        }
        FairyRing ring = FairyRing.values()[favorites.get(index)];
        if (ring == null) {
            return;
        }
        player.sendMessage("You have removed " + ring.getCode() + " from your favourites.");
        favorites.remove(favorites.indexOf(ring.ordinal()));
        if (favorites.isEmpty()) {
            player.removeAttribute("FR_FAVORITES");
        } else {
            player.putAttribute("FR_FAVORITES", favorites);
        }
        openLog(player);
    }

    private static int leftButton(Player player) {
        return Config.FAIRY_RING_LEFT.get(player);
    }

    private static int rightButton(Player player) {
        return Config.FAIRY_RING_RIGHT.get(player);
    }

    private static int middleButton(Player player) {
        return Config.FAIRY_RING_MIDDLE.get(player);
    }

    private static final int[] FAIRY_RING = {29495, 29560, 29228};

    static {
        for (int ring : FAIRY_RING) {
            ObjectAction.register(ring, 1, (player, obj) -> teleport(player, ZANARIS, obj));
            ObjectAction.register(ring, 2, FairyRing::openCombinationPanel);
            ObjectAction.register(ring, 3, (player, obj) -> {
                for (FairyRing fairyRing : values()) {
                    if(fairyRing.lastDestination == Config.FAIRY_RING_LAST_DESTINATION.get(player))
                        teleport(player, fairyRing, obj);
                }
            });
        }

        InterfaceHandler.register(Interface.FAIRY_RING_COMBINATION, h -> {
            /*
             * Left wheel
             */
            h.actions[19] = (SimpleAction) p -> Config.FAIRY_RING_LEFT.set(p, (leftButton(p) == 3 ? 0 : leftButton(p) + 1));
            h.actions[20] = (SimpleAction) p -> Config.FAIRY_RING_LEFT.set(p, (leftButton(p) == 0 ? 3 : leftButton(p) - 1));
            /*
             * Middle wheel
             */
            h.actions[21] = (SimpleAction) p -> Config.FAIRY_RING_MIDDLE.set(p, (middleButton(p) == 3 ? 0 : middleButton(p) + 1));
            h.actions[22] = (SimpleAction) p -> Config.FAIRY_RING_MIDDLE.set(p, (middleButton(p) == 0 ? 3 : middleButton(p) - 1));
            /*
             * Right wheel
             */
            h.actions[23] = (SimpleAction) p -> Config.FAIRY_RING_RIGHT.set(p, (rightButton(p) == 3 ? 0 : rightButton(p) + 1));
            h.actions[24] = (SimpleAction) p -> Config.FAIRY_RING_RIGHT.set(p, (rightButton(p) == 0 ? 3 : rightButton(p) - 1));
            /*
             * Teleport
             */
            h.actions[26] = (SimpleAction) p -> {
                for (FairyRing fairyRing : values()) {
                    if (fairyRing.varpbitValues[0] == leftButton(p) && fairyRing.varpbitValues[1] == middleButton(p) && fairyRing.varpbitValues[2] == rightButton(p)) {
                        if (p.unlockedFairyRingTeleports.length < values().length) {
                            FairyRing[] expanded = new FairyRing[values().length];
                            System.arraycopy(p.unlockedFairyRingTeleports, 0, expanded, 0, p.unlockedFairyRingTeleports.length);
                            p.unlockedFairyRingTeleports = expanded;
                        }
                        p.unlockedFairyRingTeleports[fairyRing.ordinal()] = fairyRing;
                        teleport(p, fairyRing, p.fairyRing);
                        return;
                    }
                }

                resetCombinationPanel(p);
                teleport(p, null, p.fairyRing);
            };
        });

        InterfaceHandler.register(Interface.FAIRY_RING_TRAVEL_LOG, h -> {
            for (FairyRing fairyRing : values()) {
                h.actions[fairyRing.childId] = (OptionAction) (player, option) -> {
                    if (option == 2) {
                        addFavourite(player, fairyRing);
                        return;
                    }
                    Config.FAIRY_RING_LEFT.set(player, fairyRing.varpbitValues[0]);
                    Config.FAIRY_RING_MIDDLE.set(player, fairyRing.varpbitValues[1]);
                    Config.FAIRY_RING_RIGHT.set(player, fairyRing.varpbitValues[2]);
                };
                h.actions[fairyRing.childId + 1] = (SimpleAction) player -> {
                    addFavourite(player, fairyRing);
                };
            }
            for (int index = 140; index <= 151; index++) {
                int finalIndex = index;
                h.actions[index] = (OptionAction) (player, option) -> {
                    if (option == 2 || finalIndex >= 148) {
                        removeFavourite(player, finalIndex, option);
                        return;
                    }
                    List<Integer> favorites = getFavorites(player);
                    int favoriteIndex = finalIndex - 140;
                    FairyRing ring = FairyRing.values()[favorites.get(favoriteIndex)];
                    if (ring == null) return;
                    Config.FAIRY_RING_LEFT.set(player, ring.varpbitValues[0]);
                    Config.FAIRY_RING_MIDDLE.set(player, ring.varpbitValues[1]);
                    Config.FAIRY_RING_RIGHT.set(player, ring.varpbitValues[2]);
                };
            }
        });
    }

}
