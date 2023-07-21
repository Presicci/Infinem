package io.ruin.model.activities.combat.godwars;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class GodwarsEntrance {

    static {
        ObjectAction.register(26419, 1, (player, obj) -> {
            if (Config.GODWARS_DUNGEON.get(player) == 0) {
                Item rope = player.getInventory().findItem(954);
                if(rope == null) {
                    player.sendFilteredMessage("You are not carrying a rope with you.");
                    return;
                }

                rope.remove();
                Config.GODWARS_DUNGEON.set(player, 1);
            } else {
                player.startEvent(event -> {
                    player.lock();
                    player.animate(828);
                    event.delay(1);
                    player.getMovement().teleport(2882, 5311, 2);
                    player.unlock();
                });
            }
        });
        ObjectAction.register(26370, 1, (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(828);
            event.delay(1);
            player.getMovement().teleport(2916, 3746, 0);
            player.unlock();
        }));
        // Boulder
        Tile.getObject(26415, 2898, 3716, 0).nearPosition = (p, obj) -> p.getPosition().getY() <= 3715 ? Position.of(2898, 3715) : Position.of(2898, 3719);
        ObjectAction.register(26415, "Move", ((player, obj) -> {
            if (player.getStats().get(StatType.Strength).currentLevel < 60) {
                player.sendMessage("You need a Strength level of at least 60 to move this boulder.");
                return;
            }
            boolean north = player.getAbsY() <= 3715;
            player.startEvent(e -> {
                player.lock();
                player.animate(north ? 6983 : 6984);
                e.delay(1);
                obj.animate(6985);
                player.getMovement().force(0, north ? 4 : -4, 0, 0, 0, 400, north ? Direction.NORTH : Direction.SOUTH);
                e.delay(12);
                obj.animate(6986);
                player.unlock();
            });
        }));
        ObjectAction.register(26382, "crawl-through", ((player, obj) -> {
            if (player.getStats().get(StatType.Agility).currentLevel < 60) {
                player.sendMessage("You need an Agility level of at least 60 to use this shortcut.");
                return;
            }
            player.animate(844, 10);
            Traveling.fadeTravel(player, player.getPosition().getX() == 2899 ? new Position(2904, 3720) : new Position(2899, 3713), 4);
        }));
    }
}
