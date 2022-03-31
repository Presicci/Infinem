package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.cache.EnumMap;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.farming.patch.impl.*;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/28/2022
 */
public class Geomancy extends Spell {

    private enum GeomancyData {
        FALADOR_NW(8, PatchData.FALADOR_NORTH),   //Falador (NW)
        FALADOR_SE(9, PatchData.FALADOR_SOUTH),   //Falador (SE)
        CATHERBY_N(10, PatchData.CATHERBY_NORTH),   //Catherby (N)
        CATHERBY_S(11, PatchData.CATHERBY_SOUTH),   //Catherby (S)
        ARDY_N(12, PatchData.ARDOUGNE_NORTH),   //Ardougne (N)
        ARDY_S(13, PatchData.ARDOUGNE_SOUTH),   //Ardougne (S)
        MORY_NW(14, PatchData.CANIFIS_NORTH),   //Morytania (NW)
        MORY_SE(15, PatchData.CANIFIS_SOUTH),   //Morytania (SE)
        HARMONY(142, PatchData.HARMONY_ALLOTMENT),   //Harmony
        HOSIDIUS_NE(51, PatchData.ZEAH_NORTH),   //Hosidius (NE)
        HOSIDIUS_SW(52, PatchData.ZEAH_SOUTH),   //Hosidius (SW)
        FALADOR_FLOWER(27, PatchData.FALADOR_FLOWER),   //Falador
        CATHERBY_FLOWER(28, PatchData.CATHERBY_FLOWER),   //Catherby
        ARDY_FLOWER(29, PatchData.ARDOUGNE_FLOWER),   //Ardougne
        MORY_FLOWER(30, PatchData.CANIFIS_FLOWER),   //Morytania
        HOSIDIUS_FLOWER(53, PatchData.ZEAH_FLOWER),   //Hosidius
        FALADOR_HERB(43, PatchData.FALADOR_HERB),   //Falador
        CATHERBY_HERB(44, PatchData.CATHERBY_HERB),   //Catherby
        ARDY_HERB(45, PatchData.ARDOUGNE_HERB),   //Ardougne
        MORY_HERB(46, PatchData.CANIFIS_HERB),   //Morytania
        HARMONY_HERB(143, PatchData.HARMONY_HERB),   //Harmony
        HOSIDIUS_HERB(54, PatchData.ZEAH_HERB),   //Hosidius
        TROLL_STRONGHOLD_HERB(47, PatchData.TROLLHEIM_HERB),   //Troll Stronghold
        WEISS_HERB(152, PatchData.WEISS_HERB),   //Weiss
        FALADOR_COMPOST(39, PatchData.FALADOR_COMPOST_BIN),   //Falador
        CATHERBY_COMPOST(40, PatchData.CATHERBY_COMPOST_BIN),   //Catherby
        MORY_COMPOST(41, PatchData.CANIFIS_COMPOST_BIN),   //Morytania
        ARDY_COMPOST(42, PatchData.ARDOUGNE_COMPOST_BIN),   //Ardougne
        HOSIDIUS_COMPOST(55, PatchData.ZEAH_COMPOST_BIN),   //Hosidius
        PRIF_COMPOST(169, PatchData.PRIF_COMPOST_BIN),   //Prifddinas
        FARMING_GUILD_COMPOST(141, PatchData.FARMING_GUILD_COMPOST_BIN),   //Farming Guild
        YANILLE_HOPS(16, PatchData.YANILLE_HOPS),   //Yanille
        ENTRANA_HOPS(17, PatchData.ENTRANA_HOPS),   //Entrana
        LUM_HOPS(18, PatchData.LUMBRIDGE_HOPS),   //Lumbridge
        SEERS_HOPS(19, PatchData.SEERS_HOPS),   //Seers' Village
        VARROCK_BUSH(20, PatchData.VARROCK_BUSH),   //Varrock
        RIMMINGTON_BUSH(21, PatchData.RIMMINGTON_BUSH),   //Rimmington
        ETC_BUSH(22, PatchData.ETCETERIA_BUSH),   //Etceteria
        ARDY_BUSH(23, PatchData.ARDOUGNE_BUSH),   //Ardougne
        GNOME_FRUIT(4, PatchData.GNOME_FRUIT),   //Gnome Stronghold
        TGNOME_FRUIT(5, PatchData.VILLAGE_FRUIT),   //Tree Gnome Village
        BRIM_FRUIT(6, PatchData.BRIMHAVEN_FRUIT),   //Brimhaven
        CATHERBY_FRUIT(7, PatchData.CATHERBY_FRUIT),   //Catherby
        LLETYA_FRUIT(49, PatchData.LLETYA_FRUIT),   //Lletya
        TAVERLY_TREE(0, PatchData.TAVERLEY_TREE),   //Taverley
        FALADOR_TREE(1, PatchData.FALADOR_TREE),   //Falador
        VARROCK_TREE(2, PatchData.VARROCK_TREE),   //Varrock
        LUM_TREE(3, PatchData.LUMBRIDGE_TREE),   //Lumbridge
        GNOME_TREE(48, PatchData.GNOME_TREE),   //Gnome Stronghold
        SARIM_SPIRIT_TREE(24, PatchData.PORT_SARIM_SPIRIT_TREE),   //Port Sarim
        ETC_SPIRIT_TREE(25, PatchData.ETCETERIA_SPIRIT_TREE),   //Etceteria
        BRIM_SPIRIT_TREE(26, PatchData.BRIMHAVEN_SPIRIT_TREE),   //Brimhaven
        HOSIDIUS_SPIRIT_TREE(50, PatchData.ZEAH_SPIRIT_TREE),   //Hosidius
        CANIFIS_MUSHROOM(37, PatchData.CANIFIS_MUSHROOM),   //Canifis
        TAI_CALQUAT(31, PatchData.CALQUAT),   //Tai Bwo Wannai
        KHARID_CACTUS(36, PatchData.CACTUS),   //Al Kharid
        DRAYNOR_BELLADONNA(38, PatchData.DRAYNOR_MANOR_BELLADONNA),   //Draynor Manor
        VINERY_W1(56, PatchData.VINERY_W1),   //Vinery (W1)
        VINERY_W2(57, PatchData.VINERY_W2),   //Vinery (W2)
        VINERY_W3(58, PatchData.VINERY_W3),   //Vinery (W3)
        VINERY_W4(59, PatchData.VINERY_W4),   //Vinery (W4)
        VINERY_W5(60, PatchData.VINERY_W5),   //Vinery (W5)
        VINERY_W6(61, PatchData.VINERY_W6),   //Vinery (W6)
        VINERY_E1(62, PatchData.VINERY_E1),   //Vinery (E1)
        VINERY_E2(63, PatchData.VINERY_E2),   //Vinery (E2)
        VINERY_E3(64, PatchData.VINERY_E3),   //Vinery (E3)
        VINERY_E4(65, PatchData.VINERY_E4),   //Vinery (E4)
        VINERY_E5(66, PatchData.VINERY_E5),   //Vinery (E5)
        VINERY_E6(67, PatchData.VINERY_E6),   //Vinery (E6)
        FOSSIL_SEAWEED1(147, PatchData.SEAWEED_PATCH1),   //Fossil Island
        FOSSIL_SEAWEED2(148, PatchData.SEAWEED_PATCH2),   //Fossil Island
        FOSSIL_HARDWOOD1(149, PatchData.FOSSIL_ISLAND_HARDWOOD),   //Fossil Island
        FOSSIL_HARDWOOD2(150, PatchData.FOSSIL_ISLAND_HARDWOOD1),   //Fossil Island
        FOSSIL_HARDWOOD3(151, PatchData.FOSSIL_ISLAND_HARDWOOD2),   //Fossil Island
        FARMING_GUILD_FRUIT(153, PatchData.FARMING_GUILD_FRUIT),   //Farming Guild
        FARMING_GUILD_SPIRIT_TREE(154, PatchData.FARMING_GUILD_SPIRIT_TREE),   //Farming Guild
        FARMING_GUILD_BUSH(155, PatchData.FARMING_GUILD_BUSH),   //Farming Guild
        FARMING_GUILD_N(156, PatchData.FARMING_GUILD_NORTH),   //Farming Guild (N)
        FARMING_GUILD_S(157, PatchData.FARMING_GUILD_SOUTH),   //Farming Guild (S)
        PRIF_N(166, PatchData.PRIF_NORTH),   //Prifddinas (N)
        PRIF_S(167, PatchData.PRIF_SOUTH),   //Prifddinas (S)
        FARMING_GUILD_HERB(158, PatchData.FARMING_GUILD_HERB),   //Farming Guild
        FARMING_GUILD_CACTUS(159, PatchData.FARMING_GUILD_CACTUS),   //Farming Guild
        FARMING_GUILD_TREE(160, PatchData.FARMING_GUILD_TREE),   //Farming Guild
        FARMING_GUILD_FLOWER(161, PatchData.FARMING_GUILD_FLOWER),   //Farming Guild
        PRIF_FLOWER(168, PatchData.PRIF_FLOWER),   //Prifddinas
        FARMING_GUILD_HESPORI(164, PatchData.HESPORI),   //Farming Guild
        FARMING_GUILD_REDWOOD(162, PatchData.FARMING_GUILD_REDWOOD),   //Farming Guild
        FARMING_GUILD_CELASTRUS(163, PatchData.FARMING_GUILD_CELASTRUS),   //Farming Guild
        FARMING_GUILD_ANIMA(165, PatchData.FARMING_GUILD_ANIMA),   //Farming Guild
        PRIF_CRYSTAL_TREE(170, PatchData.PRIF_CRYSTAL_TREE);   //Prifddinas

