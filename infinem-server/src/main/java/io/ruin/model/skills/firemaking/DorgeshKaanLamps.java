package io.ruin.model.skills.firemaking;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/5/2023
 */
public class DorgeshKaanLamps {

    private static final Map<Integer, Config> LAMP_CONFIGS = new HashMap<>();

    private static final String attKey = "LAMPS_FIXED";

    public static final int MAX_BROKEN = 15;

    private static void fixLamp(Player player, Item item, GameObject object) {
        if (!player.getInventory().contains(Items.LIGHT_ORB)) {
            player.sendMessage("You need a light orb to fix that lamp.");
            return;
        }
        Config config = LAMP_CONFIGS.get(object.getDef().varpBitId);
        if (config == null) return;
        if (config.get(player) != 1) {
            player.sendMessage("That lamp does not need fixing.");
            return;
        }
        if (!player.getStats().check(StatType.Firemaking, 52, "fix the lamp")) return;

        player.lock();
        player.startEvent(e -> {
            player.animate(3572);
            e.delay(1);
            item.remove(1);
            breakLamp(player, 1);   // Break a new lamp after fixing 1
            int lampsFixed = player.incrementNumericAttribute(attKey, 1);
            boolean milestone = lampsFixed % 100 == 0;
            player.getStats().addXp(StatType.Firemaking, milestone ? 6000 : 1000, true);
            config.set(player, 0);
            player.sendMessage(milestone ? "You have replaced 100 orbs and have gained extra experience." : "You fix the lamp.");
            player.getTaskManager().doLookupByUUID(932);    // Fix a Lamp in Dorgesh-Kaan
            if (lampsFixed >= 100) {
                player.getTaskManager().doLookupByUUID(935);    // Fix 100 Lamps in Dorgesh-Kaan
            }
            player.unlock();
        });
    }

    public static int getBrokenLamps(Player player) {
        int count = 0;
        for (Config config : LAMP_CONFIGS.values()) {
            if (config.get(player) == 1) count++;
        }
        return count;
    }

    public static void breakLamp(Player player, int amount) {
        List<Config> shuffledConfigs = Arrays.asList(LAMP_CONFIGS.values().toArray(new Config[0]));
        Collections.shuffle(shuffledConfigs);
        int count = 0;
        for (Config config : shuffledConfigs) {
            if (count >= amount) break;
            if (config.get(player) == 1) continue;
            config.set(player, 1);
            count++;
        }
    }

    static {
        int[] VARPS = {
                33, 297, 998, 3263, 3469, 3470, 3471, 3472, 3473, 3474, 3475, 3476, 3477, 3478, 3479, 3480,
                3481, 3482, 3483, 3484, 3485, 3486, 3487, 3488, 3489, 3490, 3491, 3492, 3493, 3494, 3495, 3496,
                3497, 3498, 3499, 3500, 3501, 3502, 3503, 3504, 3505, 3506, 3507, 3508, 3509, 3510, 3511, 3512,
                3513, 3514, 3515, 3516, 3517, 3518, 3519, 3520, 3521, 3522, 4032, 4033, 4034, 4035, 4036, 4037
        };
        for (int varp : VARPS) {
            LAMP_CONFIGS.put(varp, Config.varpbit(varp, false));
        }
        ItemObjectAction.register(Items.LIGHT_ORB, 22977, DorgeshKaanLamps::fixLamp);
    }
}
