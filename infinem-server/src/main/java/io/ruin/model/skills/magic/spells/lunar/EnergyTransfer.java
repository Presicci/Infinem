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
 * Created on 9/30/2024
 */
public class EnergyTransfer extends Spell {

    public EnergyTransfer() {
        Item[] runes = {
                Rune.NATURE.toItem(1),
                Rune.LAW.toItem(2),
                Rune.ASTRAL.toItem(3)
        };
        registerEntity(91, runes, (player, entity) -> {
            if (entity.isNpc()) {
                player.sendMessage("You can only use this spell on players.");
                return false;
            }
            if (entity.player.wildernessLevel > 0 && !entity.player.inMulti()) {
                player.sendMessage("This player is in a PvP single way combat zone, and you cannot cast this spell.");
                return false;
            }
            if (entity.player.getDuel().stage >= 4) {
                player.sendMessage("This player can't be helped right now.");
                return false;
            }
            if (Config.ACCEPT_AID.get(entity.player) == 0) {
                player.sendMessage("This player is not accepting aid right now.");
                return false;
            }
            if (player.getHp() < 11) {
                player.sendMessage("You need more hitpoints to cast this spell.");
                return false;
            }
            if (Config.SPECIAL_ENERGY.get(player) < 1000) {
                player.sendMessage("You need a special attack bar with full energy.");
                return false;
            }
            if (!Misc.withinReach(player.getPosition(), entity.getPosition(), 11)) {
                player.sendMessage("I can't reach that player.");
                return false;
            }

            player.startEvent(event -> {
                player.lock();
                player.hit(new Hit().fixedDamage(10));
                player.animate(4411);
                player.getStats().addXp(StatType.Magic, 100, true);
                event.delay(4);
                entity.graphics(734, 95, 0);
                Config.SPECIAL_ENERGY.set(player, 0);
                Config.SPECIAL_ENERGY.set(entity.player, 1000);
                entity.player.sendMessage(player.getName() + " has transferred energy to you.");
                player.unlock();
            });
            return true;
        });
    }

}