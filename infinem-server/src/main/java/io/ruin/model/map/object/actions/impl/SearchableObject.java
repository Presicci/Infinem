package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public enum SearchableObject {
    SACK(365);

    private final int objectId;
    private final Position position;
    private final String option;
    private final Item reward;

    SearchableObject(int objectId) {
        this(objectId, null, "search", null);
    }

    SearchableObject(int objectId, Position position) {
        this(objectId, position, "search", null);
    }

    SearchableObject(int objectId, Position position, String option, Item reward) {
        this.objectId = objectId;
        this.position = position;
        this.option = option;
        this.reward = reward;
    }

    public void search(Player player) {
        if (reward == null) {
            player.sendMessage("You find nothing interesting.");
            return;
        }
        if (!player.getInventory().hasRoomFor(reward)) {
            player.sendMessage("You find something, but don't have enough inventory space to take it.");
            return;
        }
        player.getInventory().add(reward);
        player.sendMessage("You find " + (reward.getAmount() > 1 ? " some " : " a ") + " " + reward.getDef().name + ".");
    }

    static {
        for (SearchableObject object : values()) {
            if (object.position != null)
                ObjectAction.register(object.objectId, object.position, object.option, (player, obj) -> object.search(player));
            else
                ObjectAction.register(object.objectId, object.option, (player, obj) -> object.search(player));
        }
    }
}
