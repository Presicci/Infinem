package io.ruin.model.skills.construction.actions;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.ItemDef;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/16/2024
 */
public class Menagerie {

    static {
        EnumDefinition map = EnumDefinition.get(985);
        StringBuilder sb = new StringBuilder();
        for(int key : map.keys) {
            int itemId = map.intValues[key];
            ItemDef def = ItemDef.get(itemId);
            if(def.pet == null) {
                System.err.println(def.name + " (" + itemId + ") pet not supported!");
                continue;
            }
            sb.append(def.name).append("|");
        }
        String s = sb.toString();
        ObjectAction.register(Buildable.PET_LIST.getBuiltObjects()[0], "read", (player, obj) -> {
            player.openInterface(InterfaceType.MAIN, 210);
            player.getPacketSender().sendClientScript(647, "s", s);
        });
    }
}
