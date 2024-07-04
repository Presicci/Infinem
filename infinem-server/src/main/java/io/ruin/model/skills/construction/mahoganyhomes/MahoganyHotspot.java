package io.ruin.model.skills.construction.mahoganyhomes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
@Getter
@AllArgsConstructor
public enum MahoganyHotspot {
    MAHOGANY_HOMES_HOTSPOT_1(Config.varpbit(10554, true),
            ImmutableSet.of(39981, 39989, 39997, 40002, 40007, 40011, 40083, 40156, 40164, 40171, 40296, 40297)),
    MAHOGANY_HOMES_HOTSPOT_2(Config.varpbit(10555, true),
            ImmutableSet.of(39982, 39990, 39998, 40008, 40084, 40089, 40095, 40157, 40165, 40172, 40287, 40293)),
    MAHOGANY_HOMES_HOTSPOT_3(Config.varpbit(10556, true),
            ImmutableSet.of(39983, 39991, 39999, 40003, 40012, 40085, 40090, 40096, 40158, 40166, 40173, 40290)),
    MAHOGANY_HOMES_HOTSPOT_4(Config.varpbit(10557, true),
            ImmutableSet.of(39984, 39992, 40000, 40086, 40091, 40097, 40159, 40167, 40174, 40288, 40291, 40294)),
    MAHOGANY_HOMES_HOTSPOT_5(Config.varpbit(10558, true),
            ImmutableSet.of(39985, 39993, 40004, 40009, 40013, 40087, 40092, 40160, 40168, 40175, 40286, 40298)),
    MAHOGANY_HOMES_HOTSPOT_6(Config.varpbit(10559, true),
            ImmutableSet.of(39986, 39994, 40001, 40005, 40010, 40014, 40088, 40093, 40098, 40161, 40169, 40176)),
    MAHOGANY_HOMES_HOTSPOT_7(Config.varpbit(10560, true),
            ImmutableSet.of(39987, 39995, 40006, 40015, 40094, 40099, 40162, 40170, 40177, 40292, 40295)),
    MAHOGANY_HOMES_HOTSPOT_8(Config.varpbit(10561, true),
            ImmutableSet.of(39988, 39996, 40163, 40289, 40299));

    private final Config varbit;
    private final ImmutableSet<Integer> objectIds;

    private static final ImmutableMap<Integer, MahoganyHotspot> HOTSPOT_BY_OBJECT_ID;
    static
    {
        final ImmutableMap.Builder<Integer, MahoganyHotspot> objects = new ImmutableMap.Builder<>();
        for (final MahoganyHotspot hotspot : values())
        {
            hotspot.getObjectIds().forEach(id -> objects.put(id, hotspot));
        }
        HOTSPOT_BY_OBJECT_ID = objects.build();
    }

    static MahoganyHotspot getByObjectId(final int objectId)
    {
        return HOTSPOT_BY_OBJECT_ID.get(objectId);
    }

    static boolean isHotspotObject(final int id)
    {
        return HOTSPOT_BY_OBJECT_ID.containsKey(id);
    }
}
