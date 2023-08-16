package io.ruin.model.skills.agility.shortcut;

import com.google.common.collect.Lists;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.RegisterObject;
import io.ruin.model.stat.StatType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public enum SteppingStone {

    LUMBRIDGE_SWAMP_CAVE1(new RegisterObject[]{
            new RegisterObject(5948)},
            1, 0.5, "jump-across",
            Position.of(3204, 9572), Position.of(3208, 9572),
            Position.of(3206, 9572)),
    LUMBRIDGE_SWAMP_CAVE2(new RegisterObject[]{
            new RegisterObject(5949)},
            1, 0.5, "jump-across",
            Position.of(3221, 9556), Position.of(3222, 9553),
            Position.of(3221, 9554)),
    BRIMHAVEN_STONES12(new RegisterObject[]{
            new RegisterObject(21738, 2649, 9561),
            new RegisterObject(21739, 2647, 9558) },
            12, 1, "jump-from",
            Position.of(2649, 9562), Position.of(2647, 9557),
            Position.of(2649, 9561), Position.of(2649, 9560), Position.of(2648, 9560), Position.of(2647, 9560), Position.of(2647, 9559), Position.of(2647, 9558)),

    CORSAIR_COVE_STONES15(new RegisterObject[]{
            new RegisterObject(31809) },
            15, 1, "jump-to",
            Position.of(1981, 8998,1 ), Position.of(1981, 8994, 1),
            Position.of(1981, 8996, 1)),

    KARAMJA_STONES30(new RegisterObject[]{
            new RegisterObject(23645),
            new RegisterObject(23647) },
            30, 1, "cross",
            Position.of(2925, 2951), Position.of(2925, 2947),
            Position.of(2925, 2950), Position.of(2925, 2949), Position.of(2925, 2948)),

    DRAYNOR_STONES31(new RegisterObject[]{
            new RegisterObject(16533) },
            31, 1, "jump-onto",
            Position.of(3149, 3363), Position.of(3154, 3363),
            Position.of(3150, 3363), Position.of(3151, 3363), Position.of(3152, 3363), Position.of(3153, 3363)),

    ZEAH_E_STONES40(new RegisterObject[]{
            new RegisterObject(29729) },
            40, 1, "cross",
            Position.of(1610, 3570), Position.of(1614, 3570),
            Position.of(1612, 3570)),

    ZEAH_W_STONES40(new RegisterObject[]{
            new RegisterObject(29730) },
            40, 1, "cross",
            Position.of(1607, 3571), Position.of(1603, 3571),
            Position.of(1605, 3571)),

    ZEAH_STONES45(new RegisterObject[]{
            new RegisterObject(29728) },
            45, 1, "cross",
            Position.of(1720, 3551), Position.of(1724, 3551),
            Position.of(1722, 3551)),

    MORTMYRE_STONES50(new RegisterObject[]{
            new RegisterObject(13504) },
            50, 1, "cross",
            Position.of(3417, 3325), Position.of(3421, 3323),
            Position.of(3419, 3325)),

    MISCELLANIA_STONES55(new RegisterObject[]{
            new RegisterObject(11768) },
            55, 1, "cross",
            Position.of(2573, 3859), Position.of(2575, 3861),
            Position.of(2573, 3861)),

    MOS_LEHARMLESS_STONES60(new RegisterObject[]{
            new RegisterObject(19042) },
            60, 1, "jump-to",
            Position.of(3715, 2969), Position.of(3708, 2969),
            Position.of(3711, 2969)),

    LUMBRIDGE_STONES66(new RegisterObject[]{
            new RegisterObject(16513) },
            66, 1, "jump-to",
            Position.of(3212, 3137), Position.of(3214, 3132),
            Position.of(3214, 3135)),

    WILDERNESS_LAVADRAG_STONES74(new RegisterObject[]{
            new RegisterObject(14918) },
            74, 1, "cross",
            Position.of(3201, 3807), Position.of(3201, 3810),
            Position.of(3201, 3808)),

    ZULRAH_STONES76(new RegisterObject[]{
            new RegisterObject(10663) },
            76, 1, "cross",
            Position.of(2160, 3072), Position.of(2154, 3072),
            Position.of(2157, 3072)),

    SHILO_STONES77(new RegisterObject[]{
            new RegisterObject(16466) },
            77, 1, "cross",
            Position.of(2863, 2971), Position.of(2863, 2976),
            Position.of(2863, 2974)),

    WILDERNESS_LAVAMAZE_STONES82(new RegisterObject[]{
            new RegisterObject(14917) },
            82, 1, "cross",
            Position.of(3091, 3882), Position.of(3093, 3879),
            Position.of(3092, 3880)),

    BRIMHAVEN_N_STONES83(new RegisterObject[]{
            new RegisterObject(19040, 2684, 9548),
            new RegisterObject(19040, 2688, 9547) },
            83, 1, "cross",
            Position.of(2682, 9548), Position.of(2690, 9547),
            Position.of(2684, 9548), Position.of(2686, 9548), Position.of(2688, 9547)),
    BRIMHAVEN_S_STONES83(new RegisterObject[]{
            new RegisterObject(19040, 2695, 9531),
            new RegisterObject(19040, 2696, 9527) },
            83, 1, "cross",
            Position.of(2695, 9533), Position.of(2697, 9525),
            Position.of(2695, 9531), Position.of(2695, 9529), Position.of(2696, 9527)),
    ;

    SteppingStone(RegisterObject[] objects, int levelReq, double xp, String option, Position startPosition, Position endPosition, Position... steps){
        this.objects = objects;
        this.xp = xp;
        this.levelReq = levelReq;
        this.option = option;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.positions = Arrays.asList(steps);
    }

    private final RegisterObject[] objects;
    private final List<Position> positions;
    private final Position startPosition, endPosition;
    private final double xp;
    private final int levelReq;
    private final String option;

    public void traverse(Player p, GameObject obj){
        if(!p.getStats().check(StatType.Agility, levelReq)){
            p.sendMessage("You need an agility level of " + levelReq + " to navigate this shortcut!");
            return;
        }
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            List<Position> posCopy = Lists.newArrayList(positions);

            Position first = posCopy.get(0);
            if(posCopy.size() == 1){
                if(startPosition.equals(p.getPosition())){
                    posCopy.add(endPosition);
                } else {
                    posCopy.add(startPosition);
                }
            } else if(obj.getPosition().equals(first)){
                posCopy.add(endPosition);
            } else {

                Collections.reverse(posCopy);
                posCopy.add(startPosition);
            }

            for(int index = 0;index<posCopy.size();index++){
                Position pos = posCopy.get(index);
                int xDiff = pos.getX() - p.getPosition().getX();
                int yDiff = pos.getY() - p.getPosition().getY();
                p.animate(741);
                p.getMovement().force(xDiff, yDiff, 0, 0, 5, 35, Direction.getDirection(p.getPosition(), pos));
                if (index != posCopy.size() - 1) e.delay(2);
                else e.delay(1);
            }

            if(World.isEco())
                p.getStats().addXp(StatType.Agility, xp, true);

            p.unlock();
        });
    }

    static {
        for (SteppingStone stone : values()) {
            for (RegisterObject object : stone.objects) {
                object.register(stone.option, stone::traverse);
            }
        }
        // Lumbridge Swamp Cave
        Tile.getObject(5948, 3206, 9572, 0).skipReachCheck = p -> p.equals(3208, 9572) || p.equals(3204, 9572);
        Tile.getObject(5949, 3221, 9554, 0).skipReachCheck = p -> p.equals(3221, 9556) || p.equals(3222, 9553);
        // Brimhaven stones
        Tile.getObject(19040, 2695, 9531, 0).skipReachCheck = p -> p.equals(2695, 9533);
        Tile.getObject(19040, 2696, 9527, 0).skipReachCheck = p -> p.equals(2697, 9525);
        Tile.getObject(19040, 2684, 9548, 0).skipReachCheck = p -> p.equals(2682, 9548);
        Tile.getObject(19040, 2688, 9547, 0).skipReachCheck = p -> p.equals(2690, 9547);
        // Wilderness lava maze
        Tile.getObject(14917, 3092, 3879, 0).skipReachCheck = p -> p.equals(3091, 3882) || p.equals(3093, 3879);
        // Shilo village
        Tile.getObject(16466, 2863, 2974, 0).skipReachCheck = p -> p.equals(2863, 2971) || p.equals(2863, 2976);
        Tile.getObject(16466, 2863, 2974, 0).nearPosition = (p, obj) -> {
            int val = Integer.compare(p.getPosition().distance(Position.of(2863, 2971)), p.getPosition().distance(Position.of(2863, 2976)));
            return val < 0 ? Position.of(2863, 2971) : Position.of(2863, 2976);
        };
        // Zulrah
        Tile.getObject(10663, 2157, 3072, 0).skipReachCheck = p -> p.equals(2160, 3072) || p.equals(2154, 3072);
        // Wilderness lavadrags
        Tile.getObject(14918, 3201, 3808, 0).skipReachCheck = p -> p.equals(3201, 3810) || p.equals(3201, 3807);
        // Lumbridge
        Tile.getObject(16513, 3214, 3135, 0).skipReachCheck = p -> p.equals(3212, 3137) || p.equals(3214, 3132);
        // Mos LeHarmless
        Tile.getObject(19042, 3711, 2969, 0).skipReachCheck = p -> p.equals(3708, 2969) || p.equals(3715, 2969);
        // Miscellania
        Tile.getObject(11768, 2573, 3861, 0).skipReachCheck = p -> p.equals(2573, 3859) || p.equals(2575, 3861);
        // Mort myre
        Tile.getObject(13504, 3419, 3325, 0).skipReachCheck = p -> p.equals(3417, 3325) || p.equals(3421, 3323);
        // Zeah
        Tile.getObject(29728, 1722, 3551, 0).skipReachCheck = p -> p.equals(1720, 3551) || p.equals(1724, 3551);
        Tile.getObject(29730, 1605, 3571, 0).skipReachCheck = p -> p.equals(1607, 3571) || p.equals(1603, 3571);
        Tile.getObject(29729, 1612, 3570, 0).skipReachCheck = p -> p.equals(1610, 3570) || p.equals(1614, 3570);
        // Corsair cove
        Tile.getObject(31809, 1981, 8996, 1).skipReachCheck = p -> p.equals(1981, 8994, 1) || p.equals(1981, 8998, 1);
    }
}
