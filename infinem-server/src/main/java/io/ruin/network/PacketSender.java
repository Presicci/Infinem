package io.ruin.network;

import io.netty.channel.ChannelFutureListener;
import io.ruin.Server;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.protocol.ServerPacket;
import io.ruin.api.utils.ISAACCipher;
import io.ruin.cache.def.InterfaceDefinition;
import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.ai.AIPlayer;
import io.ruin.model.inter.*;
import io.ruin.model.inter.handlers.NotificationInterface;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.shop.ShopItem;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Function;

@Slf4j
public class PacketSender {

    private final Player player;

    private final ISAACCipher cipher;

    public PacketSender(Player player, ISAACCipher cipher) {
        this.player = player;
        this.cipher = cipher;
    }

    public void write(OutBuffer out) { //this has to be called by main thread or else gg because of cipher
        if (player instanceof AIPlayer) {
            return;
        }
        if(!Thread.currentThread().getName().equals("server-worker #1"))
            Server.logError(player.getName() + " wrote packet off main thread!", new Throwable());
        int opcode = out.payload()[0] & 0xff;;
        ServerPacket packet = ServerPacket.getPacketByOpcode(opcode);
        System.out.println("Client outgoing: " + opcode + (packet == null ? "" : " - " + packet) + " - " + Arrays.toString(out.payload()));
        player.getChannel().write(out.encode(cipher).toBuffer());
    }

    /**
     * Packets
     */

    public void sendLogin(LoginInfo info) {
        if (player instanceof AIPlayer) {
            return;
        }
        OutBuffer out = new OutBuffer(40).sendFixedPacket(2);

        out.addByte(37);

        if (info.tfaTrust) {
            out.addByte(1);
            out.startEncrypt().addInt(info.tfaTrustValue).stopEncrypt();
            out.encrypt(cipher);
        } else {
            out.addByte(0);
            out.skip(4);
        }

        out.addByte((World.isDev() || player.isAdmin()) ? 2 : player.isModerator() ? 1 : 0) // staffModLevel
                .addByte(0) // Pmod if == 1
                .addShort(player.getIndex())
                .addByte(0) // Friends/ignores container size. 0 = 200, 1 = 400. OSRS allows 400 for members, 200 for F2P.
                .addLong(player.getUserId())
                .addLong(player.getUserId())
                .addLong(-1);
        player.getChannel().write(out.toBuffer()); //no encryption needed!

        sendRegion(true);
        sendActiveWorld();
        Region.update(player);
    }

    public void sendLogout() {
        try {
            OutBuffer out = new OutBuffer(1).sendFixedPacket(ServerPacket.LOGOUT.getPacketId());
            if (player.getChannel().isActive())
                player.getChannel().writeAndFlush(out.encode(cipher).toBuffer()).addListener(ChannelFutureListener.CLOSE);
        } catch (RejectedExecutionException ex) {
            ex.printStackTrace();
            //for shutdown loop problem
        }
    }

    public void sendRegion(boolean login) {
        player.removeFromRegions();
        Position position = player.getPosition();
        Region region = player.lastRegion = position.getRegion();
        int chunkX = position.getChunkX();
        int chunkY = position.getChunkY();
        int depth = Region.CLIENT_SIZE >> 4;
        boolean dynamic = region.dynamicData != null;
        OutBuffer out = new OutBuffer(255);
        if (login || !dynamic) {
            /**
             * Regular map
             */
            out.sendVarShortPacket(ServerPacket.REBUILD_NORMAL.getPacketId());
            if (login)
                player.getUpdater().init(out);
            if (dynamic) {
                /**
                 * Dynamic region must be sent after regular region on login. //todo check if this is still the case in 171 since they changed how this packet is sent
                 */
                player.getUpdater().updateRegion = true;
                chunkX = chunkY = 0;
            }
            out.addShort(chunkX);
            out.addShort(0); //UNUSED
            out.addLEShort(chunkY);
            int countPos = out.position();
            out.addShort(0);

            boolean forceSend = false; //Resets the landscape client sided
            if ((chunkX / 8 == 48 || chunkX / 8 == 49) && chunkY / 8 == 48)
                forceSend = true;
            if (chunkX / 8 == 48 && chunkY / 8 == 148)
                forceSend = true;
            int regionCount = 0;
            for (int xCalc = (chunkX - depth) / 8; xCalc <= (chunkX + depth) / 8; xCalc++) {
                for (int yCalc = (chunkY - depth) / 8; yCalc <= (chunkY + depth) / 8; yCalc++) {
                    int regionId = yCalc + (xCalc << 8);
                    if (!forceSend || (yCalc != 49 && yCalc != 149 && yCalc != 147 && xCalc != 50 && (xCalc != 49 || yCalc != 47))) {
                        Region r = Region.get(regionId);
                        if (r.keys == null)
                            out.skip(16);
                        else
                            out.addInt(r.keys[0]).addInt(r.keys[1]).addInt(r.keys[2]).addInt(r.keys[3]);
                        player.addRegion(r);
                        regionCount++;
                    }
                }
            }
            int curPos = out.position();
            out.position(countPos);
            out.addShort(regionCount);
            out.position(curPos);
        } else {
            /**
             * Dynamic map
             */
            out.sendVarShortPacket(ServerPacket.REBUILD_REGION.getPacketId())
                    .addShort(chunkY)
                    .addShort(chunkX)
                    .addByteNeg(0); //force refresh;

            int startPos = out.position();

            out.addShort(0);

            ArrayList<Integer> chunkRegionIds = new ArrayList<>();
            out.initBitAccess();
            for (int pointZ = 0; pointZ < 4; pointZ++) {
                for (int xCalc = (chunkX - depth); xCalc <= (chunkX + depth); xCalc++) {
                    for (int yCalc = (chunkY - depth); yCalc <= (chunkY + depth); yCalc++) {
                        Region r = Region.LOADED[(xCalc / 8) << 8 | (yCalc / 8)];
                        if (r == null || r.dynamicData == null || r.dynamicIndex != region.dynamicIndex) {
                            out.addBits(1, 0);
                            continue;
                        }
                        int[] chunkData = r.dynamicData[pointZ][xCalc % 8][yCalc % 8];
                        int chunkHash = chunkData[0];
                        int chunkRegionId = chunkData[1];
                        if (chunkHash == 0 || chunkRegionId == 0) {
                            out.addBits(1, 0);
                            continue;
                        }
                        out.addBits(1, 1);
                        out.addBits(26, chunkHash);
                        if (!chunkRegionIds.contains(chunkRegionId))
                            chunkRegionIds.add(chunkRegionId);
                        if (!player.getRegions().contains(r))
                            player.addRegion(r);
                    }
                }
            }
            out.finishBitAccess();

            int endPos = out.position();
            out.position(startPos);
            out.addShort(chunkRegionIds.size());
            out.position(endPos);

            for (int id : chunkRegionIds) {
                Region r = Region.LOADED[id];
                if (r.keys == null)
                    out.skip(16);
                else
                    out.addInt(r.keys[0]).addInt(r.keys[1]).addInt(r.keys[2]).addInt(r.keys[3]);
            }
        }
        write(out);
    }

