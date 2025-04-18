package io.ruin.model.item.actions.impl;

import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.MapArea;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;
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
    FIREMAKING_CAPE(-1, StatType.Firemaking.regularCapeId),
    FIREMAKING_CAPE_T(-1, StatType.Firemaking.trimmedCapeId),
    FIREMAKING_MASTER_CAPE(-1, StatType.Firemaking.masterCapeId),
    MAX_CAPE(-1, Items.MAX_CAPE_3),
    KANDARIN_HEADGEAR_1(-1, Items.KANDARIN_HEADGEAR_1),
    KANDARIN_HEADGEAR_2(-1, Items.KANDARIN_HEADGEAR_2),
    KANDARIN_HEADGEAR_3(-1, Items.KANDARIN_HEADGEAR_3),
    KANDARIN_HEADGEAR_4(-1, Items.KANDARIN_HEADGEAR_4),
    BRUMA_TORCH(-1, Items.BRUMA_TORCH),
    BUG_LANTERN(Items.UNLIT_BUG_LANTERN, Items.LIT_BUG_LANTERN);

    public final int unlitId, litId;

    public boolean hasLit(Player player) {
        return player.getInventory().hasId(litId) || player.getEquipment().hasId(litId);
    }

    private void light(Player player, Item item) {
        String itemName = item.getDef().name;
        item.setId(litId);
        player.sendMessage("You light the " + itemName + ".");
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
    public static boolean hasLightSource(Player player) {
        if (player.isDiamond()) return true;
        for (Lightables lightable : values()) {
            if (player.getInventory().contains(lightable.litId) || player.getEquipment().contains(lightable.litId)) {
                return true;
            }
        }
        if (MaxCapeVariants.has(player) || MaxCapeVariants.wearing(player))
            return true;
        if (MapArea.LUMBRIDGE_SWAMP_CAVE.hasFirePitInArea(player) || MapArea.CAVE_OF_HORROR.hasFirePitInArea(player) || MapArea.MOLE_LAIR.hasFirePitInArea(player))
            return true;
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
