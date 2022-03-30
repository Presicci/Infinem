package io.ruin.model.skills.farming;


import com.google.gson.annotations.Expose;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.farming.crop.impl.*;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.farming.patch.PatchGroup;
import io.ruin.model.skills.farming.patch.impl.*;

import java.util.*;

public class Farming {

    private Player player;
    private Map<Integer, Patch> patches = new HashMap<>();
    @Expose private ToolStorage storage = new ToolStorage();
    @Expose private CompostBin faladorCompostBin, canifisCompostBin, catherbyCompostBin, ardougneCompostBin, zeahCompostBin, farmingGuildCompost, prifCompostBin;
    @Expose private HerbPatch canifisHerb, ardougneHerb, faladorHerb, catherbyHerb, trollheimHerb, zeahHerb, farmingGuildHerb, harmonyHerb, weissHerb;
    @Expose private FlowerPatch faladorFlower, catherbyFlower, ardougneFlower, canifisFlower, zeahFlower, farmingGuildFlower, prifFlower;
    @Expose private AllotmentPatch faladorNorth, faladorSouth, catherbyNorth, catherbySouth, ardougneNorth, ardougneSouth, canifisNorth,
            canifisSouth, zeahNorth, zeahSouth, farmingGuildNorth, farmingGuildSouth, prifNorth, prifSouth, harmonyAllotment;
    @Expose private WoodTreePatch lumbridgeTree, faladorTree, varrockTree, gnomeTree, taverleyTree, farmingGuildTree;
    @Expose private FruitTreePatch catherbyFruit, brimhavenFruit, gnomeFruit, villageFruit, lletyaFruit, farmingGuildFruit;
    @Expose private BushPatch varrockBush, ardougneBush, etceteriaBush, rimmingtonBush, farmingGuildBush;
    @Expose private HopsPatch lumbridgeHops, seersHops, yanilleHops, entranaHops;
    @Expose private CalquatTreePatch calquat;
    @Expose private CactusPatch cactus, farmingGuildCactus;
    @Expose private CelastrusPatch celastrus;
    @Expose private SpiritTreePatch brimhavenSpiritTree, etceteriaSpiritTree, portSarimSpiritTree, zeahSpiritTree, farmingGuildSpiritTree;
    @Expose private MushroomPatch canifisMushroom;
    @Expose private RedwoodPatch redwood;
    @Expose private AnimaPatch anima;
    @Expose private HardWoodTreePatch hardWoodTreeOne, hardWoodTreeTwo, hardWoodTreeThree;
    @Expose private BelladonnaPatch belladonnaPatch;
    @Expose private CrystalTreePatch crystalTreePatch;
    @Expose private HesporiPatch hesporiPatch;

    private PatchGroup visibleGroup;

    public Farming() {

    }

