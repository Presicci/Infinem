package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class TeleportToHouse extends Spell {

    public static final Item[] RUNES = {Rune.LAW.toItem(1), Rune.EARTH.toItem(1), Rune.AIR.toItem(1)};
    public static final int LVL_REQ = 40;
    public static final int XP = 30;

    public TeleportToHouse() {
        registerClick(LVL_REQ, XP, true, RUNES, TeleportToHouse::teleport);
    }

    public static boolean teleport(Player player, Integer i) {
        if (player.house == null) {
            player.sendMessage("You don't have a house to teleport to.");
            return false;
        }
        boolean inside = i == 0 || i == 2;
        if (player.isInOwnHouse() && inside) {
            player.sendMessage("You're already in your house!");
            return false;
        }
        return player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(2);
            player.getTaskManager().doLookupByUUID(147, 1); // Teleport Using Law Runes
            if (inside)  {
                player.house.buildAndEnter(player, false);
                while (player.isLocked())
                    e.delay(1);
            } else {
                e.delay(1);
                player.getMovement().teleport(player.house.getLocation().getPosition());
            }
        });
    }
}
