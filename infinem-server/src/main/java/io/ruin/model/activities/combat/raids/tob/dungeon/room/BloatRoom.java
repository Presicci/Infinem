package io.ruin.model.activities.combat.raids.tob.dungeon.room;

import com.google.common.collect.Lists;
import io.ruin.model.activities.combat.raids.tob.party.TheatreParty;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.List;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class BloatRoom extends TheatreRoom {

    public BloatRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(13125, 1);
    }

    @Override
    public void registerObjects() {
        for (int y = 4446; y < 4450; y++) {
            ObjectAction.register(32755, convertX(3304), convertY(y), 0, "pass", (player, obj) -> {
                boolean west = player.getAbsX() > convertX(3304);
                Direction dir = west ? Direction.WEST : Direction.EAST;
                if (!player.getCombat().isDefending(25)) {
                    player.getMovement().force(west ? -1 : 1, 0, 0, 0, 10, 30, dir);
                } else {
                    player.sendMessage("You can't pass this barrier while in combat.");
                }
            });
            ObjectAction.register(32755, convertX(3287), convertY(y), 0, "pass", (player, obj) -> {
                boolean west = player.getAbsX() > convertX(3287);
                Direction dir = west ? Direction.WEST : Direction.EAST;
                if (!player.getCombat().isDefending(25)) {
                    player.getMovement().force(west ? -1 : 1, 0, 0, 0, 10, 30, dir);
                } else {
                    player.sendMessage("You can't pass this barrier while in combat.");
                }
            });
        }
        ObjectAction.register(33113, convertX(3269), convertY(4447), 0, "enter", (player, obj) -> {

        });
        ObjectAction.register(33113, convertX(3269), convertY(4447), 0, "quick-enter", (player, obj) -> {

        });
    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3296), convertY(4436)),
                Position.of(convertX(3295), convertY(4436)),
                Position.of(convertX(3296), convertY(4459)),
                Position.of(convertX(3295), convertY(4459))

        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3321, 4448, 0);
    }

}
