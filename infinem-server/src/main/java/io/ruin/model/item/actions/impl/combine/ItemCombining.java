package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

public enum ItemCombining {
    /**
     * Upgrade kits
     */
    ODIUM_WARD_UPGRADE(Items.ODIUM_WARD, Items.WARD_UPGRADE_KIT, Items.ODIUM_WARD_OR, true),
    MALEDICTION_WARD_UPGRADE(Items.MALEDICTION_WARD, Items.WARD_UPGRADE_KIT, Items.MALEDICTION_WARD_OR, true),
    GRANITE_CLAMP(Items.GRANITE_MAUL, Items.GRANITE_CLAMP, Items.GRANITE_MAUL_CLAMPED, true),
    DRAGON_PICKAXE_UPGRADE(Items.DRAGON_PICKAXE, Items.DRAGON_PICKAXE_UPGRADE_KIT, Items.DRAGON_PICKAXE_OR, true),
    STEAM_BATTLESTAFF_UPGRADE(Items.STEAM_BATTLESTAFF, Items.STEAM_STAFF_UPGRADE_KIT, Items.STEAM_BATTLESTAFF_2, true),
    MYSTIC_STEAM_BATTLESTAFF_UPGRADE(Items.MYSTIC_STEAM_STAFF, Items.STEAM_STAFF_UPGRADE_KIT, Items.MYSTIC_STEAM_STAFF_2, true),

    /**
     * Ornamental
     */
    AMULET_OF_FURY_OR(Items.AMULET_OF_FURY, Items.FURY_ORNAMENT_KIT, Items.AMULET_OF_FURY_OR, true),
    AMULET_OF_TORTURE_OR(Items.AMULET_OF_TORTURE, Items.TORTURE_ORNAMENT_KIT, Items.AMULET_OF_TORTURE_OR, true),
    OCCULT_NECKLACE_OR(Items.OCCULT_NECKLACE, Items.OCCULT_ORNAMENT_KIT, Items.OCCULT_NECKLACE_OR, true),
    NECKLACE_OF_ANGUISH_OR(Items.NECKLACE_OF_ANGUISH, Items.ANGUISH_ORNAMENT_KIT, Items.NECKLACE_OF_ANGUISH_OR, true),
    ARMADYL_GODSWORD_OR(Items.ARMADYL_GODSWORD, Items.ARMADYL_GODSWORD_ORNAMENT_KIT, Items.ARMADYL_GODSWORD_OR, true),
    BANDOS_GODSWORD_OR(Items.BANDOS_GODSWORD, Items.BANDOS_GODSWORD_ORNAMENT_KIT, Items.BANDOS_GODSWORD_OR, true),
    SARADOMIN_GODSWORD_OR(Items.SARADOMIN_GODSWORD, Items.SARADOMIN_GODSWORD_ORNAMENT_KIT, Items.SARADOMIN_GODSWORD_OR, true),
    ZAMORAK_GODSWORD_OR(Items.ZAMORAK_GODSWORD, Items.ZAMORAK_GODSWORD_ORNAMENT_KIT, Items.ZAMORAK_GODSWORD_OR, true),
    DRAGON_SCIMITAR_OR(Items.DRAGON_SCIMITAR, Items.DRAGON_SCIMITAR_ORNAMENT_KIT, Items.DRAGON_SCIMITAR_OR, true),

    /**
     * Trimmed
     */
    DRAGON_DEFENDER_T(Items.DRAGON_DEFENDER, Items.DRAGON_DEFENDER_ORNAMENT_KIT, Items.DRAGON_DEFENDER_T, true),

    /**
     * Gilded
     */
    DRAGON_CHAIN_G(Items.DRAGON_CHAINBODY, Items.DRAGON_CHAINBODY_ORNAMENT_KIT, Items.DRAGON_CHAINBODY_G, true),
    DRAGON_PLATELEGS_G(Items.DRAGON_PLATELEGS, Items.DRAGON_LEGSSKIRT_ORNAMENT_KIT, Items.DRAGON_PLATELEGS_G, true),
    DRAGON_SKIRT_G(Items.DRAGON_PLATESKIRT, Items.DRAGON_LEGSSKIRT_ORNAMENT_KIT, Items.DRAGON_PLATESKIRT_G, true),
    DRAGON_FULL_HELM_G(Items.DRAGON_FULL_HELM, Items.DRAGON_FULL_HELM_ORNAMENT_KIT, Items.DRAGON_FULL_HELM_G, true),
    DRAGON_SQUARE_G(Items.DRAGON_SQ_SHIELD, Items.DRAGON_SQ_SHIELD_ORNAMENT_KIT, Items.DRAGON_SQ_SHIELD_G, true),
    DRAGON_BOOTS_G(Items.DRAGON_BOOTS, Items.DRAGON_BOOTS_ORNAMENT_KIT, Items.DRAGON_BOOTS_G, true),
    DRAGON_PLATEBODY_G(Items.DRAGON_PLATEBODY, Items.DRAGON_PLATEBODY_ORNAMENT_KIT, Items.DRAGON_PLATEBODY_G, true),
    DRAGON_KITESHIELD_G(Items.DRAGON_KITE_SHIELD, 22239, 22244, true),

