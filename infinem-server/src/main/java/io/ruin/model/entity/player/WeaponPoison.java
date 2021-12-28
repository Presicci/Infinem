package io.ruin.model.entity.player;

import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 12/27/2021
 */
@Getter
public enum WeaponPoison {
    /**
     * Melee weapons
     */
    BRONZE_DAGGER(Items.BRONZE_DAGGER, Items.BRONZE_DAGGER_P, Items.BRONZE_DAGGER_P_2, Items.BRONZE_DAGGER_P_3),
    IRON_DAGGER(Items.IRON_DAGGER, Items.IRON_DAGGER_P, Items.IRON_DAGGER_P_2, Items.IRON_DAGGER_P_3),
    BLACK_DAGGER(Items.BLACK_DAGGER, Items.BLACK_DAGGER_P, Items.BLACK_DAGGER_P_2, Items.BLACK_DAGGER_P_3),
    WHITE_DAGGER(Items.WHITE_DAGGER, Items.WHITE_DAGGER_P, Items.WHITE_DAGGER_P_2, Items.WHITE_DAGGER_P_3),
    STEEL_DAGGER(Items.STEEL_DAGGER, Items.STEEL_DAGGER_P, Items.STEEL_DAGGER_P_2, Items.STEEL_DAGGER_P_3),
    MITHRIL_DAGGER(Items.MITHRIL_DAGGER, Items.MITHRIL_DAGGER_P, Items.MITHRIL_DAGGER_P_2, Items.MITHRIL_DAGGER_P_3),
    ADAMANT_DAGGER(Items.ADAMANT_DAGGER, Items.ADAMANT_DAGGER_P, Items.ADAMANT_DAGGER_P_2, Items.ADAMANT_DAGGER_P_3),
    RUNE_DAGGER(Items.RUNE_DAGGER, Items.RUNE_DAGGER_P, Items.RUNE_DAGGER_P_2, Items.RUNE_DAGGER_P_3),
    DRAGON_DAGGER(1215, 1231, 5680, 5698),
    ABYSSAL_DAGGER(Items.ABYSSAL_DAGGER, Items.ABYSSAL_DAGGER_P, Items.ABYSSAL_DAGGER_P_2, Items.ABYSSAL_DAGGER_P_3),
    KERIS(Items.KERIS, Items.KERIS_P, Items.KERIS_P_2, Items.KERIS_P_3),
    BONE_DAGGER(Items.BONE_DAGGER, Items.BONE_DAGGER_P, Items.BONE_DAGGER_P_2, Items.BONE_DAGGER_P_3),
    BRONZE_SPEAR(Items.BRONZE_SPEAR, Items.BRONZE_SPEAR_P, Items.BRONZE_SPEAR_P_2, Items.BRONZE_SPEAR_P_3, Items.BRONZE_SPEAR_KP),
    IRON_SPEAR(Items.IRON_SPEAR, Items.IRON_SPEAR_P, Items.IRON_SPEAR_P_2, Items.IRON_SPEAR_P_3, Items.IRON_SPEAR_KP),
    BLACK_SPEAR(Items.BLACK_SPEAR, Items.BLACK_SPEAR_P, Items.BLACK_SPEAR_P_2, Items.BLACK_SPEAR_P_3, Items.BLACK_SPEAR_KP),
    STEEL_SPEAR(Items.STEEL_SPEAR, Items.STEEL_SPEAR_P, Items.STEEL_SPEAR_P_2, Items.STEEL_SPEAR_P_3, Items.STEEL_SPEAR_KP),
    MITHRIL_SPEAR(Items.MITHRIL_SPEAR, Items.MITHRIL_SPEAR_P, Items.MITHRIL_SPEAR_P_2, Items.MITHRIL_SPEAR_P_3, Items.MITHRIL_SPEAR_KP),
    ADAMANT_SPEAR(Items.ADAMANT_SPEAR, Items.ADAMANT_SPEAR_P, Items.ADAMANT_SPEAR_P_2, Items.ADAMANT_SPEAR_P_3, Items.ADAMANT_SPEAR_KP),
    RUNE_SPEAR(Items.RUNE_SPEAR, Items.RUNE_SPEAR_P, Items.RUNE_SPEAR_P_2, Items.RUNE_SPEAR_P_3, Items.RUNE_SPEAR_KP),
    DRAGON_SPEAR(Items.DRAGON_SPEAR, Items.DRAGON_SPEAR_P, Items.DRAGON_SPEAR_P_2, Items.DRAGON_SPEAR_P_3, Items.DRAGON_SPEAR_KP),
    BRONZE_HASTA(Items.BRONZE_HASTA, Items.BRONZE_HASTA_P, Items.BRONZE_HASTA_P_2, Items.BRONZE_HASTA_P_3, Items.BRONZE_HASTA_KP),
    IRON_HASTA(Items.IRON_HASTA, Items.IRON_HASTA_P, Items.IRON_HASTA_P_2, Items.IRON_HASTA_P_3, Items.IRON_HASTA_KP),
    STEEL_HASTA(Items.STEEL_HASTA, Items.STEEL_HASTA_P, Items.STEEL_HASTA_P_2, Items.STEEL_HASTA_P_3, Items.STEEL_HASTA_KP),
    MITHRIL_HASTA(Items.MITHRIL_HASTA, Items.MITHRIL_HASTA_P, Items.MITHRIL_HASTA_P_2, Items.MITHRIL_HASTA_P_3, Items.MITHRIL_HASTA_KP),
    ADAMANT_HASTA(Items.ADAMANT_HASTA, Items.ADAMANT_HASTA_P, Items.ADAMANT_HASTA_P_2, Items.ADAMANT_HASTA_P_3, Items.ADAMANT_HASTA_KP),
    RUNE_HASTA(Items.RUNE_HASTA, Items.RUNE_HASTA_P, Items.RUNE_HASTA_P_2, Items.RUNE_HASTA_P_3, Items.RUNE_HASTA_KP),
    DRAGON_HASTA(22731, 22734, 22637, 22740, 22743),


