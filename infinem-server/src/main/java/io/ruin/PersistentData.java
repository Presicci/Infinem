package io.ruin;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.model.World;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/30/2024
 */
public class PersistentData {

    private static final Gson GSON_LOADER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static final String PATH = "../data/data.json";

    public static PersistentData INSTANCE = new PersistentData();

    @Expose public final Set<String> STARTER_IPS = new HashSet<>();
    @Expose public final Set<String> WEEK_ONE_IPS = new HashSet<>();

    public void save() {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(INSTANCE);
            File file = new File(PATH);
            Files.write(file.toPath(), json.getBytes());
            System.out.println("Saved persistent data");
        } catch (IOException e) {
            System.err.println("Couldn't save persistent data. " + e.getMessage());
        }
    }

    static {
        try {
            File saveFile = new File(PATH);
            if (saveFile.exists()) {
                String json = new String(Files.readAllBytes(saveFile.toPath()));
                INSTANCE = GSON_LOADER.fromJson(json, PersistentData.class);
                System.out.println("Loaded persistent data");
            }
        } catch (IOException e) {
            System.err.println("Couldn't load persistent data. " + e.getMessage());
        }
        // Save every 30 minutes
        World.startEvent(e -> {
            while (true) {
                e.delay(30000); // 30 minutes
                INSTANCE.save();
            }
        });
    }
}
