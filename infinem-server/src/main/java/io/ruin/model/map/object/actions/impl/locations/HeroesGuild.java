package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/23/2023
 */
public class HeroesGuild {

    private static void guildDoor(Player player, GameObject obj, int offset) {
        StatList stats = player.getStats();
        if (player.getPosition().getX() == 2902
                && (stats.get(StatType.Cooking).currentLevel < 53 || stats.get(StatType.Fishing).currentLevel < 53
                || stats.get(StatType.Herblore).currentLevel < 25 || stats.get(StatType.Mining).currentLevel < 50)) {
            player.dialogue(new MessageDialogue("You need 53 cooking, 53 fishing, 25 herblore, and 50 mining to enter the Heroes guild."));
            return;
        }
        PassableDoor.passDoor(player, obj, Direction.WEST, offset);
    }

    static {
        // Guild front door
        ObjectAction.register(2624, 2902, 3510, 0, "open", ((player, obj) -> guildDoor(player, obj, 2)));
        ObjectAction.register(2625, 2902, 3511, 0, "open", ((player, obj) -> guildDoor(player, obj, 0)));
    }
}
