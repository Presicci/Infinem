package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.DoubleDoor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/14/2024
 */
public class WaterRavineDungeon {

    static {
        Tile.getObject(6382, 3369, 3132, 0).skipReachCheck = p -> p.equals(3372, 3130);
        Tile.getObject(6381, 3368, 3133, 0).skipReachCheck = p -> p.equals(3372, 3130);
        // TODO find animation
        // Entrance
        ItemObjectAction.register(Items.ROPE, 6381, (player, item, obj) -> Traveling.fadeTravel(player, new Position(3349, 9536, 0)));
        ItemObjectAction.register(Items.ROPE, 6382, (player, item, obj) -> Traveling.fadeTravel(player, new Position(3349, 9536, 0)));

        // First set of double doors
        ObjectAction.register(10427, 3353, 9544, 0, "open", ((player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 10427, 10429, new Position(3353, 9544), new Position(3354, 9544))));
        ObjectAction.register(10429, 3354, 9544, 0, "open", ((player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 10427, 10429, new Position(3353, 9544), new Position(3354, 9544))));

        // Second set of double doors
        ObjectAction.register(10423, 3354, 9558, 0, "open", ((player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 10423, 10425, new Position(3354, 9558), new Position(3355, 9558))));
        ObjectAction.register(10425, 3355, 9558, 0, "open", ((player, obj) -> DoubleDoor.passThroughLongitudinal(player, obj, 10423, 10425, new Position(3354, 9558), new Position(3355, 9558))));
    }
}
