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

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
public class Globetrotter {

    private static void nexus(Player player) {
        OptionScroll.open(player, "Globetrotter Teleports", true, Arrays.asList(
                new Option("Annakarl (Demonic Ruins)", () -> teleport(player, MagicTeleportBounds.ANNAKARL.getBounds())),
                new Option("Ape Atoll", () -> teleport(player, MagicTeleportBounds.APE_ATOLL.getBounds())),
                new Option("Ape Atoll Dungeon", () -> teleport(player, MagicTeleportBounds.APE_ATOLL_DUNGEON.getBounds())),
                new Option("Arceuus Library", () -> teleport(player, MagicTeleportBounds.ARCEUUS_LIBRARY.getBounds())),
                new Option("Barbarian Outpost", () -> teleport(player, MagicTeleportBounds.BARBARIAN.getBounds())),
                new Option("Barrows", () -> teleport(player, MagicTeleportBounds.BARROWS.getBounds())),
                new Option("Battlefront", () -> teleport(player, MagicTeleportBounds.BATTLEFRONT.getBounds())),
                new Option("Camelot", () -> teleport(player, MagicTeleportBounds.CAMELOT.getBounds())),
                new Option("Carrallanger (Graveyard of Shadows)", () -> teleport(player, MagicTeleportBounds.CARRALLANGAR.getBounds())),
                new Option("Catherby", () -> teleport(player, MagicTeleportBounds.CATHERBY.getBounds())),
                new Option("Draynor Manor", () -> teleport(player, MagicTeleportBounds.DRAYNOR_MANOR.getBounds())),
                new Option("East Ardougne", () -> teleport(player, MagicTeleportBounds.ARDOUGNE.getBounds())),
                new Option("Falador", () -> teleport(player, MagicTeleportBounds.FALADOR.getBounds())),
                new Option("Fenkenstrain's Castle", () -> teleport(player, MagicTeleportBounds.FENKENSTRAINS_CASTLE.getBounds())),
                new Option("Fishing Guild", () -> teleport(player, MagicTeleportBounds.FISHING_GUILD.getBounds())),
                new Option("Ghorrock (Frozen Waste Plateau)", () -> teleport(player, MagicTeleportBounds.GHORROCK.getBounds())),
                new Option("Grand Exchange", () -> teleport(player, MagicTeleportBounds.VARROCK_GE.getBounds())),
                new Option("Harmony Island", () -> teleport(player, MagicTeleportBounds.HARMONY_ISLAND.getBounds())),
                new Option("Ice Plateau", () -> teleport(player, MagicTeleportBounds.ICE_PLATEAU.getBounds())),
                new Option("Kharyll (Canifis)", () -> teleport(player, MagicTeleportBounds.KHARYLL.getBounds())),
                new Option("Kourend Castle", () -> teleport(player, MagicTeleportBounds.KOUREND.getBounds())),
                new Option("Lassar (Ice Mountain)", () -> teleport(player, MagicTeleportBounds.LASSAR.getBounds())),
                new Option("Lumbridge", () -> teleport(player, MagicTeleportBounds.LUMBRIDGE.getBounds())),
                new Option("Lunar Isle", () -> teleport(player, MagicTeleportBounds.MOONCLAN.getBounds())),
                new Option("Mind Altar", () -> teleport(player, MagicTeleportBounds.MIND_ALTAR.getBounds())),
                new Option("Ourania", () -> teleport(player, MagicTeleportBounds.OURANIA.getBounds())),
                new Option("Paddewwa (Edgeville Dungeon)", () -> teleport(player, MagicTeleportBounds.PADDEWWA.getBounds())),
                new Option("Port Khazard", () -> teleport(player, MagicTeleportBounds.KHAZARD.getBounds())),
                new Option("Salve Graveyard", () -> teleport(player, MagicTeleportBounds.SALVE_GRAVEYARD.getBounds())),
                new Option("Seers' Village", () -> teleport(player, MagicTeleportBounds.CAMELOT_SEERS.getBounds())),
                new Option("Senntisten (Digsite)", () -> teleport(player, MagicTeleportBounds.SENNTISTEN.getBounds())),
                new Option("Teleport to House", () -> {
                    player.putTemporaryAttribute("LAST_TELE_HOUSE", 0);
                    TeleportToHouse.teleport(player, 0);
                }),
                new Option("Teleport to House (outside)", () -> {
                    player.putTemporaryAttribute("LAST_TELE_HOUSE", 1);
                    TeleportToHouse.teleport(player, 1);
                }),
                new Option("The Forgotten Cemetary", () -> teleport(player, MagicTeleportBounds.CEMETARY.getBounds())),
                new Option("Troll Stronghold", () -> teleport(player, BasaltTeleport.canTeleportUpper.test(player) ? BasaltTeleport.TROLL_STRONGHOLD_UPPER : BasaltTeleport.TROLL_STRONGHOLD)),
                new Option("Trollheim", () -> teleport(player, MagicTeleportBounds.TROLLHEIM.getBounds())),
                new Option("Varrock", () -> teleport(player, MagicTeleportBounds.VARROCK.getBounds())),
                new Option("Watchtower", () -> teleport(player, MagicTeleportBounds.WATCHTOWER.getBounds())),
                new Option("Waterbirth Island", () -> teleport(player, MagicTeleportBounds.WATERBIRTH.getBounds())),
                new Option("Weiss", () -> teleport(player, BasaltTeleport.WEISS)),
                new Option("West Ardougne", () -> teleport(player, MagicTeleportBounds.WEST_ARDOUGNE.getBounds())),
                new Option("Yanille", () -> teleport(player, MagicTeleportBounds.WATCHTOWER_YANILLE.getBounds()))
        ));
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
