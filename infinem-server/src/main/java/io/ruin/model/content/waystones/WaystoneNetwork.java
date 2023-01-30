package io.ruin.model.content.waystones;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.locations.prifddinas.CityEntrance;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2023
 */
public class WaystoneNetwork {

    private static final int PORTABLE_WAYSTONE = 26549;

    private static void channelWaystone(Player player, Waystone waystone) {
        if (waystone == Waystone.PRIFDDINAS && !CityEntrance.prifSkillCheck(player)) {
            return;
        }
        if (isWaystoneUnlocked(player, waystone)) {
            player.startEvent(e -> ModernTeleport.teleport(player, waystone.getTeleportPostion().getX(), waystone.getTeleportPostion().getY(), waystone.getTeleportPostion().getZ()));
        } else {
            player.dialogue(new MessageDialogue("You do not have that Waystone unlocked."));
        }
    }

    private static void unlockWaystone(Player player, Waystone waystone) {
        player.animate(832);
        player.getUnlockedWaystones().add(waystone);
    }

    private static boolean isWaystoneUnlocked(Player player, Waystone waystone) {
        return player.getUnlockedWaystones().contains(waystone);
    }

    private static void selectLocation(Player player) {
        OptionScroll.open(player, "Waystone Locations", true, Arrays.stream(Waystone.values())
                .map(stone -> new Option((player.getUnlockedWaystones().contains(stone) ? "" : "<str>") + stone.getName(), () -> channelWaystone(player, stone)))
                .toArray(Option[]::new));
    }

    private static void selectLocation(Player player, GameObject object, Waystone waystone) {
        if (!isWaystoneUnlocked(player, waystone)) {
            unlockWaystone(player, waystone);
            player.sendMessage("You unlock the " + waystone.getName() + " waystone.");
            return;
        }
        OptionScroll.open(player, "Waystone Locations", true, Arrays.stream(Waystone.values())
                .filter(e -> e != waystone)
                .map(stone -> new Option((player.getUnlockedWaystones().contains(stone) ? "" : "<str>") + stone.getName(), () -> channelWaystone(player, stone)))
                .toArray(Option[]::new));
    }

    static {
        ItemAction.registerInventory(PORTABLE_WAYSTONE, "teleport", ((player, item) -> selectLocation(player)));
        ItemAction.registerEquipment(PORTABLE_WAYSTONE, "teleport", ((player, item) -> selectLocation(player)));
        for (Waystone waystone : Waystone.values()) {
            ObjectAction.register(waystone.getObjectId(), waystone.getObjectPosition(), 1, (player, obj) -> selectLocation(player, obj, waystone));
        }
    }
}