    public void init(Player player) {
        this.player = player;
        storage.setPlayer(player);
        if (faladorHerb == null) { // if one is null, all need to be initialized, this is a new player

            faladorHerb = new HerbPatch();
            catherbyHerb = new HerbPatch();
            ardougneHerb = new HerbPatch();
            canifisHerb = new HerbPatch();
            trollheimHerb = new HerbPatch();

            faladorFlower = new FlowerPatch();
            catherbyFlower = new FlowerPatch();
            ardougneFlower = new FlowerPatch();
            canifisFlower = new FlowerPatch();

            faladorNorth = new AllotmentPatch();
            faladorSouth = new AllotmentPatch();
            catherbyNorth = new AllotmentPatch();
            catherbySouth = new AllotmentPatch();
            ardougneNorth = new AllotmentPatch();
            ardougneSouth = new AllotmentPatch();
            canifisNorth = new AllotmentPatch();
            canifisSouth = new AllotmentPatch();

            taverleyTree = new WoodTreePatch();
            faladorTree = new WoodTreePatch();
            varrockTree = new WoodTreePatch();
            lumbridgeTree = new WoodTreePatch();
            gnomeTree = new WoodTreePatch();

            gnomeFruit = new FruitTreePatch();
            villageFruit = new FruitTreePatch();
            brimhavenFruit = new FruitTreePatch();
            catherbyFruit = new FruitTreePatch();
            lletyaFruit = new FruitTreePatch();
        }

        if (faladorCompostBin == null) {
            faladorCompostBin = new CompostBin();
            canifisCompostBin = new CompostBin();
            catherbyCompostBin = new CompostBin();
            ardougneCompostBin = new CompostBin();
        }

        if (zeahNorth == null) {
            zeahNorth = new AllotmentPatch();
            zeahSouth = new AllotmentPatch();
            zeahFlower = new FlowerPatch();
            zeahHerb = new HerbPatch();
            zeahCompostBin = new CompostBin();
        }

        if (varrockBush == null) {
            varrockBush = new BushPatch();
            etceteriaBush = new BushPatch();
            rimmingtonBush = new BushPatch();
            ardougneBush = new BushPatch();
        }

        if (lumbridgeHops == null) {
            lumbridgeHops = new HopsPatch();
            entranaHops = new HopsPatch();
            seersHops = new HopsPatch();
            yanilleHops = new HopsPatch();
        }

        if (calquat == null) {
            calquat = new CalquatTreePatch();
        }
        if(cactus == null) {
            cactus = new CactusPatch();
        }
        if (brimhavenSpiritTree == null) {
            brimhavenSpiritTree = new SpiritTreePatch();
            etceteriaSpiritTree = new SpiritTreePatch();
            zeahSpiritTree = new SpiritTreePatch();
            portSarimSpiritTree = new SpiritTreePatch();
        }

        if (canifisMushroom == null) {
            canifisMushroom = new MushroomPatch();
        }

        if (celastrus == null) {
            celastrus = new CelastrusPatch();
        }

        if (redwood == null) {
            redwood = new RedwoodPatch();
        }

        if (anima == null) {
            anima = new AnimaPatch();
        }

        if (farmingGuildNorth == null) {
            farmingGuildBush = new BushPatch();
            farmingGuildNorth = new AllotmentPatch();
            farmingGuildSpiritTree = new SpiritTreePatch();
            farmingGuildCactus = new CactusPatch();
            farmingGuildTree = new WoodTreePatch();
            farmingGuildSouth = new AllotmentPatch();
            farmingGuildHerb = new HerbPatch();
            farmingGuildCompost = new CompostBin();
            farmingGuildFlower = new FlowerPatch();
            farmingGuildFruit = new FruitTreePatch();
        }

        if (hardWoodTreeOne == null) {
            hardWoodTreeOne = new HardWoodTreePatch();
            hardWoodTreeTwo = new HardWoodTreePatch();
            hardWoodTreeThree = new HardWoodTreePatch();
        }

        if (belladonnaPatch == null) {
            belladonnaPatch = new BelladonnaPatch();
        }

        if (crystalTreePatch == null) {
            crystalTreePatch = new CrystalTreePatch();
        }

        if (prifFlower == null) {
            prifFlower = new FlowerPatch();
            prifNorth = new AllotmentPatch();
            prifSouth = new AllotmentPatch();
        }

        if (harmonyAllotment == null) {
            harmonyAllotment = new AllotmentPatch();
            harmonyHerb = new HerbPatch();
        }

        if (weissHerb == null) {
            weissHerb = new HerbPatch();
        }

        if (prifCompostBin == null) {
            prifCompostBin = new CompostBin();
        }

        if (hesporiPatch == null) {
            hesporiPatch = new HesporiPatch();
        }

        addPatch(faladorCompostBin.set(PatchData.FALADOR_COMPOST_BIN).setPlayer(player));
        addPatch(catherbyCompostBin.set(PatchData.CATHERBY_COMPOST_BIN).setPlayer(player));
        addPatch(canifisCompostBin.set(PatchData.CANIFIS_COMPOST_BIN).setPlayer(player));
        addPatch(ardougneCompostBin.set(PatchData.ARDOUGNE_COMPOST_BIN).setPlayer(player));

        addPatch(faladorHerb.set(PatchData.FALADOR_HERB).setPlayer(player));
        addPatch(catherbyHerb.set(PatchData.CATHERBY_HERB).setPlayer(player));
        addPatch(ardougneHerb.set(PatchData.ARDOUGNE_HERB).setPlayer(player));
        addPatch(canifisHerb.set(PatchData.CANIFIS_HERB).setPlayer(player));
        addPatch(trollheimHerb.set(PatchData.TROLLHEIM_HERB).setPlayer(player));

        addPatch(faladorFlower.set(PatchData.FALADOR_FLOWER).setPlayer(player));
        addPatch(catherbyFlower.set(PatchData.CATHERBY_FLOWER).setPlayer(player));
        addPatch(ardougneFlower.set(PatchData.ARDOUGNE_FLOWER).setPlayer(player));
        addPatch(canifisFlower.set(PatchData.CANIFIS_FLOWER).setPlayer(player));

        addPatch(faladorNorth.set(PatchData.FALADOR_NORTH, faladorFlower).setPlayer(player));
        addPatch(faladorSouth.set(PatchData.FALADOR_SOUTH, faladorFlower).setPlayer(player));
        addPatch(catherbyNorth.set(PatchData.CATHERBY_NORTH, catherbyFlower).setPlayer(player));
        addPatch(catherbySouth.set(PatchData.CATHERBY_SOUTH, catherbyFlower).setPlayer(player));
        addPatch(ardougneNorth.set(PatchData.ARDOUGNE_NORTH, ardougneFlower).setPlayer(player));
        addPatch(ardougneSouth.set(PatchData.ARDOUGNE_SOUTH, ardougneFlower).setPlayer(player));
        addPatch(canifisNorth.set(PatchData.CANIFIS_NORTH, canifisFlower).setPlayer(player));
        addPatch(canifisSouth.set(PatchData.CANIFIS_SOUTH, canifisFlower).setPlayer(player));

        addPatch(taverleyTree.set(PatchData.TAVERLEY_TREE).setPlayer(player));
        addPatch(faladorTree.set(PatchData.FALADOR_TREE).setPlayer(player));
        addPatch(varrockTree.set(PatchData.VARROCK_TREE).setPlayer(player));
        addPatch(lumbridgeTree.set(PatchData.LUMBRIDGE_TREE).setPlayer(player));
        addPatch(gnomeTree.set(PatchData.GNOME_TREE).setPlayer(player));

        addPatch(gnomeFruit.set(PatchData.GNOME_FRUIT).setPlayer(player));
        addPatch(villageFruit.set(PatchData.VILLAGE_FRUIT).setPlayer(player));
        addPatch(brimhavenFruit.set(PatchData.BRIMHAVEN_FRUIT).setPlayer(player));
        addPatch(catherbyFruit.set(PatchData.CATHERBY_FRUIT).setPlayer(player));
        addPatch(lletyaFruit.set(PatchData.LLETYA_FRUIT).setPlayer(player));

        addPatch(zeahNorth.set(PatchData.ZEAH_NORTH).setPlayer(player));
        addPatch(zeahSouth.set(PatchData.ZEAH_SOUTH).setPlayer(player));
        addPatch(zeahFlower.set(PatchData.ZEAH_FLOWER).setPlayer(player));
        addPatch(zeahHerb.set(PatchData.ZEAH_HERB).setPlayer(player));
        addPatch(zeahCompostBin.set(PatchData.ZEAH_COMPOST_BIN).setPlayer(player));

        addPatch(varrockBush.set(PatchData.VARROCK_BUSH).setPlayer(player));
        addPatch(rimmingtonBush.set(PatchData.RIMMINGTON_BUSH).setPlayer(player));
        addPatch(etceteriaBush.set(PatchData.ETCETERIA_BUSH).setPlayer(player));
        addPatch(ardougneBush.set(PatchData.ARDOUGNE_BUSH).setPlayer(player));

        addPatch(lumbridgeHops.set(PatchData.LUMBRIDGE_HOPS).setPlayer(player));
        addPatch(entranaHops.set(PatchData.ENTRANA_HOPS).setPlayer(player));
        addPatch(yanilleHops.set(PatchData.YANILLE_HOPS).setPlayer(player));
        addPatch(seersHops.set(PatchData.SEERS_HOPS).setPlayer(player));

        addPatch(calquat.set(PatchData.CALQUAT).setPlayer(player));
        addPatch(cactus.set(PatchData.CACTUS).setPlayer(player));

        addPatch(brimhavenSpiritTree.setTeleportPosition(SpiritTreePatch.BRIMHAVEN_TELEPORT).set(PatchData.BRIMHAVEN_SPIRIT_TREE).setPlayer(player));
        addPatch(portSarimSpiritTree.setTeleportPosition(SpiritTreePatch.PORT_SARIM_TELEPORT).set(PatchData.PORT_SARIM_SPIRIT_TREE).setPlayer(player));
        addPatch(etceteriaSpiritTree.setTeleportPosition(SpiritTreePatch.ETCETERIA_TELEPORT).set(PatchData.ETCETERIA_SPIRIT_TREE).setPlayer(player));
        addPatch(zeahSpiritTree.setTeleportPosition(SpiritTreePatch.ZEAH_TELEPORT).set(PatchData.ZEAH_SPIRIT_TREE).setPlayer(player));

        addPatch(farmingGuildSpiritTree.setTeleportPosition(SpiritTreePatch.FARMING_GUILD_TELEPORT).set(PatchData.FARMING_GUILD_SPIRIT_TREE).setPlayer(player));
        addPatches(redwood.set(PatchData.FARMING_GUILD_REDWOOD).setPlayer(player), 34051, 34052, 34053, 34054, 34055, 34056, 34057, 34058, 34059,
                34637, 34639, 34635, 34633  // Upper level chop objects
        );
        addPatch(anima.set(PatchData.FARMING_GUILD_ANIMA).setPlayer(player));
        addPatch(celastrus.set(PatchData.FARMING_GUILD_CELASTRUS).setPlayer(player));
        addPatch(farmingGuildBush.set(PatchData.FARMING_GUILD_BUSH).setPlayer(player));
        addPatch(farmingGuildTree.set(PatchData.FARMING_GUILD_TREE).setPlayer(player));
        addPatch(farmingGuildHerb.set(PatchData.FARMING_GUILD_HERB).setPlayer(player));
        addPatch(farmingGuildCactus.set(PatchData.FARMING_GUILD_CACTUS).setPlayer(player));
        addPatch(farmingGuildNorth.set(PatchData.FARMING_GUILD_NORTH, farmingGuildFlower).setPlayer(player));
        addPatch(farmingGuildSouth.set(PatchData.FARMING_GUILD_SOUTH, farmingGuildFlower).setPlayer(player));
        addPatch(farmingGuildFlower.set(PatchData.FARMING_GUILD_FLOWER).setPlayer(player));
        addPatch(farmingGuildFruit.set(PatchData.FARMING_GUILD_FRUIT).setPlayer(player));
        addPatch(farmingGuildCompost.set(PatchData.FARMING_GUILD_COMPOST_BIN).setPlayer(player));
        addPatch(hesporiPatch.set(PatchData.HESPORI).setPlayer(player));

        addPatch(canifisMushroom.set(PatchData.CANIFIS_MUSHROOM).setPlayer(player));

        addPatch(hardWoodTreeOne.set(PatchData.FOSSIL_ISLAND_HARDWOOD).setPlayer(player));
        addPatch(hardWoodTreeTwo.set(PatchData.FOSSIL_ISLAND_HARDWOOD1).setPlayer(player));
        addPatch(hardWoodTreeThree.set(PatchData.FOSSIL_ISLAND_HARDWOOD2).setPlayer(player));

        addPatch(belladonnaPatch.set(PatchData.DRAYNOR_MANOR_BELLADONNA).setPlayer(player));

        addPatch(crystalTreePatch.set(PatchData.PRIF_CRYSTAL_TREE).setPlayer(player));
        addPatch(prifFlower.set(PatchData.PRIF_FLOWER).setPlayer(player));
        addPatch(prifNorth.set(PatchData.PRIF_NORTH, prifFlower).setPlayer(player));
        addPatch(prifSouth.set(PatchData.PRIF_SOUTH, prifFlower).setPlayer(player));
        addPatch(prifCompostBin.set(PatchData.PRIF_COMPOST_BIN).setPlayer(player));

        addPatch(harmonyAllotment.set(PatchData.HARMONY_ALLOTMENT).setPlayer(player));
        addPatch(harmonyHerb.set(PatchData.HARMONY_HERB).setPlayer(player));

        addPatch(weissHerb.set(PatchData.WEISS_HERB).setPlayer(player));

        patches.forEach((id, patch) -> patch.onLoad()); // force ticks
        refresh();
        player.addEvent(event -> { // absolutely disgusting
            while (true) {
                if (player.debug) {
                    event.delay(1);
                } else {
                    event.delay(20);
                }
                refresh();
            }
        });
        player.addEvent(event -> {
            while (true) {
                if (player.debug) {
                    event.delay(1);
                } else {
                    event.delay(50);
                }
                tick();
            }
        }); // farming tick
    }


