package io.ruin.model.map.object.actions.impl.locations.prifddinas;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class IorwerthDungeon {

    static {
        ObjectAction.register(36692, "pass", (player, obj) -> shortcutOne(player));
        ObjectAction.register(36693, "pass", (player, obj) -> shortcutOneBack(player));
        ObjectAction.register(36694, "pass", (player, obj) -> shortcutTwo(player));
        ObjectAction.register(36695, "pass", (player, obj) -> shortcutTwoBack(player));
    }

    public static void shortcutOne(Player player) {
        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 78, "use this shortcut"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(2594);
            player.stepAbs(player.getAbsX() - 6, player.getAbsY(), StepType.FORCE_WALK);
            event.delay(6);
            player.animate(2595);
            event.delay(2);
            player.unlock();
        });
    }

    public static void shortcutOneBack(Player player) {
        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 78, "use this shortcut"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(2594);
            player.stepAbs(player.getAbsX() + 6, player.getAbsY(), StepType.FORCE_WALK);
            event.delay(6);
            player.animate(2595);
            event.delay(2);
            player.unlock();
        });
    }

    public static void shortcutTwo(Player player) {
        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 84, "use this shortcut"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(2594);
            player.stepAbs(player.getAbsX() - 10, player.getAbsY(), StepType.FORCE_WALK);
            event.delay(10);
            player.animate(2595);
            event.delay(2);
            player.unlock();
        });
    }

    public static void shortcutTwoBack(Player player) {
        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 84, "use this shortcut"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(2594);
            player.stepAbs(player.getAbsX() + 10, player.getAbsY(), StepType.FORCE_WALK);
            event.delay(10);
            player.animate(2595);
            event.delay(2);
            player.unlock();
        });
    }

}
