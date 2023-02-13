package io.ruin.data.impl.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

@Getter
public enum DialogueLoaderSetting {
    HASITEM((p, i) -> p.getInventory().hasId(i)),
    HASEQUIPMENT((p, i) -> p.getEquipment().hasId(i)),
    HASTALKED((p, i) -> p.getSpokenToNPCSet().contains(i), (p, i) -> p.getSpokenToNPCSet().add(i)),
    HASPRAYERLEVEL((p, i) -> p.getStats().get(StatType.Prayer).currentLevel >= i),
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