    public void sendPatch(GameObject gameObject) {
        Patch patch = getPatch(gameObject);
        if (patch == null)
            return;
        patch.send();
    }


    public void refresh() {
        PatchGroup prevGroup = visibleGroup;
        if (visibleGroup == null || (!player.getPosition().inBounds(visibleGroup.getBounds()))) {
            boolean found = false;
            for (PatchGroup pg : PatchGroup.values()) {
                if (player.getPosition().inBounds(pg.getBounds())) {
                    visibleGroup = pg;
                    found = true;
                    break;
                }
            }
            if (!found) {
                visibleGroup = null;
            }
        }
        if (visibleGroup != null && prevGroup != visibleGroup) {
            for (PatchData pd : visibleGroup.getPatches()) {
                getPatch(pd.getObjectId()).send();
            }
        }
    }

    private void tick() {
        patches.forEach((id, patch) -> {
            patch.tick();
            if (patch.needsUpdate()) {
                patch.update();
                patch.setNeedsUpdate(false);
            }
        });
    }

    public void addPatch(Patch patch) {
        patches.put(patch.getObjectId(), patch);
    }

    public void addPatches(Patch patch, int... objectIds) {
        for (int id : objectIds) {
            patches.put(id, patch);
        }
    }

    public boolean handleObject(GameObject obj, int option) {
        Patch patch = patches.get(obj.id);
        if (patch != null) {
            if (patch instanceof HesporiPatch) {
                ((HesporiPatch) patch).objectAction(obj, option);
                return true;
            }
            patch.objectAction(option);
            return true;
        }
        return false;
    }

