package io.ruin.model.content.transportation.relics;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.teleport.BasaltTeleport;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.construction.actions.AchievementGallery;
import io.ruin.model.skills.magic.MagicTeleportBounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.skills.magic.spells.modern.TeleportToHouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
public class Globetrotter {

    private static final List<Option> OPTIONS = Arrays.asList(
            new Option("Annakarl (Demonic Ruins)", (player) -> teleport(player, MagicTeleportBounds.ANNAKARL.getBounds())),
            new Option("Ape Atoll", (player) -> teleport(player, MagicTeleportBounds.APE_ATOLL.getBounds())),
            new Option("Ape Atoll Dungeon", (player) -> teleport(player, MagicTeleportBounds.APE_ATOLL_DUNGEON.getBounds())),
            new Option("Arceuus Library", (player) -> teleport(player, MagicTeleportBounds.ARCEUUS_LIBRARY.getBounds())),
            new Option("Barbarian Outpost", (player) -> teleport(player, MagicTeleportBounds.BARBARIAN.getBounds())),
            new Option("Barrows", (player) -> teleport(player, MagicTeleportBounds.BARROWS.getBounds())),
            new Option("Battlefront", (player) -> teleport(player, MagicTeleportBounds.BATTLEFRONT.getBounds())),
            new Option("Camelot", (player) -> teleport(player, MagicTeleportBounds.CAMELOT.getBounds())),
            new Option("Carrallanger (Graveyard of Shadows)", (player) -> teleport(player, MagicTeleportBounds.CARRALLANGAR.getBounds())),
            new Option("Catherby", (player) -> teleport(player, MagicTeleportBounds.CATHERBY.getBounds())),
            new Option("Draynor Manor", (player) -> teleport(player, MagicTeleportBounds.DRAYNOR_MANOR.getBounds())),
            new Option("East Ardougne", (player) -> teleport(player, MagicTeleportBounds.ARDOUGNE.getBounds())),
            new Option("Falador", (player) -> teleport(player, MagicTeleportBounds.FALADOR.getBounds())),
            new Option("Fenkenstrain's Castle", (player) -> teleport(player, MagicTeleportBounds.FENKENSTRAINS_CASTLE.getBounds())),
            new Option("Fishing Guild", (player) -> teleport(player, MagicTeleportBounds.FISHING_GUILD.getBounds())),
            new Option("Ghorrock (Frozen Waste Plateau)", (player) -> teleport(player, MagicTeleportBounds.GHORROCK.getBounds())),
            new Option("Grand Exchange", (player) -> teleport(player, MagicTeleportBounds.VARROCK_GE.getBounds())),
            new Option("Harmony Island", (player) -> teleport(player, MagicTeleportBounds.HARMONY_ISLAND.getBounds())),
            new Option("Ice Plateau", (player) -> teleport(player, MagicTeleportBounds.ICE_PLATEAU.getBounds())),
            new Option("Kharyll (Canifis)", (player) -> teleport(player, MagicTeleportBounds.KHARYLL.getBounds())),
            new Option("Kourend Castle", (player) -> teleport(player, MagicTeleportBounds.KOUREND.getBounds())),
            new Option("Lassar (Ice Mountain)", (player) -> teleport(player, MagicTeleportBounds.LASSAR.getBounds())),
            new Option("Lumbridge", (player) -> teleport(player, MagicTeleportBounds.LUMBRIDGE.getBounds())),
            new Option("Lunar Isle", (player) -> teleport(player, MagicTeleportBounds.MOONCLAN.getBounds())),
            new Option("Mind Altar", (player) -> teleport(player, MagicTeleportBounds.MIND_ALTAR.getBounds())),
            new Option("Ourania", (player) -> teleport(player, MagicTeleportBounds.OURANIA.getBounds())),
            new Option("Paddewwa (Edgeville Dungeon)", (player) -> teleport(player, MagicTeleportBounds.PADDEWWA.getBounds())),
            new Option("Port Khazard", (player) -> teleport(player, MagicTeleportBounds.KHAZARD.getBounds())),
            new Option("Salve Graveyard", (player) -> teleport(player, MagicTeleportBounds.SALVE_GRAVEYARD.getBounds())),
            new Option("Seers' Village", (player) -> teleport(player, MagicTeleportBounds.CAMELOT_SEERS.getBounds())),
            new Option("Senntisten (Digsite)", (player) -> teleport(player, MagicTeleportBounds.SENNTISTEN.getBounds())),
            new Option("Teleport to House", (player) -> {
                player.putTemporaryAttribute("LAST_TELE_HOUSE", 0);
                TeleportToHouse.teleport(player, 0);
            }),
            new Option("Teleport to House (outside)", (player) -> {
                player.putTemporaryAttribute("LAST_TELE_HOUSE", 1);
                TeleportToHouse.teleport(player, 1);
            }),
            new Option("The Forgotten Cemetary", (player) -> teleport(player, MagicTeleportBounds.CEMETARY.getBounds())),
            new Option("Troll Stronghold", (player) -> teleport(player, BasaltTeleport.canTeleportUpper.test(player) ? BasaltTeleport.TROLL_STRONGHOLD_UPPER : BasaltTeleport.TROLL_STRONGHOLD)),
            new Option("Trollheim", (player) -> teleport(player, MagicTeleportBounds.TROLLHEIM.getBounds())),
            new Option("Varrock", (player) -> teleport(player, MagicTeleportBounds.VARROCK.getBounds())),
            new Option("Watchtower", (player) -> teleport(player, MagicTeleportBounds.WATCHTOWER.getBounds())),
            new Option("Waterbirth Island", (player) -> teleport(player, MagicTeleportBounds.WATERBIRTH.getBounds())),
            new Option("Weiss", (player) -> teleport(player, BasaltTeleport.WEISS)),
            new Option("West Ardougne", (player) -> teleport(player, MagicTeleportBounds.WEST_ARDOUGNE.getBounds())),
            new Option("Yanille", (player) -> teleport(player, MagicTeleportBounds.WATCHTOWER_YANILLE.getBounds()))
    );

