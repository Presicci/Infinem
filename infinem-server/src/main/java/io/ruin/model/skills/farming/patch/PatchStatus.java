package io.ruin.model.skills.farming.patch;

import com.google.gson.annotations.Expose;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/8/2024
 */
public class PatchStatus {
    @Expose
    public int stage;
    @Expose
    public int compost;
    @Expose
    public int raked;
    @Expose
    public long timePlanted;
    @Expose
    public int diseaseStage;
    @Expose
    public int produceCount;
    @Expose
    public long lastWeedGrowth;
    @Expose
    public boolean farmerProtected;
    @Expose
    public int plantedSeed = -1;
}
