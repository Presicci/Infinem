package io.ruin.model.skills.farming.patch.impl;


import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.tasksystem.areas.AreaShopItem;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.skillcapes.FarmingSkillCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.AllotmentCrop;
import io.ruin.model.skills.farming.crop.impl.HerbCrop;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.stat.StatType;

public class HerbPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int index;
        if (isDiseased()) {
            index = (getStage() - 1) | (1 << 7);
        } else if (isDead()) {
            index = 170 + Math.min(getStage() - 1, 2);
        } else {
            index = getPlantedCrop().getContainerIndex() + getStage();
        }
        return index;
    }

    @Override
    public void cropInteract() {
        if (isDead()) {
            clear();
            return;
        }
        if (getProduceCount() == 0) {
            reset(false);
            return;
        }
        player.startEvent(event -> {
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                if (getProduceCount() == 0) {
                    rollPetAndHesporiSeed();
                    player.sendMessage("You've picked all the herbs from this patch.");
                    FarmingContracts.completeFarmingContract(player, getPlantedCrop(), data);
                    reset(false);
                    return;
                }
                player.animate(2282);
                event.delay(2);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                String itemName = ItemDefinition.get(getPlantedCrop().getProduceId()).name;
                player.sendFilteredMessage("You pick a " + itemName + ".");
                player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.HARVESTHERB, itemName);
                getPlantedCrop().getCounter().increment(player, 1);
                if (getObjectId() == PatchData.TROLLHEIM_HERB.getObjectId())
                    player.getTaskManager().doLookupByUUID(445);    // Harvest Any Herb at the Troll Stronghold
                removeProduce();
                event.delay(1);
            }
        });
    }

    @Override
    public boolean removeProduce() {
        if (AreaShopItem.MAGIC_SECATEURS.hasUnlocked(player) && (player.getEquipment().getId(Equipment.SLOT_WEAPON) == 7409 || player.getInventory().contains(7409, 1)) && Random.get() < (0.1)) { // magic secateurs, save a "life"
            player.sendFilteredMessage("<col=076900>Your magic secateurs allow you to efficiently harvest the crop!");
            return false;
        }
        if (FarmingSkillCape.wearingFarmingCape(player) && Random.get() < 0.05) {
            player.sendFilteredMessage("<col=076900>Your farming cape allow you to efficiently harvest the crop!");
            return false;
        }
        if (hasGrowingAttas() && Random.get() < 0.05) {
            player.sendFilteredMessage("<col=076900>Your Attas plant allow you to efficiently harvest the crop!");
            return false;
        }
        if (data == PatchData.CATHERBY_HERB && AreaReward.CATHERBY_HERB_LIFE.hasReward(player) && Random.get() < 0.05) {
            player.sendFilteredMessage("<col=076900>Your expertise in the Kandarin region allows you to efficiently harvest the crop!");
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
        return crop instanceof HerbCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return getObjectId() == 18816 // trollheim patch
                || getObjectId() == 33176; // weiss patch
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
    public String getPatchName() {
        return "a herb";
    }

    @Override
    public HerbCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (HerbCrop) super.getPlantedCrop();
    }
}

