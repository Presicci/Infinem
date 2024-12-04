package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class Pickables {

    public enum Node {
        NETTLES(4241, 1181, 5253, 5254, 5255, 5256, 5257, 5258),
        FLAX(1779, 14896),
        CABBAGE(1965, 1161, 51835, 51836),
        WHEAT(1947, 15506, 15507, 15508),
        POTATO(1942, 312),
        ONION(1957, 3366, 51837, 51838),
        SWEETCORN(Items.SWEETCORN, 51829);

        private final int itemId;
        private final int[] objectIds;

        Node(int itemId, int... objectIds) {
            this.itemId = itemId;
            this.objectIds = objectIds;
        }

        private void pick(Player player, GameObject obj) {
            if(player.getInventory().isFull() && !player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                player.sendMessage("You can't carry any more " + ItemDefinition.get(itemId).name.toLowerCase() + ".");
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.animate(this == SWEETCORN ? 832 : 827);
                event.delay(1);
                if (this == NETTLES && player.getEquipment().get(Equipment.SLOT_HANDS) == null) {
                    player.sendMessage("Ow! You prick your hand trying to pick the nettles.");
                    Hit hit = new Hit(HitType.POISON);
                    hit.randDamage(2);
                    player.hit(hit);
                    player.unlock();
                    return;
                }
                addItem(player);
                player.sendFilteredMessage("You pick some " + ItemDefinition.get(itemId).name.toLowerCase() + ".");
                player.getStats().addXp(StatType.Farming, 1, false);
                if(Random.rollDie(6, 1))
                    remove(obj);
                player.unlock();
                if (this != NETTLES) {
                    event.delay(2);
                    while (obj.id == obj.originalId) {
                        event.delay(1);
                        if(player.getInventory().isFull() && !player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                            player.sendMessage("You can't carry any more " + ItemDefinition.get(itemId).name.toLowerCase() + ".");
                            return;
                        }
                        player.animate(this == SWEETCORN ? 832 : 827);
                        event.delay(1);
                        addItem(player);
                        player.sendFilteredMessage("You pick some " + ItemDefinition.get(itemId).name.toLowerCase() + ".");
                        player.getStats().addXp(StatType.Farming, 1, false);
                        if(Random.rollDie(6, 1))
                            remove(obj);
                    }
                }
            });
        }

        private void addItem(Player player) {
            if (player.getRelicManager().hasRelicEnalbed(Relic.ENDLESS_HARVEST)) {
                if (player.getInventory().hasRoomFor(itemId)) {
                    player.getInventory().addOrDrop(itemId, 2);
                } else {
                    player.getBank().add(itemId, 2);
                    player.sendFilteredMessage("Your Relic banks the " + ItemDefinition.get(itemId).name + " you would have gained, giving you a total of " + player.getBank().getAmount(itemId) + ".");
                }
            } else {
                player.getInventory().add(itemId, 1);
            }
        }

        private void remove(GameObject obj) {
            if (this == SWEETCORN) {
                World.startEvent(event -> {
                    obj.setId(51831);
                    event.delay(20);
                    if (obj.id == 51831) {
                        obj.setId(obj.originalId);
                    }
                });
            } else {
                obj.removeFor(20);
            }
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