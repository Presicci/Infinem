package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.Random;
import io.ruin.model.content.ActivitySpotlight;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;

import java.util.List;

public class MarkOfGrace {

    public static void rollMark(Player player, int levelReq, List<Position> spawns) {
        if (spawns == null)
            return;
        double chance = levelReq / 100D;
        if (Random.get() <= chance) {
            Position spawn = Random.get(spawns);
            int amount = Random.get(1, 4) * (ActivitySpotlight.isActive(ActivitySpotlight.DOUBLE_MARKS_OF_GRACE) ? 2 : 1);
            if (player.getRelicManager().hasRelicEnalbed(Relic.TRICKSTER) && player.getInventory().hasRoomFor(11849)) {
                player.getInventory().add(Items.MARK_OF_GRACE, amount);
                player.getCollectionLog().collect(Items.MARK_OF_GRACE, amount);
                player.getTaskManager().doLookupByUUID(52); // Obtain a Mark of Grace
            } else {
                new GroundItem(new Item(Items.MARK_OF_GRACE, amount)).owner(player).position(spawn).addToCL().spawn(2);
            }
        }
    }
}
