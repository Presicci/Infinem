package io.ruin.model.activities.wilderness.bosses;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/22/2024
 */
public class EscapeTunnels {
    static {
        ObjectAction.register(47149, 3361, 10273, 0, 1, ((player, obj) -> exitEscapeCave(player, obj, 3285, 3806)));
        ObjectAction.register(47148, 3382, 10287, 0, 1, ((player, obj) -> exitEscapeCave(player, obj, 3321, 3829)));
        ObjectAction.register(47148, 3336, 10287, 0, 1, ((player, obj) -> exitEscapeCave(player, obj, 3261, 3831)));
        ObjectAction.register(47147, 3358, 10244, 0, 1, ((player, obj) -> exitEscapeCave(player, obj, 3283, 3773)));

        LoginListener.register(p -> {
            if (p.getPosition().getRegion().getId() == 13472) {
                p.getMovement().teleport(3285, 3806, 0);
            }
        });
    }

    private static void exitEscapeCave(Player player, GameObject object, int x, int y) {
        player.startEvent(e -> {
            player.lock();
            player.animate(object.id == 47149 ? 4435 : 7041);
            e.delay(2);
            player.animate(-1);
            player.getMovement().teleport(x, y, 0);
            player.unlock();
        });
    }
}
