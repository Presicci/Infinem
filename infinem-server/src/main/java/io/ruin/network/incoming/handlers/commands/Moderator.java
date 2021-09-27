package io.ruin.network.incoming.handlers.commands;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.handlers.CommandHandler;
import io.ruin.services.Punishment;

public class Moderator {

    public static boolean handleMod(Player player, String query, String command, String[] args) {
        if(!player.isModerator() && !player.isAdmin())
            return false;
        switch(command) {
            case "unlock": {
                CommandHandler.forPlayer(player, query, "::unlock playerName", p2 -> {
                    if(!p2.isLocked()) {
                        player.sendMessage(p2.getName() + " is not locked.");
                    } else {
                        p2.unlock();
                        player.sendMessage("Unlocked " + p2.getName() + ".");
                    }
                });
                return true;
            }
            case "ban": {
                CommandHandler.forPlayer(player, query, "::ban playerName", p2 -> Punishment.ban(player, p2));
                return true;
            }
            case "removedicerank": {
                String name = query.substring(command.length() + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null)
                    player.sendMessage("Could not find player: " + name);
                else
                    p2.diceHost = false;
                return true;
            }
            case "teleto": {
                String name = query.substring(command.length() + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null)
                    player.sendMessage("Could not find player: " + name);
                else {
                    if(p2.isAdmin() && !player.isAdmin()) {
                        player.sendMessage("You can't teleport to an administrator.");
                        p2.sendMessage(player.getName() + " has just attempted to teleport to you.");
                        return false;
                    }
                    if(p2.joinedTournament) {
                        player.sendMessage("You can't teleport to a player who's in a tournament.");
                        return false;
                    }
                    player.getMovement().teleport(p2.getPosition());
                }
                return true;
            }
            case "teletome": {
                String name = query.substring(command.length() + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null)
                    player.sendMessage("Could not find player: " + name);
                else {
                    if(p2.isAdmin() && !player.isAdmin()) {
                        player.sendMessage("You can't teleport an administrator to you.");
                        p2.sendMessage(player.getName() + " has just attempted to teleport you to them.");
                        return false;
                    }
                    if(player.joinedTournament) {
                        player.sendMessage("You can't do this while inside a tournament.");
                        return false;
                    }
                    p2.getMovement().teleport(player.getPosition());
                }
                return true;
            }

        }
        return false;
    }
}