    /**
     * Ranged weapons
     */
    BRONZE_DART(Items.BRONZE_DART, Items.BRONZE_DART_P, Items.BRONZE_DART_P_2, Items.BRONZE_DART_P_3),
    IRON_DART(Items.IRON_DART, Items.IRON_DART_P, Items.IRON_DART_P_2, Items.IRON_DART_P_3),
    STEEL_DART(Items.STEEL_DART, Items.STEEL_DART_P, Items.STEEL_DART_P_2, Items.STEEL_DART_P_3),
    BLACK_DART(Items.BLACK_DART, Items.BLACK_DART_P, Items.BLACK_DART_P_2, Items.BLACK_DART_P_3),
    MITHRIL_DART(Items.MITHRIL_DART, Items.MITHRIL_DART_P, Items.MITHRIL_DART_P_2, Items.MITHRIL_DART_P_3),
    ADAMANT_DART(Items.ADAMANT_DART, Items.ADAMANT_DART_P, Items.ADAMANT_DART_P_2, Items.ADAMANT_DART_P_3),
    RUNE_DART(Items.RUNE_DART, Items.RUNE_DART_P, Items.RUNE_DART_P_2, Items.RUNE_DART_P_3),
    DRAGON_DART(Items.DRAGON_DART, Items.DRAGON_DART_P, Items.DRAGON_DART_P_2, Items.DRAGON_DART_P_3),
    BRONZE_KNIFE(Items.BRONZE_KNIFE, Items.BRONZE_KNIFE_P, 5654, 5661),
    IRON_KNIFE(Items.IRON_KNIFE, Items.IRON_KNIFE_P, 5655, 5662),
    STEEL_KNIFE(Items.STEEL_KNIFE, Items.STEEL_KNIFE_P, 5656, 5663),
    MITHRIL_KNIFE(Items.MITHRIL_KNIFE, Items.MITHRIL_KNIFE_P, 5657, 5664),
    BLACK_KNIFE(Items.BLACK_KNIFE, Items.BLACK_KNIFE_P, 5658, 5665),
    ADAMANT_KNIFE(Items.ADAMANT_KNIFE, Items.ADAMANT_KNIFE_P, 5659, 5666),
    RUNE_KNIFE(Items.RUNE_KNIFE, Items.RUNE_KNIFE_P, 5660, 5667),
    DRAGON_KNIFE(22804, 22806, 22808, 22810),

