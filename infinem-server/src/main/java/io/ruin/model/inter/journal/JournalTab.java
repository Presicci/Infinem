package io.ruin.model.inter.journal;

import io.ruin.Server;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.content.tasksystem.tasks.inter.TaskInterface;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.*;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.handlers.TabBestiary;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.collectionlog.CollectionLogInfo;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.ruin.model.World.doubleDrops;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/25/2023
 */
public class JournalTab {

    @Getter
    public enum Tab {
        SUMMARY(712),
        TASK(656),
        BESTIARY(1009),
        ACHIEVEMENT(Interface.ACHIEVEMENT),
        INFORMATION(-1);

        private final int id;

        Tab(int id) {
            this.id = id;
        }

        /**
         * Returns the components of the specified {@link Tab}.
         *
         * @return A list of components.
         */
        public List<TabComponent> getComponents() {
            List<TabComponent> list = new ArrayList<>();

            for (TabComponent component : TabComponent.values()) {
                if (!component.getTab().equals(this)) {
                    continue;
                }

                list.add(component);
            }

            return list;
        }

        static {
            for (Tab tab : values()) {
                Map<Integer, InterfaceAction> actions = new HashMap<>();

                for (TabComponent c : tab.getComponents()) {
                    if (c.getAction() == null) {
                        continue;
                    }

                    actions.put(c.getComponentId(), c.getAction());
                }

                if (actions.isEmpty()) {
                    continue;
                }

                InterfaceHandler.register(tab.getId(), interfaceHandler -> {
                    for (int component : actions.keySet()) {
                        interfaceHandler.actions[component] = actions.get(component);
                    }
                });
            }
        }
    }

    private static int t1 = 7; // 10 - 30
    private static int t2;
    private static int t3 = 9; // 9 - 20
    private static int t4 = 7;

    @Getter
    public enum TabComponent {
        /**
         * Journal tab
         */
        SUMMARY(Tab.SUMMARY, player -> {
            player.getPacketSender().sendAccessMask(712, 3, 3, 6, 2);
        }) {
            @Override
            public void init() {
                InterfaceHandler.register(getTab().getId(), h -> h.actions[3] = (SlotAction) JournalTab::handleSummaryClick);
            }
        },
        SUMMARY_ACHIEVEMENTS(Tab.SUMMARY, player -> {
        }),
        SUMMARY_TASKS(Tab.SUMMARY, player -> {
            //Config.varpbit(6347, false).set(player, 150);
            //Config.varpbit(11877, false).set(player, 400);
            //Config.LEAGUE_POINTS.set(player, 10000);
        }),
        SUMMARY_COLLECTION(Tab.SUMMARY, player -> {
            Config.COLLECTION_LOG_COLLECTED.set(player, player.getCollectionLog().getCollected().size());
            Config.COLLECTION_LOG_MAX.set(player, CollectionLogInfo.TOTAL_COLLECTABLES);
        }),
        SUMMARY_TIME_PLAYED(Tab.SUMMARY, player -> {
            player.getPacketSender().sendClientScript(3970, "iii", 0, 0, (int) TimeUnit.MILLISECONDS.toMinutes(player.playTime * Server.tickMs()));
        }),

