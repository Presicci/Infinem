package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/31/2022
 */
public class FossilIsland {

    private static void rakeVines(Player player, GameObject object) {
        if (!player.getInventory().hasId(Tool.RAKE)) {
            player.sendMessage("You need a rake to clear these vines.");
            return;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(2273);
            e.delay(2);
            player.unlock();
            object.setId(-1);
            player.getStats().addXp(StatType.Farming, 1, true);
            player.sendFilteredMessage("You rake the vines aside.");
            World.startEvent(we -> {
                we.delay(120);
                object.setId(object.originalId);
            });
        });
    }

    static {
        ObjectAction.register(30644, "clear", FossilIsland::rakeVines);
        // House on the hill stairs
        ObjectAction.register(30681, 3754, 3868, 0, "climb", (player, obj) -> player.getMovement().teleport(3757, 3869, 1));
        ObjectAction.register(30682, 3754, 3868, 1, "descend", (player, obj) -> player.getMovement().teleport(3753, 3869, 0));
        // To bank check island
        ObjectAction.register(30915, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3768, 3898))));
        // From bank check island
        ObjectAction.register(30919, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3734, 3893))));
        // Dive to underwater
        ObjectAction.register(30919, "dive", ((player, obj) -> Traveling.fadeTravel(player, new Position(3731, 10281, 1))));
        // Climb to island
        ObjectAction.register(30948, "climb", ((player, obj) -> Traveling.fadeTravel(player, new Position(3768, 3898))));
        // Rowboat to mainland
        ObjectAction.register(30914, "travel", ((player, obj) -> Traveling.fadeTravel(player, new Position(3362, 3445))));
    }
}
