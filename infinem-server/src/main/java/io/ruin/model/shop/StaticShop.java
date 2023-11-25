package io.ruin.model.shop;

import io.ruin.model.entity.player.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StaticShop {
    CULINAROMANCERS_CHEST_FOOD(0);

    private final int uuid;

    public void open(Player player) {
        Shop.shops.get(uuid).open(player);
    }
}
