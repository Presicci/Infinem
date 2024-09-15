package io.ruin.model.skills.crafting;

import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.CHISEL;

public enum Snelm {
    BLOOD_ROUND(3347, 3329),
    BLOOD_POINT(3357, 3339),
    BARK(3353, 3335),
    BLUE_ROUND(3351, 3333),
    BLUE_POINT(3361, 3343),
    MYRE_ROUND(3345, 3327),
    MYRE_POINT(3355, 3337),
    OCHRE_ROUND(3349, 3331),
    OCHRE_POINT(3359, 3341);

    private final int uncutId, cutId;

    Snelm(int uncutId, int cutId) {
        this.uncutId = uncutId;
        this.cutId = cutId;
    }

    static {
        for (Snelm snelm : values()) {
            ItemItemAction.register(Items.CHISEL, snelm.uncutId, (player, chisel, shell) -> {
                if(!player.getStats().check(StatType.Crafting, 15, CHISEL, snelm.uncutId, "cut the snelm."))
                    return;
                RandomEvent.attemptTrigger(player);
                player.animate(1309);
                player.getStats().addXp(StatType.Crafting, 32.5, true);
                player.sendFilteredMessage("You cut the snelm.");
                player.getTaskManager().doLookupByUUID(702);    // Craft a Snelm
                shell.setId(snelm.cutId);
            });
        }
    }
}
