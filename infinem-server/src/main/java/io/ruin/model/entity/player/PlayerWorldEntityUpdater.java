package io.ruin.model.entity.player;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.ServerPacket;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.worldentity.WorldEntity;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/11/2024
 */
public class PlayerWorldEntityUpdater {

    private static final int RENDER_DISTANCE = 15;

    private final Player player;

    protected ArrayList<WorldEntity> localWorldEntities = new ArrayList<WorldEntity>();

    public PlayerWorldEntityUpdater(Player player) {
        this.player = player;
    }

    public void process() {
        OutBuffer out = new OutBuffer(0xff).sendVarShortPacket(ServerPacket.WORLDENTITY_INFO.getPacketId());
        out.addByte(localWorldEntities.size());
        if (!localWorldEntities.isEmpty()) {
            for (Iterator<WorldEntity> it = localWorldEntities.iterator(); it.hasNext(); ) {
                WorldEntity we = it.next();
                if (!writeLocalUpdate(out, we)) {
                    it.remove();
                    //we.removePlayer(player);
                }
            }
        }
        // Current world
        int currentWorld = -1;
        out.addShort(currentWorld);
        out.addByte(13); // x size
        out.addByte(13); // y size
        out.addByte(Position.getLocal(player.getAbsX(), player.getPosition().getFirstChunkX()));
        out.addByte(Position.getLocal(player.getAbsY(), player.getPosition().getFirstChunkY()));
        //out.addByte(0);  // xcoord
        //out.addByte(0);  // ycoord
        out.addShort(0); // angle
        out.addShort(1);
        player.getPacketSender().write(out);
        //System.out.println(Arrays.toString(out.payload()));
        //player.getPacketSender().sendWorldEntity(); // keep moving this?
        player.getPacketSender().sendActiveWorld();
    }

    private boolean writeLocalUpdate(OutBuffer out, WorldEntity we) {
//        if (remove) {
//            out.addByte(0);
//            return false;
//        }
//        out.addByte(1);
//        out.addByte();  // level
//        out.addByte();  // deltaX
//        out.addByte();  // deltaY
//        out.addShort(); // rotation angle
//        out.addByte();  // movespeed
        return true;
    }
}
