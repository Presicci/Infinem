package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskCategory {
    //  Levels
    FIRSTLEVEL, TOTALLEVEL, BASELEVEL, COMBATLEVEL,

    // General
    UNLOCKITEM, UNLOCKITEMREGEX,
    SKILLITEM,
    EQUIP, EQUIPSET, DROP,

    NPCKILL, SLAYERKILL, SUPERIORKILL, SLAYERTASK,

    UUIDLOOKUP,

    //Skilling
    PICKPOCKET, ROOFTOP, SLAYERPOINTS, BUILDROOM, RUNECRAFT, BURNLOG, IMPLING,
    LIGHTSOURCE, BURYBONE, COMPLETECLUE, CLUEUNIQUE, UNLOCKITEMSET,
    BOXTRAP, BIRDSNARE, NETTRAP, BUTTERFLY, IMPLINGBARE, PICKPOCKETLOOT,
    IMPLINGPURO, THIEVECHEST, FIGHTCAVES, INFERNO, MOVEHOUSE, RUNECRAFTCOMBO,
    PRAYERRESTORED, COOKITEM, SLAYERTASKCOMPL, BUILDFURNITURE, SKILL99, SKILLMASTER,
    CHECKCROP, CHECKFRUIT, HARVESTHERB, CHECKCACTUS, HARVESTALLOT, BIRDHOUSEBUILD,
    BIRDHOUSEDIS, FISHCATCH, MINE, POTION, FARMINGCONTRACT, STALLLOOT, CHOPLOG,
    FLETCHLOG, FLETCH_AMMO, STRING_BOW,
    SPINNING_WHEEL, CUT_GEM, CRAFT_ARMOR, STRING_AMMY, BLOW_GLASS, MOULD_JEWL,
    SMELT_BAR, SMITH_ITEM,
    BESTIARY, BESTIARY_ENTRIES, BESTIARY_BOSS
    ;

    public final int[] uuids;

    TaskCategory(int... uuids) {
        this.uuids = uuids;
    }
}
