package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

/**
 * Class that handles berry bush picking, pretty similar to flax/crop picking.
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class BushPickables {

    @AllArgsConstructor
    public enum Nodes {
        REDBERRY(23628, 1951),
        CADAVABERRY(23625, 753);

        private final int objectId, itemId;

        /**
         * Pick a berry from the bush.
         * @param player The player picking.
         * @param obj The bush game object.
         */
        private void pick(Player player, GameObject obj) {
            if (obj.id == objectId + 2) {
                player.sendMessage("There is nothing left on the bush.");
                return;
            }
            if (player.getInventory().isFull()) {
                player.sendMessage("You can't carry any more " + ItemDef.get(itemId).name.toLowerCase() + ".");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.animate(2282);
                event.delay(1);
                player.getInventory().add(itemId, 1);
                player.sendMessage("You pick some " + ItemDef.get(itemId).name.toLowerCase() + ".");
                changeObject(obj);
                player.unlock();
            });
        }

        /*
         * If bush is full, remove a berry with no respawn timer.
         * If bush is half-full, remove berry with respawn timer.
         */
        private void changeObject(GameObject obj) {
            if (obj.id == obj.originalId) {
                World.startEvent(event -> {
                    obj.setId(obj.id + 1);
                    event.delay(20);
                    if (obj.id == obj.originalId + 1) {
                        obj.setId(obj.originalId);
                    }
                });
            } else {
                World.startEvent(event -> {
                    obj.setId(obj.id + 1);
                    event.delay(20);
                    obj.setId(obj.originalId);
                });
            }
        }
    }

    static {
        for (BushPickables.Nodes node : BushPickables.Nodes.values()) {
            for (int index = 0; index <= 2; index++) {
                ObjectAction.register(node.objectId + index, "pick-from", node::pick);
            }
        }
    }

}