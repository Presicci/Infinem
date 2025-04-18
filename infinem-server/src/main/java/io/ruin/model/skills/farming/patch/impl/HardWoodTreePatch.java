package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.HardWoodTreeCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.woodcutting.Woodcutting;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/6/2021
 */
public class HardWoodTreePatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int vb = getPlantedCrop().getContainerIndex() + getStatus().stage;
        if (isDiseased())
            return vb + 9;
        else if (isDead())
            return vb + 15;
        return vb;
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            chop();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 2) {
            clear();
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
            player.sendMessage("You clear the patch for new crops.");
        });
    }

    private void chop() {
        Woodcutting.chop(getPlantedCrop().getTreeType(), player, null, () -> false, (worldEvent) -> {
            advanceStage();
            player.sendFilteredMessage("You chop down the tree.");
            update();
            worldEvent.delay(getPlantedCrop().getTreeType().respawnTime);
            if (getPlantedCrop() == null || getStage() < getPlantedCrop().getTotalStages())
                return; // player cleared tree
            setStage(getPlantedCrop().getTotalStages() + 1);
            update();
        });
    }

    private void checkHealth() {
        rollPetAndHesporiSeed();
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.CHECKCROP, getPlantedCrop().name());
        advanceStage();
        update();
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof HardWoodTreeCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
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
    public HardWoodTreeCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (HardWoodTreeCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "a hardwood tree";
    }

}