package io.ruin.model.content;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/25/2022
 */
public enum ActivitySpotlight {
    // Double key chance
    DOUBLE_MARKS_OF_GRACE,
    DOUBLE_PEST_CONTROL_POINTS,
    DOUBLE_BIRD_NEST_CHANCE,
    DOUBLE_CLUE_NEST_CHANCE,
    DOUBLE_GEODE_CHANCE,
    DOUBLE_CLUE_BOTTLE_CHANCE
    ;

    public static ActivitySpotlight activeSpotlight;
    private static final int SPOTLIGHT_TIMER = 6000;    // 1 hour
    private static boolean spotlightsEnabled = true;

    public static void cycleSpotlight() {
        activeSpotlight = ActivitySpotlight.values()[Random.get(ActivitySpotlight.values().length - 1)];
        World.players.forEach(p -> {
            p.sendNotification("The activity spotlight is now: " + StringUtils.capitalizeFirst(activeSpotlight.toString().toLowerCase().replace("_", " ")));
        });
    }

    public static void disableSpotlights() {
        activeSpotlight = null;
        spotlightsEnabled = false;
    }

    public static boolean isActive(ActivitySpotlight spotlight) {
        return activeSpotlight == spotlight;
    }

    static {
        World.startEvent(event -> {
            cycleSpotlight();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime nearestHour = now.plusHours(1).truncatedTo(ChronoUnit.HOURS);
            Duration waitTime = Duration.between(now, nearestHour);
            event.delay(Server.toTicks((int) waitTime.toMinutes() * 60));
            while (spotlightsEnabled) {
                cycleSpotlight();
                event.delay(SPOTLIGHT_TIMER);
            }
        });
    }
}
