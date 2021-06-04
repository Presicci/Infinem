package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.RedwoodCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.woodcutting.Tree;
import io.ruin.model.skills.woodcutting.Woodcutting;
import io.ruin.model.skills.woodcutting.WoodcuttingGuild;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
public class RedwoodPatch extends Patch {

    // 37 check health
    @Override
    public int getCropVarpbitValue() {
        int val = getPlantedCrop().getContainerIndex() + getStage();
        if (isDiseased()) {
            return val + 11;
        }
        if (isDead()) {
            return val + 20;
        }
        if (getStage() == getPlantedCrop().getTotalStages()) {
            val = 37;
        }
        if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            val = 18;
        }
        int harvest = getHarvested();
        if (harvest > 0) {
            val = 40 + harvest;
        }
        return val;
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            checkHealth();
        } else if (getStage() == getPlantedCrop().getTotalStages() + 1) {
            if (player.getPosition().getZ() != 1) {
                player.sendMessage("You can't clear this by yourself, talk to Alexandra.");
            } else {
                chop();
            }
        }
    }

    @Override
    public boolean isFullyGrown() {
        if (getPlantedCrop() == null) {
            return false;
        }
        int stage = getCropVarpbitValue();
        return stage == 37 || stage == 18 || stage > 40;
    }

    @Override
    public void clear() {
        if (getPlantedCrop() == null) {
            player.sendMessage("This patch doesn't have anything planted on it.");
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
        Woodcutting.chop(Tree.REDWOOD, player, () -> false, (worldEvent) -> {
            player.sendFilteredMessage("You chop down all the bark on this side of the tree.");
            int direction = -1;
            if (player.getPosition().getX() == 1225) {
                westHarvested = true;
                direction = 0;
            } else if (player.getPosition().getX() == 1232) {
                eastHarvested = true;
                direction = 1;
            } else if (player.getPosition().getY() == 3758) {
                northHarvested = true;
                direction = 2;
            } else if (player.getPosition().getY() == 3751) {
                southHarvested = true;
                direction = 3;
            }
            update();
            worldEvent.delay(Tree.REDWOOD.respawnTime);
            if (getPlantedCrop() == null || getStage() < getPlantedCrop().getTotalStages())
                return; // player cleared tree
            switch (direction) {
                case 0:
                    westHarvested = false;
                    break;
                case 1:
                    eastHarvested = false;
                    break;
                case 2:
                    northHarvested = false;
                    break;
                case 3:
                    southHarvested = false;
                    break;
            }
            update();
        });
    }

    private void checkHealth() {
        player.sendMessage("You examine the tree and find that it is in perfect health.");
        getPlantedCrop().getCounter().increment(player, 1);
        player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
        northHarvested = false;
        westHarvested = false;
        eastHarvested = false;
        southHarvested = false;
        advanceStage();
        update();
    }

    private boolean northHarvested, westHarvested, eastHarvested, southHarvested;

    private int getHarvested() {
        return (northHarvested ? 1 : 0) + (westHarvested ? 2 : 0) + (eastHarvested ? 4 : 0) + (southHarvested ? 8 : 0);
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof RedwoodCrop;
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
    public String getPatchName() {
        return "a redwood tree";
    }

    static {
        /**
         * Redwood tree entrance to upper level
         */
        ObjectAction.register(34477, 1224, 3755, 0, "climb-up", (player, obj) -> WoodcuttingGuild.ropeLadder(player, obj, 1, 828, 1));
        ObjectAction.register(34477, 1233, 3755, 0, "climb-up", (player, obj) -> WoodcuttingGuild.ropeLadder(player, obj, -1, 828, 1));

        ObjectAction.register(34478, 1233, 3755, 1, "climb-down", (player, obj) -> WoodcuttingGuild.ropeLadder(player, obj, 0, 827, 0));
        ObjectAction.register(34478, 1224, 3755, 1, "climb-down", (player, obj) -> WoodcuttingGuild.ropeLadder(player, obj, 0, 827, 0));
    }
}
