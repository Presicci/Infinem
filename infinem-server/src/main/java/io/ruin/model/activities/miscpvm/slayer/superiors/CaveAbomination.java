package io.ruin.model.activities.miscpvm.slayer.superiors;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.prayer.Prayer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class CaveAbomination extends Superior {

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (target.player != null && target.player.getEquipment().getId(Equipment.SLOT_AMULET) != 8923
                && !target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
            npc.animate(4237);
            target.hit(new Hit(npc, AttackStyle.MAGIC).fixedDamage(target.getMaxHp() / 10).ignoreDefence().ignorePrayer());
            if (target.player != null) {
                target.player.sendMessage("<col=ff0000>The cave horror's scream rips through you!");
                target.player.sendMessage("<col=ff0000>A witchwood icon can protect you from this attack.");
            }
        } else
            basicAttack();
        return true;
    }
}