    /**
     * Recolors
     */
    FROZEN_ABYSSAL_WHIP(Items.ABYSSAL_WHIP, Items.FROZEN_WHIP_MIX, Items.FROZEN_ABYSSAL_WHIP, false),
    VOLCANIC_ABYSSAL_WHIP(Items.ABYSSAL_WHIP, Items.VOLCANIC_WHIP_MIX, Items.VOLCANIC_ABYSSAL_WHIP, false),
    BLUE_DARK_BOW_MIX(Items.DARK_BOW, Items.BLUE_DARK_BOW_PAINT, Items.DARK_BOW_BLUE, false),
    GREEN_DARK_BOW_MIX(Items.DARK_BOW, Items.GREEN_DARK_BOW_PAINT, Items.DARK_BOW_GREEN, false),
    YELLOW_DARK_BOW_MIX(Items.DARK_BOW, Items.YELLOW_DARK_BOW_PAINT, Items.DARK_BOW_YELLOW, false),
    WHITE_DARK_BOW_MIX(Items.DARK_BOW, Items.WHITE_DARK_BOW_PAINT, Items.DARK_BOW_WHITE, false),
    DARK_INFINITY_HAT(Items.INFINITY_HAT, Items.DARK_INFINITY_COLOUR_KIT, Items.DARK_INFINITY_HAT, true),
    DARK_INFINITY_TOP(Items.INFINITY_TOP, Items.DARK_INFINITY_COLOUR_KIT, Items.DARK_INFINITY_TOP, true),
    DARK_INFINITY_BOTTOMS(Items.INFINITY_BOTTOMS, Items.DARK_INFINITY_COLOUR_KIT, Items.DARK_INFINITY_BOTTOMS, true),
    LIGHT_INFINITY_HAT(Items.INFINITY_HAT, Items.LIGHT_INFINITY_COLOUR_KIT, Items.LIGHT_INFINITY_HAT, true),
    LIGHT_INFINITY_TOP(Items.INFINITY_TOP, Items.LIGHT_INFINITY_COLOUR_KIT, Items.LIGHT_INFINITY_TOP, true),
    LIGHT_INFINITY_BOTTOMS(Items.INFINITY_BOTTOMS, Items.LIGHT_INFINITY_COLOUR_KIT, Items.LIGHT_INFINITY_BOTTOMS, true),

    /**
     * Golden prospector
     */
    GOLDEN_PROSPECTOR_HELMET(Items.PROSPECTOR_HELMET, 25547, 25549, false),
    GOLDEN_PROSPECTOR_JACKET(Items.PROSPECTOR_JACKET, 25547, 25551, false),
    GOLDEN_PROSPECTOR_LEGS(Items.PROSPECTOR_LEGS, 25547, 25553, false),
    GOLDEN_PROSPECTOR_BOOTS(Items.PROSPECTOR_BOOTS, 25547, 25555, false),

    /**
     * Items
     */
    KODAI_WAND(Items.MASTER_WAND, Items.KODAI_INSIGNIA, Items.KODAI_WAND, false),
    BRIMSTONE_BOOTS(23037, 22957, 22951, false),
    AVERNIC_DEFENDER(Items.DRAGON_DEFENDER, 22477, Items.AVERNIC_DEFENDER, true, "Revert the defender back to a dragon defender and get the hilt back?"),
    SARADOMINS_LIGHT(Items.STAFF_OF_THE_DEAD, Items.SARADOMINS_LIGHT, Items.STAFF_OF_LIGHT, false),
    DRAGON_HUNTER_LANCE(Items.ZAMORAKIAN_HASTA, 22966, 22978, false),
    NEITIZNOT_FACEGUARD(Items.HELM_OF_NEITIZNOT, 24268, 24271, true, "Revert the helm back to its normal form and get the jaw back?"),
    MAGMA_HELM(Items.SERPENTINE_HELM, 13201, 13199, true, "Revert the helm back to its normal form and get the mutagen back?"),
    MAGMA_HELM_UNCHARGED(Items.SERPENTINE_HELM_UNCHARGED, 13201, 13198, true, "Revert the helm back to its normal form and get the mutagen back?"),
    TANZANITE_HELM(Items.SERPENTINE_HELM, 13200, 13197, true, "Revert the helm back to its normal form and get the mutagen back?"),
    TANZANITE_UNCHARGED(Items.SERPENTINE_HELM_UNCHARGED, 13200, 13196, true, "Revert the helm back to its normal form and get the mutagen back?"),

