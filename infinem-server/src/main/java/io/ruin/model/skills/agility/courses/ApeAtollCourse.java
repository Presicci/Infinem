package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.AttributeKey;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.utility.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.TricksterAgility;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/4/2024
 */
public class ApeAtollCourse {

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(2753, 2741, 0), true).getObject(15414, 10, 3),    // Tropical tree
            Tile.get(new Position(2752, 2741, 2), true).getObject(15417, 10, 3),    // Monkeybars
            Tile.get(new Position(2746, 2741, 0), true).getObject(1747, 22, 2),     // Skull slope
            Tile.get(new Position(2752, 2731, 0), true).getObject(15487, 10, 0),    // Rope swing
            Tile.get(new Position(2757, 2734, 0), true).getObject(16062, 10, 0)     // Tropical tree
    };

    private static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(2746, 2732, 0),
            new Position(2759, 2732, 0),
            new Position(2752, 2741, 0)
    );

    public static final int[] MONKEY_IDS = { 1462, 1463, 5257 };

    public static boolean checkGreeGree(Player player) {
        int npcId = player.getAppearance().getNpcId();
        return Arrays.stream(MONKEY_IDS).anyMatch(id -> id == npcId);
    }

    static {
        /*
         * Stepping stone
         */
        ObjectAction.register(15412, 2754, 2742, 0, "jump-to", (player, obj) -> {
            if (!checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("That stepping stone is way too small. No way I'm jumping to that."));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 48, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.npcAnimate(3481, MONKEY_IDS);
                e.delay(1);
                player.getMovement().force(-1, 0, 0, 0, 5, 35, Direction.WEST);
                e.delay(2);
                player.npcAnimate(3481, MONKEY_IDS);
                e.delay(1);
                player.getMovement().force(-1, 0, 0, 0, 5, 35, Direction.WEST);
                e.delay(1);
                player.getStats().addXp(StatType.Agility, 40.0, true);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[0]);
            });
        });
        ObjectAction.register(15414, 2753, 2741, 0, "climb", (player, obj) -> {
            if (!checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("I'm not a monkey! I don't know how to climb trees!"));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 48, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                e.delay(1);
                player.npcAnimate(3487, MONKEY_IDS);
                e.delay(3);
                player.getMovement().teleport(2753, 2742, 2);
                player.getStats().addXp(StatType.Agility, 40.0, true);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[1]);
            });
        });
        ObjectAction.register(15417, 2752, 2741, 2, "swing across", (player, obj) -> {
            if (!checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("They're called monkey bars for a reason!"));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 48, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.npcAnimate(3482, MONKEY_IDS);
                player.getMovement().force(0, -1, 0, 0, 5, 35, Direction.SOUTH);
                e.delay(1);
                player.getAppearance().setCustomRenders(Renders.APE_ATOLL_MONKEY_BARS);
                e.delay(1);
                for (int index = 0; index < 4; index++) {
                    player.getMovement().force(-1, 0, 0, 0, 5, 35, Direction.WEST);
                    e.delay(1);
                }
                player.getAppearance().restoreNPCRenders();
                player.npcAnimate(3484, MONKEY_IDS);
                player.getMovement().force(-1, 0, 0, 0, 5, 35, Direction.WEST);
                e.delay(1);
                player.getMovement().teleport(2747, 2741, 0);
                player.getStats().addXp(StatType.Agility, 40.0, true);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[2]);
            });
        });
        ObjectAction.register(1747, 2746, 2741, 0, "climb-up", (player, obj) -> {
            if (!checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("What are these? Handholds for monkeys?"));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 48, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.npcAnimate(3485, MONKEY_IDS);
                for (int index = 0; index < 4; index++) {
                    player.getMovement().force(-1, 0, 0, 0, 5, 35, Direction.WEST);
                    e.delay(1);
                }
                player.resetAnimation();
                player.putAttribute("LAST_AGIL_OBJ", obj.id);
                player.getStats().addXp(StatType.Agility, 60.0, true);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[3]);
            });
        });
        ObjectAction.register(15487, 2752, 2731, 0, "swing", (player, obj) -> {
            if (!checkGreeGree(player)) {
                player.startEvent(e -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    player.animate(751);
                    player.getPacketSender().fadeOut();
                    e.delay(1);
                    player.dialogue(new MessageDialogue("Your hands slip down the vine...").hideContinue());
                    e.delay(1);
                    player.getMovement().teleport(2755, 2741, 0);
                    e.delay(3);
                    player.getPacketSender().fadeIn();
                    player.dialogue(new MessageDialogue("...Moments later you find yourself washed ashore at the bottom of the waterfall."));
                    e.delay(1);
                    player.unlock();
                });
                return;
            }
            if (!player.getStats().check(StatType.Agility, 48, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                //obj.animate(54);
                player.npcAnimate(3488, MONKEY_IDS);
                player.getMovement().force(5, 0, 0, 0, 30, 50, Direction.EAST);
                e.delay(1);
                //obj.animate(55);
                if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 1747)
                    player.putAttribute("LAST_AGIL_OBJ", obj.id);
                player.getStats().addXp(StatType.Agility, 100.0, true);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[4]);
            });
        });
        ObjectAction.register(16062, 2757, 2734, 0, "climb-down", (player, obj) -> {
            if (!checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("No way that vine will hold me, I've been eating too many cakes."));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 48, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.stepAbs(2758, 2735, StepType.FORCE_WALK);
                e.delay(3);
                player.getMovement().teleport(2758, 2735, 1);
                player.getAppearance().setCustomRenders(Renders.APE_ATOLL_TROPICAL_TREE);
                e.delay(1);
                for (int index = 0; index < 12; index++) {
                    player.getMovement().force(1, 1, 0, 0, 5, 35, Direction.NORTH_EAST);
                    e.delay(1);
                }
                player.getAppearance().restoreNPCRenders();
                player.getMovement().teleport(2770, 2747, 0);
                player.getStats().addXp(StatType.Agility, 100.0, true);
                if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 15487) {
                    player.getStats().addXp(StatType.Agility, 200.0, true);
                    AgilityPet.rollForPet(player, 37720);
                    MarkOfGrace.rollMark(player, 48, MARK_SPAWNS);
                    player.getTaskManager().doLookupByCategory(TaskCategory.AGILITY_LAP, 1, true);
                    int laps = PlayerCounter.APE_ATOLL_COURSE.increment(player, 1);
                    if (!player.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                        player.sendFilteredMessage("Your Ape Atoll Agility lap count is: " + Color.RED.wrap(laps + "") + ".");
                    player.removeAttribute("LAST_AGIL_OBJ");
                }
                player.unlock();
            });
        });
    }
}
