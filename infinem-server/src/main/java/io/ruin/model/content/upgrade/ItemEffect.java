package io.ruin.model.content.upgrade;

import io.ruin.api.utils.Random;
import io.ruin.model.content.upgrade.effects.*;
import lombok.Getter;

import java.util.stream.Stream;

/**le
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 7/13/2021
 */
@Getter
public enum ItemEffect {

    /*
     * Weapons
     */
    DRAGONHUNTER("Dragonhunter", "Deal 10% more damage to dragons.", new DragonHunter(0.10), 30.0, true, EquipSlot.WEAPON),
    //DRAGONHUNTER_II("Dragonhunter 2", "Deal 11% more damage to dragons.", new DragonHunter(0.11), 30.0, true, EquipSlot.WEAPON),
    //DRAGONHUNTER_III("Dragonhunter 3", "Deal 12% more damage to dragons.", new DragonHunter(0.12), 30.0, true, EquipSlot.WEAPON),
    //DRAGONHUNTER_IV("Dragonhunter 4", "Deal 13% more damage to dragons.", new DragonHunter(0.13), 30.0, true, EquipSlot.WEAPON),
    //DRAGONHUNTER_V("Dragonhunter 5", "Deal 14% more damage to dragons.", new DragonHunter(0.14), 30.0, true, EquipSlot.WEAPON),
    SPIDERS_BANE("Spider's Bane", "Deal 10% more damage to spiders.", new SpidersBane(), 30.0, true, EquipSlot.WEAPON),
    GHOST_BUSTER("Ghost Buster", "Deal 5% more damage to undead.", new GhostBuster(), 30.0, true, EquipSlot.WEAPON),
    LEECHING("Leeching", "5% chance to heal for 1 when dealing a hit.", new Leeching(), 12.0, true, EquipSlot.WEAPON),
    WRETCHED("Wretched", "Deal more damage the lower your health is, 25% as effective as Dharok's.", new Wretched(), 5.0, true, EquipSlot.WEAPON),
    WRECKLESS("Wreckless", "Deal 10% more damage, but take 5% of your hits as reflect damage.", new Wreckless(), 10.0, false, EquipSlot.WEAPON),
    //DAMAGED("Damaged", "Reduced damage dealt", new Damaged(), 20.0, false, EquipSlot.WEAPON),
    //ZULRAHS_KISS("Zulrah's Kiss", "10% chance to inflict poison", new ZulrahsKiss(), 20.0, true, EquipSlot.WEAPON),
    //FIRE_ASPECT("Fire Aspect", "Does fire damage to your target over time.", new Smoldering(), 15.0, true, EquipSlot.WEAPON),

    /*
     * Jewelery
     */
    //WEALTH("Wealth", "Grants access to the rare drop table.", new Wealth(), 5.0, true, EquipSlot.RING),
    BONE_COLLECTOR("Bone Collector", "Chance to send a dropped bone to the bank.", new BoneCollector(), 10.0, true, EquipSlot.AMULET),
    COIN_COLLECTOR("Coin Collector", "Automatically pick up coins.", new CoinCollector(), 10.0, true, EquipSlot.RING),
    DEVOTION("Devotion", "Chance to bury a bone and restore your prayer some.", new Devotion(), 10.0, true, EquipSlot.AMULET),
    ZULRAHS_BLESSING("Zulrah's Blessing", "25% chance to cure poison", new ZulrahsBlessing(), 10.0, true, EquipSlot.AMULET, EquipSlot.RING),
    SARADOMINS_GRACE("Saradomin's Grace", "On opponent death, 10% chance to restore 5 prayer points", new SaradominsGrace(), 10.0, true, EquipSlot.RING, EquipSlot.AMULET),
    ZAMORAKS_CURSE("Zamorak's Curse", "On opponent death, 5% chance to restore 10 prayer points, but take 5 damage.", new ZamoraksCurse(), 10.0, false, EquipSlot.AMULET, EquipSlot.RING),
    GUTHIXS_BALANCE("Guthix's Balance", "5% chance to heal you and your opponent for 10hp", new GuthixsBalance(), 100.0, false, EquipSlot.RING, EquipSlot.AMULET),
    /*
     * Armor
     */
    RECOIL("Recoil", "Reflects some damage back at your target.", new Recoil(), 20.0, true, EquipSlot.CHEST, EquipSlot.LEGS),
    WISDOM("Wisdom", "Gives a 1% experience boost.", new Wisdom(), 10.0, true, EquipSlot.HAT, EquipSlot.CHEST, EquipSlot.LEGS, EquipSlot.HANDS, EquipSlot.FEET),
    PROTECTOR("Protector", "5% Chance to reduce damage by 20%", new Protector(), 10.0, true, EquipSlot.CHEST, EquipSlot.HAT, EquipSlot.LEGS),
    ESCAPIST("Escapist", "5% Chance to gain 5% run energy when you take damage.", new Escapist(), 10, true, EquipSlot.CHEST, EquipSlot.HAT, EquipSlot.LEGS)
    ;

    private String name, description;
    private ItemUpgrade upgrade;
    private EquipSlot[] slots;
    private double weight;
    private boolean positive;

    ItemEffect(String name, String description, ItemUpgrade upgrade, double weight, boolean positive, EquipSlot... slots) {
        this.name = name;
        this.description = description;
        this.upgrade = upgrade;
        this.weight = weight;
        this.positive = positive;
        this.slots = slots;
    }

    public static ItemEffect rollFrom(ItemEffect[] effects) {
        double totalWeight = Stream.of(values()).mapToDouble(ItemEffect::getWeight).sum();
        double random = Random.get() * totalWeight;
        int index = -1;
        for (int i = 0; i < effects.length; i++) {
            random -= effects[i].getWeight();
            if (random <= 0.0D) {
                index = i;
                break;
            }
        }
        return index == -1 ? rollFrom(effects) : effects[index]; //keep rolling if didnt find anything
    }

    public static ItemUpgrade getUpgrade(ItemEffect upgrade) {
        return valueOf(upgrade.name()).getUpgrade();
    }

    public int[] getSlots() {
        return Stream.of(slots).mapToInt(EquipSlot::ordinal).toArray();
    }

    enum EquipSlot {
        HAT, CAPE, AMULET, WEAPON, CHEST, SHIELD, BLANK_1, LEGS, BLANK_2, HANDS, FEET, BLANK_3, RING, AMMO
    }

}
