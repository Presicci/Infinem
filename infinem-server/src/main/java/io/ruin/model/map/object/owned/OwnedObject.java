package io.ruin.model.map.object.owned;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import lombok.Getter;

import java.util.Optional;

/**
 * @author ReverendDread on 7/9/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Getter
public abstract class OwnedObject extends GameObject {

    //Unique identifer for this object.
    private final String identifier;
    //This objects owner uuid.
    private final String ownerName;

    public OwnedObject(Player owner, String identifier, int id, Position pos, int type, int direction) {
        super(id, pos, type, direction);
        this.ownerName = owner.getName();
        this.identifier = identifier;
    }

    public void destroy() {
        World.deregisterOwnedObject(this);
        this.remove();
    }

    public abstract void tick();

    public boolean isOwner(Player player) {
        return player.getName().equalsIgnoreCase(ownerName);
    }

    public Player getOwner() {
        return World.getPlayer(ownerName);
    }

    public Optional<Player> getOwnerOpt() {
        return World.getPlayerByName(ownerName);
    }

}


