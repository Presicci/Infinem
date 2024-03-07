package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.courses.ApeAtollCourse;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/7/2024
 */
public class KrukDungeon {

    private static final Config KRUK_DUNGEON_ENTRANCE = Config.varpbit(5033, false).defaultValue(1);           // Toggles the trapdoor to the dungeon
    private static final Config KRUK_DUNGEON_HUNTER_ENTRANCE = Config.varpbit(5027, false).defaultValue(80);   // Toggles the entrance to the hunter area

    static {
        ObjectAction.register(28724, 2504, 9190, 1, "swing across", (player, obj) -> {
            if (!ApeAtollCourse.checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("They're called monkey bars for a reason!"));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 55, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.npcAnimate(3482, ApeAtollCourse.MONKEY_IDS);
                player.getMovement().force(0, 1, 0, 0, 5, 35, Direction.NORTH);
                e.delay(1);
                player.getAppearance().setCustomRenders(Renders.APE_ATOLL_MONKEY_BARS);
                e.delay(1);
                for (int index = 0; index < 12; index++) {
                    player.getMovement().force(0, 1, 0, 0, 5, 35, Direction.NORTH);
                    e.delay(1);
                }
                player.getAppearance().restoreNPCRenders();
                player.npcAnimate(3484, ApeAtollCourse.MONKEY_IDS);
                player.getMovement().force(0, 1, 0, 0, 5, 35, Direction.NORTH);
                e.delay(1);
                player.getStats().addXp(StatType.Agility, 15.0, true);
                player.unlock();
            });
        });
        ObjectAction.register(28727, 2504, 9202, 1, "swing across", (player, obj) -> {
            if (!ApeAtollCourse.checkGreeGree(player)) {
                player.dialogue(new PlayerDialogue("They're called monkey bars for a reason!"));
                return;
            }
            if (!player.getStats().check(StatType.Agility, 55, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.npcAnimate(3482, ApeAtollCourse.MONKEY_IDS);
                player.getMovement().force(0, -1, 0, 0, 5, 35, Direction.SOUTH);
                e.delay(1);
                player.getAppearance().setCustomRenders(Renders.APE_ATOLL_MONKEY_BARS);
                e.delay(1);
                for (int index = 0; index < 12; index++) {
                    player.getMovement().force(0, -1, 0, 0, 5, 35, Direction.SOUTH);
                    e.delay(1);
                }
                player.getAppearance().restoreNPCRenders();
                player.npcAnimate(3484, ApeAtollCourse.MONKEY_IDS);
                player.getMovement().force(0, -1, 0, 0, 5, 35, Direction.SOUTH);
                e.delay(1);
                player.getStats().addXp(StatType.Agility, 15.0, true);
                player.unlock();
            });
        });
    }
}
