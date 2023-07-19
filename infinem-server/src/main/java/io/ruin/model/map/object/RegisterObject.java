package io.ruin.model.map.object;

import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.Getter;

public class RegisterObject {

    @Getter private final int objectId;
    @Getter private final Position position;

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

    public RegisterObject(int objectId) {
        this(objectId, 0, 0);
    }

    public void register(String option, ObjectAction objectAction) {
        if (getPosition().getY() == 0 && getPosition().getX() == 0)
            ObjectAction.register(getObjectId(), option, objectAction);
        else
            ObjectAction.register(getObjectId(), getPosition(), option, objectAction);
    }
}
