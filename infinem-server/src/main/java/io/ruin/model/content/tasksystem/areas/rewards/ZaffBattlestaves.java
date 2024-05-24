package io.ruin.model.content.tasksystem.areas.rewards;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DailyResetListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/24/2024
 */
public class ZaffBattlestaves {

    private static final String KEY = "ZAFF_STAVES";

    private static void claimStaves(Player player) {
        if (!AreaReward.ZAFF_BATTLESTAVES_15.checkReward(player, "purchase discounted battlestaves from Zaff.")) return;
        int maxStaves = AreaReward.ZAFF_BATTLESTAVES_120.hasReward(player) ? 120
                : AreaReward.ZAFF_BATTLESTAVES_60.hasReward(player) ? 60
                : AreaReward.ZAFF_BATTLESTAVES_30.hasReward(player) ? 30 : 15;
        int claimedStaves = player.getAttributeIntOrZero(KEY);
        if (claimedStaves >= maxStaves) {
            player.timeTillDailyReset("You've purchased as many staves as you can for the day.<br><br>");
            return;
        }
        int purchaseAmt = maxStaves - claimedStaves;
        int cost = purchaseAmt * 7000;
        player.dialogue(
                new MessageDialogue("Buy " + purchaseAmt + " discounted battlestaves for 7,000 coints each?<br><br>" +
                        "Total cost: " + NumberUtils.formatNumber(cost) + " coins"),
                new OptionsDialogue(
                        new Option("Yes.", () -> {
                            if (player.getInventory().getAmount(995) < cost) {
                                player.dialogue(
                                        new MessageDialogue("You don't have enough coins to purchase the battlestaves.")
                                );
                                return;
                            }
                            if (player.getInventory().getAmount(995) > cost && !player.getInventory().hasRoomFor(Items.BATTLESTAFF_NOTE)) {
                                player.dialogue(
                                        new MessageDialogue("You don't have enough inventory space to purchase the battlestaves.")
                                );
                                return;
                            }
                            player.getInventory().remove(995, cost);
                            player.getInventory().add(Items.BATTLESTAFF_NOTE, purchaseAmt);
                            player.incrementNumericAttribute(KEY, purchaseAmt);
                        }),
                        new Option("No.")
                )
        );
    }

    static {
        ObjectAction.register(30357, new Position(3201, 3437), 1, (player, obj) -> claimStaves(player));
        DailyResetListener.register(player -> player.removeAttribute(KEY));
    }
}
