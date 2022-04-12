package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.CrystalTreeCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.woodcutting.Woodcutting;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/6/2021
 */
public class CrystalTreePatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        return getPlantedCrop().getContainerIndex() + stage;
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            chop();
        }
    }

    private void chop() {
        Woodcutting.chop(getPlantedCrop().getTreeType(), player, () -> false, (worldEvent) -> {
            player.getInventory().addOrDrop(23866, getCompost() == 2 ? Random.get(11, 16) : getCompost() == 3 ? Random.get(14, 16) : Random.get(8, 16));
            player.sendFilteredMessage("You chop down the tree and get some crystal shards.");
            reset(false);
        });
    }

    private void checkHealth() {
        rollPet();
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
        player.getTaskManager().doLookupByUUID(788, 1); // Check a grown Crystal Tree
        advanceStage();
        update();
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof CrystalTreeCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return true;
    }

    @Override
    public int calculateProduceAmount() {
        return 0; // we dont use this
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public CrystalTreeCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (CrystalTreeCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "a crystal tree";
    }
}