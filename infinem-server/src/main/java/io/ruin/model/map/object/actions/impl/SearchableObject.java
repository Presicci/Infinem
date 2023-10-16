package io.ruin.model.map.object.actions.impl;

import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public enum SearchableObject {

    private final int objectId;
    private final Position position;
    private final String option;
    private final Item reward;

    SearchableObject(int objectId, Position position) {
        this(objectId, position, "search", null);
    }

    SearchableObject(int objectId, Position position, String option, Item reward) {
        this.objectId = objectId;
        this.position = position;
        this.option = option;
        this.reward = reward;
    }

    static {
        for (SearchableObject object : values()) {
            ObjectAction.register(object.objectId, object.position, object.option, ((player, obj) -> {
                if (object.reward == null) {
                    player.sendMessage("You find nothing interesting.");
                    return;
                }
                Item reward = object.reward;
                if (!player.getInventory().hasRoomFor(reward)) {
                    player.sendMessage("You find something, but don't have enough inventory space to take it.");
                    return;
                }
                player.getInventory().add(reward);
                player.sendMessage("You find " + (reward.getAmount() > 1 ? " some " : " a ") + " " + reward.getDef().name + ".");
            }));
        }
    }
}
