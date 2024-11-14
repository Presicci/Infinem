package io.ruin.model.entity.player.killcount;

import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/11/2023
 */
public class KillCounterType {
    private BossKillCounter bossKillCounter;
    private SlayerKillCounter slayerKillCounter;

    public KillCounterType(BossKillCounter bossKillCounter) {
        this.bossKillCounter = bossKillCounter;
    }

    public KillCounterType(SlayerKillCounter slayerKillCounter) {
        this.slayerKillCounter = slayerKillCounter;
    }

    public int increment(Player player) {
        String key = bossKillCounter == null ? slayerKillCounter.name() : bossKillCounter.name();
        KillCounter kc = player.killCounterMap.get(key);
        if (kc == null) {
            kc = new KillCounter();
        }
        kc.increment(player);
        player.killCounterMap.put(key, kc);
        return kc.getKills();
    }

    public int getKills(Player player) {
        String key = bossKillCounter == null ? slayerKillCounter.name() : bossKillCounter.name();
        KillCounter kc = player.killCounterMap.get(key);
        if (kc == null) return 0;
        return kc.getKills();
    }
}
