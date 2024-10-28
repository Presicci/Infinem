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
            if (displayMode == 2 && Config.SIDE_PANELS.get(player) == 1) {
                displayMode = 3;
            }
            player.setDisplayMode(displayMode);
            System.out.println(displayMode);
            sendDisplay(player);
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

    private static void sendDisplay(Player player) {
        PacketSender ps = player.getPacketSender();
        if (player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if (Config.SIDE_PANELS.get(player) == 0) {
            ps.sendGameFrame(161);
        } else {
            ps.sendGameFrame(164);
        }
        openInterface(player, Interface.CHAT_BAR, 96, 1);
        openInterface(player, COMBAT_OPTIONS, 76, 1);
        openInterface(player, Interface.SKILLS, 77, 1);
        openInterface(player, Interface.QUEST_TAB, 78, 1);
        openInterface(player, Interface.INVENTORY, 79, 1);
        openInterface(player, Interface.EQUIPMENT, 80, 1);
        openInterface(player, Interface.PRAYER, 81, 1);
        openInterface(player, Interface.MODERN_SPELL_BOOK, 82, 1);
        openInterface(player, Interface.CLAN_CHAT, 83, 1);
        openInterface(player, Interface.ACCOUNT_MANAGEMENT, 84, 1);
        openInterface(player, Config.FRIENDS_AND_IGNORE_TOGGLE.get(player) == 0 ? Interface.FRIENDS_LIST : Interface.IGNORE_LIST, 85, 1);
        openInterface(player, Interface.LOGOUT, 86, 1);
        openInterface(player, Interface.OPTIONS, 87, 1);
        openInterface(player, Interface.EMOTE, 88, 1);
        openInterface(player, Interface.MUSIC_PLAYER, 89, 1);
        openInterface(player, 163, 93, 1);//private chat overlay
        openInterface(player, Interface.ORBS, 33, 1);//stat orbs
        openInterface(player, 303, 2, 1);//hp hud

        sendInitialAccessMasks(player);
    }

    public static void sendInitialAccessMasks(Player player) {
        PacketSender ps = player.getPacketSender();

        /**
         * Unlocks
         */
        //ps.sendAccessMask(261, 106, 1, 4, 2);
        //ps.sendAccessMask(261, 107, 1, 4, 2);
        ps.sendAccessMask(Interface.EMOTE, 2, 0, 52, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 6, 0, 789, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 184, 0, 4, 2);
        // Prayer filters
        ps.sendAccessMask(Interface.PRAYER, 42, 0, 4, AccessMasks.ClickOp1);
        // Magic filters
        ps.sendAccessMask(Interface.MAGIC_BOOK, 185, 0, 5, 2);

        ps.sendAccessMask(Interface.INVENTORY, 0, 0, 27, AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3,
                AccessMasks.ClickOp4, AccessMasks.ClickOp5, AccessMasks.ClickOp6, AccessMasks.ClickOp7,
                AccessMasks.ClickOp8, AccessMasks.ClickOp9, AccessMasks.ClickOp10, AccessMasks.UseOnGroundItem, AccessMasks.UseOnNpc, AccessMasks.UseOnObject,
                AccessMasks.UseOnPlayer, AccessMasks.UseOnComponent, AccessMasks.DragDepth1, AccessMasks.DragTargetable, AccessMasks.ComponentTargetable);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 5, 0, 726, AccessMasks.ClickOp1, AccessMasks.ClickOp2);
        ps.sendAccessMask(Interface.MODERN_SPELL_BOOK, 198, 0, 6, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 38, 1, 5, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 39, 1, 4, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 41, 1, 3, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 104, 0, 21, AccessMasks.ClickOp1);
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

    public static void updateGameframe(Player player, int gameframeIndex) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).getValuesAsInts();

        player.getPacketSender().sendClientScript(3998, "i", gameframeIndex == 1 ? 0 : 1);
        player.setDisplayMode(gameframeIndex);
        Config.SIDE_PANELS.setInstant(player, gameframeIndex == 3 ? 1 : 0);
        player.getPacketSender().sendGameFrame(getGameFrameFor(gameframeIndex));

        Map<Integer, Integer> newComponents = getToplevelComponents(player).getValuesAsInts();

        moveSubInterfaces(oldComponents, newComponents, player);
    }

    public static void updateDisplay(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).getValuesAsInts();

        PacketSender ps = player.getPacketSender();
        if (player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if (Config.SIDE_PANELS.get(player) == 1) {
            ps.sendGameFrame(161);
        } else {
            ps.sendGameFrame(164);
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
        sendInitialAccessMasks(player);
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
