package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public class WallSafe {

    public static final LootTable table = new LootTable().addTable(1,
            new LootItem(COINS_995, 50, 200, 295),   //coins
            new LootItem(1623, 1, 100),     //sapphire 1/5
            new LootItem(1621, 1, 25),      //emerald 1/20
            new LootItem(1619, 1, 15),      //ruby 1/30
            new LootItem(1617, 1, 8)      //diamond 1/60
    );

    private static void attempt(Player player, GameObject wallSafe) {
        if (!player.getStats().check(StatType.Thieving, 50, "attempt this"))
            return;
        if (player.getInventory().isFull()) {
            player.sendMessage("You don't have enough inventory space to do that.");
            return;
        }
        player.startEvent(event -> {
            if (!player.isAt(wallSafe.x, wallSafe.y))
                event.delay(1);
            player.privateSound(1243);
            player.sendFilteredMessage("You start cracking the safe...");
            player.animate(2247);
            event.delay(1);
            double chance = 0.35 + (double) (player.getStats().get(StatType.Thieving).currentLevel - 50) * 0.006;
            if (player.getInventory().contains(Items.STETHOSCOPE)) {  // Stethoscope gives the player a flat 10% boost
                if (player.getStats().get(StatType.Agility).fixedLevel >= 50
                        || player.getStats().get(StatType.Thieving).fixedLevel >= 99)
                    chance += 0.1;
                else
                    player.sendMessage("You need an agility level of 50 or a thieving level of 99 to use a stethoscope properly.");
            }
            int attempts = 0;
            while (attempts++ < 10) {
                player.animate(2248);
                if (Random.get() <= Math.min(chance, 0.75)) {
                    player.privateSound(1243);
                    player.animate(2249);
                    event.delay(2);
                    player.sendFilteredMessage("You get some loot.");
                    player.privateSound(1238);
                    player.getStats().addXp(StatType.Thieving, 70, true);
                    player.getInventory().add(getLoot(player));
                    if (Achievement.QUICK_HANDS.isFinished(player) && Random.rollPercent(10))
                        player.getInventory().add(getLoot(player));
                    PlayerCounter.WALL_SAFES_CRACKED.increment(player, 1);
                    openSafe(wallSafe);
                    break;
                }
                if (Random.rollDie(8)) {
                    player.lock();
                    player.privateSound(1242);
                    player.sendFilteredMessage("You slip and trigger a trap!");
                    player.hit(new Hit().randDamage(6));
                    player.animate(1113);
                    event.delay(2);
                    player.resetAnimation();
                    player.unlock();
                    break;
                }
                event.delay(1);
                player.animate(2248);
                event.delay(1);
                player.animate(2248);
                event.delay(1);
                player.animate(2248);
                event.delay(1);
            }
        });
    }

    private static void openSafe(GameObject wallSafe) {
        World.startEvent(event -> {
            wallSafe.setId(7238);
            event.delay(3);
            wallSafe.setId(7236);
        });
    }

    private static Item getLoot(Player player) {
        Item item = table.rollItem();
        if (item.getId() == COINS_995) {
            item.setAmount((int) (item.getAmount() * ((1 + (player.getStats().get(StatType.Thieving).fixedLevel - 49) * 0.02))));
        }
        return item;
    }

    static {
        ObjectAction.register(7236, "crack", WallSafe::attempt);
    }

}