package io.ruin.model.skills.farming.patch.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.BottomlessCompostBucket;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.BushCrop;
import io.ruin.model.skills.farming.crop.impl.VineCrop;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/3/2022
 */
public class VinePatch extends Patch {

    @Expose public boolean saltpetre;

    /*
     * 1 - saltpeter
     * 2 - plant
     * 9 - fully grown
     * 10 - pick
     * 15 - dead (all picked)
     */
    @Override
    public int getVarpbitValue() {
        if (plantedCrop == null) {
            if (saltpetre)
                return 1;
            return 0;
        } else if (getStage() >= getPlantedCrop().getTotalStages() + 1) {
            return getCropVarpbitValue() + 5 - getProduceCount();
        } else {
            return getCropVarpbitValue();
        }
    }

    @Override
    public int getCropVarpbitValue() {
        return getPlantedCrop().getContainerIndex() + getStage();
    }

    @Override
    public void interact() {
        if (plantedCrop == null) {
            if (!saltpetre)
                player.sendMessage("The patch needs to be treated with saltpetre.");
            else
                player.sendMessage("The patch is clear for new crops. It has been treated with saltpetre.");
        } else {
            cropInteract();
        }
    }

    @Override
    public void handleItem(Item item) {
        if (item.getDef().seedType != null) {
            if (plantedCrop != null) {
                player.sendMessage("There is already something growing in this patch.");
            } else if (canPlant(item.getDef().seedType)) {
                if (saltpetre) {
                    plant(item);
                } else {
                    player.sendMessage("The patch needs to be treated with saltpetre first.");
                }
            } else {
                player.sendMessage("You can't plant that seed on this type of patch.");
            }
        } else if (item.getId() == 13421) { // saltpetre
            treat(item);
        } else if (item.getId() == 952) { // spade, force clear
            clear();
        } else {
            player.sendMessage("Nothing interesting happens.");
        }
    }

    @Override
    public void treat(Item item) {
        if (item.getId() != 13421)
            return;
        if (plantedCrop != null) {
            player.sendMessage("You should clear the patch first.");
            return;
        }
        if (saltpetre) {
            player.sendMessage("This patch has already been treated.");
            return;
        }
        player.animate(2286);
        item.remove();
        player.getStats().addXp(StatType.Farming, 4, true);
        saltpetre = true;
        player.sendMessage("You treat the patch with saltpetre.");
        update();
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            if (getProduceCount() == 0) {
                clear();
            } else {
                pick();
            }
        }
    }

    @Override
    public void clear() {
        if (getPlantedCrop() == null) {
            player.sendMessage("This patch doesn't have anything planted on it.");
            return;
        }
        if (!player.getInventory().contains(952, 1)) {
            player.sendMessage("You will need a spade to clear this patch.");
            return;
        }
        player.startEvent(event -> {
            player.animate(831);
            event.delay(Random.get(2, 4));
            player.resetAnimation();
            reset(false);
            saltpetre = false;
            player.sendMessage("You clear the patch for new crops.");
        });
    }

    private void checkHealth() {
        rollPet();
        player.sendMessage("You examine the vines and find that they are in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, ((VineCrop) getPlantedCrop()).getCheckHealthXP(), true);
        advanceStage();
        update();
    }

    public void pick() {
        player.startEvent(event -> {
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.animate(2280);
                event.delay(1);
                player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                player.sendFilteredMessage("You pick a grape.");
                removeProduce();
                update();
                if (getProduceCount() == 0) {
                    return;
                }
                event.delay(2);
            }
        });
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof VineCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return true;
    }

    @Override
    public int calculateProduceAmount() {
        return 5;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public boolean isRaked() {
        return true;
    }

    @Override
    public String getPatchName() {
        return "a vine";
    }
}
