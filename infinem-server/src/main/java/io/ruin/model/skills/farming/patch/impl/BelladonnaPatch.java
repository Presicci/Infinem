package io.ruin.model.skills.farming.patch.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.BelladonnaCrop;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.stat.StatType;


/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/6/2021
 */
public class BelladonnaPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int value = getPlantedCrop().getContainerIndex() + getStage();
        if (isDead()) {
            value += 7;
        } else if (isDiseased()) {
            value += 4;
        }
        return value;
    }

    @Override
    public void cropInteract() {
        if (getStage() >= getPlantedCrop().getTotalStages()) {
            if (player.getEquipment().get(Equipment.SLOT_HANDS) == null) {
                player.hit(new Hit().fixedDamage(2));
                player.sendMessage("You really should wear gloves when harvesting this plant.");
                return;
            }
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
                    player.animate(2282);
                    event.delay(2);
                    player.getInventory().add(getPlantedCrop().getProduceId(), 1);
                    player.collectResource(new Item(getPlantedCrop().getProduceId(), 1));
                    player.getStats().addXp(StatType.Farming, getPlantedCrop().getHarvestXP(), true);
                    player.sendFilteredMessage("You harvest the patch.");
                    getPlantedCrop().getCounter().increment(player, 1);
                    removeProduce();
                    if (getProduceCount() == 0) {
                        player.sendMessage("You've harvested the patch completely.");
                        this.reset(false);
                        return;
                    }
                    event.delay(1);
                }
            });
        }
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof BelladonnaCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        int amount = Random.get(3, 5);
        if (getCompost() == 2) { // supercompost bonus
            amount += Random.get(0, 1);
        } else if (getCompost() == 3) { // ultracompost
            amount += Random.get(1, 3);
        }
        return amount;
    }

    @Override
    public boolean requiresCure() {
        return true;
    }

    @Override
    public BelladonnaCrop getPlantedCrop() {
        return super.getPlantedCrop() == null ? null : (BelladonnaCrop) super.getPlantedCrop();
    }

    @Override
    public String getPatchName() {
        return "a belladonna";
    }
}