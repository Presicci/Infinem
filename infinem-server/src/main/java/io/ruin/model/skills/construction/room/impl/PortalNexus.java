package io.ruin.model.skills.construction.room.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/1/2021
 */
public class PortalNexus {

    public static void sendConfigure(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.PORTAL_NEXUS_CONFIGURE);
    }
}
