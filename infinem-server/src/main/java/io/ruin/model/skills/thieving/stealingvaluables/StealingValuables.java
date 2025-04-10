package io.ruin.model.skills.thieving.stealingvaluables;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/17/2024
 */
public class StealingValuables {
    // 51998 - locked door
    // 857 - victor scratiching back
    // npc walks out, locks door then paths somewhere
    // victor walked to bank
    // at end of time walked back

    private static NPC LAVINIA;
    private static int houseCooldown = 10;

    private static void leaveHouse(NPC npc) {
        npc.lock();
        npc.startEvent(e -> {
            npc.forceText("Time to go check how rich I am!");
            npc.putTemporaryAttribute("BURG_STATE", 1);
            npc.stepAbs(1668, 3095, StepType.FORCE_WALK);
            e.waitForMovement(npc);
            npc.face(Direction.SOUTH);
            npc.forceText("Locking!");
            e.delay(4);
            npc.getMovement().routeNPC(n -> {
                        n.forceText("arrived");
                        n.putTemporaryAttribute("BURG_STATE", 2);
                        n.walkBounds = new Bounds(n.getPosition(), 2);
                        n.unlock();
                    },
                    new Position[] {
                            new Position(1668, 3098), new Position(1667, 3099),
                            new Position(1667, 3104), new Position(1666, 3105),
                            new Position(1665, 3106), new Position(1654, 3107),
                            new Position(1653, 3108), new Position(1649, 3108),
                            new Position(1648, 3109), new Position(1648, 3116)
                    }
            );
        });
    }

    static {
        SpawnListener.register(13312, npc -> LAVINIA = npc);

        /*World.startEvent(e -> {
           while (true) {
               e.delay(5);
               if (LAVINIA == null) continue;
               if (LAVINIA.hasTemporaryAttribute("BURG_STATE")) continue;
               houseCooldown -= 1;
               if (houseCooldown == 0) {
                   houseCooldown = 10;
                   leaveHouse(LAVINIA);
               }
           }
        });*/
    }
}
