package io.ruin.model.activities.combat.raids.tob.dungeon.room;

import com.google.common.collect.Lists;
import io.ruin.model.activities.combat.raids.tob.party.TheatreParty;
import io.ruin.model.map.Position;

import java.util.List;

/**
 * @author ReverendDread on 7/24/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class SotetsegRoom extends TheatreRoom {

    public SotetsegRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(13123, 1);
    }

    @Override
    public void registerObjects() {

    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3270), convertY(4313)),
                Position.of(convertX(3270), convertY(4314)),
                Position.of(convertX(3289), convertY(4314)),
                Position.of(convertX(3289), convertY(4313))
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3291, 4328, 0);
    }

}
