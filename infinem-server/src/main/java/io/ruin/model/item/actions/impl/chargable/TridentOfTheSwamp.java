package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class TridentOfTheSwamp {

    public static final int CHARGED = 12899;
    public static final int UNCHARGED = 12900;
    public static final int ENHANCED_CHARGED = 22292;
    public static final int ENHANCED_UNCHARGED = 22294;
    private static List<Item> CHARGE_ITEMS;

    static {
        CHARGE_ITEMS = Arrays.asList(Rune.DEATH.toItem(1), Rune.CHAOS.toItem(1), Rune.FIRE.toItem(5), new Item(12934, 1));
        for (Item item : CHARGE_ITEMS) {
            ItemItemAction.register(CHARGED, item.getId(), TridentOfTheSwamp::charge);
            ItemItemAction.register(UNCHARGED, item.getId(), TridentOfTheSwamp::charge);
            ItemItemAction.register(ENHANCED_CHARGED, item.getId(), TridentOfTheSwamp::charge);
            ItemItemAction.register(ENHANCED_UNCHARGED, item.getId(), TridentOfTheSwamp::charge);
        }
        for (int id : Arrays.asList(CHARGED, ENHANCED_CHARGED)) {
            ItemAction.registerInventory(id, "check", TridentOfTheSwamp::check);
            ItemAction.registerEquipment(id, "check", TridentOfTheSwamp::check);
            ItemAction.registerInventory(id, "uncharge", TridentOfTheSwamp::uncharge);
            ItemDef.get(id).addPreTargetDefendListener(TridentOfTheSwamp::consumeCharge);
        }
        ItemAction.registerInventory(CHARGED, "drop", TridentOfTheSwamp::drop);
        ItemAction.registerInventory(ENHANCED_CHARGED, "drop", TridentOfTheSwamp::drop);
        ItemAction.registerInventory(UNCHARGED, "dismantle", TridentOfTheSwamp::dismantle);
        ItemAction.registerInventory(ENHANCED_UNCHARGED, "dismantle", TridentOfTheSwamp::dismantle);
        ItemItemAction.register(TridentOfTheSeas.UNCHARGED, 12932, TridentOfTheSwamp::combine);
        ItemItemAction.register(TridentOfTheSeas.CHARGED, 12932, TridentOfTheSwamp::wrongCombine);
        ItemItemAction.register(TridentOfTheSeas.FULLY_CHARGED, 12932, TridentOfTheSwamp::wrongCombine);
        ItemItemAction.register(TridentOfTheSeas.ENHANCED_UNCHARGED, 12932, TridentOfTheSwamp::combine);
        ItemItemAction.register(TridentOfTheSeas.ENHANCED_CHARGED, 12932, TridentOfTheSwamp::wrongCombine);
    }

    private static void wrongCombine(Player player, Item item, Item item1) {
        player.sendMessage("You can only combine a magic fang with an " + Color.RED.wrap("uncharged") + " trident.");
    }

    private static void dismantle(Player player, Item item) {
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You'll need at least 1 free inventory space to do that.");
            return;
        }
        item.setId(item.getId() == ENHANCED_UNCHARGED ? TridentOfTheSeas.ENHANCED_UNCHARGED : TridentOfTheSeas.UNCHARGED);
        player.getInventory().add(12932, 1);
        player.sendMessage("You remove the magic fang from the trident.");
    }

    private static void combine(Player player, Item trident, Item fang) {
        if (!player.getStats().check(StatType.Crafting, 59, "create a toxic trident")) {
            return;
        }
        trident.setId(trident.getId() == TridentOfTheSeas.ENHANCED_UNCHARGED ? ENHANCED_UNCHARGED : UNCHARGED);
        fang.remove();
        player.sendMessage("You attach the fang to the trident, creating a toxic trident.");
    }

    private static void consumeCharge(Player player, Item staff, Hit hit, Entity entity) {
        if (AttributeExtensions.getCharges(staff) > 0) {
            AttributeExtensions.deincrementCharges(staff, 1);
        }
        if (AttributeExtensions.getCharges(staff) <= 0) {
            staff.setId(staff.getId() == ENHANCED_CHARGED ? ENHANCED_UNCHARGED : UNCHARGED);
            player.sendMessage(Color.RED.wrap("Your Trident of the Swamp has ran out of charges!"));
            player.getCombat().updateWeapon(false);
        }
    }

    private static void drop(Player player, Item item) {
        player.dialogue(new OptionsDialogue("You cannot drop this item. Uncharge it instead?",
                new Option("Yes, uncharge it.", () -> uncharge(player, item)),
                new Option("No.")));
    }

    public static void check(Player player, Item item) {
        player.sendMessage("Your Trident of the Swamp has " + AttributeExtensions.getCharges(item) + " charges remaining.");
    }

    public static void uncharge(Player player, Item item) {
        String lostItem = CHARGE_ITEMS.get(3).getDef().name.toLowerCase();
        player.dialogue(new OptionsDialogue("You will NOT get the " + lostItem + " back.",
                new Option("Okay. uncharge it.", () -> {
                    int charges = AttributeExtensions.getCharges(item);
                    for (Item i : CHARGE_ITEMS) {
                        if (i.getId() == 995) continue;
                        if (!player.getInventory().hasRoomFor(i.getId(), i.getAmount() * charges)) {
                            player.sendMessage("You do not have enough inventory space to do this.");
                            return;
                        }
                    }
                    for (int i = 0; i < CHARGE_ITEMS.size() - 1; i++) { // dont refund the last item
                        Item rune = CHARGE_ITEMS.get(i);
                        player.getInventory().add(rune.getId(), rune.getAmount() * charges);
                    }
                    AttributeExtensions.setCharges(item, 0);
                    item.setId(item.getId() == ENHANCED_CHARGED ? ENHANCED_UNCHARGED : UNCHARGED);
                }),
                new Option("No, don't uncharge it.")
        ));
    }

    public static void charge(Player p, Item trident, Item other) {
        int currentCharges = (trident.getId() == UNCHARGED || trident.getId() == ENHANCED_UNCHARGED) ? 0 : AttributeExtensions.getCharges(trident);
        int maxCharges = (trident.getId() == ENHANCED_CHARGED || trident.getId() == ENHANCED_UNCHARGED) ? 20000 : 2500;
        if (currentCharges >= maxCharges) {
            p.sendMessage("Your trident can't hold any more charges.");
            return;
        }
        int inventoryCharges = CHARGE_ITEMS.stream().mapToInt(i -> p.getInventory().getAmount(i.getId()) / i.getAmount()).min().getAsInt();
        if (inventoryCharges == 0) {
            p.sendMessage("The Trident of the Swamp requires 1 death rune, 1 chaos rune, 5 fire runes and " + NumberUtils.formatNumber(CHARGE_ITEMS.get(3).getAmount()) + " " + CHARGE_ITEMS.get(3).getDef().name.toLowerCase() + " for each charge.");
            return;
        }
        int chargesToAdd = Math.min(inventoryCharges, maxCharges - currentCharges);
        CHARGE_ITEMS.forEach(i -> p.getInventory().remove(i.getId(), i.getAmount() * chargesToAdd));
        p.animate(1979);
        p.graphics(1250, 25, 0);
        trident.setId((trident.getId() == ENHANCED_UNCHARGED || trident.getId() == ENHANCED_CHARGED) ? ENHANCED_CHARGED : CHARGED);
        AttributeExtensions.addCharges(trident, chargesToAdd);
        p.dialogue(new ItemDialogue().one(CHARGED, "You charge your Trident of the Swamp. It now has " + NumberUtils.formatNumber(chargesToAdd+currentCharges) + " charges."));
    }
}