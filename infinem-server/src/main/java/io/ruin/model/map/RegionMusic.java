package io.ruin.model.map;

import io.ruin.cache.def.db.impl.music.MusicDB;
import io.ruin.cache.def.db.impl.music.Song;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RegionMusic {

    public static final Map<String, RegionMusic> map = new HashMap<String, RegionMusic>(900);

    public static final RegionMusic get(@NotNull final String name) {
        val music = map.get(name);
        if (music == null) {
            throw new IllegalStateException("Music track '" + name + "' does not exist.");
        }
        return music;
    }

    private String name = "", hint = "";
    private int musicId, duration = 0;
    private List<Integer> regionIds;
    private boolean defaultLocked;

    public Song getSong() {
        return MusicDB.getSongById(musicId);
    }
}
