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
public class VerzikRoom extends TheatreRoom {

    public VerzikRoom(TheatreParty party) {
        super(party);
    }

    @Override
    public void onLoad() {
        build(12611, 1);
    }

    @Override
    public void registerObjects() {

    }

    @Override
    public List<Position> getSpectatorSpots() {
        return Lists.newArrayList(
                Position.of(convertX(3161), convertY(4325)),
                Position.of(convertX(3159), convertY(4325)),
                Position.of(convertX(3175), convertY(4325)),
                Position.of(convertX(3177), convertY(4325))
        );
    }

    @Override
    public Position getEntrance() {
        return Position.of(3168, 4298, 0);
    }

}
