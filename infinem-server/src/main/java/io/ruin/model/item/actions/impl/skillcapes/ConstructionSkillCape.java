package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.actions.impl.locations.prifddinas.PrifCityEntrance;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/21/2020
 */
public class ConstructionSkillCape {
    private static final int CAPE = StatType.Construction.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Construction.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Construction.masterCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerEquipment(CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerInventory(CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerEquipment(CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerInventory(TRIMMED_CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerEquipment(TRIMMED_CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerInventory(MASTER_CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerEquipment(MASTER_CAPE, "Teleport", (player, item) -> selectTeleport(player));
        ItemAction.registerInventory(MASTER_CAPE, "Tele to POH", (player, item) -> teleport(player));
        ItemAction.registerEquipment(MASTER_CAPE, "Tele to POH", (player, item) -> teleport(player));
    }

    public static void selectTeleport(Player player) {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Rimmington", () -> ModernTeleport.teleport(player, new Bounds(2953,3223,2955,3225,0))));
        options.add(new Option("Taverley", () -> ModernTeleport.teleport(player, new Bounds(2893,3464,2895,3466,0))));
        options.add(new Option("Pollnivneach", () -> ModernTeleport.teleport(player, new Bounds(3338,3003,3342,3005,0))));
        options.add(new Option("Hosidius", () -> ModernTeleport.teleport(player, new Bounds(1742,3516,1744,3518,0))));
        options.add(new Option("Rellekka", () -> ModernTeleport.teleport(player, new Bounds(2668, 3631, 2672, 3632, 0))));
        options.add(new Option("Aldarin", () -> ModernTeleport.teleport(player, new Bounds(1421, 2963, 1424, 2965, 0))));
        options.add(new Option("Brimhaven", () -> ModernTeleport.teleport(player, new Bounds(2757,3177,2759,3179,0))));
        options.add(new Option("Yanille", () -> ModernTeleport.teleport(player, new Bounds(2543,3094,2545,3096,0))));
        if (PrifCityEntrance.prifSkillCheckNoMessage(player)) {
            options.add(new Option("Prifddinas", () -> ModernTeleport.teleport(player, new Bounds(3238, 6075, 3240, 6076, 0))));
        }
        OptionScroll.open(player, "Portal Locations", options);
    }

    protected static void teleport(Player player) {
        player.getMovement().startTeleport(e -> {
            player.lock();
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(1);
            if (Config.TELEPORT_INSIDE.get(player) == 0) {
                player.house.buildAndEnter(player, false);
                while (player.isLocked())
                    e.delay(1);
            } else {
                e.delay(1);
                player.getMovement().teleport(player.house.getLocation().getPosition());
            }
            player.unlock();
        });
    }
}