    /**
     * Ranged ammo
     */
    BLURITE_BOLTS(Items.BLURITE_BOLTS, Items.BLURITE_BOLTS_P, Items.BLURITE_BOLTS_P_2, Items.BLURITE_BOLTS_P_3),
    BRONZE_BOLTS(Items.BRONZE_BOLTS, Items.BRONZE_BOLTS_P, Items.BRONZE_BOLTS_P_2, Items.BRONZE_BOLTS_P_3),
    IRON_BOLTS(Items.IRON_BOLTS, Items.IRON_BOLTS_P, Items.IRON_BOLTS_P_2, Items.IRON_BOLTS_P_3),
    STEEL_BOLTS(Items.STEEL_BOLTS, Items.STEEL_BOLTS_P, Items.STEEL_BOLTS_P_2, Items.STEEL_BOLTS_P_3),
    SILVER_BOLTS(Items.SILVER_BOLTS, Items.SILVER_BOLTS_P, Items.SILVER_BOLTS_P_2, Items.SILVER_BOLTS_P_3),
    MITHRIL_BOLTS(Items.MITHRIL_BOLTS, Items.MITHRIL_BOLTS_P, Items.MITHRIL_BOLTS_P_2, Items.MITHRIL_BOLTS_P_3),
    ADAMANT_BOLTS(Items.ADAMANT_BOLTS, Items.ADAMANT_BOLTS_P, Items.ADAMANT_BOLTS_P_2, Items.ADAMANT_BOLTS_P_3),
    RUNITE_BOLTS(Items.RUNITE_BOLTS, Items.RUNITE_BOLTS_P, Items.RUNITE_BOLTS_P_2, Items.RUNITE_BOLTS_P_3),
    DRAGON_BOLTS(Items.DRAGON_BOLTS_1, Items.DRAGON_BOLTS_1_P, Items.DRAGON_BOLTS_1_P_2, Items.DRAGON_BOLTS_1_P_3),
    BRONZE_ARROWS(Items.BRONZE_ARROW, Items.BRONZE_ARROW_P, Items.BRONZE_ARROW_P_2, Items.BRONZE_ARROW_P_3),
    IRON_ARROWS(Items.IRON_ARROW, Items.IRON_ARROW_P, Items.IRON_ARROW_P_2, Items.IRON_ARROW_P_3),
    STEEL_ARROWS(Items.STEEL_ARROW, Items.STEEL_ARROW_P, Items.STEEL_ARROW_P_2, Items.STEEL_ARROW_P_3),
    MITHRIL_ARROWS(Items.MITHRIL_ARROW, Items.MITHRIL_ARROW_P, Items.MITHRIL_ARROW_P_2, Items.MITHRIL_ARROW_P_3),
    ADAMANT_ARROWS(Items.ADAMANT_ARROW, Items.ADAMANT_ARROW_P, Items.ADAMANT_ARROW_P_2, Items.ADAMANT_ARROW_P_3),
    RUNE_ARROWS(Items.RUNE_ARROW, Items.RUNE_ARROW_P, Items.RUNE_ARROW_P_2, Items.RUNE_ARROW_P_3),
    AMETHYST_ARROWS(21326, 21332, 21334, 21336),
    DRAGON_ARROWS(Items.DRAGON_ARROW, Items.DRAGON_ARROW_P, Items.DRAGON_ARROW_P_2, Items.DRAGON_ARROW_P_3),
    BRONZE_JAVELIN(Items.BRONZE_JAVELIN, Items.BRONZE_JAVELIN_P, Items.BRONZE_JAVELIN_P_2, Items.BRONZE_JAVELIN_P_3),
    IRON_JAVELIN(Items.IRON_JAVELIN, Items.IRON_JAVELIN_P, Items.IRON_JAVELIN_P_2, Items.IRON_JAVELIN_P_3),
    STEEL_JAVELIN(Items.STEEL_JAVELIN, Items.STEEL_JAVELIN_P, Items.STEEL_JAVELIN_P_2, Items.STEEL_JAVELIN_P_3),
    MITHRIL_JAVELIN(Items.MITHRIL_JAVELIN, Items.MITHRIL_JAVELIN_P, Items.MITHRIL_JAVELIN_P_2, Items.MITHRIL_JAVELIN_P_3),
    ADAMANT_JAVELIN(Items.ADAMANT_JAVELIN, Items.ADAMANT_JAVELIN_P, Items.ADAMANT_JAVELIN_P_2, Items.ADAMANT_JAVELIN_P_3),
    RUNE_JAVELIN(Items.RUNE_JAVELIN, Items.RUNE_JAVELIN_P, Items.RUNE_JAVELIN_P_2, Items.RUNE_JAVELIN_P_3),
    AMETHYST_JAVELIN(21318, 21320, 21322, 21324),
    DRAGON_JAVELIN(Items.DRAGON_JAVELIN, Items.DRAGON_JAVELIN_P, Items.DRAGON_JAVELIN_P_2, Items.DRAGON_JAVELIN_P_3);

    private final int baseId, poisonId, plusId, plusPlusId, karamjaId;

    WeaponPoison(int baseId, int poisonId, int plusId, int plusPlusId, int karamjaId) {
        this.baseId = baseId;
        this.poisonId = poisonId;
        this.plusId = plusId;
        this.plusPlusId = plusPlusId;
        this.karamjaId = karamjaId;
    }

    WeaponPoison(int baseId, int poisonId, int plusId, int plusPlusId) {
        this(baseId, poisonId, plusId, plusPlusId, -1);
    }

    private static final int WEAPON_POISON = 187;
    private static final int WEAPON_POISON_PLUS = 5937;
    private static final int WEAPON_POISON_PLUS_PLUS = 5940;
    private static final int KARAMBWAN_PASTE = 3153;
    private static final int CLEANING_CLOTH = 3188;

