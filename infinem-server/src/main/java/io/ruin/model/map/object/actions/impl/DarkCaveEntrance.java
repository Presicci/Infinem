package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.Lightables;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/1/2023
 */
@AllArgsConstructor
public enum DarkCaveEntrance {
    KALPHITE_TO_DORGESH(23596, "enter", new Position(3515, 9520, 2), new Position(2711, 5206, 0), 2796),
    LUMBRIDGE_SWAMP_CAVE_ENTRANCE(5947, "climb-down", new Position(3169, 3172), new Position(3169, 9571), 827),
    CHASM_OF_TEARS_TO_LUMBRIDGE_SWAMP_CAVE(6658, "enter", new Position(3218, 9533, 2), new Position(3226, 9542), 2796);

    final int objectId;
    final String option;
    final Position objectPos, destination;
    final int animId;

    void enter(Player player) {
        player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(animId);
            e.delay(1);
            player.getMovement().teleport(destination);
            player.unlock();
        });
    }

    static void use(Player player, GameObject object, DarkCaveEntrance entry) {
        if (Lightables.hasLightSource(player)) {
            entry.enter(player);
        } else {
            player.dialogue(
                    new MessageDialogue("The cave is far too dark to see anything without a light source. Continue?"),
                    new OptionsDialogue("Enter the cave?",
                            new Option("Enter", () -> entry.enter(player)),
                            new Option("Do not enter")
                    )
            );
        }
    }

    static void use(Player player, GameObject object, Consumer<Player> runnable) {
        if (Lightables.hasLightSource(player)) {
            runnable.accept(player);
        } else {
            player.dialogue(
                    new MessageDialogue("The cave is far too dark to see anything without a light source. Continue?"),
                    new OptionsDialogue("Enter the cave?",
                            new Option("Enter", () -> runnable.accept(player)),
                            new Option("Do not enter")
                    )
            );
        }
    }

    static {
        for (DarkCaveEntrance entry : values()) {
            ObjectAction.register(entry.objectId, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, (player, obj) -> use(player, obj, entry));
        }
    }
}
