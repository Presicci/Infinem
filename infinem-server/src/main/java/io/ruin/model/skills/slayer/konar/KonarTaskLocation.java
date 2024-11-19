package io.ruin.model.skills.slayer.konar;

import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/19/2024
 */
@Getter
public enum KonarTaskLocation {
    NONE(-1, ""),
    STRONGHOLD_OF_SECURITY(31, "Stronghold of Security",
            new Bounds(1853, 5184, 1919, 5246, -1),
            new Bounds(1982, 5183, 2049, 5247, -1),
            new Bounds(2113, 5248, 2175, 5310, -1),
            new Bounds(2302, 5184, 2368, 5247, -1)),
    DEATH_PLATEAU(21, "Death Plateau", new Bounds(2836, 3579, 2890, 3607, -1)),
    RELEKKA_SLAYER_DUNGEON(26, "Fremennik Slayer Dungeon", new Bounds(2680, 9950, 2814, 10045, -1)),
    KRAKEN_COVE(13, "Kraken Cove", new Bounds(2238, 9983, 2304, 10048, -1)),
    CATACOMBS_OF_KOUREND(1, "Catacombs of Kourend", new Bounds(1588, 9978, 1747, 10110, -1)),
    SLAYER_TOWER(9, "Slayer Tower", 13623, 13723),
    TAVERLY_DUNGEON(6, "Taverly Dungeon", new Bounds(2812, 9666, 2969, 9855, -1)),
    WITCHAVEN_DUNGEON(7, "Witchaven Dungeon", new Bounds(2686, 9663, 2750, 9720, -1)),
    WATERFALL_DUNGEON(8, "Waterfall Dungeon", new Bounds(2558, 9860, 2596, 9916, -1)),
    STRONGHOLD_SLAYER_CAVE(2, "Stronghold Slayer Cave", new Bounds(2385, 9766, 2496, 9841, -1)),
    ASGARNIAN_ICE_DUNGEON(33, "Asgarnian Ice Dungeon", new Bounds(2980, 9531, 3085, 9601, -1)),
    BRIMHAVEN_DUNGEON(5, "Brimhaven Dungeon", new Bounds(2625, 9404, 2751, 9599, -1)),
    ABYSSAL_AREA(38, "The Abyss", new Bounds(3007, 4863, 3071, 4926, -1)),
    MYTHS_GUILD_DUNGEON(27, "Myths' Guild Dungeon", new Bounds(1876, 8960, 2072, 9085, -1)),
    WATERBIRTH_ISLAND(15, "Waterbirth Island", 9886, 10142, 7236, 7748, 7492, 7236, 11589, 11588),
    GODWARS_DUNGEON(10, "God Wars Dungeon", new Bounds(2816, 5255, 2942, 5370, -1)),
    BRINE_RAT_CAVERN(35, "Brine Rat Cavern", new Bounds(2686, 10116, 2751, 10175, -1)),
    SMOKE_DUNGEON(20, "Smoke Dungeon", new Bounds(3168, 9344, 3326, 9404, -1)),
    KALPHITE_LAIR(11, "Kalphite Lair", new Bounds(3455, 9473, 3517, 9531, -1)),
    TROLL_STRONGHOLD(22, "Troll Stronghold", new Bounds(2823, 10045, 2871, 10112, -1)),
    KARUULM_SLAYER_DUNGEON(3, "Karuulm Slayer Dungeon", new Bounds(1248, 10146, 1390, 10282, -1)),
    LITHKREN_VAULT(29, "Lithkren Vault", new Bounds(1536, 5058, 1596, 5118, -1)),
    LIGHTHOUSE(14, "Lighthouse", new Bounds(2498, 4610, 2542, 4665, -1)),
    KALPHITE_CAVE(12, "Kalphite Cave", new Bounds(3264, 9473, 3340, 9542, -1)),
    SMOKE_DEVIL_DUNGEON(19, "Smoke Devil Dungeon", new Bounds(2345, 9408, 2431, 9470, -1)),
    MOURNER_TUNNELS(28, "Mourner Tunnels", new Bounds(1855, 4608, 2045, 4670, -1)),
    EVIL_CHICKEN_LAIR(37, "Evil Chicken's Lair", new Bounds(2445, 4353, 2493, 4408, -1)),
    CHASM_OF_FIRE(4, "Chasm of Fire", new Bounds(1409, 10050, 1468, 10108, -1)),
    OGRE_ENCLAVE(34, "Ogre Enclave", new Bounds(2561, 9407, 2621, 9469, -1)),
    WYVERN_CAVE(32, "Wyvern Cave", Bounds.fromRegions(14495, 14496)),
    BATTLEFRONT(40, "Battlefront", new Bounds(1327, 3684, 1407, 3745, -1)),
    LIZARDMAN_CANYON(16, "Lizardman Canyon", new Bounds(1473, 3685, 1544, 3725, -1)),
    LIZARDMAN_SETTLEMENT(18, "Lizardman Settlement", 5175, 5431, 5275),
    KEBOS_SWAMP(39, "Kebos Swamp", new Bounds(1232, 3589, 1332, 3644, -1)),
    MOLCH(17, "Molch", new int[] { 5277 }, new Bounds(1272, 3639, 1328, 3690, -1)),
    ANCIENT_CAVERN(30, "Ancient Cavern", new Bounds(1730, 5274, 1790, 5370, -1)),
    FREMENNIK_DUNGEON(26, "Fremennik Slayer Dungeon", new Bounds(2691, 9987, 2814, 10046, -1)),
    JORMUNGANDS_PRISON(43, "Jormungand's Prison", new Bounds(2394, 10368, 2493, 10467, -1)),
    DARKMEYER(44, "Darkmeyer", new Bounds(3587, 3329, 3670, 3398, -1)),
    MEIYERDITCH(46, "Meiyerditch", new Bounds(3589, 3169, 3647, 3328, -1)), // Laboratories?
    SLEPE(45, "Slepe", new Bounds(3681, 3264, 3775, 3413, -1)),
    ISLE_OF_SOULS_DUNGEON(47, "Isle of Souls Dungeon", 8593),
    ;

    @Getter
    private final String name;
    private final Bounds[] boundaries;
    private final Region[] regions;
    // Id for enum 4064
    @Getter private final int enumId;

    KonarTaskLocation(int enumId, String name) {
        this.enumId = enumId;
        this.name = name;
        this.boundaries = new Bounds[0];
        this.regions = new Region[0];
    }

    KonarTaskLocation(int enumId, String name, Bounds... boundaries) {
        this.enumId = enumId;
        this.name = name;
        this.boundaries = boundaries;
        this.regions = new Region[0];
    }

    KonarTaskLocation(int enumId, String name, int... regionIds) {
        this.enumId = enumId;
        this.name = name;
        this.boundaries = new Bounds[0];
        List<Region> regions = new ArrayList<>(regionIds.length);
        for (int id : regionIds) {
            regions.add(Region.get(id));
        }
        this.regions = regions.toArray(new Region[0]);
    }

    KonarTaskLocation(int enumId, String name, int[] regionIds, Bounds... boundaries) {
        this.enumId = enumId;
        this.name = name;
        this.boundaries = boundaries;
        List<Region> regions = new ArrayList<>(regionIds.length);
        for (int id : regionIds) {
            regions.add(Region.get(id));
        }
        this.regions = regions.toArray(new Region[0]);
    }

    public boolean inside(Position pos) {
        for (Bounds b : boundaries)
            if (b.inBounds(pos.getX(), pos.getY(), pos.getZ(), 0))
                return true;
        for (Region r : regions)
            if (r.isInside(pos))
                return true;
        return false;
    }
}