    /**
     * Tips a weapon with the provided poison type
     */
    private static void tipWeapon(Player player, Item weapon, Item poison, WeaponPoison wp, int poisonType) {
        int newWeaponId;
        switch (poisonType) {
            case 0:
                newWeaponId = wp.poisonId;
                break;
            case 1:
                newWeaponId = wp.plusId;
                break;
            case 2:
                newWeaponId = wp.plusPlusId;
                break;
            case 3:
            default:
                newWeaponId = wp.karamjaId;
                break;
        }
        // Tip ammo 5 at a time
        if (weapon.getDef().stackable) {
            if (!player.getInventory().contains(newWeaponId) && !player.getInventory().hasFreeSlots(1) && player.getInventory().getAmount(weapon.getId()) > 5) {
                player.sendMessage("You do not have enough inventory space to do this.");
                return;
            }
            int quantity = player.getInventory().getAmount(weapon.getId());
            if (quantity > 5) {
                quantity = 5;
            }
            // Prevent overflow
            if ((long) player.getInventory().getAmount(newWeaponId) + (long) quantity > Integer.MAX_VALUE) {
                player.sendMessage("You have too many of those to make more.");
                return;
            }
            player.getInventory().remove(weapon.getId(), quantity);
            player.getInventory().add(newWeaponId, quantity);
            poison.setId(229);
            player.sendMessage("You tip some of the ammo with poison.");
        } else {
            weapon.setId(newWeaponId);
            poison.setId(229);
            player.sendMessage("You tip the weapon with poison.");
        }
    }

    /**
     * Cleans the poison off the provided weapon
     */
    private static void cleanWeapon(Player player, Item weapon, Item cloth, WeaponPoison wp) {
        int cleanId = wp.getBaseId();
        // Clean ammo 5 at a time
        if (weapon.getDef().stackable) {
            if (!player.getInventory().contains(cleanId) && !player.getInventory().hasFreeSlots(1) && player.getInventory().getAmount(weapon.getId()) > 5) {
                player.sendMessage("You do not have enough inventory space to do this.");
                return;
            }
            int quantity = player.getInventory().getAmount(weapon.getId());
            // Prevent overflow
            if ((long) player.getInventory().getAmount(cleanId) + (long) quantity > Integer.MAX_VALUE) {
                player.sendMessage("You have too many of those to clean more.");
                return;
            }
            player.getInventory().remove(weapon.getId(), quantity);
            player.getInventory().remove(cloth);
            player.getInventory().add(cleanId, quantity);
            player.sendMessage("You clean the poison off the ammo.");
        } else {
            weapon.setId(cleanId);
            player.getInventory().remove(cloth);
            player.sendMessage("You clean the poison off the weapon.");
        }
    }

    /**
     * Static registrations
     */
    static {
        for (WeaponPoison weapon : values()) {
            ItemItemAction.register(weapon.getBaseId(), WEAPON_POISON, (p, w, poison) -> tipWeapon(p, w, poison, weapon, 0));
            ItemItemAction.register(weapon.getBaseId(), WEAPON_POISON_PLUS, (p, w, poison) -> tipWeapon(p, w, poison, weapon, 1));
            ItemItemAction.register(weapon.getBaseId(), WEAPON_POISON_PLUS_PLUS, (p, w, poison) -> tipWeapon(p, w, poison, weapon, 2));
            // Cleaning cloths
            ItemItemAction.register(weapon.getPoisonId(), CLEANING_CLOTH, (p, w, cloth) -> cleanWeapon(p, w, cloth, weapon));
            ItemItemAction.register(weapon.getPlusId(), CLEANING_CLOTH, (p, w, cloth) -> cleanWeapon(p, w, cloth, weapon));
            ItemItemAction.register(weapon.getPlusPlusId(), CLEANING_CLOTH, (p, w, cloth) -> cleanWeapon(p, w, cloth, weapon));
            // Karamja poison
            if (weapon.getKaramjaId() > 0) {
                ItemItemAction.register(weapon.getBaseId(), KARAMBWAN_PASTE, (p, w, poison) -> tipWeapon(p, w, poison, weapon, 3));
                ItemItemAction.register(weapon.getKaramjaId(), CLEANING_CLOTH, (p, w, cloth) -> cleanWeapon(p, w, cloth, weapon));
            }
        }
        ItemItemAction.register(Items.KARAMJAN_RUM, Items.SILK, (p, rum, silk) -> {
            silk.setId(CLEANING_CLOTH);
            rum.remove();
            p.sendFilteredMessage("You douse the silk in rum.");
        });
    }
}