    private static void nexus(Player player) {
        OptionScroll.open(player, "Globetrotter Teleports", true, OPTIONS);
    }

    private static void jewellery(Player player) {
        player.removeTemporaryAttribute("LAST_TELE_HOUSE");
        player.putTemporaryAttribute("GLOBETROTTER_JEWELLERY", 1);
        AchievementGallery.openJewelleryBox(player, 3);
    }

    private static void lastDestination(Player player) {
        int house = player.getTemporaryAttributeOrDefault("LAST_TELE_HOUSE", -1);
        if (house != -1) {
            TeleportToHouse.teleport(player, house);
        }
        Position pos = player.getTemporaryAttribute("LAST_TELE");
        if (pos == null) return;
        ModernTeleport.teleport(player, pos);
    }

    private static void teleport(Player player, Bounds bounds) {
        player.removeTemporaryAttribute("LAST_TELE_HOUSE");
        player.putTemporaryAttribute("LAST_TELE", bounds.randomPosition());
        ModernTeleport.teleport(player, bounds);
    }

    private static void teleport(Player player, Position position) {
        player.removeTemporaryAttribute("LAST_TELE_HOUSE");
        player.putTemporaryAttribute("LAST_TELE", position);
        ModernTeleport.teleport(player, position);
    }

    static {
        ItemAction.registerInventory(28765, "nexus", (player, item) -> nexus(player));
        ItemAction.registerInventory(28765, "jewellery", (player, item) -> jewellery(player));
        ItemAction.registerInventory(28765, "last-destination", (player, item) -> lastDestination(player));
        ItemAction.registerEquipment(28765, "nexus", (player, item) -> nexus(player));
        ItemAction.registerEquipment(28765, "jewellery", (player, item) -> jewellery(player));
        ItemAction.registerEquipment(28765, "last-destination", (player, item) -> lastDestination(player));
    }
}
