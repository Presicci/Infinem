package io.ruin.model.activities.shadesofmortton;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/22/2024
 */
public class ShadeCatacombs {

    private static final int[] KEYS = {
            Items.BRONZE_KEY_CRIMSON, Items.BRONZE_KEY_BLACK, Items.BRONZE_KEY_BROWN, Items.BRONZE_KEY_PURPLE, Items.BRONZE_KEY_RED,
            Items.STEEL_KEY_CRIMSON, Items.STEEL_KEY_BLACK, Items.STEEL_KEY_BROWN, Items.STEEL_KEY_PURPLE, Items.STEEL_KEY_RED,
            Items.BLACK_KEY_CRIMSON, Items.BLACK_KEY_BLACK, Items.BLACK_KEY_BROWN, Items.BLACK_KEY_PURPLE, Items.BLACK_KEY_RED,
            Items.SILVER_KEY_CRIMSON, Items.SILVER_KEY_BLACK, Items.SILVER_KEY_BROWN, Items.SILVER_KEY_PURPLE, Items.SILVER_KEY_RED,
            25428,  // Gold crimson
            25430,  // Gold black
            25426,  // Gold brown
            25432,  // Gold purple
            25424   // Gold red
    };

    private static void openDoor(Player player, GameObject object, KeyType keyType) {
        int[] POSSIBLE_KEYS = Arrays.copyOfRange(KEYS, keyType.minKeyIndex, KEYS.length - 1);
        if (!player.getInventory().hasAtLeastOneOf(POSSIBLE_KEYS)) {
            player.sendMessage("You need a " + keyType.name().toLowerCase() + " key to open this door.");
            return;
        }
        PassableDoor.passDoor(player, object,
                object.direction == 3 ? Direction.SOUTH
                        : object.direction == 1 ? Direction.NORTH
                        : object.direction == 0 ? Direction.WEST
                        : Direction.EAST);
    }

    private static void enterCatacombs(Player player) {
        if (!player.getInventory().hasAtLeastOneOf(KEYS)) {
            player.sendMessage("You need a bronze key to enter the catacombs.");
            return;
        }
        Traveling.fadeTravel(player, new Position(3493, 9726, 0));
    }

    static {
        ObjectAction.register(4106, "open", (player, obj) -> openDoor(player, obj, KeyType.BRONZE));
        ObjectAction.register(4107, "open", (player, obj) -> openDoor(player, obj, KeyType.STEEL));
        ObjectAction.register(4108, "open", (player, obj) -> openDoor(player, obj, KeyType.BLACK));
        ObjectAction.register(4109, "open", (player, obj) -> openDoor(player, obj, KeyType.SILVER));
        ObjectAction.register(41210, "open", (player, obj) -> openDoor(player, obj, KeyType.GOLD));
        ObjectAction.register(4132, 3485, 3320, 0, "open", (player, obj) -> enterCatacombs(player));
        ObjectAction.register(4133, 3484, 3320, 0, "open", (player, obj) -> enterCatacombs(player));
        ObjectAction.register(4106, 3493, 9726, 0, "open", (player, obj) -> Traveling.fadeTravel(player, new Position(3485, 3322, 0)));
    }

    @AllArgsConstructor
    private enum KeyType {
        BRONZE(0),
        STEEL(5),
        BLACK(10),
        SILVER(15),
        GOLD(20);

        private final int minKeyIndex;
    }
}
