package io.ruin.model.inter.handlers.itemskeptondeath;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.skills.prayer.Prayer;

public class IKODInterface {

    public static void open(Player player) {
        if (player.isVisibleInterface(Interface.ITEMS_KEPT_ON_DEATH))
            player.closeInterface(InterfaceType.MAIN);
        player.openInterface(InterfaceType.MAIN, Interface.ITEMS_KEPT_ON_DEATH);

        boolean protect = player.getPrayer().isActive(Prayer.PROTECT_ITEM);
        boolean skull = player.getCombat().isSkulled();
        boolean wilderness = player.wildernessLevel >= 21;
        player.putTemporaryAttribute(AttributeKey.IKOD_PROTECT_ITEM, protect);
        player.putTemporaryAttribute(AttributeKey.IKOD_SKULL, skull);
        player.putTemporaryAttribute(AttributeKey.IKOD_WILDY, wilderness);
        player.putTemporaryAttribute(AttributeKey.IKOD_PK, false);
        IKODInterfaceFlags flags = new IKODInterfaceFlags(protect, skull, wilderness, false);
        IKODInterfaceUpdater itemKeptInterfaceState = IKODInterfaceUpdater.create(player, flags);
        itemKeptInterfaceState.update(player);
    }

    static {
        /**
         * Custom protect items
         */
        ItemDefinition.get(12931).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); // Serpentine helm (charged)
        ItemDefinition.get(13197).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); // Tanzanite helm (charged)
        ItemDefinition.get(13199).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); // Magma helm (charged)
        ItemDefinition.get(12926).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); //Charged blowpipe (charged)
        ItemDefinition.get(22550).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Craws bow (charged)
        ItemDefinition.get(22547).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Craws bow (uncharged)
        ItemDefinition.get(22545).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Viggora's chainmace (charged)
        ItemDefinition.get(22542).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Viggora's chainmace (uncharged)
        ItemDefinition.get(22555).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Thammaron's sceptre (charged)
        ItemDefinition.get(22552).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Thammaron's sceptre (uncharged)
        ItemDefinition.get(11283).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); //Dragonfire shield (charged)
        ItemDefinition.get(21633).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Ancient wyvern (charged)
        ItemDefinition.get(22002).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Dragonfire ward (charged)
        ItemDefinition.get(12899).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Trident of the swamp (charged)
        ItemDefinition.get(11907).protectValue = (int) Math.min(Integer.MAX_VALUE, 5000 * 1000L); //Trident of the seas (charged)
        ItemDefinition.get(11905).protectValue = (int) Math.min(Integer.MAX_VALUE, 5000 * 1000L); //Trident of the seas (fully charged)
        ItemDefinition.get(12904).protectValue = (int) Math.min(Integer.MAX_VALUE, 10000 * 1000L); //Toxic staff of the dead
        ItemDefinition.get(20714).protectValue = (int) Math.min(Integer.MAX_VALUE, 6000 * 1000L); //Tome of fire
        ItemDefinition.get(19550).protectValue = (int) Math.min(Integer.MAX_VALUE, 15000 * 1000L); //Ring of suffering
        ItemDefinition.get(22613).protectValue = (int) Math.min(Integer.MAX_VALUE, 120000 * 1000L); //Vesta long

        InterfaceHandler.register(Interface.ITEMS_KEPT_ON_DEATH, h -> {
            h.actions[12] = (DefaultAction) (p, option, slot, itemId) -> {
                boolean protect = p.getTemporaryAttribute(AttributeKey.IKOD_PROTECT_ITEM);
                boolean skull = p.getTemporaryAttribute(AttributeKey.IKOD_SKULL);
                boolean wilderness = p.getTemporaryAttribute(AttributeKey.IKOD_WILDY);
                boolean pk = p.getTemporaryAttribute(AttributeKey.IKOD_PK);
                switch (slot) {
                    case 0:
                        protect = !protect;
                        p.putTemporaryAttribute(AttributeKey.IKOD_PROTECT_ITEM, protect);
                        break;
                    case 1:
                        skull = !skull;
                        p.putTemporaryAttribute(AttributeKey.IKOD_SKULL, skull);
                        break;
                    case 2:
                        pk = !pk;
                        p.putTemporaryAttribute(AttributeKey.IKOD_PK, pk);
                        break;
                    case 3:
                        wilderness = !wilderness;
                        p.putTemporaryAttribute(AttributeKey.IKOD_WILDY, wilderness);
                        break;
                }
                IKODInterfaceFlags flags = new IKODInterfaceFlags(protect, skull, wilderness, pk);
                IKODInterfaceUpdater updater = IKODInterfaceUpdater.create(p, flags);
                updater.update(p);
            };
        });
    }
}
