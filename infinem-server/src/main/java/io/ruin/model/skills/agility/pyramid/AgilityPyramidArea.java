package io.ruin.model.skills.agility.pyramid;

import io.ruin.model.World;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class AgilityPyramidArea {

    public static Position getHigherTile(Position position) {
        if(position.getZ() == 3) {
            return position.relative(-320, 1856, -1);
        }
        return position.relative(0, 0, 1);
    }

    public static Position getLowerTile(Position position) {
        if (position.getY() >= 4686 && position.getY() <= 4709 && position.getZ() == 2) {
            return position.relative(320, -1856, 1);
        }
        return position.relative(0, 0, -1);
    }
}
