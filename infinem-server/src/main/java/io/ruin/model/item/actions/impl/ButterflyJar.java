package io.ruin.model.item.actions.impl;

import io.ruin.cache.NpcID;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemPlayerAction;
import io.ruin.model.stat.StatType;
import io.ruin.network.incoming.handlers.FriendsHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ButterflyJar {
    RUBY_HARVEST(Items.RUBY_HARVEST, NpcID.RUBY_HARVEST),
    SAPPHIRE_GLACIALIS(Items.SAPPHIRE_GLACIALIS, NpcID.SAPPHIRE_GLACIALIS),
    SNOWY_KNIGHT(Items.SNOWY_KNIGHT, NpcID.SNOWY_KNIGHT),
    BLACK_WARLOCK(Items.BLACK_WARLOCK, NpcID.BLACK_WARLOCK);

    private final int itemId, npcId;

    private static final int BUTTERFLY_JAR = Items.BUTTERFLY_JAR;

    static {
        for (ButterflyJar jar : values()) {
            ItemAction.registerInventory(jar.itemId, "release", (player, item) -> {
                item.setId(Items.BUTTERFLY_JAR);
                //TODO animation?
            });
            ItemPlayerAction.register(jar.itemId, (player, item, other) -> {
                if (!player.inMulti() || !other.inMulti()) {
                    player.sendMessage("Both players need to be in multi-combat for this to work.");
                    return;
                }
                if (player.wildernessLevel > 0 || other.wildernessLevel > 0) {
                    player.sendMessage("You can't use butterflies in the wilderness.");
                    return;
                }
                //TODO check if player is on friend's list
                //TODO animation?
                //TODO gfx?
                item.setId(BUTTERFLY_JAR);
                switch (jar) {
                    case RUBY_HARVEST:
                        other.getStats().get(StatType.Attack).boost(4, 0.15);
                        break;
                    case SAPPHIRE_GLACIALIS:
                        other.getStats().get(StatType.Defence).boost(4, 0.15);
                        break;
                    case SNOWY_KNIGHT:
                        other.incrementHp(15);
                        break;
                    case BLACK_WARLOCK:
                        other.getStats().get(StatType.Strength).boost(4, 0.15);
                        break;
                }
            });
        }
    }
}
