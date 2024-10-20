package io.ruin.model.entity.player;

import io.ruin.api.utils.StringUtils;
import io.ruin.api.utils.XenPost;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Descending order from highest priority group
 */
public enum PlayerGroup {
    OWNER(100, 7, 41, 100),
    DEVELOPER(3, 8, 0, 100),
    COMMUNITY_MANAGER(8, 8, 42, "Server Manager"),
    ADMINISTRATOR(5, 2, 1, 0),
    MODERATOR(4, 1, 0, 0),
    FORUM_MODERATOR(7, 1, 0, 0),
    SUPPORT(9, 8, 42, 0),
    YOUTUBER(10, 9, 43, 0),
    BETA_TESTER(6, 0, -1, 0),
    ZENYTE(17, 15, 50, 100),        // <col=E79B3A>
    ONYX(16, 15, 49, 80),           // <col=444444>
    DRAGONSTONE(15, 14, 48, 60),    // <col=aa00aa>
    DIAMOND(14, 13, 47, 40),        // <col=dddddd>
    RUBY(13, 12, 46, 25),           // <col=ff5555>
    EMERALD(12, 11, 45, 15),        // <col=55ff55>
    SAPPHIRE(11, 10, 44, 10),        // <col=5555ff>
    REGISTERED(2, 0, -1, 0),
    BANNED(18, 0, -1, 0);

    public final int id;

    public final int clientId;

    public final int clientImgId;

    public String title;
    @Getter
    public int doubleDropChance;

    PlayerGroup(int id, int clientId, int clientImgId, String title) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.title = title;
    }
    PlayerGroup(int id, int clientId, int clientImgId, int doubleDropChance) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.doubleDropChance = doubleDropChance;
        this.title = StringUtils.getFormattedEnumName(name());
    }

    PlayerGroup(int id, int clientId, int clientImgId) {
        this(id, clientId, clientImgId, "");
    }

    public void sync(Player player) {
        sync(player, null);
    }

    public void sync(Player player, Runnable successAction) {
        if (id >= 9 && id <= 15
                && !player.isGroup(OWNER)
                && !player.isGroup(MODERATOR)
                && !player.isGroup(ADMINISTRATOR)
                && !player.isGroup(COMMUNITY_MANAGER)
                && !player.isGroup(DEVELOPER)) {
            CompletableFuture.runAsync(() -> {
                Map<Object, Object> map = new HashMap<>();
                map.put("username", player.getName());
                map.put("rank", id);
                String result = XenPost.post("update_rank", map);
                if(successAction != null && "1".equals(result))
                    successAction.run();
            });
        }
    }

    public void removePKMode(Player player, String type) {
        removePKMode(player, type, null);
    }

    public void removePKMode(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            String result = XenPost.post("remove_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }

    public String tag() {
        return "<img=" + clientImgId + ">";
    }

    public static final PlayerGroup[] GROUPS_BY_ID;

    static {
        int highestGroupId = 0;
        for(PlayerGroup group : values()) {
            if(group.id > highestGroupId)
                highestGroupId = group.id;
        }
        GROUPS_BY_ID = new PlayerGroup[highestGroupId + 1];
        for(PlayerGroup group : values())
            GROUPS_BY_ID[group.id] = group;
    }

}