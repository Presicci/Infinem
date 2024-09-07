package io.ruin.model.activities.combat.pestcontrol;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/7/2024
 */
public class PestNPC extends NPC {

    public PestNPC(int id, NPC portal) {
        super(id);
        if (id >= 1709 && id <= 1713) {
            putTemporaryAttribute("PORTAL", portal);
        }
    }

    @Override
    public NPC spawn(Position position) {
        walkBounds = new Bounds(position, 3);
        return spawn(position.getX(), position.getY(), position.getZ(), Direction.SOUTH, 0);
    }
}
