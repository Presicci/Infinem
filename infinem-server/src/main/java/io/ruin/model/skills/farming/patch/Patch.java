package io.ruin.model.skills.farming.patch;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.TabStats;
import io.ruin.model.item.Item;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.actions.impl.OldFirePit;
import io.ruin.model.skills.farming.BottomlessCompostBucket;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.farming.crop.impl.AnimaCrop;
import io.ruin.model.skills.farming.patch.impl.*;
import io.ruin.model.stat.StatType;

public abstract class Patch {

    protected Player player;

    protected Crop plantedCrop;
    protected PatchData data;
    private boolean update;

    @Expose private PatchStatus status;

    public Patch() {

    }

    public Patch set(PatchData data) {
        this.data = data;
        return this;
    }

    public Patch setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public abstract int getCropVarpbitValue();

    public abstract void cropInteract();

    public abstract boolean canPlant(Crop crop);

    public abstract boolean isDiseaseImmune();

    public abstract int calculateProduceAmount();


    /**
     * @return true if this patch is cured through plant cure, false if cured through pruning
     */
    public abstract boolean requiresCure();

    public void update() {
        if (player.getFarming().getVisibleGroup() != null && player.getFarming().getVisibleGroup().getPatches().contains(data))
            send();
    }

    public void send() {
        data.getConfig().set(player, getVarpbitValue());
    }

    public void interact() {
        if (getObjectId() == 33176 && !OldFirePit.FirePit.WEISS_FIRE.isBuilt(player)) {
            player.sendMessage("The crops can't grow in areas of extreme cold. Perhaps you should look into warming up the area around first.");
            return;
        }
        if (!isRaked()) {
            if (!player.getInventory().contains(5341, 1)) {
                player.sendMessage("You need a rake to weed a farming patch.");
                return;
            }
            player.animate(2273);
            player.startEvent(event -> {
                while (true) {
                    player.animate(2273);
                    player.privateSound(2442);
                    event.delay(4);
                    if (Random.get() < 0.8) {
                        getStatus().raked++;
                        player.getStats().addXp(StatType.Farming, 3, true);
                        player.getInventory().addOrDrop(6055, 1);
                        getStatus().lastWeedGrowth = System.currentTimeMillis();
                        send();
                    }
                    if (isRaked()) {
                        player.resetAnimation();
                        player.getTaskManager().doLookupByUUID(13, 1);  // Rake a Farming Patch
                        return;
                    }
                }
            });
        } else if (plantedCrop == null) {
            player.sendMessage("The patch is clear for new crops. " + (getStatus().compost > 0 ? "It has been treated with " + (getStatus().compost == 1 ? "regular" : "super") + " compost." : ""));
        } else if (isDead()) {
            clear();
        } else if (isDiseased()) {
            if (requiresCure()) {
                usePlantCure();
            } else {
                prune();
            }
        } else {
            cropInteract();
        }
    }

    void prune() {
        if (!player.getFarming().hasSecateurs()) {
            player.sendMessage("You'll need some secateurs to prune this tree.");
            return;
        }
        player.startEvent(event -> {
            player.animate(2274);
            event.delay(3);
            if (isDead() || getPlantedCrop() == null || !isDiseased())
                return;
            player.resetAnimation();
            player.sendMessage("You prune the " + (this instanceof BushPatch ? "bush" : "tree") + ", curing it of its disease.");
            setDiseaseStage(0);
            update();
        });
    }

    void usePlantCure() {
        if (!player.getInventory().contains(6036, 1)) {
            player.sendMessage("You don't have any plant cure to use.");
            return;
        }
        player.animate(2288);
        player.getInventory().remove(6036, 1);
        setDiseaseStage(0);
        update();
        player.sendMessage("The plant cure completely cures the disease. Your crops are now perfectly healthy.");
    }

    public void tick() {
        if (status == null) return;
        if (plantedCrop == null && getStatus().raked == 0)
            /* do nothing */
            return;
        if (plantedCrop == null && System.currentTimeMillis() - getStatus().lastWeedGrowth >= TimeUtils.getMinutesToMillis(4)) {
            growWeeds();
            return;
        }
        if (isNextStageReady()) {
            diseaseRoll();
            if (!isDead()) {
                advanceStage();
                if (hasGrowingKronos()
                        && !(this instanceof AnimaPatch)
                        && Random.get() < 0.05
                        && getPlantedCrop() != null
                        && getStage() != getPlantedCrop().getTotalStages()
                        && !isDiseased()) {
                    advanceStage();
                }
                setNeedsUpdate(true);
            }
        }
    }

