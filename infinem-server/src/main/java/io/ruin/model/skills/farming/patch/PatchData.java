package io.ruin.model.skills.farming.patch;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;

public enum PatchData {

    FALADOR_HERB(8150, Config.FARMING_PATCH_4, 6),
    CATHERBY_HERB(8151, Config.FARMING_PATCH_4, 6),
    ARDOUGNE_HERB(8152, Config.FARMING_PATCH_4, 6),
    CANIFIS_HERB(8153, Config.FARMING_PATCH_4, 6),
    TROLLHEIM_HERB(18816, Config.FARMING_PATCH_1, 6),
    ZEAH_HERB(27115, Config.FARMING_PATCH_4, 6),
    FARMING_GUILD_HERB(33979, Config.FARMING_COMPOST_BIN, 6),
    HARMONY_HERB(9372, Config.FARMING_PATCH_2, 6),
    WEISS_HERB(33176, Config.FARMING_PATCH_1, 6),

    FALADOR_FLOWER(7847, Config.FARMING_PATCH_3, 5),
    CATHERBY_FLOWER(7848, Config.FARMING_PATCH_3, 5),
    ARDOUGNE_FLOWER(7849, Config.FARMING_PATCH_3, 5),
    CANIFIS_FLOWER(7850, Config.FARMING_PATCH_3, 5),
    ZEAH_FLOWER(27111, Config.FARMING_PATCH_3, 5),
    FARMING_GUILD_FLOWER(33649, Config.FARMING_PATCH_7906, 5),

    FALADOR_NORTH(8550, Config.FARMING_PATCH_1, 0),
    FALADOR_SOUTH(8551, Config.FARMING_PATCH_2, 0),
    CATHERBY_NORTH(8552, Config.FARMING_PATCH_1, 0),
    CATHERBY_SOUTH(8553, Config.FARMING_PATCH_2, 0),
    ARDOUGNE_NORTH(8554, Config.FARMING_PATCH_1, 0),
    ARDOUGNE_SOUTH(8555, Config.FARMING_PATCH_2, 0),
    CANIFIS_NORTH(8556, Config.FARMING_PATCH_1, 0),
    CANIFIS_SOUTH(8557, Config.FARMING_PATCH_2, 0),
    ZEAH_NORTH(27113, Config.FARMING_PATCH_1, 0),
    ZEAH_SOUTH(27114, Config.FARMING_PATCH_2, 0),
    FARMING_GUILD_NORTH(33694, Config.FARMING_PATCH_3, 0),
    FARMING_GUILD_SOUTH(33693, Config.FARMING_PATCH_4, 0),
    HARMONY_ALLOTMENT(21950, Config.FARMING_PATCH_1, 0),

    TAVERLEY_TREE(8388, "saplings", Config.FARMING_PATCH_1, 2),
    FALADOR_TREE(8389, "saplings", Config.FARMING_PATCH_1, 2),
    VARROCK_TREE(8390, "saplings", Config.FARMING_PATCH_1, 2),
    LUMBRIDGE_TREE(8391, "saplings", Config.FARMING_PATCH_1, 2),
    GNOME_TREE(19147, "saplings", Config.FARMING_PATCH_1, 2),
    FARMING_GUILD_TREE(33732, "saplings", Config.FARMING_PATCH_7905, 2),

    GNOME_FRUIT(7962, "saplings", Config.FARMING_PATCH_2, 3),
    VILLAGE_FRUIT(7963, "saplings", Config.FARMING_PATCH_1, 3),
    BRIMHAVEN_FRUIT(7964, "saplings", Config.FARMING_PATCH_1, 3),
    CATHERBY_FRUIT(7965, "saplings", Config.FARMING_PATCH_1, 3),
    LLETYA_FRUIT(26579, "saplings", Config.FARMING_PATCH_1, 3),
    FARMING_GUILD_FRUIT(34007, "saplings", Config.FARMING_PATCH_7909, 3),

    FALADOR_COMPOST_BIN(7836, Config.FARMING_COMPOST_BIN, -1),
    CATHERBY_COMPOST_BIN(7837, Config.FARMING_COMPOST_BIN, -1),
    CANIFIS_COMPOST_BIN(7838, Config.FARMING_COMPOST_BIN, -1),
    ARDOUGNE_COMPOST_BIN(7839, Config.FARMING_COMPOST_BIN, -1),
    ZEAH_COMPOST_BIN(27112, Config.FARMING_COMPOST_BIN, -1),
    FARMING_GUILD_COMPOST_BIN(34631, Config.FARMING_PATCH_7912, -1),
    PRIF_COMPOST_BIN(34920, Config.FARMING_PATCH_4, -1),

    VARROCK_BUSH(7577, Config.FARMING_PATCH_1, 4),
    RIMMINGTON_BUSH(7578, Config.FARMING_PATCH_1, 4),
    ETCETERIA_BUSH(7579, Config.FARMING_PATCH_1, 4),
    ARDOUGNE_BUSH(7580, Config.FARMING_PATCH_1, 4),
    FARMING_GUILD_BUSH(34006, Config.FARMING_PATCH_2, 4),

