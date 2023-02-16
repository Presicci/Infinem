package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
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
    public static final int SPINY_HELM = 4551;
    public static final int SLAYER_HELM = 11864;
    public static final int SLAYER_HELM_IMBUE = 11865;

    private static final List<Integer> MASKS = Arrays.asList(
            8901, 8903, 8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921
    );

    private static final List<Integer> IMBUED_MASKS = Arrays.asList(
            11774, 11775, 11776, 11777, 11778, 11779, 11780, 11781, 11782, 11783, 11784
    );

    private static final List<Integer> MASK_PARTS = Arrays.asList(4155, 4166, 4551, 4168, 4164);

    static {
        MASKS.stream().map(ItemDef::get).forEach(def -> def.slayerBoostMelee = true);
        IMBUED_MASKS.stream().map(ItemDef::get).forEach(def -> def.slayerBoostAll = true);
        ItemDef.get(SLAYER_HELM).slayerBoostMelee = true;
        ItemDef.get(SLAYER_HELM_IMBUE).slayerBoostAll = true;
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

    private static void makeMask(Player player) {
        if(Config.SLAYER_UNLOCKED_HELM.get(player) != 1) {
            player.sendMessage("You have not unlocked the ability to make a slayer helm.");
            return;
        }
        boolean imbued = true;
        ArrayList<Item> items = player.getInventory().collectOneOfEach(MASK_PARTS.stream().mapToInt(Integer::intValue).toArray());
        Item mask = player.getInventory().findFirst(IMBUED_MASKS.stream().mapToInt(Integer::intValue).toArray());
        if (mask == null) {
            mask = player.getInventory().findFirst(MASKS.stream().mapToInt(Integer::intValue).toArray());
            imbued = false;
        }
        if(items == null || mask == null) {
            player.sendMessage("You need a nosepeg, facemask, earmuffs, spiny helmet, enchanted gem and a black mask to make a Slayer helm.");
            return;
        }
        if(player.getStats().get(StatType.Crafting).currentLevel < 55) {
            player.sendMessage("You need a Crafting level of 55 to make a Slayer helm.");
            return;
        }
        items.forEach(Item::remove);
        mask.setId(imbued ? SLAYER_HELM_IMBUE : SLAYER_HELM);
        player.sendMessage("You combine the items into a Slayer helm.");
    }

    private static void disassemble(Player player, Item item, boolean imbued) {
        if(player.getInventory().getFreeSlots() < (MASK_PARTS.size())) {
            player.sendMessage("You don't have enough space to do this.");
            return;
        }
        if (imbued)
            item.setId(Items.BLACK_MASK_I);
        else
            item.setId(Items.BLACK_MASK);
        for(int itemId : MASK_PARTS)
            player.getInventory().add(itemId, 1);
        player.sendMessage("You disassemble your Slayer helm.");
    }

    static {
        /*
         * Item combining
         */
        for (int index = 0; index < MASK_PARTS.size(); index++) {
            for (int itemId : MASK_PARTS.subList(index + 1, MASK_PARTS.size())) {
                ItemItemAction.register(itemId, MASK_PARTS.get(index), (player, primary, secondary) -> makeMask(player));
            }
            for (int maskId : MASKS) {
                ItemItemAction.register(maskId, MASK_PARTS.get(index), (player, primary, secondary) -> makeMask(player));
            }
            for (int maskId : IMBUED_MASKS) {
                ItemItemAction.register(maskId, MASK_PARTS.get(index), (player, primary, secondary) -> makeMask(player));
            }
        }

        /*
         * Disassemble
         */
        ItemAction.registerInventory(SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, false));
        ItemAction.registerInventory(SLAYER_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, true));

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
