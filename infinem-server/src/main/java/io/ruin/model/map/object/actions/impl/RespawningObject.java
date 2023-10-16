package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/10/2023
 */
@AllArgsConstructor
public enum RespawningObject {
    AXE_LOG(5581, 5582, 25, 1, (player) -> {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You do not have enough inventory space to take this.");
            return false;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(832);
            e.delay(1);
            player.sendFilteredMessage("You take the axe from the log.");
            player.getInventory().add(Items.BRONZE_AXE);
            player.unlock();
        });
        return true;
    }),
    SPADE(9662, 10626, 50, 1, (player) -> {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You do not have enough inventory space to take this.");
            return false;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(832);
            e.delay(1);
            player.sendFilteredMessage("You take the spade from the dirt.");
            player.getInventory().add(Items.SPADE);
            player.unlock();
        });
        return true;
    }),
    SEAWEED_NET(13609, 13608, 4, 1, (player -> {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You do not have enough inventory space to take this.");
            return false;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(832);
            e.delay(1);
            player.sendFilteredMessage("You take some seaweed from the net.");
            player.getInventory().add(Items.SEAWEED);
            player.unlock();
        });
        return true;
    }));

    private final int objectId, replacementId, respawnTicks, ticksBeforeReplacement;
    private final Predicate<Player> consumer;

    static {
        for (RespawningObject object : values()) {
            ObjectAction.register(object.objectId, 1, ((player, obj) -> {
                if (object.consumer.test(player)) {
                    World.startEvent(e -> {
                        e.delay(object.ticksBeforeReplacement);
                        obj.setId(object.replacementId);
                        e.delay(object.respawnTicks);
                        obj.restore();
                    });
                }
            }));
        }
    }
}
