package io.ruin.model.skills.agility;

import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentModifier;
import io.ruin.model.content.tasksystem.relics.impl.fragments.FragmentType;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/13/2025
 */
public class Agility {

    public static void completeLap(Player player) {
        player.getTaskManager().doLookupByCategory(TaskCategory.AGILITY_LAP, 1, true);
        int fragmentCoinAmount = (int) player.getRelicFragmentManager().getModifierValue(FragmentType.Agility, FragmentModifier.AGILITY_LAP_GOLD);
        if (fragmentCoinAmount > 0) {
            player.getInventory().addOrDrop(995, fragmentCoinAmount);
        }
    }
}
