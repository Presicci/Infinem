package io.ruin.model.skills.prayer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/20/2024
 */
public class BlessedBoneShards {

    @Getter
    @AllArgsConstructor
    public enum Type {
        BONES(Bone.REGULAR_BONES, 29344, 4),
        BAT_BONES(Bone.BAT_BONES, 29346, 5),
        BIG_BONES(Bone.BIG_BONES, 29348, 12),
        ZOGRE_BONES(Bone.ZOGRE_BONES, 29350, 18),
        BABYWYRM_BONES(Bone.WYRMLING_BONES, 29354, 21),
        BABYDRAGON_BONES(Bone.BABYDRAGON_BONES, 29352, 24),
        WYRM_BONES(Bone.WYRM_BONES, 29364, 42),
        SUN_KISSED_BONES(null, 29378, 45),
        WYVERN_BONES(Bone.WYVERN_BONES, 29360, 58),
        DRAGON_BONES(Bone.DRAGON_BONES, 29356, 58),
        DRAKE_BONES(Bone.DRAKE_BONES, 29366, 64),
        FAYRG_BONES(Bone.FAYRG_BONES, 29370, 67),
        LAVA_DRAGON_BONES(Bone.LAVA_DRAGON_BONES, 29358, 68),
        RAURG_BONES(Bone.RAURG_BONES, 29372, 77),
        HYDRA_BONES(Bone.HYDRA_BONES, 29368, 93),
        DAGANNOTH_BONES(Bone.DAGANNOTH_BONES, 29376, 100),
        OURG_BONES(Bone.OURG_BONES, 29374, 115),
        SUPERIOR_DRAGON_BONES(Bone.SUPERIOR_DRAGON_BONES, 29362, 121),
        BLESSED_BONE_STATUETTE_1(null, 29338, 125),
        BLESSED_BONE_STATUETTE_2(null, 29340, 125),
        BLESSED_BONE_STATUETTE_3(null, 29342, 125);

        private final Bone bone;
        private final int blessedBoneId, shardAmount;
    }

    public static final List<Integer> BLESSABLE_BONES = new ArrayList<>();

    static {
        for (Type boneType : Type.values()) {
            if (boneType.bone != null) BLESSABLE_BONES.add(boneType.bone.id);
        }
    }
}
