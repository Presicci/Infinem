package io.ruin.model.combat.npc;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.skills.magic.spells.modern.elementaltype.FireSpell;
import io.ruin.model.skills.magic.spells.modern.elementaltype.WaterSpell;

import java.util.HashMap;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/26/2024
 */
public class Mummy extends NPCCombat {

    private static final HashMap<Integer, Integer> FIRE_IDS = new HashMap<Integer, Integer>() {{
        put(720, 725);
        put(721, 726);
        put(722, 727);
        put(723, 728);
    }};

    private int originalId;
    private boolean alight = false;

    @Override
    public void init() {
        originalId = npc.getId();
        if (FIRE_IDS.containsKey(originalId))
            npc.hitListener = new HitListener().postDamage(this::postDamage);
    }

    private void postDamage(Hit hit) {
        if (hit.attacker != null
                && hit.attacker.isPlayer()
                && hit.attackStyle.isMagic()) {
            if (!alight && hit.attackSpell instanceof FireSpell) {
                npc.transform(FIRE_IDS.get(originalId));
                alight = true;
                npc.deathEndListener = (entity, killer, killHit) -> {
                    npc.transform(originalId);
                };
            } else if (alight && hit.attackSpell instanceof WaterSpell) {
                npc.transform(originalId);
                alight = false;
            }
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }
}