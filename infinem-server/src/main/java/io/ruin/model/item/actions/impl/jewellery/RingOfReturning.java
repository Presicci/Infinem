package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RingOfReturning {

    FIVE(21129, 5, 21132),
    FOUR(21132, 4, 21134),
    THREE(21134, 3, 21136),
    TWO(21136, 2, 21138),
    ONE(21138, 1, -1);

    private final int id, charges, replacementId;

    private static void teleport(Player player, Item item, int charges, int replacementId) {
        Position target = player.getRespawnPoint().getRandomPosition();
        player.getMovement().startTeleport(20, event -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            event.delay(3);
            player.getMovement().teleport(target);
            if(replacementId == -1) {
                item.remove();
                player.sendMessage("<col=7F00FF>Your ring of returning crumbles to dust.");
            } else {
                item.setId(replacementId);
                player.sendMessage("<col=7F00FF>Your ring of returning has " + JeweleryTeleports.CHARGES[charges - 2] + " left.");
            }
        });
    }

    static {
        for(RingOfReturning ring : values()) {
            ItemAction.registerInventory(ring.id, "rub", ((player, item) -> teleport(player, item, ring.charges, ring.replacementId)));
            ItemAction.registerEquipment(ring.id, "rub", ((player, item) -> teleport(player, item, ring.charges, ring.replacementId)));
        }
    }
}

