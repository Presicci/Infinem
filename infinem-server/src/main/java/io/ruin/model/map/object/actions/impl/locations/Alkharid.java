package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Stairs;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class Alkharid {

    static {
        Stairs.registerSpiralStair(16671, new Position(3312, 3185, 0));
        Stairs.registerSpiralStair(16673, new Position(3313, 3185, 1));

        ObjectAction.register(33348, 1, (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            player.startEvent(event -> {
                player.lock();
                player.animate(2586);
                event.delay(1);

                player.getMovement().teleport(3295, 3157, 0);
                player.unlock();

            });
        });

        ObjectAction.register(33344, 1, (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            player.startEvent(event -> {
                player.lock();
                player.animate(2586);
                event.delay(1);

                player.getMovement().teleport(3295, 3159, 0);
                player.unlock();

            });
        });
    }
}
