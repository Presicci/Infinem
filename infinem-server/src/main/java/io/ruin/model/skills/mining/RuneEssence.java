package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class RuneEssence {

    private static void mine(Player player, boolean darkEssence) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rock. You do not have a pickaxe which" +
                    " you have the Mining level to use."));
            return;
        }

        if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasFreeSlots(1) && player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory and bank are too full to hold any more rune stones.");
            return;
        } else if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more rune stones.");
            return;
        }

        if (!player.getInventory().hasId(Tool.CHISEL) && darkEssence) {
            player.sendMessage("You need a chisel to mine the rune stones.");
            return;
        }

        player.startEvent(event -> {
            event.delay(1);
            player.sendMessage("You swing your pick at the rock.");

            while (true) {
                if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasFreeSlots(1) && player.getInventory().isFull() && !darkEssence) {
                    player.privateSound(2277);
                    player.sendMessage("Your inventory and bank are too full to hold any more rune stones.");
                    player.resetAnimation();
                    return;
                } else if (player.getInventory().isFull()) {
                    player.privateSound(2277);
                    player.sendMessage("Your inventory is too full to hold any more rune stones.");
                    player.resetAnimation();
                    return;
                }

                if (darkEssence && player.getStats().get(StatType.Crafting).fixedLevel < 38) {
                    player.sendMessage("You need a Crafting level of least 38 to mine from this rock.");
                    return;
                }

                Stat stat = player.getStats().get(StatType.Mining);
                player.animate(pickaxe.regularAnimationID);
                event.delay(darkEssence ? ticks(stat.currentLevel, pickaxe) : 2);

                if (darkEssence) {
                    player.animate(7201);
                    event.delay(6);
                    player.getInventory().add(13445, 1);
                    player.getStats().addXp(StatType.Crafting, 8.0, true);
                    player.getStats().addXp(StatType.Mining, 12.0, true);
                    PlayerCounter.MINED_DARK_ESSENCE.increment(player, 1);
                } else {
                    boolean pure = stat.currentLevel >= 30;
                    PlayerCounter counter = pure ? PlayerCounter.MINED_PURE_ESSENCE : PlayerCounter.MINED_RUNE_ESSENCE;
                    int itemId = pure ? 7936 : 1436;
                    int amount = 1;
                    if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getBank().hasRoomFor(itemId)) {
                        amount *= 2;
                        player.getBank().add(itemId, amount);
                        player.sendFilteredMessage("Your Relic banks the " + ItemDef.get(itemId).name + " you would have gained, giving you a total of " + player.getBank().getAmount(itemId) + ".");
                    } else {
                        player.getInventory().add(itemId, amount);
                    }
                    counter.increment(player, amount);
                    player.getStats().addXp(StatType.Mining, 5 * amount, true);
                }
            }
        });
    }

    private static double power(int level, Pickaxe pickaxe) {
        double points = ((level - 1) + 1 + pickaxe.points);
        return (Math.min(80, points));
    }

    private static int ticks(int level, Pickaxe pickaxe) {
        double power = power(level, pickaxe);
        if (power > 50)
            return 2;
        if (power > 30)
            return 3;
        if (power > 15)
            return 4;
        return 5;
    }

    static {
        ObjectAction.register(34773, "mine", (p, obj) -> mine(p, false));
        ObjectAction.register(8981, 1, (p, obj) -> mine(p, true));
        ObjectAction.register(10796, 1, (p, obj) -> mine(p, true));
    }
}
