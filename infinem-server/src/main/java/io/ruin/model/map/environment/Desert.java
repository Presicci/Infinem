package io.ruin.model.map.environment;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.Containers;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/8/2024
 */
public class Desert {

    private static final int[] REGIONS = {
            12587, 12589, 12590, 12591,
            12844, 12845, 12846, 12847, 12848,
            13098, 13100, 13101, 13102, 13103,
            13353, 13354, 13355, 13356, 13357, 13359, 13360,
            13611, 13612, 13614, 13615, 13616, 13869, 13870, 13872,
    };

    private static final Bounds[] BOUNDS = {
            new Bounds(3456, 3136, 3479, 3149, 0),  // North of uzer
            new Bounds(3392, 3136, 3455, 3164, 0),  // South of abbey
            new Bounds(3414, 3165, 3439, 3170, 0),
            new Bounds(3328, 3136, 3359, 3152, 0),  // West of giants foundry
            new Bounds(3360, 3136, 3362, 3140, 0),
            new Bounds(3336, 3152, 3351, 3159, 0),
            new Bounds(3264, 3072, 3294, 3135, 0),  // Around shantay pass
            new Bounds(3295, 3072, 3327, 3115, 0),
            new Bounds(3317, 3116, 3327, 3135, 0)
    };

    private static int getHelmetTicks(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_HAT);
        if (item == null) return 0;
        switch (item.getId()) {
            case 26969:     // Circlet of water
            case Items.DESERT_DISGUISE:
            case Items.FEZ:
            case Items.MENAPHITE_PURPLE_HAT:
            case Items.MENAPHITE_RED_HAT:
                return 20;
            case Items.PROSPECTOR_HELMET:
                return 6;
            default:
                if (item.getDef().weightInventory > 1) return -10;
                return 0;
        }
    }

    private static int getChestTicks(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_CHEST);
        if (item == null) return 0;
        switch (item.getId()) {
            case Items.DESERT_TOP:
            case Items.DESERT_TOP_2:
            case Items.DESERT_SHIRT:
            case Items.MENAPHITE_RED_TOP:
            case Items.MENAPHITE_PURPLE_TOP:
            case Items.PROSPECTOR_JACKET:
            case Items.ROBE_OF_ELIDINIS:
                return 20;
            case Items.SLAVE_SHIRT:
                return 10;
            default:
                if (item.getDef().weightInventory > 5) return -40;
                return 0;
        }
    }

    private static int getLegTicks(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_LEGS);
        if (item == null) return 0;
        switch (item.getId()) {
            case Items.DESERT_LEGS:
            case Items.DESERT_ROBE:
            case Items.DESERT_ROBES:
            case Items.MENAPHITE_PURPLE_KILT:
            case Items.MENAPHITE_PURPLE_ROBE:
            case Items.MENAPHITE_RED_KILT:
            case Items.MENAPHITE_RED_ROBE:
            case Items.PROSPECTOR_LEGS:
            case Items.ROBE_OF_ELIDINIS_2:
                return 20;
            case Items.SLAVE_ROBE:
                return 10;
            default:
                if (item.getDef().weightInventory > 5) return -30;
                return 0;
        }
    }

    private static int getBootTicks(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_FEET);
        if (item == null) return 0;
        switch (item.getId()) {
            case Items.DESERT_BOOTS:
            case Items.PROSPECTOR_BOOTS:
                return 10;
            case Items.SLAVE_BOOTS:
                return 5;
            default:
                if (item.getDef().weightInventory > 1) return -10;
                return 0;
        }
    }

    private static int getGloveTicks(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_HANDS);
        return item == null ? 0 : item.getDef().weightInventory > 1 ? -10 : 0;
    }

    private static int getShieldTicks(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_SHIELD);
        return item == null ? 0 : item.getDef().weightInventory > 1 ? -10 : 0;
    }

    private static int getEquipmentTicks(Player player) {
        int ticks = getHelmetTicks(player);
        ticks += getChestTicks(player);
        ticks += getLegTicks(player);
        ticks += getBootTicks(player);
        ticks += getGloveTicks(player);
        ticks += getShieldTicks(player);
        return ticks;
    }

    public static void tickDesertHeat(Player player) {
        if (player.isRuby() || player.isDiamond()) return;
        int attrValue = player.getTemporaryAttributeIntOrZero("DESERT");
        if (attrValue <= 0 || player.hasTemporaryAttribute("DESERTTICKING") || player.getEquipment().hasId(Items.DESERT_AMULET_4)) return;
        if (attrValue == 1) {
            int ticks = 150 + getEquipmentTicks(player);
            player.putTemporaryAttribute("DESERTTICKING", 1);
            player.addEvent(e -> {
                e.delay(ticks);
                player.removeTemporaryAttribute("DESERTTICKING");
                if (!player.hasTemporaryAttribute("DESERT")) return;
                damage(player);
            });
        }
    }

    public static final int[] WATERSKINS = { Items.WATERSKIN_0, Items.WATERSKIN_1, Items.WATERSKIN_2, Items.WATERSKIN_3, Items.WATERSKIN_4 };

    private static void damage(Player player) {
        // Water evaporates from bowls, buckets, jugs, and vials here, but i think thats kinda lame
        Item waterskin;
        for (int index = 1; index < WATERSKINS.length; index++) {
            waterskin = player.getInventory().findItem(WATERSKINS[index]);
            if (waterskin != null) {
                waterskin.setId(WATERSKINS[index - 1]);
                player.privateSound(2401);
                player.animate(829);
                player.sendFilteredMessage("You take a drink of water from the waterskin.");
                return;
            }
        }
        Item chocIce = player.getInventory().findItem(Items.CHOCICE);
        if (chocIce != null) {
            chocIce.remove();
            player.privateSound(2393);
            player.incrementHp(7);
            player.animate(829);
            player.sendFilteredMessage("You eat the choc-ice. It's very refreshing.");
            return;
        }
        player.hit(new Hit().fixedDamage(player.getStats().get(StatType.Hitpoints).fixedLevel / 10));
        player.sendMessage("The extreme desert heat causes you to take damage.");
    }

    static {
        for (int region : REGIONS) {
            MapListener.registerBounds(Bounds.fromRegion(region)).onEnter(player -> player.putTemporaryAttribute("DESERT", 1));
            MapListener.registerBounds(Bounds.fromRegion(region)).onExit(((player, logout) -> player.removeTemporaryAttribute("DESERT")));
        }
        for (Bounds bound : BOUNDS) {
            MapListener.registerBounds(bound).onEnter(player -> player.putTemporaryAttribute("DESERT", 1));
            MapListener.registerBounds(bound).onExit(((player, logout) -> player.removeTemporaryAttribute("DESERT")));
        }
    }
}
