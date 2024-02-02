package io.ruin.model.content.transportation.relics;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.locations.prifddinas.PrifCityEntrance;
import io.ruin.model.map.object.actions.impl.transportation.FairyRing;
import io.ruin.model.map.object.actions.impl.transportation.SpiritTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
public class FairysFlight {

    private static final List<Option> OPTIONS = Arrays.asList(
            new Option("Al Kharid", (player) -> teleport(player, 3318, 3203, 0)),
            new Option("Ardougne Monastery", (player) -> teleport(player, 2615, 3226, 0)),
            new Option("Brimhaven", (player) -> teleport(player, 2767, 3212, 0)),
            new Option("Catherby", (player) -> teleport(player, 2816, 3464, 0)),
            new Option("Champions' Guild", (player) -> teleport(player, 3184, 3357, 0)),
            new Option("Canifis", (player) -> teleport(player, 3454, 3474, 0)),
            new Option("Draynor Manor", (player) -> teleport(player, 3087, 3353, 0)),
            new Option("Entrana", (player) -> teleport(player, 2814, 3336, 0)),
            new Option("Etceteria", (player) -> teleport(player, 2591, 3860, 0)),
            new Option("Falador Farm", (player) -> teleport(player, 3052, 3306, 0)),
            new Option("Falador Park", (player) -> teleport(player, 3007, 3375, 0)),
            new Option("Farming Guild", (player) -> teleport(player, 1249, 3727, 0)),
            new Option("Fossil Island", (player) -> teleport(player, 3711, 3837, 0)),
            new Option("Harmony Island", (player) -> teleport(player, 3791, 2834, 0)),
            new Option("Hosidius Allotment", (player) -> teleport(player, 1741, 3554, 0)),
            new Option("Hosidius Saltpetre", (player) -> teleport(player, 1696, 3542, 0)),
            new Option("Hosidius Vinery", (player) -> teleport(player, 1806, 3556, 0)),
            new Option("Lletya", (player) -> teleport(player, 2346, 3164, 0)),
            new Option("Lumbridge", (player) -> teleport(player, 3190, 3232, 0)),
            new Option("North of Ardougne", (player) -> teleport(player, 2673, 3376, 0)),
            new Option("North of McGrubor's Wood", (player) -> teleport(player, 2662, 3526, 0)),
            new Option("North of Seth Groats' Farm", (player) -> teleport(player, 3233, 3314, 0)),
            new Option("Port Phasmatys", (player) -> teleport(player, 3600, 3523, 0)),
            new Option("Port Sarim", (player) -> teleport(player, 3059, 3261, 0)),
            new Option("Prifddinas", PrifCityEntrance::prifSkillCheckNoNPC),
            new Option("Rimmington", (player) -> teleport(player, 2943, 3223, 0)),
            new Option("Tai Bwo Wannai", (player) -> teleport(player, 2799, 3100, 0)),
            new Option("Taverly", (player) -> teleport(player, 2935, 3441, 0)),
            new Option("Tree Gnome Stronghold", (player) -> teleport(player, 2438, 3417, 0)),
            new Option("Tree Gnome Village", (player) -> teleport(player, 2488, 3182, 0)),
            new Option("Underwater", (player) -> teleport(player, 3734, 10271, 1)),
            new Option("Varrock", (player) -> teleport(player, 3230, 3456, 0)),
            new Option("Weiss", (player) -> teleport(player, 2847, 3934, 0)),
            new Option("White Wolf Mountain", (player) -> teleport(player, 2857, 3433, 0)),
            new Option("Yanille", (player) -> teleport(player, 2572, 3103, 0))
    );

    private static void teleports(Player player) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Fairy rings", () -> fairyRings(player)),
                        new Option("Spirit trees", () -> spiritTrees(player)),
                        new Option("Tool leprechauns", () -> toolLeprechauns(player))
                )
        );
    }

    private static void fairyRings(Player player) {
        player.putTemporaryAttribute("FAIRYFLIGHT_RING", 1);
        FairyRing.openCombinationPanel(player, null);
    }

    private static void spiritTrees(Player player) {
        SpiritTree.openFairysFlight(player);
    }

    private static void toolLeprechauns(Player player) {
        boolean canEnterPrif = PrifCityEntrance.prifSkillCheckNoMessage(player);
        List<Option> options = new ArrayList<>(OPTIONS);
        options.set(24, new Option((canEnterPrif ? "" : "<str>") + "Prifddinas", () -> {
            if (canEnterPrif)
                teleport(player, 3295, 6103, 0);
            else
                PrifCityEntrance.prifSkillCheckNoNPC(player);
        }));
        OptionScroll.open(player, "Tool Leprechauns", true, options);
    }

    private static void lastDestination(Player player) {
        Position position = player.getTemporaryAttribute("FAIRY_LAST_TELE");
        if (position == null) return;
        player.getMovement().startTeleport(e -> {
            player.animate(3265, 30);
            player.graphics(569);
            e.delay(3);
            player.getMovement().teleport(position.getX(), position.getY(), position.getZ());
        });
    }

    private static void teleport(Player player, int x, int y, int z) {
        teleport(player, new Position(x, y, z));
    }

    private static void teleport(Player player, Position position) {
        player.putTemporaryAttribute("FAIRY_LAST_TELE", position);
        player.getMovement().startTeleport(e -> {
            player.graphics(569);
            e.delay(3);
            player.getMovement().teleport(position.getX(), position.getY(), position.getZ());
        });
    }

    static {
        ItemAction.registerInventory(25102, "teleport", (player, item) -> teleports(player));
        ItemAction.registerInventory(25102, "last-destination", (player, item) -> lastDestination(player));
        ItemAction.registerEquipment(25102, "fairy-ring", (player, item) -> fairyRings(player));
        ItemAction.registerEquipment(25102, "spirit-tree", (player, item) -> spiritTrees(player));
        ItemAction.registerEquipment(25102, "tool-leprechauns", (player, item) -> toolLeprechauns(player));
        ItemAction.registerEquipment(25102, "last-destination", (player, item) -> lastDestination(player));
    }
}
