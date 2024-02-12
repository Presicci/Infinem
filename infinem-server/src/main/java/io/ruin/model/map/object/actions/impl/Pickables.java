package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Pickables {

    public enum Node {
        NETTLES(4241, 1181, 5256, 5258),
        FLAX(1779, 14896),
        CABBAGE(1965, 1161),
        WHEAT(1947, 15506, 15507, 15508),
        POTATO(1942, 312),
        ONION(1957, 3366);

        private final int itemId;
        private final int[] objectIds;

        Node(int itemId, int... objectIds) {
            this.itemId = itemId;
            this.objectIds = objectIds;
        }

        private void pick(Player player, GameObject obj) {
            if(player.getInventory().isFull()) {
                player.sendMessage("You can't carry any more " + ItemDef.get(itemId).name.toLowerCase() + ".");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.animate(827);
                event.delay(1);
                if (this == NETTLES && player.getEquipment().get(Equipment.SLOT_HANDS) == null) {
                    player.sendMessage("Ow! You prick your hand trying to pick the nettles.");
                    Hit hit = new Hit(HitType.POISON);
                    hit.randDamage(2);
                    player.hit(hit);
                    player.unlock();
                    return;
                }
                player.getInventory().add(itemId, 1);
                player.sendMessage("You pick some " + ItemDef.get(itemId).name.toLowerCase() + ".");
                if(Random.rollDie(6, 1))
                    remove(obj);
                player.unlock();
            });
        }

        private void remove(GameObject obj) {
            obj.removeFor(20);
        }
    }

    static {
        for (Node node : Node.values()) {
            for (int objectId : node.objectIds) {
                ObjectAction.register(objectId, "pick", node::pick);
            }
        }
    }
}