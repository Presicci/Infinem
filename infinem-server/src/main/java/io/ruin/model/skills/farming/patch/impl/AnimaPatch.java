package io.ruin.model.skills.farming.patch.impl;

import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.AnimaCrop;
import io.ruin.model.skills.farming.patch.Patch;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
public class AnimaPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        return getPlantedCrop().getContainerIndex() + getStage();
    }

    @Override
    public void cropInteract() {
        if (getStage() == getPlantedCrop().getTotalStages()) {
            clear();
        }
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof AnimaCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return false;
    }

    @Override
    public int calculateProduceAmount() {
        return 4;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public String getPatchName() {
        return "an anima";
    }
}
