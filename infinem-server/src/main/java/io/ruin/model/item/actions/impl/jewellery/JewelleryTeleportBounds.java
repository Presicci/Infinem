package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
@Getter
@AllArgsConstructor
public enum JewelleryTeleportBounds {
    // Amulet of Glory
    EDGEVILLE(new Bounds(3086, 3494, 3088, 3499, 0)),
    KARAMJA(new Bounds(2916, 3174, 2919, 3176, 0)),
    DRAYNOR_VILLAGE(new Bounds(3104, 3249, 3105, 3253, 0)),
    AL_KHARID(new Bounds(3291, 3162, 3295, 3163, 0)),

    // Ring of Wealth
    MISCELLANIA(new Bounds(2535, 3860, 2538, 3863, 0)),
    GRAND_EXCHANGE(new Bounds(3162, 3478, 3167, 3479, 0)),
    FALADOR(new Bounds(2994, 3374, 2997, 3376, 0)),
    DONDAKAN(new Bounds(2829, 10164, 2833, 10167, 0)),
    ;

    private final Bounds bounds;
}
