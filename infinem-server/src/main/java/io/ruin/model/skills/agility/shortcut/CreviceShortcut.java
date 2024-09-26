package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum CreviceShortcut {
    ZMI_1(1, 0, new Position(3056, 5586, 0), new Position(3053, 5588, 0), false),
    FALADOR_42(42, 1, Position.of(3028, 9806), Position.of(3035, 9806), true),
    ZANARIS_46(46, 1, new Position(2400, 4404, 0), new Position(2400, 4402, 0), false),
    ZANARIS_66(46, 1, new Position(2409, 4402, 0), new Position(2409, 4400, 0), false),
    HEROES_GUILD(67, 1, new Position(2899, 9902), new Position(2914, 9894), true),
    IORWERTH_DUNGEON_78(78, 1, new Position(3216, 12441), new Position(3222, 12441), true),
    IORWERTH_DUNGEON_84(84, 1, new Position(3242, 12420), new Position(3232, 12420, 0), true)
    ;

    private final int levelReq, xp;
    private final Position startPosition, endPosition;
    private final boolean useMidPoint;

    public void squeeze(Player player, GameObject obj){
        if (!player.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        player.startEvent( e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.face(obj);
            player.animate(2594);
            Position target = player.getPosition().equals(startPosition) ? endPosition : startPosition;
            e.delay(1);
            if (useMidPoint) {
                player.getMovement().teleport(
                        (startPosition.getX() + endPosition.getX()) / 2,
                        (startPosition.getY() + endPosition.getY()) / 2,
                        startPosition.getZ());
                e.delay(2);
            }
            player.getMovement().teleport(target);
            player.animate(2595);
            e.delay(1);
            player.getStats().addXp(StatType.Agility, xp, true);
            if (this == ZANARIS_66)
                player.getTaskManager().doLookupByUUID(366);    // Take the Advanced Shortcut to the Cosmic Altar
            player.unlock();
        });
    }

    static {
        // ZMI pathing
        Tile.getObject(29626, 3053, 5587, 0).walkTo = new Position(3053, 5588, 0);
        Tile.getObject(29627, 3055, 5586, 0).walkTo = new Position(3056, 5586, 0);
        ObjectAction.register(29626, 3053, 5587, 0, "Squeeze-through", CreviceShortcut.ZMI_1::squeeze);
        ObjectAction.register(29627, 3055, 5586, 0, "Squeeze-through", CreviceShortcut.ZMI_1::squeeze);

        ObjectAction.register(16543, "Squeeze-through", CreviceShortcut.FALADOR_42::squeeze);
        ObjectAction.register(17002, 2400, 4403, 0, "Squeeze-past", CreviceShortcut.ZANARIS_46::squeeze);
        ObjectAction.register(17002, 2409, 4401, 0, "Squeeze-past", CreviceShortcut.ZANARIS_66::squeeze);
        ObjectAction.register(9739, 2899, 9901, 0, "use", CreviceShortcut.HEROES_GUILD::squeeze);
        ObjectAction.register(9740, 2914, 9895, 0, "use", CreviceShortcut.HEROES_GUILD::squeeze);
        ObjectAction.register(36692, 3221, 12441, 0, "pass", CreviceShortcut.IORWERTH_DUNGEON_78::squeeze);
        ObjectAction.register(36693, 3217, 12441, 0, "pass", CreviceShortcut.IORWERTH_DUNGEON_78::squeeze);
        ObjectAction.register(36694, 3241, 12420, 0, "pass", CreviceShortcut.IORWERTH_DUNGEON_84::squeeze);
        ObjectAction.register(36695, 3233, 12420, 0, "pass", CreviceShortcut.IORWERTH_DUNGEON_84::squeeze);
    }
}