package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UnderwallTunnel {
    YANILLE_NORTH(16519, new Position(2575, 3108, 0), "climb-under", (player, obj) -> shortcut(player, 16, Direction.NORTH, 5)),
    YANILLE_SOUTH(16520, new Position(2575, 3111, 0), "climb-into", (player, obj) -> shortcut(player, 16, Direction.SOUTH, 5)),
    PISCATORIS_NORTH(12656, new Position(2344, 3651, 0), "enter", (player, obj) -> shortcut(player, 1, Direction.NORTH, 5)),
    PISCATORIS_SOUTH(12656, new Position(2344, 3654, 0), "enter", (player, obj) -> shortcut(player, 1, Direction.SOUTH, 5)),
    GRAND_EXCHANGE_SE(16529, new Position(3138, 3516, 0), "climb-into", (player, obj) -> shortcutNWToSE(player, obj, 21)),
    GRAND_EXCHANGE_NW(16530, new Position(3141, 3513, 0), "climb-into", (player, obj) -> shortcutNWToSE(player, obj, 21)),
    FALADOR_SOUTH(16528, new Position(2948, 3312, 0), "climb-into", (player, obj) -> shortcut(player, 26, Direction.SOUTH)),
    FALADOR_NORTH(16527, new Position(2948, 3310, 0), "climb-into", (player, obj) -> shortcut(player, 26, Direction.NORTH)),
    DRAYNOR_WEST(19036, new Position(3069, 3260, 0), "climb-into", (player, obj) -> shortcut(player, 42, Direction.WEST, 5)),
    DRAYNOR_EAST(19032, new Position(3066, 3260, 0), "climb-into", (player, obj) -> shortcut(player, 42, Direction.EAST, 5)),

    ;

    final int objectId;
    final Position objectPos;
    final String option;
    final ObjectAction action;

    private static void shortcutNWToSE(Player p, GameObject wall, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            if(wall.id == 16530) {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(2589);
                p.getMovement().force(-1, 0, 0, 0, 0, 50, Direction.WEST);
                e.delay(2);
                p.animate(2590);
                p.getMovement().force(-3, 3, 0, 0, 0, 100, Direction.WEST);
                e.delay(3);
                p.animate(2591);
                p.getMovement().force(-1, 0, 0, 0, 15, 33, Direction.WEST);
                e.delay(1);
                p.getStats().addXp(StatType.Agility, 1, true);
                p.unlock();
            } else {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(2589);
                p.getMovement().force(1, 0, 0, 0, 0, 50, Direction.EAST);
                e.delay(2);
                p.animate(2590);
                p.getMovement().force(3, -3, 0, 0, 0, 100, Direction.EAST);
                e.delay(3);
                p.animate(2591);
                p.getMovement().force(1, 0, 0, 0, 15, 33, Direction.EAST);
                e.delay(1);
                p.getStats().addXp(StatType.Agility, 1, true);
                p.unlock();
            }
        });
    }

    private static void shortcut(Player p, int levelReq, Direction moveDirection) {
        shortcut(p, levelReq, moveDirection, 4);
    }

    private static void shortcut(Player player, int levelReq, Direction moveDirection, int moveTiles) {
        if (!player.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        int verticalMult = moveDirection == Direction.NORTH ? 1 : moveDirection == Direction.SOUTH ? -1 : 0;
        int horizontalMult = moveDirection == Direction.EAST ? 1 : moveDirection == Direction.WEST ? -1 : 0;
        player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            e.delay(1);
            player.animate(2589);
            player.getMovement().force(horizontalMult * moveTiles, verticalMult * moveTiles, 0, 0, 0, 180, moveDirection);
            e.delay(2);
            player.animate(2590);
            e.delay(2);
            player.animate(2591);
            e.delay(1);
            if (levelReq > 1)
                player.getStats().addXp(StatType.Agility, 1, true);
            player.unlock();
        });
    }

    static {
        for (UnderwallTunnel tunnel : values()) {
            ObjectAction.register(tunnel.objectId, tunnel.objectPos, tunnel.option, tunnel.action);
        }
        Tile.getObject(19036, 3069, 3260, 0).nearPosition = (player, object) -> new Position(3070, 3260, 0);
        Tile.getObject(19032, 3066, 3260, 0).nearPosition = (player, object) -> new Position(3065, 3260, 0);
    }
}
