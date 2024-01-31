package io.ruin.model.activities.combat.godwars;


import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class GodwarsFollower {

    public static final List<Integer> ARMADYL_FOLLOWER = Arrays.asList(3165, 3163, 3164, 3162, 3166, 3167, 3168, 3169, 3170, 3171, 3172, 3173, 3174,
            3175, 3176, 3177, 3178, 3179, 3180, 3181, 3182, 3183);
    public static final List<Integer> SARADOMIN_FOLLOWER = Arrays.asList(2206, 2208, 2207, 2205, 2213, 2214, 2210, 2212, 2209, 2211);
    public static final List<Integer> ZAMORAK_FOLLOWER = Arrays.asList(3130, 3131, 3132, 3129, 3159, 3133, 3141, 3138, 484, 485, 486, 487, 3134, 5007,
            3135, 3136, 3137, 3140, 3139, 3161, 3160);
    public static final List<Integer> BANDOS_FOLLOWER = Arrays.asList(2215, 2216, 2217, 2218, 2244, 2243, 2235, 2236, 2237, 2238, 2239, 2240, 2234, 2246,
            2245, 2249, 2248, 2247, 2241, 2241, 2242);

    private static final String[] SARADOMIN_ITEM_NAMES = {
            "saradomin",
            "staff of light",
            "hallowed",
            "holy",
            "justiciar",
            "monk's robe",
            "devout boots",
            "ring of endurance"
    };

    private static final String[] ZAMORAK_ITEM_NAMES = {
            "zamorak",
            "staff of the dead",
            "staff of light",
            "staff of balance",
            "dragon hunter lance",
            "thammaron's sceptre",
            "accursed sceptre",
            "viggora's chainmace",
            "ursine chainmace",
            "inquisitor's",
            "unholy",
            "elder chaos",
            "dagon'hai"
    };

    private static final String[] BANDOS_ITEM_NAMES = {
            "bandos",
            "ancient mace",
            "book of war",
            "guardian boots",
            "war blessing"
    };

    private static final String[] ARMADYL_ITEM_NAMES = {
            "armadyl",
            "craw's bow",
            "webweaver bow",
            "book of law",
            "honourable blessing"
    };

    private static Bounds GODWARS = new Bounds(2816, 5249, 2943, 5375, -1);

    private static final Predicate<ItemDef> ARMADYL_ITEM = def -> Arrays.stream(ARMADYL_ITEM_NAMES).anyMatch(n -> def.name.toLowerCase().contains(n));
    private static final Predicate<ItemDef> SARADOMIN_ITEM = def -> Arrays.stream(SARADOMIN_ITEM_NAMES).anyMatch(n -> def.name.toLowerCase().contains(n));
    private static final Predicate<ItemDef> ZAMORAK_ITEM = def -> Arrays.stream(ZAMORAK_ITEM_NAMES).anyMatch(n -> def.name.toLowerCase().contains(n));
     private static final Predicate<ItemDef> BANDOS_ITEM = def -> Arrays.stream(BANDOS_ITEM_NAMES).anyMatch(n -> def.name.toLowerCase().contains(n));

    static {

        ItemDef.cached.values().forEach(def -> {
            if (ARMADYL_ITEM.test(def)) def.godItem[0] = true;
            if (SARADOMIN_ITEM.test(def)) def.godItem[1] = true;
            if (ZAMORAK_ITEM.test(def)) def.godItem[2] = true;
            if (BANDOS_ITEM.test(def)) def.godItem[3] = true;
        });


        /**
         * Armadyl
         */
        SpawnListener.register(ARMADYL_FOLLOWER.stream().mapToInt(i -> i).toArray(), npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_ARMADYL_KC.set(killer.player, Config.GWD_ARMADYL_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(0);
            }
        });

        /**
         * Saradomin
         */
        SpawnListener.register(SARADOMIN_FOLLOWER.stream().mapToInt(i -> i).toArray(), npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_SARADOMIN_KC.set(killer.player, Config.GWD_SARADOMIN_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(1);
            }
        });

        /**
         * Zamorak
         */
        SpawnListener.register(ZAMORAK_FOLLOWER.stream().mapToInt(i -> i).toArray(), npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_ZAMORAK_KC.set(killer.player, Config.GWD_ZAMORAK_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(2);
            }
        });

        /**
         * Bandos
         */
        SpawnListener.register(BANDOS_FOLLOWER.stream().mapToInt(i -> i).toArray(), npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_BANDOS_KC.set(killer.player, Config.GWD_BANDOS_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(3);
            }
        });
    }

    private static Predicate<Entity> isProtected(int godIndex) {
        return entity -> {
            if (entity.player == null)
                return false;
            for (Item item : entity.player.getEquipment().getItems()) {
                if (item != null && item.getDef().godItem[godIndex])
                    return true;
            }
            return false;
        };
    }
}
