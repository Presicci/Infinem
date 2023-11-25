package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/24/2023
 */
public enum InstantMovementShortcut {
    SALARIN_THE_TWISTED(23563, new Position(2616, 9572), new Position(2614, 9505, 0), 828, 67);

    private final int id;
    private final String option;
    private final Position objectPos, destinationPos;
    private int emote = -1, optionInt = -1, levelRequirement = 0;

    InstantMovementShortcut(int id, Position objectPos, Position destinationPos, int emote, int levelRequirement) {
        this(id, 1, objectPos, destinationPos, emote, levelRequirement);
    }

    InstantMovementShortcut(int id, int optionInt, Position objectPos, Position destinationPos, int emote, int levelRequirement) {
        this.id = id;
        this.option = "";
        this.optionInt = optionInt;
        this.objectPos = objectPos;
        this.emote = emote;
        this.destinationPos = destinationPos;
        this.levelRequirement = levelRequirement;
    }

    private static void movement(Player player, InstantMovementShortcut entry) {
        if (!player.getStats().check(StatType.Agility, entry.levelRequirement, "attempt this")) return;
        if (entry.emote > -1) {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(entry.emote);
                e.delay(1);
                player.getMovement().teleport(entry.destinationPos);
                player.unlock();
            });
        } else {
            player.getMovement().teleport(entry.destinationPos);
        }
    }

    static {
        for (InstantMovementShortcut entry : values()) {
            if (entry.optionInt > -1) {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.optionInt, ((player, obj) -> movement(player, entry)));
            } else {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> movement(player, entry)));
            }
        }
    }
}
