package io.ruin.model.item.actions.impl.equipaction;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.UnequipAction;
import io.ruin.model.map.MapArea;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/30/2024
 */
public class GoldHelmet {

    static {
        UnequipAction.register(Items.GOLD_HELMET, player -> {
            if (MapArea.DONDAKANS_MINE.inArea(player)) {
                Traveling.fadeTravel(player, 2824, 10169, 0, () -> player.dialogue(new MessageDialogue("A magical force pulls you back to Keldagrim.")));
            }
        });
    }
}