    public boolean itemOnPatch(Item item, GameObject obj) {
        Patch patch = patches.get(obj.id);
        if (patch != null) {
            patch.handleItem(item);
            return true;
        }
        return false;
    }

    public Patch getPatch(PatchData patchData) {
        return patches.get(patchData.getObjectId());
    }

    public Patch getPatch(GameObject obj) {
        return patches.get(obj.id);
    }

    public Patch getPatch(int objId) {
        return patches.get(objId);
    }

    public boolean hasSecateurs() {
        return player.getEquipment().getId(Equipment.SLOT_WEAPON) == 7409 || player.getInventory().contains(7409, 1)
                || player.getInventory().contains(5329, 1);
    }

    public ToolStorage getStorage() {
        return storage;
    }


    public static final List<Crop> CROPS = new ArrayList<>();

    static {
        Collections.addAll(CROPS, HerbCrop.values());
        Collections.addAll(CROPS, AllotmentCrop.values());
        Collections.addAll(CROPS, FlowerCrop.values());
        Collections.addAll(CROPS, WoodTreeCrop.values());
        Collections.addAll(CROPS, FruitTreeCrop.values());
        Collections.addAll(CROPS, MushroomCrop.values());
        Collections.addAll(CROPS, BushCrop.values());
        Collections.addAll(CROPS, HopsCrop.values());
        Collections.addAll(CROPS, AnimaCrop.values());
        Collections.addAll(CROPS, RedwoodCrop.INSTANCE);
        Collections.addAll(CROPS, CelastrusCrop.INSTANCE);
        Collections.addAll(CROPS, CalquatCrop.INSTANCE);
        Collections.addAll(CROPS, CactusCrop.values());
        Collections.addAll(CROPS, SpiritTreeCrop.INSTANCE);
        Collections.addAll(CROPS, HardWoodTreeCrop.values());
        Collections.addAll(CROPS, BelladonnaCrop.values());
        Collections.addAll(CROPS, CrystalTreeCrop.values());
        Collections.addAll(CROPS, HesporiCrop.INSTANCE);
        ItemDef.cached.values().stream().filter(Objects::nonNull).forEach(def -> {
            def.produceOf = getCropForProduce(def.id);
            def.seedType = getCropForSeed(def.id);
            if (def.seedType == null)
                def.seedType = getCropForSapling(def.id);
        });

        CROPS.stream().filter(crop -> crop instanceof TreeCrop).map(crop -> (TreeCrop) crop).forEach(crop -> {
            ItemItemAction.register(5356, crop.getSeed(), (player, primary, secondary) -> {
                primary.setId(crop.getSeedling());
                secondary.remove(1);
                player.sendFilteredMessage("You plant the seed in the pot.");
            });
        });

        CROPS.stream().filter(crop -> crop instanceof TreeCrop).map(crop -> (TreeCrop) crop).forEach(crop -> {
            for (int i = 1; i < AllotmentPatch.WATERING_CAN_IDS.size(); i++) {
                final int charges = i;
                ItemItemAction.register(AllotmentPatch.WATERING_CAN_IDS.get(charges), crop.getSeedling(), (player, primary, secondary) -> {
                    primary.setId(AllotmentPatch.WATERING_CAN_IDS.get(charges - 1));
                    secondary.setId(crop.getSapling());
                    if (charges == 1)
                        player.sendMessage("Your watering can is now empty.");
                });
            };
        });
    }


    public static Crop getCropForSeed(int seed) {
        for (Crop crop: CROPS) {
            if (crop.getSeed() == seed)
                return crop;
        }
        return null;
    }

    public static Crop getCropForProduce(int produce) {
        for (Crop crop: CROPS) {
            if (crop.getProduceId() == produce)
                return crop;
        }
        return null;
    }

    public static TreeCrop getCropForSapling(int sapling) {
        for (Crop crop: CROPS) {
            if (crop instanceof TreeCrop && ((TreeCrop)crop).getSapling() == sapling)
                return (TreeCrop) crop;
        }
        return null;
    }


    public PatchGroup getVisibleGroup() {
        return visibleGroup;
    }

    public SpiritTreePatch getBrimhavenSpiritTree() {
        return brimhavenSpiritTree;
    }

    public SpiritTreePatch getEtceteriaSpiritTree() {
        return etceteriaSpiritTree;
    }

    public SpiritTreePatch getFarmingGuildSpiritTree() {
        return farmingGuildSpiritTree;
    }

    public SpiritTreePatch getPortSarimSpiritTree() {
        return portSarimSpiritTree;
    }

    public SpiritTreePatch getZeahSpiritTree() {
        return zeahSpiritTree;
    }
}


