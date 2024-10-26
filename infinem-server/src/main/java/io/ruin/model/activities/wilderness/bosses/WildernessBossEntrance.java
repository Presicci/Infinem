package io.ruin.model.activities.wilderness.bosses;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.BossKillCounter;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Color;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/22/2024
 */
public class WildernessBossEntrance {

    @Getter
    @AllArgsConstructor
    public enum Cave {
        VENENATIS_DUNGEON_ENTRANCE(47077, new Position(3319, 3798, 0), new Position(3424, 10212, 2), new Position(3423, 10203, 2), "venenatis", true),
        SPINDEL_DUNGEON_ENTRANCE(47078, new Position(3183, 3744, 0), new Position(1632, 11556, 2), new Position(1631, 11546, 2), "spindel", true),
        CALLISTO_DUNGEON_ENTRANCE(47140, new Position(3291, 3849, 0), new Position(3359, 10334, 0), new Position(3359, 10327, 0), "callisto", true),
        ARTIO_DUNGEON_ENTRANCE(47141, new Position(3115, 3676, 0), new Position(1759, 11550, 0), new Position(1759, 11542, 0), "artio", true),
        VETION_DUNGEON_ENTRANCE(46995, new Position(3221, 3787, 0), new Position(3295, 10202, 1), new Position(3295, 10202, 1), "vet'ion", true),
        CALVARION_DUNGEON_ENTRANCE(46996, new Position(3180, 3683, 0), new Position(1887, 11546, 1), new Position(1887, 11546, 1), "calvar'ion", true),
        ESCAPE_CAVES_ENTRANCE_1(47175, new Position(3282, 3774, 0), new Position(3359, 10246, 0), null, "", false),
        ESCAPE_CAVES_ENTRANCE_2(47175, new Position(3320, 3830, 0), new Position(3381, 10286, 0), null, "", false),
        ESCAPE_CAVES_ENTRANCE_3(47175, new Position(3260, 3832, 0), new Position(3338, 10286, 0), null, "", false),
        ESCAPE_CAVES_ENTRANCE_4(47175, new Position(3284, 3807, 0), new Position(3360, 10273, 0), null, "", false);

        private final int id;
        private final Position object;
        private final Position exitLocation;
        private final Position roomCenter;
        private final String bossName;
        private final boolean bossCave;

        public static Cave get(int id, Position object) {
            for (Cave cave : VALUES) {
                if (cave.getId() == id && cave.getObject().equals(object)) {
                    return cave;
                }
            }
            return null;
        }

        public static final Cave[] VALUES = values();
        public static final Int2ObjectMap<Cave> entries = new Int2ObjectOpenHashMap<>(VALUES.length);

        static {
            for (final Cave entry : VALUES) {
                entries.put(entry.getId(), entry);
            }
        }
    }

    private static final String PAID_KEY = "PAID_WILDERNESS_BOSS_FEE";
    private static final String WARNING_KEY = "WILDERNESS_BOSS_FEE_WARNING";
    private static final String KILL_DISCOUNT = "WILDERNESS_BOSS_DISCOUNT";
    private static final int ENTRANCE_FEE = 50_000;

    static {
        for (Cave cave : Cave.values()) {
            ObjectAction.register(cave.id, 1, (player, obj) -> enterCave(player, obj, cave));
            ObjectAction.register(cave.id, 2, (player, obj) -> {
                if (cave.bossCave) {
                    peekCave(player, obj, cave);
                } else {
                    checkFee(player);
                }
            });
            if (cave.bossCave) {
                ObjectAction.register(cave.id, 3, (player, obj) -> checkFee(player));
            }
        }
    }

