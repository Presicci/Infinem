package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
public class FragmentItem {

    public static FragmentModifier[] getPossibleMods(Item item) {
        FragmentModifier[] current = getCurrentMods(item);
        FragmentModifier[] mods = Stream.of(FragmentModifier.values()).filter(eff -> {
            boolean matchesType = Arrays.stream(eff.getTypes()).anyMatch(t -> t == getType(item));
            for (FragmentModifier mod : current) {
                if (eff.equals(mod)) {
                    return false;
                }
            }
            return matchesType;
        }).toArray(FragmentModifier[]::new);
        return mods;
    }

    public static FragmentModifier[] getCurrentMods(Item item) {
        List<FragmentModifier> mods = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            String value = item.getAttributeString("MOD_" + i, "empty");
            if (!value.equalsIgnoreCase("empty")) {
                String[] splitValue = value.split(":");
                String modName = splitValue[0];
                FragmentModifier mod = FragmentModifier.valueOf(modName);
                mods.add(mod);
            }
        }
        return mods.toArray(new FragmentModifier[0]);
    }

    public static FragmentModRoll[] getMods(Item item) {
        List<FragmentModRoll> mods = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String value = item.getAttributeString("MOD_" + i, "empty");
            if (!value.equalsIgnoreCase("empty")) {
                String[] splitValue = value.split(":");
                String modName = splitValue[0];
                double val = Double.parseDouble(splitValue[1]);
                FragmentModifier mod = FragmentModifier.valueOf(modName);
                mods.add(new FragmentModRoll(mod, val));
            }
        }
        return mods.toArray(new FragmentModRoll[0]);
    }

    public static FragmentType getType(Item item) {
        String value = item.getAttributeString("TYPE", "empty");
        if (value.equalsIgnoreCase("empty")) {
            return null;
        }
        return FragmentType.valueOf(value);
    }

    public static void rollMod(Item item) {
        FragmentModRoll roll = FragmentModifier.rollFrom(getPossibleMods(item));
        if (roll != null) {
            addMod(item, roll);
        } else {
            System.err.println("Failed to roll mod:");
            System.err.println("Current - " + Arrays.toString(getCurrentMods(item)));
            System.err.println("Possible - " + Arrays.toString(getPossibleMods(item)));
        }
    }

    public static boolean addMod(Item item, FragmentModRoll mod) {
        for (int i = 1; i <= 3; i++) {
            String value = item.getAttributeString("MOD_" + i, "empty");
            if (value.equalsIgnoreCase("empty")) {
                item.putAttribute("MOD_" + i, mod);
                return true;
            }
        }
        return false;
    }

    public static double getModValue(Item item, FragmentModifier modifier) {
        for (int i = 1; i <= 3; i++) {
            String mod = item.getAttributeString("MOD_" + i, "empty");
            if (mod.equalsIgnoreCase("empty")) continue;
            String[] modSplit = mod.split(":");
            if (modSplit[0].equalsIgnoreCase(modifier.name())) {
                return Double.parseDouble(modSplit[1]);
            }
        }
        return 0;
    }

    public static String getModString(Item item) {
        return getModString(item, true);
    }

    public static String getModString(Item item, boolean doubleSpaced) {
        FragmentModRoll[] modifiers = getMods(item);
        StringBuilder sb = new StringBuilder();
        for (FragmentModRoll mod : modifiers) {
            if (sb.length() > 0) sb.append(doubleSpaced ? "<br><br>" : "<br>");
            sb.append(mod.getModifier().getDescription(getType(item), mod));
        }
        return sb.toString();
    }

    public static void examine(Player player, Item item) {
        if (!isFragment(item)) return;
        player.sendMessage("Fragment mods:");
        FragmentModRoll[] modifiers = getMods(item);
        for (FragmentModRoll mod : modifiers) {
            player.sendMessage("  - " + mod.getModifier().getDescription(getType(item), mod));
        }
    }

    public static boolean isFragment(Item item) {
        String value = item.getAttributeString("TYPE", "empty");
        return !value.equalsIgnoreCase("empty");
    }

    public static void makeFragment(Item item, FragmentType fragmentType) {
        item.putAttribute("TYPE", fragmentType.name());
    }
}
