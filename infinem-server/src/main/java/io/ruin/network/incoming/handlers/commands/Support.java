package io.ruin.network.incoming.handlers.commands;

import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.handlers.CommandHandler;
import io.ruin.services.Punishment;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/27/2021
 */
public class Support {

    public static boolean handleSupport(Player player, String query, String command, String[] args) {
        if(!player.isSupport() && !player.isModerator() && !player.isAdmin())
            return false;
        switch(command) {
            case "tradepost": {
                player.getTradePost().openViewOffers();
                return true;
            }
            case "staffcommands": {
                break;
            }
            case "kick": {
                CommandHandler.forPlayer(player, query, "::kick playerName", p2 -> Punishment.kick(player, p2));
                return true;
            }
            case "jail": {
                CommandHandler.forPlayerInt(player, query, "::jail playerName rockCount", (p2, ores) -> Punishment.jail(player, p2, ores));
                return true;
            }
            case "unjail": {
                CommandHandler.forPlayer(player, query, "::unjail playerName", p2 -> Punishment.unjail(player, p2));
                return true;
            }
            case "mute": {
                CommandHandler.forPlayerTime(player, query, "::mute playerName #d/#h/perm", (p2, time) -> Punishment.mute(player, p2, time, false));
                return true;
            }
            case "unmute": {
                CommandHandler.forPlayer(player, query, "::unmute playerName", p2 -> Punishment.unmute(player, p2));
                return true;
            }
        }
        return false;
    }
}
