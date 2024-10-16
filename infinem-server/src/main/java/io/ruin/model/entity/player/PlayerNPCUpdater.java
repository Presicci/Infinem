package io.ruin.model.entity.player;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.ServerPacket;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.UpdateMask;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerNPCUpdater {

    private static final int MAX_LOCAL = 250, MAX_ADD_PER_CYCLE = 25;

    private final Player player;

    protected ArrayList<NPC> localNpcs = new ArrayList<>(MAX_LOCAL);

    private final OutBuffer maskBuffer = new OutBuffer(0xff);

    @Setter
    @Getter
    private int maxDistance = 14;

    public PlayerNPCUpdater(Player player) {
        this.player = player;
    }

    /**
     * Process
     */

    public void process() {
        player.getPacketSender().sendNPCUpdateOrigin();
        OutBuffer out = new OutBuffer(0xff)
                .sendVarShortPacket(isLargeView()
                        ? ServerPacket.NPC_INFO_LARGE.getPacketId()
                        : ServerPacket.NPC_INFO_SMALL.getPacketId())
                .initBitAccess();
        /**
         * Local (Updating visible npcs)
         */
        out.addBits(8, localNpcs.size());
        if (!localNpcs.isEmpty()) {
            for (Iterator<NPC> it = localNpcs.iterator(); it.hasNext(); ) {
                NPC npc = it.next();
                if (!writeLocalUpdate(out, npc)) {
                    it.remove();
                    npc.removePlayer(player);
                }
            }
        }
        /**
         * Global (Making visible npcs local)
         */
        int toAdd = MAX_LOCAL - localNpcs.size();
        if (toAdd > 0) {
            toAdd = Math.min(MAX_ADD_PER_CYCLE, toAdd);
            for (NPC npc : World.npcs) {
                if (writeGlobalUpdate(out, npc)) {
                    localNpcs.add(npc);
                    npc.addPlayer(player);
                    if (--toAdd == 0)
                        break;
                }
            }
        }
        /**
         * Write
         */
        if (maskBuffer.position() > 0) {
            out.addBits(16, 65535)
                    .finishBitAccess();
            out.addBytes(maskBuffer.payload(), 0, maskBuffer.position());
            maskBuffer.position(0);
        } else {
            out.finishBitAccess();
        }
        player.getPacketSender().write(out);
    }

    /**
     * Local Updating
     */

    private boolean writeLocalUpdate(OutBuffer out, NPC npc) {
        if (npc == null || npc.isHidden() || !npc.canPlayerSee(player) || npc.getMovement().hasTeleportUpdate() || !npc.getPosition().isWithinDistance(player.getPosition(), getMaxDistance())) {
            /**
             * Remove
             */
            out.addBits(1, 1);
            out.addBits(2, 3);
            return false;
        }
        int maskData = npc.getUpdateMaskData(false, false);
        int walkDirection = npc.getMovement().walkDirection;
        int runDirection = npc.getMovement().runDirection;
        if (runDirection != -1) {
            /**
             * Run
             */
            out.addBits(1, 1);
            out.addBits(2, 2);
            out.addBits(1, 1);
            out.addBits(3, walkDirection);
            out.addBits(3, runDirection);
            if (maskData != 0) {
                out.addBits(1, 1);
                writeMasks(npc, maskData);
            } else {
                out.addBits(1, 0);
            }
            return true;
        }
        if (walkDirection != -1) {
            /**
             * Walk
             */
            out.addBits(1, 1);
            /*if(crawl){ TODO
                out.addBits(2, 2);
                out.addBits(1, 0);
            }else {*/
            out.addBits(2, 1);
            out.addBits(3, walkDirection);
            if (maskData != 0) {
                out.addBits(1, 1);
                writeMasks(npc, maskData);
            } else {
                out.addBits(1, 0);
            }
            return true;
        }
        if (maskData != 0) {
            /**
             * Masks only
             */
            out.addBits(1, 1);
            out.addBits(2, 0);
            writeMasks(npc, maskData);
            return true;
        }
        out.addBits(1, 0); //no update
        return true;
    }

    /**
     * Global Updating
     */

    private boolean writeGlobalUpdate(OutBuffer out, NPC npc) {
        if (npc.isHidden() || !npc.canPlayerSee(player) || !npc.getPosition().isWithinDistance(player.getPosition(), maxDistance) || localNpcs.contains(npc))
            return false;
        int maskData = npc.getUpdateMaskData(false, true);
        int diffX = npc.getPosition().getX() - player.getPosition().getX();
        int diffY = npc.getPosition().getY() - player.getPosition().getY();
        if (diffX < 0)
            diffX += isLargeView() ? 256 : 32;
        if (diffY < 0)
            diffY += isLargeView() ? 256 : 32;
        out.addBits(16, npc.getIndex());
        if (maskData != 0) {
            out.addBits(1, 1);
        } else {
            out.addBits(1, 0);
        }
        out.addBits(1, npc.getMovement().hasTeleportUpdate() ? 1 : 0);
        out.addBits(isLargeView() ? 8 : 5, diffY);
        out.addBits(1, 0);
        out.addBits(14, npc.getId());
        out.addBits(3, npc.spawnDirection.ordinal());
        out.addBits(isLargeView() ? 8 : 5, diffX);
        if (maskData != 0) {
            writeMasks(npc, maskData);
        }
        return true;
    }

    /**
     * Masks
     */

    private void writeMasks(NPC npc, int maskData) {
        int smallMask = 32;
        int largeMask = 512;

        if ((maskData & ~0xFF) != 0) {
            maskData |= smallMask;
        }
        if ((maskData & ~0xFFFF) != 0) {
            maskData |= largeMask;
        }

        maskBuffer.addByte(maskData);
        if ((maskData & smallMask) != 0) {
            maskBuffer.addByte(maskData >>> 8);
        }
        if ((maskData & largeMask) != 0) {
            maskBuffer.addByte(maskData >>> 16);
        }

        for (UpdateMask updateMask : npc.getMasks()) {
            if ((maskData & updateMask.get(false)) != 0) {
                updateMask.send(maskBuffer, false);
                updateMask.setSent(true);
            }
        }
    }

    public boolean isLargeView() {
        return maxDistance > 14;
    }
}