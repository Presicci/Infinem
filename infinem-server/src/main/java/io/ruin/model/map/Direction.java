package io.ruin.model.map;

public enum Direction {

    /**
     * DO NOT REORDER
     */

    NORTH_WEST(-1, 1, 768),     //0
    NORTH(0, 1, 1024),          //1
    NORTH_EAST(1, 1, 1280),     //2
    WEST(-1, 0, 512),           //3
    EAST(1, 0, 1536),           //4
    SOUTH_WEST(-1, -1, 256),    //5
    SOUTH(0, -1, 0),            //6
    SOUTH_EAST(1, -1, 1792),    //7
    NONE(-1, 0, 0);

    public final int deltaX, deltaY;

    public final int clientValue;

    Direction(int deltaX, int deltaY, int clientValue) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.clientValue = clientValue;
    }

    public static Direction get(String cardinal) {
        switch(cardinal.toUpperCase()) {
            case "N":   return Direction.NORTH;
            case "NW":  return Direction.NORTH_WEST;
            case "NE":  return Direction.NORTH_EAST;
            case "W":   return Direction.WEST;
            case "E":   return Direction.EAST;
            case "SW":  return Direction.SOUTH_WEST;
            case "SE":  return Direction.SOUTH_EAST;
            default:    return Direction.SOUTH;
        }
    }

    public static Direction getFromClientValue(int cv) {
        for (Direction dir : values()) {
            if (dir.clientValue == cv)
                return dir;
        }
        return NONE;
    }

    public static Direction getFromObjectDirection(int direction) {
        switch (direction) {
            case 0:
                return SOUTH;
            case 1:
                return WEST;
            case 2:
                return NORTH;
            case 3:
                return EAST;
            default:
                return null;
        }
    }

    public static Direction fromDoorDirection(int direction) {
        switch (direction) {
            case 0:
                return WEST;
            case 1:
                return NORTH;
            case 2:
                return EAST;
            case 3:
                return SOUTH;
            default:
                return null;
        }
    }

    public static Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case NORTH_EAST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return NORTH_EAST;
            case NORTH_WEST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return NORTH_WEST;
            default:
                return NORTH;
        }
    }

    public static Direction getDirection(Position src, Position dest) {
        int deltaX = dest.getX() - src.getX();
        int deltaY = dest.getY() - src.getY();
        return getDirection(deltaX, deltaY);
    }

    public static Direction getDirection(int deltaX, int deltaY) {
        if (deltaX != 0)//normalize
            deltaX /= Math.abs(deltaX);
        if (deltaY != 0)
            deltaY /= Math.abs(deltaY);
        for (Direction d: Direction.values()) {
            if (d.deltaX == deltaX && d.deltaY == deltaY)
                return d;
        }
        return null;
    }

    public Direction getCounterClockwiseDirection(int cycles) {
        int targetDirection = (clientValue - (cycles << 8)) & 0x7FF;
        return getFromClientValue(targetDirection);
    }

    public static boolean similarDirection(Direction dir1, Direction dir2, int marginOfError) {
        if (dir1.clientValue == dir2.clientValue)
            return true;
        int min = dir1.clientValue - (marginOfError * 256);
        int max = (dir1.clientValue + (marginOfError * 256)) % 2048;
        if (min < 0)
            min += 2048;
        return min < max ? (min <= dir2.clientValue && dir2.clientValue <= max) : (dir2.clientValue >= min || dir2.clientValue <= max);
    }
}