package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.inter.handlers.TabStats;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.SpiritTree;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.CelastrusCrop;
import io.ruin.model.skills.farming.crop.impl.SpiritTreeCrop;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.woodcutting.Tree;
import io.ruin.model.skills.woodcutting.Woodcutting;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
public class CelastrusPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int val = getPlantedCrop().getContainerIndex() + getStage();
        if (isDiseased()) {
            return val + 10;
        }
        if (isDead()) {
            return val + 15;
        }
        return val;
    }

    //14, 15, 16, 17 chop
    private void checkHealth() {
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        FarmingContracts.completeFarmingContract(player, getPlantedCrop(), data);
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, ((CelastrusCrop)getPlantedCrop()).getCheckHealthXP(), true);
        advanceStage();
        update();
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1
                || getStage() == getPlantedCrop().getTotalStages() + 2
                || getStage() == getPlantedCrop().getTotalStages() + 3) {
            player.startEvent(event -> {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.animate(2282);
                event.delay(2);
                player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                int maxBark = getCompost() == 3 ? 3 : 2;
                player.getInventory().add(getPlantedCrop().getProduceId(), Random.get(1, maxBark));
                player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                player.sendFilteredMessage("You rip some bark from the tree.");
                advanceStage();
                update();
            });
        } else if (getStage() == getPlantedCrop().getTotalStages() + 4) {
            chop();
        }
    }

    private void chop() {
        Woodcutting.chop(Tree.CELASTRUS, player, () -> false, (worldEvent) -> {
            advanceStage();
            player.sendFilteredMessage("You chop down the tree.");
            player.resetAnimation();
            reset(false);
        });
    }

    @Override
    public void objectAction(int option) {
        if (option == 1)
            interact();
        else if (option == 2 && getStage() >= getPlantedCrop().getTotalStages())
            SpiritTree.open(player);
        else if (option == 3)
            inspect();
        else if (option == 4)
            TabStats.openGuide(player, StatType.Farming, 7);
    }


    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 3;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof CelastrusCrop;
    }

    @Override
    public void plant(Item item) {
        super.plant(item);
    }

    @Override
    public String getPatchName() {
        return "a celastrus tree";
    }
}
