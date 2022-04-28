package io.ruin.utility;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;

import java.security.SecureRandom;
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
}
