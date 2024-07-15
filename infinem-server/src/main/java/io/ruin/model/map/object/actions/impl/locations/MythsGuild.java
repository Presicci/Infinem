package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2024
 */
public class MythsGuild {

    public static boolean canEnter(Player player) {
        StatList stats = player.getStats();
        return stats.get(StatType.Magic).currentLevel >= 75 && stats.get(StatType.Smithing).currentLevel >= 70
                && stats.get(StatType.Mining).currentLevel >= 68 && stats.get(StatType.Crafting).currentLevel >= 62
                && stats.get(StatType.Agility).currentLevel >= 60 && stats.get(StatType.Thieving).currentLevel >= 60
                && stats.get(StatType.Construction).currentLevel >= 50;
    }

    private static void guildGate(Player player, GameObject obj, boolean dungeon) {
        if (((!dungeon && player.getPosition().getY() == 2862) || (dungeon && player.getAbsX() == 1969))
                && (!canEnter(player))) {
            player.dialogue(new MessageDialogue("You need 75 magic, 70 smithing, 68 mining, 62 crafting, 60 agility, 60 thieving, and 50 construction to enter the Myths' guild."));
            return;
        }
        player.lock();
        player.startEvent(e -> {
            e.delay(1);
            player.stepAbs(
                    dungeon ? player.getAbsX() + (player.getAbsX() <= obj.getPosition().getX() ? 2 : -2) : player.getAbsX(),
                    !dungeon ? player.getAbsY() + (player.getAbsY() <= obj.getPosition().getY() ? 2 : -2) : player.getAbsY(),
                    StepType.FORCE_WALK);
            e.delay(2);
            player.getTaskManager().doLookupByUUID(601);    // Enter the Myths' Guild
            player.unlock();
        });
    }

    static {
        ObjectAction.register(31616, 2456, 2861, 0, "pass", (player, obj) -> guildGate(player, obj, false));
        ObjectAction.register(31616, 2457, 2861, 0, "pass", (player, obj) -> guildGate(player, obj, false));
        ObjectAction.register(31616, 2458, 2861, 0, "pass", (player, obj) -> guildGate(player, obj, false));
        ObjectAction.register(31617, 1968, 8986, 1, "pass", (player, obj) -> guildGate(player, obj, true));
        ObjectAction.register(31617, 1968, 8987, 1, "pass", (player, obj) -> guildGate(player, obj, true));
    }
}
