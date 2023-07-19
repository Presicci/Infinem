package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.RegisterObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum MonkeyBar {

    YANILLE_DUNGEON_BARS_1(new RegisterObject[]{
            new RegisterObject(23567, 2597, 9494),
            new RegisterObject(23567, 2598, 9494) },
            57, 1, Position.of(2598, 9494), Position.of(2598, 9489)),
    YANILLE_DUNGEON_BARS_2(new RegisterObject[]{
            new RegisterObject(23567, 2597, 9489),
            new RegisterObject(23567, 2598, 9489) },57, 1, Position.of(2599, 9489), Position.of(2599, 9494));

    private final RegisterObject[] objects;
    private final int level;
    private final double exp;
    private final Position from, to;

    public void traverse(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, level, "Swing across"))
            return;
        player.startEvent((event) -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            event.path(player, from);
            player.face(to.getX(), to.getY());
            event.delay(1);
            player.animate(742);
            event.delay(1);
            player.getAppearance().setCustomRenders(Renders.MONKEY_BARS);
            int stepsNeeded = object.getPosition().distance(to);
            for (int step = 0; step < stepsNeeded; step++) {
                player.stepAbs(to.getX(), to.getY(), StepType.FORCE_WALK);
                player.privateSound(2451, 2, 0);
                event.delay(1);
            }
            event.delay(1);
            player.animate(743);
            player.getAppearance().removeCustomRenders();
            event.delay(1);
            player.getStats().addXp(StatType.Agility, exp, true);
            player.unlock();
        });
    }

    static {
        for (MonkeyBar bar : values()) {
            for (RegisterObject object : bar.objects) {
                ObjectAction.register(object.getObjectId(), object.getPosition(), "swing across", bar::traverse);
            }
        }
    }
}