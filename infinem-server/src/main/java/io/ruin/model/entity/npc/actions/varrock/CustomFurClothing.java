package io.ruin.model.entity.npc.actions.varrock;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Inventory;
import io.ruin.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class CustomFurClothing {

    @Getter
    @AllArgsConstructor
    private enum FurStock {

        POLAR_CAMO_TOP(Items.POLAR_CAMO_TOP, 20, new Item(Items.POLAR_KEBBIT_FUR, 2)),
        POLAR_CAMO_LEGS(Items.POLAR_CAMO_LEGS, 20, new Item(Items.POLAR_KEBBIT_FUR, 2)),

        WOOD_CAMO_TOP(Items.WOOD_CAMO_TOP, 20, new Item(Items.COMMON_KEBBIT_FUR, 2)),
        WOOD_CAMO_LEGS(Items.WOOD_CAMO_LEGS, 20, new Item(Items.COMMON_KEBBIT_FUR, 2)),

        JUNGLE_CAMO_TOP(Items.JUNGLE_CAMO_TOP, 20, new Item(Items.FELDIP_WEASEL_FUR, 2)),
        JUNGLE_CAMO_LEGS(Items.JUNGLE_CAMO_LEGS, 20, new Item(Items.FELDIP_WEASEL_FUR, 2)),

        DESERT_CAMO_TOP(Items.DESERT_CAMO_TOP, 20, new Item(Items.DESERT_DEVIL_FUR, 2)),
        DESERT_CAMO_LEGS(Items.DESERT_CAMO_LEGS, 20, new Item(Items.DESERT_DEVIL_FUR, 2)),

        LARUPIA_TOP(Items.LARUPIA_TOP, 100, new Item(Items.LARUPIA_FUR), new Item(Items.TATTY_LARUPIA_FUR)),
        LARUPIA_LEGS(Items.LARUPIA_LEGS, 100, new Item(Items.LARUPIA_FUR), new Item(Items.TATTY_LARUPIA_FUR)),
        LARUPIA_HAT(Items.LARUPIA_HAT, 500, new Item(Items.LARUPIA_FUR)),

        GRAAHK_TOP(Items.GRAAHK_TOP, 150, new Item(Items.GRAAHK_FUR), new Item(Items.TATTY_GRAAHK_FUR)),
        GRAAHK_LEGS(Items.GRAAHK_LEGS, 150, new Item(Items.GRAAHK_FUR), new Item(Items.TATTY_GRAAHK_FUR)),
        GRAAHK_HEADDRESS(Items.GRAAHK_HEADDRESS, 750, new Item(Items.GRAAHK_FUR)),

        KYATT_TOP(Items.KYATT_TOP, 200, new Item(Items.KYATT_FUR), new Item(Items.TATTY_KYATT_FUR)),
        KYATT_LEGS(Items.KYATT_LEGS, 200, new Item(Items.KYATT_FUR), new Item(Items.TATTY_KYATT_FUR)),
        KYATT_HAT(Items.KYATT_HAT, 1000, new Item(Items.KYATT_FUR)),

        GLOVES_OF_SILENCE(Items.GLOVES_OF_SILENCE, 600, new Item(Items.DARK_KEBBIT_FUR, 2)),

        SPOTTED_CAPE(Items.SPOTTED_CAPE, 400, new Item(Items.SPOTTED_KEBBIT_FUR, 2)),

        SPOTTIER_CAPE(Items.SPOTTIER_CAPE, 800, new Item(Items.DASHING_KEBBIT_FUR, 2));

        FurStock(final int item, final int fee, @NotNull final Item fur) {
            this(item, fee, fur, null);
        }
        private final int item, fee;
        private final Item fur, alternativeFur;

        private static final FurStock[] values = values();
    }

    private static void handle(Player player, int option, int slot, int itemId) {
        FurStock furItem = FurStock.values()[slot];
        int fee = furItem.fee;
        Item requirement = furItem.fur;
        Item alternativeRequirement = furItem.alternativeFur;
        String name = ItemDef.get(itemId).name;
        if (option == 1) {
            player.sendMessage(name + " costs: " + furItem.fee + " coins and " +
                    (alternativeRequirement == null ? "" : "either ") + requirement.getAmount() + " " + requirement.getDef().name.toLowerCase()
                    + (alternativeRequirement == null ? "" : (" or 1 " + alternativeRequirement.getDef().name.toLowerCase())) + ".");
            return;
        }
        if (option == 5) {
            player.sendMessage(ItemDef.get(itemId).examine);
            return;
        }
        Inventory inventory = player.getInventory();
        int amount = option == 2 ? 1 : option == 3 ? 5 : 10;
        for (int i = 0; i < amount; i++) {
            if (inventory.getAmount(995) < fee || (!inventory.contains(requirement.getId(), requirement.getAmount())
                    && (alternativeRequirement == null || !inventory.contains(alternativeRequirement.getId(), alternativeRequirement.getAmount())))) {
                String lowercaseName = name.toLowerCase();
                String prefix = furItem == FurStock.GLOVES_OF_SILENCE || name.endsWith("legs") ? ("a pair of") : Utils.getAOrAn(name);
                player.sendMessage("You need at least " + furItem.fee + " coins and " +
                        (alternativeRequirement == null ? "" : "either ") + requirement.getAmount() + " " + requirement.getDef().name.toLowerCase()
                        + (alternativeRequirement == null ? "" : (" or 1 " + alternativeRequirement.getDef().name.toLowerCase())) + " to purchase " + prefix + " " + lowercaseName + ".");
                return;
            }
            if (inventory.contains(requirement.getId(), requirement.getAmount())) {
                inventory.remove(requirement);
            } else {
                inventory.remove(alternativeRequirement);
            }
            inventory.remove(995, fee);
            inventory.addOrDrop(new Item(furItem.item));
        }
    }

    static {
        InterfaceHandler.register(477, h -> {
            h.actions[6] = (DefaultAction) CustomFurClothing::handle;
        });
        NPCAction.register(2887, "fur clothing", ((player, npc) -> {
            player.openInterface(InterfaceType.MAIN, 477);
            player.getPacketSender().sendAccessMask(477, 6, 0, 19,
                    AccessMasks.combine(AccessMasks.ClickOp1, AccessMasks.ClickOp2, AccessMasks.ClickOp3, AccessMasks.ClickOp4, AccessMasks.ClickOp5));
        }));
    }
}
