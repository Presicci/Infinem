package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.World;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class Dream extends Spell {

    public Dream() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.COSMIC.toItem(1),
                Rune.BODY.toItem(5)
        };
        clickAction = (player, i) -> {
            if (player.getHp() >= player.getMaxHp()) {
                player.sendMessage("You have no need to cast this spell since your hitpoints are already full.");
                return;
            }
            if (player.getTemporaryAttribute("nmz") != null) {
                player.sendMessage("You cannot perform Inception here.");
                return;
            }
            RuneRemoval r = RuneRemoval.get(player, runes);
            if (r == null) {
                player.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            r.remove();
            player.getStats().addXp(StatType.Magic, 76, true);
            player.startPersistableEvent(e -> {
                player.lock(LockType.MOVEMENT);
                int count = 0;
                player.animate(7627);
                while (true) {
                    e.delay(1);
                    player.animate(7627);
                    player.graphics(1056);
                    if (count >= 15) {
                        if (player.getHp() >= player.getMaxHp())
                            break;
                        player.incrementHp(1);
                        count = 0;
                    } else {
                        ++count;
                    }
                }
            }).setCancelCondition(() -> player.hasTemporaryAttribute("LOCKED_MOVEMENT"), () -> {
                player.startEvent(e -> {
                    player.animate(7628);
                    e.delay(1);
                    player.unlock();
                });
            });
        };
    }
}
