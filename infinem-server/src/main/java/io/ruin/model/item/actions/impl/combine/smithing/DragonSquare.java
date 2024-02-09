package io.ruin.model.item.actions.impl.combine.smithing;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.stat.StatType;

public class DragonSquare {

    private static final int LEFT = 2366;
    private static final int RIGHT = 2368;
    private static final int FULL = 1187;

    private static void make(Player player) {
        Item leftHalf = player.getInventory().findItem(LEFT);
        Item rightHalf = player.getInventory().findItem(RIGHT);

        if(leftHalf == null || rightHalf == null) {
            player.dialogue(new ItemDialogue().two(LEFT, RIGHT, "You need both the left and right shield halves to forge a square shield."));
            return;
        }
        if(!SmithBar.hasHammer(player)) {
            player.dialogue(new MessageDialogue("You need a hammer to forge the shield halves."));
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.sendMessage("You start to hammer the metal...");
            player.animate(SmithBar.getHammerAnim(player));
            event.delay(6);
            if(player.getInventory().hasId(RIGHT) && player.getInventory().hasId(LEFT)) {
                player.getInventory().remove(RIGHT, 1);
                player.getInventory().remove(LEFT, 1);
                player.getInventory().add(FULL, 1);
                player.getStats().addXp(StatType.Smithing, 75, true);
                player.sendMessage("You forge the shield halves together to complete it.");
            }
            player.unlock();
        });
    }

    static {
        ItemObjectAction.register(LEFT, "anvil", (player, item, obj) -> make(player));
        ItemObjectAction.register(RIGHT, "anvil", (player, item, obj) -> make(player));
    }
}
