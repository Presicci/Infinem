package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/15/2023
 */
public class ObservatoryDungeon {

    private static void openDoor(Player player) {
        if (Config.OBSERVATORY_ROPE.get(player) != 1) {
            player.sendMessage("There doesn't seem to be a way down from here.");
            return;
        }
        Traveling.fadeTravel(player, 2449, 3155, 0);
    }

    static {
        // North stair down
        ObjectAction.register(25432, 2458, 3186, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2355, 9394, 0));
        // North stair up
        ObjectAction.register(25429, 2355, 9395, 0, "climb up", (player, obj) -> player.getMovement().teleport(2457, 3186, 0));

        // South stair down
        ObjectAction.register(25434, 2438, 3164, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2335, 9350, 0));
        // South stair up
        ObjectAction.register(25429, 2335, 9351, 0, "climb up", (player, obj) -> player.getMovement().teleport(2439, 3164, 0));

        // Observatory stairs down
        ObjectAction.register(25437, 2443, 3159, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2444, 3162, 0));
        // Observatory stairs up
        ObjectAction.register(25431, 2444, 3159, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2442, 3159, 1));

        // Door down
        ObjectAction.register(25526, 2445, 3165, 0, "open", (p, obj) -> openDoor(p));
        ObjectAction.register(25527, 2444, 3166, 0, "open", (p, obj) -> openDoor(p));
    }
}
