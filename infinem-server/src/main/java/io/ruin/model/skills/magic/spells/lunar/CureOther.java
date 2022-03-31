package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

public class CureOther extends Spell {

    public CureOther() {
        Item[] runes = {
                Rune.LAW.toItem(1),
                Rune.ASTRAL.toItem(1),
                Rune.EARTH.toItem(10)
        };
        registerEntity(68, runes, (player, entity) -> {
            if (entity.player == null) {
                player.sendMessage("You can only use this spell on players.");
                return false;
            }
            if (entity.player.getDuel().stage >= 4) {
                player.sendMessage("That player can't be cured right now.");
                return false;
            }
            if (Config.ACCEPT_AID.get(entity.player) == 0) {
                player.sendMessage("That player is not accepting aid right now.");
                return false;
            }
            if (!entity.player.isPoisoned()) {
                player.sendMessage("They're not poisoned, so there is no need to cast this!");
                return false;
            }

            player.startEvent(event -> {
                player.lock();
                player.animate(4411);
                player.publicSound(2886);
                player.getStats().addXp(StatType.Magic, 65, true);
                event.delay(3);
                entity.graphics(738, 95, 0);
                entity.curePoison(0);
                entity.player.sendMessage(player.getName() + " has cured your poison.");
                player.unlock();
            });
            return true;
        });
    }

}