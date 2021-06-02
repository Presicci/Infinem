package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.StatType;

public class Curse extends TargetSpell {

    public Curse() {
        setLvlReq(19);
        setBaseXp(29.0);
        setMaxDamage(0);
        setAnimationId(1165);
        setCastGfx(108, 92, 0);
        setCastSound(127, 1, 0);
        setHitGfx(110, 124);
        setHitSound(125);
        setProjectile(new Projectile(109, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BODY.toItem(1), Rune.EARTH.toItem(3), Rune.WATER.toItem(2));
    }

    private static final StatType stat = StatType.Defence;
    private static final double percent = 0.05;

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