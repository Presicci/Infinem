package io.ruin.data.impl.dialogue;

import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

@Getter
public enum DialogueLoaderSetting {
    HASSLAYERTASK((p, i) -> Slayer.getTask(p) == i),
    HASITEMS((p, i) -> {
        int secondary = p.getTemporaryAttributeIntOrZero(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        if (secondary > 0)
            return p.getInventory().hasId(i) && p.getInventory().hasId(secondary);
        return p.getInventory().hasId(i);
    }),
    HASITEM((p, i) -> p.getInventory().hasId(i)),
    HASEQUIPMENT((p, i) -> {
        int secondary = p.getTemporaryAttributeIntOrZero(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        if (secondary > 0)
            return p.getEquipment().hasId(i) || p.getEquipment().hasId(secondary);
        return p.getEquipment().hasId(i);
    }),
    HASTALKED((p, i) -> p.getSpokenToNPCSet().contains(i), (p, i) -> p.getSpokenToNPCSet().add(i)),
    HASLEVEL((p, i) -> {
        String skillString = p.getTemporaryAttribute(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        switch (skillString) {
            case "ATTACK":
                return p.getStats().get(StatType.Attack).currentLevel >= i;
            case "DEFENCE":
                return p.getStats().get(StatType.Defence).currentLevel >= i;
            case "STRENGTH":
                return p.getStats().get(StatType.Strength).currentLevel >= i;
            case "HITPOINTS":
                return p.getStats().get(StatType.Hitpoints).currentLevel >= i;
            case "RANGED":
                return p.getStats().get(StatType.Ranged).currentLevel >= i;
            case "PRAYER":
                return p.getStats().get(StatType.Prayer).currentLevel >= i;
            case "MAGIC":
                return p.getStats().get(StatType.Magic).currentLevel >= i;
            case "COOKING":
                return p.getStats().get(StatType.Cooking).currentLevel >= i;
            case "WOODCUTTING":
                return p.getStats().get(StatType.Woodcutting).currentLevel >= i;
            case "FLETCHING":
                return p.getStats().get(StatType.Fletching).currentLevel >= i;
            case "FISHING":
                return p.getStats().get(StatType.Fishing).currentLevel >= i;
            case "FIREMAKING":
                return p.getStats().get(StatType.Firemaking).currentLevel >= i;
            case "CRAFTING":
                return p.getStats().get(StatType.Crafting).currentLevel >= i;
            case "SMITHING":
                return p.getStats().get(StatType.Smithing).currentLevel >= i;
            case "MINING":
                return p.getStats().get(StatType.Mining).currentLevel >= i;
            case "HERBLORE":
                return p.getStats().get(StatType.Herblore).currentLevel >= i;
            case "AGILITY":
                return p.getStats().get(StatType.Agility).currentLevel >= i;
            case "THIEVING":
                return p.getStats().get(StatType.Thieving).currentLevel >= i;
            case "SLAYER":
                return p.getStats().get(StatType.Slayer).currentLevel >= i;
            case "FARMING":
                return p.getStats().get(StatType.Farming).currentLevel >= i;
            case "RUNECRAFTING":
                return p.getStats().get(StatType.Runecrafting).currentLevel >= i;
            case "HUNTER":
                return p.getStats().get(StatType.Hunter).currentLevel >= i;
            case "CONSTRUCTION":
                return p.getStats().get(StatType.Construction).currentLevel >= i;
            default:
                return true;
        }
    }),
    RAND;
    private final BiPredicate<Player, Integer> biPredicate;
    private final BiConsumer<Player, Integer> biConsumer;

    DialogueLoaderSetting(BiPredicate<Player, Integer> biPredicate, BiConsumer<Player, Integer> biConsumer) {
        this.biPredicate = biPredicate;
        this.biConsumer = biConsumer;
    }

    DialogueLoaderSetting(BiPredicate<Player, Integer> biPredicate) {
        this(biPredicate, null);
    }

    DialogueLoaderSetting() {
        this(null);
    }
}
