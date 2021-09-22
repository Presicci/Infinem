package io.ruin.model.skills.prayer;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/17/2021
 */
@AllArgsConstructor
@Getter
public enum Ashes {
    FIENDISH(25766, 10, PlayerCounter.FIENDISH_ASHES_BURIED),
    VILE(25769, 25, PlayerCounter.VILE_ASHES_BURIED),
    MALICIOUS(25772, 65, PlayerCounter.MALICIOUS_ASHES_BURIED),
    ABYSSAL(25775, 85, PlayerCounter.ABYSSAL_ASHES_BURIED),
    INFERNAL(25778, 110, PlayerCounter.INFERNAL_ASHES_BURIED);

    private final int itemId;
    private final double experience;
    private final PlayerCounter playercounter;

    private void scatter(Player player, Item bone) {
        player.resetActions(true, false, true);
        player.startEvent(event -> {
            if(player.boneBuryDelay.isDelayed())
                return;
            bone.remove();
            //player.animate(827);  TODO find animation
            player.getStats().addXp(StatType.Prayer, experience, true);
            //player.privateSound(2738);    TODO find sounds
            playercounter.increment(player, 1);
            player.boneBuryDelay.delay(2);
            player.sendMessage("You scatter the ashes.");
        });
    }

    static {
        for(Ashes ashes : values()) {
            ItemAction.registerInventory(ashes.itemId, "scatter", ashes::scatter);
        }
    }

    public static Ashes get(int ashId) {
        for (Ashes a : values()) {
            if (ashId == a.itemId)
                return a;
        }
        return null;
    }
}
