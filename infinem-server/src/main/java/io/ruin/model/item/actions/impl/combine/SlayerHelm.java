package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.skills.slayer.SlayerMaster;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlayerHelm {

    public static final int NOSE_PEG = 4168;
    public static final int FACEMASK = 4164;
    public static final int GEM = 4155;
    public static final int EARMUFFS = 4166;
    public static final int BLACK_MASK = 8901;
    public static final int BLACK_MASK_IMBUE = 11784;
    public static final int SPINY_HELM = 4551;
    public static final int SLAYER_HELM = 11864;
    public static final int SLAYER_HELM_IMBUE = 11865;

    private static final List<Integer> MELEE_BOOST_HELMS = Arrays.asList(
            8901, 8903, 8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921, // black masks
            SLAYER_HELM
    );

    private static final List<Integer> ALL_BOOST_HELMS = Arrays.asList(
            11774, 11775, 11776, 11777, 11778, 11779, 11780, 11781, 11782, 11783, 11784, // black masks
            SLAYER_HELM_IMBUE
    );

    static {
        MELEE_BOOST_HELMS.stream().map(ItemDef::get).forEach(def -> def.slayerBoostMelee = true);
        ALL_BOOST_HELMS.stream().map(ItemDef::get).forEach(def -> def.slayerBoostAll = true);
    }


    public static boolean boost(Player player, Entity target, Hit hit) {
        if (hit.attackStyle != null && target.npc != null && (Slayer.isTask(player, target.npc)
                || target.npc.getId() == 7413)) { // npc 7413 = undead combat dummy, always counts as task for max hit
            ItemDef helm = player.getEquipment().getDef(Equipment.SLOT_HAT);
            if (helm == null)
                return false;
            if (hit.attackStyle.isMelee() && (helm.slayerBoostMelee || helm.slayerBoostAll)) {
                hit.boostAttack(0.1667);
                hit.boostDamage(0.1667);
                return true;
            } else if (helm.slayerBoostAll) {
                hit.boostAttack(0.15);
                hit.boostDamage(0.15);
                return true;
            }
        }
        return false;
    }

    private static void makeMask(Player player, boolean imbued) {
        if(Config.SLAYER_UNLOCKED_HELM.get(player) != 1) {
            player.sendMessage("You have not unlocked the ability to make a slayer helm.");
            return;
        }

        ArrayList<Item> items = player.getInventory().collectOneOfEach(NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, imbued ? BLACK_MASK_IMBUE : BLACK_MASK);
        if(items == null) {
            player.sendMessage("You need a nosepeg, facemask, earmuffs, spiny helmet, enchanted gem and a black mask in your inventory in order to make a Slayer helm.");
            return;
        }
        if(player.getStats().get(StatType.Crafting).currentLevel < 55) {
            player.sendMessage("You need a Crafting level of 55 to make a Slayer helm.");
            return;
        }
        items.forEach(Item::remove);
        player.getInventory().add(imbued ? SLAYER_HELM_IMBUE : SLAYER_HELM, 1);
        player.sendMessage("You combine the items into a Slayer helm.");
    }

    private static void disassemble(Player player, Item item, int...itemIds) {
        if(player.getInventory().getFreeSlots() < (itemIds.length - 1)) { //-1 because the item itself is being removed, which makes for 1 more slot.
            player.sendMessage("You don't have enough space to do this.");
            return;
        }
        item.remove();
        for(int itemId : itemIds)
            player.getInventory().add(itemId, 1);
        player.sendMessage("You disassemble your Slayer helm.");
    }

    static {
        /*
         * Item combining
         */
        ItemItemAction.register(NOSE_PEG, FACEMASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, GEM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, EARMUFFS, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(NOSE_PEG, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, GEM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, EARMUFFS, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, FACEMASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(FACEMASK, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(GEM, EARMUFFS, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(GEM, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(GEM, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(GEM, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(EARMUFFS, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(EARMUFFS, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(EARMUFFS, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(SPINY_HELM, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(SPINY_HELM, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));

        /*
         * Disassemble
         */
        ItemAction.registerInventory(SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK));
        ItemAction.registerInventory(SLAYER_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE));

        for (int helm : Arrays.asList(SLAYER_HELM, SLAYER_HELM_IMBUE)) {
            ItemAction.registerEquipment(helm, 4, (player, item) -> SlayerMaster.checkTask(player));
            ItemAction.registerEquipment(helm, "log", (player, item) -> KillCounter.openOwnSlayer(player));
            ItemAction.registerInventory(helm, "check", (player, item) -> SlayerMaster.checkTask(player));
        }
        ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "check", (player, item) -> SlayerMaster.checkTask(player));
        ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "activate", (player, item) -> SlayerMaster.checkTask(player));
        ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "log", (player, item) -> KillCounter.openOwnSlayer(player));
    }

}
