package io.ruin.model.map.object.actions.impl;

import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Misc;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/16/2021
 */
@AllArgsConstructor
public enum Signpost {

    VARROCK_SOUTH_FORK(7141, 3268, 3332,
            "Sheep lay this way.",
            "East to Al Kharid mine and follow the path north to Varrock east gate.",
            "South through farms to Al Kharid and Lumbridge.",
            "West to Champions' Guild and Varrock south gate."
    ),

    LUMBRIDGE_CASTLE(18493, 3235, 3228,
            "Head north towards Fred's farm and the windmill.",
            "Cross the bridge and head east to Al Kharid or north to Varrock.",
            "South to the swamps of Lumbridge.",
            "West to the Lumbridge Castle and Draynor Village. Beware the goblins!"
    ),

    EAST_LUMBRIDGE(18493, 3261, 3230,
            "North to farms and Varrock.",
            "East to Al Kharid - toll gate; bring some money.",
            "The River Lum lies to the south.",
            "West to Lumbridge."
    ),

    DRAYNOR(18493, 3107, 3296,
            "North to Draynor Manor.",
            "East to Lumbridge.",
            "South to Draynor Village and the Wizards' Tower.",
            "West to Port Sarim, Falador and Rimmington."
    ),

    NORTH_OF_AL_KHARID(7141, 3283, 3333,
            "North to one of Varrock's mines.",
            "East to the Digsite's exam center.",
            "South past the mine to Al Kharid.",
            "West to Lumbridge and Varrock south gate."
    ),

    SOUTH_OF_FALADOR(18493, 2983, 3278,
            "Mysterious ruins dominate the forest to the north.",
            "East to Faldor and Port Sarim.",
            "Rimmington lies to the south.",
            "The Crafting Guild occupies the shore to the west."
    );

    private final int objectId, x, y;
    private final String north, east, south, west;

    static {
        // Loops through the enum and statically assigns actions to each signpost
        for (Signpost signpost : values()) {
            GameObject object = Tile.getObject(signpost.objectId, signpost.x, signpost.y, 0);
            object.skipReachCheck = p -> Misc.getDistance(object.x, object.y, p.getX(), p.getY()) <= 5;
            ObjectAction.register(signpost.objectId, signpost.x, signpost.y, 0, "Read", (player, obj) -> {
                // Open the signpost interface
                player.openInterface(InterfaceType.MAIN, 135);
                // Move the camera so its facing north
                player.getPacketSender().sendClientScript(143, "ii", 280, 0);
                player.getPacketSender().moveCameraToLocation(3235, 3221, 1500, 2, 10);
                player.getPacketSender().turnCameraToLocation(3235, 3230, 450, 2, 10);
                // Tanned background behind interface
                //player.getPacketSender().sendClientScript(917, "ii", 10786175, 200);
                // Assign the corresponding directions to the signpost
                player.getPacketSender().sendString(135, 2, signpost.north);
                player.getPacketSender().sendString(135, 7, signpost.east);
                player.getPacketSender().sendString(135, 8, signpost.south);
                player.getPacketSender().sendString(135, 11, signpost.west);
                //
                player.getPacketSender().sendClientScript(2524, "ii", -1, -1);
            });
        }
        InterfaceHandler.register(135, h -> {
            h.closedAction = (player, integer) -> player.getPacketSender().resetCamera();
        });
    }
}
