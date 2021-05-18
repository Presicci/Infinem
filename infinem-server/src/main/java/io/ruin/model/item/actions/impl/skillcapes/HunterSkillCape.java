package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/17/2021
 */
public class HunterSkillCape {
    private static final int CAPE = StatType.Hunter.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Hunter.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Teleport", HunterSkillCape::hunterTeleport);
        ItemAction.registerEquipment(CAPE, "Teleport", HunterSkillCape::hunterTeleport);

        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", HunterSkillCape::hunterTeleport);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Teleport", HunterSkillCape::hunterTeleport);
    }

    protected static void hunterTeleport(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose an option",
                new Option("Carnivorous chinchompas", () -> ModernTeleport.teleport(player, new Bounds(2548, 2908, 2551, 2912, 0))),
                new Option("Black chinchompas (33 Wilderness)", () -> ModernTeleport.teleport(player, new Bounds(3135, 3780, 3141, 3787, 0)))));
    };
}
