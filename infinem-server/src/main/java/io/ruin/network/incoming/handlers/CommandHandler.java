package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.*;
import io.ruin.model.World;
import io.ruin.model.entity.player.*;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.*;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.incoming.handlers.commands.*;
import io.ruin.services.Loggers;
import io.ruin.utility.IdHolder;

import java.io.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@IdHolder(ids = {21})//@IdHolder(ids = {17})
public class CommandHandler implements Incoming {

    public static final Bounds EDGEVILLE = new Bounds(3036, 3478, 3144, 3524, -1);

    //private static final Bounds MAGE_BANK = new Bounds(2527, 4708, 2551, 4727, 0);

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String query = in.readString();
        handle(player, query);
    }

    public static void handle(Player player, String query) {
        if((query = query.trim()).isEmpty())
            return;
        if(!query.contains("yell") && !query.contains("Yell"))
            player.sendFilteredMessage("<col=cc0000>::" + query);
        Loggers.logCommand(player.getUserId(), player.getName(), player.getIp(), query);
        if (player.isStaff()) {
            String format = String.format("Command:[Player:[%s] Position:%s IPAddress:[%s] Query:[%s]]", player.getName(), player.getPosition(), player.getIp(), query);
            ServerWrapper.log(format);
        }
        String command;
        String[] args;
        int spaceIndex = query.indexOf(' ');
        if(spaceIndex == -1) {
            command = query;
            args = null;
        } else {
            command = query.substring(0, spaceIndex);
            args = query.substring(spaceIndex + 1).split(" ");
        }
        try {
            command = command.toLowerCase();
            if(Administrator.handleAdmin(player, query, command, args))
                return;
            if(player.isLocked()) {
                player.sendMessage("Please finish what you're doing first.");
                return;
            }
            if(Support.handleSupport(player, query, command, args))
                return;
            if(Moderator.handleMod(player, query, command, args))
                return;
            if(SeniorModerator.handleSeniorMod(player, query, command, args))
                return;
            if(Regular.handleRegular(player, query, command, args))
                return;
            player.sendMessage("Sorry, that command does not exist.");
        } catch(Throwable t) {
            t.printStackTrace();
            if (player.isAdmin())
                player.sendMessage("Error handling command '" + query + "': " + t.getMessage());
        }
    }


    /*private static final List<String> enabledDevCmds = Arrays.asList(
            "item",
            "pickup",
            "empty",

            "fi",
            "fitem",

            "master",
            "lvl",

            "copyinv",
            "copyarm",

            "inter",
            "heal",
            "debug",

            "tele",
            "Bank"
    );*/

    /**
     * Utils
     */

    public static void forName(Player player, String cmdQuery, String exampleUsage, Consumer<String> consumer) {
        try {
            String name = cmdQuery.substring(cmdQuery.indexOf(" ") + 1).trim();
            consumer.accept(name);
        } catch(Exception e) {
            player.sendMessage("Invalid command usage. Example: [" + exampleUsage + "]");
        }
    }

    private static void forNameString(Player player, String cmdQuery, String exampleUsage, BiConsumer<String, String> consumer) {
        try {
            String s = cmdQuery.substring(cmdQuery.indexOf(" ") + 1).trim();
            int i = s.lastIndexOf(" ");
            String name = s.substring(0, i).trim();
            String string = s.substring(i).trim();
            consumer.accept(name, string);
        } catch(Exception e) {
            player.sendMessage("Invalid command usage. Example: [" + exampleUsage + "]");
        }
    }

    private static void forNameTime(Player player, String cmdQuery, String exampleUsage, BiConsumer<String, Long> consumer) {
        forNameString(player, cmdQuery, exampleUsage, (name, string) -> {
            try {
                if(string.equalsIgnoreCase("perm")) {
                    consumer.accept(name, -1L);
                    return;
                }
                long time = Long.valueOf(string.substring(0, string.length() - 1));
                String unit = string.substring(string.length() - 1).toLowerCase();
                if(unit.equals("h"))
                    time = TimeUtils.getHoursToMillis(time);
                else if(unit.equals("d"))
                    time = TimeUtils.getDaysToMillis(time);
                else
                    throw new IOException("Invalid time unit: " + unit);
                consumer.accept(name, System.currentTimeMillis() + time);
            } catch(Exception e) {
                ServerWrapper.logError("Invalid command usage. Example: [" + exampleUsage + "]", e);
            }
        });
    }

    public static void forPlayer(Player player, String cmdQuery, String exampleUsage, Consumer<Player> consumer) {
        forName(player, cmdQuery, exampleUsage, name -> {
            try {
                Player p = getOnlinePlayer(player, name);
                if(p != null)
                    consumer.accept(p);
            } catch(Exception e) {
                player.sendMessage("Invalid command usage. Example: [" + exampleUsage + "]");
                e.printStackTrace();
            }
        });
    }

    private static void forPlayerString(Player player, String cmdQuery, String exampleUsage, BiConsumer<Player, String> consumer) {
        forNameString(player, cmdQuery, exampleUsage, (name, string) -> {
            try {
                Player p = getOnlinePlayer(player, name);
                if(p != null)
                    consumer.accept(p, string);
            } catch(Exception e) {
                player.sendMessage("Invalid command usage. Example: [" + exampleUsage + "]");
            }
        });
    }

    public static void forPlayerInt(Player player, String cmdQuery, String exampleUsage, BiConsumer<Player, Integer> consumer) {
        forNameString(player, cmdQuery, exampleUsage, (name, string) -> {
            try {
                Player p = getOnlinePlayer(player, name);
                if(p != null)
                    consumer.accept(p, Integer.parseInt(string));
            } catch(Exception e) {
                player.sendMessage("Invalid command usage. Example: [" + exampleUsage + "]");
            }
        });
    }

    public static void forPlayerTime(Player player, String cmdQuery, String exampleUsage, BiConsumer<Player, Long> consumer) {
        forNameTime(player, cmdQuery, exampleUsage, (name, time) -> {
            try {
                Player p = getOnlinePlayer(player, name);
                if(p != null)
                    consumer.accept(p, time);
            } catch(Exception e) {
                player.sendMessage("Invalid command usage. Example: [" + exampleUsage + "]");
            }
        });
    }

    private static Player getOnlinePlayer(Player player, String name) {
        Player p = World.getPlayer(name);
        if(p == null)
            player.sendMessage("User '" + name + "' is not online.");
        return p;
    }

    public static void teleportDangerous(Player player, int x, int y, int z) {
        if (player.wildernessLevel != 0 || player.pvpAttackZone) {
            player.sendMessage("You can't use this command from where you are standing.");
            return;
        }
        player.dialogue(
                new MessageDialogue("<col=ff0000>Warning:</col> This teleport is inside the wilderness.<br> Are you sure you want to do this?").lineHeight(24),
                new OptionsDialogue(
                        new Option("Yes", () -> teleport(player, x, y, z)),
                        new Option("No")
                )
        );
    }

    public static void teleport(Player player, Position position) {
        teleport(player, position.getX(), position.getY(), position.getZ());
    }

    public static void teleport(Player player, int x, int y, int z) {
        if (player.wildernessLevel != 0 || player.pvpAttackZone) {
            player.sendMessage("You can't use this command from where you are standing.");
            return;
        }
        player.getMovement().startTeleport(event -> {
            player.animate(3864);
            player.graphics(1039);
            player.privateSound(200, 0, 10);
            event.delay(2);
            player.getMovement().teleport(x, y, z);
        });
    }

    private static void sendItems(Player player, ItemContainer container, int scriptId) {
        Object[] ids = new Object[container.getItems().length];
        StringBuilder sb = new StringBuilder(ids.length);
        for(int i = 0; i < ids.length; i++) {
            ids[i] = container.getId(i);
            sb.append("i");
        }
        player.getPacketSender().sendClientScript(scriptId, sb.toString(), ids);
    }
}