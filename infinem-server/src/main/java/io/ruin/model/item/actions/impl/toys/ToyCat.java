package io.ruin.model.item.actions.impl.toys;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/23/2023
 */
public class ToyCat {

    private static void play(Player player, int anim) {
        player.animate(anim);
        player.forceText("Grrrr!");
    }

    static {
        ItemAction.registerInventory(Items.TIGER_TOY, "play-with", (player, item) -> play(player, 3414));
        ItemAction.registerInventory(Items.LION_TOY, "play-with", (player, item) -> play(player, 3413));
        ItemAction.registerInventory(Items.SNOW_LEOPARD_TOY, "play-with", (player, item) -> play(player, 3541));
        ItemAction.registerInventory(Items.AMUR_LEOPARD_TOY, "play-with", (player, item) -> play(player, 3839));
    }
}
