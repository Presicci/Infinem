package io.ruin.model.content.waystones;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Waystone {
    // Varrock? Catherby?
    FALADOR(40408, new Position(2968, 3385), new Position(2967, 3385)),
    LUMBRIDGE(40409, new Position(3238, 3222), new Position(3237, 3222)),
    ARDOUGNE(40410, new Position(2680, 3308), new Position(2681, 3307)),
    RELLEKKA(40411, new Position(2664, 3648), new Position(2663, 3649)),
    POLLNIVNEACH(40412, new Position(3355, 2969), new Position(3357, 2969)),
    CANIFIS(40413, new Position(3494, 3493), new Position(3495, 3492)),
    PRIFDDINAS(40414, new Position(3266, 6068), new Position(3265, 6068)),
    BRIMHAVEN(40415, new Position(2804, 3186), new Position(2805, 3185)),
    FEROX_ENCLAVE(40416, new Position(3146, 3631), new Position(3146, 3630)),
    KOUREND(40414, new Position(1664, 3674), new Position(1662, 3676)),
    VARROCK(40409, new Position(3223, 3426), new Position(3223, 3428)),
    CAMELOT(40410, new Position(2751, 3480), new Position(2751, 3479));

    private final int objectId;
    private final Position objectPosition, teleportPostion;

    public String getName() {
        return StringUtils.fixCaps(name().replace("_", " "));
    }
}
