package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/29/2023
 */
public enum RopeObject {
    KALPHITE_LAIR_EXTERIOR(3827, 1, 4586, 827, new Position(3484, 9510, 2)),
    KALPHITE_LAIR_INTERIOR(23609, 1, 11705, 827, new Position(3507, 9494, 0));

    RopeObject(int objectId, int optionIndex, int varpbit, int animation, Position destination) {
        Config config = Config.varpbit(varpbit, true);
        ObjectAction.register(objectId, optionIndex, (player, obj) -> {
            if (config.get(player) == 0) return;    // No rope attached
            if (animation > -1) {
                player.startEvent(e -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    player.animate(animation);
                    e.delay(1);
                    player.getMovement().teleport(destination);
                    player.unlock();
                });
            } else {
                player.getMovement().teleport(destination);
            }
        });

        ItemObjectAction.register(Items.ROPE, objectId, (player, item, obj) -> {
            if (config.get(player) == 1) {
                player.sendMessage("You already have a rope set up here.");
                return;
            }
            item.remove();
            config.set(player, 1);
            player.sendMessage("You set up the rope.");
        });
    }
}
