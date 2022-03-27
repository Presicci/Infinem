package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.slayer.SlayerCreature;
import io.ruin.model.stat.StatType;

public class MonsterExamine extends Spell {

    public MonsterExamine() {
        Item[] runes = {
                Rune.ASTRAL.toItem(1),
                Rune.COSMIC.toItem(1),
                Rune.MIND.toItem(1)
        };
        registerEntity(66, runes, (player, entity) -> {
            if (entity.player != null) {
                player.sendMessage("You can't use this on players; it only works on monsters!");
                return false;
            }
            if (entity.npc.getCombat() == null) {
                player.sendMessage("That is not a monster.");
                return false;
            }
            player.examineMonster = entity.npc;
            overview(player);
            player.openInterface(InterfaceType.INVENTORY, Interface.MONSTER_EXAMINE);
            player.getPacketSender().sendString(Interface.MONSTER_EXAMINE, 3, entity.npc.getDef().name);
            player.animate(6293);
            player.graphics(1059);
            entity.npc.graphics(736);
            player.getStats().addXp(StatType.Magic, 61, true);
            return true;
        });
    }

    private static void overview(Player player) {
        NPC examined = player.examineMonster;
        player.getPacketSender().sendString(Interface.MONSTER_EXAMINE, 20, "<col=ffb000>Stats<br><br>" +
                "<col=bebebe>Combat level: " + examined.getDef().combatLevel + "<br>" +
                "<col=bebebe>Hitpoints: " + examined.getMaxHp() + "<br>" +
                "<col=bebebe>Attack: " + (int) examined.getCombat().getLevel(StatType.Attack) + "<br>" +
                "<col=bebebe>Defence: " + (int) examined.getCombat().getLevel(StatType.Defence) + "<br>" +
                "<col=bebebe>Strength: " + (int) examined.getCombat().getLevel(StatType.Strength) + "<br>" +
                "<col=bebebe>Magic: " + (int) examined.getCombat().getLevel(StatType.Magic) + "<br>" +
                "<col=bebebe>Ranged: " + (int) examined.getCombat().getLevel(StatType.Ranged) + "<br>" +
                "<col=bebebe>Max standard hit: " + examined.getCombat().getMaxDamage() + "<br>" +
                "<col=bebebe>Main attack style: " + capitalize(examined.getCombat().getAttackStyle().toString().toLowerCase()) + "<br>"
        );
    }

    private static void offensive(Player player) {
        NPC examined = player.examineMonster;
        player.getPacketSender().sendString(Interface.MONSTER_EXAMINE, 22, "<col=ffb000>Aggressive Stats<br><br>" +
                "<col=bebebe>Attack Rate: " + examined.getCombat().getInfo().attack_ticks + "<br>" +
                "<col=bebebe>Stab: " + (int) examined.getCombat().getBonus(0) + "<br>" +
                "<col=bebebe>Slash: " + (int) examined.getCombat().getBonus(1) + "<br>" +
                "<col=bebebe>Crush: " + (int) examined.getCombat().getBonus(2) + "<br>" +
                "<col=bebebe>Magic: " + (int) examined.getCombat().getBonus(3) + "<br>" +
                "<col=bebebe>Ranged: " + (int) examined.getCombat().getBonus(4) + "<br>"
        );
    }

    private static void defensive(Player player) {
        NPC examined = player.examineMonster;
        player.getPacketSender().sendString(Interface.MONSTER_EXAMINE, 24, "<col=ffb000>Defensive Stats<br><br>" +
                "<col=bebebe>Stab: " + (int) examined.getCombat().getBonus(5) + "<br>" +
                "<col=bebebe>Slash: " + (int) examined.getCombat().getBonus(6) + "<br>" +
                "<col=bebebe>Crush: " + (int) examined.getCombat().getBonus(7) + "<br>" +
                "<col=bebebe>Magic: " + (int) examined.getCombat().getBonus(8) + "<br>" +
                "<col=bebebe>Ranged: " + (int) examined.getCombat().getBonus(9) + "<br>"
        );
    }

    private static void other(Player player) {
        NPC examined = player.examineMonster;
        StringBuilder sb = new StringBuilder("<col=ffb000>Other Attributes<br><br>");
        sb.append("<col=bebebe>Is ").append(SlayerCreature.isSlayerCreature(examined) ? "not " : "").append("a slayer monster.<br>");
        sb.append("<col=bebebe>Can ").append(examined.getCombat().getInfo().poison_immunity ? "Immune to poison." : "Can be poisoned.").append("<br>");
        sb.append("<col=bebebe>Can ").append(examined.getCombat().getInfo().venom_immunity ? "Immune to venom." : "Can be envenomed.").append("<br>");
        if (examined.getDef().dragon) {
            sb.append("<col=bebebe>Draconic.<br>");
        }
        if (examined.getDef().demon) {
            sb.append("<col=bebebe>Demonic.<br>");
        }
        player.getPacketSender().sendString(Interface.MONSTER_EXAMINE, 26, sb.toString());
    }

    static {
        InterfaceHandler.register(Interface.MONSTER_EXAMINE, h -> {
            h.actions[5] = (SimpleAction) MonsterExamine::overview;
            h.actions[8] = (SimpleAction) MonsterExamine::offensive;
            h.actions[11] = (SimpleAction) MonsterExamine::defensive;
            h.actions[14] = (SimpleAction) MonsterExamine::other;
        });
    }

    public static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }

}