    private static void enterCave(Player player, GameObject object, Cave cave) {
        final int cost = ENTRANCE_FEE - (player.getAttributeIntOrZero(KILL_DISCOUNT) * (ENTRANCE_FEE / 5));
        if (player.hasAttribute(PAID_KEY)) {
            teleport(player, object, cave);
            return;
        }
        if (!(player.getInventory().contains(995, cost) || player.getBank().contains(new Item(995, cost)))) {
            player.sendMessage(Color.RED.wrap("You need " + NumberUtils.formatNumber(cost) + " coins in your inventory or bank to pay the entry fee."));
            return;
        }
        List<Option> options = new ArrayList<>();
        options.add(new Option("Yes.", () -> payAndTeleport(player, object, cave, cost)));
        if (!player.hasAttribute(WARNING_KEY)) {
            options.add(new Option("Yes, and don't ask again.", () -> {
                payAndTeleport(player, object, cave, cost);
                player.putAttribute(WARNING_KEY, 1);
                player.sendMessage(Color.RED.wrap("You will not be asked again about paying the entry fee."));
            }));
        }
        options.add(new Option("No."));

        player.dialogue(
                new ItemDialogue().one(995, "You need to pay a " + NumberUtils.formatNumber(cost) + " coin fee to enter. This can be taken from your inventory or bank."),
                new OptionsDialogue("Pay " + NumberUtils.formatNumber(cost) + " coins to enter?", options)
        );
    }

    private static void checkFee(Player player) {
        final int cost = ENTRANCE_FEE - (player.getAttributeIntOrZero(KILL_DISCOUNT) * (ENTRANCE_FEE / 5));
        player.dialogue(
                new ItemDialogue().one(995, "The base entry fee for these caves is " + NumberUtils.formatNumber(ENTRANCE_FEE) + " coins, which "
                                + (player.hasAttribute(PAID_KEY) ? "you have paid." : "can be paid from your inventory or bank.")
                                + "Killing wilderness bosses will reduce the fee by " + NumberUtils.formatNumber(ENTRANCE_FEE / 5) + "."
                                + " You have "
                                + (player.getAttributeIntOrZero(KILL_DISCOUNT) > 0 ? "a " + cost : "no discount towards your next fee."
                        )
                ));
    }

    private static void peekCave(Player player, GameObject object, Cave cave) {
        final int kc = bossKillCounts(player, cave);
        if (kc < 20) {
            player.sendMessage("You don't know this cave well enough to glean any useful information. You feel that you'll be more successful once you've killed the boss inside, " + (20 - kc) + " more times.");
            return;
        }
        int count = (int) cave.getExitLocation().getRegion().players.stream().count();

        if (count > 0) {
            player.sendMessage("You peek into the darkness and can make out some movement. There is activity inside.");
        } else {
            player.sendMessage("You peek into the darkness and can make out no movement. There is no activity inside.");
        }
    }

    private static void payAndTeleport(Player player, GameObject object, Cave cave, int cost) {
        teleport(player, object, cave);
        if (player.getInventory().contains(995, cost)) {
            player.getInventory().remove(995, cost);
        } else {
            player.sendMessage(Color.RED.wrap("You enter the cave and " + NumberUtils.formatNumber(cost) + " coins are taken from your bank to pay the entry fee."));
            player.getBank().remove(995, cost);
        }
        player.putAttribute(PAID_KEY, 1);
    }

    private static void teleport(Player player, GameObject object, Cave cave) {
        player.startEvent(e -> {
            player.lock();
            player.face(object);
            player.animate(object.id == 47175 ? 2796 : 7041);
            e.delay(object.id == 47175 ? 2 : 1);
            player.getMovement().teleport(cave.getExitLocation());
            player.animate(object.id == 47175 ? -1 : 767);
            player.unlock();
        });
    }

    private static int bossKillCounts(Player player, Cave cave) {
        switch (cave) {
            case VENENATIS_DUNGEON_ENTRANCE:
                return BossKillCounter.VENENATIS.getCounter().getKills(player);
            case SPINDEL_DUNGEON_ENTRANCE:
                return BossKillCounter.SPINDEL.getCounter().getKills(player);
            case CALLISTO_DUNGEON_ENTRANCE:
                return BossKillCounter.CALLISTO.getCounter().getKills(player);
            case ARTIO_DUNGEON_ENTRANCE:
                return BossKillCounter.ARTIO.getCounter().getKills(player);
            case VETION_DUNGEON_ENTRANCE:
                return BossKillCounter.VETION.getCounter().getKills(player);
            case CALVARION_DUNGEON_ENTRANCE:
                return BossKillCounter.CALVARION.getCounter().getKills(player);
            default:
                return 0;
        }
    }
}
