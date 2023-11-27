package io.ruin.model.skills.agility.shortcut;

import io.ruin.cache.AnimDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

import java.util.stream.IntStream;

/**
 * @author ReverendDread on 3/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Grappling {

    protected static final int[] CROSSBOWS = { Items.PHOENIX_CROSSBOW, Items.CROSSBOW, Items.BRONZE_CROSSBOW,
            Items.BLURITE_CROSSBOW, Items.IRON_CROSSBOW, Items.STEEL_CROSSBOW, Items.MITH_CROSSBOW, Items.ADAMANT_CROSSBOW,
            Items.RUNE_CROSSBOW, Items.PHOENIX_CROSSBOW_2, Items.ARMADYL_CROSSBOW, Items.DRAGON_HUNTER_CROSSBOW, Items.DRAGON_CROSSBOW };
    private static final int GRAPPLING_HOOK = 9419;

    public static void grapple(Player player, GameObject object, int agilityLevel, int rangedLevel, int strengthLevel, int emoteId, int gfxId, int delay, Position position, Position destination) {
        if (!player.getStats().check(StatType.Agility, agilityLevel, "grapple") ||
            !player.getStats().check(StatType.Strength, strengthLevel, "grapple") ||
            !player.getStats().check(StatType.Ranged, rangedLevel, "grapple")) {
            return;
        }
        if (!player.getEquipment().hasId(GRAPPLING_HOOK)) {//grappling hook
            player.sendMessage("You need a mithril grapple tipped bolt with a rope to do that.");
            return;
        }
        if (IntStream.of(CROSSBOWS).noneMatch(player.getEquipment()::hasId)) {
            player.sendMessage("You need a crossbow equipped to do that.");
            return;
        }
        player.startEvent(e -> { //4455, 760
            player.lock();
            e.path(player, position);
            player.animate(emoteId);
            player.graphics(gfxId, 100, 0);
            e.delay(delay);
            e.delay(3);
            player.getMovement().teleport(destination);
            player.unlock();
        });
    }

}
