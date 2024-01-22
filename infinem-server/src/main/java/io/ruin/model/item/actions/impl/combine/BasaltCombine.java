package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/14/2023
 */
public class BasaltCombine {

    private enum BasaltType {
        ICY_BASALT(22599, new Item(22603), new Item(22595, 3), new Item(22593, 1)),
        STONY_BASALT(22601, new Item(22603), new Item(22597, 3), new Item(22593, 1));

        private static final BasaltType[] values = values();
        private final int basaltId;
        private final Item[] requirements;

        BasaltType(final int basaltId, final Item... requirements) {
            this.basaltId = basaltId;
            this.requirements = requirements;
        }
    }

    private static void empower(Player player, Item from, Item to) {
        List<SkillItem> list = new ArrayList<>();
        for (BasaltType type : BasaltType.values) {
            if (!player.getInventory().containsAll(false, type.requirements)) continue;
            list.add(new SkillItem(type.basaltId).addAction((p, amount, event) -> empower(player, type, amount)));
        }
        if (list.isEmpty()) {
            player.sendMessage("You do not have enough salts to empower the basalt.");
            return;
        }
        SkillDialogue.make(player, list.toArray(new SkillItem[0]));
    }

    private static void empower(Player player, BasaltType type, int amount) {
        for (Item item : type.requirements) {
            int invQuantity = player.getInventory().getAmount(item.getId()) / item.getAmount();
            if (invQuantity < amount) {
                amount = invQuantity;
            }
        }
        if (amount <= 0) {
            player.sendMessage("You do not have enough salts to empower the basalt.");
            return;
        }
        for (Item item : type.requirements) {
            player.getInventory().remove(item.getId(), item.getAmount() * amount);
        }
        player.getInventory().addOrDrop(type.basaltId, amount);
    }

    static {
        ItemItemAction.register(22603, 22593, BasaltCombine::empower);
        ItemItemAction.register(22603, 22595, BasaltCombine::empower);
        ItemItemAction.register(22603, 22597, BasaltCombine::empower);
    }
}
