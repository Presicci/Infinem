package io.ruin.model.activities.combat.nightmarezone;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum NMZPotion {
    SUPER_RANGING(11725, 250, 26277, Config.NMZ_SUPER_RANGE_DOSES),
    SUPER_MAGIC(11729, 250, 26278, Config.NMZ_SUPER_MAGIC_DOSES),
    OVERLOAD(11733, 1500, 26279, Config.NMZ_OVERLOAD_DOSES),
    ABSORPTION(11737,1000, 26280, Config.NMZ_ABSORPTION_DOSES);

    private final int id, price, objectId;
    private final Config config;

    public static NMZPotion getBenefits(int id) {
        for (NMZPotion benefits : values())
            if (benefits.id == id)
                return benefits;
        return null;
    }

    private String getName() {
        return name().toLowerCase().replace("_", " ");
    }

    private void check(Player player, GameObject gameObject) {
        player.dialogue(new MessageDialogue("You have "
                + config.get(player)
                + " doses of "
                + getName()
                + " potion stored."));
    }

    // Dialogue:
    // How many doses? (1-28) or free slots or avaialable potions
    // populate with 4 doses then whatever is left
    private void take(Player player, GameObject gameObject) {
        int doses = config.get(player);
        if (doses <= 0) {
            player.sendMessage("There are no potion doses to take.");
            return;
        }
        int fourDoses = doses / 4;
        int remainder = doses % 4;
        int potions = fourDoses + (remainder == 0 ? 0 : 1);
        int freeSlots = player.getInventory().getFreeSlots();
        if (freeSlots <= 0) {
            player.sendMessage("Your inventory is too full to take any potions.");
            return;
        }
        int maxPotions = Math.min(potions, freeSlots);
        player.integerInput("Take how many potions? (1 - " + maxPotions + ")", i -> {
            if (i > maxPotions) i = maxPotions;
            if (i == 0) return;
            if (i > fourDoses) {
                player.getInventory().add(id - 3, fourDoses);
                player.getInventory().add(id - (remainder - 1), 1);
                config.set(player, 0);
                player.dialogue(new ItemDialogue().one(id - 3, "You take the last of your " + getName() + " doses."));
            } else {
                player.getInventory().add(id - 3, i);
                int remaining = config.decrement(player, i * 4);
                player.dialogue(new ItemDialogue().one(id - 3, "You take " + (i*4) + " doses of your " + getName() + " potion. You have " + remaining + " doses left."));
            }
        });
    }

    private void store(Player player, GameObject gameObject) {
        int doses = config.get(player);
        if (doses >= 255) {
            player.dialogue(new MessageDialogue("Your potion storage for " + getName() + " potions is full."));
            return;
        }
        int dosesToAdd = 0;
        for (Item item : player.getInventory().getItems()) {
            if (item == null) continue;
            int itemId = item.getId();
            if (itemId >= id - 3 && itemId <= id) {
                int itemDoses = (id - itemId) + 1;
                if (dosesToAdd + doses + itemDoses > 255) continue;
                dosesToAdd += itemDoses;
                item.remove();
            }
        }
        if (dosesToAdd == 0) {
            player.dialogue(new MessageDialogue("You don't have any potion doses to store."));
            return;
        }
        int newAmount = config.increment(player, dosesToAdd);
        player.dialogue(new ItemDialogue().one(id - 3, "You deposit " + dosesToAdd + " doses of " + getName() + " potion. You now have " + newAmount + " doses stored."));
    }

    private void storePotion(Player player, Item item, GameObject gameObject) {
        int doses = config.get(player);
        if (doses >= 255) {
            player.dialogue(new MessageDialogue("Your potion storage for " + getName() + " potions is full."));
            return;
        }
        int itemDoses = (id - item.getId()) + 1;
        if (itemDoses + doses > 255) {
            int remainingDoses = itemDoses - (255 - doses);
            config.set(player, 255);
            item.setId(id - (remainingDoses - 1));
        } else {
            config.increment(player, itemDoses);
            item.remove();
        }
    }

    static {
        for (NMZPotion potion : values()) {
            ObjectAction.register(potion.objectId, "check", potion::check);
            ObjectAction.register(potion.objectId, "take", potion::take);
            ObjectAction.register(potion.objectId, "store", potion::store);
            for (int index = potion.id; index > potion.id - 4; index--) {
                ItemObjectAction.register(index, potion.objectId, potion::storePotion);
            }
        }
    }
}
