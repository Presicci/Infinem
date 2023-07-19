package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/12/2023
 */
@AllArgsConstructor
public enum CrawlUnder {

    WEISS_DOCK_TREE(33192, new Position(2857, 3955), 68, 1, Position.of(2857, 3957), Position.of(2857, 3954));

    private final int objectId;
    private final Position objectPos;
    private final int level;
    private final double exp;
    private final Position to, from;

    public void traverse(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, level, "crawl under"))
            return;
        player.startEvent((event) -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            Position destination = player.getPosition().equals(to) ? from : to;
            Direction dir = Direction.getDirection(player.getPosition(), destination);
            player.animate(2796);
            player.getMovement().force(destination, 0, 90, dir);
            event.delay(player.getPosition().distance(destination));
            player.getStats().addXp(StatType.Agility, exp, true);
            player.unlock();
        });
    }

    static {
        for (CrawlUnder crawl : values()) {
            ObjectAction.register(crawl.objectId, crawl.objectPos, "pass", crawl::traverse);
        }
    }
}
