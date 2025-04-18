package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.SpellSack;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.skills.magic.spells.modern.elementaltype.EarthSpell;

public class EarthWave extends EarthSpell {

    protected SpellSack getSpellSack() {
        return SpellSack.SURGE;
    }

    public EarthWave() {
        setLvlReq(70);
        setBaseXp(40.0);
        setMaxDamage(19);
        setAnimationId(1167);
        setCastGfx(164, 92, 0);
        setCastSound(134, 1, 0);
        setHitGfx(166, 124);
        setHitSound(135);
        setProjectile(new Projectile(165, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(1), Rune.EARTH.toItem(7), Rune.AIR.toItem(5));
        setAutoCast(15);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        if (hit.attacker.player != null)
            hit.attacker.player.getTaskManager().doLookupByUUID(213, 1);    // Cast a Wave Spell
    }
}