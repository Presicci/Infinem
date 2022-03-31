package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.SeaweedCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/31/2022
 */
public class SeaweedPatch extends Patch {
    /*
     * 8 - fully grown
     * 9 - missing one
     * 10 - missing two
     * 11-13 - diseased
     * 14-16 - dead
     */
    @Override
    public int getCropVarpbitValue() {
        if (isDiseased())
            return getStage() + 10;
        if (isDead())
            return getStage() + 13;
        if (isFullyGrown())
            return getPlantedCrop().getContainerIndex() + getStage() + Math.min(3, 3 - getProduceCount());
        return getPlantedCrop().getContainerIndex() + getStage();
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            pick();
        }
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
                player.sendFilteredMessage("You pick a blade of seaweed.");
                removeProduce();
                update();
                if (getProduceCount() == 0) {
                    rollPet();
                    player.sendMessage("You've harvested the patch completely.");
                    SeaweedPatch.this.reset(false);
                    return;
                }
                event.delay(2);
            }
        });
    }

    @Override
    public boolean removeProduce() {
        if (hasGrowingAttas() && Random.get() < 0.05) {
            player.sendFilteredMessage("<col=076900>Your Attas plant allow you to efficiently harvest the crop!");
            return false;
        }
        /*
         * The chance to not consume a life upon harvest is 64.1% at level 23 and 82% at level 99, using linear interpolation for other levels.
         */
        double initialSaveChance = 64.1;
        int saveChance = (int) (initialSaveChance + (0.236 * (player.getStats().get(StatType.Farming).currentLevel - 23)));
        if (Random.rollPercent(saveChance))
            return false;
        if (--produceCount <= 0) {
            produceCount = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof SeaweedCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        switch (getCompost()) {
            case 3:
                return 6;
            case 2:
                return 5;
            case 1:
                return 4;
            default:
                return 3;
        }
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public String getPatchName() {
        return "a seaweed";
    }
}
