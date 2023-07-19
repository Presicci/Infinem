package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.World;
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
public enum LogBalance {
    // TODO get coordinates
    KARAMJA_LOG1(new RegisterObject[]{
            new RegisterObject(23644, 0, 0) },
            1,1, "cross",
            Position.of(2906, 3049), Position.of(2910, 3049)),
    CAMELOT_LOG20(new RegisterObject[]{
            new RegisterObject(23274, 0, 0) },
            20, 1, "walk-across",
            Position.of(2598, 3477), Position.of(2603, 3477)),
    BRIMHAVEN_LOG30(new RegisterObject[]{
            new RegisterObject(20882, 0, 0),
            new RegisterObject(20884, 0, 0) },
            30, 1, "walk-across",
            Position.of(2682, 9506), Position.of(2687, 9506)),
    ARDY_LOG33(new RegisterObject[]{
            new RegisterObject(16548, 0, 0),
            new RegisterObject(16546, 0, 0) },
            33, 1, "walk-across",
            Position.of(2602, 3336), Position.of(2598, 3336)),
    ISAFDAR_1_LOG45(new RegisterObject[]{
            new RegisterObject(3931, 0, 0) },
            45, 1, "cross",
            Position.of(2202, 3237), Position.of(2196, 3237)),
    ISAFDAR_2_LOG45(new RegisterObject[]{
            new RegisterObject(3932, 0, 0), },
            45, 1, "cross",
            Position.of(2258, 3250), Position.of(2264, 3250)),
    ISAFDAR_3_LOG45(new RegisterObject[]{
            new RegisterObject(3933, 0, 0) },
            45, 1, "cross",
            Position.of(2290, 3232), Position.of(2290, 3239)),
    CAMELOT_LOG48(new RegisterObject[]{
            new RegisterObject(16542, 0, 0),
            new RegisterObject(16540, 0, 0) },
            48, 1, "walk-across",
            Position.of(2722, 3592), Position.of(2722, 3596))
    ;

    private final RegisterObject[] objects;
    private final int levelReq, xp;
    private final String option;
    private final Position startPosition, endPosition;

    public void traverse(Player player, GameObject obj){
        player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            Position target = player.getPosition().isWithinDistance(startPosition, 1) ? endPosition : startPosition;
            int distance = startPosition.distance(endPosition);
            player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
            e.delay(distance + 1);
            if(World.isEco())
                player.getStats().addXp(StatType.Agility, xp, true);
            player.getAppearance().removeCustomRenders();
            player.unlock();
        });
    }

    static {
        for (LogBalance log : values()) {
            for (RegisterObject object : log.objects) {
                ObjectAction.register(object.getObjectId(), object.getPosition(), log.option, log::traverse);
            }
        }
    }
}