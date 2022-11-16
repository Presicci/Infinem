package io.ruin.model.inter.handlers;

import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.EnumMap;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Music;
import io.ruin.model.map.Region;
import io.ruin.utility.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MusicPlayer {

    public MusicPlayer(final Player player) {
        this.player = player;
    }

    /** The given player who owns this handler. */
    private final transient Player player;

    /** The music track that's currently playing. */
    private transient Music currentlyPlaying;

    /** The amount of ticks the current song has played for, and the amount of ticks at which this song ends. */
    private transient int ticks, nextSongAtTicks;

    /** Whether the music player is currently stopped or not. */
    @Getter @Setter private transient boolean stopped;

    private static List<Music> musicList = null;

    static {
        try {
            musicList = JsonUtils.fromJson(JsonUtils.fromFile(new File("data/music.json")), List.class, Music.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert musicList != null;
        for (Music music : musicList) {
            Music.map.put(music.getName(), music);

            List<Integer> regionIds = music.getRegionIds();
            if (regionIds != null) {
                for (int region : regionIds) {
                    if (region <= Region.LOADED.length - 1) {
                        Region.get(region).getMusicTracks().add(music);
                    }
                }
            }
        }
        InterfaceHandler.register(Interface.MUSIC_PLAYER, h -> {
            h.actions[3] = (DefaultAction) (p, option, slot, item) -> {
                if (option == 1) {
                    if (!p.getMusic().play(slot)) {
                        p.sendMessage("You have not unlocked this piece of music yet!");
                    }
                } else {
                    p.getMusic().sendUnlockHint(slot);
                }
            };
            h.actions[7] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 1);
            };
            h.actions[9] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 0);
            };
            h.actions[11] = (SimpleAction) p -> {
                if(Config.MUSIC_LOOP.toggle(p) == 1)
                    p.sendMessage("Music looping is now enabled.");
                else
                    p.sendMessage("Music looping now disabled.");
            };
        });
    }

    public void stop() {
        setStopped(true);
        player.getPacketSender().sendMusic(-1);
    }

    /** Restarts the currently playing track again(after setting music volume from 0 to > 0) */
    public void restartCurrent() {
        val music = currentlyPlaying;
        if (music == null) {
            return;
        }
        if (stopped)
            stopped = false;
        nextSongAtTicks = music.getDuration();
        currentlyPlaying = music;
        ticks = 0;
    }

    /** Plays a random track in the current region. */
    private void playRandomTrackInRegion() {
        val list = player.getPosition().getRegion().getMusicTracks();
        for (Music music : list) {
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        val randomTrack = Utils.getRandomCollectionElement(list);
        if (randomTrack == currentlyPlaying) {
            return;
        }
        val slot = EnumMap.get(812).valuesAsKeysStrings().get(randomTrack.getName()) - 1;
        play(slot);
    }

    /** Plays a random track in the current region. */
    private void playRandomTrack() {
        int slot = new Random().nextInt(musicList.size());
        while (!isUnlocked(slot)) {
            slot = new Random().nextInt(musicList.size());
        }
        play(slot);
    }

    /** Unlocks all the music tracks associated with the given region id. */
    public void unlock(final int regionId) {
        val list = Region.get(regionId).getMusicTracks();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (val music : list) {
            unlock(music, false);
        }
        if (Config.MUSIC_PREFERENCE.get(player) == 0) {
            return;
        }
        playRandomTrackInRegion();
    }

    public void unlock(final Music music) {
        unlock(music, true);
    }

    public void unlock(final Music music, final boolean play) {
        val slot = EnumMap.get(812).valuesAsKeysStrings().get(music.getName()) - 1;
        val musicIndex = EnumMap.get(819).intValues[slot];
        val index = (musicIndex >> 14) - 1;
        if (index >= Config.MUSIC_UNLOCKS.length) {
            return;
        }

        val varp = Config.MUSIC_UNLOCKS[index];
        val value = varp.get(player);
        if (!isUnlocked(slot)) {
            varp.set(player, value | (1 << (musicIndex & 0x3FFF)));
            player.sendFilteredMessage("<col=ff0000>You have unlocked a new music track: " + music.getName());
            //val unlocked = player.getEmotesHandler().isUnlocked(Emote.AIR_GUITAR);
            //if (!unlocked && unlockedMusicCount() >= 500) {
            //    player.getEmotesHandler().unlock(Emote.AIR_GUITAR);
            //    player.sendMessage(Colour.RS_GREEN.wrap("Congratulations, you've unlocked the Air Guitar emote!"));
            // }
        }
        if (play) {
            if (currentlyPlaying == music) {
                return;
            }
            play(slot);
        }
    }

    /** Whether the track at the given slot is unlocked or not. */
    private boolean isUnlocked(final int slot) {
        val randomSong = EnumMap.get(819).intValues[slot];
        if (randomSong == -1) {
            return true;
        }
        val index = (randomSong >> 14) - 1;
        if (index >= Config.MUSIC_UNLOCKS.length) {
            return false;
        }
        val value = Config.MUSIC_UNLOCKS[index].get(player);
        val bitshift = randomSong & 0x3fff;
        return (value & (1 << bitshift)) != 0;
    }

    /** Processes the music player. */
    public void processMusicPlayer() {
        if (stopped) {
            return;
        }
        if (++ticks >= nextSongAtTicks) {
            resetCurrent();
            ticks = 0;
            if (Config.MUSIC_LOOP.get(player) != 1) {   // LOOP off
                if (Config.MUSIC_PREFERENCE.get(player) == 1) { // on AUTO
                    playRandomTrack();  // basically shuffle, add option for this if people want
                } else {
                    stopped = true;
                }
            } else {
                val current = currentlyPlaying;
                currentlyPlaying = null;
                if (current != null) {
                    val slot = EnumMap.get(812).valuesAsKeysStrings().get(current.getName()) - 1;
                    play(slot);
                } else {
                    playRandomTrackInRegion();
                }
            }
        }
    }

    /** Sends the hint for the music track at the given slot. */
    public void sendUnlockHint(final int slotId) {
        val musicName = EnumMap.get(812).stringValues[slotId];
        val music = Music.map.get(musicName);
        if (music == null) {
            return;
        }
        player.sendMessage((!isUnlocked(slotId) ? "This track unlocks " : "This track was unlocked ") + music.getHint().replace("unlocked ", ""));
    }

    /** Resets the current music by stopping it client-sided. */
    private void resetCurrent() {
        player.getPacketSender().sendMusic(-1);
    }

    public boolean play(int slot) {
        if (!isUnlocked(slot)) {
            return false;
        }
        String musicName = EnumMap.get(812).stringValues[slot];
        Music music = Music.map.get(musicName);
        if (music == null) {
            return false;
        }
        if (currentlyPlaying == music) {
            resetCurrent();
        }
        nextSongAtTicks = music.getDuration();
        ticks = 0;
        currentlyPlaying = music;
        stopped = false;
        player.getPacketSender().sendMusic(music.getMusicId());
        player.getPacketSender().sendString(Interface.MUSIC_PLAYER, 6, musicName);
        //TreasureTrail.playSong(player, music.getName());
        return true;
    }
}

