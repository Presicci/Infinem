package io.ruin.model.activities.combat.ChampionChallenge;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/26/2024
 */
@Getter
public enum Champion {
    IMP(Items.CHAMPION_SCROLL_IMP, 1457, 160),
    GOBLIN(Items.CHAMPION_SCROLL_GOBLIN, 1455, 128),
    SKELETON(Items.CHAMPION_SCROLL_SKELETON, 1460, 232),
    ZOMBIE(Items.CHAMPION_SCROLL_ZOMBIE, 1461, 240),
    GIANT(Items.CHAMPION_SCROLL_GIANT, 1454, 280),
    HOBGOBLIN(Items.CHAMPION_SCROLL_HOBGOBLIN, 1456, 232),
    GHOUL(Items.CHAMPION_SCROLL_GHOUL, 1453, 400),
    EARTH_WARRIOR(Items.CHAMPION_SCROLL_EARTH_WARRIOR, 1452, 432),
    JOGRE(Items.CHAMPION_SCROLL_JOGRE, 1458, 480),
    LESSER_DEMON(Items.CHAMPION_SCROLL_LESSER_DEMON, 1459, 592),
    LEON(Items.CHAMPION_SCROLL, -1, 492);

    private final int scrollId, experience;
    private final Config config;

    Champion(int scrollId, int varpbit, int experience) {
        this.scrollId = scrollId;
        this.experience = experience;
        if (varpbit > -1)
            this.config = Config.varpbit(varpbit, true);
        else
            this.config = null;
    }
}
