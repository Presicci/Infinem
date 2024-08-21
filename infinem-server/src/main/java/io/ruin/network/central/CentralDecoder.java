package io.ruin.network.central;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginRequest;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.process.LoginWorker;
import kilim.Pausable;
import kilim.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CentralDecoder extends MessageDecoder {

    private volatile byte response;

    private CountDownLatch latch = new CountDownLatch(1);

    protected long lastMessageAt = System.currentTimeMillis();

    protected CentralDecoder() {
        super(null, false);
    }

    protected final void awaitResponse() throws IOException {
        try {
            latch.await(10L, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            /* ignored */
        }
        latch = null;
        if(response != 1)
            throw new IOException("Connection Denied: " + response);
    }

    @Override
    protected void handle(Object object, InBuffer in, int opcode) {
        lastMessageAt = System.currentTimeMillis();
        if(opcode == 0) {
            /**
             * Ping
             */
            return;
        }
        if(opcode == 1) {
            /**
             * Connection response
             */
            response = in.readByte();
            latch.countDown();
            return;
        }
        if(opcode == 2) {
            /**
             * Login response
             */
            Response response = Response.valueOf(in.readUnsignedByte());
            String name = in.readString();
            LoginRequest request = LoginWorker.get(name);
            if(request == null)
                return;
            if(response != Response.SUCCESS) {
                request.deny(response);
                return;
            }
            int userId = in.readInt();
            String saved = in.readString();
            byte unreadPMs = in.readByte();
            List<Integer> groupIds = null;
            while(in.remaining() > 0) {
                if(groupIds == null)
                    groupIds = new ArrayList<>();
                groupIds.add(in.readUnsignedByte());
            }
            request.info.update(userId, name, saved, groupIds, unreadPMs);
            request.success();
            return;
        }
        if(opcode == 3) {
            /**
             * Save response
             */
            int userId = in.readInt();
            int attempt = in.readUnsignedByte();
            Player player = World.getPlayer(userId, false);
            if(player != null)
                player.finishLogout(attempt);
            return;
        }
        if(opcode == 4) {
            /**
             * Online check
             */
            int userId = in.readInt();
            if(World.getPlayer(userId, false) == null) {
                System.out.println("Logging out 'stuck' user: " + userId);
                CentralClient.sendLogout(userId);
            }
            return;
        }
        if(opcode == 98 || opcode == 99) {
            /**
             * Client packet
             */
            // Translate packet to client packet
            int userId = in.readInt();
            InBuffer bufferClone = new InBuffer(in.getPayload().clone());
            OutBuffer out = new OutBuffer(in.remaining());
            while(in.remaining() > 0)
                out.addByte(in.readByte());
            // Parse packet for local changes
            bufferClone.readInt();

            Set<String> onlineFriends = null;
            switch (bufferClone.readByte()) {
                case 17:    // Friends list update
                    onlineFriends = new HashSet<>();
                    bufferClone.readUnsignedShort();
                    while (bufferClone.remaining() > 0) {
                        bufferClone.readUnsignedByte();
                        String name = bufferClone.readString();
                        bufferClone.readString();
                        int worldId = bufferClone.readUnsignedShort();
                        bufferClone.readUnsignedByte();
                        bufferClone.readUnsignedByte();
                        if (worldId > 0) {
                            onlineFriends.add(name);
                            bufferClone.skip(6);
                        }
                        bufferClone.skip(1);
                    }
                    break;
                case 7:     // Ignore
                    /*
                    for (SocialMember ignore : ignores) {
                        out.addByte(ignore.sendNewName() ? 1 : 0);
                        out.addString(ignore.name);
                        out.addString(ignore.lastName);
                        out.skip(1);
                    }
                     */
                    break;
                case 12:    // Privacy
                    /*
                    OutBuffer out = new OutBuffer(2).sendFixedPacket(12).addByte(privacy);
                     */
                    break;
                case 21:    // Receive pm
                    /*
                    OutBuffer out = new OutBuffer(255).sendVarShortPacket(21)
                    .addString(fromName);
                    for (int i = 0; i < 5; ++i) {
                        out.addByte(Random.get(255));
                    }
                    out.addByte(fromRank);
                    Huffman.encrypt(out, message);
                     */
                    break;
                case 22:    // Get message buffer
                    /*
                    OutBuffer out = new OutBuffer(255).sendVarBytePacket(22);
                    out.addString(senderName);
                    out.addLong(StringUtils.stringToLong(this.name));
                    for (int i = 0; i < 5; ++i) {
                        out.addByte(Random.get(255));
                    }
                    out.addByte(rankId);
                    Huffman.encrypt(out, message);
                    return out;
                     */
                    break;
                case 23:    // Send string
                    /*
                    OutBuffer out = new OutBuffer(3 + 4 + Protocol.strLen(string))
                    .sendVarShortPacket(23)
                    .writeStringCp1252NullTerminated(string)
                    .addInt(interfaceId << 16 | childId);
                     */
                    break;
                case 42:
                    /*
                    private OutBuffer getLeaveBuffer() {
                        return this.getBuffer(0);
                    }

                    private OutBuffer getChannelBuffer() {
                        return this.getBuffer(1);
                    }

                    private OutBuffer getSettingsBuffer() {
                        return this.getBuffer(2);
                    }



                    if (type == 0) {
                        return new OutBuffer(3).sendVarShortPacket(42);
                    }
                    OutBuffer out = new OutBuffer(255).sendVarShortPacket(42).
                            addString(this.owner).
                            addLong(StringUtils.stringToLong(this.name)).//addString(this.name).
                                    addByte(ClanChat.getRankId(this.kickRank));
                    if (type == 2) {
                        out.addByte(255);
                        return out;
                    }
                    out.addByte(this.ccMembersCount);
                    for (int i = 0; i < this.ccMembersCount; ++i) {
                        SocialMember member = this.ccMembers[i];
                        out.addString(member.name);
                        out.addShort(member.worldId);
                        out.addByte(this.getRankId(member.name));
                        out.addByte(0);
                    }
                    return out;
                     */
                    break;
                case 56:    // Send pm
                    /*
                    OutBuffer out = new OutBuffer(255).sendVarShortPacket(56)
                    .addString(toUsername);
                    Huffman.encrypt(out, message);
                     */
                    break;
                case 66:
                    break;
            }
            Set<String> finalOnlineFriends = onlineFriends;
            new Task() { //todo - come back to this
                @Override
                public void execute() throws Pausable {
                    long sleepPeriod = 10L;
                    long timeoutPeriod = 5000L;
                    while(true) {
                        Player player = World.getPlayer(userId, true);
                        if(player != null && player.isOnline()) {
                            Server.worker.execute(() -> player.getPacketSender().write(out));
                            if (finalOnlineFriends != null) {
                                player.onlineFriendNames = finalOnlineFriends;
                            }
                            return;
                        }
                        if(timeoutPeriod < sleepPeriod)
                            return;
                        timeoutPeriod -= sleepPeriod;
                        Task.sleep(sleepPeriod);
                    }
                }
            }.start();
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch(opcode) {
            case 0:  return 0;          //ping
            case 1:  return 1;          //connected
            case 2:  return VAR_INT;    //login response
            case 3:  return 5;          //saved response
            case 4:  return 4;          //online check
            case 98: return VAR_BYTE;   //client packet
            case 99: return VAR_SHORT;  //client packet
        }
        return UNHANDLED;
    }

}