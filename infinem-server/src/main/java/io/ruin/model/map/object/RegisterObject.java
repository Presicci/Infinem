package io.ruin.model.map.object;

import io.ruin.model.map.Position;
import lombok.Getter;

public class RegisterObject {

    @Getter private final int objectId;
    @Getter private final Position position;

    public RegisterObject(int objectId, Position position) {
        this.objectId = objectId;
        this.position = position;
        if (position.getX() == 0 || position.getY() == 0)
            System.err.println("ObjectID " + objectId + " being registered with missing coords: " + position);
    }

    public RegisterObject(int objectId, int x, int y) {
        this(objectId, new Position(x, y, 0));
    }

    public RegisterObject(int objectId, int x, int y, int z) {
        this(objectId, new Position(x, y, z));
    }
}