    public void sendWorldEntity() {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(ServerPacket.REBUILD_WORLDENTITY.getPacketId());
        out.addShort(-1);
        out.addShort(0);
        out.addShort(0);

        Position position = player.getPosition();
        Region region = player.lastRegion = position.getRegion();
        int chunkX = position.getChunkX();
        int chunkY = position.getChunkY();
        int depth = Region.CLIENT_SIZE >> 4;
        int startPos = out.position();
        out.addShort(0);
        ArrayList<Integer> chunkRegionIds = new ArrayList<>();
        out.initBitAccess();
        for (int pointZ = 0; pointZ < 4; pointZ++) {
            for (int xCalc = (chunkX - depth); xCalc <= (chunkX + depth); xCalc++) {
                for (int yCalc = (chunkY - depth); yCalc <= (chunkY + depth); yCalc++) {
                    Region r = Region.LOADED[(xCalc / 8) << 8 | (yCalc / 8)];
                    if (r == null || r.dynamicData == null || r.dynamicIndex != region.dynamicIndex) {
                        out.addBits(1, 0);
                        continue;
                    }
                    int[] chunkData = r.dynamicData[pointZ][xCalc % 8][yCalc % 8];
                    int chunkHash = chunkData[0];
                    int chunkRegionId = chunkData[1];
                    if (chunkHash == 0 || chunkRegionId == 0) {
                        out.addBits(1, 0);
                        continue;
                    }
                    out.addBits(1, 1);
                    out.addBits(26, chunkHash);
                    if (!chunkRegionIds.contains(chunkRegionId))
                        chunkRegionIds.add(chunkRegionId);
                    if (!player.getRegions().contains(r))
                        player.addRegion(r);
                }
            }
        }
        out.finishBitAccess();

        int endPos = out.position();
        out.position(startPos);
        out.addShort(chunkRegionIds.size());
        out.position(endPos);

        for (int id : chunkRegionIds) {
            Region r = Region.LOADED[id];
            if (r.keys == null)
                out.skip(16);
            else
                out.addInt(r.keys[0]).addInt(r.keys[1]).addInt(r.keys[2]).addInt(r.keys[3]);
        }
        write(out);
        /*
        buffer.p2(message.index)
        buffer.p2(message.baseX)
        buffer.p2(message.baseZ)
        encodeRegion(buffer, message.zones)
         */
    }

    public void sendModelInformation(int parentId, int childId, int zoom, int rotationX, int rotationY) {
        if (!InterfaceDefinition.valid(parentId, childId)) {
            new Exception("INVALID sendModelInformation " + parentId + ":" + childId).printStackTrace();
            return;
        }
        OutBuffer out = new OutBuffer(11).sendFixedPacket(36)
                .addLEShort(rotationX)
                .addShortAdd(rotationY)
                .addLEInt(parentId << 16 | childId)
                .addLEShort(zoom);
        write(out);
    }

    public void sendGameFrame(int id) {
        player.setGameFrameId(id);
        OutBuffer out = new OutBuffer(3)
                .sendFixedPacket(ServerPacket.IF_OPENTOP.getPacketId())
                .addShort(id);
        write(out);
    }

    public void sendUrl(String url, boolean copy) {
        sendClientScript(1081, "sii", url, 1, 1);
    }

    public void sendInterface(int interfaceId, int parentId, int childId, int overlayType) {
        if (!InterfaceDefinition.valid(interfaceId) || !InterfaceDefinition.valid(parentId, childId)) {
            new Exception("INVALID sendInterface " + interfaceId
                    + " -> " + parentId + ":" + childId
                    + " (overlayType=" + overlayType + ")").printStackTrace();
            return;
        }
        player.setVisibleInterface(interfaceId, parentId, childId);
        OutBuffer out = new OutBuffer(8).sendFixedPacket(ServerPacket.IF_OPENSUB.getPacketId())
                .addLEShortAdd(interfaceId)
                .addIMEInt(parentId << 16 | childId)
                .addByteSub(overlayType);
        write(out);
    }

