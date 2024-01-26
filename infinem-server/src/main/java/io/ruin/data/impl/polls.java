package io.ruin.data.impl;

import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.content.poll.Poll;
import io.ruin.model.inter.utils.Config;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
public class polls extends DataFile {

    public static int latestPollId = 1;
    public static final Map<Integer, Poll> POLLS = new HashMap<Integer, Poll>();
    public static long lastSave;
    public static Config POLL_BOOTH;

    @Override
    public String path() {
        return "polls/live/*.json";
    }

    @Override
    public int priority() {
        return 20;
    }

    @Override
    public Object fromJson(File originalFile, String fileName, String json) {
        List<Poll> polls = JsonUtils.fromJson(json, List.class, Poll.class);
        polls.sort((p1, p2) -> p1 == null || p2 == null ? 0 : Math.max(p1.getPollId(), p2.getPollId()));
        for (final Poll p : polls) {
            if (p == null) {
                continue;
            }
            int id = p.getPollId();
            if (id > latestPollId) latestPollId = id;
            if (!p.isClosed()) POLL_BOOTH = Config.varpbit(4337, false).defaultValue(1);
            POLLS.put(id, p);
        }
        return polls;
    }

    public static void toJson() {
        toJson(latestPollId);
    }

    public static void toJson(int id) {
        try (PrintWriter pw = new PrintWriter(Server.dataFolder.getAbsolutePath() + "/polls/live/" + id + ".json", "UTF-8")) {
            List<Poll> poll = new ArrayList<>();
            poll.add(POLLS.get(id));
            pw.println(JsonUtils.GSON_PRETTY.toJson(poll));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkSave() {
        if (System.currentTimeMillis() - lastSave > 300000L) {
            lastSave = System.currentTimeMillis();
            toJson();
        }
    }
}
