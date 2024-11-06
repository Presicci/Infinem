package io.ruin.model.inter.handlers;

import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.SetEffect;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.SkillingOutfit;

import java.text.DecimalFormat;

public class EquipmentStats {

    public static final int STAB_ATTACK = 0, SLASH_ATTACK = 1, CRUSH_ATTACK = 2, MAGIC_ATTACK = 3, RANGE_ATTACK = 4;

    public static final int STAB_DEFENCE = 5, SLASH_DEFENCE = 6, CRUSH_DEFENCE = 7, MAGIC_DEFENCE = 8, RANGE_DEFENCE = 9;

    public static final int MELEE_STRENGTH = 10, RANGED_STRENGTH = 11, MAGIC_DAMAGE = 12, PRAYER = 13;

    public static final int UNDEAD = 14, SLAYER = 15;

    public static final String[] STAT_NAMES = { "Stab Attack", "Slash Attack", "Crush Attack", "Magic Attack", "Range Attack",
            "Stab Defence", "Slash Defence", "Crush Defence", "Magic Defence", "Range Defence",
            "Melee Strength", "Ranged Strength", "Magic Damage", "Prayer", "Undead", "Slayer"};

    public static String getNameForIndex(int index) {
        return STAT_NAMES[index];
    }

    public static void open(Player player) {
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, Interface.EQUIPMENT_STATS);
        player.openInterface(InterfaceType.INVENTORY, Interface.EQUIPMENT_STATS_INVENTORY);
        player.getPacketSender().sendClientScript(149, "IviiiIsssss", 5570560, 93, 4, 7, 1, -1, "Equip", "", "", "", "");
        player.getPacketSender().sendAccessMask(Interface.EQUIPMENT_STATS_INVENTORY, 0, 0, 27, 1180674);
        update(player, Interface.EQUIPMENT_STATS, 24);
    }

    /**
     * @author Jire
     */
    public enum Stat {
        STAB_ATTACK("Stab"),
        SLASH_ATTACK("Slash"),
        CRUSH_ATTACK("Crush"),
        MAGIC_ATTACK("Magic"),
        RANGE_ATTACK("Range", false, true),

        STAB_DEFEND("Stab"),
        SLASH_DEFEND("Slash"),
        CRUSH_DEFEND("Crush"),
        MAGIC_DEFEND("Magic"),
        RANGE_DEFEND("Range", false, true),

        MELEE_STRENGTH("Melee strength"),
        RANGED_STRENGTH("Ranged strength"),
        MAGIC_DAMAGE("Magic damage", true),
        PRAYER("Prayer", false, true),

        UNDEAD("Undead", true),
        SLAYER("Slayer", true);

        public final String stringName;
        public final boolean percent;
        public final boolean skipChild;

        public final String string;

        Stat(String stringName, boolean percent, boolean skipChild) {
            this.stringName = stringName;
            this.percent = percent;
            this.skipChild = skipChild;

            string = stringName + ": ";
        }

        Stat(String stringName, boolean percent) {
            this(stringName, percent, false);
        }

        Stat(String stringName) {
            this(stringName, false);
        }

        public void sendString(Player player, int bonus, int interfaceID, int childID) {
            player.getPacketSender().sendString(interfaceID, childID, string + asBonus(bonus, percent));
        }
    }

    public static void update(Player player, int interfaceID, int startChildID) {
        int[] bonuses = player.getEquipment().bonuses;
        Stat[] stats = Stat.values();
        int childID = startChildID;
        for (int i = 0; i < bonuses.length; i++) {
            int bonus = bonuses[i];
            Stat stat = stats[i];
            stat.sendString(player, bonus, interfaceID, childID);
            childID += stat.skipChild ? 2 : 1;
        }
        player.getPacketSender().sendWeight((int) (player.getInventory().weight + player.getEquipment().weight));
        DecimalFormat df = new DecimalFormat("0.0#");
        int baseAttackSpeed = player.getCombat().weaponType.attackTicks;
        int actualAttackSpeed = player.getCombat().getAttackType() == AttackType.RAPID_RANGED ? baseAttackSpeed - 1 : baseAttackSpeed;
        player.getPacketSender().sendString(84, 53, "Base: " + df.format(baseAttackSpeed * 0.6));
        player.getPacketSender().sendString(84, 54, "Actual: " + df.format(actualAttackSpeed * 0.6));
        Config.EQUIPMENT_SET_BONUS.set(player, getSetBonusIndex(player));
    }

    // Enum 3047
    public static int getSetBonusIndex(Player player) {
        Config.GRACEFUL_SET_PIECES.set(player, SkillingOutfit.GRACEFUL.countSetPiecesForInterface(player));
        Config.ZEALOT_SET_PIECES.set(player, SkillingOutfit.ZEALOT.countSetPiecesForInterface(player));
        Config.SWAMPBARK_SET_PIECES.set(player, SetEffect.SWAMPBARK.numberOfPieces(player));
        Config.BLOODBARK_SET_PIECES.set(player, SetEffect.BLOODBARK.numberOfPieces(player));
        // 7 - Inquis
        if (SkillingOutfit.GRACEFUL.hasPieces(player)) return 8;
        if (SetEffect.OBSIDIAN_ARMOUR.hasPieces(player)) return 9;
        if (SetEffect.VOID_MELEE.hasPieces(player)) return 10;
        if (SetEffect.VOID_RANGE.hasPieces(player)) return 11;
        if (SetEffect.ELITE_VOID_RANGE.hasPieces(player)) return 12;
        if (SetEffect.VOID_MAGE.hasPieces(player)) return 13;
        if (SetEffect.ELITE_VOID_MAGE.hasPieces(player)) return 14;
        // 15 - Justiciar
        if (SkillingOutfit.ANGLER.hasPieces(player)) return 16;
        if (SkillingOutfit.LUMBERJACK.hasPieces(player)) return 17;
        if (SkillingOutfit.PROSPECTOR.hasPieces(player)) return 18;
        // 19 - Farmer
        if (SkillingOutfit.PYROMANCER.hasPieces(player)) return 20;
        // 21 - Shayzien
        if (SkillingOutfit.ROGUE.hasPieces(player)) return 22;
        if (SkillingOutfit.CONSTRUCTION.hasPieces(player)) return 23;
        // 24 - Crystal armour
        if (SetEffect.SWAMPBARK.hasPieces(player)) return 25;
        if (SetEffect.BLOODBARK.hasPieces(player)) return 26;
        if (SkillingOutfit.ZEALOT.hasPieces(player)) return 27;
        // 28 - Spirit angler
        // 29 - Virtus robes
        // 30 - Raiments of the eye
        // 31 - Smith's uniform
        // 32 - Statius's equipment
        // 33 - Vesta's equipment
        // 34 - Morrigan's equipment
        // 35 - Zuriel's equipment
        // 36 - Statius's Corrupt Equipment
        // 37 - Vesta's Corrupt Equipment
        // 38 - Morrigan's Corrupt Equipment
        // 39 - Zuriel's Corrupt Equipment
        // 40 - Bloodrager Effect
        // 41 - Frostweaver Effect
        // 42 - Frostweaver Effect Set
        // 43 - Eclipse Effect
        // 44 - Hunter's Armour 60%
        // 45 - Hunter's Armour 40%
        // 46 - Hunter's Armour 20%
        // 47 - Guild Hunter Outfit
        if (SetEffect.AHRIM_DAMNED.hasPieces(player)) return 2001;
        if (SetEffect.DHAROK_DAMNED.hasPieces(player)) return 2002;
        if (SetEffect.GUTHAN_DAMNED.hasPieces(player)) return 2003;
        if (SetEffect.KARIL_DAMNED.hasPieces(player)) return 2004;
        if (SetEffect.TORAG_DAMNED.hasPieces(player)) return 2005;
        if (SetEffect.VERAC_DAMNED.hasPieces(player)) return 2006;
        if (SetEffect.AHRIM.hasPieces(player)) return 1;
        if (SetEffect.DHAROK.hasPieces(player)) return 2;
        if (SetEffect.GUTHAN.hasPieces(player)) return 3;
        if (SetEffect.KARIL.hasPieces(player)) return 4;
        if (SetEffect.TORAG.hasPieces(player)) return 5;
        if (SetEffect.VERAC.hasPieces(player)) return 6;
        return 0;
    }

    public static String asBonus(int value, boolean percent) {
        String s = (value >= 0 ? "+" : "") + value;
        if(percent)
            s += "%";
        return s;
    }

    /**
     * Handler
     */

    static {
        InterfaceHandler.register(Interface.EQUIPMENT_STATS, h -> {
            h.actions[10] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_HAT);
            h.actions[11] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_CAPE);
            h.actions[12] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_AMULET);
            h.actions[13] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_WEAPON);
            h.actions[14] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_CHEST);
            h.actions[15] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_SHIELD);
            h.actions[16] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_LEGS);
            h.actions[17] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_HANDS);
            h.actions[18] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_FEET);
            h.actions[19] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_RING);
            h.actions[20] = (OptionAction) (player, option) -> TabEquipment.itemAction(player, option, Equipment.SLOT_AMMO);
        });
        InterfaceHandler.register(Interface.EQUIPMENT_STATS_INVENTORY, h -> {
            h.actions[0] = (DefaultAction) (player, option, slot, itemId) -> {
                Item item = player.getInventory().get(slot, itemId);
                if(item == null)
                    return;
                if(option == 1)
                    player.getEquipment().equip(item);
                else
                    item.examine(player);
            };
        });
    }

}
