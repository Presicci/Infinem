package io.ruin.model.combat;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.npc.MaxHitDummy;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

public class CombatUtils {

    /**
     * Magic calcs
     */

    private static final int[] MAGIC_CALC_SLOTS = {Equipment.SLOT_CHEST, Equipment.SLOT_LEGS};

    private static double getMagicInterference(Player player) {
        int interferenceCount = 0;
        for(int slot : MAGIC_CALC_SLOTS) {
            ItemDefinition def = player.getEquipment().getDef(slot);
            if(def == null || def.equipBonuses == null)
                continue;
            /**
             * Things like Rune armor, D'hide, etc.
             */
            if(def.equipBonuses[EquipmentStats.MAGIC_ATTACK] < 0)
                interferenceCount++;
        }
        return interferenceCount * 0.45;
    }

    /**
     * Attack calcs
     */

    private static int getEffectiveAttack(Entity entity, StatType statType, AttackType attackType) {
        int effectiveAttack = (int) entity.getCombat().getLevel(statType);
        if(statType == StatType.Magic) {
            if(entity.player != null) {
                effectiveAttack = (int) Math.floor((double) effectiveAttack * (1D + entity.player.getPrayer().magicBoost));
            }
        } else if(statType == StatType.Ranged) {
            if (entity.player != null)
                effectiveAttack *= (1D + entity.player.getPrayer().rangedAttackBoost);
            if (attackType == AttackType.ACCURATE)
                effectiveAttack += 3;
            else if (attackType == AttackType.LONG_RANGED)
                effectiveAttack += 1;
        } else if (statType == StatType.Defence) {
            if(entity.player != null) {
                effectiveAttack = (int) Math.floor((double) effectiveAttack * (1D + entity.player.getPrayer().defenceBoost));
            }
        } else {
            if(entity.player != null)
                effectiveAttack *= (1D + entity.player.getPrayer().attackBoost);
            if(attackType == AttackType.ACCURATE)
                effectiveAttack += 3;
            else if(attackType == AttackType.CONTROLLED)
                effectiveAttack += 1;
        }
        effectiveAttack += entity.player != null ? 8 : 9;
        return effectiveAttack;
    }

    public static double getAttackBonus(Entity entity, AttackStyle attackStyle, AttackType attackType) {
        double effectiveAttack, bonus = 0;
        if(attackStyle == AttackStyle.MAGIC || attackStyle == AttackStyle.RANGED_MAGIC) {
            effectiveAttack = getEffectiveAttack(entity, StatType.Magic, attackType);
            bonus = entity.getCombat().getBonus(EquipmentStats.MAGIC_ATTACK);
        } else if(attackStyle == AttackStyle.RANGED || attackStyle == AttackStyle.MAGICAL_RANGED) {
            effectiveAttack = getEffectiveAttack(entity, StatType.Ranged, attackType);
            bonus = entity.getCombat().getBonus(EquipmentStats.RANGE_ATTACK);
        } else if (entity.isPlayer() && attackStyle == AttackStyle.DRAGONFIRE) {
            effectiveAttack = getEffectiveAttack(entity, StatType.Defence, attackType);
        } else {
            effectiveAttack = getEffectiveAttack(entity, StatType.Attack, attackType);
            if(attackStyle == AttackStyle.STAB)
                bonus = entity.getCombat().getBonus(EquipmentStats.STAB_ATTACK);
            else if(attackStyle == AttackStyle.SLASH)
                bonus = entity.getCombat().getBonus(EquipmentStats.SLASH_ATTACK);
            else if(attackStyle == AttackStyle.CRUSH || attackStyle == AttackStyle.RANGED_MELEE)
                bonus = entity.getCombat().getBonus(EquipmentStats.CRUSH_ATTACK);
            else if(attackStyle == AttackStyle.MAGICAL_MELEE)
                bonus = 0; // i know this was implied before but im leaving it explicit just to make it clear
        }
        return effectiveAttack * (bonus + 64D);
    }

    /**
     * Defence calcs
     */

    private static double getEffectiveDefence(Entity entity) {
        AttackType type = entity.getCombat().getAttackType();
        double effectiveDefence = entity.getCombat().getLevel(StatType.Defence);
        if(entity.player != null) {
            effectiveDefence *= (1D + entity.player.getPrayer().defenceBoost);
            if (SetEffect.TORAG_DAMNED.hasPieces((Player) entity)) {
                effectiveDefence *= 1D + (1D - (Double.valueOf(entity.getHp()) / Double.valueOf(entity.getMaxHp())));
            }
        }
        if(type != null) {
            if(type == AttackType.DEFENSIVE || type == AttackType.LONG_RANGED)
                effectiveDefence += 3;
            else if(type == AttackType.CONTROLLED)
                effectiveDefence += 1;
        }
        effectiveDefence += entity.player != null ? 0 : 9;
        return effectiveDefence;
    }

