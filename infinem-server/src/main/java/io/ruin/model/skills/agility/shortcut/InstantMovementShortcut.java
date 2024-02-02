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
    MYSTERIOUS_PIPE_MAIN(34655, new Position(1316, 10214), new Position(1346, 10232, 0), 827, 88),
    MYSTERIOUS_PIPE_HYDRA(34655, new Position(1346, 10231), new Position(1316, 10213, 0), 827, 88),
    ASGARNIAN_ICE_DUNGEON_ENTER(42506, new Position(3025, 9570), new Position(3035, 9557, 0), 2796, 72),
    ASGARNIAN_ICE_DUNGEON_EXIT(42507, new Position(3034, 9558), new Position(3026, 9572, 0), 2796, 72),
    SALARIN_THE_TWISTED(23563, new Position(2616, 9572), new Position(2614, 9505, 0), 828, 67),
    WATCHTOWER(20056, new Position(2548, 3119), new Position(2548, 3118, 1), 828, 18);

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
