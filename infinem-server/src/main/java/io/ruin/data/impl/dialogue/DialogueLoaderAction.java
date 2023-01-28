package io.ruin.data.impl.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.stat.StatType;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public enum DialogueLoaderAction {
    FRITZ_SELL((player -> {
        player.dialogue(
                new NPCDialogue(2053, "Fantastic! Let's see, for that much glass I could pay you ..."),
                new NPCDialogue(2053, "... " + player.getInventory().getAmount(Items.MOLTEN_GLASS) * 20 + " gold pieces!"),
                new OptionsDialogue(
                        new Option("Are you mad? I've run all over the island to make this glass.", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Are you mad? I've run all over the island to make this glass."),
                                    new NPCDialogue(2053, "Pity. Well you can find me here if you change your mind.")
                            );
                        }),
                        new Option("Sure, that sounds like a fair price.", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Sure, that sounds like a fair price."),
                                    new NPCDialogue(2053, "Here you are then."),
                                    new ItemDialogue().two(995, Items.MOLTEN_GLASS, "You trade the glass for some gold.").action(
                                            () -> {
                                                int amt = player.getInventory().getAmount(Items.MOLTEN_GLASS);
                                                player.getInventory().remove(Items.MOLTEN_GLASS, amt);
                                                player.getInventory().add(995, amt * 20);
                                            }
                                    ),
                                    new NPCDialogue(2053, "Thanks very much, [player name]. I'll buy any more that you bring.")
                            );
                        })
                )
        );
    })),
    HEAL((player) -> player.getStats().get(StatType.Hitpoints).restore()),
    ITEM(null),  // Handled specifically in the loader
    SHOP(null),
    ;

    private final Consumer<Player> action;

    DialogueLoaderAction(Consumer<Player> action) {
        this.action = action;
    }


}
