package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.UpdateMask;

public class EntityDirectionUpdate extends UpdateMask {

    public Entity target;
    private boolean update;
    private int stage;

    public void set(Entity target, boolean temporary) {
        this.target = target;
        this.stage = (temporary ? 1 : 0);
        this.update = true;
        setSent(false);
    }

    public void remove(boolean delay) {
        if(delay) {
            /**
             * Delay Remove
             */
            stage = 1;
            setSent(true);
        } else {
            /**
             * Remove
             */
            set(null, false);
        }
    }

    @Override
    public void reset() {
        update = false;
        if(stage == 1) {
            /**
             * Queue remove
             */
            stage = 2;
            setSent(true);
        } else if(stage == 2) {
            /**
             * Remove
             */
            remove(false);
        }
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return update || (added && target != null);
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        int index = 0xFFFFFF;
        if (target != null) {
            index = target.getClientIndex();
        }
        if (playerUpdate) {
            out.addLEShortAdd(index & 0xffff);
            out.addByteNeg(index >> 16);
        } else {
            out.addLEShortAdd(index & 0xffff);
            out.addByteNeg(index >> 16);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 64 : 16;
    }

}
