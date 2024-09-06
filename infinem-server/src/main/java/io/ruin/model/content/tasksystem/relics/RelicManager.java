package io.ruin.model.content.tasksystem.relics;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.StructDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/11/2022
 */
public class RelicManager {

    // 1 = twisted, 2 = trailblazer, 3 = infinem, 4 = trailblazer II
    private static final Config RELIC_SET = Config.varpbit(10032, false).defaultValue(3);

    public static final int[] TIER_REQUIREMENTS = {
            0,      // Tier 1
            500,    // Tier 2
            2000,   // Tier 3
            4000,   // Tier 4
            7500,   // Tier 5
            15000   // Tier 6
    };

    private static final Config[] RELICS = {
            Config.varpbit(10049, true),
            Config.varpbit(10050, true),
            Config.varpbit(10051, true),
            Config.varpbit(10052, true),
            Config.varpbit(10053, true),
            Config.varpbit(11696, true)
    };

    public RelicManager(Player player) {
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;

    public boolean hasRelicInTier(Relic relic) {
        return hasRelicInTier(relic.getTier());
    }

    public boolean hasRelicInTier(int tier) {
        return RELICS[tier - 1].get(player) != 0;
    }

    public boolean hasPointsForRelic(Relic relic) {
        return Config.LEAGUE_POINTS.get(player) >= TIER_REQUIREMENTS[relic.getTier() - 1];
    }

    public boolean hasRelicBefore(Relic relic) {
        if (relic.getTier() == 1) return true;
        return RELICS[relic.getTier() - 2].get(player) != 0;
    }

    public int pointsForCurrentRelic() {
        return TIER_REQUIREMENTS[Math.min(0, getHighestTier() - 1)];
    }

    public int pointsForNextRelic() {
        int tier = getHighestTier();
        if (tier >= RELICS.length) return -1;
        return TIER_REQUIREMENTS[tier];
    }

    public boolean takeRelic(Relic relic) {
        int tier = relic.getTier();
        Config config = RELICS[tier-1];
        if (hasRelicInTier(relic)) {
            player.sendMessage("You already have a relic of this tier.");
            return false;
        }
        int pointRequirement = TIER_REQUIREMENTS[tier-1];
        if (!hasPointsForRelic(relic)) {
            player.sendMessage("You need " + pointRequirement + " task points to unlock a relic from tier " + tier + ".");
            return false;
        }
        if (!hasPointsForRelic(relic)) {
            player.sendMessage("You need a relic from tier " + (tier - 1) + " before you can take a relic from this tier.");
            return false;
        }
        // Check point requirement
        config.set(player, relic.getConfigValue());
        player.getTaskManager().doLookupByUUID(1006);
        player.resetActions(false, true, false);
        int[] itemIds = relic.getItemIds();
        if (itemIds != null) {
            for (int itemId : itemIds) {
                if (player.getInventory().hasRoomFor(itemId)) {
                    player.getInventory().add(itemId);
                } else if (player.getBank().hasRoomFor(itemId)) {
                    player.getBank().add(itemId);
                    player.sendMessage("Your inventory is full, so the relic item was sent to your bank: " + ItemDefinition.get(itemId).name);
                } else {
                    player.sendMessage("Your inventory and bank are full, see the Infinem guide in Lumbridge to claim your relic item: " + ItemDefinition.get(itemId).name);
                }
            }
        }
        player.lock();
        player.startEvent(e -> {
            player.animate(9208);
            player.graphics(2021);
            e.delay(11);
            player.unlock();
        });
        return true;
    }

    public boolean hasRelic(Relic relic) {
        return RELICS[relic.getTier()-1].get(player) == relic.getConfigValue();
    }

    public boolean hasRelicEnalbed(Relic relic) {
        return hasRelic(relic);
    }

    public void removeRelic(int tier) {
        if (tier < 0 || tier >= RELICS.length) return;
        RELICS[tier-1].set(player, 0);
    }

    public int getHighestTier() {
        for (int index = 0; index < RELICS.length; index++) {
            if (!hasRelicInTier(index + 1))
                return index;
        }
        return RELICS.length;
    }

    public int reclaimRelicItems() {
        List<Item> itemsToAdd = new ArrayList<>();
        for (Relic relic : Relic.values()) {
            if (relic.getItemIds().length > 0 && hasRelic(relic)) {
                for (int itemId : relic.getItemIds()) {
                    if (player.findItem(itemId, true) == null) {
                        itemsToAdd.add(new Item(itemId, 1));
                    }
                }
            }
        }
        if (itemsToAdd.isEmpty()) {
            return 0;
        }
        if (!player.getInventory().hasFreeSlots(itemsToAdd.size())) {
            return -itemsToAdd.size();
        }
        for (Item item : itemsToAdd) {
            player.getInventory().add(item);
        }
        return itemsToAdd.size();
    }

    public static int getPassiveStruct(int tier) {
        int structId = tier + 8999;
        StructDefinition def = StructDefinition.get(structId);
        if (def.getParams().containsKey(1019)) {
            return (int) def.getParams().get(1019);
        }
        return -1;
    }
}
