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
        // Western pillar pit
        Tile.getObject(20542, 3148, 4597, 1).skipReachCheck = pos -> pos.equals(3150, 4597) || pos.equals(3146, 4597);
        Tile.getObject(20543, 3148, 4595, 1).skipReachCheck = pos -> pos.equals(3150, 4595) || pos.equals(3146, 4595);
        Tile.getObject(20544, 3146, 4595, 1).skipReachCheck = pos -> pos.equals(3148, 4595) || pos.equals(3148, 4597) || pos.equals(3144, 4595) || pos.equals(3144, 4597);
        Tile.getObject(20545, 3144, 4595, 1).skipReachCheck = pos -> pos.equals(3142, 4595, 1) || pos.equals(3144, 4597, 1) || pos.equals(3146, 4595, 1);
        Tile.getObject(20546, 3142, 4595, 1).skipReachCheck = pos -> pos.equals(3140, 4595, 1) || pos.equals(3144, 4595, 1) || pos.equals(3144, 4597, 1);
        Tile.getObject(20547, 3144, 4597, 1).skipReachCheck = pos -> pos.equals(3144, 4595, 1) || pos.equals(3142, 4595, 1) || pos.equals(3146, 4595, 1) || pos.equals(3144, 4599, 1);
        Tile.getObject(20548, 3144, 4599, 1).skipReachCheck = pos -> pos.equals(3144, 4601, 1) || pos.equals(3144, 4597, 1) || pos.equals(3146, 4597, 1);
        Tile.getObject(20560, 3150, 4597, 1).walkTo = new Position(3148, 4597, 1);
        Tile.getObject(20561, 3150, 4595, 1).walkTo = new Position(3148, 4595, 1);
        Tile.getObject(20562, 3140, 4595, 1).walkTo = new Position(3142, 4595, 1);
        Tile.getObject(20563, 3144, 4601, 1).walkTo = new Position(3144, 4599, 1);
        ObjectAction.register(20542, "jump-to", TarnsLair::jump);
        ObjectAction.register(20543, "jump-to", TarnsLair::jump);
        ObjectAction.register(20543, "jump-to", TarnsLair::jump);
        ObjectAction.register(20544, "jump-to", TarnsLair::jump);
        ObjectAction.register(20545, "jump-to", TarnsLair::jump);
        ObjectAction.register(20546, "jump-to", TarnsLair::jump);
        ObjectAction.register(20547, "jump-to", TarnsLair::jump);
        ObjectAction.register(20548, "jump-to", TarnsLair::jump);
        ObjectAction.register(20561, "jump-to", TarnsLair::jump);
        ObjectAction.register(20562, "jump-to", TarnsLair::jump);
        ObjectAction.register(20563, "jump-to", TarnsLair::jump);


        // Eastern Pillar pit
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