    void growWeeds() {
        getStatus().raked -= 1;
        setNeedsUpdate(true);
        getStatus().lastWeedGrowth += TimeUtils.getMinutesToMillis(4);
    }


    protected boolean isNextStageReady() {
        if (getPlantedCrop() == null)
            return false;
        long stageTime = plantedCrop.getStageTime();
        int actualStage = (int) Math.floor(getTimeElapsed() / (double) (stageTime));
        return getStatus().stage < plantedCrop.getTotalStages() && actualStage > getStatus().stage && !isDead();
    }

    protected void diseaseRoll() {
        double diseaseChance = plantedCrop.getDiseaseChance(getStatus().compost) * (hasGrowingIasor() ? 0.2 : 1);
        if (!isFarmerProtected() && !isDiseaseImmune() && getStatus().diseaseStage == 0 && getStage() != (plantedCrop.getTotalStages() - 1) && Random.get() < diseaseChance) {
            getStatus().diseaseStage = 1;
            setNeedsUpdate(true);
        } else if (isDiseased()) {
            getStatus().diseaseStage = 2; // rip
            setNeedsUpdate(true);
        }
    }

    protected void advanceStage() {
        getStatus().stage++;
    }

    public boolean isDiseased() {
        return getStatus().diseaseStage == 1;
    }

    public boolean isDead() {
        return getStatus().diseaseStage == 2;
    }

    public boolean isFullyGrown() {
        return getPlantedCrop() != null && getStage() >= getPlantedCrop().getTotalStages();
    }

    public long getTimeElapsed() {
        return System.currentTimeMillis() - getTimePlanted();
    }

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

    public void reset(boolean weeds) {
        setPlantedCrop(null);
        getStatus().stage = 0;
        getStatus().diseaseStage = 0;
        getStatus().produceCount = 0;
        getStatus().timePlanted = 0;
        getStatus().compost = 0;
        getStatus().raked = weeds ? 0 : 3;
        getStatus().farmerProtected = false;
        update();
    }


    public void treat(Item item) {
        int compostType;
        if (item.getId() == 6032)
            compostType = 1;
        else if (item.getId() == 6034)
            compostType = 2;
        else if (item.getId() == 21483)
            compostType = 3;
        else if (item.getId() == 22997) {
            compostType = BottomlessCompostBucket.getType(item) + 1;
        } else
            throw new IllegalArgumentException("Invalid compost");

        if (!isRaked()) {
            player.sendMessage("You should clear the patch first.");
            return;
        }
        if (getCompost() >= compostType) {
            player.sendMessage("This patch has already been treated.");
            return;
        }
        setCompost(compostType);
        player.animate(2283);
        if (item.getId() == 22997)
            BottomlessCompostBucket.removeCharge(player, item);
        else if (!player.hasAttribute(AttributeKey.DISCARD_BUCKETS))
            item.setId(1925);
        else
            item.remove();
        player.getStats().addXp(StatType.Farming, 4, true);
        player.sendMessage("You treat the patch with " + (compostType == 2 ? "super" : compostType == 3 ? "ultra" : "") + " compost.");
    }

    public void plant(Item item) {
        Crop crop = item.getDef().seedType;
        if (!canPlant(crop))
            return;
        boolean sapling = false;
        if (crop instanceof TreeCrop) {
            if (item.getId() == crop.getSeed()) {
                player.sendMessage("Tree seeds must first be grown into saplings in a plant pot before being planted.");
                return;
            } else {
                sapling = true;
            }
        }
        String type = sapling ? "sapling" : "seed";
        if (!player.getStats().check(StatType.Farming, crop.getLevelReq(), "plant that " + type)) {
            return;
        }
        if (!player.getInventory().contains(sapling ? 5325 : 5343, 1)) {
            player.sendMessage("You need a " + (sapling ? "gardening trowel" : "seed dibber") + " to plant " + type + "s.");
            return;
        }
        if (!isRaked()) {
            player.sendMessage("You must clear the patch of any weeds before planting a " + type + ".");
            return;
        }
        final boolean tree = sapling;
        player.animate(sapling ? 830 : 2291);
        player.startEvent(event -> {
            event.delay(2);
            player.sendFilteredMessage("You plant the " + type + " in the patch.");
            if (!tree)
                player.getInventory().remove(crop.getSeed(), 1);
            else
                item.setId(5350);
            setPlantedCrop(crop);
            getStatus().produceCount = calculateProduceAmount();
            player.getStats().addXp(StatType.Farming, crop.getPlantXP(), true);
            getStatus().timePlanted = System.currentTimeMillis();
            send();
        });
    }

