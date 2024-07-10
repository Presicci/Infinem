package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.MapArea;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/5/2024
 */
public class WineOfZamorak {

    private static void drain(Player player) {
        player.animate(832);
        player.graphics(78);
        player.hit(new Hit().fixedDamage(player.getStats().get(StatType.Hitpoints).fixedLevel / 20 + 1));
        player.getStats().get(StatType.Attack).drain(0.5);
        player.getStats().get(StatType.Strength).drain(0.5);
        player.getStats().get(StatType.Defence).drain(0.5);
        player.getStats().get(StatType.Ranged).drain(0.5);
        player.getStats().get(StatType.Magic).drain(0.5);
    }

    public static boolean takeWine(Player player) {
        if (MapArea.ASGARNIA_CHAOS_TEMPLE.inArea(player) || MapArea.ASGARNIA_CHAOS_TEMPLE_SECOND_FLOOR.inArea(player)) {
            boolean npcPresent = false;
            for (NPC npc : player.localNpcs()) {
                if (npc.getId() != 527 && npc.getId() != 528)
                    continue;
                if (npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
                    npc.face(player);
                    npc.getCombat().setTarget(player);
                    npcPresent = true;
                    if (Random.rollDie(2, 1))
                        npc.forceText("Leave Zamorak's wine alone!");
                }
            }
            if (!npcPresent) {
                drain(player);
                return true;
            }
        }
        drain(player);
        return false;
    }

    public static void telegrabWine(Player player) {
        if (MapArea.ASGARNIA_CHAOS_TEMPLE.inArea(player)) {
            for (NPC npc : player.localNpcs()) {
                if (npc.getId() != 527 && npc.getId() != 528)
                    continue;
                if (npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
                    npc.face(player);
                    npc.getCombat().setTarget(player);
                    if (Random.rollDie(2, 1))
                        npc.forceText("Leave Zamorak's wine alone!");
                }
            }
            player.sendFilteredMessage("STOP STEALING MY WINE! GAH!");
        }
        if (MapArea.DEEP_WILDERNESS_DUNGEON.inArea(player))
            player.getTaskManager().doLookupByUUID(863);    // Telegrab The Wine in the Deep Wilderness Dungeon
    }
}
