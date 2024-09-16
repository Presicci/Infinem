package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Items;
import io.ruin.utility.Color;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SkillLamp {

    ATTACK(StatType.Attack, true, false),
    STRENGTH(StatType.Strength, true, false),
    RANGED(StatType.Ranged, true, false),
    MAGIC(StatType.Magic, true, false),
    DEFENCE(StatType.Defence, true, false),
    HITPOINTS(StatType.Hitpoints, true, false),
    PRAYER(StatType.Prayer, true, false),
    AGILITY(StatType.Agility, false, false),
    HERBLORE(StatType.Herblore, false, false),
    THIEVING(StatType.Thieving, false, false),
    CRAFTING(StatType.Crafting, false, false),
    RUNECRAFTING(StatType.Runecrafting, false, false),
    MINING(StatType.Mining, false, false),
    SMITHING(StatType.Smithing, false, false),
    FISHING(StatType.Fishing, false, false),
    COOKING(StatType.Cooking, false, false),
    FIRE_MAKING(StatType.Firemaking, false, false),
    WOODCUTTING(StatType.Woodcutting, false, false),
    FLETCHING(StatType.Fletching, false, false),
    SLAYER(StatType.Slayer, false, false),
    FARMING(StatType.Farming, false, false),
    CONSTRUCTION(StatType.Construction, false, false),
    HUNTER(StatType.Hunter, false, false);

    private final StatType statType;
    private final boolean combat;
    private final boolean disabled;

    public static final int[] SKILL_LAMPS = { 2528, Items.BOOK_OF_KNOWLEDGE, 28800 };

    private static void activate(Player player, Item item) {
        player.getPacketSender().sendVarp(261, 1);
        player.openInterface(InterfaceType.MAIN, Interface.SKILL_LAMP);
        player.getPacketSender().sendString(Interface.SKILL_LAMP, 25, "Choose the stat you wish to be advanced!");
        player.putTemporaryAttribute("LAMP", item.getId());
    }

    static {
        for (int lamp : SKILL_LAMPS) {
            ItemAction.registerInventory(lamp, "rub", SkillLamp::activate);
            ItemAction.registerInventory(lamp, "read", SkillLamp::activate);
        }

        InterfaceHandler.register(Interface.SKILL_LAMP, h -> {
            h.actions[0] = (SlotAction) (player, slot) -> {
                SkillLamp skill = SkillLamp.values()[slot];
                String skillName = StringUtils.getFormattedEnumName(skill);
                if (skill.disabled) {
                    player.sendMessage(Color.DARK_RED.wrap(skillName + " is currently disabled!"));
                    return;
                }
                int lampId = player.getTemporaryAttributeIntOrZero("LAMP");
                Item lamp = player.getInventory().findItem(lampId);
                if (lamp == null)
                    return;
                int experience = player.getStats().get(skill.statType).fixedLevel * (lampId == Items.BOOK_OF_KNOWLEDGE ? 50 : 200);     // OSRS is 10x skill level
                player.closeInterface(InterfaceType.MAIN);
                lamp.remove();
                player.getStats().addXp(skill.statType, experience, false);
                player.sendMessage(Color.DARK_GREEN.wrap("You have been awarded " + NumberUtils.formatNumber(experience) + " " + skillName + " experience."));
            };
            h.closedAction = (player, i) -> player.removeTemporaryAttribute("LAMP");
        });
    }
}