    public void handleItem(Item item) {
        if (item.getDef().seedType != null) {
            if (plantedCrop != null) {
                player.sendMessage("There is already something growing in this patch.");
            } else if (canPlant(item.getDef().seedType)) {
                plant(item);
            } else {
                player.sendMessage("You can't plant that seed on this type of patch.");
            }
        } else if (item.getId() == 6032 || item.getId() == 6034 || item.getId() == 21483 || item.getId() == 22997) { // compost / supercompost / ultracompost / bottomless
            treat(item);
        } else if (item.getId() == 6036) { // plant cure
            if (isDead()) {
                player.sendMessage("Plant cure can't bring plants back from the dead! You should clear the patch.");
            } else if (!isDiseased()) {
                player.sendMessage("This patch is perfectly healthy, there is no need use plant cure on it.");
            } else {
                if (requiresCure())
                    usePlantCure();
                else
                    player.sendMessage("You should try pruning the patch instead.");
            }
        } else if (item.getId() == 952) { // spade, force clear
            clear();
        } else if (item.getId() == 5350) { // plant pot
            if (!isRaked()) {
                player.sendMessage("You should clear the patch first.");
            } else {
                fillPlantPots();
            }
        } else {
            player.sendMessage("Nothing interesting happens.");
        }
    }

    public void fillPlantPots() {
        if (!player.getInventory().contains(5325, 1)) {
            player.sendMessage("You'll need a gardening trowel to fill plant pots.");
            return;
        }
        player.startEvent(event -> {
            player.animate(2272);
            event.delay(4);
            int amount = player.getInventory().remove(5350, 28);
            if (amount > 0)
                player.getInventory().add(5356, amount);
        });
    }

    public int getCompost() {
        return getStatus().compost;
    }

    public void setCompost(int compost) {
        getStatus().compost = compost;
    }

    public int getVarpbitValue() {
        if (!isRaked())
            return getRaked();
        else if (plantedCrop == null)
            return 3;
        else
            return getCropVarpbitValue();
    }

    public boolean isRaked() {
        return getStatus().raked >= 3;
    }

    public int getRaked() {
        return getStatus().raked;
    }

    public void setRaked(int raked) {
        getStatus().raked = raked;
    }

    public Crop getPlantedCrop() {
        return plantedCrop;
    }

    public void setPlantedCrop(Crop plantedCrop) {
        this.plantedCrop = plantedCrop;
        getStatus().plantedSeed = plantedCrop == null ? -1 : plantedCrop.getSeed();
    }

    public int getObjectId() {
        return data.getObjectId();
    }

    public int getDiseaseStage() {
        return getStatus().diseaseStage;
    }

    public void setDiseaseStage(int diseaseStage) {
        getStatus().diseaseStage = diseaseStage;
    }

    public int getStage() {
        return getStatus().stage;
    }

    public void setStage(int stage) {
        getStatus().stage = stage;
    }

    public long getTimePlanted() {
        return getStatus().timePlanted;
    }

    public void setTimePlanted(long timePlanted) {
        getStatus().timePlanted = timePlanted;
    }

    public int getProduceCount() {
        return getStatus().produceCount;
    }

    public void setProduceCount(int produceCount) {
        getStatus().produceCount = produceCount;
    }

    public boolean hasGrowingAttas() {
        Patch patch = player.getFarming().getPatch(PatchData.FARMING_GUILD_ANIMA.getObjectId());
        return patch.getPlantedCrop() == AnimaCrop.ATTAS && patch.getStage() != patch.getPlantedCrop().getTotalStages();
    }

