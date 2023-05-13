package io.ruin.network.incoming.handlers;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.EnumMap;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.PacketSender;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.ruin.model.inter.Interface.COMBAT_OPTIONS;

@SuppressWarnings("Duplicates")
@IdHolder(ids = {24})//@IdHolder(ids = {72})
public class DisplayHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int displayMode = in.readByte();
        int canvasWidth = in.readShort();
        int canvasHeight = in.readShort();
        if(!player.hasDisplay()) {
            sendDisplay(player, displayMode);
            player.start();
            player.setOnline(true);
        } else {
            setDisplayMode(player, displayMode);
        }
    }

    public static final int DEFAULT_SCREEN_CHILD_OFFSET = 7;

    public static void setDisplayMode(Player p, int displayMode) {
        if (p.getDisplayMode() == displayMode) return;
        p.setDisplayMode(displayMode);
        PacketSender ps = p.getPacketSender();

        Map<Integer, Integer> oldComponents = getToplevelComponents(p).ints();

        ps.sendGameFrame(getGameFrameFor(displayMode));
        ps.sendClientScript(3998, "i", displayMode - 1);
        switch (displayMode) {
            case 2:
                Config.SIDE_PANELS.set(p, 0);
                break;
            case 3:
                Config.SIDE_PANELS.set(p, 1);
                break;
        }
        Map<Integer, Integer> newComponents = getToplevelComponents(p).ints();
        moveSubInterfaces(oldComponents, newComponents, p);
        //ps.sendAccessMask(Interface.QUEST, 7, 0, 19, 2);
        //ps.sendAccessMask(Interface.QUEST, 8, 0, 118, 2);
        //ps.sendAccessMask(Interface.QUEST, 9, 0, 11, 2);
        //ps.sendAccessMask(Interface.OPTIONS, 106, 1, 4, 2);
        //ps.sendAccessMask(Interface.OPTIONS, 107, 1, 4, 2);
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 47, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 665, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 184, 0, 4, 2);

        ps.sendClientScript(3970, "IIi", 46661634, 46661635, 1);
        Config.varpbit(12933, false).set(p, 1); // if 1 then it shows time played.

        Config.varp(1780, false).set(p, 432); // Membership days left
    }

    private static void sendDisplay(Player player, int displayMode) {
        PacketSender ps = player.getPacketSender();
        ps.sendGameFrame(165);
        ps.sendInterface(162, 165, 1, 1);
        ps.sendInterface(163, 165, 2, 1);

        int tabChildOffset = DEFAULT_SCREEN_CHILD_OFFSET;
        if(Config.DATA_ORBS.get(player) == 0)
            ps.sendInterface(160, 165, 24 + tabChildOffset, 1);

        if(Config.XP_COUNTER_SHOWN.get(player) == 1)
            ps.sendInterface(122, 165, 4 + tabChildOffset, 1);

        //ps.sendInterface(378, 165, 28, 0); //welcome screen pt1
        //ps.sendInterface(50, 165, 27, 0); //welcome screen pt2

        ps.sendInterface(Interface.SKILLS, 165, 9 + tabChildOffset, 1);
        ps.sendInterface(Interface.QUEST_TAB, 165, 10 + tabChildOffset, 1);
        ps.sendInterface(Interface.QUEST, Interface.QUEST_TAB, 33, 1);
        //ps.sendInterface(729, Interface.QUEST_TAB, 2, 1);
        ps.sendInterface(Interface.INVENTORY, 165, 11 + tabChildOffset, 1);
        ps.sendInterface(Interface.EQUIPMENT, 165, 12 + tabChildOffset, 1);
        ps.sendInterface(Interface.PRAYER, 165, 13 + tabChildOffset, 1);
        ps.sendInterface(Interface.MAGIC_BOOK, 165, 14 + tabChildOffset, 1);


        //ps.sendInterface(432, 165, 16, 1);

        ps.sendInterface(Interface.ACCOUNT_MANAGEMENT, 165, 16 + tabChildOffset, 1);
        ps.sendInterface(Config.FRIENDS_AND_IGNORE_TOGGLE.get(player) == 0 ? Interface.FRIENDS_LIST : Interface.IGNORE_LIST, 165, 17 + tabChildOffset, 1);
        ps.sendInterface(Interface.LOGOUT, 165, 18 + tabChildOffset, 1);
        ps.sendInterface(Interface.OPTIONS, 165, 19 + tabChildOffset, 1);
        ps.sendInterface(Interface.EMOTE, 165, 20 + tabChildOffset, 1);
        ps.sendInterface(Interface.MUSIC_PLAYER, 165, 21 + tabChildOffset, 1);
        ps.sendInterface(Interface.CLAN_CHAT, 165, 15 + tabChildOffset, 1);
        ps.sendInterface(COMBAT_OPTIONS, 165, 8 + tabChildOffset, 1);

        /**
         * Unlocks
         */
        ps.sendAccessMask(Interface.QUEST, 6, 0, 22, 2);
        ps.sendAccessMask(Interface.QUEST, 7, 0, 128, 2);
        ps.sendAccessMask(Interface.QUEST, 8, 0, 14, 2);
       // ps.sendAccessMask(Interface.OPTIONS, 106, 1, 4, 2);
        //ps.sendAccessMask(Interface.OPTIONS, 107, 1, 4, 2);
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 50, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 665, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 194, 0, 5, 2);

        setDisplayMode(player, displayMode);
    }

    public static void updateDisplay(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).ints();

        PacketSender ps = player.getPacketSender();
        if(player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if(Config.SIDE_PANELS.get(player) == 1) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).ints();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    public static void updateResizedTabs(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).ints();
        PacketSender ps = player.getPacketSender();
        if(player.getGameFrameId() == 161) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).ints();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    private static void moveSubInterfaces(Map<Integer, Integer> oldComponents, Map<Integer, Integer> newComponents, Player player) {
        PacketSender ps = player.getPacketSender();

        for (Map.Entry<Integer, Integer> newComponent : newComponents.entrySet()) {
            int key = newComponent.getKey();
            int to = newComponent.getValue();

            if (to == -1) {
                continue;
            }

            if (oldComponents.containsKey(key)) {
                int from = oldComponents.get(key);

                if (from == -1) {
                    continue;
                }

                ps.moveInterface(from >> 16, from & 0xffff, to >> 16, to & 0xffff);
            }
        }
        // Temporary fix for broken access masks
        /*ps.sendAccessMask(Interface.QUEST, 7, 0, 19, 2);
        ps.sendAccessMask(Interface.QUEST, 8, 0, 118, 2);
        ps.sendAccessMask(Interface.QUEST, 9, 0, 11, 2);
        ps.sendAccessMask(Interface.OPTIONS, 106, 1, 4, 2);
        ps.sendAccessMask(Interface.OPTIONS, 107, 1, 4, 2);
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 47, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 625, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 184, 0, 4, 2);*/
    }

    public static int getGameFrameFor(int displayMode) {
        switch (displayMode) {
            case 1:
                return Interface.FIXED_SCREEN;
            case 2:
                return Interface.RESIZED_SCREEN;
            case 3:
                return Interface.RESIZED_STACKED_SCREEN;
            default:
                throw new IllegalArgumentException("Unknown display mode " + displayMode);
        }
    }

    private static EnumMap getToplevelComponents(Player player) {
        switch (player.getGameFrameId()) {
            case Interface.FIXED_SCREEN:
                return EnumMap.get(1129);

            case Interface.RESIZED_SCREEN:
                return EnumMap.get(1130);

            case Interface.RESIZED_STACKED_SCREEN:
                return EnumMap.get(1131);

            case Interface.DEFAULT_SCREEN:
                return EnumMap.get(1132);

            case Interface.MOBILE_SCREEN:
                return EnumMap.get(1745);
        }

        return null;
    }

}
