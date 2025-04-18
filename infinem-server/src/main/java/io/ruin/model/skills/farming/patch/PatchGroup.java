package io.ruin.model.skills.farming.patch;

import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PatchGroup {

    FALADOR(new Bounds(new Position(3055, 3308, 0), 25),
            Arrays.asList(PatchData.FALADOR_FLOWER, PatchData.FALADOR_HERB, PatchData.FALADOR_NORTH, PatchData.FALADOR_SOUTH, PatchData.FALADOR_COMPOST_BIN)),
    CATHERBY(new Bounds(new Position(2810, 3464, 0), 25),
            Arrays.asList(PatchData.CATHERBY_FLOWER, PatchData.CATHERBY_HERB, PatchData.CATHERBY_NORTH, PatchData.CATHERBY_SOUTH, PatchData.CATHERBY_COMPOST_BIN)),
    CANIFIS(new Bounds(new Position(3601, 3526, 0), 25),
            Arrays.asList(PatchData.CANIFIS_FLOWER, PatchData.CANIFIS_HERB, PatchData.CANIFIS_NORTH, PatchData.CANIFIS_SOUTH, PatchData.CANIFIS_COMPOST_BIN)),
    ARDOUGNE(new Bounds(new Position(2666, 3375, 0), 25),
            Arrays.asList(PatchData.ARDOUGNE_FLOWER, PatchData.ARDOUGNE_HERB, PatchData.ARDOUGNE_NORTH, PatchData.ARDOUGNE_SOUTH, PatchData.ARDOUGNE_COMPOST_BIN)),
    ZEAH(new Bounds(new Position(1733, 3555, 0), 25),
            Arrays.asList(PatchData.ZEAH_FLOWER, PatchData.ZEAH_HERB, PatchData.ZEAH_NORTH, PatchData.ZEAH_SOUTH, PatchData.ZEAH_COMPOST_BIN)),
    FARMING_GUILD(Bounds.fromRegions(4922, 4765),
            Arrays.asList(PatchData.FARMING_GUILD_COMPOST_BIN, PatchData.FARMING_GUILD_HERB, PatchData.FARMING_GUILD_SPIRIT_TREE, PatchData.FARMING_GUILD_ANIMA,
                    PatchData.FARMING_GUILD_BUSH, PatchData.FARMING_GUILD_CACTUS, PatchData.FARMING_GUILD_CELASTRUS, PatchData.FARMING_GUILD_FLOWER,
                    PatchData.FARMING_GUILD_FRUIT, PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_REDWOOD, PatchData.FARMING_GUILD_SOUTH,
                    PatchData.FARMING_GUILD_TREE, PatchData.HESPORI)),
    HARMONY(Bounds.fromRegion(15148), Arrays.asList(PatchData.HARMONY_HERB, PatchData.HARMONY_ALLOTMENT)),
    WEISS(Bounds.fromRegion(11325), Collections.singletonList(PatchData.WEISS_HERB)),
    TROLLHEIM(new Bounds(new Position(2827, 3694, 0), 25),
            Collections.singletonList(PatchData.TROLLHEIM_HERB)),
    FALADOR_TREE(new Bounds(new Position(3004, 3373, 0), 25),
            Collections.singletonList(PatchData.FALADOR_TREE)),
    VARROCK(new Bounds(new Position(3229, 3459, 0), 32),
            Collections.singletonList(PatchData.VARROCK_TREE)),
    TAVERLEY(new Bounds(new Position(2936, 3438, 0), 20),
            Collections.singletonList(PatchData.TAVERLEY_TREE)),
    LUMBRIDGE(new Bounds(new Position(3193, 3231, 0), 32),
            Collections.singletonList(PatchData.LUMBRIDGE_TREE)),
    GNOME_STRONGHOLD(new Bounds(new Position(2461, 3444, 0), 64),
            Arrays.asList(PatchData.GNOME_TREE, PatchData.GNOME_FRUIT)),
    CATHERBY_BEACH(new Bounds(new Position(2860, 3433, 0), 20),
            Collections.singletonList(PatchData.CATHERBY_FRUIT)),
    BRIMHAVEN(new Bounds(new Position(2770, 3213, 0), 30),
            Arrays.asList(PatchData.BRIMHAVEN_FRUIT, PatchData.BRIMHAVEN_SPIRIT_TREE)),
    GNOME_VILLAGE(new Bounds(new Position(2490, 3180, 0), 25),
            Collections.singletonList(PatchData.VILLAGE_FRUIT)),
    LLETYA(new Bounds(new Position(2347, 3162, 0), 25),
            Collections.singletonList(PatchData.LLETYA_FRUIT)),
    VARROCK_BUSH(new Bounds(new Position(3181, 3358, 0), 25),
            Collections.singletonList(PatchData.VARROCK_BUSH)),
    RIMMINGTON_BUSH(new Bounds(new Position(2940, 3222, 0), 25),
            Collections.singletonList(PatchData.RIMMINGTON_BUSH)),
    ETCETERIA(new Bounds(new Position(2603, 3857, 0), 40), // spirit tree in this group
            Arrays.asList(PatchData.ETCETERIA_BUSH, PatchData.ETCETERIA_SPIRIT_TREE)),
    ARDOUGNE_BUSH(new Bounds(new Position(2617, 3226, 0), 25),
            Collections.singletonList(PatchData.ARDOUGNE_BUSH)),
    YANILLE_HOPS(new Bounds(new Position(2575, 3104, 0), 25),
            Collections.singletonList(PatchData.YANILLE_HOPS)),
    ENTRANA_HOPS(new Bounds(new Position(2810, 3336, 0), 25),
            Collections.singletonList(PatchData.ENTRANA_HOPS)),
    LUMBRIDGE_HOPS(new Bounds(new Position(3229, 3315, 0), 25),
            Collections.singletonList(PatchData.LUMBRIDGE_HOPS)),
    SEERS_HOPS(new Bounds(new Position(2666, 3525, 0), 25),
            Collections.singletonList(PatchData.SEERS_HOPS)),
    CALQUAT(new Bounds(new Position(2796, 3101, 0), 25),
            Collections.singletonList(PatchData.CALQUAT)),
    CACTUS(new Bounds(new Position(3315, 3203, 0), 25),
            Collections.singletonList(PatchData.CACTUS)),
    PORT_SARIM_SPIRIT_TREE(new Bounds(new Position(3060, 3258, 0), 25),
            Collections.singletonList(PatchData.PORT_SARIM_SPIRIT_TREE)),
    ZEAH_SPIRIT_TREE(new Bounds(new Position(1693, 3542, 0), 25),
            Collections.singletonList(PatchData.ZEAH_SPIRIT_TREE)),
    CANIFIS_MUSHROOM(new Bounds(new Position(3452, 3473, 0), 25),
            Collections.singletonList(PatchData.CANIFIS_MUSHROOM)),
    FOSSIL_ISLAND(new Bounds(new Position(3708, 3836), 25),
            Arrays.asList(PatchData.FOSSIL_ISLAND_HARDWOOD, PatchData.FOSSIL_ISLAND_HARDWOOD1, PatchData.FOSSIL_ISLAND_HARDWOOD2)),
    DRAYNOR_MANOR(new Bounds(new Position(3087, 3353), 25),
            Collections.singletonList(PatchData.DRAYNOR_MANOR_BELLADONNA)),
    PRIFDDINAS_CRYSTAL_TREE(new Bounds(new Position(3293, 6118), 25),
            Collections.singletonList(PatchData.PRIF_CRYSTAL_TREE)),
    PRIFDDINAS(new Bounds(new Position(3292, 6101), 25),
            Arrays.asList(PatchData.PRIF_NORTH, PatchData.PRIF_SOUTH, PatchData.PRIF_FLOWER, PatchData.PRIF_COMPOST_BIN)),
    GRAPEVINES(Bounds.fromRegion(7223),
            Arrays.asList(PatchData.VINERY_E1, PatchData.VINERY_E2, PatchData.VINERY_E3, PatchData.VINERY_E4, PatchData.VINERY_E5, PatchData.VINERY_E6,
                    PatchData.VINERY_W1, PatchData.VINERY_W2, PatchData.VINERY_W3, PatchData.VINERY_W4, PatchData.VINERY_W5, PatchData.VINERY_W6)),
    SEAWEEDS(Bounds.fromRegion(15008),
            Arrays.asList(PatchData.SEAWEED_PATCH1, PatchData.SEAWEED_PATCH2)),

    /*
     * Varlamore
     */
    ORTUS_FARM(Bounds.fromRegion(6192),
            Arrays.asList(PatchData.VARLAMORE_NORTH, PatchData.VARLAMORE_SOUTH, PatchData.VARLAMORE_FLOWER,
                    PatchData.VARLAMORE_HERB, PatchData.VARLAMORE_COMPOST_BIN)),
    LOCUS_OASIS(Bounds.fromRegion(6702), Collections.singletonList(PatchData.VARLAMORE_TREE)),
    ALDARIN(Bounds.fromRegion(5421), Collections.singletonList(PatchData.VARLAMORE_HOPS));

    private final Bounds bounds;
    private final List<PatchData> patches;

    PatchGroup(Bounds bounds, List<PatchData> patches) {
        this.bounds = bounds;
        this.patches = patches;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public List<PatchData> getPatches() {
        return patches;
    }


}
