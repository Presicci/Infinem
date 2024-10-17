package io.ruin.model.entity.shared.masks;

import io.ruin.Server;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

public class HitsUpdate extends UpdateMask {

    /**
     * Ratio calc
     */
    private static final int[][] RATIO_DATA = {
            {30, 30},       //0
            {100, 100},     //1
            {120, 120},     //2
            {160, 160},     //3
            {120, 120},     //4
            {60, 60},       //5
            {100, 100},     //6
            {100, 100},     //7
            {120, 120},     //8
    };

    private final ArrayList<Splat> splats = new ArrayList<>(4);

    public int hpBarType;

    private int hpBarRatio;

    public int curHp, maxHp;

    public long removeAt;

    private boolean forceSend = false;

    private final Entity defender;

    public HitsUpdate(Entity entity) {
        defender = entity;
    }

    public void add(Hit hit, int curHp, int maxHp) {
        if (splats.size() < 6)
            splats.add(new Splat(hit.type.activeId, hit.type.tintedId, hit.damage, 0, hit.attacker));
        this.hpBarRatio = toRatio(hpBarType, curHp, maxHp);
        this.curHp = curHp;
        this.maxHp = maxHp;
        this.removeAt = Server.getEnd(10);
    }


    /**
     * For showing HP bar without any hits
     */
    public void forceSend(int curHp, int maxHp) {
        hpBarRatio = toRatio(hpBarType, curHp, maxHp);
        forceSend = true;
    }

    @Override
    public void reset() {
        splats.clear();
        forceSend = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return forceSend || !splats.isEmpty();
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        if (playerUpdate)
            out.addByteNeg(splats.size());
        else
            out.addByteSub(splats.size()); // ?
        boolean tinting = receivingPlayer != null && Config.HITSPLAT_TINTING.get(receivingPlayer) == 0;
        for (Splat splat : splats) {
            if (!tinting || (splat.attacker == receivingPlayer || defender == receivingPlayer)) {
                out.addSmart(splat.id);
            } else {
                out.addSmart(splat.tintedId);
            }
            out.addSmart(splat.damage);
            out.addSmart(splat.delay);
        }

        if (playerUpdate)
            out.addByteAdd(1); // Hp bar count
        else
            out.addByteAdd(1); // Hp bar count ?
        out.addSmart(hpBarType);
        out.addSmart(0); // Second bar type
        out.addSmart(0); // Hp bar delay
        if (playerUpdate) {
            out.addByte(hpBarRatio);
            // if second smart is greater than 0, out.addByteS
        } else
            out.addByte(hpBarRatio);
            // if second smart is greater than 0, out.addByteA
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 128 : 128;
    }

    @AllArgsConstructor
    private static final class Splat {
        private final int id, tintedId, damage, delay;
        private final Entity attacker;
    }

    private static int toRatio(int type, int min, int max) {
        int ratio = (Math.min(min, max) * RATIO_DATA[type][0]) / max;
        if (ratio == 0 && min > 0)
            ratio = 1;
        return ratio;
    }

}