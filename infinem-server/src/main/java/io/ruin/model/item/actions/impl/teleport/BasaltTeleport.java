package io.ruin.model.item.actions.impl.teleport;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.stat.StatType;

import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2024
 */
public class BasaltTeleport {

    public static final Position WEISS = new Position(2846, 3939, 0);
    public static final Position TROLL_STRONGHOLD = new Position(2844, 3693, 0);
    public static final Position TROLL_STRONGHOLD_UPPER = new Position(2838, 3693, 0);

    public static final Predicate<Player> canTeleportUpper = player -> player.getStats().get(StatType.Agility).currentLevel >= 73;

    static {
        ItemAction.registerInventory(22599, "weiss", (player, item) -> {
            player.getMovement().startTeleport(event -> {
                player.lock(LockType.FULL_NULLIFY_DAMAGE);
                player.animate(8172);
                player.graphics(1628);
                event.delay(3);
                item.remove(1);
                player.getMovement().teleport(WEISS);
                player.unlock();
            });
        });
        ItemAction.registerInventory(22601, "troll stronghold", (player, item) -> {
            player.getMovement().startTeleport(event -> {
                player.lock(LockType.FULL_NULLIFY_DAMAGE);
                player.animate(8172);
                player.graphics(1617);
                event.delay(3);
                item.remove(1);
                player.getMovement().teleport(canTeleportUpper.test(player) ? TROLL_STRONGHOLD_UPPER : TROLL_STRONGHOLD);
                player.unlock();
            });
        });
    }
}