        /*PLAYERS(Tab.QUEST, t1++, player -> "Players Online: " + Color.GREEN.wrap(String.valueOf((int) Math.round((World.players.count() ))))),
        STAFF(Tab.QUEST, t1++, player -> "Staff Online: " + Color.GREEN.wrap(String.valueOf(getStaffOnlineCount())), (SimpleAction) JournalTab::sendStaffOnline),
        UPTIME(Tab.QUEST, t1++, player -> "Uptime: " + Color.GREEN.wrap(TimeUtils.fromMs(Server.currentTick() * Server.tickMs(), false))),
        SERVER_TIME(Tab.QUEST, t1++, player -> "Time: " + Color.GREEN.wrap(getServerTime())),

        PLAYER_INFORMATION(Tab.QUEST, t1++),
        RANK(Tab.QUEST, t1++, player -> {
            if (player.isStaff() && player.isDonator()) {
                return "Rank: " + Color.GREEN.wrap(player.getPrimaryGroup().title);
            }
            if (player.isDonator() && !player.isYoutuber()) {
                return "Rank: " + Color.GREEN.wrap(player.getSecondaryGroup().title);
            }
            return "Rank: " + Color.GREEN.wrap(player.getPrimaryGroup().title);
        }),
        TOTAL_PURCHASE(Tab.QUEST, t1++, player -> "Total Donated: " + Color.GREEN.wrap( "$" + player.storeAmountSpent)),
        XP_MODE(Tab.QUEST, t1++, player -> "Mode: " + Color.GREEN.wrap(player.getGameMode().toString())),
        XP_BONUS(Tab.QUEST, t1++, player -> "Your CombatXP Bonus: " + Color.GREEN.wrap(player.xpMode.getCombatRate() + "X")),
        SKILL_XP_BONUS(Tab.QUEST, t1++, player -> "Your SkillXP Bonus: " + Color.GREEN.wrap(player.xpMode.getSkillRate() + "X")),
        //PLAYTIME(Tab.QUEST, t1++, player -> "Time Played: " + Color.GREEN.wrap(TimeUtils.fromMs(player.playTime * Server.tickMs(), false))),
        KDR(Tab.QUEST, t1++, player -> "KDR: " + Color.GREEN.wrap(getKdr(player))),
        CREDITS(Tab.QUEST, t1++, player -> "PVM Points: " + Color.GREEN.wrap(Integer.toString(player.PvmPoints))),
        AFK_Points(Tab.QUEST, t1++, player -> "AFK Points: " + Color.GREEN.wrap(Integer.toString(player.afkPoints))),
        PEST_POINTS(Tab.QUEST, t1++, player -> "Pest Points: " + Color.GREEN.wrap(Integer.toString(player.pestPoints))),
        SLAYER_POINTS(Tab.QUEST, t1++, player -> "Slayer Points: " + Color.GREEN.wrap(Integer.toString(Config.SLAYER_POINTS.get(player)))),
        WINTERTODT_POINTS(Tab.QUEST, t1++, player -> "Wintertodt Points: " + Color.GREEN.wrap(Integer.toString(player.wintertodtstorePoints))),
        PK_BONUS(Tab.QUEST, t1++, player -> "Mage Arena Points: " + Color.GREEN.wrap(String.valueOf(player.mageArenaPoints))),
        /**
         * Activity tab
         */
        /*EMPTY_6(Tab.QUEST, t1++),
        WELL(Tab.QUEST, t1++, player -> "Well Status: " + WellChecker()),
        PLAYERS_DUEL(Tab.QUEST, t1++, player -> "Duel Arena: " + Color.GREEN.wrap(String.valueOf(Duel.players.size())) + " players."),
        PLAYERS_TOURNY(Tab.QUEST, t1++, player -> "Tournament: " + Color.GREEN.wrap(String.valueOf(PVPInstance.players.size())) + " players."),
        XP_BOOST(Tab.QUEST, t1++, player -> "XP Bonus: " + Color.GREEN.wrap(World.xpMultiplier >= 2 ? Color.GREEN.wrap("Enabled") : Color.RED.wrap("Disabled"))),


//        SLAYER_BONUS(Tab.QUEST, t1++, player -> "Slayer Bonus: " + Color.GREEN.wrap(Misc.stateOf(WellOfGoodwill.isDoubleSlayer()))),
//        PC_BONUS(Tab.QUEST, t1++, player -> "Pest Bonus: " + Color.GREEN.wrap(Misc.stateOf(WellOfGoodwill.isDoublePest()))),

        /**
         * Misc tab.
         */
        /*DROP_TABLES(Tab.MISCELLANEOUS, t4++, player -> "View drop tables", (SimpleAction) Bestiary::open),
        EMPTY(Tab.MISCELLANEOUS, t4++, player -> "View Box drop tables", (SimpleAction) Bestiary::openBoxLoot),
        EMPTY2(Tab.MISCELLANEOUS, t4++, player -> "View store", (SimpleAction) player -> player.openUrl("https://obsidian-ps.com/store.php")),
        EMPTY3(Tab.MISCELLANEOUS, t4++, player -> "Join discord", (SimpleAction) player -> player.openUrl("https://discord.gg/Jf3zxrrKjZ")),
        //EMPTY3(Tab.MISCELLANEOUS, t4++),

        COMBAT(Tab.MISCELLANEOUS, t4++),
        TARGET_OVERLAY(Tab.MISCELLANEOUS, t4++, player -> "Target Overlay", (SimpleAction) player -> player.sendMessage("This feature is currently disabled.")),
        DRAG_SETTING(Tab.MISCELLANEOUS, t4++, new DragSetting()),
        TIMER_TOGGLE(Tab.MISCELLANEOUS, t4++, new ShowTimers()),
        BH_OVERLAY(Tab.MISCELLANEOUS, t4++, new BountyOverlay()),
        KD_OVERLAY(Tab.MISCELLANEOUS, t4++, new KDOverlay()),

        BOUNTY_HUNTER(Tab.MISCELLANEOUS, t4++),
        BH_TARGETING(Tab.MISCELLANEOUS, t4++, new BountyHunterTargeting()),
        BH_STREAKS(Tab.MISCELLANEOUS, t4++, new BountyHunterStreaks()),
        EMPTY4(Tab.MISCELLANEOUS, t4++),

        BROADCASTS(Tab.MISCELLANEOUS, t4++),
        BC_BOSS_EVENTS(Tab.MISCELLANEOUS, t4++, new BroadcastBossEvent()),
        BC_VOLCANO(Tab.MISCELLANEOUS, t4++, new BroadcastActiveVolcano()),
        BC_HOTSPOT(Tab.MISCELLANEOUS, t4++, new BroadcastHotspot()),
        BC_SUPPLY_CHEST(Tab.MISCELLANEOUS, t4++, new BroadcastSupplyChest()),
        BC_ANNOUNCEMENTS(Tab.MISCELLANEOUS, t4++, new BroadcastAnnouncements()),
        BC_TOURNAMENTS(Tab.MISCELLANEOUS, t4++, new BroadcastTournaments()),

        OTHER(Tab.MISCELLANEOUS, t4++),
        BREAK_VIALS(Tab.MISCELLANEOUS, t4++, new BreakVials()),
        DISCARD_BUCKETS(Tab.MISCELLANEOUS, t4++, new DiscardBuckets()),
        HIDE_ICON(Tab.MISCELLANEOUS, t4++, new HideIcon()),
        RISK_PROTECTION(Tab.MISCELLANEOUS, t4++, RiskProtection.INSTANCE),
        HIDE_YELLS(Tab.MISCELLANEOUS, t4++, new HideYells()),
        EMPTY_7(Tab.MISCELLANEOUS, t4++),*/
//        EXP_LOCK(Tab.MISCELLANEOUS, t4++, player -> "Experience lock", (SimpleAction) p -> ExperienceLock.open(p)),
        ;