        public final int val;
        public final PatchData patchData;

        GeomancyData(int val) {
            this(val, null);
        }

        GeomancyData(int val, PatchData patchData) {
            this.val = val;
            this.patchData = patchData;
        }
    }

    private static void openPatchInterface(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.GEOMANCY);
        for (GeomancyData data : GeomancyData.values()) {
            Map<Integer, Integer> items = EnumMap.get(1236).ints();
            PatchData pd = data.patchData;
            if (pd == null) {
                continue;
            }
            Patch patch = player.getFarming().getPatch(pd);
            if (patch instanceof CompostBin && patch.getStage() == 0 && patch.getProduceCount() == 0) {
                player.getPacketSender().sendClientScript(1119, "iiii", data.val, getItemId(patch), 0, 0);
                player.getPacketSender().sendItem(179, items.get(data.val), getItemId(patch), 1);
            } else if (!patch.isRaked() && !(patch instanceof CompostBin)) {
                player.getPacketSender().sendClientScript(1119, "iiii", data.val, 6055, 0, 0);
                player.getPacketSender().sendItem(179, items.get(data.val), 6055, 1);
            } else if (patch.isRaked() && patch.getPlantedCrop() == null && !(patch instanceof CompostBin)) {
                player.getPacketSender().sendClientScript(1119, "iiii", data.val, 1925, 0, 0);
                player.getPacketSender().sendItem(179, items.get(data.val), 1925, 1);
            } else {
                /* Bit flags
                 * 0 - 1 - Diseased
                 * 1 - 2 - Dead
                 * 2 - 4 - Gardener protecting
                 * 3 - 8 - Nearby flower protecting
                 * 4 - 16 - Watered if z(location) == widget(0, 1)
                 * 5 - 32 - Compost
                 * 6 - 64 - Supercompost
                 * 7 - 128 - Ultracompost
                 * 8 - 256 - The people of Hosidius are portecting the patch as a favour
                 */
                int bitMask = 0;
                if (patch.isDiseased())
                    bitMask |= 1;
                if (patch.isDead())
                    bitMask |= 1 << 1;
                if (!patch.isFullyGrown() && !patch.isDead() && patch.isFarmerProtected())
                    bitMask |= 1 << 2;
                if (!patch.isFullyGrown() && !patch.isDead() && patch.isFlowerProtected())
                    bitMask |= 1 << 3;
                if (!patch.isFullyGrown() && !patch.isDead()
                        && (patch instanceof FlowerPatch || patch instanceof HopsPatch || patch instanceof AllotmentPatch))
                    bitMask |= 1 << 4;
                if (patch.getCompost() == 1)
                    bitMask |= 1 << 5;
                if (patch.getCompost() == 2)
                    bitMask |= 1 << 6;
                if (patch.getCompost() == 3)
                    bitMask |= 1 << 7;
                //bitMask |= 1 << 8;    The people of Hosidius are portecting the patch as a favour

                // 1 bit for water / 8 bits for min yield / 6 bits for min / 8 unknown bits / 6 bits for max
                int growthMask = 0;
                growthMask |= patch instanceof CompostBin ? 2 : patch.getPlantedCrop().getTotalStages();
                growthMask |= patch.getStage() << 14;
                if (patch.isFullyGrown()) {
                    growthMask |= patch.getProduceCount() << 20;
                }
                if (patch.isWatered()) {
                    growthMask |= 1 << 28;
                }
                player.getPacketSender().sendClientScript(1119, "iiii", data.val, getItemId(patch), growthMask, bitMask);
                player.getPacketSender().sendItem(179, items.get(data.val), getItemId(patch), 1);
            }
        }
    }

    private static int getItemId(Patch patch) {
        int produceId = patch.getPlantedCrop() == null ? 0 : patch.getPlantedCrop().getProduceId();
        if (patch instanceof AnimaPatch) {
            return patch.getPlantedCrop().getSeed() == 22881 ? 22940 : patch.getPlantedCrop().getSeed() == 22883 ? 22939 : 22938;
        }
        if (patch instanceof SpiritTreePatch) {
            return 2015;
        }
        if (patch instanceof CompostBin) {
            return ((CompostBin) patch).getProductId();
        }
        //return 23044; Hespori
        return produceId;
    }

    Item[] runes = {
            Rune.LAW.toItem(0),
            Rune.ASTRAL.toItem(0),
            Rune.COSMIC.toItem(0)
    };

    public Geomancy() {
        registerClick(1, 1, true, runes, (player, integer) -> {
            openPatchInterface(player);
            return false;
        });
    }

    static {
        //InterfaceHandler.register(Interface.MONSTER_EXAMINE, h -> {
        //    h.actions[5] = (SimpleAction) MonsterExamine::overview;
        //});
    }
}
