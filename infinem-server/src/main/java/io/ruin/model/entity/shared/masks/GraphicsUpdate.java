package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class GraphicsUpdate extends UpdateMask {

    @AllArgsConstructor
    public static class SpotAnim {
        public int id, height, delay;
    }

    private final Map<Integer, SpotAnim> pending = new HashMap<>();

    public void set(int id, int height, int delay) {
        set(0, id, height, delay);
    }

    public void set(int slot, int id, int height, int delay) {
        pending.put(slot, new SpotAnim(id, height, delay));
    }

    @Override
    public void reset() {
        pending.clear();
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return !pending.isEmpty();
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        if (playerUpdate) {
            out.addByte(pending.size());
            for (Map.Entry<Integer, SpotAnim> entry : pending.entrySet()) {
                SpotAnim spotAnim = entry.getValue();
                out.addByte(entry.getKey());
                out.addShortAdd(spotAnim.id);
                out.addLEInt(spotAnim.delay | (spotAnim.height << 16));
            }
        } else {
            out.addByteAdd(pending.size());
            for (Map.Entry<Integer, SpotAnim> entry : pending.entrySet()) {
                SpotAnim spotAnim = entry.getValue();
                out.addByteSub(entry.getKey());
                out.addShortAdd(spotAnim.id);
                out.addInt(spotAnim.height << 16 | spotAnim.delay);
            }
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 65536 : 262144;
    }

}