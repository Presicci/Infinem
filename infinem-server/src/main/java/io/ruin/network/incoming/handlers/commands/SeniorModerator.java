package io.ruin.network.incoming.handlers.commands;

import io.ruin.content.activities.tournament.TournamentManager;
import io.ruin.content.activities.tournament.TournamentPlaylist;
import io.ruin.model.World;
import io.ruin.model.activities.pvp.leaderboard.DeepWildernessPker;
import io.ruin.model.activities.pvp.leaderboard.EdgePker;
import io.ruin.model.activities.pvp.leaderboard.Leaderboard;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.skills.slayer.SlayerMaster;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/27/2021
 */
public class SeniorModerator {

    public static boolean handleSeniorMod(Player player, String query, String command, String[] args) {
        if (!player.isAdmin())
            return false;
        switch (command) {
            case "hp": {
                int amount = Integer.parseInt(args[0]);
                player.setHp(amount);
                player.sendMessage("HP set to " + amount + ".");
                return true;
            }

            case "hide": {
                if(player.isHidden()) {
                    player.setHidden(false);
                    player.sendMessage("You are now visible.");
                } else {
                    player.setHidden(true);
                    player.sendMessage("You are now hidden.");
                }
                return true;
            }

            case "resetslayertask": {
                Player p2 = World.getPlayer(String.join(" ", args));
                if(p2 == null) {
                    player.sendMessage("Player can't be found.");
                    return true;
                }
                Slayer.resetTask(p2);
                p2.sendMessage("Your slayer task has been reset.");
                player.sendMessage("You have reset " + p2.getName() + "'s task.");
                return true;
            }

            case "removeedgepker":
                String playerName = query.substring(command.length() + 1);
                for (EdgePker killas : Leaderboard.edgePkers.values()) {
                    if (playerName.equalsIgnoreCase(killas.getPlayer().getName())) {
                        Leaderboard.edgePkers.remove(killas.getPlayer().getUserId());
                    }
                }
                return true;

            case "removedeeppker":
                String playerNameDeep = query.substring(command.length() + 1);
                for (DeepWildernessPker killas : Leaderboard.deepWildernesPkers.values()) {
                    if (playerNameDeep.equalsIgnoreCase(killas.getPlayer().getName())) {
                        Leaderboard.deepWildernesPkers.remove(killas.getPlayer().getUserId());
                    }
                }
                return true;

            case "toggledmm":
                if (TournamentManager.activeTournament == null) {
                    player.sendMessage("Error setting tournament configs without a valid tournament active.");
                } else {
                    TournamentManager.activeTournament.setAttributes(TournamentPlaylist.DMM_TRIBRID.getAttributes());
                    player.sendMessage("Set tournament preset config to DMM type.");
                }
                return true;

            case "enabletourneyfee":
                TournamentManager.requireFee = true;
                player.sendMessage("The tournament will now require a fee to participate.");
                return true;

            case "disabletourneyfee":
                TournamentManager.requireFee = false;
                player.sendMessage("The tournament will no longer require a fee to participate.");
                return true;

            case "ttime":
                int mins = Integer.parseInt(args[0]);
                if (mins <= 0) {
                    player.sendMessage("You must set a value greater than 0 to set the tournament time (in mins).");
                } else {
                    TournamentManager.activityTimer = mins;
                    player.sendMessage("The tournament will now begin in "+ mins +" mins.");
                }
                return true;

            case "endtournament":
                if (TournamentManager.activeTournament == null) {
                    player.sendMessage("There is no tournament active to end.");
                } else {
                    TournamentManager.activeTournament.end(true);
                }
                return true;

            case "toggletournament":
                TournamentManager.enabled = !TournamentManager.enabled;
                player.sendMessage("The tournament is now "+ (TournamentManager.enabled ? "enabled" : "disabled") +".");
                return true;

            case"bmb":
            case "bmboost": {
                int multiplier = Integer.parseInt(args[0]);
                if(multiplier < 1) {
                    player.sendMessage("Blood money multiplier cannot be less than 1.");
                    multiplier = 1;
                } else if(multiplier > 4) {
                    player.sendMessage("Blood money multiplier cannot be greater than 4.");
                    multiplier = 4;
                }
                World.boostBM(multiplier);
                return true;
            }

            case "getbmboost": {
                player.sendMessage("The bloody money multiplier is currently: " + World.bmMultiplier);
                return true;
            }

            case "xpb":
            case "xpboost": {
                int multiplier = Integer.parseInt(args[0]);
                if(multiplier < 1) {
                    player.sendMessage("Experience multiplier cannot be less than 1.");
                    multiplier = 1;
                } else if(multiplier > 4) {
                    player.sendMessage("Experience multiplier cannot be greater than 4.");
                    multiplier = 4;
                }
                World.boostXp(multiplier);
                return true;
            }

            case "setbasebm": {
                int base = Integer.parseInt(args[0]);
                if(base < 1) {
                    player.sendMessage("Base Blood Money cannot be less than 1.");
                    base = 1;
                } else if(base > 150) {
                    player.sendMessage("Base Blood Money cannot be greater than 150.");
                    base = 150;
                }
                World.setBaseBloodMoney(base);
                return true;
            }


            case "doublexpweekend": {
                World.toggleWeekendExpBoost();
                return true;
            }
            case "doubledrops": {
                World.toggleDoubleDrops();
                return true;
            }

            case "doubleslayer": {
                World.toggleDoubleSlayer();
                return true;
            }

            case "doublepc": {
                World.toggleDoublePest();
                return true;
            }

            case "togglewildernesskeyevent": {
                World.toggleWildernessKeyEvent();
                boolean active = World.wildernessKeyEvent;
                player.sendMessage("The wilderness key event is now " + (active ? "enabled" : "disabled") + ".");
                return true;
            }

            case "toggledmmkeyevent": {
                World.toggleDmmKeyEvent();
                boolean active = World.wildernessDeadmanKeyEvent;
                player.sendMessage("The DMM key event is now " + (active ? "enabled" : "disabled") + ".");
                return true;
            }
        }
        return false;
    }
}
