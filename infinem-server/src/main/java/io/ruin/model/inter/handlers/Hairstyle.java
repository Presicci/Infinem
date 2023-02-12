package io.ruin.model.inter.handlers;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/12/2023
 */
public class Hairstyle {

    private static void open(Player player, NPC npc) {
        int gender = player.getAppearance().getGender();
        Config.HAIRDRESSDER.set(player, gender == 0 ? 1 : 3);
        player.openInterface(InterfaceType.MAIN, Interface.HAIRDRESSER);
        player.getPacketSender().sendAccessMask(Interface.HAIRDRESSER, 2, 0, 71, 2);
        player.getPacketSender().sendAccessMask(Interface.HAIRDRESSER, 8, 0, 24, 2);
    }

    static {
        //NPCAction.register(1305, "haircut", (Hairstyle::open));
    }
}
