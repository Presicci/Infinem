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

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum JumpShortcut {
    LUMBERYARD1(2618, new Position(3308, 3492), 1, 1, "climb-over",
            new Position(3308, 3491, 0), new Position(3308, 3493, 0)),
    SHEEP_JUMP1(12982, new Position(3197, 3276), 1,1, "climb-over",
            new Position(3197, 3278), new Position(3197, 3275)),
    TROLL_W_JUMP1(3748, new Position(2833, 3628), 1,1, "climb",
            new Position(2833, 3627), new Position(2833, 3629)),
    TROLL_E_JUMP1(3748, new Position(2834, 3628), 1,1, "climb",
            new Position(2834, 3627), new Position(2834, 3629)),
    TROLL_FAR_W_JUMP1(3748, new Position(2821, 3635), 1,1, "climb",
            new Position(2820, 3635), new Position(2822, 3635)),
    CABBAGE_JUMP1(7527, new Position(3063, 3282), 1,1, "climb-over",
            new Position(3063, 3281), new Position(3063, 3284)),
    ARDY_JUMP1(993, new Position(2647, 3375), 1,1, "climb-over",
            new Position(2649, 3375), new Position(2646, 3375)),
    BURGH_JUMP1(12776, new Position(3474, 3221), 1,1, "jump-over",
            new Position(3473, 3221), new Position(3474, 3221)),
    FALADOR_JUMP5(24222, new Position(2935, 3355), 5,1, "climb-over",
            new Position(2936, 3355), new Position(2934, 3355)),
    CORSAIR_JUMP10(31757, new Position(2546, 2872), 10,1, "climb",
            new Position(2546, 2873), new Position(2546, 2871)),
    VARROCK_JUMP13(16518, new Position(3240, 3335), 13,1, "jump-over",
            new Position(3240, 3334), new Position(3240, 3335)),
    TROLL_S_JUMP44(3748, new Position(2910, 3686), 44,1, "climb",
            new Position(2911, 3686), new Position(2909, 3686)),
    TROLL_N_JUMP44(3748, new Position(2910, 3687), 44,1, "climb",
            new Position(2911, 3687), new Position(2909, 3687)),
    DEATH_PLATEAU_JUMP1(3748, new Position(2856, 3612), 1,1, "climb",
            new Position(2856, 3613), new Position(2856, 3611)),
    DEATH_PLATEAU_JUMP2(3748, new Position(2857, 3612), 1,1, "climb",
            new Position(2857, 3613), new Position(2857, 3611)),
    ZEAH_JUMP49(27990, new Position(1776, 3883), 49,1, "jump",
            new Position(1776, 3884), new Position(1776, 3880)),
    ZEAH_JUMP69(34741, new Position(1761, 3873), 69,1, "jump",
            new Position(1761, 3874), new Position(1761, 3872)),
    FALCONRY1(19222, new Position(2371, 3620), 1, 0, "climb-over",
            new Position(2371, 3622), new Position(2371, 3619)),
    WEISS1(33312, new Position(2851, 3936), 1, 0, "cross",
            new Position(2852, 3936), new Position(2850, 3936));

    private final int objectId;
    private final Position objectPos;
    private final int levelReq, xp;
    private final String option;
    private final Position startPosition, endPosition;

    public void traverse(Player p, GameObject obj){
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        if(p.getPosition() != startPosition){

        }
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(839);
            Position pos = p.getPosition().equals(startPosition) ? endPosition : startPosition;
            int xDiff = pos.getX() - p.getPosition().getX();
            int yDiff = pos.getY() - p.getPosition().getY();

            p.getMovement().force(xDiff, yDiff, 0, 0, 5, 80, Direction.getDirection(p.getPosition(), pos));
            e.delay(2);
            p.getStats().addXp(StatType.Agility, xp, true);
            p.unlock();
        });
    }

    static {
        for (JumpShortcut shortcut : values()) {
            ObjectAction.register(shortcut.objectId, shortcut.objectPos, shortcut.option, (shortcut::traverse));
            Tile.getObject(shortcut.objectId, shortcut.objectPos).skipReachCheck = position -> position.equals(shortcut.startPosition) || position.equals(shortcut.endPosition);
            Tile.getObject(shortcut.objectId, shortcut.objectPos).nearPosition = (player, obj) -> {
                int val = Integer.compare(player.getPosition().distance(shortcut.startPosition), player.getPosition().distance(shortcut.endPosition));
                return val < 0 ? shortcut.startPosition : shortcut.endPosition;
            };
        }
    }
}
