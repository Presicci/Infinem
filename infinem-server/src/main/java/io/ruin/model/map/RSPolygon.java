package io.ruin.model.map;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;

import java.awt.*;
import java.util.BitSet;

public final class RSPolygon {

    public RSPolygon(final int swregionID, final int neRegionId) {
        this(calculateSWCornerX(swregionID), calculateSWCornerY(swregionID),
                calculateSWCornerX(neRegionId) + 64, calculateSWCornerY(neRegionId) + 64);
    }

    public RSPolygon(final int regionID) {
        this(calculateSWCornerX(regionID), calculateSWCornerY(regionID),
                calculateSWCornerX(regionID) + 64, calculateSWCornerY(regionID) + 64);
    }

    public RSPolygon(int swX, int swY, int neX, int neY) {
        this(new int[][]{
                {swX, swY},
                {neX, swY},
                {neX, neY},
                {swX, neY}
        });
    }

    public RSPolygon(Position sw, Position ne) {
        this(sw.getX(), sw.getY(), ne.getX(), ne.getY());
    }

    public RSPolygon(final int[][] points) {
        this(points, 0, 1, 2, 3);
    }

    public RSPolygon(final int[][] points, final int... planes) {
        final int[] xPoints = new int[points.length];
        final int[] yPoints = new int[points.length];
        for (int index = 0; index < points.length; index++) {
            final int[] area = points[index];
            xPoints[index] = area[0];
            yPoints[index] = area[1];
        }
        this.points = points;

        polygon = new EfficientPolygon(xPoints, yPoints, points.length);

        this.planes.addElements(0, planes);
    }

    @Getter
    private final Polygon polygon;
    @Getter
    private final IntArrayList planes = new IntArrayList(4);
    private final BitSet planeSet = new BitSet(4);
    @Getter
    private final int[][] points;

    public boolean contains(final int x, final int y) {
        return polygon.contains(x, y);
    }

    public boolean contains(final int x, final int y, final int plane) {
        return containsPlane(plane) && contains(x, y);
    }

    public boolean contains(final Position position) {
        return contains(position.getX(), position.getY(), position.getZ());
    }

    public void addPoint(final int x, final int y) {
        polygon.addPoint(x, y);
    }

    public boolean containsPlane(int plane) {
        return true; //planeSet.get(plane);
    }

    private static int calculateSWCornerX(final int regionId) {
        return (regionId >> 8) << 6;
    }

    private static int calculateSWCornerY(final int regionId) {
        return (regionId & 0xFF) << 6;
    }

    public static RSPolygon growRelative(Position position, int grow) {
        int north = position.getY() + grow;
        int east = position.getX() + grow;
        int west = position.getX() - grow;
        int south = position.getY() - grow;
        return new RSPolygon(west, south, east, north);
    }

    public static RSPolygon growAbsolute(Position position, int grow) {
        int north = position.getY() + grow;
        int east = position.getX() + grow;
        int west = position.getX() - grow;
        int south = position.getY() - grow;
        return new RSPolygon(west, south, east, north);
    }

    public ObjectArrayList<Position> getBorderPositions(int z) {
        ObjectArrayList<Position> positions = new ObjectArrayList<>();
        int west = (int) polygon.getBounds2D().getMinX();
        int east = (int) polygon.getBounds2D().getMaxX();
        int south = (int) polygon.getBounds2D().getMinY();
        int north = (int) polygon.getBounds2D().getMaxY();
        for (int x = west; x <= east; x++) {
            for (int y = south; y <= north; y++) {
                if (x == west || x == east || y == south || y == north)
                    positions.add(new Position(x, y, z));
            }
        }
        return positions;
    }

    public ObjectArrayList<Position> getAllpositions(int z) {
        ObjectArrayList<Position> positions = new ObjectArrayList<>();
        int west = (int) polygon.getBounds2D().getMinX();
        int east = (int) polygon.getBounds2D().getMaxX();
        int south = (int) polygon.getBounds2D().getMinY();
        int north = (int) polygon.getBounds2D().getMaxY();
        for (int x = west; x <= east; x++) {
            for (int y = south; y <= north; y++) {
                positions.add(new Position(x, y, z));
            }
        }
        return positions;
    }

}
