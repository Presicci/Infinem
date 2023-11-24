package io.ruin.cache.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Tommeh | 6 aug. 2018 | 13:32:12
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
@Getter
@RequiredArgsConstructor
public enum ArchiveType {

    SKELETONS(0),
    BASES(1),
    CONFIGS(2),
    INTERFACES(3),
    SYNTHS(4),
    MAPS(5),
    MUSIC(6),
    MODELS(7),
    SPRITES(8),
    TEXTURES(9),
    BINARY(10),
    JINGLES(11),
    CLIENTSCRIPTS(12),
    FONTMETRICS(13),
    VORBIS(14),
    INSTRUMENTS(15),
    WORLDMAPDATA_LEGACY(16),
    DEFAULTS(17),
    WORLDMAPGEOGRAPHY(18),
    WORLDMAPDATA(19),
    WORLDMAPGROUND(20),
    REFERENCE(255);

    private final int id;
}
