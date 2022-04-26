package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WindSurge extends TargetSpell {

    public WindSurge() {
        setLvlReq(81);
        setBaseXp(44.5);
        setMaxDamage(21);
        setAnimationId(7855);
        setCastGfx(1455, 92, 0);
        setCastSound(162, 1, 0);
        setHitGfx(1457, 124);
        setProjectile(new Projectile(1456, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.AIR.toItem(7), Rune.WRATH.toItem(1));
        setAutoCast(48);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        if (hit.attacker.player != null)
            hit.attacker.player.getTaskManager().doLookupByUUID(214, 1);    // Cast a Surge Spell
    }
}