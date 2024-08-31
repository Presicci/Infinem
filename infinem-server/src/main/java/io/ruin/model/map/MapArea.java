package io.ruin.model.map;

import io.ruin.model.entity.player.Player;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.impl.Lightables;
import io.ruin.model.map.object.actions.impl.OldFirePit;
import io.ruin.model.skills.firemaking.DorgeshKaanLamps;
import lombok.Getter;

import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
@Getter
public enum MapArea {
    LUMBRIDGE_CASTLE(12850),
    VARROCK(12598, 12597, 12596, 12854, 12853, 12852, 13107, 13109, 13108),
    ARDOUGNE(9779, 10035, 10291, 10292, 10547, 10548, 10803, 10804),
    DRAGON_FORGE(1744, 5277, 1760, 5293, 1),
    WIZARD_TOWER(12337),
    REVENANT_CAVES(12703, 12702, 12701, 12959, 12958, 12957),
    WILDERNESS_SLAYER_CAVE(13470, 13469, 13726, 13725),
    PRIFDDINAS(12894, 12895, 13150, 13151),
    IORWERTH_DUNGEON(12737, 12738, 12993, 12994),
    FISHING_GUILD(2596, 3394, 2614, 3426, 0, (player -> {
        player.getTaskManager().doLookupByUUID(575, 1); // Enter the Fishing Guild
    })),
    MAGE_ARENA(3093, 3921, 3117, 3946, 0),
    MAGE_ARENA_BANK(2528, 4711, 2549, 4723, 0, player -> {
        player.getTaskManager().doLookupByUUID(844);    // Enter the Mage Arena Bank
    }),
    WILDERNESS_RESOURCE_AREA(3174, 3924, 3196, 3944, 0),
    FALADOR_FARM(3044, 3300, 3065, 3318, 0),
    CRAFTING_GUILD(2929, 3279, 2943, 3288, 0),
    COOKING_GUILD(3144, 3447, 3148, 3452, 0),
    EXCLUSIVE_AMETHYST_MINE(2999, 9705, 3012, 9728, 0),
    COMBAT_TRAINING_CAMP_ENTRANCE(2516, 3357, 2519, 3360, 0, player -> {
        player.getTaskManager().doLookupByUUID(561);    // Enter the Combat Training Camp
    }),
    WILDERNESS(player -> player.wildernessLevel > 0),
    ASGARNIA_CHAOS_TEMPLE(2930, 3513, 2940, 3518, 0),
    ASGARNIA_CHAOS_TEMPLE_SECOND_FLOOR(2938, 3516, 2940, 3518, 1),
    DEEP_WILDERNESS_DUNGEON(12193),
    WILDERNESS_GODWARS_DUNGEON(12190),
    WILDERNESS_GODWARS_DUNGEON_ENTRANCE(3062, 10155, 3070, 10160, 3, player -> {
        player.getTaskManager().doLookupByUUID(843);    // Enter the Wilderness God Wars Dungeon
    }),
    SLAYER_TOWER(13623, 13723),
    DONDAKANS_MINE(Bounds.fromRegions(10061, 10317), (player, logout) -> {
        if (logout) player.getMovement().teleport(2824, 10169, 0);
    }),
    FEROX_1(3133, 3617, 3136, 3620, 0, player -> {
        player.getTaskManager().doLookupByUUID(845);    // Visit Ferox Enclave
    }),
    FEROX_2(3123, 3626, 3145, 3632, 0, player -> {
        player.getTaskManager().doLookupByUUID(845);    // Visit Ferox Enclave
    }),
    FEROX_3(3132, 3633, 3137, 3639, 0, player -> {
        player.getTaskManager().doLookupByUUID(845);    // Visit Ferox Enclave
    }),
    FEROX_4(3144, 3626, 3154, 3645, 0, player -> {
        player.getTaskManager().doLookupByUUID(845);    // Visit Ferox Enclave
    }),
    FOUNTAIN_OF_RUNE(new Bounds(3367, 3890, 3387, 3901, 0), player -> Config.FOUNTAIN_OF_RUNE.set(player, 1), (player, logout) -> Config.FOUNTAIN_OF_RUNE.set(player, 0)),
    VARROCK_SEWER(3152, 9856, 3312, 9919, 0),
    RELLEKKA_ROCK_CRABS(2655, 3710, 2727, 3735, 0),
    PORT_PHASMATYS(3653, 3457, 3726, 3507, 0, player -> player.getTaskManager().doLookupByUUID(709)),
    GNOME_STRONGHOLD(9525, 9526, 9781, 9782),
    AL_KHARID(13105, 13106),
    // Dark caves
    LUMBRIDGE_SWAMP_CAVE(OldFirePit.FirePit.LUMBRIDGE_SWAMP_CAVES_FIRE, 3, 12693, 12949),
    CAVE_OF_HORROR(OldFirePit.FirePit.MOS_LE_HARMLESS_FIRE, 3, 14994, 14995, 15251),
    MOLE_LAIR(OldFirePit.FirePit.GIANT_MOLE_FIRE, 3, 6992, 6993),
    CHASM_OF_TEARS(null, 2, 12948),
    HAUNTED_MINE_FLOOR_6(null, 1, 11077),
    DORGESH_KAAN(Bounds.fromRegions(10834, 10835, 11091, 11092, 11348, 11349), (player -> {
        int broken = DorgeshKaanLamps.getBrokenLamps(player);
        if (broken >= DorgeshKaanLamps.MAX_BROKEN) return;
        DorgeshKaanLamps.breakLamp(player, DorgeshKaanLamps.MAX_BROKEN - broken);
    })),
    DORGESH_KAAN_CAVE(null, 3, 3221, 9603, 3307, 9662, 0),
    DORGESH_KAAN_DUNGEON(null, 3, 10833),
    SHAYZIEN_CRYPT(null, 3, 6043),
    SLAYER_TOWER_BASEMENT(null, 1, 13723),
    SOPHANEM_DUNGEON(null, 3, 13200),
    TEMPLE_OF_IKOV_LOWER_LEVEL(null, 1, 2639, 9760, 2654, 9767, 0)
    ;

