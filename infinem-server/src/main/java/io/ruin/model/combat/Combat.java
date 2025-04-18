package io.ruin.model.combat;

import io.ruin.Server;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Bounds;
import io.ruin.model.stat.StatType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public abstract class Combat {

    /**
     * Death
     */

    @Setter
    private boolean dead;

    @Getter @Setter
    public boolean truelyDead;

    /**
     * Target
     */

    @Getter @Setter protected Entity target;

    /**
     * Attacking
     */

    private long lastAttackTick;

    private int lastAttackTickDelay;

    public int lastPlayerAttacked;

    private int attackDelayTicks;

    public void updateLastAttack(int tickDelay) {
        lastAttackTick = Server.currentTick();
        lastAttackTickDelay = tickDelay;
        lastPlayerAttacked = target == null || //honestly Idk how target can be null...
                target.player == null ? -1 : target.player.getUserId();
    }

    public void delayAttack(int ticks) {
        attackDelayTicks += ticks; //this needs to increment specifically for combo eating
    }

    public boolean isAttacking(int timeoutTicks) {
        return Server.currentTick() - lastAttackTick < timeoutTicks;
    }

    public boolean hasAttackDelay() {
        if(isAttacking(lastAttackTickDelay + attackDelayTicks))
            return true;
        attackDelayTicks = 0;
        return false;
    }

    public boolean canAnimateDefence(int animTicks) {
        if (!isAttacking(lastAttackTickDelay + attackDelayTicks) || animTicks <= lastAttackTick - Server.currentTick()) {
            return true;
        }
        return false;
    }

    /**
     * Defending
     */

    private long lastDefendTick;

    public boolean retaliating;

    public Entity lastAttacker;

    public void updateLastDefend(Entity attacker) {
        lastDefendTick = Server.currentTick();
        lastAttacker = attacker;
    }

    public boolean isDefending(long timeoutTicks) {
        return Server.currentTick() - lastDefendTick < timeoutTicks;
    }

    private static final Bounds EDGEVILLE_BOUNDS = new Bounds(2993, 3523, 3124, 3597, -1);

    public boolean allowPj(Entity attacker) {
        return attacker == lastAttacker
                || (lastAttacker != null && lastAttacker.isNpc() && attacker.isNpc() && attacker.npc.hasTemporaryAttribute("CAN_PJ"))
                || !isDefending(attacker.getPosition().inBounds(EDGEVILLE_BOUNDS) ? 16 : 8);
    }

    /**
     * @return Player can be attacked by all NPCs & by one single player
     */
    public boolean allowSinglePlus(Entity attacker, Entity target) {
        if (lastAttacker != null && lastAttacker.isPlayer() && target != lastAttacker)
            return false;
        if (lastAttacker != null && lastAttacker.isNpc())
            return false;
        return target.getCombat().lastAttacker == null || target.getCombat().lastAttacker.isNpc();
    }

    /**
     * Killers
     */

    public HashMap<Integer, Killer> killers = new HashMap<>();

    public void addKiller(Entity attacker, int damage) {
        if(attacker.player == null)
            return;
        if(killers == null)
            killers = new HashMap<>();
        int userId = attacker.player.getUserId();
        Killer k = killers.get(userId);
        if(k == null)
            k = new Killer();
        k.player = attacker.player;
        k.damage += damage;
        killers.put(userId, k);
    }

    public void resetKillers() {
        if(killers != null)
            killers.clear();
    }
    
    public Killer getKiller() {
        if(killers == null) {
            Server.logWarning("getKiller(): killers were null, no killer was selected!");
            return null;
        }
        Killer highestKiller = null;
        for(Killer killer : killers.values()) {
            if(highestKiller == null || killer.damage > highestKiller.damage)
                highestKiller = killer;
        }
        return highestKiller;
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * Reset
     */

    public void reset() {
        target = null;
    }

    /**
     * Abstract
     */

    public abstract boolean allowRetaliate(Entity attacker);

    public abstract void startDeath(Hit killHit);

    public abstract AttackStyle getAttackStyle();

    public abstract AttackType getAttackType();

    public abstract double getLevel(StatType statType);

    public abstract double getBonus(int bonusType);

    public abstract int getDefendAnimation();

    public abstract int getDefendSound();

    public abstract double getDragonfireResistance();

    public abstract void faceTarget();
}