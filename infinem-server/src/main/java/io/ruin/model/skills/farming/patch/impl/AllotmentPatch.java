package io.ruin.model.skills.farming.patch.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.tasksystem.areas.AreaShopItem;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.AllotmentCrop;
import io.ruin.model.skills.farming.crop.impl.FlowerCrop;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class AllotmentPatch extends Patch {

    private FlowerPatch associatedFlowerPatch;
    @Expose
    private boolean watered;

    public static final List<Integer> WATERING_CAN_IDS = Arrays.asList(5331, 5333, 5334, 5335, 5336, 5337, 5338, 5339, 5340);

    public Patch set(PatchData data, FlowerPatch associatedFlowerPatch) {
        super.set(data);
        this.associatedFlowerPatch = associatedFlowerPatch;
        return this;
    }

    private void water(Item item, int index) {
        player.addEvent(event -> {
            player.animate(2293);
            event.delay(2);
            watered = true;
            update();
            item.setId(WATERING_CAN_IDS.get(index - 1));
            if (index == 1)
                player.sendMessage("Your watering can is now empty.");
            player.getStats().addXp(StatType.Farming, 1, false);

        });
    }

    @Override
    public void reset(boolean weeds) {
        watered = false;
        super.reset(weeds);
    }

    @Override
    public void handleItem(Item item) { // we only handle an item if it's a watering can, if not just let the superclass handle it
        int can = WATERING_CAN_IDS.indexOf(item.getId());
        if (can != -1) {
            if (can == 0) {
                player.sendMessage("Your watering can has no water in it.");
            } else if (getPlantedCrop() == null) {
                player.sendMessage("There is nothing to water on this patch.");
            } else if (isDiseased() || isDead()) {
                player.sendMessage("Water won't cure your crops.");
            } else if (getStage() >= getPlantedCrop().getTotalStages()) {
                player.sendMessage("Your crops are already fully grown.");
            } else {
                water(item, can);
            }
        } else {
            super.handleItem(item);
        }
    }

    @Override
    protected void advanceStage() {
        super.advanceStage();
        watered = false;
    }

    private static final int[] snapeGrassDisease = {0, 196, 197, 198, 202, 203, 204};
    private static final int[] snapeGrassDead = {0, 193, 194, 195, 209, 210, 211};

    @Override
    public int getCropVarpbitValue() {
        int value = getPlantedCrop().getContainerIndex() + getStage();
        if (getPlantedCrop() == AllotmentCrop.SNAPE_GRASS && getStage() == getPlantedCrop().getTotalStages()) {
            value = 138;
        }
        if (getPlantedCrop() == AllotmentCrop.SNAPE_GRASS) {
            if (watered)
                value -= 65;
            else if (getDiseaseStage() == 1)
                value = snapeGrassDisease[getStatus().stage];
            else if (getDiseaseStage() == 2)
                value = snapeGrassDead[getStatus().stage];
        } else {
            if (watered)
                value |= 1 << 6;
            else if (getDiseaseStage() > 0)
                value |= (getDiseaseStage() + 1) << 6;
        }
        return value;
    }

    @Override
    public void cropInteract() {
        if (getStage() >= getPlantedCrop().getTotalStages()) {
            player.startEvent(event -> {
                while (true) {
                    if (player.getInventory().getFreeSlots() == 0) {
                        player.sendMessage("Not enough space in your inventory.");
                        return;
                    }
                    if (!player.getInventory().contains(952, 1)) {
                        player.sendMessage("You'll need a spade to harvest your crops.");
                        return;
                    }
                    player.animate(830);
                    event.delay(2);
                    player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                    player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                    player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                    player.sendFilteredMessage("You harvest the patch.");
                    player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.HARVESTALLOT, ItemDefinition.get(getPlantedCrop().getProduceId()).name);
                    getPlantedCrop().getCounter().increment(player, 1);
                    removeProduce();
                    if (getProduceCount() == 0) {
                        rollPetAndHesporiSeed();
                        player.sendMessage("You've harvested the patch completely.");
                        FarmingContracts.completeFarmingContract(player, getPlantedCrop(), data);
                        AllotmentPatch.this.reset(false);
                        return;
                    }
                    event.delay(1);
                }
            });
        }
    }

    @Override
    public void plant(Item item) {
        Crop crop = item.getDef().seedType;
        if (!canPlant(crop))
            return;
        if (!player.getStats().check(StatType.Farming, crop.getLevelReq(), "plant that seed")) {
            return;
        }
        if (!isRaked()) {
            player.sendMessage("You must clear the patch of any weeds before planting a seed.");
            return;
        }
        if (!player.getInventory().contains(crop.getSeed(), 3)) {
            player.sendMessage("You'll need at least 3 seeds to plant in this patch.");
            return;
        }
        if (!player.getInventory().contains(5343, 1)) {
            player.sendMessage("You need a seed dibber to plant seeds.");
            return;
        }
        player.startEvent(event -> {
            player.animate(2291);
            event.delay(2);
            player.getInventory().remove(crop.getSeed(), 3);
            setPlantedCrop(crop);
            setProduceCount(calculateProduceAmount());
            player.getStats().addXp(StatType.Farming, crop.getPlantXP(), true);
            setTimePlanted(System.currentTimeMillis());
            player.sendFilteredMessage("You plant the seed in the patch.");
            player.getTaskManager().doLookupByUUID(14, 1);  // Plant Seeds in an Allotment Patch
            player.getTaskManager().doLookupByCategory(TaskCategory.PLANT_SEED, 1, true);
            send();
        });
    }

    @Override
    public boolean removeProduce() {
        if (AreaShopItem.MAGIC_SECATEURS.hasUnlocked(player) && (player.getEquipment().getId(Equipment.SLOT_WEAPON) == 7409 || player.getInventory().contains(7409, 1)) && Random.get() < (0.1)) { // magic secateurs, save a "life"
            player.sendFilteredMessage("<col=076900>Your magic secateurs allow you to efficiently harvest the crop!");
            return false;
        }
        if (hasGrowingAttas() && Random.get() < 0.05) {
            player.sendFilteredMessage("<col=076900>Your Attas plant allow you to efficiently harvest the crop!");
            return false;
        }
        double saveChance = getPlantedCrop().getSaveProductChance(player);
        if (Random.get(100) < saveChance) return false;
        if (--getStatus().produceCount <= 0) {
            getStatus().produceCount = 0;
            return true;
        }
        return false;
    }


    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof AllotmentCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        if (watered)
            return true;
        return isFlowerProtected();
    }

    @Override
    public boolean isFlowerProtected() {
        if (associatedFlowerPatch == null || associatedFlowerPatch.getDiseaseStage() > 0)
            return false;
        FlowerCrop flower = associatedFlowerPatch.getPlantedCrop();
        if (flower == null || !associatedFlowerPatch.isFullyGrown())
            return false;
        return getPlantedCrop().getProtectionFlower() == flower || flower == FlowerCrop.WHITE_LILY;
    }

    @Override
    public boolean isWatered() {
        return watered;
    }

    @Override
    public int calculateProduceAmount() {
        return 3 + getCompost();
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public AllotmentCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (AllotmentCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "an allotment";
    }


}
