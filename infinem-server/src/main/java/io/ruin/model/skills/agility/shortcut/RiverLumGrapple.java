package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/27/2023
 */
public class RiverLumGrapple {

    private static final int SPLASH_GFX = 68, SHOOT_ANIM = 4230, SWIM_ANIM = 4467;
    private static final GameObject EAST_TREE = new GameObject(17036, new Position(3260, 3178, 0), 10, 2);
    private static final GameObject EAST_TREE_ROPE = new GameObject(17038, new Position(3260, 3178, 0), 10, 2);
    private static final GameObject WEST_TREE = new GameObject(17039, new Position(3244, 3179, 0), 10, 0);
    private static final GameObject WEST_TREE_ROPE = new GameObject(17040, new Position(3244, 3179, 0), 10, 0);
    private static final Position WEST_LANDING = new Position(3246, 3179, 0);
    private static final Position EAST_LANDING = new Position(3259, 3179, 0);
    private static final Position WEST_PLATFORM = new Position(3252, 3180, 0);
    private static final Position EAST_PLATFORM = new Position(3253, 3179, 0);

    private static final List<Position> EAST_ROPE = Arrays.asList(
            new Position(3254, 3179, 0), new Position(3255, 3179, 0), new Position(3256, 3179, 0),
            new Position(3257, 3179, 0), new Position(3258, 3179, 0), new Position(3259, 3179, 0)
    );

    private static final List<Position> WEST_ROPE = Arrays.asList(
            new Position(3251, 3179, 0), new Position(3250, 3179, 0), new Position(3249, 3179, 0),
            new Position(3248, 3179, 0), new Position(3247, 3179, 0), new Position(3246, 3179, 0)
    );

    private static void grapple(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, 8, "grapple") ||
                !player.getStats().check(StatType.Strength, 18, "grapple") ||
                !player.getStats().check(StatType.Ranged, 37, "grapple")) {
            return;
        }
        if (!player.getEquipment().hasId(9419)) {
            player.sendMessage("You need a mithril grapple tipped bolt with a rope to do that.");
            return;
        }
        if (IntStream.of(Grappling.CROSSBOWS).noneMatch(player.getEquipment()::hasId)) {
            player.sendMessage("You need a crossbow equipped to do that.");
            return;
        }
        player.lock();
        boolean west = player.getAbsX() > object.x;
        player.startEvent(e -> {
            player.animate(SHOOT_ANIM);
            e.delay(3);
            // Spawn rope tree
            (west ? EAST_TREE_ROPE : WEST_TREE_ROPE).spawn();
            // Spawn rope
            for (Position tile : west ? EAST_ROPE : WEST_ROPE) {
                new GameObject(17034, tile, 22, 0).spawn();
            }
            Position target = west ? EAST_PLATFORM : new Position(3251, 3179, 0);
            if (west) {
                player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
                e.delay(1);
                player.getAppearance().setCustomRenders(Renders.SWIM);
                player.graphics(SPLASH_GFX);
                e.delay(5);
                player.getAppearance().removeCustomRenders();
            } else {    // To correct wrong pathing
                player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
                e.delay(2);
                player.getAppearance().setCustomRenders(Renders.SWIM);
                player.graphics(SPLASH_GFX);
                e.delay(3);
                player.stepAbs(WEST_PLATFORM.getX(), WEST_PLATFORM.getY(), StepType.FORCE_WALK);
                e.delay(1);
                player.getAppearance().removeCustomRenders();
            }
            player.getRouteFinder().routeAbsolute(west ? WEST_PLATFORM.getX() : EAST_PLATFORM.getX(), west ? WEST_PLATFORM.getY() : EAST_PLATFORM.getY());
            e.delay(2);
            // Remove rope tree
            (west ? EAST_TREE : WEST_TREE).spawn();
            // Remove previous rope
            for (Position tile : west ? EAST_ROPE : WEST_ROPE) {
                new GameObject(-1, tile, 22, 0).spawn();
            }
            e.delay(1);
            player.face(west ? WEST_TREE : EAST_TREE);
            player.animate(SHOOT_ANIM);
            e.delay(3);
            (west ? WEST_TREE_ROPE : EAST_TREE_ROPE).spawn();
            for (Position tile : west ? WEST_ROPE : EAST_ROPE) {
                new GameObject(17034, tile, 22, 0).spawn();
            }
            target = west ? WEST_LANDING : EAST_LANDING;
            player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(Renders.SWIM);
            player.graphics(SPLASH_GFX);
            e.delay(west ? 4 : 5);
            player.getAppearance().removeCustomRenders();
            e.delay(west ? 2 : 1);
            // Remove rope tree
            (west ? WEST_TREE : EAST_TREE).spawn();
            // Remove previous rope
            for (Position tile : west ? WEST_ROPE : EAST_ROPE) {
                new GameObject(-1, tile, 22, 0).spawn();
            }
            player.getTaskManager().doLookupByUUID(930);    // Grapple across the River Lum
            player.unlock();
        });
    }

    static {
        Tile.getObject(17068, 3252, 3179, 0).nearPosition = (player, object) -> player.getAbsX() > object.x ? new Position(3259, 3179, 0) : new Position(3246, 3179, 0);
        ObjectAction.register(17068, 3252, 3179, 0, "grapple", RiverLumGrapple::grapple);
        LoginListener.register(player -> {
            if (player.getPosition().distance(new Position(3252, 3179, 0)) <= 6 && !player.getPosition().equals(3258, 3176, 0))
                player.getMovement().teleport(3246, 3179, 0);
        });
    }
}
