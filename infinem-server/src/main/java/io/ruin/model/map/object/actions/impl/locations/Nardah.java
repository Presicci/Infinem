package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class Nardah {

    private static void heal(Player player) {
        player.animate(833);
        player.graphics(1039);
        player.getStats().restore(false);
        player.getMovement().restoreEnergy(100);
        player.getCombat().restoreSpecial(100);
        player.curePoison(1);
        player.cureVenom(1, 1);
        player.sendMessage("Your stats have been restored!");
    }

    static {
        // Nardah statuette
        ObjectAction.register(10389, 1, (player, obj) -> heal(player));
    }
}
