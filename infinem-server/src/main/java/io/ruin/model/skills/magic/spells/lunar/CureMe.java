package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class CureMe extends Spell {

    public CureMe() {
        Item[] runes = {
                Rune.LAW.toItem(1),
                Rune.ASTRAL.toItem(2),
                Rune.COSMIC.toItem(2)
        };
        registerClick(71, 69, true, runes, (player, integer) -> {
            if(player.getDuel().stage >= 4) {
                player.sendMessage("You can't be cured right now.");
                return false;
            }
            if(!player.isPoisoned()) {
                player.sendMessage("You're not poisoned, so there is no need to cast this!");
                return false;
            }
            player.startEvent(e -> {
                player.lock();
                player.animate(4411);
                e.delay(1);
                player.publicSound(2886);
                player.graphics(748, 120, 0);
                player.curePoison(0);
                player.sendMessage("You have cured your poison.");
                e.delay(1);
                player.unlock();
            });
            return true;
        });
    }

}
