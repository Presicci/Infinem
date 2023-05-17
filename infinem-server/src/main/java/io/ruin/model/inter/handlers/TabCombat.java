package io.ruin.model.inter.handlers;

import io.ruin.model.combat.SetEffect;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class TabCombat {

    static {
        InterfaceHandler.register(Interface.COMBAT_OPTIONS, h -> {
            h.actions[4] = (SimpleAction) p -> p.getCombat().changeAttackSet(0);
            h.actions[8] = (SimpleAction) p -> p.getCombat().changeAttackSet(1);
            h.actions[12] = (SimpleAction) p -> p.getCombat().changeAttackSet(2);
            h.actions[16] = (SimpleAction) p -> p.getCombat().changeAttackSet(3);
            h.actions[21] = (SimpleAction) p -> openAutocast(p, true);
            h.actions[26] = (SimpleAction) p -> openAutocast(p, false);
            h.actions[30] = (SimpleAction) Config.AUTO_RETALIATE::toggle;
            h.actions[36] = (SimpleAction) p -> p.getCombat().toggleSpecial();
        });
        InterfaceHandler.register(Interface.AUTOCAST_SELECTION, h -> {
            h.actions[1] = (SlotAction) TabCombat::selectAutocast;
        });
    }

    private static void open(Player player, int interfaceId) {//meehhhh (Todo better interface positioning system..)
        final int parentId = player.getGameFrameId();// Do we need 67?
        final int childId = parentId == Interface.FIXED_SCREEN ? 75 : 79;
        player.getPacketSender().sendInterface(interfaceId, parentId, childId, 1);
    }

    private static void openAutocast(Player player, boolean defensive) {
        Integer autocastId = getAutocastId(player);
        if(autocastId == null) {
            player.sendMessage("Your staff can't autocast with that spellbook.");
            return;
        }
        open(player, Interface.AUTOCAST_SELECTION);
        player.getPacketSender().sendAccessMask(Interface.AUTOCAST_SELECTION, 1, 0, 52, 2);
        Config.AUTOCAST_SET.set(player, autocastId);
        Config.DEFENSIVE_CAST.set(player, defensive ? 1 : 0);
    }

    public static void updateAutocast(Player player, boolean login) {
        if(login) {
            int index = Config.AUTOCAST.get(player);
            player.getCombat().autocastSpell = TargetSpell.AUTOCASTS[index];
        } else {
            if(player.isVisibleInterface(Interface.AUTOCAST_SELECTION))
                open(player, Interface.COMBAT_OPTIONS);
            resetAutocast(player);
        }
    }

    public static void resetAutocast(Player player) {
        if(player.getCombat().autocastSpell != null) {
            player.getCombat().autocastSpell = null;
            Config.AUTOCAST.set(player, 0);
            player.getCombat().updateCombatLevel();
        }
    }

    private static void selectAutocast(Player player, int slot) {
        if(slot < 0 || slot >= TargetSpell.AUTOCASTS.length)
            return;
        if(slot != 0) {
            player.getCombat().autocastSpell = TargetSpell.AUTOCASTS[slot];
            Config.AUTOCAST.set(player, slot);
        }
        open(player, Interface.COMBAT_OPTIONS);
        player.getCombat().updateWeapon(true);
        player.getCombat().updateCombatLevel();
    }

    /*
     * https://oldschool.runescape.wiki/w/Autocast
     */
    private static Integer getAutocastId(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_WEAPON);
        int staffId = item == null ? -1 : player.getEquipment().getId(Equipment.SLOT_WEAPON);
        if (staffId == -1) //shouldn't happen
            return null;
        if (SpellBook.ANCIENT.isActive(player)) {
            switch (staffId) {
                case Items.AHRIMS_STAFF:
                case Items.AHRIMS_STAFF_25:
                case Items.AHRIMS_STAFF_50:
                case Items.AHRIMS_STAFF_75:
                case Items.AHRIMS_STAFF_100:
                    return SetEffect.AHRIM_DAMNED.hasPieces(player) ? 4675 : null;
                case Items.ANCIENT_STAFF:
                case Items.MASTER_WAND:
                case Items.KODAI_WAND:
                case 24422: // Nightmare staff
                case 24424: // Volatile nightmare staff
                case 24425: // Eldritch nightmare staff
                    return 4675;
                default:
                    return null;
            }
        } else if (SpellBook.ARCEUUS.isActive(player)) {
            switch (staffId) {
                case Items.SKULL_SCEPTRE:
                case 21276: // Skull sceptre (i)
                case Items.MASTER_WAND:
                case Items.KODAI_WAND:
                case Items.AHRIMS_STAFF:
                case Items.AHRIMS_STAFF_25:
                case Items.AHRIMS_STAFF_50:
                case Items.AHRIMS_STAFF_75:
                case Items.AHRIMS_STAFF_100:
                case Items.SLAYERS_STAFF:
                case 21255: // Slayer's staff (e)
                case Items.STAFF_OF_THE_DEAD:
                case Items.TOXIC_STAFF_OF_THE_DEAD:
                    return 9013;
                default:
                    return null;
            }
        } else if (SpellBook.MODERN.isActive(player)) {
            switch (staffId) {
                case Items.STAFF_OF_THE_DEAD:
                case Items.TOXIC_STAFF_OF_THE_DEAD:
                    return 11791;
                case Items.IBANS_STAFF:
                case Items.IBANS_STAFF_U:
                    return 1409;
                case Items.SLAYERS_STAFF:
                case 21255: // Slayer's staff (e)
                    return 4170;
                case Items.STAFF_OF_LIGHT:
                    return 22296;
                case 24144: // Staff of balance
                    return 24144;
                case Items.VOID_KNIGHT_MACE:
                    return 8841;
                case Items.SKULL_SCEPTRE:
                case 21276: // Skull sceptre (i)
                    return null;
                default:
                    return -1;
            }
        } else {
            return null;
        }
    }

}