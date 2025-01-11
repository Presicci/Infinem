package io.ruin.model.skills.slayer;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfSlaughter;
import io.ruin.model.item.actions.impl.jewellery.ExpeditiousBracelet;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.MapArea;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.skills.slayer.konar.KonarTaskLocation;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class Slayer {

    /**
     * Final array containing npcIDs for all wilderness bosses.
     * used to roll slayer enchantments
     */
    static private final int[] WILDERNESS_BOSSES = {1};

    /**
     * Method called on each npc kill that the player makes.
     * Checks that the npc is part of the task, if passed, do slayer things.
     *
     * @param player The player that killed the npc.
     * @param npc    The npc killed.
     */
    public static void handleNPCKilled(Player player, NPC npc) {
        final int task = getTask(player);
        int am = getTaskAmount(player);
        final int master = Config.SLAYER_MASTER.get(player);

        SlayerCreature creature = SlayerCreature.lookup(task);
        int bossTask = getBossTask(player);
        SlayerBoss boss = bossTask == 0 ? null : SlayerBoss.lookup(npc.getId());
        if (task > 0 && am > 0 && master > 0
                && ((creature != null && creature.contains(npc))
                || (creature == SlayerCreature.BOSSES && boss != null && Arrays.stream(boss.ids).anyMatch(id -> id == npc.getId())))) {
            int combatLevel = npc.getDef().combatLevel;
            Position dropPosition = npc.getCombat().getDropPosition();
            if (master == SlayerMaster.KRYSTILIA_ID && player.wildernessLevel <= 0) {
                player.sendMessage("<col=FF0000>You must kill your slayer assignment within the wilderness to receive experience!");
                return;
            } else if (master == SlayerMaster.KRYSTILIA_ID) {
                /*
                 * Larran's Key rolling
                 * Formula for drop chance pulled from wiki
                 */
                int chance = (int) Math.floor(0.3 * Math.pow((80 - combatLevel), 2)) + 100;
                if (combatLevel > 80) {
                    chance = (int) Math.floor(-0.18519 * combatLevel) + 115;
                }
                if (MapArea.WILDERNESS_SLAYER_CAVE.inArea(npc.getPosition())) {
                    chance *= 0.85; // 15% boost in wildy slayer cave
                }
                if (chance < 50) {
                    chance = 50;
                }
                if (Random.get(chance) == 1) {
                    new GroundItem(23490, 1).owner(player).position(dropPosition).spawn();
                }

                /*
                 * Slayer enchantment rolling
                 * Formula for drop chance pulled from wiki
                 */
                chance = 320 - (int) Math.floor(npc.getDef().combatInfo.hitpoints * 0.8);
                // Bosses are always 1/30
                if (Arrays.stream(WILDERNESS_BOSSES).anyMatch((b) -> b == npc.getId())) {
                    chance = 30;
                }
                if (Random.get(chance) == 1) {
                    new GroundItem(21257, 1).owner(player).position(dropPosition).spawn();
                }

                /*
                 * No emblem system for now,
                 * if I do add in the future, this
                 * is where it will go.
                 */
            } else if (master == SlayerMaster.KONAR_ID && !KonarTaskLocation.values()[player.slayerLocation].inside(npc.getPosition().copy())) {
                String location = KonarTaskLocation.values()[player.slayerLocation].getName();
                player.sendMessage("<col=FF0000>You must kill your slayer assignment at the " + location + " to receive experience!");
                return;
            } else if (master == SlayerMaster.KONAR_ID) {
                /*
                 * Brimstone Key rolling
                 * Formula for drop chance pulled from wiki
                 */
                int chance = (int) Math.floor(Math.pow((100 - combatLevel), 2) / 5) + 100;
                if (combatLevel > 100) {
                    chance = 120 - (int) Math.floor((double) combatLevel / 5);
                }
                if (npc.getCombat().getInfo().slayer_level > 0) {
                    chance *= 0.8;
                }

                if (Random.get(chance) == 1) {
                    new GroundItem(23083, 1).owner(player).position(dropPosition).spawn();
                }
            }

            final int hp = NPCDefinition.get(npc.getId()).combatInfo.hitpoints;
            final boolean superior = SuperiorSlayer.getSuperior(creature, npc.getId()) != -1 && SuperiorSlayer.getSuperior(creature, npc.getId()) == npc.getId();
            final int xp = superior ? hp * 10 : hp;

            player.getStats().addXp(StatType.Slayer, xp, true);
            player.getTaskManager().doLookupByCategory(TaskCategory.SLAYERKILL, 1, true);

            if (player.getRelicManager().hasRelic(Relic.MONSTER_HUNTER)) {
                player.incrementHp(2);
            }

            int killsCounted = 1;
            if (BraceletOfSlaughter.test(player)) {
                killsCounted = 0;
            } else if (am > 1 && ExpeditiousBracelet.test(player)) {
                killsCounted = 2;
            }
            if (decrementTaskAmount(player, killsCounted, master) > 0) {
                SuperiorSlayer.trySpawn(player, creature, npc);
            }
            Player partner = PartnerSlayer.getPartner(player);
            if (partner != null && partner.getPosition().distance(npc.getPosition()) <= PartnerSlayer.TILE_RANGE) {
                decrementTaskAmount(partner, killsCounted, master);
            }
        }
    }

    public static void resetTask(Player player) {
        setTask(player, 0);
        setTaskAmount(player, 0);
    }

    /**
     * Checks if the npc is part of the player's task.
     *
     * @param player The player that killed the npc.
     * @param npc    The npc being checked.
     * @return True if task is for npc, false if not.
     */
    public static boolean isTask(Player player, NPC npc) {
        final int task = getTask(player);
        int am = getTaskAmount(player);
        final int master = Config.SLAYER_MASTER.get(player);

        SlayerCreature creature = SlayerCreature.lookup(task);

        return task > 0 && am > 0 && master > 0 && creature != null && creature.contains(npc);
    }

    /**
     * Checks whether the player is wearing a slayer helmet.
     *
     * @param player The player being checked.
     * @return True if helmet is worn.
     */
    public static boolean hasSlayerHelmEquipped(Player player) {
        ItemDefinition def = player.getEquipment().getDef(Equipment.SLOT_HAT);
        return def != null && (def.slayerBoostMelee || def.slayerBoostAll);
    }

    /**
     * Checks whether the player is wearing a face mask.
     *
     * @param player The player being checked.
     * @return True if face mask is worn.
     */
    public static boolean hasFaceMask(Player player) {
        Item helm = player.getEquipment().get(Equipment.SLOT_HAT);
        return helm != null && (helm.getId() == Items.FACEMASK || helm.getDef().slayerBoostMelee || helm.getDef().slayerBoostAll);
    }

    public static void sendVarps(Player player) {
        player.getPacketSender().sendVarp(261, getTaskAmount(player));
        player.getPacketSender().sendVarp(262, getTask(player));
        player.getPacketSender().sendVarp(263, getBossTask(player));
        player.getPacketSender().sendVarp(264, player.getAttributeIntOrZero(AttributeKey.STORED_SLAYER_TASK_AMOUNT));
        player.getPacketSender().sendVarp(265, player.getAttributeIntOrZero(AttributeKey.STORED_SLAYER_TASK));
        player.getPacketSender().sendVarp(266, player.getAttributeIntOrZero(AttributeKey.STORED_BOSS_TASK));
    }

    public static void setTask(Player player, int uuid) {
        player.putAttribute(AttributeKey.SLAYER_TASK, uuid);
    }

    public static int getTask(Player player) {
        return player.getAttributeIntOrZero(AttributeKey.SLAYER_TASK);
    }

    public static void setBossTask(Player player, int uuid) {
        player.putAttribute(AttributeKey.BOSS_TASK, uuid);
    }

    public static int getBossTask(Player player) {
        return player.getAttributeIntOrZero(AttributeKey.BOSS_TASK);
    }

    public static void setTaskAmount(Player player, int amount) {
        player.putAttribute(AttributeKey.SLAYER_TASK_AMOUNT, amount);
    }

    public static int getTaskAmount(Player player) {
        return player.getAttributeIntOrZero(AttributeKey.SLAYER_TASK_AMOUNT);
    }

    public static int decrementTaskAmount(Player player, int amount, int master) {
        if (player.getAttributeIntOrZero(AttributeKey.SLAYER_TASK_AMOUNT) <= 0) return 0;
        int am = player.incrementNumericAttribute(AttributeKey.SLAYER_TASK_AMOUNT, -amount);
        if (am <= 0) {
            final int spree = ++player.slayerSpree;
            final int points = SlayerMaster.getTaskPoints(master, spree);
            final int current = Config.SLAYER_POINTS.get(player);

            player.sendMessage("<col=7F00FF>You've completed " + spree + " tasks in a row and received " + points + " points; return to a Slayer Master.");
            Config.SLAYER_POINTS.set(player, current + points);
            player.getTaskManager().doLookupByCategory(TaskCategory.SLAYERTASKCOMPL, 1, true);

            int vipTicketChance = SlayerMaster.getVIPTicketChance(master);
            if (Random.rollDie(vipTicketChance)) {
                if (player.getBank().hasRoomFor(32058, 1)) {
                    player.getBank().add(32058, 1);
                    player.sendMessage(Color.RED.wrap("<shad=0>For completing your task you are awarded a Slayer VIP ticket. It was sent to your bank."));
                } else {
                    player.getInventory().addOrDrop(32058, 1);
                    player.sendMessage(Color.RED.wrap("<shad=0>For completing your task you are awarded a Slayer VIP ticket."));
                }
            }
        }
        SlayerMaster.sendTask(player);
        return am;
    }

    public static int getSkipCost(Player player) {
        int cost = 20;
        if (player.isDiamond()) {
            cost = 0;
        } else if (player.isRuby()) {
            cost = 10;
        }
        return cost;
    }
}
