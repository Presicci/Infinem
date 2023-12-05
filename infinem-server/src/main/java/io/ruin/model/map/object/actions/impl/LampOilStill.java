package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.Containers;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/4/2023
 */
public class LampOilStill {

    private static final int[] STILLS = { 5908, 22773 };
    private static final String attKey = "OIL_STILL";

    private static void clearOil(Player player, Item item, Containers waterContainer) {
        int fillableIndex = player.getAttributeIntOrZero(attKey);
        if (fillableIndex == 0) {
            player.dialogue(new MessageDialogue("There is no oil in the lamp still. No reason to wash it out."));
            return;
        }
        player.animate(3572);
        item.setId(waterContainer.empty);
        player.removeAttribute(attKey);
        player.sendMessage("You wash out the lamp still.");
    }

    private static void loadOil(Player player, Item item, OilFillable fillable) {
        int fillableIndex = player.getAttributeIntOrZero(attKey);
        if (fillableIndex != 0) {
            player.dialogue(new MessageDialogue("There is already " + ItemDef.get(OilFillable.values()[fillableIndex - 1].oil).name + " in the lamp still. Wash it out first."));
            return;
        }
        player.animate(3572);
        item.remove(1);
        player.putAttribute(attKey, fillable.ordinal() + 1);
        player.sendMessage("You add " + item.getDef().name + " to the lamp still.");
    }

    private static void fill(Player player, Item item, OilFillable fillable) {
        int fillableIndex = player.getAttributeIntOrZero(attKey);
        if (fillableIndex == 0) {
            player.dialogue(new MessageDialogue("You first need to fill the lamp still with " + ItemDef.get(fillable.oil).name + "."));
            return;
        }
        int oilId = OilFillable.values()[fillableIndex - 1].oil;
        if (oilId != fillable.oil) {
            player.dialogue(new MessageDialogue("The lamp still is currently filled with "
                    + ItemDef.get(oilId).name
                    + ". You can only fill a " + ItemDef.get(fillable.empty).name
                    + " with " + ItemDef.get(fillable.oil).name + "."));
            return;
        }
        player.animate(3572);
        player.removeAttribute(attKey);
        item.setId(fillable.filled);
        if (fillable == OilFillable.IMPLING_JAR) {
            player.getTaskManager().doLookupByUUID(934);    // Make an Impling Jar From Scratch
        }
        player.sendMessage("You fill the " + ItemDef.get(fillable.filled).name + ".");
    }

    static {
        for (int id : STILLS) {
            for (OilFillable fillable : OilFillable.values()) {
                ItemObjectAction.register(fillable.oil, id, (player, item, obj) -> loadOil(player, item, fillable));
                ItemObjectAction.register(fillable.empty, id, (player, item, obj) -> fill(player, item, fillable));
            }
            for (Containers waterContainer : Containers.values()) {
                ItemObjectAction.register(waterContainer.full, id, (player, item, obj) -> clearOil(player, item, waterContainer));
            }
        }
    }

    @AllArgsConstructor
    private enum OilFillable {
        BULLSEYE_LANTERN(Items.BULLSEYE_LANTERN_EMPTY, Items.SWAMP_TAR, Items.BULLSEYE_LANTERN),
        OIL_LANTERN(Items.EMPTY_OIL_LANTERN, Items.SWAMP_TAR, Items.OIL_LANTERN),
        OIL_LAMP(Items.EMPTY_OIL_LAMP, Items.SWAMP_TAR, Items.OIL_LAMP),
        IMPLING_JAR(Items.BUTTERFLY_JAR, Items.IMP_REPELLENT, Items.IMPLING_JAR);

        private final int empty, oil, filled;
    }
}
