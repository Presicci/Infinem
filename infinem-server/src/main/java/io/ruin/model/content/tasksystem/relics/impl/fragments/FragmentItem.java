package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.item.Item;
import io.ruin.model.item.attributes.AttributeExtensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
public class FragmentItem extends Item {

    private final FragmentType type;

    public FragmentItem(int id, int amount, FragmentType type) {
        super(id, amount);
        this.type = type;
    }

    public FragmentItem(int id, FragmentType type) {
        super(id);
        this.type = type;
    }

    public FragmentItem(int id, int amount, FragmentType type, Map<String, String> attributes) {
        super(id, amount, attributes);
        this.type = type;
    }

    public FragmentModifier[] getPossibleMods() {
        FragmentModifier[] current = getCurrentMods();
        FragmentModifier[] mods = Stream.of(FragmentModifier.values()).filter(eff -> {
            boolean matchesType = Arrays.stream(eff.getTypes()).anyMatch(t -> t == type);
            for (FragmentModifier mod : current) {
                if (eff.equals(mod)) {
                    return false;
                }
            }
            return matchesType;
        }).toArray(FragmentModifier[]::new);
        return mods;
    }

    public FragmentModifier[] getCurrentMods() {
        List<FragmentModifier> mods = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            String value = getAttributeString("MOD_" + i, "empty");
            if (!value.equalsIgnoreCase("empty")) {
                String[] splitValue = value.split("_");
                String modName = splitValue[0];
                FragmentModifier mod = FragmentModifier.valueOf(modName);
                mods.add(mod);
            }
        }
        return mods.toArray(new FragmentModifier[0]);
    }

    public FragmentModRoll[] getMods() {
        List<FragmentModRoll> mods = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            String value = getAttributeString("MOD_" + i, "empty");
            if (!value.equalsIgnoreCase("empty")) {
                String[] splitValue = value.split("_");
                String modName = splitValue[0];
                double val = Double.parseDouble(splitValue[1]);
                FragmentModifier mod = FragmentModifier.valueOf(modName);
                mods.add(new FragmentModRoll(mod, val));
            }
        }
        return mods.toArray(new FragmentModRoll[0]);
    }

    public void rollMod() {
        FragmentModRoll roll = FragmentModifier.rollFrom(getPossibleMods());
        if (roll != null) {
            addMod(roll);
        } else {
            System.err.println("Failed to roll mod:");
            AttributeExtensions.printAttributes(this);
        }
    }

    public boolean addMod(FragmentModRoll mod) {
        for (int i = 1; i < 3; i++) {
            String value = getAttributeString("MOD_" + i, "empty");
            if (value.equalsIgnoreCase("empty")) {
                putAttribute("MOD_" + i, mod);
                return true;
            }
        }
        return false;
    }
}
