package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/23/2023
 */
public enum InstantMovementObject {
    SHAYZIEN_CRYPT_STAIRS_NW_UP(32393, new Position(1494, 9978, 1), new Position(1498, 9978, 2)),
    SHAYZIEN_CRYPT_STAIRS_NW_DOWN(32394, new Position(1496, 9978, 2), new Position(1493, 9978, 1)),

    SHAYZIEN_CRYPT_STAIRS_NE_UP(32393, new Position(1510, 9978, 1), new Position(1509, 9978, 2)),
    SHAYZIEN_CRYPT_STAIRS_NE_DOWN(32394, new Position(1510, 9978, 2), new Position(1514, 9978, 1)),

    SHAYZIEN_CRYPT_STAIRS_S_UP(32393, new Position(1503, 9925, 2), new Position(1503, 9929, 3)),
    SHAYZIEN_CRYPT_STAIRS_S_DOWN(32394, new Position(1503, 9927, 3), new Position(1503, 9924, 2)),

    SHAYZIEN_PRISON_ENTRANCE(41922, new Position(1464, 3568), new Position(1444, 9960)),
    SHAYZIEN_PRISON_EXIT(41923, new Position(1445, 9960), new Position(1466, 3568)),

    KOUREND_CASTLE_STAIRS_ENTRY_BOTTOM_1(11807, new Position(1615, 3665, 0), new Position(1614, 3665, 1)),
    KOUREND_CASTLE_STAIRS_ENTRY_BOTTOM_2(11807, new Position(1615, 3680, 0), new Position(1614, 3681, 1)),
    KOUREND_CASTLE_STAIRS_DOWN_1(11799, new Position(1615, 3665, 1), new Position(1618, 3665, 0)),
    KOUREND_CASTLE_STAIRS_DOWN_2(11799, new Position(1615, 3680, 1), new Position(1618, 3680, 0)),
    KOUREND_CASTLE_STAIRS_NORTH(12536, new Position(1616, 3687, 1), new Position(1616, 3686, 2)),
    KOUREND_CASTLE_STAIRS_NORTH_DOWN(12538, new Position(1616, 3687, 2), new Position(1615, 3687, 1)),
    KOUREND_CASTLE_STAIRS_SOUTH(12536, new Position(1616, 3658, 1), new Position(1617, 3660, 2)),
    KOUREND_CASTLE_STAIRS_SOUTH_DOWN(12538, new Position(1617, 3659, 2), new Position(1618, 3659, 1)),

    WITCHHAVEN_DUNGEON_ENTRANCE(18270, "climb-down", new Position(2696, 3283, 0), new Position(2696, 9683, 0)),
    WITCHHAVEN_DUNGEON_EXIT(18354, "climb-up", new Position(2696, 9682, 0), new Position(2697, 3283, 0)),

    WITCHHAVEN_DUNGEON_WALL(19124, new Position(2701, 9688, 0), new Position(2323, 5104, 0)),
    WITCHHAVEN_DUNGEON_PASSAGE(18412, new Position(2322, 5104, 0), new Position(2700, 9688, 0)),

    TROLL_STRONGHOLD_ENTRACE(3771, "enter", new Position(2839, 3689, 0), new Position(2837, 10090, 2)),
    TROLL_STRONGHOLD_EXIT(3772, "use", new Position(2838, 10091, 2), new Position(2840, 3690, 0)),
    TROLL_STRONGHOLD_EXIT2(3773, "leave", new Position(2838, 10089, 2), new Position(2840, 3690, 0)),
    TROLL_STRONGHOLD_EXIT3(3774, "leave", new Position(2838, 10090, 2), new Position(2840, 3690, 0)),
    TROLL_STRONGHOLD_ENTRANCE2(3762, "open", new Position(2827, 3647, 0), new Position(2824, 10050, 0)),
    TROLL_STRONGHOLD_EXIT4(3761, "open", new Position(2823, 10048, 0), new Position(2827, 3646, 0)),

    TROLL_STRONGHOLD_STAIRS_DOWN1(3789, "climb-down", new Position(2843, 10108, 2), new Position(2841, 10108, 1)),
    TROLL_STRONGHOLD_STAIRS_UP1(3788, "climb-up", new Position(2842, 10108, 1), new Position(2845, 10108, 2)),
    TROLL_STRONGHOLD_STAIRS_DOWN2(3789, "climb-down", new Position(2843, 10051, 2), new Position(2841, 10052, 1)),
    TROLL_STRONGHOLD_STAIRS_UP2(3788, "climb-up", new Position(2842, 10051, 1), new Position(2845, 10052, 2)),
    TROLL_STRONGHOLD_STAIRS_DOWN3(3789, "climb-down", new Position(2852, 10107, 1), new Position(2852, 10105, 0)),
    TROLL_STRONGHOLD_STAIRS_UP3(3788, "climb-up", new Position(2852, 10106, 0), new Position(2852, 10109, 1)),

    DEATH_PLATEAU_PATHWAY_DUNGEON_SOUTH_EXIT(3758, "exit", new Position(2906, 10017, 0), new Position(2904, 3643, 0)),
    DEATH_PLATEAU_PATHWAY_DUNGEON_SOUTH_ENTRANCE(3757, "enter", new Position(2903, 3644, 0), new Position(2907, 10019, 0)),
    DEATH_PLATEAU_PATHWAY_DUNGEON_NORTH_EXIT(3758, "exit", new Position(2906, 10036, 0), new Position(2908, 3654, 0)),
    DEATH_PLATEAU_PATHWAY_DUNGEON_NORTH_ENTRANCE(3757, "enter", new Position(2907, 3652, 0), new Position(2907, 10035, 0));

    private final int id;
    private final String option;
    private final Position objectPos, destinationPos;
    private int emote = -1, optionInt = -1;

    InstantMovementObject(int id, String option, Position objectPos, Position destinationPos) {
        this.id = id;
        this.option = option;
        this.objectPos = objectPos;
        this.destinationPos = destinationPos;
    }

    InstantMovementObject(int id, Position objectPos, Position destinationPos) {
        this(id, 1, objectPos, destinationPos);
    }

    InstantMovementObject(int id, Position objectPos, Position destinationPos, int emote) {
        this(id, 1, objectPos, destinationPos, emote);
    }

    InstantMovementObject(int id, int optionInt, Position objectPos, Position destinationPos) {
        this(id, optionInt, objectPos, destinationPos, -1);
    }

    InstantMovementObject(int id, int optionInt, Position objectPos, Position destinationPos, int emote) {
        this.id = id;
        this.option = "";
        this.optionInt = optionInt;
        this.objectPos = objectPos;
        this.emote = emote;
        this.destinationPos = destinationPos;
    }

    private static void movement(Player player, InstantMovementObject entry) {
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
        for (InstantMovementObject entry : values()) {
            if (entry.optionInt > -1) {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.optionInt, ((player, obj) -> movement(player, entry)));
            } else {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> movement(player, entry)));
            }
        }
    }
}
