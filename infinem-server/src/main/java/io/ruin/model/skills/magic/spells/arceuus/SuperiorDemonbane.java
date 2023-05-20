package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/16/2023
 */
public class SuperiorDemonbane extends TargetSpell {

    public SuperiorDemonbane() {
        setLvlReq(62);
        setBaseXp(36);
        setMaxDamage(23);
        setAnimationId(8977);
        setCastGfx(1867, 0, 0);
        setHitGfx(1868, 0);
        setRunes(Rune.SOUL.toItem(1), Rune.FIRE.toItem(8));
        setAutoCast(54);
        setCastCheck((p, t) -> {
            if (t.isNpc() && t.npc.getDef().demon) {
                return true;
            }
            if (!p.isPlayer()) return false;
            p.player.sendMessage("Demonbane can only be cast on demons.");
            return false;
        });
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        super.beforeHit(hit, target);
        hit.boostAttack(0.20);
    }
}