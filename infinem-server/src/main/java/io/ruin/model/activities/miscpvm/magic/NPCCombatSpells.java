package io.ruin.model.activities.miscpvm.magic;

import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public enum NPCCombatSpells {
    WATER_STRIKE(new Projectile(94, 43, 31, 51, 56, 10, 16, 64),
            1162,
            93, 92,
            211, 1,
            95, 124,
            212),
    EARTH_STRIKE(new Projectile(97, 43, 31, 51, 56, 10, 16, 64),
            1162,
            96, 92,
            132, 1,
            98, 124,
            133),

    FIRE_STRIKE(new Projectile(100, 43, 31, 51, 56, 10, 16, 64),
            1162,
            99, 92,
            160, 1,
            101, 124,
            161),

    CONFUSE(new Projectile(103, 36, 31, 61, 56, 10, 16, 64),
            1163,
            102,92,
            119, 1,
            104, 200,
            121,
            (entity -> {
                if (entity.player != null) {
                    entity.player.getStats().get(StatType.Attack).drain(0.05);
                } else {
                    entity.npc.getCombat().getStat(StatType.Attack).drain(0.05);
                }
            })),
    VULNERABILITY(new Projectile(168, 36, 31, 34, 56, 10, 16, 64),
            1165,
            167,92,
            3009, 1,
            169, 124,
            3008,
            (entity -> {
                if (entity.player != null) {
                    entity.player.getStats().get(StatType.Defence).drain(0.1);
                } else {
                    entity.npc.getCombat().getStat(StatType.Defence).drain(0.1);
                }
            })),
    WEAKEN(new Projectile(106, 36, 31, 44, 56, 10, 16, 64),
            1164,
            105, 92,
            3011, 1,
            107, 200,
            3010,
            (entity -> {
                if (entity.player != null) {
                    entity.player.getStats().get(StatType.Strength).drain(0.05);
                } else {
                    entity.npc.getCombat().getStat(StatType.Strength).drain(0.05);
                }
            })),


    /* NPC Specific */
    SKELETON_MAGE_WEAKEN(new Projectile(106, 36, 31, 44, 56, 10, 16, 64),
            1164,
            105, 92,
            3011, 1,
            107, 200,
            3010,
            (entity -> {
                if (entity.player != null) {
                    entity.player.getStats().get(StatType.Attack).drain(4);
                    entity.player.getStats().get(StatType.Strength).drain(4);
                    entity.player.getStats().get(StatType.Defence).drain(4);
                } else {
                    entity.npc.getCombat().getStat(StatType.Attack).drain(4);
                    entity.npc.getCombat().getStat(StatType.Strength).drain(4);
                    entity.npc.getCombat().getStat(StatType.Defence).drain(4);
                }
            }));

    private final Projectile projectile;
    private final int animId, castGfxId, castGfxHeight, castSoundId, castSoundType, hitGfxId, hitGfxHeight, hitSound;
    private final Consumer<Entity> onHitAction;

    NPCCombatSpells(Projectile projectile, int animId, int castGfxId, int castGfxHeight, int castSoundId, int castSoundType, int hitGfxId, int hitGfxHeight, int hitSound, Consumer<Entity> onHitAction) {
        this.projectile = projectile;
        this.animId = animId;
        this.castGfxId = castGfxId;
        this.castGfxHeight = castGfxHeight;
        this.castSoundId = castSoundId;
        this.castSoundType = castSoundType;
        this.hitGfxId = hitGfxId;
        this.hitGfxHeight = hitGfxHeight;
        this.hitSound = hitSound;
        this.onHitAction = onHitAction;
    }

    NPCCombatSpells(Projectile projectile, int animId, int castGfxId, int castGfxHeight, int castSoundId, int castSoundType, int hitGfxId, int hitGfxHeight, int hitSound) {
        this(projectile, animId, castGfxId, castGfxHeight, castSoundId, castSoundType, hitGfxId, hitGfxHeight, hitSound, null);
    }
}
