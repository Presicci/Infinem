package io.ruin.model.activities.combat.gauntlet;

import io.ruin.model.map.Position;
import io.ruin.utility.Misc;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Greco
 * @since 04/08/2021
 */
@Getter
public enum GauntletRooms {

    STARTER_ROOM(1904, 5664, GauntletRoomType.MIDDLE, RoomDirection.ALL),
    BOSS_ROOM(1904, 5680, GauntletRoomType.MIDDLE, RoomDirection.ALL),
    FOUR_DOOR_ROOM_1(1856, 5632, GauntletRoomType.MIDDLE, RoomDirection.ALL),
    FOUR_DOOR_ROOM_2(1856, 5648, GauntletRoomType.MIDDLE, RoomDirection.ALL),
    FOUR_DOOR_ROOM_3(1856, 5664, GauntletRoomType.MIDDLE, RoomDirection.ALL),
    FOUR_DOOR_ROOM_4(1856, 5680, GauntletRoomType.MIDDLE, RoomDirection.ALL),

    THREE_DOOR_ROOM_1(1872, 5632, GauntletRoomType.SIDE, RoomDirection.EAST, RoomDirection.SOUTH, RoomDirection.WEST),
    THREE_DOOR_ROOM_2(1872, 5648, GauntletRoomType.SIDE, RoomDirection.EAST, RoomDirection.SOUTH, RoomDirection.WEST),
    THREE_DOOR_ROOM_3(1872, 5664, GauntletRoomType.SIDE, RoomDirection.EAST, RoomDirection.SOUTH, RoomDirection.WEST),
    THREE_DOOR_ROOM_4(1872, 5680, GauntletRoomType.SIDE, RoomDirection.EAST, RoomDirection.SOUTH, RoomDirection.WEST),

    TWO_DOOR_ROOM_1(1888, 5632, GauntletRoomType.CORNER, RoomDirection.WEST, RoomDirection.SOUTH),
    TWO_DOOR_ROOM_2(1888, 5648, GauntletRoomType.CORNER, RoomDirection.WEST, RoomDirection.SOUTH),
    TWO_DOOR_ROOM_3(1888, 5664, GauntletRoomType.CORNER, RoomDirection.WEST, RoomDirection.SOUTH),
    TWO_DOOR_ROOM_4(1888, 5680, GauntletRoomType.CORNER, RoomDirection.WEST, RoomDirection.SOUTH);

    private GauntletRooms(int x, int y, GauntletRoomType type, int... openDirections) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.openDirections = openDirections;
    }

    private int x, y;
    private GauntletRoomType type;
    private int[] openDirections;

    /**
     * Finds a room of a specifc {@link GauntletRoomType}
     * @param type The room type.
     * @return
     */
    public static GauntletRooms findRoomOfType(GauntletRoomType type) {
        List<GauntletRooms> lookup = Stream.of(values()).filter(room -> room.getType() == type && room != GauntletRooms.STARTER_ROOM && room != GauntletRooms.BOSS_ROOM).collect(Collectors.toList());
        return Misc.randomTypeOfList(lookup);
    }

    /**
     * Checks if the room has the requested direction to be opened.
     * @param requestedDir The requested direction to be opened.
     * @param rotation The room rotation.
     * @return
     */
    public boolean hasDirection(int requestedDir, int rotation) {
        return Arrays.stream(openDirections).anyMatch(dir -> ((dir + rotation) & 3) == requestedDir);
    }

    public Position getLocation() {
        return new Position(x, y);
    }
}
