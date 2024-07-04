package io.ruin.model.skills.construction;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/4/2024
 */
public enum HouseStyleBlueprint {
    HOSIDIUS(24885, HouseStyle.HOSIDIUS);

    private final int blueprintId;
    private final HouseStyle unlockedStyle;

    HouseStyleBlueprint(int blueprintId, HouseStyle unlockedStyle) {
        this.blueprintId = blueprintId;
        this.unlockedStyle = unlockedStyle;
    }

    private void inspect(Player player, Item item) {
        String key = unlockedStyle.name() + "_BLUEPRINT";
        if (player.hasAttribute(key)) {
            player.sendMessage("You already have this style unlocked.");
            return;
        }
        String styleName = StringUtils.initialCaps(unlockedStyle.name().toLowerCase());
        player.dialogue(
                new MessageDialogue("These blueprints appear to be a set of instructions for creating the " + styleName + " player-owned house theme.<br><br>Would you like to learn the style?"),
                new OptionsDialogue("Learn the blueprints?",
                        new Option("Yes (Blueprints are destroyed)", () -> {
                            item.remove();
                            player.putAttribute(key, 1);
                        }),
                        new Option("No")
                )
        );
    }

    static {
        for (HouseStyleBlueprint blueprint : values()) {
            ItemAction.registerInventory(blueprint.blueprintId, "inspect", blueprint::inspect);
        }
    }
}
