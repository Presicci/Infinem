package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/28/2022
 */
public class VengeanceOther extends Spell {

    public VengeanceOther() {
        Item[] runes = {
                Rune.DEATH.toItem(2),
                Rune.ASTRAL.toItem(3),
                Rune.EARTH.toItem(10)
        };
        registerEntity(93, runes, VengeanceOther::cast);
    }

    public static boolean cast(Player player, Entity target) {
        if (target.player == null) {
            player.sendMessage("You can only cast this spell on other players.");
            return false;
        }
        if (target.player.vengeanceActive) {
            player.sendMessage("That player already has vengeance active.");
            return false;
        }
        if (target.player.getDuel().stage >= 4) {
            player.sendMessage("That player can't be vengeanced right now.");
            return false;
        }
        if (Config.ACCEPT_AID.get(target.player) == 0) {
            player.sendMessage("That player is not accepting aid right now.");
            return false;
        }
        if (Config.VENG_COOLDOWN.get(player) == 1) {
            player.sendMessage("Vengeance spells may only be cast every 30 seconds.");
            return false;
        }
        if (player.getStats().get(StatType.Defence).currentLevel < 40) {
            player.sendMessage("You need at least 40 defence to cast Vengeance Other.");
            return false;
        }
        player.resetActions(true, true, false);
        player.animate(4411);
        player.publicSound(2907, 1, 0);

        target.player.vengeanceActive = true;
        target.graphics(725, 92, 0);
        target.player.sendMessage(player.getName() + " has cast vengeance on you.");

        Config.VENG_COOLDOWN.set(player, 1);
        player.getPacketSender().sendWidget(Widget.VENGEANCE, 30);
        player.addTickEvent(new TickEvent(
                TickEventType.GENERIC_UNCHECKABLE_EVENT,
                50,
                () -> Config.VENG_COOLDOWN.set(player, 0)
        ));
        return true;
    }
}