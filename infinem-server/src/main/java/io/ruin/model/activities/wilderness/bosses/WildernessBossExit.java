package io.ruin.model.activities.wilderness.bosses;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Misc;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/22/2024
 */
public class WildernessBossExit {

    public static final List<Position> EXIT_LOCATIONS = Arrays.asList(
            new Position(3360, 10295, 0),
            new Position(3341, 10255, 0),
            new Position(3376, 10255, 0)
    );

    @Getter
    @AllArgsConstructor
    public enum MinorCave {
        SPINDEL_DUNGEON_EXIT(new Position(1630, 11527, 2), new Position(3182, 3746, 0)),
        ARTIO_DUNGEON_EXIT(new Position(1758, 11531, 0), new Position(3115, 3675, 0)),
        CALVARION_DUNGEON_EXIT(new Position(1886, 11534, 1), new Position(3180, 3682, 0));

        private final Position object;
        private final Position exitLocation;

        public static final MinorCave[] VALUES = values();
        public static final Int2ObjectMap<MinorCave> entries = new Int2ObjectOpenHashMap<>(VALUES.length);

        static {
            for (final MinorCave entry : VALUES) {
                entries.put(entry.getObject().getRegion().getId(), entry);
            }
        }
    }

    private static final Map<GameObject, Long> lastLeaveMap = new HashMap<>();
    private static final Map<GameObject, Position> lastLocationMap = new HashMap<>();

    private static final int[] OBJECTS = {47000, 46925, 47122};

    static {
        for (int object : OBJECTS) {
            ObjectAction.register(object, 1, WildernessBossExit::leaveCave);
        }
    }

    private static void leaveCave(Player player, GameObject object) {
        if (MinorCave.entries.containsKey(object.getPosition().getRegion().getId())) {
            MinorCave caves = MinorCave.entries.get(object.getPosition().getRegion().getId());
            player.startEvent(e -> {
                player.lock();
                player.face(object);
                e.delay(2);
                player.getMovement().teleport(caves.getExitLocation());
                player.unlock();
            });
            return;
        }
        if (lastLeaveMap.get(object) != null && lastLeaveMap.get(object) != 0) {
            if (System.currentTimeMillis() - lastLeaveMap.get(object) > 10000) {
                lastLocationMap.put(object, EXIT_LOCATIONS.get(Misc.random(2)));
            }
        } else {
            lastLocationMap.put(object, EXIT_LOCATIONS.get(Misc.random(2)));
        }
        lastLeaveMap.put(object, System.currentTimeMillis());
        player.getMovement().teleport(lastLocationMap.get(object));
    }
}