    /**
     * Shattered Relic League Cosmetics
     */
    SHATTERED_WHIP(Items.ABYSSAL_WHIP, 26421, 26482, true),
    SHATTERED_TENTACLE(Items.ABYSSAL_TENTACLE, 26421, 26484, true),
    SHATTERED_CROSSBOW(Items.RUNE_CROSSBOW, 26421, 26486, true),
    SHATTERED_BALANCE_BOOK(Items.BOOK_OF_BALANCE, 26421, 26488, true),
    SHATTERED_DARKNESS_BOOK(Items.BOOK_OF_DARKNESS, 26421, 26490, true),
    SHATTERED_LAW_BOOK(Items.BOOK_OF_LAW, 26421, 26492, true),
    SHATTERED_WAR_BOOK(Items.BOOK_OF_WAR, 26421, 26494, true),
    SHATTERED_HOLY_BOOK(Items.HOLY_BOOK, 26421, 26496, true),
    SHATTERED_UNHOLY_BOOK(Items.UNHOLY_BOOK, 26421, 26498, true);

    public final int mainId, accessoryId, combinedId;
    public final boolean reversible;
    private String dismantleString = "Revert the item back to its normal form and get the kit back?";

    ItemCombining(int mainId, int accessoryId, int combinedId, boolean reversible) {
        this.mainId = mainId;
        this.accessoryId = accessoryId;
        this.combinedId = combinedId;
        this.reversible = reversible;
        ItemDefinition.get(combinedId).combinedFrom = this;
    }

    ItemCombining(int mainId, int accessoryId, int combinedId, boolean reversible, String dismantleString) {
        this(mainId, accessoryId, combinedId, reversible);
        this.dismantleString = dismantleString;
    }

    private static void make(Player player, Item item, Item kit, ItemCombining itemCombining) {
        String message;
        if (itemCombining.reversible)
            message = "Combine the " + item.getDef().name + " and " + kit.getDef().name + "?";
        else
            message = "Combining these items will be irreversible";
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", message, itemCombining.combinedId, 1, () -> {
                player.animate(713);
                player.dialogue(new ItemDialogue().one(itemCombining.combinedId, "You apply the " + kit.getDef().name + " to the " + item.getDef().name + "."));
                kit.remove();
                item.setId(itemCombining.combinedId);
            })
        );
    }

    private static void revert(Player player, Item item, ItemCombining itemCombining) {
        if(player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough inventory space to do this.");
            return;
        }
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", itemCombining.dismantleString, itemCombining.mainId, 1, () -> {
                player.getInventory().add(itemCombining.accessoryId);
                item.setId(itemCombining.mainId);
                new ItemDialogue().one(itemCombining.mainId, "You remove the " + ItemDefinition.get(itemCombining.accessoryId).name + " from the " + item.getDef().name + ".");
            })
        );
    }

    static {
        for (ItemCombining kit : values()) {
            ItemItemAction.register(kit.mainId, kit.accessoryId, (player, mainId, accessoryId) -> make(player, mainId, accessoryId, kit));
            ItemAction.registerInventory(kit.combinedId, "dismantle", (player, item) -> revert(player, item, kit));
            ItemAction.registerInventory(kit.combinedId, "revert", (player, item) -> revert(player, item, kit));
            ItemAction.registerInventory(kit.combinedId, "dismantle kit", (player, item) -> revert(player, item, kit));
            ItemAction.registerInventory(kit.combinedId, "restore", (player, item) -> revert(player, item, kit));
            int combinedProtect = ItemDefinition.get(kit.combinedId).protectValue;
            int componentsProtect = Math.max(ItemDefinition.get(kit.mainId).protectValue, ItemDefinition.get(kit.accessoryId).protectValue);
            if (combinedProtect < componentsProtect)
                ItemDefinition.get(kit.combinedId).protectValue = componentsProtect;
        }
    }

}
