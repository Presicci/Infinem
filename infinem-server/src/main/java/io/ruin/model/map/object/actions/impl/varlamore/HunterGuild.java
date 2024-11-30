package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/30/2024
 */
public class HunterGuild {

    private static boolean check(Player player, boolean message) {
        if (player.getStats().check(StatType.Hunter, 46)) return true;
        if (message) player.dialogue(new MessageDialogue("You need a hunter level of 46 to use the guild's amenities."));
        return false;
    }

    private static void takeLog(Player player, GameObject object) {
        if (!player.getInventory().hasId(Tool.KNIFE)) {
            player.sendMessage("You need a knife to break off the logs.");
            return;
        }
        if (player.getInventory().getAmount(Items.LOGS) >= 3) {
            player.sendMessage("You already have some logs. Taking more could risk the stability of the cavern.");
            return;
        }
        if (!player.getInventory().hasRoomFor(Items.LOGS)) {
            player.sendMessage("You don't have enough inventory space to take a log.");
            return;
        }
        player.sendMessage("");
        player.getInventory().add(Items.LOGS);
    }

    static {
        ObjectAction.register(51641, 1556, 3048, 0, "climb-down", (player, obj) -> {
            if (!check(player, true)) return;
            Traveling.fadeTravel(player, 1557, 9451, 0);
        });
        ObjectAction.register(51746, "take-log", HunterGuild::takeLog);
    }
}
