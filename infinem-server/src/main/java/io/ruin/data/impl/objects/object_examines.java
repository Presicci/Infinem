package io.ruin.data.impl.objects;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.ObjectDef;
import io.ruin.data.DataFile;

import java.io.File;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/31/2023
 */
public class object_examines extends DataFile {
    @Override
    public String path() {
        return "objects/obj_examines.json";
    }

    @Override
    public Object fromJson(File originalFile, String fileName, String json) {
        List<Examine> examines = JsonUtils.fromJson(json, List.class, Examine.class);
        if (!Server.dataOnlyMode) {
            examines.forEach(examine -> {
                ObjectDef.get(examine.id).examine = examine.examine;
            });
        }
        return examines;
    }

    public static final class Examine {
        @Expose public int id;
        @Expose public String examine;
    }
}
