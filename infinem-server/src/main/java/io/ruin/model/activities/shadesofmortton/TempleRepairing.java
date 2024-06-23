package io.ruin.model.activities.shadesofmortton;

import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/23/2024
 */
public class TempleRepairing {

    private static final int FLUSH_WALL = 4068;
    private static final int CORNER_WALL = 4079;

    static {
        // Varp 343 - repair state
        // Varp 344 - resources
        // Varp 345 - sanctity
        /*for (int index = FLUSH_WALL; index < FLUSH_WALL + 11; index++) {
            int finalIndex = index;
            ObjectAction.register(index, "repair", (player, obj) -> obj.setId(finalIndex + 1));
        }
        for (int index = CORNER_WALL; index < CORNER_WALL + 11; index++) {
            int finalIndex = index;
            ObjectAction.register(index, "repair", (player, obj) -> obj.setId(finalIndex + 1));
        }
        MapListener.registerBounds(Bounds.fromRegion(13875))
                .onEnter(player -> {
                    player.openInterface(InterfaceType.PRIMARY_OVERLAY, 171);
                })
                .onExit((player, logout) -> {
                    player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
                });*/
    }
}
