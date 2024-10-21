package io.ruin.model.inter.handlers;

import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.db.impl.music.MusicDB;
import io.ruin.cache.def.db.impl.music.Song;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.RegionMusic;
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

    /**
     * The given player who owns this handler.
     */
    private final transient Player player;

    /**
     * The music track that's currently playing.
     */
    private transient Song currentlyPlaying;

    /**
     * The amount of ticks the current song has played for, and the amount of ticks at which this song ends.
     */
    private transient int ticks, nextSongAtTicks;

    /**
     * Whether the music player is currently stopped or not.
     */
    @Getter @Setter
    private transient boolean stopped = false;

    private static List<RegionMusic> regionMusicList = null;

    static {
        try {
            regionMusicList = JsonUtils.fromJson(JsonUtils.fromFile(new File(Server.dataFolder.getAbsolutePath() + "/music.json")), List.class, RegionMusic.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert regionMusicList != null;
        for (RegionMusic regionMusic : regionMusicList) {
            RegionMusic.map.put(regionMusic.getName(), regionMusic);
            if (!MusicDB.ROWS_BY_NAME.containsKey(regionMusic.getName().toLowerCase())) {
                System.out.println("Missing: " + regionMusic.getName());
            }
            List<Integer> regionIds = regionMusic.getRegionIds();
            if (regionIds != null) {
                for (int region : regionIds) {
                    if (region <= Region.LOADED.length - 1) {
                        Region.get(region).getRegionMusicTracks().add(regionMusic);
                    }
                }
            }
        }
        InterfaceHandler.register(Interface.MUSIC_PLAYER, h -> {
            h.actions[6] = (DefaultAction) (p, option, slot, item) -> {
                if (option == 1) {
                    if (!p.getMusic().play(slot)) {
                        p.sendMessage("You have not unlocked this piece of music yet!");
                    } else {
                        if (Config.MUSIC_PREFERENCE.get(p) == 1) {
                            Config.MUSIC_PREFERENCE.set(p, 0);
                        }
                    }
                } else {
                    p.getMusic().sendUnlockHint(slot);
                }
            };
            h.actions[10] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 1);
            };
            h.actions[13] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 0);
            };
            h.actions[16] = (SimpleAction) p -> {
                Config.MUSIC_PREFERENCE.set(p, 2);
            };
        });
    }

    public void stop() {
        setStopped(true);
        player.getPacketSender().sendMusic(-1);
    }

    /**
     * Restarts the currently playing track again(after setting music volume from 0 to > 0)
     */
    public void restartCurrent() {
        Song song = currentlyPlaying;
        if (song == null) {
            return;
        }
        if (stopped)
            stopped = false;
        else {
            player.getPacketSender().sendMusic(song.getId(), 0, 0, 0, 0);
            player.getPacketSender().sendString(Interface.MUSIC_PLAYER, 9, song.getName());
        }
        nextSongAtTicks = song.getDuration();
        ticks = 0;
    }

    /**
     * Plays a random track in the current region.
     */
    private void playRandomTrackInRegion() {
        Set<RegionMusic> list = player.getPosition().getRegion().getRegionMusicTracks();
        if (list == null || list.isEmpty()) {
            return;
        }
        Song song = Utils.getRandomCollectionElement(list).getSong();
        if (song == currentlyPlaying) {
            return;
        }
        play(song.getSlot());
    }

    /**
     * Plays a random track in the current region.
     */
    private void playRandomTrack() {
        int slot = new Random().nextInt(regionMusicList.size());
        while (!isUnlocked(slot)) {
            slot = new Random().nextInt(regionMusicList.size());
        }
        play(slot);
    }

    /**
     * Unlocks all the music tracks associated with the given region id.
     */
    public void unlock(int regionId) {
        Set<RegionMusic> list = Region.get(regionId).getRegionMusicTracks();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (RegionMusic music : list) {
            unlock(music.getSong(), false);
        }
        if (Config.MUSIC_PREFERENCE.get(player) == 0) {
            return;
        }
        playRandomTrackInRegion();
    }

    public void unlock(RegionMusic regionMusic) {
        unlock(regionMusic.getSong(), true);
    }

    public void unlock(Song song, boolean play) {
        try {
            int index = song.getVarpIndex();
            if (index >= Config.MUSIC_UNLOCKS.length || index < 0) {
                return;
            }
            Config varp = Config.MUSIC_UNLOCKS[index];
            int currentValue = varp.get(player);
            if (!isUnlocked(song)) {
                varp.set(player, currentValue | (1 << song.getBit()));
                if (Config.MUSIC_UNLOCK_MESSAGE.get(player) == 0) {
                    player.sendFilteredMessage("<col=ff0000>You have unlocked a new music track: " + song.getName());
                }
                //val unlocked = player.getEmotesHandler().isUnlocked(Emote.AIR_GUITAR);
                //if (!unlocked && unlockedMusicCount() >= 500) {
                //    player.getEmotesHandler().unlock(Emote.AIR_GUITAR);
                //    player.sendMessage(Colour.RS_GREEN.wrap("Congratulations, you've unlocked the Air Guitar emote!"));
                // }
            }
            if (play) {
                if (currentlyPlaying == song) {
                    return;
                }
                play(song);
            }
        } catch (Exception e) {
            System.out.println("Error unlocking music - " + song.getName() + ": " + song.getId());
        }

    }

    /**
     * Whether the track at the given slot is unlocked or not.
     */
    private boolean isUnlocked(Song song) {
        int index = song.getVarpIndex();
        if (index == -1) return true;
        if (index >= Config.MUSIC_UNLOCKS.length) {
            return false;
        }
        int value = Config.MUSIC_UNLOCKS[index].get(player);
        int bitshift = song.getBit();
        return (value & (1 << bitshift)) != 0;
    }

    private boolean isUnlocked(int slot) {
        return isUnlocked(MusicDB.ROWS.get(slot));
    }

    /**
     * Processes the music player.
     */
    public void processMusicPlayer() {
        if (stopped) {
            return;
        }
        if (++ticks >= nextSongAtTicks) {
            Song currentSong = currentlyPlaying;
            resetCurrent();
            ticks = 0;
            if (Config.MUSIC_PREFERENCE.get(player) == 2) {
                playRandomTrack();
            } else if (Config.MUSIC_LOOP.get(player) == 0 && currentSong != null) {
                stopped = true;
            } else {
                Song current = currentlyPlaying;
                currentlyPlaying = null;
                if (current != null) {
                    play(current.getSlot());
                } else {
                    playRandomTrackInRegion();
                }
            }
        }
    }

    /**
     * Sends the hint for the music track at the given slot.
     */
    public void sendUnlockHint(int slot) {
        Song song = MusicDB.getSongBySlot(slot);
        player.sendMessage((!isUnlocked(song) ? "This track unlocks " : "This track was unlocked ") + song.getUnlockHint().replace("unlocked ", ""));
    }

    /**
     * Resets the current music by stopping it client-sided.
     */
    private void resetCurrent() {
        player.getPacketSender().sendMusic(-1);
    }

    public boolean play(int slot) {
        return play(MusicDB.ROWS.get(slot));
    }

    public boolean play(Song song) {
        if (!isUnlocked(song)) {
            return false;
        }
        String musicName = song.getName();
        if (currentlyPlaying == song) {
            resetCurrent();
        }
        nextSongAtTicks = song.getDuration();
        ticks = 0;
        currentlyPlaying = song;
        stopped = false;
        player.getPacketSender().sendMusic(song.getId(), 0, 60, 60, 0);
        player.getPacketSender().sendString(Interface.MUSIC_PLAYER, 9, musicName);
        //TreasureTrail.playSong(player, music.getName());
        return true;
    }
}

