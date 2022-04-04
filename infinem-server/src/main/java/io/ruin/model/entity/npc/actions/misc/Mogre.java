package io.ruin.model.entity.npc.actions.misc;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/2/2022
 */
public class Mogre {

    private static final GameObject[] FISHING_SPOT = {
            new GameObject(10087, 2982, 3113, 0, 10, 0),
            new GameObject(10088, 2996, 3106, 0, 10, 0),
            new GameObject(10089, 3005, 3117, 0, 10, 0)
    };

    private static final Position[] NPC_SPAWNS = {
            new Position(2983, 3113),
            new Position(2996, 3107),
            new Position(3004, 3118)
    };

    private static final int MOGRE = 2592, FISHING_EXPLOSIVE = Items.FISHING_EXPLOSIVE_2,
            THROWING_GFX = 50, PROJECTILE_GFX = 49;

    private static final Projectile PROJECTILE = new Projectile(PROJECTILE_GFX, 40, 0, 5, 0, 10, 8, 11);

    private static void lure(Player player, GameObject object, Position position) {
        if (!player.getInventory().contains(FISHING_EXPLOSIVE)) {
            player.dialogue(new MessageDialogue("You need a fishing explosive to lure anything out of the water."));
            return;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(864);    // TODO get a better anim
            player.graphics(THROWING_GFX);
            e.delay(1);
            player.getInventory().remove(FISHING_EXPLOSIVE, 1);
            PROJECTILE.send(player, object.getPosition());
            e.delay(3);
            NPC npc = new NPC(MOGRE);
            npc.removeIfIdle(player);
            npc.spawn(position);
            npc.targetPlayer(player, false);
            npc.getCombat().setAllowRespawn(false);
            npc.forceText("Human scare all da fishies!");
            npc.attackTargetPlayer();
            player.unlock();
        });
    }

    static {
        for (int index = 0; index < 3; index++) {
            GameObject spot = FISHING_SPOT[index];
            Position position = NPC_SPAWNS[index];
            // TODO this skip reach check doesnt work
            Tile.getObject(spot.id, spot.x, spot.y, 0).skipReachCheck = p -> p.isWithinDistance(new Position(spot.x, spot.y), 4);
            ObjectAction.register(spot.id, "lure", ((player, obj) -> lure(player, obj, position)));
            ObjectAction.register(spot.id, "bait", ((player, obj) -> player.sendMessage("Something seems to have scared all the fishes away...")));
        }
    }
}
