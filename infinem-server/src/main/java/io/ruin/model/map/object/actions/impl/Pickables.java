package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

public class Pickables {

    @AllArgsConstructor
    public enum Nodes {
        FLAX(14896, 1779),
        CABBAGE(1161, 1965),
        WHEAT(15506, 1947),
        WHEAT1(15507, 1947),
        WHEAT2(15508, 1947),
        POTATO(312, 1942),
        ONION(3366, 1957);

        public final int objectId, itemId;

        private void pick(Player player, GameObject obj) {
            if(player.getInventory().isFull()) {
                player.sendMessage("You can't carry any more " + ItemDef.get(itemId).name.toLowerCase() + ".");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.animate(827);
                event.delay(1);
                player.getInventory().add(itemId, 1);
                player.sendMessage("You pick some " + ItemDef.get(itemId).name.toLowerCase() + ".");
                if(Random.rollDie(6, 1))
                    remove(obj);
                player.unlock();
            });
        }

        private void remove(GameObject obj) {
            World.startEvent(event -> {
                obj.remove();
                event.delay(20);
                obj.restore();
            });
        }
    }

    static {
        for (Nodes node : Nodes.values()) {
            ObjectAction.register(node.objectId, "pick", node::pick);
        }
    }

}