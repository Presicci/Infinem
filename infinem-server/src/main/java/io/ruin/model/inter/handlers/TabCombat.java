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
        final int childId = parentId == Interface.FIXED_SCREEN ? 75 : Config.SIDE_PANELS.get(player) == 1 ? 78 : 79;
        player.getPacketSender().sendInterface(interfaceId, parentId, childId, 1);
    }

    private static void openAutocast(Player player, boolean defensive) {
        Integer autocastId = getAutocastId(player);
        if(autocastId == null) {
            player.sendMessage("Your staff can't autocast with that spellbook.");
            return;
        }
        open(player, Interface.AUTOCAST_SELECTION);
        player.getPacketSender().sendAccessMask(Interface.AUTOCAST_SELECTION, 1, 0, 58, 2);
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

    public static void forceResetAutocast(Player player) {
        Config.AUTOCAST.set(player, 0);
        player.getCombat().autocastSpell = null;
        player.getCombat().updateCombatLevel();
    }

    public static void resetAutocast(Player player) {
        Integer autocastId = getAutocastId(player);
        if (player.getCombat().autocastSpell != null) {
            if (autocastId == null) {
                player.getCombat().autocastSpell = null;
                player.getCombat().updateCombatLevel();
            } else if (Config.AUTOCAST_SET.get(player) != autocastId) {
                Config.AUTOCAST.set(player, 0);
                player.getCombat().autocastSpell = null;
                player.getCombat().updateCombatLevel();
            } else {
                player.getCombat().autocastSpell = TargetSpell.AUTOCASTS[Config.AUTOCAST.get(player)];
            }
        } else if (autocastId != null) {
            if (Config.AUTOCAST_SET.get(player) == autocastId) {
                player.getCombat().autocastSpell = TargetSpell.AUTOCASTS[Config.AUTOCAST.get(player)];
            } else {
                Config.AUTOCAST.set(player, 0);
            }
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
     * CS2 script 243
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
                case 27626: // Ancient sceptre
                case 28260: // Blood ancient sceptre
                case 28262: // Ice ancient sceptre
                case 28264: // Smoke ancient sceptre
                case 28266: // Shadow ancient sceptre
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
                case Items.SARADOMIN_STAFF:
                case Items.GUTHIX_STAFF:
                case Items.ZAMORAK_STAFF:
                case Items.STAFF_OF_AIR:
                case Items.STAFF_OF_EARTH:
                case Items.STAFF_OF_WATER:
                case Items.STAFF_OF_FIRE:
                case Items.AIR_BATTLESTAFF:
                case Items.EARTH_BATTLESTAFF:
                case Items.WATER_BATTLESTAFF:
                case Items.FIRE_BATTLESTAFF:
                case Items.STAFF:
                case Items.MAGIC_STAFF:
                case Items.WHITE_MAGIC_STAFF:
                case Items.BATTLESTAFF:
                case Items.MYSTIC_AIR_STAFF:
                case Items.MYSTIC_EARTH_STAFF:
                case Items.MYSTIC_WATER_STAFF:
                case Items.MYSTIC_FIRE_STAFF:
                case Items.DUST_BATTLESTAFF:
                case Items.LAVA_BATTLESTAFF:
                case Items.MIST_BATTLESTAFF:
                case Items.MUD_BATTLESTAFF:
                case Items.SMOKE_BATTLESTAFF:
                case Items.STEAM_BATTLESTAFF:
                case Items.MYSTIC_DUST_STAFF:
                case Items.MYSTIC_LAVA_STAFF:
                case Items.MYSTIC_MIST_STAFF:
                case Items.MYSTIC_MUD_STAFF:
                case Items.MYSTIC_SMOKE_STAFF:
                case Items.MYSTIC_STEAM_STAFF:
                case Items.AHRIMS_STAFF:
                case Items.AHRIMS_STAFF_100:
                case Items.AHRIMS_STAFF_75:
                case Items.AHRIMS_STAFF_50:
                case Items.AHRIMS_STAFF_25:
                case Items.ANCIENT_STAFF:
                case 23342: // 3rd age druidic staff
                case 23363: // Staff of bob the cat
                case Items.THIRD_AGE_WAND:
                case Items.KODAI_WAND:
                case Items.MASTER_WAND:
                case Items.TEACHER_WAND:
                case Items.APPRENTICE_WAND:
                case Items.BEGINNER_WAND:
                case Items.SARADOMIN_CROZIER:
                case Items.GUTHIX_CROZIER:
                case Items.ZAMORAK_CROZIER:
                case Items.ARMADYL_CROZIER:
                case Items.BANDOS_CROZIER:
                case Items.ANCIENT_CROZIER:
                case Items.TOKTZ_MEJ_TAL:
                case Items.LUNAR_STAFF:
                case Items.ROD_OF_IVANDIS_1:
                case Items.ROD_OF_IVANDIS_2:
                case Items.ROD_OF_IVANDIS_3:
                case Items.ROD_OF_IVANDIS_4:
                case Items.ROD_OF_IVANDIS_5:
                case Items.ROD_OF_IVANDIS_6:
                case Items.ROD_OF_IVANDIS_7:
                case Items.ROD_OF_IVANDIS_8:
                case Items.ROD_OF_IVANDIS_9:
                case Items.ROD_OF_IVANDIS_10:
                case 22398: // Ivandis flail
                case 24699: // Blisterwood flail
                case 22368: // Bryo staff (u)
                case 22370: // Bryo staff
                case 27626: // Ancient sceptre
                case 28260: // Blood ancient sceptre
                case 28262: // Ice ancient sceptre
                case 28264: // Smoke ancient sceptre
                case 28266: // Shadow ancient sceptre
                case 12795: // Steam battlestaff (or)
                case 12796: // Mystic steam staff (or)
                case 21198: // Lava battlestaff (or)
                case 21200: // Mystic lava staff (or)
                case 22552: // Thammaron's (u)
                case 22555: // Thammaron's
                    return -1;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}