package io.ruin.model.map;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/7/2023
 */
public class TileTrigger {

    public static void registerTrigger(Position position, int size, Consumer<Entity> trigger) {
        for (int xOffset = 0; xOffset < size; xOffset++) {
            for (int yOffset = 0; yOffset < size; yOffset++) {
                registerTrigger(position.relative(xOffset, yOffset), trigger);
            }
        }
    }

    public static void registerPlayerTrigger(Position position, int size, Consumer<Player> trigger) {
        for (int xOffset = 0; xOffset < size; xOffset++) {
            for (int yOffset = 0; yOffset < size; yOffset++) {
                registerPlayerTrigger(position.relative(xOffset, yOffset), trigger);
            }
        }
    }

    public static void registerTrigger(Position position, Consumer<Entity> trigger) {
        Tile tile = Tile.get(position, true);
        if (tile == null) {
            System.err.println("[TileTrigger] Error registering trigger at " + position);
            return;
        }
        tile.addTrigger(trigger);
    }

    public static void registerPlayerTrigger(Position position, Consumer<Player> trigger) {
        Tile tile = Tile.get(position, true);
        if (tile == null) {
            System.err.println("[TileTrigger] Error registering trigger at " + position);
            return;
        }
        tile.addPlayerTrigger(trigger);
    }


}
