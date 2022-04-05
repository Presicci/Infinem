package io.ruin.model.content.tasksystem.tasks;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/17/2021
 */
public enum TaskCategory {
    //  Levels
    FIRSTLEVEL, TOTALLEVEL, BASELEVEL, COMBATLEVEL,

    UNLOCKITEM, SKILLITEM,

    NPCKILL, SLAYERKILL, SUPERIORKILL,

    UUIDLOOKUP,

    //  Skilling
    TOOL,

    //  Items
    EQUIP,

    //  Thieving
    PICKPOCKET, STALLS,

    //  Farming
    RAKE, PLANTALLOTMENT(14), PROTECTCROPS(15),

    //  Woodcutting
    WOODCUTTING, BIRDNEST,

    // Cooking
    COOKING, BURN,

    // Mining
    MINING, MINEGEM,

    // Fletching
    FLETCHING,

    // Slayer
    RECEIVESLAYER, SLAYERPOINTS, CHECKSLAYER(31),

    // Crafting
    SPINNING, CUTGEM, CRAFTING,

    // Fishing
    FISHING,

    // Smithing
    SMELT, SMITH,

    // Construction
    PURCHASEHOUSE(41), BUILDROOM, SAWMILL,

    // Runecrafting
    ESSENCEMINE(44), CRAFTRUNE, LOCATETALISMAN,

    // Firemaking
    FIREMAKING, LIGHT,

    // Hunter
    IMPLING, BIRDSNARE
    ;

    public final int[] uuids;

    TaskCategory(int... uuids) {
        this.uuids = uuids;
    }
}
