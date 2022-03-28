package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class StatSpy extends Spell {

    private static final StatType[] SKILLS = {
            StatType.Attack,
            StatType.Hitpoints,
            StatType.Mining,
            StatType.Strength,
            StatType.Agility,
            StatType.Smithing,
            StatType.Defence,
            StatType.Herblore,
            StatType.Fishing,
            StatType.Ranged,
            StatType.Thieving,
            StatType.Cooking,
            StatType.Prayer,
            StatType.Crafting,
            StatType.Firemaking,
            StatType.Magic,
            StatType.Fletching,
            StatType.Woodcutting,
            StatType.Runecrafting,
            StatType.Slayer,
            StatType.Farming,
            StatType.Construction,
            StatType.Hunter
    };

    public StatSpy() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.COSMIC.toItem(2),
                Rune.BODY.toItem(5)
        };
        registerEntity(75, runes, (player, entity) -> {
            if (entity.player == null) {
                player.sendMessage("You can only use this spell on other players.");
                return false;
            }
            sendSpy(player, entity.player);
            player.animate(6293);
            player.graphics(1059);
            entity.player.graphics(736);
            entity.player.sendMessage(player.getName() + " is spying on your stats.");
            player.getStats().addXp(StatType.Magic, 76, true);
            return true;
        });
    }

    private static void sendSpy(Player player, Player target) {
        player.openInterface(InterfaceType.INVENTORY, Interface.STAT_SPY);
        player.getPacketSender().sendString(Interface.STAT_SPY, 1, "" + target.getStats().get(StatType.Attack).currentLevel);
        player.getPacketSender().sendString(Interface.STAT_SPY, 2, "" + target.getStats().get(StatType.Attack).fixedLevel);
        for (int index = 1; index < SKILLS.length; index++) {
            player.getPacketSender().sendString(Interface.STAT_SPY, index * 4 + 1, "" + target.getStats().get(SKILLS[index]).currentLevel);
            player.getPacketSender().sendString(Interface.STAT_SPY, index * 4 + 2, "" + target.getStats().get(SKILLS[index]).fixedLevel);
        }
        player.getPacketSender().sendString(Interface.STAT_SPY, 5, "" + target.getHp());
        player.getPacketSender().sendString(Interface.STAT_SPY, 94, "Player: " + target.getName());
    }
}
