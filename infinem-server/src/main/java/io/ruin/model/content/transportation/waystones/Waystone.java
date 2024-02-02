package io.ruin.model.content.transportation.waystones;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Waystone {
    LUMBRIDGE(40409, new Position(3238, 3222), new Position(3237, 3222)),
    VARROCK(40409, new Position(3223, 3426), new Position(3223, 3428)),
    FALADOR(40408, new Position(2968, 3385), new Position(2967, 3385)),
    CAMELOT(40410, new Position(2751, 3480), new Position(2751, 3479)),
    POLLNIVNEACH(40412, new Position(3355, 2969), new Position(3357, 2969)),
    RELLEKKA(40411, new Position(2664, 3648), new Position(2663, 3649)),
    KOUREND(49994, new Position(1663, 3676), new Position(1662, 3676)),
    FEROX_ENCLAVE(40416, new Position(3146, 3631), new Position(3146, 3630)),
    PRIFDDINAS(40414, new Position(3266, 6068), new Position(3265, 6068)),
    ARDOUGNE(40410, new Position(2680, 3308), new Position(2681, 3307)),
    BRIMHAVEN(40415, new Position(2804, 3186), new Position(2805, 3185)),
    CANIFIS(40413, new Position(3494, 3493), new Position(3495, 3492));

    private final int objectId;
    private final Position objectPosition, teleportPostion;

    public String getName() {
        return StringUtils.fixCaps(name().replace("_", " "));
    }
}
