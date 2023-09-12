package io.ruin.model.entity.player.killcount;

import io.ruin.cache.NPCDef;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/10/2023
 */
public enum SlayerKillCounter {
    ABERRANT_SPECTRES("Aberrant spectres"),
    ABYSSAL_DEMONS("Abyssal demons"),
    BANSHEES("Banshees"),
    //BASILISK_KNIGHTS("Basilisk knights"),
    BASILISKS("Basilisks"),
    BLOODVELDS("Bloodvelds"),
    BRINE_RATS("Brine rats"),
    BRUTAL_BLACK_DRAGONS("Brutal black dragons"),
    CAVE_BUGS("Cave bugs"),
    CAVE_CRAWLERS("Cave crawlers"),
    CAVE_HORRORS("Cave horrors"),
    CAVE_KRAKEN("Cave kraken"),
    CAVE_SLIMES("Cave slime"),
    COCKATRICE("Cockatrice"),
    CRAWLING_HANDS("Crawling hands"),
    DARK_BEASTS("Dark beasts"),
    DRAKES("Drakes"),
    DUST_DEVILS("Dust devils"),
    FEVER_SPIDERS("Fever spiders"),
    FOSSIL_ISLAND_WYVERNS("Fossile Island Wyverns", 7792, 7793, 7794, 7795),
    GARGOYLES("Gargoyles"),
    //HARPIE_BUG_SWARMS("Harpie Bug Swarms"),
    HYDRAS("Hydras"),
    INFERNAL_MAGES("Infernal mages"),
    JELLIES("Jellies", "jelly"),
    //KILLERWATTS("Killerwatts"),
    KURASK("Kurask"),
    LIZARDS("Lizards"),
    MOGRES("Mogres"),
    //MOLANISKS("Molanisks"),
    MUTATED_ZYGOMITES("Mutated zygomites", "zygomite"),
    NECHRYAEL("Nechryael"),
    PYREFIENDS("Pyrefiends"),
    REVENANTS("Revenants"),
    ROCKSLUGS("Rockslugs"),
    SEA_SNAKES("Sea snakes"),
    SKELETAL_WYVERNS("Skeletal wyverns"),
    SMOKE_DEVILS("Smoke devils"),
    SPIRITUAL_MAGES("Spiritual mages"),
    SPIRITUAL_RANGERS("Spiritual rangers"),
    SPIRITUAL_WARRIORS("Spiritual warriors"),
    SUPERIOR_CREATURES("Superior creatures",
            7388, // Crushing hand
            7389, // Chasm crawler
            7390, // Screaming banshee
            7391, // Screaming twisted banshee
            7392, // Giant rockslug
            7393, // Cockathrice
            7394, // Flaming pyrelord
            9465, // Infernal pyrelord,
            7395, 9287, 9288, // Monstrous basilisk
            7396, // Malevolent mage
            7397, // Insatiable bloodveld
            7398, // Insatiable mutated bloodveld
            7399, // Vitreous jelly
            7400, // Vitreous warped jelly
            10397, // Spiked turoth
            7401, // Cave abomination
            7402, // Abhorrent spectre
            7403, // Repugnant spectre
            9258, // Basilisk sentinel
            10398, 10399, // Shadow wyrm
            7404, // Choke devil
            7405, // King kurask
            7407, 7408, // Marble gargoyle
            7411, // Nechryarch
            10400, 10401, // Guardian drake
            7410, // Greater abyssal demon
            7409, // Night beast
            7406, // Nuclear smoke devil
            10402 // Colossal hydra
    ),
    TERROR_DOGS("Terror dogs"),
    TUROTH("Turoth"),
    VYREWATCH("Vyrewatch"),
    WALL_BEASTS("Wall Beasts"),
    WYRMS("Wyrms");

    public final String name;

    SlayerKillCounter(String name) {
        this.name = name;
        NPCDef.cached.values().stream().filter(Objects::nonNull)
                .filter(def -> def.name.toLowerCase().contains(name.toLowerCase().substring(0, name.length() - 1)))
                .forEach(def -> def.killCounterType = new KillCounterType(this));
    }

    SlayerKillCounter(String name, String npcName) {
        this.name = name;
        NPCDef.cached.values().stream().filter(Objects::nonNull)
                .filter(def -> def.name.toLowerCase().contains(npcName.toLowerCase()))
                .forEach(def -> def.killCounterType = new KillCounterType(this));
    }

    SlayerKillCounter(String name, int... ids) {
        this.name = name;
        if (NPCDef.cached.values().stream()
                .filter(Objects::nonNull)
                .filter(def -> {
                    if (Arrays.stream(ids).anyMatch(i -> i == def.id)) {
                        return true;
                    }
                    return false;
                }).count() <= 0)
            System.out.println("No match for " + Arrays.toString(ids));
    }
}
