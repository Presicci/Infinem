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
public class VasiliasRoom extends TheatreRoom {

    public VasiliasRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(13122, 1);
    }

    @Override
    public void registerObjects() {

    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3304), convertY(4243)),
                Position.of(convertX(3304), convertY(4254)),
                Position.of(convertX(3290), convertY(4257)),
                Position.of(convertX(3287), convertY(4243))
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3295, 4283, 0);
    }

}
