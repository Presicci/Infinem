package io.ruin.model.content.transportation;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.TeleportTab;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/9/2023
 */
public class DorgeshKaanSphere {

    static {
        NPCAction.register(2303, "buy-sphere", (player, npc) -> npc.openShop(player));
    }
}
