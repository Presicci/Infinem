package io.ruin.model.activities.miscpvm;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.actions.impl.jewellery.EfaritaysAid;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/24/2023
 */
public class Vampyre extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    private boolean isSilver(String name) {
        name = name.toLowerCase();
        return name.contains("silver") || name.contains("ivandis") || name.contains("blisterwood");
    }

    public void preDefend(Hit hit) {
        boolean block = false;
        if (hit.attacker != null && hit.attacker.player != null) {
            if (hit.attackStyle.isMelee() && (hit.attackWeapon == null || !isSilver(hit.attackWeapon.name))) {
                block = true;
            } else if (hit.attackStyle.isRanged() && (hit.rangedAmmo == null || !isSilver(hit.rangedAmmo.name))) {
                block = true;
            } else if (hit.attackStyle.isMagic()) {
                block = true;
            }
            if (block && EfaritaysAid.test(hit.attacker.player)) {
                block = false;
                hit.maxDamage = 10;
            }
            if (block) {
                hit.block();
                hit.attacker.player.sendMessage("Vampyres can only be harmed by silver weapons.");
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
