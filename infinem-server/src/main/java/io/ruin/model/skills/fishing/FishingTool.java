package io.ruin.model.skills.fishing;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FishingTool {

    SMALL_FISHING_NET(303, 621),
    BIG_FISHING_NET(305, 620),
    FISHING_ROD(307, 313, 622, 623),
    FLY_FISHING_ROD(309, 314, 622, 623),
    BARBARIAN_ROD(11323, 314, 622, 623),
    LOBSTER_POT(301, 619),
    HARPOON(311, 618),
    OILY_FISHING_ROD(1585, 313, 622, 623),
    DARK_CRAB_POT(301, 11940, 619),
    ANGLER_ROD(307, 13431, 622, 623),
    CRYSTAL_HARPOON(23762, 8336, HARPOON),
    INFERNAL_HARPOON(21031, 7402, HARPOON),
    DRAGON_HARPOON(21028, 7401, HARPOON),
    BARB_TAIL_HARPOON(10129, 5108, HARPOON),
    KARAMBWAN_VESSEL(3157, 3150, 1193, 1193),
    PEARL_ROD(22846, 313, 8191, 8188, FISHING_ROD),
    PEARL_FLY_ROD(22844, 314, 8192, 8189, FLY_FISHING_ROD),
    PEARL_OILY_ROD(23122, 313, 8191, 8188, OILY_FISHING_ROD), // TODO get oily anims
    PEARL_BARBARIA_ROD(22842, 314, 8193, 8190, BARBARIAN_ROD);

    public final int id;
    public final int startAnimationId, loopAnimationId;
    public final int secondaryId;
    public final String primaryName, secondaryName;
    private final FishingTool baseTool;

    FishingTool(int id, int animationId, FishingTool baseTool) {
        this(id, -1, animationId, animationId, baseTool);
    }

    FishingTool(int id, int animationId) {
        this(id, -1, animationId, animationId);
    }

    FishingTool(int id, int secondaryId, int animationId) {
        this(id, secondaryId, animationId, animationId);
    }

    FishingTool(int id, int secondaryId, int startAnimationId, int loopAnimationId) {
        this(id, secondaryId, startAnimationId, loopAnimationId, null);
    }

    FishingTool(int id, int secondaryId, int startAnimationId, int loopAnimationId, FishingTool baseTool) {
        this.id = id;
        this.secondaryId = secondaryId;
        this.startAnimationId = startAnimationId;
        this.loopAnimationId = loopAnimationId;
        this.primaryName = ItemDef.get(id).name.toLowerCase();
        this.secondaryName = secondaryId == -1 ? null : ItemDef.get(secondaryId).name.toLowerCase();
        this.baseTool = baseTool;
    }

    private static final Map<FishingTool, List<FishingTool>> ALTERNATIVE_TOOLS = new HashMap<>();

    static {
        // Create a mapping of tool bases to alternatives, ex. HARPOON base with DRAGON_HARPOON alt
        for (FishingTool tool : FishingTool.values()) {
            FishingTool base = tool.baseTool;
            if (base == null)
                continue;
            if (!ALTERNATIVE_TOOLS.containsKey(base))
                ALTERNATIVE_TOOLS.put(base, new ArrayList<>());
            List<FishingTool> list = ALTERNATIVE_TOOLS.get(base);
            list.add(tool);
            ALTERNATIVE_TOOLS.put(base, list);
        }
    }

    public static FishingTool getAlternative(Player player, FishingTool tool) {
        if (!ALTERNATIVE_TOOLS.containsKey(tool))
            return tool;
        List<FishingTool> tools = ALTERNATIVE_TOOLS.get(tool);
        if (tools.isEmpty())
            return tool;
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        for (FishingTool t : tools) {
            if ((weapon != null && weapon.getId() == t.id) || player.getInventory().contains(t.id, 1, false, true))
                return t;
        }
        return tool;
    }
}