    private static int getEffectiveMagicDefence(Entity entity) {
        if (entity.player != null) {
            double effectiveMagicDefence = Math.floor(entity.getCombat().getLevel(StatType.Magic) * 0.7);
            double effectiveDefence = Math.floor(entity.getCombat().getLevel(StatType.Defence) * 0.3);
            effectiveMagicDefence *= (1D + entity.player.getPrayer().magicBoost);
            effectiveDefence *= (1D + entity.player.getPrayer().defenceBoost);
            return (int) Math.floor(effectiveMagicDefence) + (int) Math.floor(effectiveDefence) + 8;
        } else {
            return (int) entity.getCombat().getLevel(StatType.Magic) + 9;
        }
    }

    public static double getDefenceBonus(Entity entity, Hit hit) {
        double effectiveDefence = getEffectiveDefence(entity);
        double bonus = 0;
        AttackStyle attackStyle = hit.attackStyle;
        if (attackStyle == AttackStyle.MAGIC || attackStyle == AttackStyle.MAGICAL_RANGED || attackStyle == AttackStyle.MAGICAL_MELEE) {
            effectiveDefence = getEffectiveMagicDefence(entity);
            bonus = entity.getCombat().getBonus(EquipmentStats.MAGIC_DEFENCE);
        } else if (attackStyle == AttackStyle.STAB)
            bonus = entity.getCombat().getBonus(EquipmentStats.STAB_DEFENCE);
        else if (attackStyle == AttackStyle.SLASH)
            bonus = entity.getCombat().getBonus(EquipmentStats.SLASH_DEFENCE);
        else if (attackStyle == AttackStyle.CRUSH)
            bonus = entity.getCombat().getBonus(EquipmentStats.CRUSH_DEFENCE);
        else if (attackStyle == AttackStyle.RANGED || attackStyle == AttackStyle.RANGED_MAGIC || attackStyle == AttackStyle.RANGED_MELEE) {
            if (entity.isNpc()) {
                ItemDefinition weapon = hit.attackWeapon;
                RangedWeapon.DamageType damageType;
                if (weapon != null && weapon.rangedWeapon != null) damageType = weapon.rangedWeapon.damageType;
                else damageType = RangedWeapon.DamageType.STANDARD;
                if (damageType == RangedWeapon.DamageType.LIGHT) {
                    bonus = entity.getCombat().getBonus(10);
                } else if (damageType == RangedWeapon.DamageType.HEAVY) {
                    bonus = entity.getCombat().getBonus(11);
                } else {
                    bonus = entity.getCombat().getBonus(9);
                }
            } else {
                bonus = entity.getCombat().getBonus(EquipmentStats.RANGE_DEFENCE);
            }
        }
        return effectiveDefence * (bonus + 64D);
    }

    /**
     * Max damage (Melee & Ranged only!)
     */

    private static double getEffectiveStrength(Entity entity, StatType statType, AttackType attackType) {
        double prayerBonus = 0, styleBonus = 0;
        double effectiveStrength = entity.getCombat().getLevel(statType);
        if(statType == StatType.Ranged) {
            if(entity.player != null)
                prayerBonus = (1D + entity.player.getPrayer().rangedStrengthBoost);
            if(attackType == AttackType.ACCURATE)
                styleBonus += 3;
            else if(attackType == AttackType.LONG_RANGED)
                styleBonus += 1;
        } else {
            if(entity.player != null) {
                prayerBonus = 1D + entity.player.getPrayer().strengthBoost;
                if (attackType == AttackType.AGGRESSIVE) {
                    styleBonus = 3;
                } else if (attackType == AttackType.CONTROLLED) {
                    styleBonus = 1;
                }
            }
        }
        return Math.ceil(effectiveStrength * prayerBonus) + styleBonus + entity.getStrAdder();
    }

    public static int getMaxDamage(Entity entity, AttackStyle attackStyle, AttackType attackType) {
        double effectiveStrength, bonus;
        if(attackStyle == AttackStyle.RANGED) {
            effectiveStrength = getEffectiveStrength(entity, StatType.Ranged, attackType);
            bonus = entity.getCombat().getBonus(EquipmentStats.RANGED_STRENGTH);
        } else {
            effectiveStrength = getEffectiveStrength(entity, StatType.Strength, attackType);
            bonus = entity.getCombat().getBonus(EquipmentStats.MELEE_STRENGTH);
        }
        return (int) (1.3 + (effectiveStrength / 10d) + (bonus / 80d) + ((effectiveStrength * bonus) / 640d));
    }

    /**
     * Experience
     */

