package io.ruin.model.skills.slayer;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/11/2025
 */
public class SlayerVIPTicket {

    static {
        ItemAction.registerInventory(32058, "inspect", (player, item) -> player.dialogue(
                new ItemDialogue().one(32058, "Take this ticket to a slayer master to select the task you'd like to be assigned! Consumed in the process.")));
    }
}
