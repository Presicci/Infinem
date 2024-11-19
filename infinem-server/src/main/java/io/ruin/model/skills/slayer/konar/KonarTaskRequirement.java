package io.ruin.model.skills.slayer.konar;

import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.slayer.PartnerSlayer;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/19/2024
 */
@Getter
@AllArgsConstructor
public enum KonarTaskRequirement {
    BLACK_DRAGON_CATACOMBS(KonarTask.BLACK_DRAGON, KonarTaskLocation.CATACOMBS_OF_KOUREND,
            (p) -> p.getStats().checkFixed(StatType.Slayer, 77)
                    && (PartnerSlayer.getCombatLevel(p) >= 100 || !p.slayerCombatCheck)),
    BLUE_DRAGON_CATACOMBS(KonarTask.BLUE_DRAGON, KonarTaskLocation.CATACOMBS_OF_KOUREND,
            (p) -> PartnerSlayer.getCombatLevel(p) >= 100 || !p.slayerCombatCheck),
    RED_DRAGON_CATACOMBS(KonarTask.RED_DRAGON, KonarTaskLocation.CATACOMBS_OF_KOUREND,
            (p) -> PartnerSlayer.getCombatLevel(p) >= 100 || !p.slayerCombatCheck),

    ;

    private final KonarTask task;
    private final KonarTaskLocation taskLocation;
    private final Predicate<Player> requirement;

    public boolean test(Player player) {
        return requirement.test(player);
    }

    protected static final Map<KonarTask, List<KonarTaskRequirement>> REQUIREMENTS = new HashMap<>();

    static {
        for (KonarTaskRequirement req : values()) {
            REQUIREMENTS.computeIfAbsent(req.task, location -> new ArrayList<>()).add(req);
        }
    }
}
