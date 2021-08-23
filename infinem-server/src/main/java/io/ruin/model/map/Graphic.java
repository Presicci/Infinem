package io.ruin.model.map;

import lombok.Builder;
import lombok.Getter;

/**
 * @author ReverendDread on 6/15/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Builder @Getter
public class Graphic {

    private final int id;
    private final int height;
    private final int delay;

    private final int soundId, soundType, soundDelay;

}
