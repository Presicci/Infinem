package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/23/2023
 */
public enum InstantMovementObject {
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

    InstantMovementObject(int id, String option, Position objectPos, Position destinationPos) {
        this.id = id;
        this.option = option;
        this.objectPos = objectPos;
        this.destinationPos = destinationPos;
    }

    static {
        for (InstantMovementObject entry : values()) {
            ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> player.getMovement().teleport(entry.destinationPos)));
        }
    }
}
