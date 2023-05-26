package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;

public enum ItemCombining {
    /**
     * Upgrade kits
     */
    ODIUM_WARD_UPGRADE(12802, 11926, 12807, true),
    MALEDICTION_WARD_UPGRADE(12802, 11924, 12806, true),
    GRANITE_CLAMP(12849, 4153, 12848, true),
    DRAGON_PICKAXE_UPGRADE(11920, 12800, 12797, true),
    STEAM_BATTLESTAFF_UPGRADE(11787, 12798, 12795, true),
    MYSTIC_STEAM_BATTLESTAFF_UPGRADE(11789, 12798, 12796, true),
    /**
     * Ornamental
     */
    AMULET_OF_FURY_OR(6585, 12526, 12436, true),
    AMULET_OF_TORTURE_OR(19553, 20062, 20366, true),
    OCCULT_NECKLACE_OR(12002, 20065, 19720, true),
    ARMADYL_GODSWORD_OR(11802, 20068, 20368, true),
    BANDOS_GODSWORD_OR(11804, 20071, 20370, true),
    SARADOMIN_GODSWORD_OR(11806, 20074, 20372, true),
    ZAMORAK_GODSWORD_OR(11808, 20077, 20374, true),
    DRAGON_SCIMITAR_OR(4587, 20002, 20000, true),
    NECKLACE_OF_ANGUISH_OR(19547, 22246, 22249, true),
    /**
     * Trimmed
     */
    DRAGON_DEFENDER_T(12954, 20143, 19722, true),
    /**
     * Gilded
     */
    DRAGON_CHAIN_G(3140, 12534, 12414, true),
    DRAGON_PLATELEGS_G(4087, 12536, 12415, true),
    DRAGON_SKIRT_G(4585, 12536, 12416, true),
    DRAGON_FULL_HELM_G(11335, 12538, 12417, true),
    DRAGON_SQUARE_G(1187, 12532, 12418, true),
    DRAGON_BOOTS_G(11840, 22231, 22234, true),
    DRAGON_PLATEBODY_G(22239, 21895, 22244, true),
    DRAGON_KITESHIELD_G(22236, 21892, 22242, true),
    /**
     * Recolors
     */
    FROZEN_ABYSSAL_WHIP(4151, 12769, 12774, false),
    VOLCANIC_ABYSSAL_WHIP(4151, 12771, 12773, false),
    BLUE_DARK_BOW_MIX(12757, 11235, 12766, false),
    GREEN_DARK_BOW_MIX(12759, 11235, 12765, false),
    YELLOW_DARK_BOW_MIX(12761, 11235, 12767, false),
    WHITE_DARK_BOW_MIX(12763, 11235, 12768, false),
    DARK_INFINITY_HAT(12528, 6918, 12457, true),
    DARK_INFINITY_TOP(12528, 6916, 12458, true),
    DARK_INFINITY_BOTTOMS(12528, 6924, 12459, true),
    LIGHT_INFINITY_HAT(12530, 6918, 12419, true),
    LIGHT_INFINITY_TOP(12530, 6916, 12420, true),
    LIGHT_INFINITY_BOTTOMS(12530, 6924, 12421, true),
    /**
     * Items
     */
    KODAI_WAND(21043, 6914, 21006, false),
    BRIMSTONE_BOOTS(23037, 22957, 22951, false),
    AVERNIC_DEFENDER(12954, 22477, 22322, true),
    SARADOMINS_LIGHT(11791, 13256, 22296, false),
    DRAGON_HUNTER_LANCE(22966, 11889, 22978, false),
    /**
     * Shattered Relic League Cosmetics
     */
    SHATTERED_WHIP(4151, 26421, 26482, true),
    SHATTERED_TENTACLE(12006, 26421, 26484, true),
    SHATTERED_CROSSBOW(9185, 26421, 26486, true),
    SHATTERED_BALANCE_BOOK(3844, 26421, 26488, true),
    SHATTERED_DARKNESS_BOOK(12612, 26421, 26490, true),
    SHATTERED_LAW_BOOK(12610, 26421, 26492, true),
    SHATTERED_WAR_BOOK(12608, 26421, 26494, true),
    SHATTERED_HOLY_BOOK(3840, 26421, 26496, true),
    SHATTERED_UNHOLY_BOOK(3842, 26421, 26498, true)
    ;

    public final int primaryId, secondaryId, combinedId;
    public final boolean reversible;

    ItemCombining(int primaryId, int secondaryId, int combinedId, boolean reversible) {
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.combinedId = combinedId;
        this.reversible = reversible;
        ItemDef.get(combinedId).combinedFrom = this;
    }

    private static void make(Player player, Item item, Item kit, int resultID, boolean reversible) {
        String message;
        if (reversible)
            message = "Combine the " + item.getDef().name + " and " + kit.getDef().name + "?";
        else
            message = "Combining these items will be irreversible";
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", message, resultID, 1, () -> {
                player.animate(713);
                Item result = new Item(resultID, 1);
                AttributeExtensions.putAttributes(result, item.copyOfAttributes());
                item.remove();
                kit.remove();
                player.getInventory().add(result);
                new ItemDialogue().one(resultID, "You apply the " + item.getDef().name + " to the " + kit.getDef().name + ".");
            })
        );
    }

    private static void revert(Player player, Item kit, int primary, int revert) {
        if(player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough inventory space to do this.");
            return;
        }
        Item item = new Item(primary, 1);
        AttributeExtensions.putAttributes(item, kit.copyOfAttributes());
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", "Revert the item back to its normal form and get the kit back?", primary, 1, () -> {
                player.getInventory().add(item);
                kit.setId(revert);
                AttributeExtensions.removeUpgrades(kit);
                new ItemDialogue().one(primary, "You remove the " + kit.getDef().name + " from the " + item.getDef().name + ".");
            })
        );
    }

    static {
        for (ItemCombining kit : values()) {
            ItemItemAction.register(kit.primaryId, kit.secondaryId, (player, primary, secondary) -> make(player, primary, secondary, kit.combinedId, kit.reversible));
            ItemAction.registerInventory(kit.combinedId, "dismantle", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            ItemAction.registerInventory(kit.combinedId, "revert", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            ItemAction.registerInventory(kit.combinedId, "dismantle kit", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            int combinedProtect = ItemDef.get(kit.combinedId).protectValue;
            int componentsProtect = Math.max(ItemDef.get(kit.primaryId).protectValue, ItemDef.get(kit.secondaryId).protectValue);
            if (combinedProtect < componentsProtect)
                ItemDef.get(kit.combinedId).protectValue = componentsProtect;
        }
    }

}
