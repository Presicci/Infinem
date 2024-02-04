package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/3/2024
 */
public class TiranwnDenseForest {

    public static void passClimb(Player player, GameObject obj, Position destination){
        if (!player.getStats().check(StatType.Agility, 56, "attempt this"))
            return;
        player.startEvent( e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.face(obj);
            player.animate(839);
            e.delay(1);
            player.getMovement().teleport(destination);
            e.delay(1);
            player.unlock();
        });
    }

    public static void passSideways(Player player, GameObject obj, Position destination){
        if (!player.getStats().check(StatType.Agility, 56, "attempt this"))
            return;
        player.startEvent( e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.face(obj);
            player.animate(2594);
            e.delay(1);
            player.getMovement().teleport(destination);
            player.animate(2595);
            e.delay(1);
            player.unlock();
        });
    }

    static {
        // Tyras camp
        ObjectAction.register(3998, 2187, 3169, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2188, 3162, 0)));
        ObjectAction.register(3999, 2187, 3163, 0, "enter", (player, obj) -> passClimb(player, obj, new Position(2188, 3171, 0)));
        ObjectAction.register(3939, 2173, 3156, 0, "enter", (player, obj) -> {
            if (player.getAbsX() > 2174) {
                passSideways(player, obj, new Position(2173, 3156, 0));
            } else {
                passSideways(player, obj, new Position(2175, 3158, 0));
            }
        });
        // East of tyras
        ObjectAction.register(3939, 2216, 3167, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2217, 3160, 0)));
        ObjectAction.register(3939, 2216, 3161, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2217, 3169, 0)));
        // East even more
        ObjectAction.register(3937, 2232, 3148, 0, "enter", (player, obj) -> passClimb(player, obj, new Position(2240, 3149, 0)));
        ObjectAction.register(3939, 2238, 3148, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2231, 3149, 0)));
        // West of Lletya
        ObjectAction.register(3937, 2272, 3191, 0, "enter", (player, obj) -> passClimb(player, obj, new Position(2265, 3192, 0)));
        ObjectAction.register(3938, 2266, 3191, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2274, 3191, 0)));
        // Northwest of Lletya
        ObjectAction.register(3938, 2278, 3223, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2279, 3231, 0)));
        ObjectAction.register(3939, 2278, 3229, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2279, 3222, 0)));
        // North of Lletya
        ObjectAction.register(3937, 2302, 3214, 0, "enter", (player, obj) -> passClimb(player, obj, new Position(2304, 3225, 0)));
        ObjectAction.register(3938, 2302, 3223, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2303, 3213, 0)));
        // South of Prif
        ObjectAction.register(3937, 2237, 3248, 0, "enter", (player, obj) -> passClimb(player, obj, new Position(2230, 3249, 0)));
        ObjectAction.register(3938, 2231, 3248, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2239, 3249, 0)));
        // South even more
        ObjectAction.register(3939, 2236, 3218, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2226, 3219, 0)));
        ObjectAction.register(3938, 2227, 3218, 0, "enter", (player, obj) -> passSideways(player, obj, new Position(2238, 3219, 0)));
    }
}
