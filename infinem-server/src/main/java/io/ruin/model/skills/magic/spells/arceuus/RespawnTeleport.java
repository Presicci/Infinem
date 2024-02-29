package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/28/2024
 */
public class RespawnTeleport extends Spell {

    public RespawnTeleport(int lvlReq, double xp, Item... runeItems) {
        registerClick(lvlReq, xp, true, runeItems, (p, i) -> teleport(p));
    }

    private static boolean teleport(Player player) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3865);
            player.graphics(1296);
            e.delay(3);
            player.getMovement().teleport(player.getRespawnPoint().getRandomPosition());
            player.getTaskManager().doLookupByUUID(147, 1); // Teleport Using Law Runes
        });
    }
}
