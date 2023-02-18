package io.ruin.model.entity.player.gamemode;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/17/2023
 */
@Getter
public class Tileman {
    private final Player player;

    public Tileman(Player player) {
        this.player = player;
    }

    private final Set<Position> collectedTiles = new HashSet<>();

    public boolean processTileMove(Position position) {
        if (hasTileUnlocked(position)) {
            return true;
        }
        if (getFreeTiles() <= 0) {
            return false;
        }
        unlockTile(position);
        player.sendMessage("Tiles:" + getUnlockedTileAmount() + "/" + getMaximumTiles());
        return true;
    }

    public int getUnlockedTileAmount() {
        return collectedTiles.size();
    }

    public int getMaximumTiles() {
        return (int) (player.getStats().totalXp / 1000);
    }

    public int getFreeTiles() {
        return getMaximumTiles() - getUnlockedTileAmount();
    }

    public void unlockTile(Position position) {
        collectedTiles.add(position);
    }

    public boolean hasTileUnlocked(Position position) {
        return collectedTiles.contains(position);
    }
}
