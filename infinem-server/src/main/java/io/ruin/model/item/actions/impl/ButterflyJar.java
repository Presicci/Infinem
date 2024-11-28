package io.ruin.model.item.actions.impl;

import io.ruin.cache.NpcID;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemPlayerAction;
import io.ruin.model.stat.StatType;
import io.ruin.network.incoming.handlers.FriendsHandler;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public enum ButterflyJar {
    RUBY_HARVEST(Items.RUBY_HARVEST, player -> player.getStats().get(StatType.Attack).boost(4, 0.15)),
    SAPPHIRE_GLACIALIS(Items.SAPPHIRE_GLACIALIS, player -> player.getStats().get(StatType.Defence).boost(4, 0.15)),
    SNOWY_KNIGHT(Items.SNOWY_KNIGHT, player -> player.incrementHp(15)),
    BLACK_WARLOCK(Items.BLACK_WARLOCK, player -> player.getStats().get(StatType.Strength).boost(4, 0.15)),
    SUNLIGHT_MOTH(28890, player -> {
        Consumable.restore(player, 6, 0.20, false);
        player.incrementHp(8);
    }),
    MOONLIGHT_MOTH(28893, player -> player.getStats().get(StatType.Prayer).restore(22));

    private final int itemId;
    private final Consumer<Player> buff;

    private void release(Player player, Item item) {
        item.setId(Items.BUTTERFLY_JAR);
        buff.accept(player);
    }

    private void shareRelease(Player player, Item item, Player other) {
        if (player.getDuel().stage >= 4) {
            player.sendMessage("You can't use this right now.");
            return;
        }
        if (!player.inMulti() || !other.inMulti()) {
            player.sendMessage("Both players need to be in multi-combat for this to work.");
            return;
        }
        if (player.wildernessLevel > 0 || other.wildernessLevel > 0) {
            player.sendMessage("You can't use butterflies in the wilderness.");
            return;
        }
        List<Player> players = new ArrayList<>();
        for (Entity target : player.localPlayers()) {
            if (players.size() >= 3) {
                break;
            }
            if (target.player == null) {
                continue;
            }
            if (target.player == player) {
                continue;
            }
            if (target.player.getPosition().distance(player.getPosition()) > 2) {
                continue;
            }
            if (Config.ACCEPT_AID.get(target.player) == 0) {
                continue;
            }
            if (target.player.getDuel().stage >= 4) {
                continue;
            }
            players.add(target.player);
        }
        //TODO animation?
        //TODO gfx?
        item.setId(Items.BUTTERFLY_JAR);
        buff.accept(player);
        for (Player p : players) {
            buff.accept(p);
        }
    }

    static {
        for (ButterflyJar jar : values()) {
            ItemAction.registerInventory(jar.itemId, "release", jar::release);
            ItemPlayerAction.register(jar.itemId, jar::shareRelease);
        }
    }
}
