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
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public enum ClimbingSpot {

    PATERDOMUS_CLIMB65(new RegisterObject[]{
            new RegisterObject(16998, 3425, 3476),
            new RegisterObject(16999, 3426, 3477) },
            Direction.WEST, 65, 1,
            new Position(3427, 3477, 0), new Position(3424, 3476, 0),
            new Position(3426, 3477, 0), new Position(3425, 3476, 0)),

    CAIRN_S_CLIMB1(new RegisterObject[] {
            new RegisterObject(2231, new Position(2794, 2978)),
            new RegisterObject(2231, new Position(2792, 2978)) },
            Direction.WEST, 1,1,
            Position.of(2795, 2978), Position.of(2791, 2978),
            Position.of(2794, 2978), Position.of(2792, 2978)),

    CAIRN_M_CLIMB1(new RegisterObject[] {
            new RegisterObject(2231, 2792, 2979),
            new RegisterObject(2231, 2794, 2979) },
            Direction.WEST, 1,1,
            Position.of(2795, 2979), Position.of(2791, 2979),
            Position.of(2794, 2979), Position.of(2792, 2979)),

    CAIRN_N_CLIMB1(new RegisterObject[]{
            new RegisterObject(2231, 2792, 2980),
            new RegisterObject(2231, 2794, 2980) },
            Direction.WEST, 1,1,
            Position.of(2795, 2980), Position.of(2791, 2980),
            Position.of(2794, 2980), Position.of(2792, 2980)),

    EAGLES_TOP_E_CLIMB25(new RegisterObject[]{
            new RegisterObject(19849, 2323, 3497),
            new RegisterObject(19849, 2322, 3501) },
            Direction.SOUTH, 25,1,
            Position.of(2324, 3497), Position.of(2322, 3502),
            Position.of(2324, 3498), Position.of(2323, 3500), Position.of(2322, 3501)),

    EAGLES_TOP_W_CLIMB25(new RegisterObject[]{ new RegisterObject(19849, 2324, 3498) },
            Direction.SOUTH, 25,1,
            Position.of(2323, 3496), Position.of(2322, 3502),
            Position.of(2323, 3497), Position.of(2323, 3500), Position.of(2322, 3501)),

    GNOME_CLIMB37(new RegisterObject[]{
            new RegisterObject(16535, 2489, 3520),
            new RegisterObject(16534, 2487, 3515) },
            Direction.SOUTH, 37,1,
            Position.of(2489, 3521), Position.of(2486, 3515),
            Position.of(2489, 3520), Position.of(2489, 3517), Position.of(2487, 3515)),

    AL_KHARID_CLIMB38(new RegisterObject[]{
            new RegisterObject(16550, 3303, 3315),
            new RegisterObject(16549, 3305, 3315) },
            Direction.EAST, 38,1,
            Position.of(3302, 3315), Position.of(3306, 3315),
            Position.of(3303, 3315), Position.of(3305, 3315)),

    DEATH_PLATEAU_EAST1(new RegisterObject[]{
            new RegisterObject(3791, 2878, 3623),
            new RegisterObject(3790, 2879, 3623) },
            Direction.EAST, 1, 1,
            Position.of(2880, 3623), Position.of(2877, 3623),
            Position.of(2879, 3623), Position.of(2878, 3623)),

    DEATH_PLATEAU_EAST2(new RegisterObject[]{
            new RegisterObject(3791, 2878, 3622),
            new RegisterObject(3790, 2879, 3622) },
            Direction.EAST, 1, 1,
            Position.of(2880, 3622), Position.of(2877, 3622),
            Position.of(2879, 3622), Position.of(2878, 3622)),

    DEATH_PLATEAU_NORTH1(new RegisterObject[] {
            new RegisterObject(3722, 2880, 3595),
            new RegisterObject(3723, 2880, 3594) },
            Direction.NORTH, 1, 1,
            Position.of(2880, 3596), Position.of(2880, 3593),
            Position.of(2880, 3595), Position.of(2880, 3594)),

    DEATH_PLATEAU_NORTH2(new RegisterObject[] {
            new RegisterObject(3722, 2881, 3595),
            new RegisterObject(3723, 2881, 3594)},
            Direction.NORTH, 1, 1,
            Position.of(2881, 3596), Position.of(2881, 3593),
            Position.of(2881, 3595), Position.of(2881, 3594)),

    DEATH_PLATEAU_WEST1(new RegisterObject[]{
            new RegisterObject(3791, 2860, 3627),
            new RegisterObject(3790, 2859, 3627) },
            Direction.WEST, 1, 1,
            Position.of(2861, 3627), Position.of(2858, 3627),
            Position.of(2860, 3627), Position.of(2859, 3627)),

    DEATH_PLATEAU_WEST2(new RegisterObject[]{
            new RegisterObject(3790, 2859, 3626),
            new RegisterObject(3791, 2860, 3626) },
            Direction.WEST, 1, 1,
            Position.of(2861, 3626), Position.of(2858, 3626),
            Position.of(2860, 3626), Position.of(2859, 3626)),
    TROLL_CLIMB41(new RegisterObject[]{
            new RegisterObject(16521, 2870, 3671),
            new RegisterObject(16521, 2871, 3671) },
            Direction.EAST, 41,1,
            Position.of(2869, 3671), Position.of(2872, 3671),
            Position.of(2870, 3671), Position.of(2871, 3671)),

    TROLL_1_CLIMB43(new RegisterObject[]{
            new RegisterObject(16522, 2878, 3666),
            new RegisterObject(16522, 2878, 3667) },
            Direction.NORTH, 43,1,
            Position.of(2878, 3665), Position.of(2878, 3668),
            Position.of(2878, 3666), Position.of(2878, 3667)),

    TROLL_2_CLIMB43(new RegisterObject[]{ new RegisterObject(3804, 2887, 3661) },
            Direction.NORTH, 43,1,
            Position.of(2887, 3660), Position.of(2887, 3662),
            Position.of(2887, 3661)),

    TROLL_3_CLIMB43(new RegisterObject[]{ new RegisterObject(3803, 2888, 3661)},
            Direction.NORTH, 43,1,
            Position.of(2888, 3660), Position.of(2888, 3662),
            Position.of(2888, 3661)),

    TROLL_4_CLIMB43(new RegisterObject[]{ new RegisterObject(3804, 2885, 3684)},
            Direction.EAST, 43,1,
            Position.of(2884, 3684), Position.of(2886, 3684),
            Position.of(2885, 3684)),

    TROLL_5_CLIMB43(new RegisterObject[]{ new RegisterObject(3803, 2885, 3683) },
            Direction.EAST, 43,1,
            Position.of(2884, 3683), Position.of(2886, 3683),
            Position.of(2885, 3683)),

    TROLL_W_CLIMB44(new RegisterObject[]{ new RegisterObject(16523, 2908, 3682) },
            Direction.WEST, 44,1,
            Position.of(2907, 3682), Position.of(2909, 3682),
            Position.of(2908, 3682)),

    TROLL_S_CLIMB44(new RegisterObject[]{ new RegisterObject(16523, 2909, 3683) },
            Direction.SOUTH, 44,1,
            Position.of(2909, 3682), Position.of(2909, 3684),
            Position.of(2909, 3683)),

    TROLL_CLIMB47(new RegisterObject[]{
            new RegisterObject(16524, 2902, 3680),
            new RegisterObject(16524, 2901, 3680) },
            Direction.WEST, 47,1,
            Position.of(2903, 3680), Position.of(2900, 3680),
            Position.of(2902, 3680), Position.of(2901, 3680)),

    TROLL_HERB_CLIMB73(new RegisterObject[]{
            new RegisterObject(16464, 2843, 3693),
            new RegisterObject(16464, 2839, 3693) },
            Direction.WEST, 73,1,
            Position.of(2844, 3693), Position.of(2838, 3693),
            Position.of(2842, 3693), Position.of(2840, 3693)),

    TROLL_WILDERNESS1(new RegisterObject[]{
            new RegisterObject(16545, 2916, 3672),
            new RegisterObject(16545, 2917, 3672) },
            Direction.EAST, 64, 0,
            Position.of(2915, 3672),  Position.of(2918, 3672), Position.of(2916, 3672),
            Position.of(2917, 3672)),

    TROLL_WILDERNESS2(new RegisterObject[]{
            new RegisterObject(16545, 2923, 3673),
            new RegisterObject(16545, 2922, 3672) },
            Direction.WEST, 64, 0,
            Position.of(2921, 3672), Position.of(2924, 3673), Position.of(2922, 3672),
            Position.of(2923, 3672), Position.of(2923, 3673)),

    TROLL_WILDERNESS3(new RegisterObject[]{
            new RegisterObject(16545, 2947, 3678),
            new RegisterObject(16545, 2948, 3679)},
            Direction.WEST, 64, 0,
            Position.of(2946, 3678), Position.of(2949, 3679), Position.of(2947, 3678),
            Position.of(2948, 3678), Position.of(2948, 3679)),

    TROLL_WILDERNESS4(new RegisterObject[]{ new RegisterObject(16545, 2949, 3680) }, Direction.SOUTH, 64, 0,
            Position.of(2949, 3679), Position.of(2949, 3681), Position.of(2949, 3680)),

    ARANDAR_CLIMB59(new RegisterObject[]{
            new RegisterObject(16515, 2344, 3295),
            new RegisterObject(16514, 2346, 3299) },
            Direction.NORTH, 59,1,
            Position.of(2344, 3294), Position.of(2346, 3300),
            Position.of(2344, 3295), Position.of(2345, 3297), Position.of(2346, 3299)),

    ARANDAR_CLIMB68(new RegisterObject[]{
            new RegisterObject(16515, 2338, 3285),
            new RegisterObject(16514, 2338, 3282) },
            Direction.SOUTH, 68,1,
            Position.of(2338, 3286), Position.of(2338, 3281),
            Position.of(2338, 3285), Position.of(2338, 3282)),

    ARANDAR_CLIMB85(new RegisterObject[]{
            new RegisterObject(16515, 2337, 3253),
            new RegisterObject(16514, 2333, 3252) },
            Direction.WEST, 85,1,
            Position.of(2338, 3253), Position.of(2332, 3252),
            Position.of(2337, 3253), Position.of(2335, 3253), Position.of(2333, 3252)),

    KHARAZI_VINE(new RegisterObject[]{
            new RegisterObject(26884, 2899, 2941),
            new RegisterObject(26886, 2899, 2938) },
            Direction.SOUTH, 79, 1,
            Position.of(2899, 2942), Position.of(2899, 2937), Position.of(2899, 2940)),

    AGILITY_PYRAMID_1(new RegisterObject[]{
            new RegisterObject(11949, 3351, 2827),
            new RegisterObject(11949, 3349, 2827) },
            Direction.WEST, 1, 0,
            Position.of(3352, 2827), Position.of(3348, 2827), Position.of(3351, 2827), Position.of(3350, 2827), Position.of(3349, 2827)),
    AGILITY_PYRAMID_2(new RegisterObject[]{
            new RegisterObject(11949, 3351, 2828),
            new RegisterObject(11949, 3349, 2828) },
            Direction.WEST, 1, 0,
            Position.of(3352, 2828), Position.of(3348, 2828), Position.of(3351, 2828), Position.of(3350, 2828), Position.of(3349, 2828)),
    AGILITY_PYRAMID_3(new RegisterObject[]{
            new RegisterObject(11949, 3351, 2829),
            new RegisterObject(11949, 3349, 2829) },
            Direction.WEST, 1, 0,
            Position.of(3352, 2829), Position.of(3348, 2829), Position.of(3351, 2829), Position.of(3350, 2829), Position.of(3349, 2829)),

    AGILITY_PYRAMID_4(new RegisterObject[]{
            new RegisterObject(11948, 3335, 2829),
            new RegisterObject(11948, 3337, 2829) },
            Direction.WEST, 1, 0,
            Position.of(3338, 2829), Position.of(3334, 2829), Position.of(3337, 2829), Position.of(3336, 2829), Position.of(3335, 2829)),
    AGILITY_PYRAMID_5(new RegisterObject[]{
            new RegisterObject(11948, 3335, 2828),
            new RegisterObject(11948, 3337, 2828) },
            Direction.WEST, 1, 0,
            Position.of(3338, 2828), Position.of(3334, 2828), Position.of(3337, 2828), Position.of(3336, 2828), Position.of(3335, 2828)),
    AGILITY_PYRAMID_6(new RegisterObject[]{
            new RegisterObject(11948, 3335, 2827),
            new RegisterObject(11948, 3337, 2827) },
            Direction.WEST, 1, 0,
            Position.of(3338, 2827), Position.of(3334, 2827), Position.of(3337, 2827), Position.of(3336, 2827), Position.of(3335, 2827)),
    AGILITY_PYRAMID_7(new RegisterObject[]{
            new RegisterObject(11948, 3335, 2826),
            new RegisterObject(11948, 3337, 2826) },
            Direction.WEST, 1, 0,
            Position.of(3338, 2826), Position.of(3334, 2826), Position.of(3337, 2826), Position.of(3336, 2826), Position.of(3335, 2826)),

    WEISS_DOCK_1(new RegisterObject[]{
            new RegisterObject(33184, 2852, 3965) },
            Direction.SOUTH, 68, 1,
            Position.of(2852, 3966), Position.of(2852, 3964), Position.of(2852, 3965)),
    WEISS_DOCK_2(new RegisterObject[]{
            new RegisterObject(33185, 2853, 3964),
            new RegisterObject(33185, 2854, 3964) },
            Direction.EAST, 68, 1,
            Position.of(2852, 3964), Position.of(2855, 3964), Position.of(2853, 3964), Position.of(2854, 3964)),
    WEISS_DOCK_3(new RegisterObject[]{
            new RegisterObject(33328, 2854, 3962),
            new RegisterObject(33327, 2853, 3962) },
            Direction.SOUTH, 68, 1,
            Position.of(2855, 3964), Position.of(2853, 3961), Position.of(2855, 3963), Position.of(2854, 3962)),
    WEISS_DOCK_4(new RegisterObject[]{
            new RegisterObject(33191, 2859, 3961) },
            Direction.SOUTH, 68, 1,
            Position.of(2859, 3962), Position.of(2859, 3960), Position.of(2859, 3961)),

    CHASM_OF_TEARS_1(new RegisterObject[]{
            new RegisterObject(6673, 3240, 9525, 2)},
            Direction.WEST, 1, 0,
            new Position(3241, 9525, 2), new Position(3239, 9525, 2),
            new Position(3240, 9525, 2)),
    CHASM_OF_TEARS_2(new RegisterObject[]{
            new RegisterObject(6673, 3240, 9524, 2)},
            Direction.WEST, 1, 0,
            new Position(3241, 9524, 2), new Position(3239, 9524, 2),
            new Position(3240, 9524, 2)),
    CHASM_OF_TEARS_3(new RegisterObject[]{
            new RegisterObject(6672, 3239, 9499, 2)},
            Direction.WEST, 1, 0,
            new Position(3240, 9499, 2), new Position(3238, 9499, 2),
            new Position(3239, 9499, 2)),
    CHASM_OF_TEARS_4(new RegisterObject[]{
            new RegisterObject(6672, 3239, 9498, 2)},
            Direction.WEST, 1, 0,
            new Position(3240, 9498, 2), new Position(3238, 9498, 2),
            new Position(3239, 9498, 2)),
    CHASM_OF_TEARS_5(new RegisterObject[]{
            new RegisterObject(6672, 3239, 9497, 2)},
            Direction.WEST, 1, 0,
            new Position(3240, 9497, 2), new Position(3238, 9497, 2),
            new Position(3239, 9497, 2)),

    ;

    ClimbingSpot(RegisterObject[] objects, Direction faceDir, int levelReq, int xp, Position startPosition, Position endPosition, Position... steps){
        this.objects = objects;
        this.faceDir = faceDir;
        this.xp = xp;
        this.levelReq = levelReq;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.positions = Arrays.asList(steps);
    }

    private final RegisterObject[] objects;
    private final List<Position> positions;
    private final Position startPosition, endPosition;
    private final int xp, levelReq;
    private final Direction faceDir;

    public void traverse(Player p, GameObject obj){
        if(!p.getStats().check(StatType.Agility, levelReq, " to use this")){
            return;
        }
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            List<Position> posCopy = Lists.newArrayList(positions);
            if(posCopy.size() == 1){
                if(startPosition.equals(p.getPosition())){
                    posCopy.add(endPosition);
                } else {
                    posCopy.add(startPosition);
                }
            } else if(p.getPosition().isWithinDistance(startPosition, 1)){
                posCopy.add(endPosition);
            } else {
                Collections.reverse(posCopy);
                posCopy.add(startPosition);
            }


            for(int index = 0;index<posCopy.size();index++){
                Position pos = posCopy.get(index);
                int xDiff = pos.getX() - p.getPosition().getX();
                int yDiff = pos.getY() - p.getPosition().getY();
                p.animate(740);
                int speed = index == 0 || index == posCopy.size() - 1 ? 35 : 80;
                p.getMovement().force(xDiff, yDiff, 0, 0, 5, speed, faceDir);
                e.delay(Math.max(Math.abs(xDiff), Math.abs(yDiff)));
            }

            if(World.isEco())
                p.getStats().addXp(StatType.Agility, xp, true);
            p.unlock();
            p.resetAnimation();
        });
    }

    public static void traverseStatic(Player p, GameObject obj, Direction direction, int tiles, boolean towardsWall){
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            for (int index = 0; index < tiles; index++) {
                p.animate(740);
                p.getMovement().force(direction == Direction.EAST ? (1) : (direction == Direction.WEST ? -1 : 0),
                        direction == Direction.NORTH ? (1) : (direction == Direction.SOUTH ? -1 : 0),
                        0, 0, 5, 65, towardsWall ? direction : Direction.getOppositeDirection(direction));
                e.delay(2);
            }
            p.unlock();
            p.resetAnimation();
        });
    }

    public static void traverseStaticBidirectional(Player p, GameObject obj, Direction direction, int tiles) {
        switch (direction) {
            case NORTH:
            case SOUTH:
                if (p.getPosition().getY() > obj.getPosition().getY()) {
                    traverseStatic(p, obj, Direction.SOUTH, tiles, direction == Direction.NORTH);
                } else {
                    traverseStatic(p, obj, Direction.NORTH, tiles, direction == Direction.SOUTH);
                }
                break;
            case EAST:
            case WEST:
                if (p.getPosition().getX() > obj.getPosition().getX()) {
                    traverseStatic(p, obj, Direction.WEST, tiles, direction == Direction.EAST);
                } else {
                    traverseStatic(p, obj, Direction.EAST, tiles, direction == Direction.WEST);
                }
                break;
        }
    }

    static {
        for (ClimbingSpot spot : values()) {
            for (RegisterObject object : spot.objects) {
                object.register(1, spot::traverse);
            }
        }
        // Paterdomus
        Tile.getObject(16999, 3426, 3477, 0).nearPosition = (player, object) -> new Position(3427, 3477, 0);
    }
}
