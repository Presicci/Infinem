package io.ruin.model.combat;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum SetEffect {
    VERAC((player, target, hit) -> {
        if (Random.rollDie(4)) {
            hit.ignorePrayer().ignoreDefence();
            hit.maxDamage += 1;
            hit.damage += 1;
            target.graphics(1041);
        }
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_HAT, 4753, 4976, 4977, 4978, 4979),
            new Piece(Equipment.SLOT_CHEST, 4757, 4988, 4989, 4990, 4991),
            new Piece(Equipment.SLOT_LEGS, 4759, 4994, 4995, 4996, 4997),
            new Piece(Equipment.SLOT_WEAPON, 4755, 4982, 4983, 4984, 4985)),

    VERAC_DAMNED(
            new Piece(Equipment.SLOT_HAT, 4753, 4976, 4977, 4978, 4979),
            new Piece(Equipment.SLOT_CHEST, 4757, 4988, 4989, 4990, 4991),
            new Piece(Equipment.SLOT_LEGS, 4759, 4994, 4995, 4996, 4997),
            new Piece(Equipment.SLOT_WEAPON, 4755, 4982, 4983, 4984, 4985),
            new Piece(Equipment.SLOT_AMULET, 12851, 12853)),

    DHAROK((player, target, hit) -> {
        double damageMod = ((player.getMaxHp() - player.getHp()) / 100f) * (player.getMaxHp() / 100f);
        hit.boostDamage(damageMod);
        player.sendMessage(damageMod + "");
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_HAT, 4716, 4980, 4981, 4982, 4983),
            new Piece(Equipment.SLOT_CHEST, 4720, 4892, 4893, 4894, 4895),
            new Piece(Equipment.SLOT_LEGS, 4722, 4898, 4899, 4900, 4901),
            new Piece(Equipment.SLOT_WEAPON, 4718, 4886, 4887, 4888, 4889)),

    DHAROK_DAMNED((player, target, hit) -> {
        if (Random.rollDie(4)) {
            int damage = (int) Math.ceil(hit.damage * 0.15);
            if(damage == 0)
                return;
            hit.attacker.hit(new Hit().fixedDamage(damage));
        }
    }, (player, hit) -> hit.attacker != null && hit.attackStyle != null,
            new Piece(Equipment.SLOT_HAT, 4716, 4980, 4981, 4982, 4983),
            new Piece(Equipment.SLOT_CHEST, 4720, 4892, 4893, 4894, 4895),
            new Piece(Equipment.SLOT_LEGS, 4722, 4898, 4899, 4900, 4901),
            new Piece(Equipment.SLOT_WEAPON, 4718, 4886, 4887, 4888, 4889),
            new Piece(Equipment.SLOT_AMULET, 12851, 12853)),

    GUTHAN((player, target, hit) -> {
        if (Random.rollDie(4)) {
            target.graphics(398,0 ,0);
            player.incrementHp(hit.damage);
        }
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_HAT, 4724, 4904, 4905, 4906, 4907),
            new Piece(Equipment.SLOT_CHEST, 4728, 4916, 4917, 4918, 4919),
            new Piece(Equipment.SLOT_LEGS, 4730, 4922, 4923, 4924, 4925),
            new Piece(Equipment.SLOT_WEAPON, 4726, 4910, 4911, 4912, 4913)),

    GUTHAN_DAMNED((player, target, hit) -> {
        if (Random.rollDie(4)) {
            target.graphics(398,0 ,0);
            int hp = player.getHp();
            int maxHp = player.getMaxHp();
            int restore = hit.damage;
            int newHp = Math.min(hp + restore, maxHp + 10);
            player.setHp(newHp);
        }
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_HAT, 4724, 4904, 4905, 4906, 4907),
            new Piece(Equipment.SLOT_CHEST, 4728, 4916, 4917, 4918, 4919),
            new Piece(Equipment.SLOT_LEGS, 4730, 4922, 4923, 4924, 4925),
            new Piece(Equipment.SLOT_WEAPON, 4726, 4910, 4911, 4912, 4913),
            new Piece(Equipment.SLOT_AMULET, 12851, 12853)),

    TORAG((player, target, hit) -> {
        if (target instanceof Player && Random.rollDie(4)) {
            target.player.getMovement().drainEnergy(20);
            target.graphics(399);
        }
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_HAT, 4745, 4952, 4953, 4954, 4955),
            new Piece(Equipment.SLOT_CHEST, 4749, 4964, 4965, 4966, 4967),
            new Piece(Equipment.SLOT_LEGS, 4751, 4970, 4971, 4972, 4973),
            new Piece(Equipment.SLOT_WEAPON, 4747, 4958, 4959, 4960, 4961)),

    TORAG_DAMNED(
            new Piece(Equipment.SLOT_HAT, 4745, 4952, 4953, 4954, 4955),
            new Piece(Equipment.SLOT_CHEST, 4749, 4964, 4965, 4966, 4967),
            new Piece(Equipment.SLOT_LEGS, 4751, 4970, 4971, 4972, 4973),
            new Piece(Equipment.SLOT_WEAPON, 4747, 4958, 4959, 4960, 4961),
            new Piece(Equipment.SLOT_AMULET, 12851, 12853)),

    KARIL((player, target, hit) -> {
        if (target instanceof Player && Random.rollDie(4)) {
            target.player.getStats().get(StatType.Agility).drain(20D);
            target.graphics(401);
        }
    }, (player, hit) -> hit.attackStyle.isRanged(),
            new Piece(Equipment.SLOT_HAT, 4732, 4928, 4929, 4930, 4931),
            new Piece(Equipment.SLOT_CHEST, 4736, 4940, 4941, 4942, 4943),
            new Piece(Equipment.SLOT_LEGS, 4738, 4946, 4947, 4948, 4949),
            new Piece(Equipment.SLOT_WEAPON, 4734, 4934, 4935, 4936, 4937)),

    KARIL_DAMNED((player, target, hit) -> {
        if (Random.rollDie(4)) {
            if (target.getHp() <= 0 || hit.attackWeapon == null) {
                return;
            }
            int damage = hit.damage/2;
            if (damage > target.getHp()) {
                damage = target.getHp();
            }
            if (damage <= 0) {
                return;
            }
            target.hit(new Hit(player, hit.attackStyle, hit.attackType).fixedDamage(damage).setAttackWeapon(null).ignoreDefence().ignorePrayer());
        }
    }, (player, hit) -> hit.attackStyle.isRanged(),
            new Piece(Equipment.SLOT_HAT, 4732, 4928, 4929, 4930, 4931),
            new Piece(Equipment.SLOT_CHEST, 4736, 4940, 4941, 4942, 4943),
            new Piece(Equipment.SLOT_LEGS, 4738, 4946, 4947, 4948, 4949),
            new Piece(Equipment.SLOT_WEAPON, 4734, 4934, 4935, 4936, 4937),
            new Piece(Equipment.SLOT_AMULET, 12851, 12853)),

    AHRIM((player, target, hit) -> {
        if (Random.rollDie(4)) {
            if (target instanceof Player) {
                target.player.getStats().get(StatType.Strength).drain(5);
                target.graphics(400);
            } else if (target instanceof NPC) {
                target.npc.getCombat().getStat(StatType.Strength).drain(5);
                target.graphics(400);
            }
        }
    }, (player, hit) -> hit.attackStyle.isMagic(),
            new Piece(Equipment.SLOT_HAT, 4708, 4856, 4857, 4858, 4859),
            new Piece(Equipment.SLOT_CHEST, 4712, 4868, 4869, 4870, 4871),
            new Piece(Equipment.SLOT_LEGS, 4714, 4874, 4875, 4876, 4877),
            new Piece(Equipment.SLOT_WEAPON, 4710, 4862, 4863, 4864, 4865)),

    AHRIM_DAMNED((player, target, hit) -> {
        if (Random.rollDie(4)) {
            hit.boostDamage(0.3);
        }
    }, (player, hit) -> hit.attackStyle.isMagic(),
            new Piece(Equipment.SLOT_HAT, 4708, 4856, 4857, 4858, 4859),
            new Piece(Equipment.SLOT_CHEST, 4712, 4868, 4869, 4870, 4871),
            new Piece(Equipment.SLOT_LEGS, 4714, 4874, 4875, 4876, 4877),
            new Piece(Equipment.SLOT_WEAPON, 4710, 4862, 4863, 4864, 4865),
            new Piece(Equipment.SLOT_AMULET, 12851, 12853)),

    VOID_MELEE((player, target, hit) -> {
        hit.boostDamage(0.1).boostAttack(0.1);
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_CHEST, 8839, 26463),
            new Piece(Equipment.SLOT_LEGS, 8840, 26465),
            new Piece(Equipment.SLOT_HANDS, 8842, 26467),
            new Piece(Equipment.SLOT_HAT, 11665, 26477)),

    VOID_RANGE((player, target, hit) -> {
        hit.boostDamage(0.1).boostAttack(0.1);
    }, (player, hit) -> hit.attackStyle.isRanged(),
            new Piece(Equipment.SLOT_CHEST, 8839, 26463),
            new Piece(Equipment.SLOT_LEGS, 8840, 26465),
            new Piece(Equipment.SLOT_HANDS, 8842, 26467),
            new Piece(Equipment.SLOT_HAT, 11664, 26475)),

    VOID_MAGE((player, target, hit) -> {
        hit.boostAttack(0.45);
    }, (player, hit) -> hit.attackStyle.isMagic(),
            new Piece(Equipment.SLOT_CHEST, 8839, 26463),
            new Piece(Equipment.SLOT_LEGS, 8840, 26465),
            new Piece(Equipment.SLOT_HANDS, 8842, 26467),
            new Piece(Equipment.SLOT_HAT, 11663, 26473)),

    ELITE_VOID_MELEE((player, target, hit) -> {
        hit.boostDamage(0.1).boostAttack(0.1);
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_CHEST, 13072, 26469),
            new Piece(Equipment.SLOT_LEGS, 13073, 26471),
            new Piece(Equipment.SLOT_HANDS, 8842, 26467),
            new Piece(Equipment.SLOT_HAT, 11665, 26477)),

    ELITE_VOID_RANGE((player, target, hit) -> {
        hit.boostDamage(0.125).boostAttack(0.1);
    }, (player, hit) -> hit.attackStyle.isRanged(),
            new Piece(Equipment.SLOT_CHEST, 13072, 26469),
            new Piece(Equipment.SLOT_LEGS, 13073, 26471),
            new Piece(Equipment.SLOT_HANDS, 8842, 26467),
            new Piece(Equipment.SLOT_HAT, 11664, 26475)),

    ELITE_VOID_MAGE((player, target, hit) -> {
        hit.boostAttack(0.45).boostDamage(0.025);
    }, (player, hit) -> hit.attackStyle.isMagic(),
            new Piece(Equipment.SLOT_CHEST, 13072, 26469),
            new Piece(Equipment.SLOT_LEGS, 13073, 26471),
            new Piece(Equipment.SLOT_HANDS, 8842, 26467),
            new Piece(Equipment.SLOT_HAT, 11663, 26473)),

    BERSERKER_NECKLACE((player, target, hit) -> {
        hit.boostDamage(0.20);
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_AMULET, 11128),
            new Piece(Equipment.SLOT_WEAPON, 6523, 6525, 6527, 6528)),

    OBSIDIAN_ARMOUR((player, target, hit) -> {
        hit.boostAttack(0.10).boostDamage(0.10);
    }, (player, hit) -> hit.attackStyle.isMelee(),
            new Piece(Equipment.SLOT_CHEST, 21301),
            new Piece(Equipment.SLOT_LEGS, 21304),
            new Piece(Equipment.SLOT_HAT, 21298),
            new Piece(Equipment.SLOT_WEAPON, 6523, 6525, 6527, 6528)),

    GRACEFUL(new Piece(Equipment.SLOT_HAT, "graceful hood"),
            new Piece(Equipment.SLOT_CHEST, "graceful top"),
            new Piece(Equipment.SLOT_LEGS, "graceful legs"),
            new Piece(Equipment.SLOT_HANDS, "graceful gloves"),
            new Piece(Equipment.SLOT_FEET, "graceful boots"),
            new Piece(Equipment.SLOT_CAPE, def -> def.name.toLowerCase().contains("graceful cape") || def.name.toLowerCase().contains("agility cape") || def.name.toLowerCase().contains("agility master cape")|| def.name.toLowerCase().contains("max cape"))
            ),

    BLOODBARK(new Piece(Equipment.SLOT_HAT, 25413),
            new Piece(Equipment.SLOT_CHEST, 25404),
            new Piece(Equipment.SLOT_LEGS, 25416),
            new Piece(Equipment.SLOT_HANDS, 25407),
            new Piece(Equipment.SLOT_FEET, 25410))
    ;

    BiPredicate<Player, Hit> hitCondition; // must fulfill this condition to even check if the pieces are equipped
    private Effect hitEffect;

    SetEffect(Effect hitEffect, BiPredicate<Player, Hit> hitCondition, Piece... pieces) {
        this.hitCondition = hitCondition;
        this.hitEffect = hitEffect;
        this.pieces = pieces;
    }

    SetEffect(Piece... pieces) {
        this.pieces = pieces;
    }

    private Piece[] pieces;

    public boolean hasPieces(Player player) {
        for (Piece p : pieces)
            if (!p.hasPiece(player))
                return false;
        return true;
    }

    public int numberOfPieces(Player player) {
        int count = 0;
        for (Piece p : pieces)
            if (p.hasPiece(player))
                ++count;
        return count;
    }

    public boolean activate(Player player, Hit hit) {
        return hit.attackStyle != null && hitCondition.test(player, hit) && hasPieces(player);
    }

    public boolean checkAndApply(Player player, Entity target, Hit hit) {
        if (activate(player, hit)) {
            hitEffect.doEffect(player, target, hit);
            return true;
        }
        return false;
    }

    private static class Piece {
        public Piece(int slot, Integer... ids) {
            this.slot = slot;
            this.ids = Arrays.asList(ids);
        }

        public Piece(int slot, String name) {
            this(slot, def -> def.name.toLowerCase().contains(name));
        }

        public Piece(int slot, Predicate<ItemDefinition> predicate) {
            this.ids = ItemDefinition.cached.values().stream().filter(predicate).map(def -> def.id).collect(Collectors.toList());
            this.slot = slot;
        }

        int slot;

        List<Integer> ids;

        boolean hasPiece(Player player) { // can probably add something like ItemDef.setEffect[0
            int id = player.getEquipment().getId(slot);
            return id != -1 && ids.contains(id);
        }

    }

    @FunctionalInterface
    private interface Effect {
        void doEffect(Player player, Entity target, Hit hit);
    }

}
