package io.ruin.model.skills.construction.room.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.*;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/1/2021
 */
public class PortalNexus {

    /**
     *  VB 6670 - type of nexus, 0/1 first, 2 second, 3 third
     *
     */
    public static void sendConfigure(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.PORTAL_NEXUS_CONFIGURE);
        player.getPacketSender().sendAccessMask(Interface.PORTAL_NEXUS_CONFIGURE, 22, 1, 30, AccessMasks.ClickOp1, AccessMasks.UseOnComponent, AccessMasks.ComponentTargetable, AccessMasks.DragDepth4, AccessMasks.DragTargetable);
        player.getPacketSender().sendAccessMask(Interface.PORTAL_NEXUS_CONFIGURE, 29, 0, 30, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(Interface.PORTAL_NEXUS_CONFIGURE, 39, 1, 30, AccessMasks.ClickOp1, AccessMasks.UseOnComponent, AccessMasks.ComponentTargetable, AccessMasks.DragDepth4, AccessMasks.DragTargetable);

    }
}
