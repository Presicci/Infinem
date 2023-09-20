package io.ruin.model.map.object.actions.impl;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public enum UnimplementedObject {
    Soul_Wars(41199, new Position(2199, 2842)),
    Soul__Wars(41200, new Position(2220, 2842)),
    The_Shayzien_Agility_Course(42209, new Position(1554, 3631));

    private final int objectId, option;
    private final Position objectPosition;

    UnimplementedObject(int objectId, Position position) {
        this(objectId, position, 1);
    }

    UnimplementedObject(int objectId, Position position, int option) {
        this.objectId = objectId;
        this.objectPosition = position;
        this.option = option;
    }

    static {
        for (UnimplementedObject obj : values()) {
            ObjectAction.register(obj.objectId, obj.objectPosition, obj.option, ((p, o) -> p.sendMessage(obj.name().replace("__", " ").replace("_", " ") + " is not implemented yet.")));
        }
    }
}
