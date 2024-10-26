package io.ruin.utility;

import io.ruin.Server;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.map.route.routes.TargetRoute;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Misc {

    private static long lastUpdateTime = 0;
    private static long timeCorrection = 0;

    public static int random(int min , int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static int random(int range) {
        return (int) (java.lang.Math.random() * (range + 1));
    }

    public static double randomDouble(double min, double max) {
        return (Math.random() * (max - min) + min);
    }

    public static synchronized long currentTimeCorrectedMillis() {
        long current = System.currentTimeMillis();
        if (current < lastUpdateTime)
            timeCorrection += lastUpdateTime - current;
        lastUpdateTime = current;
        return current + timeCorrection;
    }

    public static int getDistance(Position src, Position dest) {
        return getDistance(src.getX(), src.getY(), dest.getX(), dest.getY());
    }

    public static int getDistance(Position src, int destX, int destY) {
        return getDistance(src.getX(), src.getY(), destX, destY);
    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        int diffX = Math.abs(x1 - x2);
        int diffY = Math.abs(y1 - y2);
        return Math.max(diffX, diffY);
    }

    public static int getClosestX(Entity src, Entity target) {
        return getClosestX(src, target.getPosition());
    }

    public static int getClosestX(Entity src, Position target) {
        if (src.getSize() == 1)
            return src.getAbsX();
        if (target.getX() < src.getAbsX())
            return src.getAbsX();
        else if (target.getX() >= src.getAbsX() && target.getX() <= src.getAbsX() + src.getSize() - 1)
            return target.getX();
        else
            return src.getAbsX() + src.getSize() - 1;
    }

    public static int getClosestY(Entity src, Entity target) {
        return getClosestY(src, target.getPosition());
    }

    public static int getClosestY(Entity src, Position target) {
        if (src.getSize() == 1)
            return src.getAbsY();
        if (target.getY() < src.getAbsY())
            return src.getAbsY();
        else if (target.getY() >= src.getAbsY() && target.getY() <= src.getAbsY() + src.getSize() - 1)
            return target.getY();
        else
            return src.getAbsY() + src.getSize() - 1;
    }

    public static Position getClosestPosition(Entity src, Entity target) {
        return new Position(getClosestX(src, target), getClosestY(src, target), src.getHeight());
    }

    public static Position getClosestPosition(Entity src, Position target) {
        return new Position(getClosestX(src, target), getClosestY(src, target), src.getHeight());
    }

    public static int getEffectiveDistance(Entity src, Entity target) {
        Position pos = getClosestPosition(src, target);
        Position pos2 = getClosestPosition(target, src);
        return getDistance(pos, pos2);
    }

    public static <T> T randomTypeOfList(List<T> list) {
        if(list == null || list.size() == 0)
            return null;
        return list.get(new SecureRandom().nextInt(list.size()));
    }

    public static String format_string(String string, Object... params) {
        return params == null ? string : String.format(string, (Object[]) params);
    }

    public static String getGenderPronoun(Player player) {
        return player.getAppearance().isMale() ? "him" :  "her";
    }

    public static String abbreviateItemQuantity(final long quantity) {
        if (quantity >= 1000 && quantity < 1_000_000) {
            return quantity / 1000 + "K";
        } else if (quantity >= 1_000_000 && quantity <= 9999999999L) {
            return quantity / 1000000 + "M";
        } else if (quantity >= 10000000000L && quantity <= 9999999999999L) {
            return quantity / 1000000000L + "B";
        } else if (quantity >= 10000000000000L) {
            return quantity / 10000000000000L + "T";
        }
        return String.valueOf(quantity);
    }

    public static boolean withinReach(Position playerPos, Position otherPos, int distance) {
        if (!TargetRoute.inTarget(playerPos.getX(), playerPos.getY(), 1, otherPos.getX(), otherPos.getY(), 1)
                && TargetRoute.inRange(playerPos.getX(), playerPos.getY(), 1, otherPos.getX(), otherPos.getY(), 1, distance)
                && ProjectileRoute.allow(playerPos.getX(), playerPos.getY(), playerPos.getZ(), 1, otherPos.getX(), otherPos.getY(), 1))
            return true;
        return playerPos.equals(otherPos);
    }

    public static int getMoveDirection(final int xOffset, final int yOffset) {
        if (xOffset < 0) {
            if (yOffset < 0) {
                return 0;
            } else if (yOffset > 0) {
                return 5;
            } else {
                return 3;
            }
        } else if (xOffset > 0) {
            if (yOffset < 0) {
                return 2;
            } else if (yOffset > 0) {
                return 7;
            } else {
                return 4;
            }
        } else {
            if (yOffset < 0) {
                return 1;
            } else if (yOffset > 0) {
                return 6;
            } else {
                return -1;
            }
        }
    }

    public static List<Position> calculateLine(int x1, int y1, final int x2, final int y2, final int plane) {
        final List<Position> tiles = new ArrayList<>();
        final int dx = Math.abs(x2 - x1);
        final int dy = Math.abs(y2 - y1);
        final int sx = (x1 < x2) ? 1 : -1;
        final int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;
        while (true) {
            tiles.add(new Position(x1, y1, plane));
            if (x1 == x2 && y1 == y2) {
                break;
            }
            final int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
        return tiles;
    }

    public static int clientDelayToTicks(int delay) {
        return Math.max(1, (delay * 16) / Server.tickMs());
    }
}
