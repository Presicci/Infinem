package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.modern.elementaltype.FireSpell;

public class FireBlast extends FireSpell {

    public FireBlast() {
        setLvlReq(59);
        setBaseXp(34.5);
        setMaxDamage(16);
        setAnimationId(1162);
        setCastGfx(129, 92, 0);
        setCastSound(155, 1, 0);
        setHitGfx(131, 124);
        setHitSound(156);
        setProjectile(new Projectile(130, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.FIRE.toItem(5), Rune.AIR.toItem(4));
        setAutoCast(12);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        if (hit.attacker.player != null)
            hit.attacker.player.getTaskManager().doLookupByUUID(146, 1);    // Cast a Blast Spell
    }
}