    public boolean hasGrowingIasor() {
        Patch patch = player.getFarming().getPatch(PatchData.FARMING_GUILD_ANIMA.getObjectId());
        return patch.getPlantedCrop() == AnimaCrop.IASOR && patch.getStage() != patch.getPlantedCrop().getTotalStages();
    }

    public boolean hasGrowingKronos() {
        Patch patch = player.getFarming().getPatch(PatchData.FARMING_GUILD_ANIMA.getObjectId());
        return patch.getPlantedCrop() == AnimaCrop.KRONOS && patch.getStage() != patch.getPlantedCrop().getTotalStages();
    }

    public boolean removeProduce() {
        if ((player.getEquipment().getId(Equipment.SLOT_WEAPON) == 7409 || player.getInventory().contains(7409, 1)) && Random.get() < (0.1)) { // magic secateurs, save a "life"
            player.sendFilteredMessage("<col=076900>Your magic secateurs allow you to efficiently harvest the crop!");
            return false;
        }
        if (hasGrowingAttas() && Random.get() < 0.05) {
            player.sendFilteredMessage("<col=076900>Your Attas plant allow you to efficiently harvest the crop!");
            return false;
        }
        if (this instanceof AllotmentPatch || this instanceof HopsPatch
                || this instanceof HerbPatch) {
            switch (getCompost()) {
                case 3: // Ultra
                    if (Random.get() < 0.15) {  // 15% chance for free harvest
                        return false;
                    }
                    break;
                case 2: // Super
                    if (Random.get() < 0.10) {  // 10% chance for free harvest
                        return false;
                    }
                    break;
                case 1: // Compost
                    if (Random.get() < 0.05) {  // 5% chance for free harvest
                        return false;
                    }
                    break;
                case 0: // Nothing
                default:
                    break;
            }
        }
        if (--getStatus().produceCount <= 0) {
            getStatus().produceCount = 0;
            return true;
        }
        return false;
    }

    public boolean needsUpdate() {
        return update;
    }

    public void setNeedsUpdate(boolean update) {
        this.update = update;
    }


    public void onLoad() {
        if (status == null) return;
        if (getStatus().plantedSeed > 0) {
            Crop crop = ItemDefinition.get(getStatus().plantedSeed).seedType;
            if (crop != null && canPlant(crop))
                plantedCrop = crop;
            else
                reset(true); // invalid seed planted, reset patch
        }
        while (isNextStageReady()) { // important to simulate natural growth so no cycles are skipped
            tick();
        }
    }


    public void objectAction(int option) {
        if (option == 1)
            interact();
        else if (option == 2)
            inspect();
        else if (option == 3)
            clear();
        else if (option == 4)
            TabStats.openGuide(player, StatType.Farming, data.getGuideChildId());
    }

    protected void inspect() {
        String msg = "";
        msg += "This is " + getPatchName() + " patch. ";
        if ((this instanceof VinePatch) && ((VinePatch) this).saltpetre) {
            msg += "The soil has been treated with saltpetre. ";
        } else if (getCompost() == 0) {
            msg += "The soil has not been treated. ";
        } else {
            msg += "The soil has been treated with ";
            msg += getCompost() == 1 ? "compost" : (getCompost() == 3 ? "ultracompost" : "supercompost");
            msg += ". ";
        }
        if (getPlantedCrop() == null) {
            if (isRaked())
                msg += "The patch is clear for new crops. ";
            else
                msg += "The patch needs weeding.";
        } else {
            msg += "The patch has something growing in it.";
        }
        player.sendMessage(msg);
    }

    public abstract String getPatchName();

    public boolean isFarmerProtected() {
        return getStatus().farmerProtected;
    }

    public void setFarmerProtected(boolean farmerProtected) {
        getStatus().farmerProtected = farmerProtected;
    }

    public void rollPet() {
        int petOdds = plantedCrop.getPetOdds();
        if (petOdds <= 0) {
            return;
        }
        if (Random.rollDie(petOdds - (player.getStats().get(StatType.Farming).currentLevel * 25)))
            Pet.TANGLEROOT.unlock(player);
    }

    public boolean isFlowerProtected() {
        return false;
    }

    public boolean isWatered() {
        return false;
    }

    public PatchStatus getStatus() {
        if (status == null) status = new PatchStatus();
        return status;
    }
}
