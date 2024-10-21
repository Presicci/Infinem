package io.ruin.cache.def.db.impl.music;

import io.ruin.cache.def.db.DBRowDefinition;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/20/2024
 */
@Getter
public class Song {

    private final String name;
    private final int id;
    private final int duration;
    private final String unlockHint;
    private final int varpIndex;
    private final int bit;
    private final int slot;

    public Song(DBRowDefinition row, int slot) {
        this.name = (String) row.columnValues[1][0];
        this.id = (int) row.columnValues[4][0];
        this.duration = (int) row.columnValues[3][0];
        this.unlockHint = (String) row.columnValues[2][0];
        Object[] storage = row.columnValues[5];
        if (storage != null && storage.length == 2) {
            this.varpIndex = (int) storage[0] - 1;
            this.bit = (int) storage[1];
        } else {
            this.varpIndex = -1;
            this.bit = -1;
        }
        this.slot = slot;
    }

    @Override
    public String toString()
    {
        return "MusicRow{" + "id=" + id + ", name=" + name + ", songLength=" + duration + ", slot=" + slot + "}";
    }
}