    private OldFirePit.FirePit firePit;
    private final Bounds bounds;
    private Predicate<Player> predicate;

    MapArea(OldFirePit.FirePit firepit, int darknessLevel, int southWestX, int southWestY, int northEastX, int northEastY, int z) {
        this(firepit, darknessLevel, new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z));
    }

    MapArea(OldFirePit.FirePit firepit, int darknessLevel, int... regionId) {
        this(firepit, darknessLevel, Bounds.fromRegions(regionId));
    }

    MapArea(OldFirePit.FirePit firepit, int darknessLevel, Bounds bounds) {
        this.bounds = bounds;
        this.firePit = firepit;
        registerOnEnter(bounds, (player -> {
            player.putTemporaryAttribute(AttributeKey.DARKNESS_LEVEL, darknessLevel);
            if (!Lightables.hasLightSource(player) && (firepit == null || !firepit.isBuilt(player))) {
                player.putTemporaryAttribute(AttributeKey.DARKNESS_TICKS, 0);
                player.openInterface(InterfaceType.SECONDARY_OVERLAY, darknessLevel == 1 ? 97 : darknessLevel == 2 ? 98 : 96);
            } else {
                player.putTemporaryAttribute(AttributeKey.DARKNESS_TICKS, -1);
            }
        }));
        registerOnExit(bounds, ((player, logout) -> {
            player.removeTemporaryAttribute(AttributeKey.DARKNESS_TICKS);
            player.removeTemporaryAttribute(AttributeKey.DARKNESS_LEVEL);
            player.closeInterface(InterfaceType.SECONDARY_OVERLAY);
        }));
    }

    MapArea(int... regionId) {
        this.bounds = Bounds.fromRegions(regionId);
    }

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
    }

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z, MapListener.EnteredAction enteredAction) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
        registerOnEnter(bounds, enteredAction);
    }

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z, MapListener.ExitAction exitAction) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
        registerOnExit(bounds, exitAction);
    }

    MapArea(Predicate<Player> predicate) {
        this.bounds = null;
        this.predicate = predicate;
    }

    MapArea(Bounds bounds, MapListener.EnteredAction enteredAction) {
        this.bounds = bounds;
        registerOnEnter(bounds, enteredAction);
    }

    MapArea(Bounds bounds, MapListener.ExitAction exitAction) {
        this.bounds = bounds;
        registerOnExit(bounds, exitAction);
    }

    MapArea(Bounds bounds,  MapListener.EnteredAction enteredAction, MapListener.ExitAction exitAction) {
        this.bounds = bounds;
        registerOnEnter(bounds, enteredAction);
        registerOnExit(bounds, exitAction);
    }

    private void registerOnEnter(Bounds bounds, MapListener.EnteredAction enteredAction) {
        MapListener.registerBounds(bounds).onEnter(enteredAction);
    }

    private void registerOnExit(Bounds bounds, MapListener.ExitAction exitAction) {
        MapListener.registerBounds(bounds).onExit(exitAction);
    }

    public boolean inArea(Player player) {
        if (predicate != null && predicate.test(player)) return true;
        if (bounds == null) return false;
        return bounds.inBounds(player);
    }

    public boolean inArea(Position position) {
        if (bounds == null) return false;
        return bounds.inBounds(position);
    }

    public boolean hasFirePitInArea(Player player) {
        if (!inArea(player))
            return false;
        if (firePit == null)
            return false;
        return firePit.isBuilt(player);
    }

    public static MapArea getMapArea(Player player) {
        for (MapArea mapArea : values()) {
            if (mapArea.inArea(player))
                return mapArea;
        }
        return null;
    }
}
