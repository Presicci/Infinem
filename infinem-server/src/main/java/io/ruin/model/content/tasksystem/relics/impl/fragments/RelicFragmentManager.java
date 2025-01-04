package io.ruin.model.content.tasksystem.relics.impl.fragments;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
public class RelicFragmentManager {

    @Expose @Getter private Map<FragmentType, Item> fragments = new HashMap<>();

    /*public boolean getValue(String modName) {
        for (FragmentItem fragment : fragments) {
            if (fragment == null) continue;
            fragment.getMods();
        }
    }*/

    public void putFragment(FragmentType type, Item fragment) {
        fragments.put(type, fragment);
    }

    public Item getFragment(FragmentType type) {
        return fragments.getOrDefault(type, null);
    }

    public void removeFragment(FragmentType type) {
        fragments.remove(type);
    }

    public double getModifierValue(StatType statType, FragmentModifier mod) {
        FragmentType fragmentType = FragmentType.BY_STAT_TYPE.getOrDefault(statType, null);
        if (fragmentType == null) return 0;
        return getModifierValue(fragmentType, mod);
    }

    public double getModifierValue(FragmentType type, FragmentModifier mod) {
        Item fragmentItem = getFragment(type);
        if (fragmentItem == null) return 0;
        return FragmentItem.getModValue(fragmentItem, mod);
    }

    public boolean rollChanceModifier(FragmentType type, FragmentModifier mod) {
        Item fragmentItem = getFragment(type);
        if (fragmentItem == null) return false;
        return Random.get() < FragmentItem.getModValue(fragmentItem, mod);
    }
}
