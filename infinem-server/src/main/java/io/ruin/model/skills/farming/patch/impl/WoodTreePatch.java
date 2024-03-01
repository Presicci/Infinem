package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.WoodTreeCrop;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.skills.woodcutting.Woodcutting;
import io.ruin.model.stat.StatType;

public class WoodTreePatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        return getDiseaseStage() << 6 | (getStage() + getPlantedCrop().getContainerIndex());
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
            if (getPlantedCrop() != null && getPlantedCrop().getRoots() != -1 && getStage() >= getPlantedCrop().getTotalStages()) {
                player.getInventory().addOrDrop(getPlantedCrop().getRoots(), getRootAmount());
                if (getPlantedCrop() == WoodTreeCrop.MAGIC)
                    player.getTaskManager().doLookupByUUID(181, 1); // Dig Up Some Magic Roots
            }
            player.sendMessage("You clear the patch for new crops.");
        });
    }

    public int getRootAmount() {
        if (getPlantedCrop() == null)
            return 0;
        return 1 + Math.max(Math.min(3, (player.getStats().get(StatType.Farming).currentLevel - getPlantedCrop().getLevelReq()) / 8), 0);
    }

    private void chop() {
        Woodcutting.chop(getPlantedCrop().getTreeType(), player, () -> false, (worldEvent) -> {
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
        rollPet();
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        FarmingContracts.completeFarmingContract(player, getPlantedCrop(), data);
        player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
        player.getTaskManager().doLookupByCategoryAndTrigger(TaskCategory.CHECKCROP, getPlantedCrop().name());
        advanceStage();
        update();
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof WoodTreeCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return data == PatchData.FALADOR_TREE && AreaReward.FALADOR_TREE_PATCH_DISEASE_FREE.hasReward(player);
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
    public WoodTreeCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (WoodTreeCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "a tree";
    }

}