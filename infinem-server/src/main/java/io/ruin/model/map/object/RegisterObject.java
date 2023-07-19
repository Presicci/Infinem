package io.ruin.model.map.object;

import io.ruin.model.map.Position;
import lombok.Getter;

public class RegisterObject {

    @Getter private int objectId;
    @Getter private Position position;

    public RegisterObject(int objectId, Position position) {
        this.objectId = objectId;
        this.position = position;
    }

    public RegisterObject(int objectId, int x, int y) {
        this(objectId, new Position(x, y, 0));
    }

    public RegisterObject(int objectId, int x, int y, int z) {
        this(objectId, new Position(x, y, z));
    }
}
