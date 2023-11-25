package io.ruin.model.combat.npc;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.skills.magic.spells.modern.EarthStrike;
import io.ruin.model.skills.magic.spells.modern.FireStrike;
import io.ruin.model.skills.magic.spells.modern.WaterStrike;
import io.ruin.model.skills.magic.spells.modern.WindStrike;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/25/2023
 */
public class SalarinTheTwisted extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    public void preDefend(Hit hit) {
        boolean block = false;
        if (hit.attacker != null && hit.attacker.player != null) {
            if (!hit.attackStyle.isMagic())
                block = true;
            if (!(hit.attackSpell instanceof WindStrike) && !(hit.attackSpell instanceof WaterStrike) && !(hit.attackSpell instanceof EarthStrike) && !(hit.attackSpell instanceof FireStrike))
                block = true;
            hit.maxDamage = 12;
            hit.minDamage = 9;
            if (block) {
                hit.block();
                hit.attacker.player.sendMessage("Salarin resists your attack.");
            }
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;

    }
}
