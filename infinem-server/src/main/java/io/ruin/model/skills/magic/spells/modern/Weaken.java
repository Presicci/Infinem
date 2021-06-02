package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.StatType;

public class Weaken extends TargetSpell {

    public Weaken() {
        setLvlReq(11);
        setBaseXp(21.0);
        setMaxDamage(0);
        setAnimationId(1164);
        setCastGfx(105, 92, 0);
        setCastSound(3011, 1, 0);
        setHitGfx(107, 200);
        setHitSound(3010);
        setProjectile(new Projectile(106, 36, 31, 44, 56, 10, 16, 64));
        setRunes(Rune.BODY.toItem(1), Rune.EARTH.toItem(2), Rune.WATER.toItem(3));
    }

    @Override
    public boolean cast(Entity entity, Entity target) {
        if(target.player != null ? target.player.getStats().get(StatType.Strength).isDrained()
                : target.npc.getCombat().getStat(StatType.Strength).isDrained()) {
            entity.player.sendMessage("That targets strength is already drained." + target.player.getStats().get(StatType.Strength).currentLevel + "/" + target.player.getStats().get(StatType.Strength).fixedLevel);
            return false;
        }
        return super.cast(entity, target);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        hit.attacker.player.sendMessage("Before: " + target.player.getStats().get(StatType.Strength).currentLevel);
        if(hit.isBlocked()) {
            hit.attacker.player.getStats().addXp(StatType.Magic, 21, false);
        } else {
            hit.attacker.player.getStats().addXp(StatType.Magic, 21, false);
            if (target.player != null) {
                target.player.getStats().get(StatType.Strength).drain(0.05);
            } else {
                target.npc.getCombat().getStat(StatType.Strength).drain(0.05);
            }
        }
        hit.attacker.player.sendMessage("After: " + target.player.getStats().get(StatType.Strength).currentLevel);
        hit.hide();
    }
}