package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/8/2024
 */
public class CombatLevelUpdate extends UpdateMask {

    private int level = -1;

    public void set(int level) {
        this.level = level;
    }

    @Override
    public void reset() {
        level = -1;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return level != -1;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        out.addLEInt(level);
    }

    @Override
    public int get(boolean playerUpdate) {
        return 16384;
    }
}