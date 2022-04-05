package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskCategory {
    //  Levels
    FIRSTLEVEL, TOTALLEVEL, BASELEVEL, COMBATLEVEL,

    // General
    UNLOCKITEM, SKILLITEM,
    EQUIP, EQUIPSET,

    NPCKILL, SLAYERKILL, SUPERIORKILL, SLAYERTASK,

    UUIDLOOKUP,

    //Skilling
    PICKPOCKET, ROOFTOP, SLAYERPOINTS, BUILDROOM, RUNECRAFT, BURNLOG, IMPLING,
    LIGHTSOURCE, BURYBONE, COMPLETECLUE, CLUEUNIQUE, UNLOCKITEMSET,
    BOXTRAP, BIRDSNARE, NETTRAP, BUTTERFLY, IMPLINGBARE
    ;

    public final int[] uuids;

    TaskCategory(int... uuids) {
        this.uuids = uuids;
    }
}
