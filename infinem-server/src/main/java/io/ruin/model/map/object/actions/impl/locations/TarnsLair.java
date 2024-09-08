package io.ruin.model.map.object.actions.impl.locations;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/8/2024
 */
public class TarnsLair {

    private static void jump(Player player, GameObject object) {
        int x = player.getAbsX() > object.x ? -2 : player.getAbsX() == object.x ? 0 : 2;
        int y = player.getAbsY() > object.y ? -2 : player.getAbsY() == object.y ? 0 : 2;
        Direction dir = Direction.getDirection(player.getPosition(), player.getPosition().relative(x, y));
        player.getMovement().force(x, y, 0, 0, 15, 50, dir);
        player.animate(769);
    }

    static {
        // West start
        Tile.getObject(20549, 3182, 4596, 1).skipReachCheck = pos -> pos.equals(3180, 4596) || pos.equals(3184, 4598);
        Tile.getObject(20550, 3182, 4600, 1).skipReachCheck = pos -> pos.equals(3180, 4600) || pos.equals(3184, 4600) || pos.equals(3184, 4598);
        Tile.getObject(20551, 3184, 4600, 1).skipReachCheck = pos -> pos.equals(3182, 4600) || pos.equals(3184, 4598) || pos.equals(3186, 4598);
        Tile.getObject(20552, 3184, 4598, 1).skipReachCheck = pos -> pos.equals(3182, 4596) || pos.equals(3184, 4600) || pos.equals(3186, 4598) || pos.equals(3186, 4596) || pos.equals(3182, 4600);
        Tile.getObject(20553, 3186, 4598, 1).skipReachCheck = pos -> pos.equals(3184, 4598) || pos.equals(3186, 4596) || pos.equals(3188, 4596) || pos.equals(3188, 4600) || pos.equals(3184, 4600);
        Tile.getObject(20554, 3186, 4596, 1).skipReachCheck = pos -> pos.equals(3186, 4598) || pos.equals(3188, 4596) || pos.equals(3184, 4598);
        Tile.getObject(20555, 3188, 4596, 1).skipReachCheck = pos -> pos.equals(3186, 4596) || pos.equals(3190, 4596) || pos.equals(3186, 4598);
        Tile.getObject(20556, 3188, 4600, 1).skipReachCheck = pos -> pos.equals(3190, 4600) || pos.equals(3186, 4598);
        Tile.getObject(20564, 3180, 4596, 1).walkTo = new Position(3182, 4596, 1);
        Tile.getObject(20565, 3180, 4600, 1).walkTo = new Position(3182, 4600, 1);
        Tile.getObject(20566, 3190, 4596, 1).walkTo = new Position(3188, 4596, 1);
        Tile.getObject(20567, 3190, 4600, 1).walkTo = new Position(3188, 4600, 1);

        ObjectAction.register(20549, "jump-to", TarnsLair::jump);
        ObjectAction.register(20550, "jump-to", TarnsLair::jump);
        ObjectAction.register(20551, "jump-to", TarnsLair::jump);
        ObjectAction.register(20552, "jump-to", TarnsLair::jump);
        ObjectAction.register(20553, "jump-to", TarnsLair::jump);
        ObjectAction.register(20554, "jump-to", TarnsLair::jump);
        ObjectAction.register(20555, "jump-to", TarnsLair::jump);
        ObjectAction.register(20556, "jump-to", TarnsLair::jump);
        ObjectAction.register(20564, "jump-to", TarnsLair::jump);
        ObjectAction.register(20565, "jump-to", TarnsLair::jump);
        ObjectAction.register(20566, "jump-to", TarnsLair::jump);
        ObjectAction.register(20567, "jump-to", TarnsLair::jump);
    }
}
