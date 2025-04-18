package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.skills.magic.spells.modern.elementaltype.EarthSpell;

public class EarthBlast extends EarthSpell {

    public EarthBlast() {
        setLvlReq(53);
        setBaseXp(31.5);
        setMaxDamage(15);
        setAnimationId(1162);
        setCastGfx(138, 92, 0);
        setCastSound(128, 1, 0);
        setHitGfx(140, 124);
        setHitSound(129);
        setProjectile(new Projectile(139, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.EARTH.toItem(4), Rune.AIR.toItem(3));
        setAutoCast(11);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        if (hit.attacker.player != null)
            hit.attacker.player.getTaskManager().doLookupByUUID(146, 1);    // Cast a Blast Spell
    }
}