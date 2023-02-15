package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/28/2022
 */
public class HealGroup extends Spell {

    public HealGroup() {
        Item[] runes = {
                Rune.ASTRAL.toItem(4),
                Rune.BLOOD.toItem(3),
                Rune.LAW.toItem(6)
        };
        registerClick(95, 124, true, runes, ((player, integer) -> {
            List<Player> players = new ArrayList<>();
            for (Entity target : player.localPlayers()) {
                if (players.size() >= 5) {
                    break;
                }
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
                if (target.getHp() >= target.getMaxHp()) {
                    continue;
                }
                players.add(target.player);
            }
            if(player.getDuel().stage >= 4) {
                player.sendMessage("You can't use this right now.");
                return false;
            }
            if (!player.inMulti()) {
                player.sendMessage("This spell can only be cast in multi-combat zones.");
                return false;
            }
            if (players.size() == 0) {
                player.sendMessage("There are no players in range to heal.");
                return false;
            }
            if (player.getHp() < (player.getMaxHp() / 9)) {
                player.sendMessage("You need more health to cast this spell.");
                return false;
            }
            int health = (int) (player.getHp() * .75);
            Hit hit = new Hit().fixedDamage(health).ignoreDefence().ignorePrayer().ignoreAbsorption();
            player.hit(hit);
            player.animate(4409);
            player.publicSound(2886);
            player.sendMessage("You transfer most of your health to those around you.");
            for (Player target : players) {
                target.graphics(744, 120, 0);
                target.player.sendMessage(player.getName() + " has healed you.");
                target.incrementHp(health / players.size());
            }
            return true;
        }));
    }
}
