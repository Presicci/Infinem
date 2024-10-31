package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/8/2024
 */
public class ModelRecolorUpdate extends UpdateMask {

    private int startTick = -1;
    private int endTick = -1;
    private int hue = -1;
    private int saturation = -1;
    private int lightness = -1;
    private int alpha = -1;

    public void set(int startTick, int endTick, int hue, int saturation, int lightness, int alpha) {
        this.startTick = startTick;
        this.endTick = endTick;
        this.hue = hue;
        this.saturation = saturation;
        this.lightness = lightness;
        this.alpha = alpha;
    }

    @Override
    public void reset() {
        this.startTick = -1;
        this.endTick = -1;
        this.hue = -1;
        this.saturation = -1;
        this.lightness = -1;
        this.alpha = -1;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return this.startTick != -1;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        if (playerUpdate) {
            out.addLEShortAdd(startTick);
            out.addShortAdd(endTick);
            out.addByte(hue);
            out.addByteAdd(saturation);
            out.addByteSub(lightness);
            out.addByteSub(alpha);
        } else {
            out.addShortAdd(startTick);
            out.addLEShortAdd(endTick);
            out.addByteAdd(hue);
            out.addByteSub(saturation);
            out.addByte(lightness);
            out.addByteAdd(alpha);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 1024 : 32768;
    }
}