package io.ruin.model.map.object.actions.impl.transportation;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.locations.prifddinas.PrifCityEntrance;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.farming.patch.impl.SpiritTreePatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiritTree {

    static {
        for (int id : Arrays.asList(1293, 1294, 1295, 37329)) {
            ObjectAction.register(id, 1, (player, obj) -> open(player));
            ObjectAction.register(id, 2, (player, obj) -> open(player));
        }
    }

    private static final List<PatchData> SPIRIT_TREE_PATCHES = Arrays.asList(
            PatchData.PORT_SARIM_SPIRIT_TREE,
            PatchData.BRIMHAVEN_SPIRIT_TREE,
            PatchData.ETCETERIA_SPIRIT_TREE,
            PatchData.ZEAH_SPIRIT_TREE,
            PatchData.FARMING_GUILD_SPIRIT_TREE
    );
    private static final String[] PATCH_NAMES = {
            "Port Sarim",
            "Brimhaven",
            "Etceteria",
            "Zeah",
            "Farming Guild"
    };

    private static List<Option> getOptions(Player player) {
        List<Option> options = new ArrayList<>();
        if (player.getTaskManager().hasCompletedTask(564)) {  // Make it through the Tree Gnome Village Maze
            options.add(new Option("Tree Gnome Village", () -> teleport(player, 2542, 3170, 0)));
        } else {
            options.add(new Option("<str>Tree Gnome Village", () -> player.dialogue(new MessageDialogue("You need to traverse the Tree Gnome Village Maze before traveling to that spirit tree."))));
        }
        //options.add(new Option("Tree Gnome Village", () -> teleport(player, 2542, 3170, 0)));
        options.add(new Option("Gnome Stronghold", () -> teleport(player, 2462, 3444, 0)));
        options.add(new Option("Battlefield of Khazard", () -> teleport(player, 2555, 3259, 0)));
        options.add(new Option("Grand Exchange", () -> teleport(player, 3184, 3508, 0)));
        options.add(new Option("Feldip Hills", () -> teleport(player, 2488, 2851, 0)));
        if (PrifCityEntrance.prifSkillCheckNoMessage(player)) {
            options.add(new Option("Prifddinas", () -> teleport(player, 3274, 6123, 0)));
        }
        int i = 0;
        for (PatchData farmTree : SPIRIT_TREE_PATCHES) {
            SpiritTreePatch patch = (SpiritTreePatch) player.getFarming().getPatch(farmTree.getObjectId());
            String name = PATCH_NAMES[i++];
            Runnable action;
            if (patch.canTeleport()) {
                action = () -> {
                    PatchData fromPatch = player.getTemporaryAttributeOrDefault("SPIRIT_TREE_PATCH", null);
                    if (fromPatch != null && fromPatch != farmTree) player.getTaskManager().doLookupByUUID(295);    // Travel Between Your Spirit Trees
                    teleport(player, patch.getTeleportPosition().getX(), patch.getTeleportPosition().getY(), patch.getTeleportPosition().getZ());
                };
            } else {
                name = "<str>" + name;
                action = () -> player.dialogue(new MessageDialogue("You do not have a fully grown spirit tree at that location."));
            }
            options.add(new Option(name, action));

        }
        options.add(getHouseOption(player));
        return options;
    }

    private static Option getHouseOption(Player player) {
        String houseText = "Played-owned House";
        Runnable houseAction;
        if (player.house == null) {
            houseText = "<str>" + houseText;
            houseAction = () -> player.sendMessage("You don't have a house to teleport to.");
        } else if (player.house.getSpiritTreePosition() == null) {
            houseText = "<str>" + houseText;
            houseAction = () -> player.sendMessage("Your house doesn't have a spirit tree planted.");
        } else {
            houseAction = () -> houseTeleport(player);
        }
        return new Option(houseText, houseAction);
    }

    public static void openFairysFlight(Player player) {
        player.putTemporaryAttribute("FAIRYFLIGHT_TREE", 1);
        player.removeTemporaryAttribute("SPIRIT_TREE_PATCH");
        OptionScroll.open(player, "Spirit Tree Locations", getOptions(player));
    }

    public static void open(Player player) {
        player.removeTemporaryAttribute("FAIRYFLIGHT_TREE");
        player.removeTemporaryAttribute("SPIRIT_TREE_PATCH");
        OptionScroll.open(player, "Spirit Tree Locations", getOptions(player));
    }

    public static void openFromPatch(Player player, PatchData patchData) {
        player.removeTemporaryAttribute("FAIRYFLIGHT_TREE");
        player.putTemporaryAttribute("SPIRIT_TREE_PATCH", patchData);
        OptionScroll.open(player, "Spirit Tree Locations", getOptions(player));
    }

    private static void teleport(Player player, int x, int y, int z) {
        if (player.hasTemporaryAttribute("FAIRYFLIGHT_TREE")) {
            player.getMovement().startTeleport(e -> {
                player.animate(3265, 30);
                player.graphics(569);
                e.delay(3);
                player.getMovement().teleport(x, y, z);
                player.putTemporaryAttribute("FAIRY_LAST_TELE", new Position(x, y, z));
                player.removeTemporaryAttribute("FAIRYFLIGHT_TREE");
            });
        } else {
            player.getMovement().startTeleport(event -> {
                player.animate(828);
                player.dialogue(new ItemDialogue().one(6063, "You place your hands on the dry tough bark of the<br>" +
                        "spirit tree, and feel a surge of energy run through<br>" +
                        "your veins.").hideContinue());
                event.delay(2);
                player.closeDialogue();
                player.getMovement().teleport(x,y,z);
            });
        }
    }

    private static void houseTeleport(Player player) {
        player.lock();
        player.startEvent(event -> {
            player.animate(828);
            player.dialogue(new ItemDialogue().one(6063, "You place your hands on the dry tough bark of the<br>" +
                    "spirit tree, and feel a surge of energy run through<br>" +
                    "your veins.").hideContinue());
            event.delay(1);
            player.house.buildAndEnter(player, player.house.getSpiritTreePosition(), false);
        });
    }

}