    public void sendModel(int parentId, int childId, int modelId) {
        if (!InterfaceDefinition.valid(parentId, childId)) {
            new Exception("INVALID sendModel " + parentId + ":" + childId + " (modelId=" + modelId + ")").printStackTrace();
            return;
        }
        OutBuffer out = new OutBuffer(7).sendFixedPacket(74)
                .addMEInt(parentId << 16 | childId)
                .addShortAdd(modelId);
        write(out);
    }

    public void removeInterface(int parentId, int childId) {
        if (!InterfaceDefinition.valid(parentId, childId)) {
            new Exception("INVALID removeInterface " + parentId + ":" + childId).printStackTrace();
            return;
        }
        player.removeVisibleInterface(parentId, childId);
        OutBuffer out = new OutBuffer(5).sendFixedPacket(ServerPacket.IF_CLOSESUB.getPacketId())
                .addInt(parentId << 16 | childId);
        write(out);
    }

    public void moveInterface(int fromParentId, int fromChildId, int toParentId, int toChildId) {
        if (!InterfaceDefinition.valid(fromParentId, fromChildId) || !InterfaceDefinition.valid(toParentId, toChildId)) {
            new Exception("INVALID moveInterface " + fromParentId + "," + fromChildId + " -> " + toParentId + "," + toChildId).printStackTrace();
            return;
        }
        player.moveVisibleInterface(fromParentId, fromChildId, toParentId, toChildId);
        OutBuffer out = new OutBuffer(9).sendFixedPacket(ServerPacket.IF_MOVESUB.getPacketId())
                .addMEInt(fromParentId << 16 | fromChildId)
                .addLEInt(toParentId << 16 | toChildId);
        write(out);
    }

    public void sendString(int interfaceId, int childId, String string) {
        if (!InterfaceDefinition.valid(interfaceId, childId)) {
            new Exception("INVALID sendString " + interfaceId + ":" + childId + " (\"" + string + "\")").printStackTrace();
            return;
        }
        OutBuffer out = new OutBuffer(3 + 4 + Protocol.strLen(string))
                .sendVarShortPacket(ServerPacket.IF_SETTEXT.getPacketId())
                .addString(string)
                .addInt(interfaceId << 16 | childId);
        write(out);
    }

    public void setHidden(int interfaceId, int childId, boolean hide) {
        if (!InterfaceDefinition.valid(interfaceId, childId)) {
            new Exception("INVALID setHidden " + interfaceId + ":" + childId + " (hide=" + hide + ")").printStackTrace();
            return;
        }
        OutBuffer out = new OutBuffer(6).sendFixedPacket(ServerPacket.IF_SETHIDE.getPacketId())
                .addByteSub(hide ? 1 : 0)
                .addIMEInt(interfaceId << 16 | childId);
        write(out);
    }

