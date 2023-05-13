package io.ruin.network.incoming;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.PackageLoader;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.IdHolder;

public interface Incoming {

    Incoming[] HANDLERS = new Incoming[256];

    int[] OPTIONS = new int[256];

    boolean[] IGNORED = new boolean[256];

    int[] SIZES = new int[256];

    static void load() throws Exception {
        for(Class c : PackageLoader.load("io.ruin.network.incoming.handlers", Incoming.class)) {
            Incoming incoming = (Incoming) c.newInstance();
            IdHolder idHolder = (IdHolder) c.getAnnotation(IdHolder.class);
            if(idHolder == null) {
                /* handler is disabled, most likely for upgrading */
                continue;
            }
            int option = 1;
            for(int id : idHolder.ids()) {
                HANDLERS[id] = incoming;
                OPTIONS[id] = option++;
            }
        }
        /**
         * Ignored
         */
        int[] ignored = {
                82,
                86,
                61,
                64,
                7,
                17,
                1,
                58

        };
        for(int opcode : ignored)
            IGNORED[opcode] = true;
        /**
         * Sizes
         */
        for(int i = 0; i < SIZES.length; i++)
            SIZES[i] = Byte.MIN_VALUE;
        SIZES[0] = -1;
        SIZES[1] = -2;
        SIZES[2] = 8;
        SIZES[3] = 15;
        SIZES[4] = 16;
        SIZES[5] = -1;
        SIZES[6] = 3;
        SIZES[7] = 4;
        SIZES[8] = 3;
        SIZES[9] = 7;
        SIZES[10] = 8;
        SIZES[11] = 3;
        SIZES[12] = 16;
        SIZES[13] = 8;
        SIZES[14] = -1;
        SIZES[15] = 16;
        SIZES[16] = 2;
        SIZES[17] = 6;
        SIZES[18] = 3;
        SIZES[19] = 7;
        SIZES[20] = 15;
        SIZES[21] = -1;
        SIZES[22] = 4;
        SIZES[23] = 8;
        SIZES[24] = 5;
        SIZES[25] = 4;
        SIZES[26] = -1;
        SIZES[27] = -1;
        SIZES[28] = 8;
        SIZES[29] = 7;
        SIZES[30] = 3;
        SIZES[31] = 8;
        SIZES[32] = -1;
        SIZES[33] = 6;
        SIZES[34] = 8;
        SIZES[35] = -1;
        SIZES[36] = 3;
        SIZES[37] = 7;
        SIZES[38] = 2;
        SIZES[39] = 8;
        SIZES[40] = 11;
        SIZES[41] = 7;
        SIZES[42] = 8;
        SIZES[43] = 15;
        SIZES[44] = 7;
        SIZES[45] = 3;
        SIZES[46] = 3;
        SIZES[47] = 8;
        SIZES[48] = 4;
        SIZES[49] = -1;
        SIZES[50] = 2;
        SIZES[51] = 8;
        SIZES[52] = 3;
        SIZES[53] = 11;
        SIZES[54] = 8;
        SIZES[55] = -1;
        SIZES[56] = 8;
        SIZES[57] = -1;
        SIZES[58] = 0;
        SIZES[59] = -1;
        SIZES[60] = 4;
        SIZES[61] = 1;
        SIZES[62] = 9;
        SIZES[63] = 9;
        SIZES[64] = 0;
        SIZES[65] = 8;
        SIZES[66] = -1;
        SIZES[67] = 0;
        SIZES[68] = 13;
        SIZES[69] = 7;
        SIZES[70] = 3;
        SIZES[71] = -2;
        SIZES[72] = 7;
        SIZES[73] = 0;
        SIZES[74] = -1;
        SIZES[75] = 14;
        SIZES[76] = 10;
        SIZES[77] = -1;
        SIZES[78] = 8;
        SIZES[79] = -1;
        SIZES[80] = 7;
        SIZES[81] = 2;
        SIZES[82] = -1;
        SIZES[83] = 8;
        SIZES[84] = 7;
        SIZES[85] = 2;
        SIZES[86] = -1;
        SIZES[87] = -1;
        SIZES[88] = 3;
        SIZES[89] = 0;
        SIZES[90] = 3;
        SIZES[91] = -2;
        SIZES[92] = 8;
        SIZES[93] = -1;
        SIZES[94] = -1;
        SIZES[95] = 3;
        SIZES[96] = 8;
        SIZES[97] = -1;
        SIZES[98] = 8;
        SIZES[99] = -1;
        SIZES[100] = 7;
        SIZES[101] = 3;
        SIZES[102] = 15;
        SIZES[103] = 11;
        SIZES[104] = 8;
        SIZES[105] = 11;
    }

    /**
     * Separator
     */

    void handle(Player player, InBuffer in, int opcode);

}
