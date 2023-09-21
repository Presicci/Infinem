package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
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
    SLAYER(StatType.Slayer, false, false),
    FARMING(StatType.Farming, false, false),
    MINING(StatType.Mining, false, false),
    SMITHING(StatType.Smithing, false, false),
    FISHING(StatType.Fishing, false, false),
    COOKING(StatType.Cooking, false, false),
    FIRE_MAKING(StatType.Firemaking, false, false),
    WOODCUTTING(StatType.Woodcutting, false, false),
    FLETCHING(StatType.Fletching, false, false),
    CONSTRUCTION(StatType.Construction, false, false),
    HUNTER(StatType.Hunter, false, false);

    private final StatType statType;
    private final boolean combat;
    private final boolean disabled;

    public static final int SKILL_LAMP = 2528;

    static {
        ItemAction.registerInventory(SKILL_LAMP, "rub", (player, item) -> {
            player.openInterface(InterfaceType.MAIN, Interface.SKILL_LAMP);
            player.getPacketSender().sendString(Interface.SKILL_LAMP, 25, "Choose the stat you wish to be advanced!");
        });

        InterfaceHandler.register(Interface.SKILL_LAMP, h -> {
            h.actions[0] = (SlotAction) (player, slot) -> {
                SkillLamp skill = SkillLamp.values()[slot];
                String skillName = StringUtils.getFormattedEnumName(skill);
                if (skill.combat) {
                    player.sendMessage(Color.DARK_RED.wrap("You can't add experience to a combat stat!"));
                    return;
                }
                if (skill.disabled) {
                    player.sendMessage(Color.DARK_RED.wrap(skillName + " is currently disabled!"));
                    return;
                }
                Item lamp = player.getInventory().findItem(SKILL_LAMP);
                if (lamp == null)
                    return;
                int experience = Random.get(25000, 50000);
                player.closeInterface(InterfaceType.MAIN);
                lamp.remove();
                player.getStats().addXp(skill.statType, experience, false);
                player.sendMessage(Color.DARK_GREEN.wrap("You have been awarded " + NumberUtils.formatNumber(experience) + " " + skillName + " experience."));
            };
        });
    }
}
