package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.StatType;

public class Enfeeble extends TargetSpell {

    public Enfeeble() {
        setLvlReq(73);
        setBaseXp(83.0);
        setMaxDamage(0);
        setAnimationId(1168);
        setCastGfx(170, 92, 0);
        setCastSound(148, 1, 0);
        setHitGfx(172, 124);
        setHitSound(150);
        setProjectile(new Projectile(171, 36, 31, 48, 56, 10, 16, 64));
        setRunes(Rune.SOUL.toItem(1), Rune.EARTH.toItem(8), Rune.WATER.toItem(8));
    }

    private static final StatType stat = StatType.Strength;
    private static final double percent = 0.10;

    @Override
    public boolean cast(Entity entity, Entity target) {
        if(target.player != null ? target.player.getStats().get(stat).isDrained()
                : target.npc.getCombat().getStat(stat).isDrained()) {
            entity.player.sendMessage("That target's " + stat.toString().toLowerCase() + " is already drained.");
            return false;
        }
        int statLevel = target.player != null ? target.player.getStats().get(stat).fixedLevel
                : target.npc.getCombat().getStat(stat).fixedLevel;
        if((int) (statLevel * percent) == 0) {
            entity.player.sendMessage("That target's " + stat.toString().toLowerCase() + " is too low to drain.");
            return false;
        }
        return super.cast(entity, target);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        if(!hit.isBlocked()) {
            if (target.player != null) {
                target.player.getStats().get(stat).drain(percent);
            } else {
                target.npc.getCombat().getStat(stat).drain(percent);
            }
        }
        hit.hide();
    }
}