    YANILLE_HOPS(8173, Config.FARMING_PATCH_1, 1),
    ENTRANA_HOPS(8174, Config.FARMING_PATCH_1, 1),
    LUMBRIDGE_HOPS(8175, Config.FARMING_PATCH_1, 1),
    SEERS_HOPS(8176, Config.FARMING_PATCH_1, 1),

    CALQUAT(7807, "saplings", Config.FARMING_PATCH_1, 7),
    CACTUS(7771, Config.FARMING_PATCH_1, 7),
    FARMING_GUILD_CACTUS(33761, Config.FARMING_PATCH_7904, 7),
    CANIFIS_MUSHROOM(8337, Config.FARMING_PATCH_1, 7),
    FARMING_GUILD_CELASTRUS(34629, "saplings", Config.FARMING_PATCH_7910, 7),
    FARMING_GUILD_ANIMA(33998, Config.FARMING_PATCH_7911, 7),
    FARMING_GUILD_REDWOOD(34059, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD1(34058, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD2(34057, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD3(34056, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD4(34055, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD5(34054, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD6(34053, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD7(34052, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD8(34051, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD9(34635, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD10(34633, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD11(34637, "saplings", Config.FARMING_PATCH_7907, 7),
    FARMING_GUILD_REDWOOD12(34639, "saplings", Config.FARMING_PATCH_7907, 7),

    FOSSIL_ISLAND_HARDWOOD(30481, "saplings", Config.FARMING_PATCH_3, 7),
    FOSSIL_ISLAND_HARDWOOD1(30480, "saplings", Config.FARMING_PATCH_2, 7),
    FOSSIL_ISLAND_HARDWOOD2(30482, "saplings", Config.FARMING_PATCH_1, 7),

    HESPORI(34630, Config.HESPORI_PATCH, 7),

    DRAYNOR_MANOR_BELLADONNA(7572, Config.FARMING_PATCH_1, 7),

    PRIF_CRYSTAL_TREE(34906, Config.FARMING_COMPOST_BIN, 7),
    PRIF_SOUTH(34921, Config.FARMING_PATCH_2, 0),
    PRIF_NORTH(34922, Config.FARMING_PATCH_1, 0),
    PRIF_FLOWER(34919, Config.FARMING_PATCH_3, 5),

    BRIMHAVEN_SPIRIT_TREE(8383, "saplings", Config.FARMING_PATCH_2, 7),
    PORT_SARIM_SPIRIT_TREE(8338, "saplings", Config.FARMING_PATCH_1, 7),
    ETCETERIA_SPIRIT_TREE(8382, "saplings", Config.FARMING_PATCH_2, 7),
    ZEAH_SPIRIT_TREE(27116, "saplings", Config.FARMING_PATCH_7904, 7),
    FARMING_GUILD_SPIRIT_TREE(33733, "saplings", Config.FARMING_PATCH_1, 7),

    VINERY_W1(11816, Config.VINERY_PATCH_7, 7),
    VINERY_W2(11817, Config.VINERY_PATCH_8, 7),
    VINERY_W3(11947, Config.VINERY_PATCH_9, 7),
    VINERY_W4(12598, Config.VINERY_PATCH_10, 7),
    VINERY_W5(12599, Config.VINERY_PATCH_11, 7),
    VINERY_W6(12600, Config.VINERY_PATCH_12, 7),
    VINERY_E1(11810, Config.VINERY_PATCH_1, 7),
    VINERY_E2(11811, Config.VINERY_PATCH_2, 7),
    VINERY_E3(11812, Config.VINERY_PATCH_3, 7),
    VINERY_E4(11813, Config.VINERY_PATCH_4, 7),
    VINERY_E5(11814, Config.VINERY_PATCH_5, 7),
    VINERY_E6(11815, Config.VINERY_PATCH_6, 7),

    SEAWEED_PATCH1(30501, Config.FARMING_PATCH_2, 7),
    SEAWEED_PATCH2(30500, Config.FARMING_PATCH_1, 7);

    PatchData(int objectId, Config config, int guideChildId) {
        this.objectId = objectId;
        this.config = config;
        this.type = "seeds";
        this.guideChildId = guideChildId;
    }


    PatchData(int objectId, String type, Config config, int guideChildId) {
        this.objectId = objectId;
        this.config = config;
        this.type = type;
        this.guideChildId = guideChildId;
    }

    public int getObjectId() {
        return objectId;
    }

    public Config getConfig() {
        return config;
    }

    public int getGuideChildId() {
        return guideChildId;
    }

    public String getType() {
        return type;
    }

    private final int objectId;
    private final Config config;
    private final String type;
    private final int guideChildId;

    static {
        for (PatchData pd : values()) {
            for (int i = 1; i < 6; i++) {
                final int opt = i;
                ObjectAction.register(pd.getObjectId(), opt, ((player, obj) -> player.getFarming().handleObject(obj, opt)));
            }
            ItemObjectAction.register(pd.getObjectId(), ((player, item, obj) -> player.getFarming().itemOnPatch(item, obj)));
        }
        Tile.getObject(34055, 1228, 3754, 0).nearPosition = (p, obj) -> Position.of(1233, 3754);

    }

}
