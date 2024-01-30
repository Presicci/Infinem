package io.ruin.model.item.actions.impl.jewellery;

public enum SkillsNecklace {

    SIX(11968, 6, 11970),
    FIVE(11970, 5, 11105),
    FOUR(11105, 4, 11107),
    THREE(11107, 3, 11109),
    TWO(11109, 2, 11111),
    ONE(11111, 1, 11113),
    UNCHARGED(11113, 0, -1);

    private final int id, charges, replacementId;

    SkillsNecklace(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("necklace", false,
                new JeweleryTeleports.Teleport("Fishing Guild", JewelleryTeleportBounds.FISHING_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Mining Guild", JewelleryTeleportBounds.MINING_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Crafting Guild", JewelleryTeleportBounds.CRAFTING_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Cooking Guild", JewelleryTeleportBounds.COOKING_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Woodcutting Guild", JewelleryTeleportBounds.WOODCUTTING_GUILD.getBounds()),
                new JeweleryTeleports.Teleport("Farming Guild", JewelleryTeleportBounds.FARMING_GUILD.getBounds())
        );
        for(SkillsNecklace necklace : values())
            teleports.register(necklace.id, necklace.charges, necklace.replacementId);
    }
}
