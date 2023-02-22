package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.actions.edgeville.Bob;
import io.ruin.model.item.actions.ItemNPCAction;

import static io.ruin.cache.NpcID.BOB_2812;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/22/2023
 */
public class OttoGodblessed {
    static {
        /*
         * Zamorakian spear/hasta conversion
         */
        for(int id : new int[]{11824, 11889})
            ItemNPCAction.register(id, 2914, Bob::convertZamorakianWeapon);
    }
}
