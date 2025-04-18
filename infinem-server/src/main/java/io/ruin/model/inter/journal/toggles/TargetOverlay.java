package io.ruin.model.inter.journal.toggles;

import io.ruin.Server;
import io.ruin.utility.Color;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Config;

public class TargetOverlay extends JournalEntry {

    @Override
    public void send(Player player) {
        if(player.getAttributeIntOrZero("TARGET_OVERLAY") == 3)
            send(player, "Target Overlay", "PvP & PvM", Color.GREEN);
        else if(player.getAttributeIntOrZero("TARGET_OVERLAY") == 2)
            send(player, "Target Overlay", "PvM", Color.GREEN);
        else if(player.getAttributeIntOrZero("TARGET_OVERLAY") == 1)
            send(player, "Target Overlay", "PvP", Color.GREEN);
        else
            send(player, "Target Overlay", "Disabled", Color.RED);
    }

    @Override
    public void select(Player player) {
        int setting = player.incrementNumericAttribute("TARGET_OVERLAY", 1);
        if(setting > 3)
            player.putAttribute("TARGET_OVERLAY", 0);
        set(player, player.getCombat().getTarget());
        send(player);
    }

    public static void set(Player player, Entity target) {
        if(target == null || target.getCombat() == null || target.getCombat().isDead())
            return;
        player.targetOverlayTarget = target;
        player.targetOverlayResetTicks = -2;

        // Resend proper config for XP counter to trigger render
        Config.XP_COUNTER_SHOWN.update(player);
    }

    public static void reset(Player player) {
        player.targetOverlayTarget = null;
        player.targetOverlayResetTicks = 10;
    }

    public static void process(Player player) {
        boolean visible = player.isVisibleInterface(596);
        Entity target = player.getCombat().getTarget();
        if(target == null) {
            if(++player.targetOverlayResetTicks < 10)
                target = player.targetOverlayTarget;
        }
        if(target != null && isEnabled(player, target)) {
            /**
             * Show
             */
            if(!visible)
                player.openInterface(InterfaceType.TARGET_OVERLAY, 596);
            if(player.targetOverlayResetTicks < 0) {
                player.targetOverlayResetTicks = 0;
                if(target.player != null)
                    player.getPacketSender().sendString(596, 5, target.player.getName());
                else
                    player.getPacketSender().sendString(596, 5, target.npc.getDef().name);
            }
            if(Server.isPast(target.hitsUpdate.removeAt)) {
                Config.TARGET_OVERLAY_CUR.set(player, target.getHp());
                Config.TARGET_OVERLAY_MAX.set(player, target.getMaxHp());
            } else {

                Config.TARGET_OVERLAY_CUR.set(player, target.hitsUpdate.curHp);
                Config.TARGET_OVERLAY_MAX.set(player, target.hitsUpdate.maxHp);
            }
        } else if(visible) {
            /**
             * Remove
             */
            player.targetOverlayTarget = null;
            player.closeInterface(InterfaceType.TARGET_OVERLAY);
        }
    }

    private static boolean isEnabled(Player player, Entity target) {
        if(player.getAttributeIntOrZero("TARGET_OVERLAY") == 3)
            return true;
        if(player.getAttributeIntOrZero("TARGET_OVERLAY") == 2)
            return target.npc != null;
        if(player.getAttributeIntOrZero("TARGET_OVERLAY") == 1)
            return target.player != null;
        return false;
    }

}