package io.ruin.model.content.bestiary;

import io.ruin.model.content.bestiary.perks.impl.*;
import io.ruin.model.content.bestiary.perks.impl.misc.BarrowsChestPerk;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2024
 */
@Getter
public enum BestiaryEntryModifiers {
    BOSS(
            entry -> BestiaryDef.isBoss(entry),
            entry -> {
                entry.clearPerks();
                entry.addPerk(new DamagePerk(true));
                entry.addPerk(new AccuracyPerk(true));
                entry.addPerk(new ExtraDropPerk(true));
                entry.addPerk(new NotedDropPerk(true));
                entry.addPerk(new ReducedEnemyAccuracyPerk(true));
                entry.addPerk(new RespawnPerk(true));
                entry.addPerk(new LuckPerk(true));
                entry.addPerk(new GoldPickupPerk());
            }
    ),
    BARROWS_BROTHERS(
            entry -> entry != null && entry.equalsIgnoreCase("barrows brother"),
            entry -> {
                entry.removePerk(ExtraDropPerk.class);
                entry.removePerk(GoldPickupPerk.class);
                entry.removePerk(RespawnPerk.class);
                entry.addPerk(new BarrowsChestPerk());
            }
    );

    private final Predicate<String> predicate;
    private final Consumer<BestiaryEntry> consumer;

    BestiaryEntryModifiers(Predicate<String> predicate, Consumer<BestiaryEntry> consumer) {
        this.predicate = predicate;
        this.consumer = consumer;
    }

    protected static final HashMap<String, List<Consumer<BestiaryEntry>>> MODIFIERS = new HashMap<>();

    static {
        BestiaryDef.ENTRIES.forEach(entry -> {
            for (BestiaryEntryModifiers modifier : values()) {
                if (modifier.predicate.test(entry)) MODIFIERS.computeIfAbsent(entry, v -> new ArrayList<>()).add(modifier.consumer);
            }
        });
    }
}
