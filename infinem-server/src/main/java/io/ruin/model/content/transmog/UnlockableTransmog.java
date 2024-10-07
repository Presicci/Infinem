package io.ruin.model.content.transmog;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.item.Items;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/5/2024
 */
@Getter
@AllArgsConstructor
public enum UnlockableTransmog {
    // Monkey backpacks
    MONKEY(TransmogSlot.CAPE, 19556, "Search the crate north of Tree Gnome Stronghold."),
    KARAMJA_MONKEY(TransmogSlot.CAPE, 24862, "Complete laps of the Ape Atoll Agility course."),
    ZOMBIE_MONKEY(TransmogSlot.CAPE, 24863, "Complete laps of the Ape Atoll Agility course."),
    MANIACAL_MONKEY(TransmogSlot.CAPE, 24864, "Complete laps of the Ape Atoll Agility course."),
    SKELETON_MONKEY(TransmogSlot.CAPE, 24865, "Complete laps of the Ape Atoll Agility course."),
    KRUK_JR(TransmogSlot.CAPE, 24866, "Complete laps of the Ape Atoll Agility course."),
    PRINCELY_MONKEY(TransmogSlot.CAPE, 24867, "Complete laps of the Ape Atoll Agility course."),
    // Coffins
    BRONZE_COFFIN(TransmogSlot.CAPE, 25459, "Open Shades of Mort'ton chests."),
    STEEL_COFFIN(TransmogSlot.CAPE, 25461, "Open Shades of Mort'ton chests."),
    BLACK_COFFIN(TransmogSlot.CAPE, 25463, "Open Shades of Mort'ton chests."),
    SILVER_COFFIN(TransmogSlot.CAPE, 25465, "Open Shades of Mort'ton chests."),
    GOLD_COFFIN(TransmogSlot.CAPE, 25467, "Open Shades of Mort'ton chests."),
    // Misc
    FIRE_CAPE(TransmogSlot.CAPE, Items.FIRE_CAPE, "Complete the Fight Caves."),
    INFERNAL_CAPE(TransmogSlot.CAPE, Items.INFERNAL_CAPE, "Complete The Inferno.")
    ;

    private final TransmogSlot slot;
    private final int itemId;
    private final String unlockRequirement;

    public static final Map<TransmogSlot, List<UnlockableTransmog>> TRANSMOGS_BY_SLOT = new HashMap<>();
    public static final Map<Integer, UnlockableTransmog> TRANSMOGS_BY_ID = new HashMap<>();

    private static final long NEWEST_LOAD_TIMESTAMP = 1;

    static {
        for (UnlockableTransmog t : values()) {
            TRANSMOGS_BY_ID.put(t.getItemId(), t);
            if (TRANSMOGS_BY_SLOT.containsKey(t.slot)) {
                TRANSMOGS_BY_SLOT.get(t.slot).add(t);
            } else {
                List<UnlockableTransmog> list = new ArrayList<>();
                list.add(t);
                TRANSMOGS_BY_SLOT.put(t.slot, list);
            }
        }
        LoginListener.register(player -> {
            if (player.lastLogin >= NEWEST_LOAD_TIMESTAMP) return;
            for (UnlockableTransmog t : values()) {
                if (player.getCollectionLog().hasCollected(t.getItemId())) {
                    player.getTransmogCollection().addToCollection(t.getItemId(), false);
                }
            }
            for (StatType type : StatType.values()) {
                if (player.getStats().get(type).fixedLevel >= 99) {
                    for (int itemId : Arrays.asList(type.regularCapeId, type.trimmedCapeId, type.hoodId)) {
                        player.getTransmogCollection().addToCollection(itemId, false);
                    }
                }
                if (player.getStats().get(type).experience >= Stat.xpForLevel(120)) {
                    player.getTransmogCollection().addToCollection(type.masterCapeId, false);
                }
            }
            if (player.getStats().isMaxed()) {
                player.getTransmogCollection().addToCollection(13342, false);
                player.getTransmogCollection().addToCollection(13281, false);
            }
        });
    }
}
