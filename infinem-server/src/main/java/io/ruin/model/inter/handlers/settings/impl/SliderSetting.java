package io.ruin.model.inter.handlers.settings.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import lombok.Getter;

import java.util.function.BiConsumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/15/2024
 */
public enum SliderSetting {
    SOUND_EFFECT_VOLUME(1, 0, 0, (player, integer) -> {
        player.sendMessage((integer * 5) + "");
        Config.SOUND_EFFECT_MUTED.setInstant(player, 1 - integer == 0 ? 1 : 0);
        Config.SOUND_EFFECT_VOLUME.setInstant(player, integer * 5);
        player.getPacketSender().sendVarp(169, integer * 5);
    }),
    AREA_SOUND_VOLUME(1, 21, 21, (player, integer) -> {
        player.sendMessage((integer * 5) + "");
        Config.AREA_SOUND_EFFECT_VOLUME.setInstant(player, integer * 5);
        Config.AREA_SOUND_EFFECT_MUTED.setInstant(player, integer == 0 ? 1 : 0);
    }),
    MUSIC_VOLUME(1, 42, 42, (player, integer) -> {
        player.sendMessage((integer * 5) + "");
        Config.MUSIC_VOLUME.set(player, integer * 5);
        Config.MUSIC_MUTED.setInstant(player, 1 - integer == 0 ? 1 : 0);
    });

    @Getter private final int menuIndex, childIndex, searchIndex;
    @Getter private final BiConsumer<Player, Integer> consumer;

    SliderSetting(int menuIndex, int childIndex, int searchIndex, BiConsumer<Player, Integer> consumer) {
        this.menuIndex = menuIndex;
        this.childIndex = childIndex;
        this.searchIndex = searchIndex;
        this.consumer = consumer;
    }
}
