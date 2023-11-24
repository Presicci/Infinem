package io.ruin.model.item.actions.impl.toys;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2023
 */
public class ToyHorsey {

    private static void play(Player player, int anim) {
        player.animate(anim);
        player.forceText("Just say neigh to gambling!");
    }

    static {
        ItemAction.registerInventory(Items.BROWN_TOY_HORSEY, "play-with", (player, item) -> play(player, 918));
        ItemAction.registerInventory(Items.WHITE_TOY_HORSEY, "play-with", (player, item) -> play(player, 919));
        ItemAction.registerInventory(Items.BLACK_TOY_HORSEY, "play-with", (player, item) -> play(player, 920));
        ItemAction.registerInventory(Items.GREY_TOY_HORSEY, "play-with", (player, item) -> play(player, 921));
    }
}
