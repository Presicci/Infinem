package io.ruin.model.entity.attributes;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/16/2021
 */
public enum AttributeKey {

    GRAIN_IN_HOPPER
    ;

    private String saveName;
    private AttributeType type;

    AttributeKey() {}

    AttributeKey(String saveName, AttributeType type) {
        this.saveName = saveName;
        this.type = type;
    }

    public String saveName() {
        return saveName;
    }

    public AttributeType saveType() {
        return type;
    }
}
