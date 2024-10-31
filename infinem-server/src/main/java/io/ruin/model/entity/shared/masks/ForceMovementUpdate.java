package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;

public class ForceMovementUpdate extends UpdateMask {

    private int diffX1, diffY1;
    private int diffX2, diffY2;
    private int speed1;
    private int speed2;
    private int direction;
    public boolean updated = false;

    public void set(int diffX1, int diffY1, int diffX2, int diffY2, int speed1, int speed2, int direction) {
        this.diffX1 = diffX1;
        this.diffY1 = diffY1;
        this.diffX2 = diffX2;
        this.diffY2 = diffY2;
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.direction = direction;
        this.updated = true;
    }

    @Override
    public void reset() {
        updated = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return updated;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        if (playerUpdate) {
            out.addByteSub(diffX1);
            out.addByte(diffY1);
            out.addByteNeg(diffX2);
            out.addByte(diffY2);
            out.addShort(speed1);
            out.addShortAdd(speed2);
            out.addLEShort(direction);
        } else {
            out.addByteSub(diffX1);
            out.addByteAdd(diffY1);
            out.addByte(diffX2);
            out.addByteNeg(diffY2);
            out.addLEShort(speed1);
            out.addLEShort(speed2);
            out.addShort(direction);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 256 : 4096;
    }

}