package io.ruin.model.item.actions.impl.combine.smithing;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

public class WyvernShield {

    private static final int VISAGE = 21637;
    private static final int ELE_SHIELD = 2890;
    private static final int FULL = 21633;

    private static void make(Player player) {
        Item leftHalf = player.getInventory().findItem(VISAGE);
        Item rightHalf = player.getInventory().findItem(ELE_SHIELD);

        if(leftHalf == null || rightHalf == null) {
            player.dialogue(new ItemDialogue().two(VISAGE, ELE_SHIELD, "You need a wyvern visage and elemental shield to forge an ancient wyvern shield."));
            return;
        }

        Item hammer = player.getInventory().findItem(Tool.HAMMER);
        if(hammer == null) {
            player.sendMessage("You need a hammer to forge the shield.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the shield...");
            player.animate(898);
            event.delay(6);
            if(player.getInventory().hasId(VISAGE) && player.getInventory().hasId(ELE_SHIELD)) {
                player.getInventory().remove(ELE_SHIELD, 1);
                player.getInventory().remove(VISAGE, 1);
                player.getInventory().add(FULL, 1);
                player.getStats().addXp(StatType.Smithing, 2000, true);
                player.getStats().addXp(StatType.Magic, 2000, true);
                player.getStats().get(StatType.Magic).drain(99);    // Drain magic to 0
                player.sendMessage("You forge the visage to the shield.");
            }
            player.unlock();
        });
    }

    static {
        //  TODO change to the strange machine in house on the hill
        ItemObjectAction.register(VISAGE, "anvil", (player, item, obj) -> make(player));
        ItemObjectAction.register(ELE_SHIELD, "anvil", (player, item, obj) -> make(player));
    }
}
