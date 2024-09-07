package io.ruin.model.content;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.utility.Broadcast;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/25/2022
 */
public enum ActivitySpotlight {
    // Double key chance
    DOUBLE_MARKS_OF_GRACE,
    DOUBLE_PEST_CONTROL_POINTS,
    QUADRUPLE_BIRD_NEST_CHANCE,
    QUADRUPLE_CLUE_NEST_CHANCE,
    QUADRUPLE_GEODE_CHANCE,
    QUADRUPLE_CLUE_BOTTLE_CHANCE
    ;

    private static List<ActivitySpotlight> DISABLED_SPOTLIGHTS = Arrays.asList();
    public static ActivitySpotlight activeSpotlight;
    private static final int SPOTLIGHT_TIMER = 6000;    // 1 hour
    private static boolean spotlightsEnabled = true;

    private static List<ActivitySpotlight> getPossibleActivities() {
        return Arrays.stream(values()).filter(activity -> !DISABLED_SPOTLIGHTS.contains(activity) && activity != activeSpotlight).collect(Collectors.toList());
    }

    public static void cycleSpotlight() {
        activeSpotlight = Random.get(getPossibleActivities());
        Broadcast.ACTIVITY_SPOTLIGHT.sendNews("<col=d9a414><shad=0>The activity spotlight is now: " + StringUtils.capitalizeFirst(activeSpotlight.toString().toLowerCase().replace("_", " ")));
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
