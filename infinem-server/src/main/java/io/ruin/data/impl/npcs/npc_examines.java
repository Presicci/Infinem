package io.ruin.data.impl.npcs;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.NPCDef;
import io.ruin.data.DataFile;

import java.io.File;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/31/2023
 */
public class npc_examines extends DataFile {
    @Override
    public String path() {
        return "npcs/npc_examines.json";
    }

    @Override
    public Object fromJson(File originalFile, String fileName, String json) {
        List<io.ruin.data.impl.objects.object_examines.Examine> examines = JsonUtils.fromJson(json, List.class, io.ruin.data.impl.objects.object_examines.Examine.class);
        if (!Server.dataOnlyMode) {
            examines.forEach(examine -> {
                NPCDef.get(examine.id).examine = examine.examine;
            });
        }
        return examines;
    }

    public static final class Examine {
        @Expose
        public int id;
        @Expose public String examine;
    }
}