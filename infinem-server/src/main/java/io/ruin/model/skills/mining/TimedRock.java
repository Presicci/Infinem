package io.ruin.model.skills.mining;

import io.ruin.model.World;
import io.ruin.model.map.object.GameObject;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/6/2024
 */
public class TimedRock {
    protected static final String TIMER_KEY = "ROCK_TIMER";
    private static final String HEALTH_KEY = "ROCK_HEALTH";
    private static final String TYPE_KEY = "ROCK_TYPE";
    private static final String EMPTY_KEY = "ROCK_EMPTY";
    private static final List<GameObject> ACTIVE_ROCKS = new ArrayList<>();

    private static void setupRock(GameObject rock, Rock rockData, int emptyId) {
        rock.putTemporaryAttribute(TYPE_KEY, rockData);
        rock.putTemporaryAttribute(EMPTY_KEY, emptyId);
        rock.putTemporaryAttribute(TIMER_KEY, 1);
        ACTIVE_ROCKS.add(rock);
    }

    protected static void pingIfActive(GameObject rock) {
        if (!rock.hasTemporaryAttribute(TIMER_KEY)) return;
        rock.putTemporaryAttribute(TIMER_KEY, 5);
    }

    protected static void pingRock(GameObject rock, Rock rockType, int emptyId) {
        if (!rock.hasTemporaryAttribute(TIMER_KEY)) setupRock(rock, rockType, emptyId);
        rock.putTemporaryAttribute(TIMER_KEY, 5);
    }

    private static void damageRock(GameObject rock) {
        int health = rock.incrementTemporaryNumericAttribute(HEALTH_KEY, 5);
        Rock rockType = rock.getTemporaryAttribute(TYPE_KEY);
        int rockTimer = rock.getTemporaryAttributeIntOrZero(TIMER_KEY);
        System.out.println("Rock " + rock.x + ", " + rock.y + " has " + health + " health. " + rockTimer);
        if (health >= rockType.depleteTime) {
            rock.removeTemporaryAttribute(TIMER_KEY);
            rock.removeTemporaryAttribute(HEALTH_KEY);
            int emptyId = rock.getTemporaryAttributeIntOrZero(EMPTY_KEY);
            World.startEvent(worldEvent -> {
                rock.setId(emptyId);
                worldEvent.delay(rockType.respawnTime);
                if (rock.id == emptyId) {
                    rock.setId(rock.originalId);
                }
            });
        }
    }

    private static void healRock(GameObject rock) {
        int health = rock.incrementTemporaryNumericAttribute(HEALTH_KEY, -3);
        int rockTimer = rock.getTemporaryAttributeIntOrZero(TIMER_KEY);
        System.out.println("Rock " + rock.x + ", " + rock.y + " has " + health + " health. " + rockTimer);
        if (health <= 0) {
            rock.removeTemporaryAttribute(TIMER_KEY);
            rock.removeTemporaryAttribute(HEALTH_KEY);
        }
    }

    static {
        World.startEvent(e -> {
            while (true) {
                e.delay(5);
                for (GameObject rock : ACTIVE_ROCKS) {
                    // If rock is dormant
                    if (!rock.hasTemporaryAttribute(TIMER_KEY)) {
                        healRock(rock);
                        continue;
                    }
                    int rockTimer = rock.getTemporaryAttributeIntOrZero(TIMER_KEY);
                    if (rockTimer <= 0) {
                        rock.removeTemporaryAttribute(TIMER_KEY);
                        healRock(rock);
                        continue;
                    }
                    rock.incrementTemporaryNumericAttribute(TIMER_KEY, -5);
                    damageRock(rock);
                }
                ACTIVE_ROCKS.removeIf(rock -> {
                    int health = rock.getTemporaryAttributeIntOrZero(HEALTH_KEY);
                    Rock rockType = rock.getTemporaryAttribute(TYPE_KEY);
                    return health >= rockType.depleteTime || health <= 0;
                });
            }
        });
    }
}
