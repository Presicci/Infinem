package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;

public class ForceTextUpdate extends UpdateMask {

    private String forceText;

    public void set(String forceText) {
        this.forceText = forceText;
    }

    @Override
    public void reset() {
        forceText = null;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return forceText != null;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        out.addString(forceText);
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 16 : 32;
    }

}
