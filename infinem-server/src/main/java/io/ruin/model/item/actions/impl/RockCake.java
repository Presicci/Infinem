package io.ruin.model.item.actions.impl;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

public class RockCake {

    private static void eat(Player player) {
        if(player.getHp() >= 2 && !player.rockCakeDelay.isDelayed()) {
            if (player.getHp() < 10) {
                player.rockCakeDelay.delay(2);
            } else {
                player.rockCakeDelay.delay(1);
            }
            player.privateSound(1018);
            player.hit(new Hit().fixedDamage(1).ignoreAbsorption());
            player.animate(829);
        }
    }

    private static void guzzle(Player player) {
        if(player.getHp() >= 2 && !player.rockCakeDelay.isDelayed()) {
            int damage = (player.getHp() + 10) / 10;
            if (player.getHp() < 10) {
                player.rockCakeDelay.delay(2);
            } else {
                player.rockCakeDelay.delay(1);
            }
            player.privateSound(1018);
            player.hit(new Hit().fixedDamage(damage).ignoreAbsorption());
            player.animate(829);
        }
    }

    static {
        ItemAction.registerInventory(Items.DWARVEN_ROCK_CAKE, "eat", (player, item) -> eat(player));
        ItemAction.registerInventory(Items.DWARVEN_ROCK_CAKE_2, "guzzle", (player, item) -> guzzle(player));
    }
}