    public static void addXp(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int damageDealt) {
        if (attackType == null) return;
        boolean multiplier = victim.npc != null;
        double xp = damageDealt * 4D;
        double monsterMod = 1.0;
        if (multiplier && Arrays.stream(MaxHitDummy.dummyIds).noneMatch(i -> i == victim.npc.getId())) {
            monsterMod += Math.max(0D, victim.npc.getCombat().getInfo().combat_xp_modifier / 100D);
        }
        xp *= monsterMod;
        if (xp <= 0)
            return;
        if(attackStyle.isMagic()) {//this can pretty much only happen from trident!!
            xp /= 2;
            if(player.showHitAsExperience()) {
                player.getStats().addXp(StatType.Magic, damageDealt, false);
                return;
            }
            switch(attackType) {
                case ACCURATE:
                    player.getStats().addXp(StatType.Magic, xp, multiplier);
                    break;
                case DEFENSIVE:
                    xp /= 2;
                    player.getStats().addXp(StatType.Magic, xp, multiplier);
                    player.getStats().addXp(StatType.Defence, xp, multiplier);
                    break;
            }
        } else if(attackStyle.isRanged()) {
            if(player.showHitAsExperience()) {
                player.getStats().addXp(StatType.Ranged, damageDealt, false);
                return;
            }
            switch(attackType) {
                case LONG_RANGED:
                    xp /= 2;
                    player.getStats().addXp(StatType.Ranged, xp, multiplier);
                    player.getStats().addXp(StatType.Defence, xp, multiplier);
                    break;
                default:
                    player.getStats().addXp(StatType.Ranged, xp, multiplier);
                    break;
            }
        } else {
            switch(attackType) {
                case ACCURATE:
                    if(player.showHitAsExperience()) {
                        player.getStats().addXp(StatType.Attack, damageDealt, false);
                        return;
                    }
                    player.getStats().addXp(StatType.Attack, xp, multiplier);
                    break;
                case AGGRESSIVE:
                    if(player.showHitAsExperience()) {
                        player.getStats().addXp(StatType.Strength, damageDealt, false);
                        return;
                    }
                    player.getStats().addXp(StatType.Strength, xp, multiplier);
                    break;
                case CONTROLLED:
                    if(player.showHitAsExperience()) {
                        player.getStats().addXp(StatType.Attack, damageDealt, false);
                        return;
                    }
                    xp /= 3;
                    player.getStats().addXp(StatType.Attack, xp, multiplier);
                    player.getStats().addXp(StatType.Strength, xp, multiplier);
                    player.getStats().addXp(StatType.Defence, xp, multiplier);
                    break;
                case DEFENSIVE:
                    if(player.showHitAsExperience()) {
                        player.getStats().addXp(StatType.Defence, damageDealt, false);
                        return;
                    }
                    player.getStats().addXp(StatType.Defence, xp, multiplier);
                    break;
            }
        }
        player.getStats().addXp(StatType.Hitpoints, damageDealt * 1.33 * monsterMod, multiplier);
    }

    public static void addMagicXp(Player player, double baseXp, int damage, boolean multiplier) {
        double xp = baseXp + (damage * 2D);
        if(player.showHitAsExperience) {
            player.getStats().addXp(StatType.Magic, damage, false);
            return;
        }
        if(Config.DEFENSIVE_CAST.get(player) == 1) {
            xp /= 2;
            player.getStats().addXp(StatType.Defence, xp, multiplier);
            player.getStats().addXp(StatType.Magic, xp, multiplier);
        } else {
            player.getStats().addXp(StatType.Magic, xp, multiplier);
        }
        if(damage > 0)
            player.getStats().addXp(StatType.Hitpoints, damage * 1.33, multiplier);
    }

    /**
     * Combat level
     */

    public static int getCombatLevel(Player player) {
        StatList stats = player.getStats();
        double attack = stats.get(StatType.Attack).fixedLevel;
        double defence = stats.get(StatType.Defence).fixedLevel;
        double strength = stats.get(StatType.Strength).fixedLevel;
        double hitpoints = stats.get(StatType.Hitpoints).fixedLevel;
        double ranged = stats.get(StatType.Ranged).fixedLevel;
        double prayer = stats.get(StatType.Prayer).fixedLevel;
        double magic = stats.get(StatType.Magic).fixedLevel;
        double coreBase = (defence + hitpoints + (int) (prayer / 2D)) * 0.25;
        double meleeBase = (attack + strength) * 0.325;
        double rangedBase = ((int) (ranged / 2D) + ranged) * 0.325;
        double magicBase = ((int) (magic / 2D) + magic) * 0.325;
        return (int) (coreBase + Math.max(meleeBase, Math.max(rangedBase, magicBase)));
    }

}
