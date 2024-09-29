package io.ruin.model.skills.mining;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class RuneEssence {

    private static void mine(Player player, boolean darkEssence) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rock. You do not have a pickaxe which" +
                    " you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }
        if (darkEssence && player.getStats().get(StatType.Crafting).fixedLevel < 38) {
            player.sendMessage("You need a Crafting level of least 38 to mine from this rock.");
            player.privateSound(2277);
            return;
        }
        if (!player.getInventory().hasId(Tool.CHISEL) && darkEssence) {
            player.sendMessage("You need a chisel to mine the rune stones.");
            player.privateSound(2277);
            return;
        }
        if (!player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getInventory().isFull()) {
            player.sendMessage("Your inventory is too full to hold any more rune stones.");
            player.privateSound(2277);
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(event -> {
            player.sendMessage("You swing your pick at the rock.");
            int loops = 0;
            while (true) {
                if (!player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST) && player.getInventory().isFull()) {
                    player.sendMessage("Your inventory is too full to hold any more rune stones.");
                    player.privateSound(2277);
                    player.resetAnimation();
                    return;
                }
                Stat stat = player.getStats().get(StatType.Mining);
                player.animate(pickaxe.regularAnimationID);
                event.delay(pickaxe.ticks);
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
                    if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                        amount *= 2;
                        if (player.getBank().hasRoomFor(itemId)) {
                            player.getBank().add(itemId, amount);
                            player.sendFilteredMessage("Your Relic banks the " + ItemDefinition.get(itemId).name + " you would have gained, giving you a total of " + player.getBank().getAmount(itemId) + ".");
                        } else {
                            player.getInventory().addOrDrop(itemId, amount);
                        }
                    } else {
                        player.getInventory().add(itemId, amount);
                    }
                    counter.increment(player, amount);
                    player.getStats().addXp(StatType.Mining, 5 * amount, true);
                }
                if (loops++ > 50) {
                    break;
                }
            }
        });
    }

    static {
        ObjectAction.register(34773, "mine", (p, obj) -> mine(p, false));
        ObjectAction.register(8981, 1, (p, obj) -> mine(p, true));
        ObjectAction.register(10796, 1, (p, obj) -> mine(p, true));
    }
}
