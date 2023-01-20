package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Tuple;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/19/2023
 */
@AllArgsConstructor
public enum InfernalTools {

    INFERNAL_AXE(6739, 13241, 13242, new Tuple<>(StatType.Woodcutting, StatType.Firemaking), new Tuple<>(61, 85)),
    INFERNAL_HARPOON(21028, 21031, 21033, new Tuple<>(StatType.Fishing, StatType.Cooking), new Tuple<>(75, 85)),
    INFERNAL_PICKAXE(11920, 13243, 13244, new Tuple<>(StatType.Mining, StatType.Smithing), new Tuple<>(61, 85));

    private final int dragonTool, infernalTool, unchargedTool;
    private final Tuple<StatType, StatType> stats;
    private final Tuple<Integer, Integer> statRequirements;

    private static final int SMOULDERING_STONE = 13233;
    private static final int MAX_CHARGES = 5000;
    private static final int EMOTE = 4513, GRAPHIC = 1240;

    static {
        for (InfernalTools tool : InfernalTools.values()) {
            ItemAction.registerInventory(tool.infernalTool, "check", InfernalTools::check);
            ItemAction.registerEquipment(tool.infernalTool, "check", InfernalTools::check);
            ItemItemAction.register(tool.dragonTool, SMOULDERING_STONE, tool::create);
            ItemItemAction.register(tool.unchargedTool, SMOULDERING_STONE, tool::charge);
            ItemItemAction.register(tool.unchargedTool, tool.dragonTool, tool::charge);
        }
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your " + item.getDef().name + " currently has " + NumberUtils.formatNumber(item.getAttributeInt(AttributeTypes.CHARGES)) + " charges.");
    }

    private void charge(Player player, Item tool, Item itemUsedToCharge) {
        int currentCharges = AttributeExtensions.getCharges(tool);
        if (currentCharges > 0) {
            System.err.println("Tried to charge while tool already had charges! player: " + player.getName() + ", tool: " + this);
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sacrifice the " + itemUsedToCharge.getDef().name + " to add 5,000 charges to your " + ItemDef.get(infernalTool).name + "?", itemUsedToCharge.getId(), 1, () -> {
                    tool.setId(infernalTool);
                    tool.putAttribute(AttributeTypes.CHARGES, MAX_CHARGES);
                    itemUsedToCharge.remove(1);
                    player.dialogue(new ItemDialogue().one(tool.getId(), "Your " + tool.getDef().name + " now holds " + NumberUtils.formatNumber(AttributeExtensions.getCharges(tool)) + " charges."));
                })
        );
    }

    public void removeCharge(Player player) {
        Item tool = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (tool == null || tool.getId() != infernalTool)
            tool = player.getInventory().findItemIgnoringAttributes(infernalTool, false);
        if ((tool != null && tool.getId() == infernalTool)) {
            int currentCharges = AttributeExtensions.getCharges(tool);
            if (currentCharges <= 0) {
                System.err.println("Tried to remove charge with no available charges! player: " + player.getName() + ", tool: " + this.toString());
                return;
            }
            AttributeExtensions.deincrementCharges(tool, 5000);
            if (AttributeExtensions.getCharges(tool) <= 0) {
                player.sendMessage("Your " + tool.getDef().name + " has run out of charges.");
                tool.setId(unchargedTool);
            }
        }
    }

    public boolean hasCharge(Player player) {
        Item tool = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (tool == null || tool.getId() != infernalTool)
            tool = player.getInventory().findItemIgnoringAttributes(infernalTool, false);
        if ((tool != null && tool.getId() == infernalTool)) {
            return AttributeExtensions.getCharges(tool) > 0;
        }
        return false;
    }

    private void create(Player player, Item tool, Item smoulderingStone) {
        if (player.getStats().get(stats.first()).currentLevel < statRequirements.first()) {
            player.dialogue(new MessageDialogue("You need a " + stats.first() + " level of " +statRequirements.first() + " to create an " + ItemDef.get(infernalTool).name + "."));
            return;
        }
        if (player.getStats().get(stats.second()).currentLevel < statRequirements.second()) {
            player.dialogue(new MessageDialogue("You need a " + stats.second() + " level of " +statRequirements.second() + " to create an " + ItemDef.get(infernalTool).name + "."));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Convert your " + tool.getDef().name + " into an " + ItemDef.get(infernalTool).name + "?<br>This process is irreversible.", infernalTool, 1, () -> {
                    smoulderingStone.remove();
                    tool.setId(infernalTool);
                    tool.putAttribute(AttributeTypes.CHARGES, MAX_CHARGES);
                    player.animate(EMOTE);
                    player.graphics(GRAPHIC);
                    player.getStats().addXp(stats.first(), 200, true);
                    player.getStats().addXp(stats.second(), 350, true);
                    player.dialogue(new ItemDialogue().one(infernalTool, "You infuse the smouldering stone into the " + ItemDef.get(dragonTool).name + " to make an " + ItemDef.get(infernalTool).name + "."));
                })
        );
    }
}
