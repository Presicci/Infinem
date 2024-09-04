package io.ruin.data.impl.items;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.RangedAmmo;
import io.ruin.model.combat.RangedWeapon;
import io.ruin.model.stat.StatType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class item_info {

    public static void loadFromDatabase() {
        Server.gameDb.execute(connection -> {
            PreparedStatement statement = null;
            ResultSet rs;
            try {
                statement = connection.prepareStatement("select  * from item_info");
                rs = statement.executeQuery();
                while (rs.next()) {
                    ItemDefinition def = ItemDefinition.get(rs.getInt("id"));
                    def.tradeable = rs.getInt("tradeable") == 1;
                    def.examine = rs.getString("examine");
                    def.weightInventory = rs.getInt("weight");
                    def.weightEquipment = def.weightInventory;
                    def.equipSlot = rs.getInt("equip_slot");
                    if (rs.wasNull()) {
                        def.equipSlot = -1;
                    }
                    String[] bonuses = {
                            "stab_attack",
                            "slash_attack",
                            "crush_attack",
                            "magic_attack",
                            "range_attack",
                            "stab_defence",
                            "slash_defence",
                            "crush_defence",
                            "magic_defence",
                            "range_defence",
                            "melee_strength",
                            "ranged_strength",
                            "magic_damage",
                            "prayer"
                    };
                    for(int i = 0; i < bonuses.length; i++) {
                        int value = rs.getInt(bonuses[i]);
                       // if (value > 0) {
                            if (def.equipBonuses == null) {
                                def.equipBonuses = new int[14];
                            }
                            def.equipBonuses[i] = value;
                       // }
                    }
                    String requirementString = rs.getString("equip_requirements");
                    if (requirementString != null && !requirementString.isEmpty()) {
                        List<Integer> reqs = new ArrayList<>();
                        if (requirementString.equalsIgnoreCase("max")) {
                            for (StatType stat : StatType.values()) {
                                reqs.add(stat.ordinal() << 8 | 99);
                            }
                        } else {
                            String[] requirements = requirementString.split(",");
                            try {
                                for (String requirement : requirements) {
                                    String[] parts = requirement.split(":");
                                    int stat = Integer.parseInt(parts[0]);
                                    int level = Integer.parseInt(parts[1]);
                                    reqs.add(stat << 8 | level);
                                    if (stat == 4) def.rangedLevel = level;
                                }
                            } catch (Exception e) {
                                ServerWrapper.logError("Failed to load requirements for item: " + def.id, e);
                            }
                        }
                        if (!reqs.isEmpty()) {
                            def.equipReqs = new int[reqs.size()];
                            for (int i = 0; i < def.equipReqs.length; i++)
                                def.equipReqs[i] = reqs.get(i);
                        }
                    }
                    String tagString = rs.getString("tags");
                    if (tagString != null && !tagString.isEmpty()) {
                        String[] tags = tagString.split("(?<!\\\\),");
                        for (String tag : tags) {
                            tag = tag.replace("\\,", ",");
                            String[] parts = tag.split(":");
                            switch (parts[0].toLowerCase()) {
                                case "weapon_type":
                                    def.weaponType = weapon_types.MAP.get(parts[1]);
                                    break;
                                case "shield_type":
                                    def.shieldType = shield_types.MAP.get(parts[1]);
                                    break;
                                case "ranged_weapon":
                                    def.rangedWeapon = RangedWeapon.valueOf(parts[1]);
                                    break;
                                case "ranged_ammo":
                                    def.rangedAmmo = RangedAmmo.valueOf(parts[1]);
                                    break;
                                case "destroy":
                                    def.custom_values.put("DESTROY", parts.length == 1 ? "" : parts[1]);
                                    break;
                                case "release":
                                    def.custom_values.put("RELEASE", parts.length == 1 ? "" : parts[1]);
                                    break;
                                case "hide_hair":
                                    def.hideHair = true;
                                    break;
                                case "hide_beard":
                                    def.hideBeard = true;
                                    break;
                                case "hide_arms":
                                    def.hideArms = true;
                                    break;
                                case "dropannounce":
                                    def.dropAnnounce = true;
                                    break;
                                case "two_handed":
                                    def.twoHanded = true;
                                    break;
                            }
                        }
                    }
                }
            } finally {
                DatabaseUtils.close(statement);
                ItemDefinition.forEach(item_info::loadMisc);
                //shield_types.unload();
                //weapon_types.unload();
            }
        });
    }

    private static void loadMisc(ItemDefinition def) {
        def.dropOption = def.getOption("drop", "release");
        def.equipOption = def.getOption("wield", "equip", "wear", "ride", "hold", "chill");
        def.pickupOption = def.getGroundOption("take", "pickup");
        if(def.value > 0) {
            def.highAlchValue = (def.value * 3) / 5;
            def.lowAlchValue = (def.highAlchValue / 3) * 2;
        }
        /*
         * Keep last
         */
        if(def.isNote()) {
            ItemDefinition reg = ItemDefinition.get(def.notedId);
            if (reg == null) {
                System.err.println("ITEM NOTE ERROR! ID:" + def.id + "");
                return;
            }
            def.name = reg.name;
            def.tradeable = reg.tradeable;
            def.examine = "Swap this note at any bank for the equivalent item.";
            def.stackable = true;
        }
        if(def.isPlaceholder()) {
            ItemDefinition reg = ItemDefinition.get(def.placeholderMainId);
            def.name = reg.name;
            def.examine = reg.examine;
            /*ItemDef.forEach(def2 -> {
                if(def2.inventoryModel != reg.inventoryModel || def2.hasPlaceholder())
                    return;
                if(!stripName(def2.name).startsWith(stripName(reg.name)))
                    return;
                def2.placeholderMainId = reg.placeholderMainId;
                def2.placeholderTemplateId = reg.placeholderTemplateId;
            });*/
        }
    }

    public static final class Temp {
        @Expose public int id;
        @Expose public boolean tradeable;
        @Expose public String examine;
        @Expose public double weight;
        @Expose public Double weight_equipped;
        @Expose public int protect_value;
        @Expose public boolean wilderness;
        /**
         * Attributes that don't have to be set.
         */
        @Expose public String destroy;
        @Expose public String release;
        @Expose public Integer equip_slot;
        @Expose public Boolean two_handed;
        @Expose public Boolean hide_hair;
        @Expose public Boolean hide_beard;
        @Expose public Boolean hide_arms;
        //okay this isn't the prettiest design...
        @Expose public Integer attack_level, defence_level, strength_level, hitpoints_level, ranged_level, prayer_level, magic_level,
                cooking_level, woodcutting_level, fletching_level, fishing_level, firemaking_level, crafting_level, smithing_level, mining_level,
                herblore_level, agility_level, thieving_level, slayer_level, farming_level, runecrafting_level, hunter_level, construction_level;
        //neither is this...
        @Expose public Integer[] bonuses;
        @Expose public Integer stab_attack_bonus, slash_attack_bonus, crush_attack_bonus, magic_attack_bonus, range_attack_bonus,
                stab_defence_bonus, slash_defence_bonus, crush_defence_bonus, magic_defence_bonus, range_defence_bonus,
                melee_strength_bonus, ranged_strength_bonus, magic_damage_bonus,
                prayer_bonus, undead_bonus, slayer_bonus;
        @Expose public String shield_type;
        @Expose public String weapon_type;
        @Expose public String ranged_weapon;
        @Expose public String ranged_ammo;
        @Expose public boolean dropAnnounce;
    }

}