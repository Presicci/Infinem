package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.combat.Hit;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/1/2024
 */
public class HealOther extends Spell {

    public HealOther() {
        Item[] runes = {
                Rune.BLOOD.toItem(1),
                Rune.LAW.toItem(3),
                Rune.ASTRAL.toItem(3)
        };
        registerEntity(92, runes, (player, entity) -> {
            if (entity.isNpc()) {
                player.sendMessage("You can only use this spell on players.");
                return false;
            }
            if (player.getHp() < (int) (player.getMaxHp() * 0.11)) {
                player.sendMessage("You need at least 11% of your hitpoints to cast this spell.");
                return false;
            }
            if (entity.player.getDuel().stage >= 4) {
                player.sendMessage("This player can't be healed right now.");
                return false;
            }
            if (Config.ACCEPT_AID.get(entity.player) == 0) {
                player.sendMessage("This player is not accepting aid right now.");
                return false;
            }
            if (entity.getHp() >= entity.getMaxHp()) {
                player.sendMessage(entity.player.getName() + " doesn't require any more hitpoints.");
                return false;
            }
            if (!Misc.withinReach(player.getPosition(), entity.getPosition(), 11)) {
                player.sendMessage("I can't reach that player.");
                return false;
            }
            int maxHeal = (int) (player.getHp() * 0.75);
            int reqHeal = entity.getMaxHp() - entity.getHp();
            if (reqHeal > maxHeal) {
                player.hit(new Hit().fixedDamage(maxHeal));
                entity.player.incrementHp(maxHeal);
            } else {
                player.hit(new Hit().fixedDamage(reqHeal));
                entity.player.incrementHp(reqHeal);
            }
            player.animate(4411);
            player.getStats().addXp(StatType.Magic, 101, true);
            entity.player.graphics(736, 95, 0);
            entity.player.sendMessage(player.getName() + " has healed you.");
            return true;
        });
    }

}