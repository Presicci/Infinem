package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WaterBlast extends WaterSpell {

    public WaterBlast() {
        setLvlReq(47);
        setBaseXp(28.5);
        setMaxDamage(14);
        setAnimationId(1162);
        setCastGfx(135, 92, 0);
        setCastSound(207, 1, 0);
        setHitGfx(137, 124);
        setHitSound(208);
        setProjectile(new Projectile(136, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.WATER.toItem(3), Rune.AIR.toItem(3));
        setAutoCast(10);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        if (hit.attacker.player != null)
            hit.attacker.player.getTaskManager().doLookupByUUID(146, 1);    // Cast a Blast Spell
    }
}