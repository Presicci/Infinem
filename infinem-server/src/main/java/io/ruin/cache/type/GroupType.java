package io.ruin.cache.type;

import lombok.Getter;

/**
 * @author Tommeh | 6 aug. 2018 | 13:32:22
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public enum GroupType {

    UNDERLAY(1),
    IDENTKIT(3),
    OVERLAY(4),
    INV(5),
    OBJECT(6),
    ENUM(8),
    NPC(9),
    ITEM(10),
    PARAMS(11),
    SEQUENCE(12),
    SPOTANIM(13),
    VARBIT(14),
    VARCLIENTSTRING(15),
    VARPLAYER(16),
    VARCLIENT(19),
    HITMARK(32),
    HITBAR(33),
    STRUCT(34),
    MAP_LABELS(35);

    GroupType(int id) {
        this.id = id;
    }

    @Getter
    private final int id;
}
