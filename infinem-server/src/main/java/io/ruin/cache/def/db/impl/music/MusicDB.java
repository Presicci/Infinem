package io.ruin.cache.def.db.impl.music;

import io.ruin.cache.def.db.DBRowDefinition;
import io.ruin.cache.def.db.DBTableIndexDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/20/2024
 */
public class MusicDB {

    public static final List<Song> ROWS = new ArrayList<>();
    public static final Map<String, Song> ROWS_BY_NAME = new HashMap<>();

    public static Song getSongBySlot(int id) {
        return ROWS.get(id);
    }

    public static Song getSongByName(String name) {
        return ROWS_BY_NAME.get(name.toLowerCase());
    }

    static {
        DBTableIndexDefinition indexDef = DBTableIndexDefinition.get(44, 8);
        List<Integer> songs = indexDef.getRowsByInteger(0);
        for (int songId : songs) {
            DBRowDefinition row = DBRowDefinition.get(songId);
            if (row == null) {
                System.err.println("[MusicDB] Music row was null: " + songId);
                continue;
            }
            Song song = new Song(row, ROWS.size());
            ROWS.add(song);
            ROWS_BY_NAME.put(song.getName().toLowerCase(), song);
        }
    }
}
