package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.SpellSack;
import io.ruin.model.skills.magic.spells.modern.elementaltype.FireSpell;

public class FireWave extends FireSpell {

    protected SpellSack getSpellSack() {
        return SpellSack.SURGE;
    }

    public FireWave() {
        setLvlReq(75);
        setBaseXp(42.5);
        setMaxDamage(20);
        setAnimationId(1167);
        setCastGfx(155, 92, 0);
        setCastSound(162, 1, 0);
        setHitGfx(157, 124);
        setHitSound(163);
        setProjectile(new Projectile(156, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(1), Rune.FIRE.toItem(7), Rune.AIR.toItem(5));
        setAutoCast(16);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        if (hit.attacker.player != null)
            hit.attacker.player.getTaskManager().doLookupByUUID(213, 1);    // Cast a Wave Spell
    }
}