        private Tab tab;
        private int componentId;
        private TextField text;
        private InterfaceAction action;
        private SimpleAction send;

        TabComponent(Tab tab) {
            this(tab, 0, null, null);
        }

        TabComponent(Tab tab, SimpleAction send) {
            this(tab, 0, null, null);
            this.send = send;
        }

        TabComponent(Tab tab, int componentId) {
            this(tab, componentId, null, null);
        }

        TabComponent(Tab tab, int componentId, TextField text) {
            this(tab, componentId, text, null);
        }

        TabComponent(Tab tab, int componentId, TextField text, InterfaceAction action) {
            this.tab = tab;
            this.componentId = componentId;
            this.text = text;
            this.action = action;
            init();
        }

        /*TabComponent(Tab tab, int componentId, JournalToggle toggle) {
            this.tab = tab;
            this.componentId = componentId;
            this.text = toggle.getText();
            this.action = (SimpleAction) toggle::handle;
            init();
        }*/

        public void init() {

        }

        public void send(Player player) {
            if (send != null) {
                send.handle(player);
                return;
            }

            //if (getTab().equals(Tab.MISCELLANEOUS)) {
            //    player.getPacketSender().sendString(getTab().getId(), getComponentId(), getText().send(player));
           // } else {
                player.getPacketSender().sendString(getTab().getId(), getComponentId(), " - " + getText().send(player));
            //}
        }
    }

    public static String WellChecker() {
        //if (WellofGoodwill.getMinutesRemaining() <= 0) {
            return "EMPTY";
        //} else {
         //   return String.valueOf(WellofGoodwill.getMinutesRemaining() + " Mins Left!");
        //}
    }

    public static void setTab(Player player, Tab tab) {
        int index = tab.ordinal() != 0 ? tab.ordinal() + 1 : tab.ordinal();
       /* if (tab == Tab.MISCELLANEOUS) {
            index = tab.ordinal();
        } else if (tab == Tab.ACTIVITIES) {
            index = 0;
        } else if (tab == Tab.QUEST) {
            index = 1;
        }*/
        //Config.QUEST_TOTAL_TABS.set(player, 1);
       /* if (tab == Tab.SUMMARY) {
            Config.QUEST_ACTIVE_TAB.set(player, 0);
        } else {
            if (tab == Tab.MISCELLANEOUS) {
                Config.QUEST_ACTIVE_TAB.set(player, 3);
            } else
                Config.QUEST_ACTIVE_TAB.set(player, tab.ordinal());
        }*/
        player.getPacketSender().sendInterface(tab.getId(), 629, 33, 1);
        Config.ACTIVE_QUEST_TAB.set(player, tab.ordinal());
        updateTab(player, tab);
    }

