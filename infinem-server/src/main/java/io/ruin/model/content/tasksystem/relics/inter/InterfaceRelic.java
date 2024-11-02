package io.ruin.model.content.tasksystem.relics.inter;

import io.ruin.model.content.tasksystem.relics.Relic;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/20/2024
 */
@Getter
@AllArgsConstructor
public enum InterfaceRelic {
    EYE_OF_THE_ARTISAN(730, Relic.EYE_OF_THE_ARTISAN),
    GIFT_OF_THE_GATHERER(731, Relic.GIFT_OF_THE_GATHERER),
    WAY_OF_THE_WARRIOR(732, Relic.WAY_OF_THE_WARRIOR),
    ENDLESS_HARVEST(1722, Relic.ENDLESS_HARVEST),
    PRODUCTION_MASTER(1723, Relic.PRODUCTION_MASTER),
    TRICKSTER(4730, Relic.TRICKSTER),
    FAIRYS_FLIGHT(1725, Relic.FAIRYS_FLIGHT),
    GLOBETROTTER(4717, Relic.GLOBETROTTER),
    DUNGEON_HUB_PREMIUM(9010, Relic.DUNGEON_HUB_PREMIUM),
    DEADEYE(1728, Relic.DEADEYE),
    JUGGERNAUT(1729, Relic.JUGGERNAUT),
    ARCHMAGE(1730, Relic.ARCHMAGE),
    TRESURE_HUNTER(1732, Relic.TREASURE_HUNTER),
    MONSTER_HUNTER(4718, Relic.MONSTER_HUNTER);

    private final int struct;
    private final Relic relic;
}
