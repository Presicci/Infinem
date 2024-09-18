package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/17/2024
 */
public class BlastFurnaceCoffer {

    private static final Config COFFER_FILLED = Config.varpbit(5356, true);
    private static final Config COFFER = Config.varpbit(5357, true);

    protected static int getAmount(Player player) {
        return COFFER.get(player);
    }

    protected static boolean hasAmount(Player player, int amount) {
        return COFFER.get(player) > amount;
    }

    protected static void decrementCofferAmount(Player player, int amount) {
        int newAmt = COFFER.increment(player, -amount);
        if (newAmt <= 0) {
            COFFER_FILLED.set(player, 0);
            player.sendMessage("Your coffer has run out of coins.");
        }
    }

    protected static void incrementCofferAmount(Player player, int amount) {
        int newAmt = COFFER.increment(player, amount);
        COFFER_FILLED.set(player, 1);
    }

    private static void deposit(Player player) {
        player.integerInput("Amount to deposit:", i -> {
            int inventoryCoins = player.getInventory().getAmount(995);
            if (inventoryCoins == 0) {
                player.dialogue(new MessageDialogue("You do not have any coins to deposit into the coffer."));
                return;
            }
            if (i > inventoryCoins) {
                i = inventoryCoins;
            }
            int cofferCoins = COFFER.get(player);
            if ((long) cofferCoins + inventoryCoins >= Integer.MAX_VALUE) {
                i = Integer.MAX_VALUE - cofferCoins;
                if (i == 0) {
                    player.dialogue(new MessageDialogue("Your coffer is full."));
                    return;
                }
            }
            player.getInventory().remove(995, i);
            incrementCofferAmount(player, i);
            BlastFurnace.processBars(player);
        });
    }

    private static void withdraw(Player player) {
        if (player.getBankPin().requiresVerification(BlastFurnaceCoffer::withdraw))
            return;
        player.integerInput("Amount to withdraw:", i -> {
            int coinsStored = COFFER.get(player);
            int amountToWithdraw = Math.min(coinsStored, i);
            int inventoryAmount = player.getInventory().getAmount(995);
            if ((long) inventoryAmount + amountToWithdraw > Integer.MAX_VALUE) {
                amountToWithdraw = Integer.MAX_VALUE - inventoryAmount;
                if (amountToWithdraw <= 0) {
                    player.dialogue(new MessageDialogue("You have too many coins in your inventory to withdraw from the coffer."));
                    return;
                }
            }
            player.getInventory().addOrDrop(995, amountToWithdraw);
            int newAmt = COFFER.increment(player, -amountToWithdraw);
            if (newAmt <= 0) COFFER_FILLED.set(player, 0);
        });
    }

    private static void useCoffer(Player player) {
        if (COFFER.get(player) == 0) {
            if (player.getInventory().hasId(995)) {
                deposit(player);
            } else {
                player.dialogue(new MessageDialogue("There are no coins in the coffer or your inventory."));
            }
            return;
        }
        if (player.getInventory().getAmount(995) <= 0) {
            withdraw(player);
        } else {
            player.dialogue(new OptionsDialogue("Current coffer: " + COFFER.get(player),
                    new Option("Deposit", () -> deposit(player)),
                    new Option("Withdraw", () -> withdraw(player))
            ));
        }
    }

    static {
        ObjectAction.register(29330, 1, (player, obj) -> useCoffer(player));
    }
}
