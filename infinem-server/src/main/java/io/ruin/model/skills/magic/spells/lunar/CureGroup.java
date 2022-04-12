package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.Entity;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/28/2022
 */
public class CureGroup extends Spell {

    public CureGroup() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.COSMIC.toItem(2),
                Rune.LAW.toItem(2)
        };
        registerClick(74, 74, true, runes, ((player, integer) -> {
            if (player.getDuel().stage >= 4) {
                player.sendMessage("You can't cast this right now.");
                return true;
            }
            for (Entity target : player.localPlayers()) {
                if (target.player == null) {
                    continue;
                }
                if (target.player == player) {
                    continue;
                }
                if (target.player.getPosition().distance(player.getPosition()) > 1) {
                    continue;
                }
                if (Config.ACCEPT_AID.get(target.player) == 0) {
                    continue;
                }
                if (target.player.getDuel().stage >= 4) {
                    continue;
                }
                if (!target.isPoisoned()) {
                    continue;
                }
                target.graphics(744, 120, 0);
                target.curePoison(0);
                target.player.sendMessage(player.getName() + " has cured your poison.");
            }
            player.startEvent(e -> {
                player.lock();
                player.animate(4409);
                e.delay(2);
                if (player.isPoisoned()) {
                    player.curePoison(0);
                    player.sendMessage("You have cured your poison.");
                }
                player.publicSound(2886);
                player.graphics(744, 120, 0);
                e.delay(1);
                player.unlock();
            });
            return true;
        }));
    }
}
