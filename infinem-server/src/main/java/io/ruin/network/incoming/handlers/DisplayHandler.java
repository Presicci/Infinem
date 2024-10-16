package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.def.EnumDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.handlers.XpCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.PacketSender;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;

import java.util.Map;

import static io.ruin.model.inter.Interface.COMBAT_OPTIONS;

@SuppressWarnings("Duplicates")
@ClientPacketHolder(packets = {ClientPacket.EVENT_WINDOW_SETTING})
public class DisplayHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int displayMode = in.readByte();
        int canvasWidth = in.readShort();
        int canvasHeight = in.readShort();
        if (!player.hasDisplay()) {
            player.setDisplayMode(displayMode);
            sendDisplay(player, displayMode);
            player.start();
            player.setOnline(true);
            if (Config.XP_COUNTER_SHOWN.get(player) == 1)
                XpCounter.sendCounter(player);
        } else {
            boolean wasFixed = player.isFixedScreen();
            player.setDisplayMode(displayMode);
            if (wasFixed == player.isFixedScreen())
                return;
            updateDisplay(player);
        }
    }

    public static final int DEFAULT_SCREEN_CHILD_OFFSET = 7;

    public static void setDisplayMode(Player p, int displayMode) {
        /*if (displayMode == 2 && Config.SIDE_PANELS.get(p) == 1) displayMode = 3;
        if (p.getDisplayMode() == displayMode) return;
        p.setDisplayMode(displayMode);
        p.closeInterface(InterfaceType.MAIN_STRETCHED);
        PacketSender ps = p.getPacketSender();

        Map<Integer, Integer> oldComponents = getToplevelComponents(p).getValuesAsInts();

        ps.sendGameFrame(getGameFrameFor(displayMode));
        ps.sendClientScript(3998, "i", displayMode - 1);
        Map<Integer, Integer> newComponents = getToplevelComponents(p).getValuesAsInts();
        moveSubInterfaces(oldComponents, newComponents, p);
        ps.sendAccessMask(Interface.OPTIONS, 39, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 53, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 67, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 79, 1, 5, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 80, 1, 4, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 82, 1, 3, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 23, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 81, 1, 5, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 47, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 665, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 184, 0, 4, 2);

        ps.sendClientScript(3970, "IIi", 46661634, 46661635, (int) TimeUnit.MILLISECONDS.toMinutes(p.playTime * Server.tickMs()));

        if (Config.ASK_TIME_PLAYED.get(p) == 0)
            Config.varpbit(12933, false).set(p, 1); // if 1 then it shows time played.*/
    }

    private static void sendDisplay(Player player, int displayMode) {
        if (displayMode == 2 && Config.SIDE_PANELS.get(player) == 1) displayMode = 3;
        PacketSender ps = player.getPacketSender();
        if (player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if (Config.SIDE_PANELS.get(player) == 0) {
            ps.sendGameFrame(164);
            //player.getPacketSender().sendInterface(Interface.CHAT_BAR, 164, 91, 1);
        } else {
            ps.sendGameFrame(161);
            //player.getPacketSender().sendInterface(Interface.CHAT_BAR, 161, 96, 1);
        }
        openInterface(player, Interface.CHAT_BAR, 96, 1);
        openInterface(player, COMBAT_OPTIONS, 76, 1);
        openInterface(player, Interface.SKILLS, 77, 1);//skills tab
        openInterface(player, Interface.INVENTORY, 79, 1);//inventory
        openInterface(player, Interface.EQUIPMENT, 80, 1);//equipment tab
        openInterface(player, Interface.PRAYER, 81, 1);//prayer tab
        openInterface(player, Interface.MODERN_SPELL_BOOK, 82, 1);//magic spellbook
        openInterface(player, Interface.CLAN_CHAT, 83, 1);//side channels
        openInterface(player, Interface.ACCOUNT_MANAGEMENT, 84, 1);//account management
        openInterface(player, Interface.LOGOUT, 86, 1);//logout tab
        openInterface(player, Interface.SCROLL, 87, 1);//settings side
        openInterface(player, Interface.EMOTE, 88, 1);//emote tab
        openInterface(player, Interface.MUSIC_PLAYER, 89, 1);//music tab
        openInterface(player, Interface.QUEST_TAB, 78, 1);
        openInterface(player, 163, 93, 1);//private chat overlay
        openInterface(player, Interface.ORBS, 33, 1);//stat orbs
        openInterface(player, 303, 2, 1);//hp hud
        openInterface(player, Config.FRIENDS_AND_IGNORE_TOGGLE.get(player) == 0 ? Interface.FRIENDS_LIST : Interface.IGNORE_LIST, 85, 1);//Friends/Ignores

        sendInitialAccessMasks(player);
        /*int tabChildOffset = DEFAULT_SCREEN_CHILD_OFFSET;
        if (Config.DATA_ORBS.get(player) == 0)
            ps.sendInterface(160, 165, 24 + tabChildOffset, 1);


        ps.sendInterface(Interface.SKILLS, 165, 9 + tabChildOffset, 1);
        ps.sendInterface(Interface.QUEST_TAB, 165, 10 + tabChildOffset, 1);
        ps.sendInterface(Interface.QUEST, Interface.QUEST_TAB, 33, 1);
        ps.sendInterface(Interface.INVENTORY, 165, 11 + tabChildOffset, 1);
        ps.sendInterface(Interface.EQUIPMENT, 165, 12 + tabChildOffset, 1);
        ps.sendInterface(Interface.PRAYER, 165, 13 + tabChildOffset, 1);
        ps.sendInterface(Interface.MAGIC_BOOK, 165, 14 + tabChildOffset, 1);

        ps.sendInterface(Interface.ACCOUNT_MANAGEMENT, 165, 16 + tabChildOffset, 1);
        ps.sendInterface(Config.FRIENDS_AND_IGNORE_TOGGLE.get(player) == 0 ? Interface.FRIENDS_LIST : Interface.IGNORE_LIST, 165, 17 + tabChildOffset, 1);
        ps.sendInterface(Interface.LOGOUT, 165, 18 + tabChildOffset, 1);
        ps.sendInterface(Interface.OPTIONS, 165, 19 + tabChildOffset, 1);
        ps.sendInterface(Interface.EMOTE, 165, 20 + tabChildOffset, 1);
        ps.sendInterface(Interface.MUSIC_PLAYER, 165, 21 + tabChildOffset, 1);
        ps.sendInterface(Interface.CLAN_CHAT, 165, 15 + tabChildOffset, 1);
        //ps.sendInterface(Interface.CLAN_CHAT, Interface.SOCIAL_TAB, 7, 1);
        ps.sendInterface(COMBAT_OPTIONS, 165, 8 + tabChildOffset, 1);*/

        /**
         * Unlocks
         */
        /*ps.sendAccessMask(Interface.QUEST, 6, 0, 22, 2);
        ps.sendAccessMask(Interface.QUEST, 7, 0, 128, 2);
        ps.sendAccessMask(Interface.QUEST, 8, 0, 14, 2);
        // ps.sendAccessMask(Interface.OPTIONS, 106, 1, 4, 2);
        //ps.sendAccessMask(Interface.OPTIONS, 107, 1, 4, 2);
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 50, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 665, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 194, 0, 5, 2);
        ps.sendAccessMask(Interface.CHAT_BAR, 59, 0, 1, 2);*/
    }

    public static void sendInitialAccessMasks(Player player) {
        PacketSender ps = player.getPacketSender();

        /**
         * Unlocks
         */
        //ps.sendAccessMask(261, 106, 1, 4, 2);
        //ps.sendAccessMask(261, 107, 1, 4, 2);
        ps.sendAccessMask(Interface.EMOTE, 2, 0, 52, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 6, 0, 580, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 184, 0, 4, 2);
        ps.sendAccessMask(Interface.PRAYER, 42, 0, 5, 2);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 185, 0, 5, 2);

        ps.sendAccessMask(Interface.INVENTORY, 0, 0, 27, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3,
                AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp6, AccessMasks.ClickOp7,
                AccessMasks.ClickOp8, AccessMasks.ClickOp9, AccessMasks.ClickOp10, AccessMasks.UseOnGroundItem, AccessMasks.UseOnNpc, AccessMasks.UseOnObject,
                AccessMasks.UseOnPlayer, AccessMasks.UseOnComponent, AccessMasks.DragDepth1, AccessMasks.DragTargetable, AccessMasks.ComponentTargetable);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 5, 0, 726, AccessMasks.ClickOp1, AccessMasks.ClickOp2);
        ps.sendAccessMask(Interface.MODERN_SPELL_BOOK, 195, 0, 6, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 38, 1, 5, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 39, 1, 4, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 41, 1, 3, AccessMasks.ClickOp1);
    }

    public static void updateDisplay(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).getValuesAsInts();

        PacketSender ps = player.getPacketSender();
        if (player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if (Config.SIDE_PANELS.get(player) == 0) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).getValuesAsInts();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    public static void updateResizedTabs(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).getValuesAsInts();
        PacketSender ps = player.getPacketSender();
        if (player.getGameFrameId() == 161) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).getValuesAsInts();
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
    }

    public static void openInterface(Player player, int interfaceId, int resizableComponent, int overlayType) {
        int gameframe = player.getGameFrameId();
        int childId = -1;

        if (gameframe == 161) {
            childId = resizableComponent;
        } else {
            EnumDefinition enumDef = getToplevelComponents(player);

            assert enumDef != null;

            final Map<Integer, Integer> ints = enumDef.getValuesAsInts();
            assert ints != null;
            final int key = 161 << 16 | resizableComponent;

            assert ints.containsKey(key);

            childId = ints.get(key) & 0xFFFF;
        }

        if (childId == -1) {
            System.out.println("DisplayHandler: Invalid interface opened: " + interfaceId);
            return;
        }

        player.getPacketSender().sendInterface(interfaceId, gameframe, childId, overlayType);
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

    private static EnumDefinition getToplevelComponents(Player player) {
        switch (player.getGameFrameId()) {
            case Interface.FIXED_SCREEN:
                return EnumDefinition.get(1129);

            case Interface.RESIZED_SCREEN:
                return EnumDefinition.get(1130);

            case Interface.RESIZED_STACKED_SCREEN:
                return EnumDefinition.get(1131);

            case Interface.DEFAULT_SCREEN:
                return EnumDefinition.get(1132);

            case Interface.MOBILE_SCREEN:
                return EnumDefinition.get(1745);
        }

        return null;
    }

}
