package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
@Getter
@AllArgsConstructor
public enum FragmentType {
    AGILITY(StatType.Agility, 2, 3761, 3801),
    COOKING(StatType.Cooking, 6, 3778, 3798),
    CRAFTING(StatType.Crafting, 10, 3767, 3787),
    FARMING(StatType.Farming, 14, 3762, 3822),
    FIREMAKING(StatType.Firemaking, 18, 3773, 3833),
    Fishing(StatType.Fishing, 22, 3772, 3812),
    FLETCHING(StatType.Fletching, 26, 3770, 3830),
    HERBLORE(StatType.Herblore, 30, 3778, 3818),
    HUNTER(StatType.Hunter, 34, 3774, 3814),
    MINING(StatType.Mining, 38, 3760, 3800),
    RUNECRAFTING(StatType.Runecrafting, 42, 3765, 3785),
    SMITHING(StatType.Smithing, 46, 3768, 3788),
    THIEVING(StatType.Thieving, 50, 3769, 3789),
    WOODCUTTING(StatType.Woodcutting, 54, 3762, 3802);

    private final StatType statType;
    private final int spriteIndex, inactiveSprite, activeSprite;

    @Override
    public String toString() {
        return StringUtils.capitalizeFirst(name().toLowerCase());
    }

    public static final Map<StatType, FragmentType> BY_STAT_TYPE = new HashMap<>();
    public static final Map<Integer, FragmentType> BY_SPRITE_INDEX = new HashMap<>();

    static {
        for (FragmentType type : values()) {
            BY_STAT_TYPE.put(type.statType, type);
            BY_SPRITE_INDEX.put(type.spriteIndex, type);
        }
    }
}
