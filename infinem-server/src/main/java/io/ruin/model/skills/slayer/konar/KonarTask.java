package io.ruin.model.skills.slayer.konar;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/19/2024
 */
@Getter
public enum KonarTask {
    ABERRANT_SPECTRE(41, KonarTaskLocation.SLAYER_TOWER, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.STRONGHOLD_SLAYER_CAVE),
    ABYSSAL_DEMON(42, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.ABYSSAL_AREA, KonarTaskLocation.SLAYER_TOWER),
    ADAMANT_DRAGON(108, KonarTaskLocation.LITHKREN_VAULT),
    ANKOU(79, KonarTaskLocation.STRONGHOLD_OF_SECURITY, KonarTaskLocation.STRONGHOLD_SLAYER_CAVE, KonarTaskLocation.CATACOMBS_OF_KOUREND),
    AVIANSIES(94, KonarTaskLocation.GODWARS_DUNGEON),
    BASILISKS(43, KonarTaskLocation.JORMUNGANDS_PRISON, KonarTaskLocation.FREMENNIK_DUNGEON),
    BLACK_DEMON(30, KonarTaskLocation.TAVERLY_DUNGEON, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.CHASM_OF_FIRE),
    BLACK_DRAGON(27, KonarTaskLocation.TAVERLY_DUNGEON, KonarTaskLocation.EVIL_CHICKEN_LAIR, KonarTaskLocation.MYTHS_GUILD_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND),
    BLOODVELD(48, KonarTaskLocation.STRONGHOLD_SLAYER_CAVE, KonarTaskLocation.GODWARS_DUNGEON, KonarTaskLocation.SLAYER_TOWER, KonarTaskLocation.CATACOMBS_OF_KOUREND), // Missing - Iorwerth dungeon (req Prif access), Meiyerditch Laboratories
    BLUE_DRAGON(25, KonarTaskLocation.TAVERLY_DUNGEON, KonarTaskLocation.OGRE_ENCLAVE, KonarTaskLocation.MYTHS_GUILD_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.ISLE_OF_SOULS_DUNGEON), // Missing - Ruins of Tapoyauik
    BRINE_RAT(84, KonarTaskLocation.BRINE_RAT_CAVERN),
    BRONZE_DRAGON(58, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND),
    CAVE_KRAKEN(92, KonarTaskLocation.KRAKEN_COVE),
    DAGANNOTH(35, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.WATERBIRTH_ISLAND), // Missing - Lighthouse, Jormungand's prison
    DARK_BEAST(66, KonarTaskLocation.MOURNER_TUNNELS),  // Missing - Iorwerth dungeon (req Prif access)
    DRAKE(112, KonarTaskLocation.KARUULM_SLAYER_DUNGEON),
    DUST_DEVIL(49, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.SMOKE_DUNGEON),
    FIRE_GIANT(16, KonarTaskLocation.KARUULM_SLAYER_DUNGEON, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.STRONGHOLD_SLAYER_CAVE, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.WATERFALL_DUNGEON, KonarTaskLocation.ISLE_OF_SOULS_DUNGEON, KonarTaskLocation.GIANTS_DEN),
    FOSSILE_ISLAND_WYVERN(106, KonarTaskLocation.WYVERN_CAVE),
    GARGOYLE(46, KonarTaskLocation.SLAYER_TOWER),
    GREATER_DEMON(29, KonarTaskLocation.CHASM_OF_FIRE, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.KARUULM_SLAYER_DUNGEON, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.ISLE_OF_SOULS_DUNGEON),
    HELLHOUND(31, KonarTaskLocation.STRONGHOLD_SLAYER_CAVE, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.TAVERLY_DUNGEON, KonarTaskLocation.KARUULM_SLAYER_DUNGEON, KonarTaskLocation.WITCHAVEN_DUNGEON),
    HYDRA(113, KonarTaskLocation.KARUULM_SLAYER_DUNGEON),
    IRON_DRAGON(59, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.ISLE_OF_SOULS_DUNGEON),
    JELLY(50, KonarTaskLocation.RELEKKA_SLAYER_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND), // Missing - Ruins of Tapoyauik
    KALPHITE(53, KonarTaskLocation.KALPHITE_LAIR, KonarTaskLocation.KALPHITE_CAVE),
    KURASK(45, KonarTaskLocation.RELEKKA_SLAYER_DUNGEON),  // Missing - Iorwerth dungeon (req Prif access)
    // LESSER_NAGUA
    LIZARDMEN(90, KonarTaskLocation.BATTLEFRONT, KonarTaskLocation.LIZARDMAN_CANYON, KonarTaskLocation.LIZARDMAN_SETTLEMENT, KonarTaskLocation.KEBOS_SWAMP, KonarTaskLocation.MOLCH),
    MITHRIL_DRAGON(93, KonarTaskLocation.ANCIENT_CAVERN),
    MUTATED_ZYGOMITES(74, KonarTaskLocation.FOSSIL_ISLAND, KonarTaskLocation.ZANARIS),
    NECHRYAEL(52, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.SLAYER_TOWER),  // Missing - Iorwerth dungeon (req Prif access)
    RED_DRAGON(26, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND, KonarTaskLocation.FORTHOS_DUNGEON, KonarTaskLocation.MYTHS_GUILD_DUNGEON),
    RUNE_DRAGON(109, KonarTaskLocation.LITHKREN_VAULT),
    SKELETAL_WYVERN(72, KonarTaskLocation.ASGARNIAN_ICE_DUNGEON),
    SMOKE_DEVIL(95, KonarTaskLocation.SMOKE_DEVIL_DUNGEON),
    STEEL_DRAGON(60, KonarTaskLocation.BRIMHAVEN_DUNGEON, KonarTaskLocation.CATACOMBS_OF_KOUREND),
    TROLL(18, KonarTaskLocation.TROLL_STRONGHOLD, KonarTaskLocation.DEATH_PLATEAU), // Missing - Keldagrim, South ofg Mount Quidamortem
    TUROTH(36, KonarTaskLocation.RELEKKA_SLAYER_DUNGEON),
    VAMPYRES(34, KonarTaskLocation.DARKMEYER, KonarTaskLocation.SLEPE), // Missing - Meiyerditch
    // WARPED_CREATURES
    // WATERFIENDS
    WYRM(111, KonarTaskLocation.KARUULM_SLAYER_DUNGEON);    // Missing - Neypotzli

    private final int uid;
    private final KonarTaskLocation[] locations;

    KonarTask(int uid, KonarTaskLocation... location) {
        this.uid = uid;
        this.locations = location;
    }

    /**
     * Generates a location from the task's array of locations.
     *
     * @param player   The player getting the task.
     * @param taskUuid The task being assigned.
     */
    public static void assignLocation(Player player, int taskUuid) {
        Optional<KonarTask> task = Arrays.stream(values()).filter(s -> s.getUid() == taskUuid).findFirst();
        if (!task.isPresent()) {
            return;
        }
        KonarTask t = task.get();
        List<KonarTaskLocation> locations = new ArrayList<>(Arrays.asList(t.locations));
        List<KonarTaskRequirement> requirements = KonarTaskRequirement.REQUIREMENTS.getOrDefault(t, new ArrayList<>());
        for (KonarTaskRequirement requirement : requirements) {
            if (!requirement.test(player)) locations.remove(requirement.getTaskLocation());
        }
        KonarTaskLocation location = Random.get(locations);
        player.slayerLocation = location.ordinal();
    }
}
