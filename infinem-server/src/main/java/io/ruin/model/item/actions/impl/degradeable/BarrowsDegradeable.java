package io.ruin.model.item.actions.impl.degradeable;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.npc.actions.RepairNPC;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemDropPrompt;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.equipment.EquipAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.actions.Workshop;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/4/2024
 */
public enum BarrowsDegradeable {
    DHAROK_HELM(Items.DHAROKS_HELM, Items.DHAROKS_HELM_100),
    DHAROK_PLATEBODY(Items.DHAROKS_PLATEBODY, Items.DHAROKS_PLATEBODY_100),
    DHAROK_PLATELEGS(Items.DHAROKS_PLATELEGS, Items.DHAROKS_PLATELEGS_100),
    DHAROK_GREATAXE(Items.DHAROKS_GREATAXE, Items.DHAROKS_GREATAXE_100),

    AHRIM_HOOD(Items.AHRIMS_HOOD, Items.AHRIMS_HOOD_100),
    AHRIM_ROBETOP(Items.AHRIMS_ROBETOP, Items.AHRIMS_ROBETOP_100),
    AHRIM_ROBESKIRT(Items.AHRIMS_ROBESKIRT, Items.AHRIMS_ROBESKIRT_100),
    AHRIM_STAFF(Items.AHRIMS_STAFF, Items.AHRIMS_STAFF_100),

    GUTHANS_HELM(Items.GUTHANS_HELM, Items.GUTHANS_HELM_100),
    GUTHANS_PLATEBODY(Items.GUTHANS_PLATEBODY, Items.GUTHANS_PLATEBODY_100),
    GUTHANS_CHAINSKIRT(Items.GUTHANS_CHAINSKIRT, Items.GUTHANS_CHAINSKIRT_100),
    GUTHANS_WARSPEAR(Items.GUTHANS_WARSPEAR, Items.GUTHANS_WARSPEAR_100),

    TORAGS_HELM(Items.TORAGS_HELM, Items.TORAGS_HELM_100),
    TORAGS_PLATEBODY(Items.TORAGS_PLATEBODY, Items.TORAGS_PLATEBODY_100),
    TORAGS_PLATELEGS(Items.TORAGS_PLATELEGS, Items.TORAGS_PLATELEGS_100),
    TORAGS_HAMMERS(Items.TORAGS_HAMMERS, Items.TORAGS_HAMMERS_100),

    KARILS_COIF(Items.KARILS_COIF, Items.KARILS_COIF_100),
    KARILS_LEATHERTOP(Items.KARILS_LEATHERTOP, Items.KARILS_LEATHERTOP_100),
    KARILS_LEATHERSKIRT(Items.KARILS_LEATHERSKIRT, Items.KARILS_LEATHERSKIRT_100),
    KARILS_CROSSBOW(Items.KARILS_CROSSBOW, Items.KARILS_CROSSBOW_100),

    VERACS_HELM(Items.VERACS_HELM, Items.VERACS_HELM_100),
    VERACS_BRASSARD(Items.VERACS_BRASSARD, Items.VERACS_BRASSARD_100),
    VERACS_PLATESKIRT(Items.VERACS_PLATESKIRT, Items.VERACS_PLATESKIRT_100),
    VERACS_FLAIL(Items.VERACS_FLAIL, Items.VERACS_FLAIL_100);

    private final int newId;
    private final int degradeableBaseId;

    public static final int MAX_CHARGES = 20_000;
    private static final int CHARGES_PER_QUARTER = MAX_CHARGES / 4;
    private static final int[] DEGRADE_CHARGES = { CHARGES_PER_QUARTER * 3, CHARGES_PER_QUARTER * 2, CHARGES_PER_QUARTER };

    BarrowsDegradeable(int newId, int degradeableBaseId) {
        this.newId = newId;
        this.degradeableBaseId = degradeableBaseId;
        for (int id : Arrays.asList(newId, degradeableBaseId, degradeableBaseId + 1, degradeableBaseId + 2, degradeableBaseId + 3)) {
            ItemDefinition def = ItemDefinition.get(id);
            if (def.equipSlot == Equipment.SLOT_WEAPON) {
                def.addPreTargetDefendListener((player, item, hit, target) -> {
                    if (hit.attackWeapon != null) removeCharge(item, 1);
                });
            } else {
                def.addPreDefendListener((player, item, hit) -> removeCharge(item, 1));
            }
            ItemAction.registerInventory(id, "check", (player, item) -> player.sendMessage("Your " + ItemDefinition.get(newId).name + " has " + item.getCharges() + " charges remaining."));
            ItemAction.registerEquipment(id, "check", (player, item) -> player.sendMessage("Your " + ItemDefinition.get(newId).name + " has " + item.getCharges() + " charges remaining."));
            ItemDefinition.get(id).custom_values.put("DROP_PROMPT", new ItemDropPrompt("Dropping this item will break it.", this::destroy));
        }
        EquipAction.register(newId, (player -> {
            Item item = player.getEquipment().findItemIgnoringAttributes(newId, false);
            if (item == null) return;
            item.setId(degradeableBaseId);
            item.setCharges(MAX_CHARGES);
        }));
    }

    public void destroy(Item item) {
        item.removeCharges();
        item.setId(degradeableBaseId + 4);
    }

    public void removeCharge(Item item, int amount) {
        int newCharges = item.getCharges() - amount;
        if (newCharges <= 0) {
            destroy(item);
            return;
        }
        item.setCharges(newCharges);
        int index = item.getId() - degradeableBaseId;
        if (index >= DEGRADE_CHARGES.length) return;
        if (newCharges <= DEGRADE_CHARGES[index])
            item.setId(degradeableBaseId + (index + 1));
    }

    private int getStaticRepairCost() {
        int equipSlot = ItemDefinition.get(newId).equipSlot;
        if (equipSlot == Equipment.SLOT_WEAPON) {
            return (int) (100_000);
        } else if (equipSlot == Equipment.SLOT_HAT) {
            return (int) (60_000 );
        } else if (equipSlot == Equipment.SLOT_CHEST) {
            return (int) (90_000);
        } else {
            return (int) (80_000);
        }
    }

    public int getRepairCost(Item item) {
        double multi = 1D - ((double) item.getCharges() / MAX_CHARGES);
        return (int) (getStaticRepairCost() * multi);
    }

    static {
        for (BarrowsDegradeable set : values()) {
            for (int id : Arrays.asList(set.degradeableBaseId, set.degradeableBaseId + 1, set.degradeableBaseId + 2, set.degradeableBaseId + 3, set.degradeableBaseId + 4)) {
                for (int npc : RepairNPC.REPAIR_NPCS) {
                    ItemNPCAction.register(id, npc, (player, item, npc1) -> RepairNPC.repairItem(player, item, set.getRepairCost(item), set.newId));
                }
                ItemObjectAction.register(id, Buildable.ARMOUR_STAND.getBuiltObjects()[0], (player, item, obj) -> Workshop.repair(player, item, set.getRepairCost(item), set.newId));
                RepairNPC.REPAIR_COSTS.put(id, set.getStaticRepairCost());
                ItemDefinition.get(id).custom_values.put("REPAIRED_ID", set.newId);
            }
        }
    }
}
