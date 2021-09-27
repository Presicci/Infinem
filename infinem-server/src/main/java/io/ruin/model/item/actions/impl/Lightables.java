package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/27/2021
 */
@AllArgsConstructor
public enum Lightables {
    CANDLE(Items.CANDLE, Items.LIT_CANDLE),
    BLACK_CANDLE(Items.BLACK_CANDLE, Items.LIT_BLACK_CANDLE),
    TORCH(Items.UNLIT_TORCH, Items.TORCH),
    LANTERN(Items.CANDLE_LANTERN, Items.CANDLE_LANTERN_2),
    BLACK_LANTERN(Items.CANDLE_LANTERN_3, Items.CANDLE_LANTERN_4),
    OIL_LAMP(Items.OIL_LAMP, Items.OIL_LAMP_2),
    OIL_LANTERN(Items.OIL_LANTERN, Items.OIL_LANTERN_2),
    BULLSEYE_LANTERN(Items.BULLSEYE_LANTERN, Items.BULLSEYE_LANTERN_2),
    SAPPHIRE_LANTERN(Items.SAPPHIRE_LANTERN_2, Items.SAPPHIRE_LANTERN_3),
    EMERALD_LANTERN(Items.EMERALD_LANTERN, Items.EMERALD_LANTERN_2),
    MINING_HELMET(Items.MINING_HELMET_2, Items.MINING_HELMET);

    public final int unlitId, litId;

    private void light(Player player, Item item) {
        item.setId(litId);
        player.sendMessage("You light the " + item.getDef().name + ".");
    }

    private void extinguish(Player player, Item item) {
        item.setId(unlitId);
        player.sendMessage("You extinguish the " + item.getDef().name + ".");
    }

    /**
     * Checks whether a player has a lit light source on them
     * @param player The player to check
     * @return True if the player has a light source, false if not
     */
    private static boolean hasLightSource(Player player) {
        for (Lightables lightable : values()) {
            if (player.getInventory().hasId(lightable.litId) || player.getEquipment().hasId(lightable.litId)) {
                return true;
            }
        }
        return false;
    }

    static {
        for (Lightables lightable : values()) {
            ItemItemAction.register(Tool.TINDER_BOX, lightable.unlitId, (player, tinderbox, item) -> {
                lightable.light(player, item);
            });
            ItemAction.registerInventory(lightable.litId, "extinguish", lightable::extinguish);
        }
    }
}
