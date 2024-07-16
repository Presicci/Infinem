package io.ruin.model.skills;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import lombok.AllArgsConstructor;

public enum Tool {
    SAW(new InventoryTool(Items.SAW), new InventoryTool(Items.CRYSTAL_SAW), new EquipmentTool(24880)),
    HAMMER(new InventoryTool(Items.HAMMER), new EquipmentTool(25644))
    ;

    private final AbstractTool[] tools;

    Tool(AbstractTool... tools) {
        this.tools = tools;
    }

    public boolean hasTool(Player player) {
        for (AbstractTool tool : tools) {
            if (tool.hasTool(player)) return true;
        }
        return false;
    }

    @AllArgsConstructor
    private abstract static class AbstractTool {
        protected final int toolId;

        public abstract boolean hasTool(Player player);
    }

    private static class InventoryTool extends AbstractTool {
        private InventoryTool(int toolId) {
            super(toolId);
        }

        @Override
        public boolean hasTool(Player player) {
            return player.getInventory().hasId(toolId);
        }
    }

    private static class EquipmentTool extends AbstractTool {
        private EquipmentTool(int toolId) {
            super(toolId);
        }

        @Override
        public boolean hasTool(Player player) {
            return player.getInventory().hasId(toolId) || player.getEquipment().hasId(toolId);
        }
    }

    public static final int KNIFE = 946;

    public static final int FEATHER = 314;

    public static final int CHISEL = 1755;

    public static final int NEEDLE = 1733;

    public static final int THREAD = 1734;

    public static final int BALL_OF_WOOL = 1759;

    public static final int VIAL_OF_WATER = 227;

    public static final int TINDER_BOX = 590;

    public static final int PESTLE_AND_MORTAR = 233;

    public static final int SHEARS = 1735;

    public static final int EMPTY_BUCKET = 1925;

    public static final int SPADE = 952;

    public static final int RAKE = 5341;

    public static final int SEED_DIBBER = 5343;

    public static final int CRYSTAL_SAW = 9625;

    public static final int GLASSBLOWING_PIPE = 1785;

    public static final int TROWEL = 676;

    public static final int WATERING_CAN = 5331;
}