    public static void updateTab(Player player, Tab tab) {
        /*if (tab == Tab.QUEST) {
            player.getPacketSender().sendString(tab.getId(), 1, "Journal");
        } else */if (tab == Tab.ACHIEVEMENT) {
            player.getPacketSender().sendAccessMask(259, 2, 0, 11, AccessMasks.ClickOp1);
//            player.getPacketSender().sendString(tab.getId(), 1, "Achievement Diary");
//            for (Achievements.AchievementCategory category : Achievements.AchievementCategory.values()) {
//                player.getAchievements().updateTabProgress(category);
            //           }
       /* } else if (tab == Tab.ACTIVITIES) {
            player.getPacketSender().sendString(tab.getId(),1, "Activities");
        } else if (tab == Tab.MISCELLANEOUS) {
            player.getPacketSender().sendString(tab.getId(),1, "Miscellaneous");

            int count = 0;

            for (JournalEntry entry : Journal.TOGGLES.getEntries()) {
                if (count++ == 2) {
                    break;
                }

            }*/
        }

        for (TabComponent c : tab.getComponents()) {
            if (c.text == null && c.send == null) {
                continue;
            }

            c.send(player);
        }
    }

    public static void send(Player player, TabComponent c) {
        player.getPacketSender().sendString(c.getTab().getId(), c.getComponentId(), " - " + c.getText().send(player));
    }

    private static void handleSummaryClick(Player player, int slot) {
        if (slot == 3) {
            TaskInterface.openTaskInterface(player);
        } else if (slot == 4) {
            setTab(player, Tab.ACHIEVEMENT);
        } else if (slot == 5) {
            //CombatAchievements.openOverview(player);
        } else if (slot == 6) {
            player.getCollectionLog().open(player);
        }
    }

    /**
     * Sends an interface filtered with staff that are currently online.
     * @param player    The player.
     */
    private static void sendStaffOnline(Player player) {
        int interId = 846;
        player.openInterface(InterfaceType.MAIN, interId);
        player.getPacketSender().sendString(interId, 4, "Online Staff");
        List<Player> staffList = World.getPlayerStream().filter(Player::isStaff).collect(Collectors.toList());
        int component = 6;
        for (Player p1 : staffList) {
            player.getPacketSender().sendString(interId, component++, p1.getPrimaryGroup().tag() + " " + p1.getName());
        }
    }

    private static int getStaffOnlineCount() {
        List<Player> staffList = World.getPlayerStream().filter(Player::isStaff).collect(Collectors.toList());
        return staffList.size();
    }

    private static String getKdr(Player player) {
        int kills = Config.PVP_KILLS.get(player);
        int deaths = Config.PVP_DEATHS.get(player);

        return (kills + "/" + deaths + "/" + (deaths == 0 ? "0,00" : String.format("%.2f", ((double) kills / deaths))));
    }

    private static String getDoubleDrops() {
        if (doubleDrops) {
            return Color.GREEN.wrap("Enabled");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }
    private static String getDoublePkp() {
        //if (doublePkp) {
        ///    return Color.GREEN.wrap("Enabled");
        //} else {
            return Color.RED.wrap("Disabled");
        //}
    }
    private static String getDoubleSlayerPoints() {
        if (World.doubleSlayer) {
            return Color.GREEN.wrap("Enabled");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }
    private static String getDoublePcPoints() {
        if (World.doublePest) {
            return Color.GREEN.wrap("Enabled");
        } else {
            return Color.RED.wrap("Disabled");
        }
    }

    private static String getServerTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    private static double getXpBonus(Player player) {
        double xp = 1;

        if (World.xpMultiplier > 0)
            xp += World.xpMultiplier - 1;

        if(player.expBonus.isDelayed()) //50% xp scrolls
            xp += 0.5;

        if (player.wildernessLevel > 1) //wilderness bonus
            xp += .25;

        if (World.weekendExpBoost)
            xp += .25;

//        if (WellOfGoodwill.isDoubleXp())
//            xp += 1;

        return xp;
    }

    public interface TextField {
        String send(Player player);
    }

    static {
        InterfaceHandler.register(Interface.QUEST_TAB, interfaceHandler -> {
            interfaceHandler.actions[3] = (SimpleAction) player -> setTab(player, Tab.SUMMARY);
            interfaceHandler.actions[8] = (SimpleAction) player -> setTab(player, Tab.TASK);
            interfaceHandler.actions[13] = (SimpleAction) player -> {
                setTab(player, Tab.BESTIARY);
                TabBestiary.sendTab(player);
            };
            interfaceHandler.actions[18] = (SimpleAction) player -> setTab(player, Tab.ACHIEVEMENT);
            interfaceHandler.actions[23] = (SimpleAction) player -> setTab(player, Tab.INFORMATION);
        });
        LoginListener.register(player -> {
            setTab(player,Tab.SUMMARY);
            player.addEvent(event -> {
                while(true) {
                    //updateTab(player,Tab.QUEST);
                    event.delay(5);
                }
            });
        });
    }
}
