package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/8/2024
 */
public class NPCOverrideUpdate extends UpdateMask {
    private boolean resetModels;
    private int[] models;
    private int[] recolors;
    private int[] retextures;
    private int useLocalPlayer = -1;

    @Override
    public void reset() {
        resetModels = false;
        models = null;
        recolors = null;
        retextures = null;
        useLocalPlayer = -1;
    }

    @Override
    public boolean hasUpdate(boolean justAdded) {
        return resetModels != false || models != null || recolors != null || retextures != null || useLocalPlayer != -1;
    }

    private final static int MODELS_BIT = 1 << 1;
    private final static int RECOLORS_BIT = 1 << 2;
    private final static int RETEXTURES_BIT = 1 << 3;
    private final static int LOCAL_PLAYER_BIT = 1 << 4;

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        if (resetModels) {
            out.addByteNeg(1);
            return;
        }

        int updateFlag = 0;

        updateFlag |= (models != null) ? MODELS_BIT : 0;
        updateFlag |= (recolors != null) ? RECOLORS_BIT : 0;
        updateFlag |= (retextures != null) ? RETEXTURES_BIT : 0;
        updateFlag |= (useLocalPlayer != -1) ? LOCAL_PLAYER_BIT : 0;

        out.addByteAdd(updateFlag);

        if (models != null) {
            out.addByteAdd(models.length);

            for (int model : models) {
                out.addLEShort(model);
            }
        }

        if (recolors != null) {
            for (int recolor : recolors) {
                out.addLEShortAdd(recolor);
            }
        }

        if (retextures != null) {
            for (int retexture : retextures) {
                out.addShortAdd(retexture);
            }
        }

        if (useLocalPlayer != -1) {
            out.addByteSub(useLocalPlayer);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return 2048;
    }

    public void resetModels() {
        this.resetModels = true;
    }

    public NPCOverrideUpdate models(int[] models) {
        this.models = models;
        return this;
    }

    public NPCOverrideUpdate recolors(int[] recolors) {
        this.recolors = recolors;
        return this;
    }

    public NPCOverrideUpdate retextures(int[] retextures) {
        this.retextures = retextures;
        return this;
    }

    public NPCOverrideUpdate localPlayer(boolean useLocalPlayer) {
        this.useLocalPlayer = useLocalPlayer ? 1 : 0;
        return this;
    }
}
