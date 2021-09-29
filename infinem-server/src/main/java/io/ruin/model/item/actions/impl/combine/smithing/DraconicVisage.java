package io.ruin.model.item.actions.impl.combine.smithing;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DraconicVisage {
    SHIELD(Items.DRAGONFIRE_SHIELD, Items.DRACONIC_VISAGE, Items.ANTIDRAGON_SHIELD),
    WARD(22003, Items.DRACONIC_VISAGE, 22006);

    private final int product, primary, secondary;

    private void make(Player player) {
        Item leftHalf = player.getInventory().findItem(primary);
        Item rightHalf = player.getInventory().findItem(secondary);

        if(leftHalf == null || rightHalf == null) {
            player.dialogue(new ItemDialogue().two(primary, secondary, "You need a draconic visage and " + ItemDef.get(secondary).descriptiveName + " to forge " + ItemDef.get(product).descriptiveName + "."));
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
            if(player.getInventory().hasId(primary) && player.getInventory().hasId(secondary)) {
                player.getInventory().remove(primary, 1);
                player.getInventory().remove(secondary, 1);
                player.getInventory().add(product, 1);
                player.getStats().addXp(StatType.Smithing, 2000, true);
                player.sendMessage("You forge the visage to the shield.");
            }
            player.unlock();
        });
    }
}