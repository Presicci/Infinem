package io.ruin.model.item.actions.impl;

import io.ruin.model.content.tasksystem.tasks.TaskCategory;
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
    MINING_HELMET(Items.MINING_HELMET_2, Items.MINING_HELMET),
    FIREMAKING_CAPE(-1, Items.FIREMAKING_CAPE),
    FIREMAKING_CAPE_T(-1, Items.FIREMAKING_CAPE_T),
    FIREMAKING_MASTER_CAPE(-1, 30244),
    MAX_CAPE(-1, Items.MAX_CAPE_3),
    KANDARIN_HEADGEAR_1(-1, Items.KANDARIN_HEADGEAR_1),
    KANDARIN_HEADGEAR_2(-1, Items.KANDARIN_HEADGEAR_2),
    KANDARIN_HEADGEAR_3(-1, Items.KANDARIN_HEADGEAR_3),
    KANDARIN_HEADGEAR_4(-1, Items.KANDARIN_HEADGEAR_4),
    BRUMA_TORCH(-1, Items.BRUMA_TORCH);

    public final int unlitId, litId;

    private void light(Player player, Item item) {
        item.setId(litId);
        player.sendMessage("You light the " + item.getDef().name + ".");
        player.getTaskManager().doLookupByCategory(TaskCategory.LIGHTSOURCE, item.getDef().name);
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
            if (lightable.unlitId != -1) {
                ItemItemAction.register(Tool.TINDER_BOX, lightable.unlitId, (player, tinderbox, item) -> {
                    lightable.light(player, item);
                });
                ItemAction.registerInventory(lightable.litId, "extinguish", lightable::extinguish);
            }
        }
    }
}
