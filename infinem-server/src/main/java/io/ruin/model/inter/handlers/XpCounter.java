package io.ruin.model.inter.handlers;

import io.ruin.model.entity.shared.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class XpCounter {

    public static void select(Player player, int option) {
        if(option == 1) {
            /**
             * Toggle
             */
            boolean enabled = Config.XP_COUNTER_SHOWN.toggle(player) == 1;
            int childId = player.isFixedScreen() ? 16 : 7;
            if(enabled)
                player.getPacketSender().sendInterface(122, player.getGameFrameId(), childId, 1);
            else
                player.getPacketSender().removeInterface(player.getGameFrameId(), childId);
        } else {
            /**
             * Setup
             */
            player.getPacketSender().sendVarp(638, 0); //selected stat << 23 | selected tracker << 28
            player.getPacketSender().sendClientScript(917, "ii", -1, -1);
            player.openInterface(InterfaceType.MAIN, 137);
            player.getPacketSender().sendAccessMask(137, 50, 1, 3, 2);
            player.getPacketSender().sendAccessMask(137, 51, 1, 3, 2);  // Position
            player.getPacketSender().sendAccessMask(137, 52, 1, 3, 2);  // Size
            player.getPacketSender().sendAccessMask(137, 53, 1, 4, 2);  // Duration
            player.getPacketSender().sendAccessMask(137, 54, 1, 32, 2); // Counter
            player.getPacketSender().sendAccessMask(137, 55, 1, 32, 2); // Progress
            player.getPacketSender().sendAccessMask(137, 56, 1, 8, 2);  // Color
            player.getPacketSender().sendAccessMask(137, 57, 1, 2, 2);  // Group
            player.getPacketSender().sendAccessMask(137, 58, 1, 3, 2);  // Speed
            player.getPacketSender().sendAccessMask(137, 59, 1, 3, 2);  // Fake drops
            player.getPacketSender().sendAccessMask(137, 17, 0, 24, 2);
        }
    }

    private static boolean onTotalMenu(Player player) {
        return Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player) == 24;
    }

    private static StatType statTypeOfCurrentMenu(int slot) {
        switch (slot) {
            case 1:
                return StatType.Attack;
            case 2:
                return StatType.Strength;
            case 3:
                return StatType.Ranged;
            case 4:
                return StatType.Magic;
            case 5:
                return StatType.Defence;
            case 6:
                return StatType.Hitpoints;
            case 7:
                return StatType.Prayer;
            case 8:
                return StatType.Agility;
            case 9:
                return StatType.Herblore;
            case 10:
                return StatType.Thieving;
            case 11:
                return StatType.Crafting;
            case 12:
                return StatType.Runecrafting;
            case 13:
                return StatType.Mining;
            case 14:
                return StatType.Smithing;
            case 15:
                return StatType.Fishing;
            case 16:
                return StatType.Cooking;
            case 17:
                return StatType.Firemaking;
            case 18:
                return StatType.Woodcutting;
            case 19:
                return StatType.Fletching;
            case 20:
                return StatType.Slayer;
            case 21:
                return StatType.Farming;
            case 22:
                return StatType.Construction;
            case 23:
                return StatType.Hunter;
            default:
                return null;
        }
    }

    private static int getCurrentXp(Player player, StatType statType) {
        long currentXp = (onTotalMenu(player) || statType == null) ? player.getStats().totalXp : (long) player.getStats().get(statType).experience;
        if (onTotalMenu(player)) {
            currentXp /= 1000;
        }
        return (int) currentXp;
    }

    private static int getCurrentXp(Player player, int slot) {
        StatType statType = statTypeOfCurrentMenu(slot);
        return getCurrentXp(player, statType);
    }

    private static final int[] SET_BUTTONS = { 30, 38, 42 };

    static {
        InterfaceHandler.register(137, h -> {
            h.actions[51] = (SlotAction) (player, slot) -> Config.XP_COUNTER_POSITION.set(player, slot - 1);
            h.actions[52] = (SlotAction) (player, slot) -> Config.XP_COUNTER_SIZE.set(player, slot - 1);
            h.actions[58] = (SlotAction) (player, slot) -> Config.XP_COUNTER_SPEED.set(player, slot - 1);
            h.actions[53] = (SlotAction) (player, slot) -> Config.XP_COUNTER_DURATION.set(player, slot - 1);
            h.actions[54] = (SlotAction) (player, slot) -> Config.XP_COUNTER_COUNTER.set(player, slot - 1);
            h.actions[55] = (SlotAction) (player, slot) -> Config.XP_COUNTER_PROGRESS_BAR.set(player, slot - 1);
            h.actions[56] = (SlotAction) (player, slot) -> Config.XP_COUNTER_COLOUR.set(player, slot - 1);
            h.actions[57] = (SlotAction) (player, slot) -> Config.XP_COUNTER_GROUP.set(player, slot - 1);
            h.actions[59] = (SlotAction) (player, slot) -> Config.XP_COUNTER_FAKE_DROPS.set(player, slot -1);
            // Open tracker for skill
            h.actions[17] = (SlotAction) (player, slot) -> {
                int realSlot = slot + 1;
                Config.XP_COUNTER_TRACKER_STAT_VIEWING.set(player, realSlot);
                if (onTotalMenu(player)) {
                    realSlot = 0;
                }
                int start = Config.XP_COUNTER_TRACKER_SKILL_START[realSlot].get(player);
                int goal = Config.XP_COUNTER_TRACKER_SKILL_GOAL[realSlot].get(player);
                player.getPacketSender().sendVarp(261, start);
                player.getPacketSender().sendVarp(262, goal);
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_START, start);
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_GOAL, goal);
                int activeValue = goal == -1 ? 1 : (goal == 0 && start == 0) ? 0 : 2;
                Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.set(player, activeValue);
            };
            // Disable tracker for skill
            h.actions[21] = (SlotAction) (player, slot) -> {
                player.getPacketSender().sendVarp(262, 0);
                player.getPacketSender().sendVarp(261, 0);
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_START, 0);
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_GOAL, 0);
                Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.set(player, 0);
            };
            // Enable tracker, middle panel
            h.actions[25] = (SlotAction) (player, slot) -> {
                player.getPacketSender().sendVarp(261, getCurrentXp(player, Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player)));
                player.getPacketSender().sendVarp(262, -1);
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_START, getCurrentXp(player, Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player)));
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_GOAL, -1);
                Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.set(player, 1);
            };
            // Enable goal tracking, bottom panel
            h.actions[33] = (SlotAction) (player, slot) -> {
                StatType statType = statTypeOfCurrentMenu(Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player));
                Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.set(player, 2);
                player.getPacketSender().sendVarp(261, getCurrentXp(player, statType));
                int goalXp;
                if (onTotalMenu(player)) {
                    long xp = player.getStats().totalXp / 1000;
                    long threshold = xp / 10;
                    goalXp = (int)(xp + threshold);
                    if (goalXp > 4600000) {
                        xp = 4600000;
                    }
                } else {
                    goalXp = Stat.xpForLevel(player.getStats().get(statType).currentLevel + 1);
                    if (goalXp >= Stat.xpForLevel(99)) {
                        goalXp = 200000000;
                    }
                }
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_START, getCurrentXp(player, statType));
                player.putTemporaryAttribute(AttributeKey.XP_COUNTER_GOAL, goalXp);
                player.getPacketSender().sendVarp(262, goalXp);
            };
            // Set buttons on trackers
            for (int button : SET_BUTTONS) {
                h.actions[button] = (OptionAction) (player, option) -> {
                    StatType statType = statTypeOfCurrentMenu(Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player));
                    long currentXp = (onTotalMenu(player) || statType == null) ? player.getStats().totalXp : (long) player.getStats().get(statType).experience;
                    if (onTotalMenu(player)) {
                        currentXp /= 1000;
                    }
                    long finalCurrentXp = currentXp;
                    String message = "";
                    boolean convertToXP = false;
                    if (option == 6) {    // Set to level
                        message = "Set tracker start point: (skill level)";
                        convertToXP = true;
                    } else if (option == 7) { // Set to xp
                        if (onTotalMenu(player)) {
                            message = "Set tracker start point in THOUSANDS of XP:";
                        } else {
                            message = "Set tracker start point: (XP value)";
                        }
                    }
                    boolean goal = button == 42;
                    if (message.length() > 1) { // Set to xp or level
                        boolean finalConvertToXP = convertToXP;
                        player.integerInput(message, amount -> {
                            int xp = finalConvertToXP ? Stat.xpForLevel(amount) : amount;
                            if (!goal && xp > finalCurrentXp) {
                                xp = (int) finalCurrentXp;
                            }
                            if (xp > 4600000 && onTotalMenu(player) && button == 42) {
                                xp = 4600000;
                                player.sendMessage("Maximum total experience target is 4.6b XP.");
                            } else if (xp > 200000000 && !onTotalMenu(player) || xp > 4600000 && onTotalMenu(player)) {
                                player.sendMessage(xp + " is not a valid amount of XP.");
                                return;
                            }
                            player.getPacketSender().sendVarp(goal ? 262 : 261, xp);
                            player.putTemporaryAttribute(goal ? AttributeKey.XP_COUNTER_GOAL : AttributeKey.XP_COUNTER_START, xp);
                        });
                    } else {    // Set to current xp
                        int xp = getCurrentXp(player, Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player));
                        player.getPacketSender().sendVarp(goal ? 262 : 261, xp);
                        player.putTemporaryAttribute(goal ? AttributeKey.XP_COUNTER_GOAL : AttributeKey.XP_COUNTER_START, xp);
                    }
                    Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.set(player, (button == 38 || button == 42) ? 2 : 1);
                };
            }
            // Save button
            h.actions[46] = (SlotAction) (player, slot) -> {
                StatType statType = statTypeOfCurrentMenu(Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player));
                long currentXp = (onTotalMenu(player) || statType == null) ? player.getStats().totalXp : (long) player.getStats().get(statType).experience;
                int start = player.getTemporaryAttributeIntOrZero(AttributeKey.XP_COUNTER_START);
                int multiplier = onTotalMenu(player) ? 1000 : 1;
                if ((long) start * multiplier > currentXp) {
                    player.sendMessage("You can't set the start point of your tracker higher than your current XP in the skill.");
                    return;
                }
                int s = Config.XP_COUNTER_TRACKER_STAT_VIEWING.get(player);
                if (onTotalMenu(player)) {
                    s = 0;
                }
                int typeActive = Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.get(player);
                Config.XP_COUNTER_TRACKER_SKILL_START[s].set(player, (typeActive == 1 || typeActive == 2) ? start : 0);
                int goal = player.getTemporaryAttributeIntOrZero(AttributeKey.XP_COUNTER_GOAL);
                if (goal > 4600000 && onTotalMenu(player)) {
                    goal = 4600000;
                }
                Config.XP_COUNTER_TRACKER_SKILL_GOAL[s].set(player, goal);
                Config.XP_COUNTER_TRACKER_STAT_VIEWING.set(player, 0);
            };
            // Discard button
            h.actions[45] = (SlotAction) (player, slot) -> {
                Config.XP_COUNTER_TRACKER_STAT_VIEWING.set(player, 0);
                Config.XP_COUNTER_TRACKER_TYPE_ACTIVE.set(player, 0);
                player.removeTemporaryAttribute(AttributeKey.XP_COUNTER_START);
                player.removeTemporaryAttribute(AttributeKey.XP_COUNTER_GOAL);
            };
        });
    }

}