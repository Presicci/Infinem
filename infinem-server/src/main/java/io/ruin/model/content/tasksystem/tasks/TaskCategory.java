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

    NPCKILL, SLAYERKILL, SUPERIORKILL,

    UUIDLOOKUP,

    //Skilling
    PICKPOCKET, ROOFTOP, SLAYERPOINTS, BUILDROOM, RUNECRAFT, BURNLOG, IMPLING,
    LIGHTSOURCE
    ;

    public final int[] uuids;

    TaskCategory(int... uuids) {
        this.uuids = uuids;
    }
}
