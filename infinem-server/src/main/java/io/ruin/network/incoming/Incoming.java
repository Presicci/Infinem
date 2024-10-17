package io.ruin.network.incoming;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.PackageLoader;
import io.ruin.model.entity.player.Player;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.IdHolder;

public interface Incoming {

    Incoming[] HANDLERS = new Incoming[256];

    int[] OPTIONS = new int[256];

    boolean[] IGNORED = new boolean[256];

    static void load() throws Exception {
        for(Class c : PackageLoader.load("io.ruin.network.incoming.handlers", Incoming.class)) {
            Incoming incoming = (Incoming) c.newInstance();
            IdHolder idHolder = (IdHolder) c.getAnnotation(IdHolder.class);
            ClientPacketHolder cpHolder = (ClientPacketHolder) c.getAnnotation(ClientPacketHolder.class);
            if (idHolder != null) {
                int option = 1;
                for(int id : idHolder.ids()) {
                    HANDLERS[id] = incoming;
                    OPTIONS[id] = option++;
                }
            } else if (cpHolder != null) {
                int option = 1;
                for(ClientPacket packet : cpHolder.packets()) {
                    HANDLERS[packet.packetId] = incoming;
                    OPTIONS[packet.packetId] = option++;
                }
            }
        }
        /**
         * Ignored
         */
        int[] ignored = {
                /*82,
                86,
                61,
                64,
                7,
                17,
                1,
                58*/

        };
        for(int opcode : ignored)
            IGNORED[opcode] = true;
    }

    /**
     * Separator
     */

    void handle(Player player, InBuffer in, int opcode);

}
