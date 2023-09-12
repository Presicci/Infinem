package io.ruin.model.entity.player.killcount;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;

public class KillCounter {

    static {
        /* Interface handler */
        InterfaceHandler.register(Interface.KILL_COUNTER, h -> {
            h.actions[16] = (SlotAction) (p, slot) -> p.putTemporaryAttribute("KILL_COUNTER_SLOT", slot);
            h.actions[25] = (SimpleAction) KillCounter::resetStreak;
            h.closedAction = (player, integer) -> {
                player.removeTemporaryAttribute("KILL_COUNTER_LIST");
                player.removeTemporaryAttribute("KILL_COUNTER_SLOT");
            };
        });
    }

    private static void resetStreak(Player player) {
        String list = player.getTemporaryAttribute("KILL_COUNTER_LIST");
        int slot = player.getTemporaryAttributeOrDefault("KILL_COUNTER_SLOT", -1);
        if (list == null || slot < 0 || slot >= (list.equals("BOSS") ? BossKillCounter.values().length : SlayerKillCounter.values().length)) {
            return;
        }
        String name;
        if (list.equals("BOSS")) {
            BossKillCounter kc = BossKillCounter.values()[slot];
            name = kc.name();
            player.sendMessage("Your " +  kc.name + " streak has been reset.");
        } else {
            SlayerKillCounter kc = SlayerKillCounter.values()[slot];
            name = kc.name();
            player.sendMessage("Your " + kc.name + " streak has been reset.");
        }
        KillCounter counter = player.killCounterMap.get(name);
        counter.resetStreak();
        player.getPacketSender().sendClientScript(1588, "i", slot);
        player.removeTemporaryAttribute("KILL_COUNTER_SLOT");
    }

    public static void openOwnBoss(Player player) {
        StringBuilder names = new StringBuilder();
        StringBuilder totalCounts = new StringBuilder();
        StringBuilder streaks = new StringBuilder();
        for (BossKillCounter boss : BossKillCounter.values()) {
            KillCounter kc = player.getAttribute(boss.name());
            if (kc == null) {
                names.append(boss.name);
                totalCounts.append(0);
                streaks.append(0);
            } else {
                if (boss.name == null) {
                    names.append("null");
                } else {
                    names.append(boss.name);
                }
                totalCounts.append(NumberUtils.formatNumber(kc.getKills()));
                streaks.append(NumberUtils.formatNumber(kc.getStreak()));
            }
            names.append("|");
            totalCounts.append("|");
            streaks.append("|");
        }
        player.putTemporaryAttribute("KILL_COUNTER_LIST", "BOSS");
        open(player, player, BossKillCounter.values().length, names, totalCounts, streaks, "Boss Kill Log");
    }

    public static void openBoss(Player player, Player killer) {
        StringBuilder names = new StringBuilder();
        StringBuilder totalCounts = new StringBuilder();
        StringBuilder streaks = new StringBuilder();
        for (BossKillCounter boss : BossKillCounter.values()) {
            KillCounter kc = killer.getAttribute(boss + "_KC");
            if (kc == null) {
                names.append(boss.name);
                totalCounts.append(0);
                streaks.append(0);
            } else {
                if (boss.name == null) {
                    names.append("null");
                } else {
                    names.append(boss.name);
                }
                totalCounts.append(NumberUtils.formatNumber(kc.getKills()));
                streaks.append(NumberUtils.formatNumber(kc.getStreak()));
            }
            names.append("|");
            totalCounts.append("|");
            streaks.append("|");
        }
        open(player, killer, BossKillCounter.values().length, names, totalCounts, streaks, killer.getName() + "'s Boss Kill Log");
    }

    public static void openOwnSlayer(Player player) {
        StringBuilder names = new StringBuilder();
        StringBuilder totalCounts = new StringBuilder();
        StringBuilder streaks = new StringBuilder();
        for (SlayerKillCounter monster : SlayerKillCounter.values()) {
            KillCounter kc = player.killCounterMap.get(monster.name());
            if (kc == null) {
                names.append(monster.name);
                totalCounts.append(0);
                streaks.append(0);
            } else {
                if (monster.name == null) {
                    names.append("null");
                } else {
                    names.append(monster.name);
                }
                totalCounts.append(NumberUtils.formatNumber(kc.getKills()));
                streaks.append(NumberUtils.formatNumber(kc.getStreak()));
            }
            names.append("|");
            totalCounts.append("|");
            streaks.append("|");
        }
        player.putTemporaryAttribute("KILL_COUNTER_LIST", "SLAYER");
        open(player, player, SlayerKillCounter.values().length, names, totalCounts, streaks, "Slayer Kill Log");
    }

    public static void openSlayer(Player player, Player killer) {
        StringBuilder names = new StringBuilder();
        StringBuilder totalCounts = new StringBuilder();
        StringBuilder streaks = new StringBuilder();
        for (SlayerKillCounter monster : SlayerKillCounter.values()) {
            KillCounter kc = killer.killCounterMap.get(monster.name());
            if (kc == null) {
                names.append(monster.name);
                totalCounts.append(0);
                streaks.append(0);
            } else {
                if (monster.name == null) {
                    names.append("null");
                } else {
                    names.append(monster.name);
                }
                totalCounts.append(NumberUtils.formatNumber(kc.getKills()));
                streaks.append(NumberUtils.formatNumber(kc.getStreak()));
            }
            names.append("|");
            totalCounts.append("|");
            streaks.append("|");
        }
        player.putTemporaryAttribute("KILL_COUNTER_LIST", "SLAYER");
        open(player, killer, SlayerKillCounter.values().length, names, totalCounts, streaks, killer.getName() + "'s Slayer Kill Log");
    }

    private static void open(Player player, Player killer, int size, StringBuilder names, StringBuilder totalCounts, StringBuilder streaks, String title) {
        player.openInterface(InterfaceType.MAIN, Interface.KILL_COUNTER);
        player.getPacketSender().sendClientScript(1584,
                "sssis",
                names.toString(),
                totalCounts.toString(),
                streaks.toString(),
                size,
                title
        );
        if (player == killer) {
            player.getPacketSender().sendAccessMask(Interface.KILL_COUNTER, 16, 0, size, 2);
        } else {
            player.getPacketSender().setHidden(Interface.KILL_COUNTER, 16, true);
        }
    }

    public static KillCounter getKillCounter(Player player, BossKillCounter counter) {
        return player.killCounterMap.get(counter.name());
    }

    public static KillCounter getKillCounter(Player player, SlayerKillCounter counter) {
        return player.killCounterMap.get(counter.name());
    }

    public static int getKillCount(Player player, BossKillCounter counter) {
        return getKillCounter(player, counter).kills;
    }

    public static int getKillCount(Player player, SlayerKillCounter counter) {
        return getKillCounter(player, counter).kills;
    }

    @Expose private int kills;

    @Expose private int streak;

    private String name;
    private boolean messageOnKill;

    private Achievement achievement;

    public void increment(Player player) {
        kills++;
        streak++;
        if (messageOnKill)
            player.sendMessage("Your " + name +" kill count is: " + Color.RED.wrap(NumberUtils.formatNumber(kills)));
        if (achievement != null)
            achievement.update(player);
    }

    public void resetStreak() {
        streak = 0;
    }

    public int getKills() {
        return kills;
    }

    public int getStreak() {
        return streak;
    }

    public String getName() {
        return name;
    }

    public KillCounter setName(String name) {
        this.name = name;
        return this;
    }

    public KillCounter messageOnKill() {
        messageOnKill = true;
        return this;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
