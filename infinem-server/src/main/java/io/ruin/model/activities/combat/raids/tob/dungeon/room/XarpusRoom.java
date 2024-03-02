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
public class XarpusRoom extends TheatreRoom {

    public XarpusRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(12612, 2);
    }

    @Override
    public void registerObjects() {

    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3157), convertY(4383), 1),
                Position.of(convertX(3157), convertY(4391), 1),
                Position.of(convertX(3183), convertY(4391), 1),
                Position.of(convertX(3183), convertY(4383), 1)
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3170, 4375, 0);
    }

}