    public void sendSetColor(int interfaceId, int childId, Color colour) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(ServerPacket.IF_SETCOLOUR.getPacketId())
                .addShort(colour.getRed() << 10 | colour.getGreen() << 5 | colour.getBlue())
                .addLEInt(interfaceId << 16 | childId);
        write(out);
    }

    public void sendItem(int parentId, int childId, int itemId, int amount) {
        if (!InterfaceDefinition.valid(parentId, childId)) {
            new Exception("INVALID sendItem " + parentId + ":" + childId + " (itemId=" + itemId + ", amount=" + amount + ")").printStackTrace();
            return;
        }
        OutBuffer out = new OutBuffer(11).sendFixedPacket(ServerPacket.IF_SETOBJECT.getPacketId())
                .addLEShort(itemId)
                .addIMEInt(amount)
                .addLEInt(parentId << 16 | childId);
        write(out);
    }

    public void setAlignment(int parentId, int childId, int x, int y) {
        if (!InterfaceDefinition.valid(parentId, childId)) {
            new Exception("INVALID setAlignment " + parentId + ":" + childId + " (x=" + x + ", y=" + y + ")").printStackTrace();
            return;
        }
        OutBuffer out = new OutBuffer(9).sendFixedPacket(ServerPacket.IF_SETPOSITION.getPacketId())
                .addLEShort(y)
                .addIMEInt(parentId << 16 | childId)
                .addShortAdd(x)
        ;
        write(out);
    }

    /**
     * 1,048,576 - 20 - ?
     * 262,144 - 18 - Move item
     * 1024 - 10 - Option 10
     * 512 - 9 - Option 9
     * 256 - 8 - Option 8
     * 128 - 7 - Option 7
     * 64 - 6 - Option 6
     * 32 - 5 - Option 5
     * 16 - 4 - Option 4
     * 8 - 3 - Option 3
     * 4 - 2 - Option 2
     * 2 - 1 - Option 1
     * 1 - 0 - Option 0
     */
    public void sendAccessMask(WidgetInfo widgetInfo, int minChildID, int maxChildID, AccessMask... accessMask) {
        sendAccessMask(widgetInfo, minChildID, maxChildID, AccessMasks.combine(accessMask));
    }

    public void sendAccessMask(WidgetInfo widgetInfo, int minChildID, int maxChildID, int... masks) {
        sendAccessMask(widgetInfo.getGroupId(), widgetInfo.getChildId(), minChildID, maxChildID, masks);
    }

    public void sendAccessMask(boolean debug, int interfaceId, int childParentId, int minChildId, int maxChildId, AccessMask... accessMask) {
        sendAccessMask(debug, interfaceId, childParentId, minChildId, maxChildId, AccessMasks.combine(accessMask));
    }

    public void sendAccessMask(int interfaceId, int childParentId, int minChildId, int maxChildId, AccessMask... accessMask) {
        sendAccessMask(true, interfaceId, childParentId, minChildId, maxChildId, accessMask);
    }

    public void sendAccessMask(int interfaceId, int childParentId, int minChildId, int maxChildId, int... masks) {
        sendAccessMask(true, interfaceId, childParentId, minChildId, maxChildId, masks);
    }

    public void sendAccessMask(boolean debug, int interfaceId, int childParentId, int minChildId, int maxChildId, int... masks) {
        if (!InterfaceDefinition.valid(interfaceId, childParentId/*Math.max(childParentId, Math.max(minChildId, maxChildId))*/)) {
            if (debug)
                new Exception("INVALID sendAccessMask " + interfaceId + ":" + childParentId + " (" + minChildId + ".." + maxChildId + ")").printStackTrace();
            return;
        }
        int mask = AccessMasks.combine(masks);
        OutBuffer out = new OutBuffer(13).sendFixedPacket(ServerPacket.IF_SETEVENTS.getPacketId())
                .addShort(minChildId)
                .addLEShort(maxChildId)
                .addMEInt(interfaceId << 16 | childParentId)
                .addInt(mask);
        write(out);
    }

    public void sendClientScript(int id, Object... params) {
        StringBuilder args = new StringBuilder();
        if (params != null) {
            for (Object obj : params) {
                if (obj instanceof String) {
                    args.append("s");
                } else {
                    args.append("i");
                }
            }
        }
        OutBuffer out;
        if (params == null) {
            out = new OutBuffer(3);
        } else {
            out = new OutBuffer(3 + Protocol.strLen(args.toString()) + (params.length * 4));
        }
        out.sendVarShortPacket(ServerPacket.RUNCLIENTSCRIPT.getPacketId());
        out.addString(args.toString());
        if (params != null) {
            for (int i = params.length - 1; i >= 0; i--) {
                Object param = params[i];
                if (param instanceof String)
                    out.addString((String) param);
                else
                    out.addInt((Integer) param);
            }
        }
        out.addInt(id);
        write(out);
    }

    public void sendClientScript(int id, String type, Object... params) {
        OutBuffer out = new OutBuffer(3 + Protocol.strLen(type) + (params.length * 4))
                .sendVarShortPacket(ServerPacket.RUNCLIENTSCRIPT.getPacketId())
                .addString(type);
        for (int i = type.length() - 1; i >= 0; i--) {
            Object param = params[i];
            if (param instanceof String)
                out.addString((String) param);
            else
                out.addInt((Integer) param);
        }
        out.addInt(id);
        write(out);
    }

    public void sendSystemUpdate(int time) {
        OutBuffer out = new OutBuffer(3)
                .sendFixedPacket(ServerPacket.UPDATE_REBOOT_TIMER.getPacketId())
                .addShortAdd(time * 50 / 30);
        write(out);
    }

    public void setTextStyle(int parentId, int childId, int horizontalAlignment, int verticalAlignment, int lineHeight) {
        sendClientScript(600, "iiiI", horizontalAlignment, verticalAlignment, lineHeight, parentId << 16 | childId);
    }

    public void sendPopupNotification(int color, String title, String text) {
        NotificationInterface.sendNotification(player, color, title, text);
    }

    public void fadeIn() {
        sendClientScript(948, "iiiii", 0, 0, 0, 255, 50);
        clearFade();
    }

    public void fadeOut() {
        InterfaceType.SECONDARY_OVERLAY.open(player, 174);
        sendClientScript(951, "");
    }

    public void pohFadeIn() {
        InterfaceType.SECONDARY_OVERLAY.close(player);
    }

    public void pohFadeOut() {
        InterfaceType.SECONDARY_OVERLAY.open(player, Interface.CONSTRUCTION_LOADING);
    }

    public void prifFadeIn() {
        sendClientScript(2922);
        clearFade();
    }

    public void prifFadeOut() {
        InterfaceType.SECONDARY_OVERLAY.open(player, 641);
        sendClientScript(2921);
    }

    public void nightmareFadeIn() {
        sendClientScript(2894, "iiii", 41549825, 41549826, 0, 200);
        clearFade();
    }

    public void nightmareFadeOut() {
        InterfaceType.SECONDARY_OVERLAY.open(player, 634);
        sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 13109328, -1, -1);
    }

    public void deathFadeIn() {
        sendClientScript(2894, "iiii", 41549825, 41549826, -1, -1);
        clearFade();
    }

    public void deathFadeOut() {
        InterfaceType.SECONDARY_OVERLAY.open(player, 634);
        sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 4128927, -1, -1);
    }

    public void clearFade() {
        player.addEvent(e -> {
            e.delay(2);
            InterfaceType.SECONDARY_OVERLAY.close(player);
        });
    }

    public void sendMessage(String message, String extension, int type) {
        OutBuffer out = Protocol.messagePacket(message, extension, type);
        write(out);
    }

    public void sendVarp(int id, int value) {
        if (id >= 20000) {
            if (id != 20000 && id != 20002 && id != 20004) {
                return;
            }
            return; // ignore the custom varps.
        }
        if (id < 0)
            id = 0;
        OutBuffer out;
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE)
            out = new OutBuffer(7).sendFixedPacket(ServerPacket.VARP_LARGE.getPacketId())
                    .addShort(id)
                    .addInt(value);
        else
            out = new OutBuffer(4).sendFixedPacket(ServerPacket.VARP_SMALL.getPacketId())
                    .addByteSub(value)
                    .addLEShort(id);
        write(out);
    }

    public void sendItems(int parentId, int childId, int containerId, Item... items) {
        sendItems(parentId << 16 | childId, containerId, items, items.length);
    }

    public void sendItems(int parentId, int childId, int containerId, Item[] items, int length) {
        sendItems(parentId << 16 | childId, containerId, items, length);
    }


    public void sendItems(WidgetInfo widgetInfo, int containerId, Item[] items, int length) {
        sendItems(widgetInfo.getPackedId(), containerId, items, length);
    }

    public void sendItems(int containerId, List<Item> items) {
        sendItems(-1, containerId, items.toArray(new Item[0]), items.size());
    }

    public void sendItems(int interfaceHash, int containerId, Item[] items, int length) {
        OutBuffer out = new OutBuffer(10 + (length * 10)).sendVarShortPacket(ServerPacket.UPDATE_INV_FULL.getPacketId())
                .addInt(interfaceHash)
                .addShort(containerId)
                .addShort(length);
        for (int slot = 0; slot < length; slot++) {
            Item item = items[slot];
            if (item == null || item.getId() < 0) {
                out.addByteNeg(0);
                out.addLEShortAdd(0);
            } else {
                if (item.getAmount() < 255) {
                    out.addByteNeg(item.getAmount());
                } else {
                    out.addByteNeg(255);
                    out.addIMEInt(item.getAmount());
                }
                out.addLEShortAdd(item.getId() + 1);
            }
        }
        write(out);
    }

    public void sendShopItems(int interfaceHash, int containerId, ShopItem[] items, int length) {
        OutBuffer out = new OutBuffer(10 + (length * 10)).sendVarShortPacket(ServerPacket.UPDATE_INV_FULL.getPacketId())
                .addInt(interfaceHash)
                .addShort(containerId)
                .addShort(length);
        for (int slot = 0; slot < items.length; slot++) {
            ShopItem item = items[slot];
            if (item == null || item.getId() < 0) {
                out.addByteNeg(0);
                out.addLEShortAdd(0);
            } else {
                if (item.getAmount() < 255) {
                    out.addByteNeg(item.getAmount());
                } else {
                    out.addByteNeg(255);
                    out.addIMEInt(item.getAmount());
                }
                out.addLEShortAdd(item.getId() + 1);
            }
        }
        write(out);
    }

    public void updateItems(int interfaceHash, int containerId, Item[] items, boolean[] updatedSlots, int updatedCount) {
        OutBuffer out = new OutBuffer(10 + (updatedCount * 10)).sendVarShortPacket(ServerPacket.UPDATE_INV_PARTIAL.getPacketId())
                .addInt(interfaceHash)
                .addShort(containerId);
        for (int slot = 0; slot < items.length; slot++) {
            if (updatedSlots[slot]) {
                Item item = items[slot];
                out.addSmart(slot);
                if (item == null || item.getId() < 0) {
                    out.addShort(0);
                } else {
                    out.addShort(item.getId() + 1);
                    if (item.getAmount() < 255) {
                        out.addByte(item.getAmount());
                    } else {
                        out.addByte(255);
                        out.addInt(item.getAmount());
                    }

                }
            }
        }
        write(out);
    }

    public void updateItems(int interfaceHash, int containerId, ShopItem[] items, boolean[] updatedSlots, int updatedCount) {
        OutBuffer out = new OutBuffer(10 + (updatedCount * 10)).sendVarShortPacket(ServerPacket.UPDATE_INV_PARTIAL.getPacketId())
                .addInt(interfaceHash)
                .addShort(containerId);
        for (int slot = 0; slot < items.length; slot++) {
            if (updatedSlots[slot]) {
                ShopItem item = items[slot];
                out.addSmart(slot);
                if (item == null || item.getId() < 0) {
                    out.addShort(0);
                } else {
                    out.addShort(item.getId() + 1);
                    if (item.getAmount() < 255) {
                        out.addByte(item.getAmount());
                    } else {
                        out.addByte(255);
                        out.addInt(item.getAmount());
                    }

                }
            }
        }
        write(out);
    }

    public void sendStat(int id, int currentLevel, int experience) {
        OutBuffer out = new OutBuffer(8).sendFixedPacket(ServerPacket.UPDATE_STAT_V2.getPacketId())
                .addMEInt(experience)
                .addByteSub(id)
                .addByteNeg(currentLevel) // Boosted level
                .addByteAdd(currentLevel);
        write(out);
    }

    public void sendRunEnergy(int energy) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.UPDATE_RUNENERGY.getPacketId())
                .addShort(energy * 100);
        write(out);
    }

    public void sendWeight(int weight) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.UPDATE_RUNWEIGHT.getPacketId())
                .addShort(weight);
        write(out);
    }

    public void sendPlayerAction(String name, boolean top, int option) {
        OutBuffer out = new OutBuffer(4 + Protocol.strLen(name)).sendVarBytePacket(ServerPacket.SET_PLAYER_OP.getPacketId())
                .addByteNeg(option)
                .addByte(top ? 1 : 0)
                .addString(name);
        write(out);
    }

    public void worldHop(String host, int id, int flags) {
        OutBuffer out = new OutBuffer(50).sendFixedPacket(ServerPacket.LOGOUT_TRANSFER.getPacketId())
                .addString(host)
                .addShort(id)
                .addInt(flags);
        //todo@@ write(out);
    }

    public void resetMapFlag() {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.SET_MAP_FLAG.getPacketId())
                .addByte(-1)
                .addByte(-1);
        write(out);
    }

    public void setMapFlag(int x, int y) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.SET_MAP_FLAG.getPacketId())
                .addByte(Position.getLocal(x, player.getPosition().getFirstChunkX()))
                .addByte(Position.getLocal(y, player.getPosition().getFirstChunkY()));

        write(out);
    }

    public void clearChunks() {
        /*OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.UPDATE_ZONE_FULL_FOLLOWS.getPacketId())
                .addByteC(-1)
                .addByte(-1);
        write(out);TODO wtf is this?*/
    }

    public void clearChunk(int chunkAbsX, int chunkAbsY) {
        int x = Position.getLocal(chunkAbsX, player.getPosition().getFirstChunkX());
        int y = Position.getLocal(chunkAbsY, player.getPosition().getFirstChunkY());
        int z = player.getHeight();
        OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.UPDATE_ZONE_FULL_FOLLOWS.getPacketId())
                .addByteAdd(x)
                .addByteSub(z)
                .addByteSub(y);
        write(out);
    }

    private void sendMapPacket(int x, int y, int z, Function<Integer, OutBuffer> write) {
        if (player.getHeight() != z)
            return;
        int chunkAbsX = (x >> 3) << 3;
        int chunkAbsY = (y >> 3) << 3;
        int targetLocalX = x - chunkAbsX;
        int targetLocalY = y - chunkAbsY;
        int playerLocalX = Position.getLocal(chunkAbsX, player.getPosition().getFirstChunkX());
        int playerLocalY = Position.getLocal(chunkAbsY, player.getPosition().getFirstChunkY());
        if (playerLocalX >= 0 && playerLocalX < 104 && playerLocalY >= 0 && playerLocalY < 104) {
            write(new OutBuffer(4).sendFixedPacket(ServerPacket.UPDATE_ZONE_PARTIAL_FOLLOWS.getPacketId())
                    .addByte(playerLocalX)
                    .addByteAdd(z)
                    .addByteAdd(playerLocalY));
            write(write.apply((targetLocalX & 0x7) << 4 | (targetLocalY & 0x7)));
        }
    }

    public void sendGroundItem(GroundItem groundItem) {
        sendMapPacket(groundItem.x, groundItem.y, groundItem.z, offsetHash ->
                new OutBuffer(16).sendFixedPacket(ServerPacket.OBJ_ADD.getPacketId())
                        .addLEShort((groundItem.getDespawnTime() * 1000) / 600)   // despawn time TODO
                        .addByteSub(offsetHash)
                        .addLEShortAdd(groundItem.id)
                        .addLEShort(0)// visible time TODO
                        .addByte(0)// private TODO
                        .addByte(31) //OPFILTER TODO
                        .addByteSub(groundItem.activeOwner)// ownership
                        .addLEInt(groundItem.amount)
        );
    }

    public void sendUpdateGroundItem(GroundItem groundItem, int oldAmount) {
        sendMapPacket(groundItem.x, groundItem.y, groundItem.z, offsetHash ->
                new OutBuffer(14).sendFixedPacket(ServerPacket.OBJ_COUNT.getPacketId())
                        .addByteSub(offsetHash)
                        .addInt(groundItem.amount)
                        .addLEShortAdd(groundItem.id)
                        .addIMEInt(oldAmount)
        );
    }

    public void sendRemoveGroundItem(GroundItem groundItem) {
        sendMapPacket(groundItem.x, groundItem.y, groundItem.z, offsetHash ->
                new OutBuffer(8).sendFixedPacket(ServerPacket.OBJ_DEL.getPacketId())
                        .addShort(groundItem.id)
                        .addByteSub(offsetHash)
                        .addIMEInt(groundItem.amount)
        );
    }

    public void sendCreateObject(int id, int x, int y, int z, int type, int dir) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(6).sendFixedPacket(ServerPacket.LOC_ADD_CHANGE.getPacketId())
                        .addShortAdd(id)
                        .addByteNeg(31)
                        .addByteNeg(type << 2 | dir)
                        .addByteNeg(offsetHash)
        );
    }

    public void sendRemoveObject(int x, int y, int z, int type, int dir) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(3).sendFixedPacket(ServerPacket.LOC_DEL.getPacketId())
                        .addByte(offsetHash)
                        .addByte(type << 2 | dir)
        );
    }

    public void sendObjectAnimation(int x, int y, int z, int type, int dir, int animationId) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(5).sendFixedPacket(ServerPacket.LOC_ANIM.getPacketId())
                        .addByteSub(type << 2 | dir)
                        .addByte(offsetHash)
                        .addLEShort(animationId)
        );
    }

    public void sendProjectile(int projectileId, int startX, int startY, int destX, int destY, int targetIndex, int startHeight, int endHeight, int delay, int duration, int curve, int something) {
        sendMapPacket(startX, startY, player.getHeight(), offsetHash ->
                new OutBuffer(22).sendFixedPacket(ServerPacket.MAP_PROJANIM.getPacketId())
                        .addShort(projectileId)
                        .addByteNeg(endHeight)
                        .addLEMedium(targetIndex)
                        .addShortAdd(something)// progress
                        .addShort(duration)
                        .addByte(offsetHash)
                        .addMedium_alt1(0)// source index
                        .addByteAdd(curve)
                        .addByte(startHeight)
                        .addByteSub(destX - startX)
                        .addShortAdd(delay)
                        .addByte(destY - startY)
        );
    }

    public void sendGraphics(int id, int height, int delay, int x, int y, int z) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(7).sendFixedPacket(ServerPacket.MAP_ANIM.getPacketId())
                        .addByteSub(offsetHash)
                        .addShortAdd(id)
                        .addByteSub(height)
                        .addShortAdd(delay)
        );
    }

    public void sendAreaSound(int id, int type, int delay, int x, int y, int distance) {
        /*sendMapPacket(x, y, player.getHeight(), offsetHash ->
                new OutBuffer(8).sendFixedPacket(ServerPacket.SOUND_AREA.getPacketId())
                        .addByte(distance)
                        .addShortAdd(id)
                        .addByteSub(1) // drop off range
                        .addByteSub(type) // loops
                        .addByte(delay)
                        .addByteAdd(offsetHash)
        );*/
    }

    public void sendSoundEffect(int id, int type, int delay) {
        OutBuffer out = new OutBuffer(6).sendFixedPacket(ServerPacket.SYNTH_SOUND.getPacketId())
                .addShort(id)
                .addByte(type)
                .addShort(delay);
        write(out);
    }

    public void sendMusic(int id) {
        sendMusic(id, 0, 0, 0, 0);
    }

    public void sendMusic(int id, int fadeOutDelay, int fadeOutSpeed, int fadeInDelay, int fadeInSpeed) {
        OutBuffer out = new OutBuffer(11).sendFixedPacket(ServerPacket.MIDI_SONG_V2.getPacketId())
                .addShortAdd(fadeOutDelay)
                .addLEShortAdd(fadeOutSpeed)
                .addLEShort(fadeInDelay)
                .addLEShortAdd(id)
                .addShortAdd(fadeInSpeed);
        write(out);
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendDropTable(String name, int petId, int petAverage, List<Integer[]> drops) {
        OutBuffer out = new OutBuffer(3 + Protocol.strLen(name) + 4 + (drops.size() * 13)).sendVarShortPacket(90)
                .addString(name)
                .addShort(petId)
                .addShort(petAverage);
        for(Integer[] drop : drops) {
            out.addShort(drop[0]);  //id
            out.addByte(drop[1]);   //broadcast
            out.addInt(drop[2]);    //min amount
            out.addInt(drop[3]);    //max amount
            out.addInt(drop[4]);  //average
        }
        write(out);
    }

    public void sendTaskInterface(List<String> tasks, List<Integer> points, List<Boolean> completeTasks, List<Integer> areas, int playerPoints, int areaFilter, int skillFilter, int tierFilter, int completedFilter, int sortBy) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(92).addShort(tasks.size());
        for (String name : tasks) {
            out.addString(name);
        }
        for (Integer point : points) {
            out.addByte(point);
        }
        for (boolean complete : completeTasks) {
            out.addByte(complete ? 1 : 0);
        }
        for (Integer area : areas) {
            out.addByte(area);
        }
        out.addShort(playerPoints);
        out.addByte(areaFilter);
        out.addByte(skillFilter);
        out.addByte(tierFilter);
        out.addByte(completedFilter);
        out.addByte(sortBy);
        write(out);
    }

    public void sendTaskFilterInterface(String[] filters, String sort) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(93);
        out.addString(sort);
        for (String filter : filters) {
            out.addString(filter);
        }
        write(out);
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendLoyaltyRewards(int dayReward, int currentSpree, int highestSpree, int totalClaimedRewards, Item... loyaltyRewards) {
        /*OutBuffer out = new OutBuffer(3 + 1 + 12 + (loyaltyRewards.length * 8)).sendVarShortPacket(93)
                .addByte(dayReward)
                .addInt(currentSpree)
                .addInt(highestSpree)
                .addInt(totalClaimedRewards);
        for(Item reward : loyaltyRewards) {
            out.addInt(reward.getId());
            out.addInt(reward.getAmount());
        }
        write(out);*/
    }
    //TODO: 184 Revision Fix Custom Packet
