package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.StatType;

public class Confuse extends TargetSpell {

    public Confuse() {
        setLvlReq(3);
        setBaseXp(13.0);
        setMaxDamage(0);
        setAnimationId(1163);
        setCastGfx(102, 92, 0);
        setCastSound(119, 1, 0);
        setHitGfx(104, 200);
        setHitSound(121);
        setProjectile(new Projectile(103, 36, 31, 61, 56, 10, 16, 64));
        setRunes(Rune.BODY.toItem(1), Rune.EARTH.toItem(2), Rune.WATER.toItem(3));
    }

    private static final StatType stat = StatType.Attack;
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