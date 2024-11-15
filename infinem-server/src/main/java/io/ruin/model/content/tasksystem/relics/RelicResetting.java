package io.ruin.model.content.tasksystem.relics;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/14/2024
 */
public class RelicResetting {

    static {
        ItemAction.registerInventory(32041, "inspect", (player, item) -> {
            if (!player.getRelicManager().hasRelicInTier(4)) {
                player.dialogue(new MessageDialogue("You do not currently have a tier 4 relic."));
                return;
            }
            player.dialogue(
                    new ItemDialogue().one(32041, "Consuming this tablet will reset your tier 4 combat relic. Would you like to continue?"),
                    new OptionsDialogue("Reset your tier 4 combat relic?",
                            new Option("Yes", () -> {
                                item.remove();
                                player.getRelicManager().removeRelic(4);
                                player.dialogue(new MessageDialogue("The tablet crumbles to dust as you feel your combat prowess fade completely."));
                            }),
                            new Option("No"))
            );
        });
    }
}