//    public void sendBuyCredits(String message, int discountPercent, int selectedCreditPack, int selectedPaymentMethod, BuyCredits... packs) {
//        /*OutBuffer out = new OutBuffer(3 + Protocol.strLen(message) + 3 + (packs.length * 12)).sendVarShortPacket(12)
//                .addString(message)
//                .addByte(discountPercent)
//                .addByte(selectedCreditPack)
//                .addByte(selectedPaymentMethod);
//        for(BuyCredits pack : packs) {
//            out.addInt(pack.purchasePrice);
//            out.addInt(pack.purchaseAmount);
//            out.addInt(pack.freeAmount);
//        }*/
//        //todo@@ write(out);
//    }

    public void sendWidget(Widget widget, int seconds) {
        if (true) return;
        OutBuffer out = new OutBuffer(4).sendVarShortPacket(91)
                .addByte(widget.getSpriteId())
                .addShort(seconds * 50)
                .addString(widget.getName())
                .addString(widget.getDescription());
        write(out);
    }

    public void sendPlayerHead(int parentId, int childId) {
        OutBuffer out = new OutBuffer(5).sendFixedPacket(ServerPacket.IF_SETPLAYERHEAD.getPacketId())
                .addIMEInt(parentId << 16 | childId);
        write(out);
    }

    public void sendNpcHead(int parentId, int childId, int npcId) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(ServerPacket.IF_SETNPCHEAD.getPacketId())
                .addLEShort(npcId)
                .addLEInt(parentId << 16 | childId);
        write(out);
    }

    public void animateInterface(int parentId, int childId, int animationId) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(ServerPacket.IF_SETANIM.getPacketId())
                .addShort(animationId)
                .addMEInt(parentId << 16 | childId);
        write(out);
    }

    public void sendMapState(int state) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(ServerPacket.MINIMAP_TOGGLE.getPacketId())
                .addByte(state);
        write(out);
    }

    public void sendHintIcon(Entity target) {
        OutBuffer out = new OutBuffer(8).sendFixedPacket(ServerPacket.HINT_ARROW.getPacketId())
                .addByte(target.player == null ? 1 : 10)
                .addShort(target.getIndex())
                .skip(3);
        write(out);
    }

    public void sendHintIcon(int x, int y) {
        OutBuffer out = new OutBuffer(8).sendFixedPacket(ServerPacket.HINT_ARROW.getPacketId())
                .addByte(2)
                .addShort(x)
                .addShort(y)
                .addByte(1);    // height
        write(out);
    }

    public void sendHintIcon(Position position) {
        OutBuffer out = new OutBuffer(8).sendFixedPacket(ServerPacket.HINT_ARROW.getPacketId())
                .addByte(2)
                .addShort(position.getX())
                .addShort(position.getY())
                .addByte(1);    // height
        write(out);
    }

    public void resetHintIcon(boolean npcType) {
        OutBuffer out = new OutBuffer(8).sendFixedPacket(ServerPacket.HINT_ARROW.getPacketId())
                .addByte(npcType ? 1 : 10)
                .addShort(-1)
                .skip(3);
        write(out);
    }

    public void turnCameraToLocation(int x, int y, int cameraHeight, int constantSpeed, int variableSpeed) {
        Position pos = new Position(x, y, 0);
        int posX = pos.getSceneX(player.getPosition());
        int posY = pos.getSceneY(player.getPosition());
        OutBuffer out = new OutBuffer(7).sendFixedPacket(ServerPacket.CAM_ROTATETO.getPacketId())
                .addByte(posX)
                .addByte(posY)
                .addShort(cameraHeight)
                .addByte(constantSpeed)
                .addByte(variableSpeed);
        write(out);
    }

    public void moveCameraToLocation(int x, int y, int cameraHeight, int constantSpeed, int variableSpeed) {
        Position pos = new Position(x, y, 0);
        int posX = pos.getSceneX(player.getPosition());
        int posY = pos.getSceneY(player.getPosition());
        OutBuffer out = new OutBuffer(7).sendFixedPacket(ServerPacket.CAM_MOVETO.getPacketId())
                .addByte(posX)
                .addByte(posY)
                .addShort(cameraHeight)
                .addByte(constantSpeed)
                .addByte(variableSpeed);
        write(out);
    }

    /**
     * @param shakeType 0 shakes X, 1 shakes Z, 2 shakes Y, 3 shakes Yaw, 4 shakes Pitch
     */
    public void shakeCamera(int shakeType, int intensity) {
        OutBuffer out = new OutBuffer(5).sendFixedPacket(ServerPacket.CAM_SHAKE.getPacketId())
                .addByte(shakeType)
                .addByte(intensity)
                .addByte(intensity)
                .addByte(intensity);
        write(out);
    }

    public void resetCamera() {
        OutBuffer out = new OutBuffer(1).sendFixedPacket(ServerPacket.CAM_RESET.getPacketId());
        write(out);
    }

    public void sendActiveWorld() {
        OutBuffer out = new OutBuffer(6).sendFixedPacket(ServerPacket.SET_ACTIVE_WORLD.getPacketId())
                .addByte(0)     // 0 for top level, 1 for world entity
                .addShort(0)    // WorldID
                .addByte(player.getHeight());
        write(out);
    }

    public void sendNPCUpdateOrigin() {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(ServerPacket.SET_NPC_UPDATE_ORIGIN.getPacketId())
                .addByte(Position.getLocal(player.getAbsX(), player.getPosition().getFirstChunkX()))
                .addByte(Position.getLocal(player.getAbsY(), player.getPosition().getFirstChunkY()))
        ;
        write(out);
    }

    public void rebuildWorldEntities() {
        OutBuffer out = new OutBuffer(9).sendVarShortPacket(ServerPacket.REBUILD_WORLDENTITY.getPacketId())
                .addShort(0)
                .addShort(0)
                .addShort(0)
                .addShort(0);
        write(out);
    }

    public void hideObjectOptions(boolean state) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(ServerPacket.HIDEOBJOPS.getPacketId())
                .addByte(state ? 1 : 0);
        write(out);
    }

    public void hideNPCOptions(boolean state) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(ServerPacket.HIDENPCOPS.getPacketId())
                .addByte(state ? 1 : 0);
        write(out);
    }

    public void hideGroundItemOptions(boolean state) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(ServerPacket.HIDELOCOPS.getPacketId())
                .addByte(state ? 1 : 0);
        write(out);
    }
}