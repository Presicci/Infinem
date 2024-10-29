package io.ruin.model.inter.handlers;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/28/2024
 */
public class BossHealthBar {

    private static final int BOSS_HP_BAR = 303;
    private static final Color[] ORIGINAL_COLORS = {new Color(204, 0, 0), new Color(149, 0, 0), new Color(0, 245, 0)};

    public static void open(Player player, NPC npc) {
        open(player, npc, ORIGINAL_COLORS);
    }

    public static void open(Player player, NPC npc, Color[] colors) {
        if (Config.BOSS_HEALTH_BAR_ENABLED.get(player) == 1)
            return;
        Config.BOSS_HEALTH_BAR_POSITION.set(player, 1);
        Config.BOSS_HEALTH_BAR_ID.set(player, npc.getId());
        Config.BOSS_HEALTH_BAR_CURRENT_HP.set(player, npc.getHp());
        Config.BOSS_HEALTH_BAR_MAX_HP.set(player, npc.getMaxHp());

        player.getPacketSender().sendSetColor(303, 13, colors[0]);
        player.getPacketSender().sendSetColor(303, 14, colors[1]);
        player.getPacketSender().sendSetColor(303, 15, colors[2]);

        player.getPacketSender().sendClientScript(2887, 19857413, 19857416, 19857414, 19857415, 19857417, 19857419, 19857421, 19857422, 19857423, 19857428, 19857426, 19857427, 19857424, 19857425, 0);
        openScripts(player);
    }

    public static void updateBar(Player player, NPC npc) {
        if (Config.BOSS_HEALTH_BAR_ENABLED.get(player) == 1)
            return;
        Config.BOSS_HEALTH_BAR_ID.set(player, npc.getId());
        Config.BOSS_HEALTH_BAR_CURRENT_HP.set(player, npc.getHp());
        Config.BOSS_HEALTH_BAR_MAX_HP.set(player, npc.getMaxHp());
    }

    public static void closeFromDeath(Player player) {
        Config.BOSS_HEALTH_BAR_CURRENT_HP.set(player, 0);
        player.getPacketSender().sendClientScript(2889, 19857413, 19857416, 19857414, 19857415, 19857417, 19857419, 19857421, 19857422, 19857423, 19857428, 19857426, 19857427, 19857424, 19857425, 0);
        player.removeTemporaryAttribute("HEALTH_BAR_NPC");
    }

    public static void close(Player player) {
        player.getPacketSender().sendClientScript(2889, 19857413, 19857416, 19857414, 19857415, 19857417, 19857419, 19857421, 19857422, 19857423, 19857428, 19857426, 19857427, 19857424, 19857425, 0);
        player.removeTemporaryAttribute("HEALTH_BAR_NPC");
    }

    public static void updateColor(Player player, Color[] colors) {
        if (Config.BOSS_HEALTH_BAR_ENABLED.get(player) == 1)
            return;
        player.getPacketSender().sendSetColor(303, 13, colors[0]);
        player.getPacketSender().sendSetColor(303, 14, colors[1]);
        player.getPacketSender().sendSetColor(303, 15, colors[2]);
    }

    public static void resetColors(Player player) {
        updateColor(player, ORIGINAL_COLORS);
    }

    public static void tick(Player player) {
        if (!player.getCombat().isDefending(30)) {
            close(player);
            return;
        }
        NPC npc = player.getTemporaryAttributeOrDefault("HEALTH_BAR_NPC", null);
        if (npc == null || npc.getCombat() == null || npc.getCombat().isDead()) {
            closeFromDeath(player);
            return;
        }
        updateBar(player, npc);
    }

    private static void openScripts(Player p) {
        p.getPacketSender().sendClientScript(2887,
                BOSS_HP_BAR << 16 | 5,
                BOSS_HP_BAR << 16 | 8,
                BOSS_HP_BAR << 16 | 6,
                BOSS_HP_BAR << 16 | 7,
                BOSS_HP_BAR << 16 | 9,
                BOSS_HP_BAR << 16 | 11,
                BOSS_HP_BAR << 16 | 13,
                BOSS_HP_BAR << 16 | 14,
                BOSS_HP_BAR << 16 | 15,
                BOSS_HP_BAR << 16 | 20,
                BOSS_HP_BAR << 16 | 18,
                BOSS_HP_BAR << 16 | 19,
                BOSS_HP_BAR << 16 | 16,
                BOSS_HP_BAR << 16 | 17,
                255
        );
        p.getPacketSender().sendClientScript(2102,
                BOSS_HP_BAR << 16 | 5,
                BOSS_HP_BAR << 16 | 20,
                BOSS_HP_BAR << 16 | 13,
                BOSS_HP_BAR << 16 | 14,
                BOSS_HP_BAR << 16 | 15,
                BOSS_HP_BAR << 16 | 8,
                BOSS_HP_BAR << 16 | 9,
                BOSS_HP_BAR << 16 | 18,
                BOSS_HP_BAR << 16 | 19,
                BOSS_HP_BAR << 16 | 16,
                BOSS_HP_BAR << 16 | 17,
                1
        );
        p.getPacketSender().sendClientScript(2376,
                BOSS_HP_BAR << 16 | 0,
                BOSS_HP_BAR << 16 | 2,
                BOSS_HP_BAR << 16 | 4,
                BOSS_HP_BAR << 16 | 5,
                BOSS_HP_BAR << 16 | 8,
                BOSS_HP_BAR << 16 | 10,
                BOSS_HP_BAR << 16 | 20,
                BOSS_HP_BAR << 16 | 13,
                BOSS_HP_BAR << 16 | 14,
                BOSS_HP_BAR << 16 | 15,
                BOSS_HP_BAR << 16 | 9,
                BOSS_HP_BAR << 16 | 6,
                BOSS_HP_BAR << 16 | 7,
                BOSS_HP_BAR << 16 | 11,
                BOSS_HP_BAR << 16 | 18,
                BOSS_HP_BAR << 16 | 19,
                BOSS_HP_BAR << 16 | 16,
                BOSS_HP_BAR << 16 | 17,
                BOSS_HP_BAR << 16 | 3
        );
    }

    public static final Set<String> HP_BAR_BOSSES = new HashSet<>(Arrays.asList(
            "callisto", "artio", "spindel", "venenatis", "calvar'ion", "vet'ion", "king black dragon", "zulrah", "vorkath", "nex", "corporeal beast",
            "phantom muspah", "general graardor", "commander zilyana", "kree'arra", "k'ril tsutsaroth", "kalphite queen", "sarachnis", "scorpia",
            "obor", "bryophyta", "hespori", "skotizo", "abyssal sire", "cerberus", "kraken", "thermonuclear smoke devil", "alchemical hydra",
            "tztok-jad", "tzkal-zuk", "tekton", "vanguard", "vespula", "vasa nistirio", "muttadile", "great olm"
    ));

    static {
        NPCDefinition.forEach(npc -> {
            if (HP_BAR_BOSSES.contains(npc.name.toLowerCase()))
            npc.custom_values.put("BOSS_BAR", 1);
        });
    }
}