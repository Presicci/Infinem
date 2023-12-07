package io.ruin.model.skills.cooking.gnome;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/6/2023
 */
public class GnomeCooking {

    private static final Config typeIndex = Config.varpbit(698, false);
    private static final Config recipeIndex = Config.varpbit(2517, false);

    private static void open(Player player, GnomeCookingType type) {
        if (!type.containsRecipeIndex(recipeIndex.get(player))) {
            recipeIndex.setInstant(player, type.getLowestRecipeIndex());
        }
        typeIndex.setInstant(player, type.ordinal());
        player.openInterface(InterfaceType.MAIN, Interface.GNOME_COOKING);
        player.getPacketSender().sendAccessMask(Interface.GNOME_COOKING, 4, 0, 7, AccessMasks.ClickOp1);
    }

    private static GnomeCookingType getCookingType(Player player) {
        return GnomeCookingType.values()[typeIndex.get(player)];
    }

    static {
        for (GnomeCookingType type : GnomeCookingType.values()) {
            ItemAction.registerInventory(type.getPrepareItem(), type.getPrepareOption(), (player, item) -> open(player, type));
        }
        InterfaceHandler.register(Interface.GNOME_COOKING, h -> {
            h.actions[4] = (SlotAction) (player, slot) -> recipeIndex.set(player, getCookingType(player).getRecipeIndexBySlot(slot));
            h.actions[9] = (SimpleAction) player -> GnomeRecipe.create(player, recipeIndex.get(player));
        });
    }
}
