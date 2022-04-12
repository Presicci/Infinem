package io.ruin.model.skills.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class KonarData {

    /**
     * Generates a location from the task's array of locations.
     *
     * @param player   The player getting the task.
     * @param taskUuid The task being assigned.
     */
    public static void assignLocation(Player player, int taskUuid) {
        Optional<Task> task = Arrays.stream(Task.values()).filter(s -> s.uid == taskUuid).findFirst();
        if (!task.isPresent()) {
            return;
        }
        TaskLocation location = Random.get(task.get().locations);
        player.slayerLocation = location.ordinal();
    }

    /**
     * Data for tasks with their possible locations.
     */
    private enum Task {
        ABERRANT_SPECTRE(41, TaskLocation.SLAYER_TOWER, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.STRONGHOLD_SLAYER_CAVE),
        ABYSSAL_DEMON(42, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.ABYSSAL_AREA, TaskLocation.SLAYER_TOWER),
        ADAMANT_DRAGON(108, TaskLocation.LITHKREN_VAULT),
        ANKOU(79, TaskLocation.STRONGHOLD_OF_SECURITY, TaskLocation.STRONGHOLD_SLAYER_CAVE, TaskLocation.CATACOMBS_OF_KOUREND),
        BLACK_DEMON(30, TaskLocation.TAVERLY_DUNGEON, TaskLocation.BRIMHAVEN_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        BLACK_DRAGON(27, TaskLocation.TAVERLY_DUNGEON, TaskLocation.EVIL_CHICKEN_LAIR, TaskLocation.MYTHS_GUILD_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        BLOODVELD(48, TaskLocation.STRONGHOLD_SLAYER_CAVE, TaskLocation.GODWARS_DUNGEON, TaskLocation.SLAYER_TOWER, TaskLocation.CATACOMBS_OF_KOUREND),
        BLUE_DRAGON(25, TaskLocation.TAVERLY_DUNGEON, TaskLocation.OGRE_ENCLAVE, TaskLocation.MYTHS_GUILD_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        //BRINE_RAT("brine rat", TaskLocation.BRINE_RAT_CAVERN),
        BRONZE_DRAGON(58, TaskLocation.BRIMHAVEN_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        IRON_DRAGON(59, TaskLocation.BRIMHAVEN_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        STEEL_DRAGON(60, TaskLocation.BRIMHAVEN_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        JELLY(50, TaskLocation.RELEKKA_SLAYER_DUNGEON, TaskLocation.CATACOMBS_OF_KOUREND),
        CAVE_KRAKEN(92, TaskLocation.KRAKEN_COVE),
        DAGANNOTH(35, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.LIGHTHOUSE, TaskLocation.WATERBIRTH_ISLAND),
        DARK_BEAST(66, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.MOURNER_TUNNELS),
        DRAKE(112, TaskLocation.KARUULM_SLAYER_DUNGEON),
        DUST_DEVIL(49, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.SMOKE_DUNGEON),
        FIRE_GIANT(16, TaskLocation.KARUULM_SLAYER_DUNGEON, TaskLocation.BRIMHAVEN_DUNGEON, TaskLocation.STRONGHOLD_SLAYER_CAVE, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.WATERFALL_DUNGEON),
        GARGOYLE(46, TaskLocation.SLAYER_TOWER),
        GREATER_DEMON(29, TaskLocation.CHASM_OF_FIRE, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.TAVERLY_DUNGEON, TaskLocation.KARUULM_SLAYER_DUNGEON, TaskLocation.BRIMHAVEN_DUNGEON),
        HELLHOUND(31, TaskLocation.STRONGHOLD_SLAYER_CAVE, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.TAVERLY_DUNGEON, TaskLocation.KARUULM_SLAYER_DUNGEON, TaskLocation.WITCHAVEN_DUNGEON),
        HYDRA(113, TaskLocation.KARUULM_SLAYER_DUNGEON),
        KALPHITE(53, TaskLocation.KALPHITE_LAIR, TaskLocation.KALPHITE_CAVE),
        KURASK(45, TaskLocation.RELEKKA_SLAYER_DUNGEON),
        NECHRYAEL(52, TaskLocation.CATACOMBS_OF_KOUREND, TaskLocation.SLAYER_TOWER),
        RUNE_DRAGON(109, TaskLocation.LITHKREN_VAULT),
        SKELETAL_WYVERN(72, TaskLocation.ASGARNIAN_ICE_DUNGEON),
        SMOKE_DEVIL(95, TaskLocation.SMOKE_DEVIL_DUNGEON),
        TROLL(18, TaskLocation.TROLL_STRONGHOLD, TaskLocation.DEATH_PLATEAU),
        TUROTH(36, TaskLocation.RELEKKA_SLAYER_DUNGEON),
        //WATERFIEND("waterfiend", TaskLocation.KRAKEN_COVE),
        WYRM(111, TaskLocation.KARUULM_SLAYER_DUNGEON);

        private final int uid;
        private final TaskLocation[] locations;

        Task(int uid, TaskLocation... location) {
            this.uid = uid;
            this.locations = location;
        }
    }

    /**
     * Data for location bounds and names.
     */
    public enum TaskLocation {
        NONE(""),
        STRONGHOLD_OF_SECURITY("Stronghold of Security",
                new Bounds(1853, 5184, 1919, 5246, -1),
                new Bounds(1982, 5183, 2049, 5247, -1),
                new Bounds(2113, 5248, 2175, 5310, -1),
                new Bounds(2302, 5184, 2368, 5247, -1)),
        DEATH_PLATEAU("Death Plateau", new Bounds(2836, 3579, 2890, 3607, -1)),
        RELEKKA_SLAYER_DUNGEON("Relekka Slayer Dungeon", new Bounds(2680, 9950, 2814, 10045, -1)),
        KRAKEN_COVE("Kraken Cove", new Bounds(2238, 9983, 2304, 10048, -1)),
        CATACOMBS_OF_KOUREND("Catacombs of Kourend", new Bounds(1588, 9978, 1747, 10110, -1)),
        SLAYER_TOWER("Slayer Tower", new Bounds(1246, 10138, 1391, 10281, -1)),
        TAVERLY_DUNGEON("Taverly Dungeon", new Bounds(2812, 9666, 2969, 9855, -1)),
        WITCHAVEN_DUNGEON("Witchaven Dungeon", new Bounds(2686, 9663, 2750, 9720, -1)),
        WATERFALL_DUNGEON("Waterfall Dungeon", new Bounds(2558, 9860, 2596, 9916, -1)),
        STRONGHOLD_SLAYER_CAVE("Stronghold Slayer Cave", new Bounds(2385, 9766, 2496, 9841, -1)),
        ASGARNIAN_ICE_DUNGEON("Asgarnian Ice Dungeon", new Bounds(2980, 9531, 3085, 9601, -1)),
        BRIMHAVEN_DUNGEON("Brimhaven Dungeon", new Bounds(2625, 9404, 2751, 9599, -1)),
        ABYSSAL_AREA("Abyssal Area", new Bounds(3007, 4863, 3071, 4926, -1)),
        MYTHS_GUILD_DUNGEON("Myths' Guild Dungeon", new Bounds(1876, 8960, 2072, 9085, -1)),
        WATERBIRTH_ISLAND("Waterbirth Island", new Bounds(2495, 3713, 2557, 3772, -1)),
        GODWARS_DUNGEON("Godwars Dungeon", new Bounds(2816, 5255, 2942, 5370, -1)),
        BRINE_RAT_CAVERN("Brine Rat Cavern", new Bounds(2686, 10116, 2751, 10175, -1)),
        SMOKE_DUNGEON("Smoke Dungeon", new Bounds(3200, 9344, 3326, 9404, -1)),
        KALPHITE_LAIR("Kalphite Lair", new Bounds(3455, 9473, 3517, 9531, -1)),
        TROLL_STRONGHOLD("Troll Stronghold", new Bounds(2823, 10045, 2871, 10112, -1)),
        KARUULM_SLAYER_DUNGEON("Karuulm Slayer Dungeon", new Bounds(1248, 10146, 1390, 10282, -1)),
        LITHKREN_VAULT("Lithkren Vault", new Bounds(1536, 5058, 1596, 5118, -1)),
        LIGHTHOUSE("Lighthouse", new Bounds(2498, 4610, 2542, 4665, -1)),
        KALPHITE_CAVE("Kalphite Cave", new Bounds(3264, 9473, 3340, 9542, -1)),
        SMOKE_DEVIL_DUNGEON("Smoke Devil Dungeon", new Bounds(2345, 9408, 2431, 9470, -1)),
        MOURNER_TUNNELS("Mourner Tunnels", new Bounds(1855, 4608, 2045, 4670, -1)),
        EVIL_CHICKEN_LAIR("Evil Chicken's Lair", new Bounds(2445, 4353, 2493, 4408, -1)),
        CHASM_OF_FIRE("Chasm of Fire", new Bounds(1409, 10050, 1468, 10108, -1)),
        OGRE_ENCLAVE("Ogre Enclave", new Bounds(2561, 9407, 2621, 9469, -1));

        private final String name;
        private final Bounds[] boundaries;

        TaskLocation(String name, Bounds... boundaries) {
            this.name = name;
            this.boundaries = boundaries;
        }

        public String getName() {
            return name;
        }

        public boolean inside(Position pos) {
            for (Bounds b : boundaries)
                if (b.inBounds(pos.getX(), pos.getY(), pos.getZ(), 0))
                    return true;
            return false;
        }
    }

}
