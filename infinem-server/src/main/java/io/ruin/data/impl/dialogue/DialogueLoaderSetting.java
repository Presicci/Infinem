package io.ruin.data.impl.dialogue;

import io.ruin.model.entity.shared.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

@Getter
public enum DialogueLoaderSetting {
    HASITEM((p, i) -> p.getInventory().hasId(i)),
    HASEQUIPMENT((p, i) -> {
        int secondary = p.getTemporaryAttributeIntOrZero(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        System.out.println(secondary + "," + i);
        if (secondary > 0)
            return p.getEquipment().hasId(i) || p.getEquipment().hasId(secondary);
        return p.getEquipment().hasId(i);
    }),
    HASTALKED((p, i) -> p.getSpokenToNPCSet().contains(i), (p, i) -> p.getSpokenToNPCSet().add(i)),
    HASLEVEL((p, i) -> {
        String skillString = p.getTemporaryAttribute(AttributeKey.DIALOGUE_ACTION_ARGUMENTS);
        switch (skillString) {
            case "PRAYER":
                return p.getStats().get(StatType.Prayer).currentLevel >= i;
            case "FARMING":
                return p.getStats().get(StatType.Farming).currentLevel >= i;
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
