package io.ruin.model.map.object.actions.impl.locations.prifddinas;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class IorwerthDungeon {

    static {
        ObjectAction.register(36690, "enter", (player, obj) -> enterIorwerthDungeon(player));
        ObjectAction.register(36691, "exit", (player, obj) -> exitIorwerthDungeon(player));
        ObjectAction.register(36692, "pass", (player, obj) -> shortcutOne(player));
        ObjectAction.register(36693, "pass", (player, obj) -> shortcutOneBack(player));
        ObjectAction.register(36694, "pass", (player, obj) -> shortcutTwo(player));
        ObjectAction.register(36695, "pass", (player, obj) -> shortcutTwoBack(player));
    }

    public static void enterIorwerthDungeon(Player player) {
        player.lock();
        player.getPacketSender().fadeOut();
        player.dialogue(
                new MessageDialogue("Welcome to the Iorwerth Dungeon.")
        );
        player.getMovement().teleport(3225, 12445, 0);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

    public static void exitIorwerthDungeon(Player player) {
        player.lock();
        player.getPacketSender().fadeOut();
        player.dialogue(
                new MessageDialogue("Welcome to the Iorwerth District.")
        );
        player.getMovement().teleport(3225, 6046, 0);
        player.getPacketSender().fadeIn();
        player.unlock();
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
