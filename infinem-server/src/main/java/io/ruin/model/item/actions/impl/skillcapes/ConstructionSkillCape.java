package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/21/2020
 */
public class ConstructionSkillCape {
    private static final int CAPE = StatType.Construction.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Construction.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerEquipment(CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerInventory(CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerEquipment(CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Tele to POH", (player, item) -> teleport(player));
    }

    public static void selectTeleport(Player player) {
        player.dialogue(new OptionsDialogue("Choose Location:",
                new Option("Rimmington", () -> ModernTeleport.teleport(player, new Bounds(2953,3223,2955,3225,0))),
                new Option("Taverley", () -> ModernTeleport.teleport(player, new Bounds(2893,3464,2895,3466,0))),
                new Option("Pollnivneach", () -> ModernTeleport.teleport(player, new Bounds(3338,3003,3342,3005,0))),
                new Option("Next", () -> selectTeleport2(player))
        ));
    }

    protected static void selectTeleport2(Player player) {
        player.dialogue(new OptionsDialogue("Choose Location:",
                new Option("Hosidius", () -> ModernTeleport.teleport(player, new Bounds(1742,3516,1744,3518,0))),
                new Option("Brimhaven", () -> ModernTeleport.teleport(player, new Bounds(2757,3177,2759,3179,0))),
                new Option("Yanille", () -> ModernTeleport.teleport(player, new Bounds(2543,3094,2545,3096,0))),
                new Option("Previous", () -> selectTeleport(player))
        ));
    }

    protected static void teleport(Player player) {
        player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(2);
            if (Config.TELEPORT_INSIDE.get(player) == 0) {
                player.house.buildAndEnter(player, false);
            } else {
                player.getMovement().teleport(player.house.getLocation().getPosition());
            }
        });
    }
}
