package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/26/2021
 */
public class BonecrusherNecklace {

    private static final int BONECRUSHER = 13116, DRAGONBONE_NECKLACE = 22111, HYDRA_TAIL = 22988, BONECRUSHER_NECKLACE = 22986, ECTO_TOKEN = 4278;

    private static void combine(Player player) {
        ArrayList<Item> items = player.getInventory().collectOneOfEach(BONECRUSHER, DRAGONBONE_NECKLACE, HYDRA_TAIL);
        if (items == null) {
            return;
        }
        for (Item item : items) {
            if (item.getId() != BONECRUSHER) {
                player.getInventory().remove(item);
            } else {
                item.setId(BONECRUSHER_NECKLACE);
            }
        }
        player.sendMessage("You combine the bonecrusher, dragonbone necklace and hydra tail to create a bonecrusher necklace.");
    }

    private static void dismantle(Player player) {
        Item necklace = player.getInventory().findItem(BONECRUSHER_NECKLACE);
        if (necklace == null) {
            return;
        }
        necklace.setId(BONECRUSHER);
        player.getInventory().add(DRAGONBONE_NECKLACE, 1);
        player.getInventory().add(HYDRA_TAIL, 1);
        player.sendMessage("You dismantle the necklace.");
    }

    private static void makeNecklace(Player player) {
        player.dialogue(
                new MessageDialogue("Are you sure you wish the combine the Hydra Tail,<br>Dragonbone Necklace, and the Bonecrusher<br>to create the Bonecrusher necklace?"),
                new YesNoDialogue("Are you sure you want to do this?", "Your bonecrusher charges will be retained.", BONECRUSHER_NECKLACE, 1, () -> {
                    combine(player);
                })
        );
    }

    private static void dismantleNecklace(Player player) {
        player.dialogue(
                new MessageDialogue("Are you sure you wish to dismantle your Bonecrusher Necklace into a Hydra Tail, Dragonbone Necklace and Bonecrusher?"),
                new YesNoDialogue("Are you sure you want to do this?", "Your bonecrusher charges will be retained.", BONECRUSHER, 1, () -> {
                    dismantle(player);
                })
        );
    }

    static {
        ItemAction.registerInventory(BONECRUSHER_NECKLACE, "activity", (player, item) -> player.getBoneCrusher().toggleActive());
        ItemAction.registerInventory(BONECRUSHER_NECKLACE, "dismantle", (player, item) -> dismantleNecklace(player));
        ItemAction.registerInventory(BONECRUSHER_NECKLACE, "check/uncharge", (player, item) -> player.dialogue(
                new OptionsDialogue("What would you like to do?",
                        new Option("Check charges", () -> player.getBoneCrusher().checkCharges()),
                        new Option("Remove charges", () -> {
                            if (player.getBoneCrusher().getCharges() <= 0) {
                                player.sendMessage("Your bonecrusher does not have any charges.");
                                return;
                            }
                            player.getInventory().addOrDrop(ECTO_TOKEN, (player.getBoneCrusher().getCharges() / 25));
                            player.getBoneCrusher().setCharges(0);
                            player.sendMessage("You remove all the charges from your bonecrusher.");
                        })
                )));
        ItemItemAction.register(BONECRUSHER_NECKLACE, ECTO_TOKEN, (player, primary, secondary) -> {
            int charges = player.getBoneCrusher().getCharges();
            player.getBoneCrusher().setCharges(charges + (secondary.getAmount() * 25));
            player.getInventory().remove(secondary.getId(), secondary.getAmount());
            player.getBoneCrusher().checkCharges();
        });

        ItemItemAction.register(BONECRUSHER, DRAGONBONE_NECKLACE, (player, primary, secondary) -> {
            makeNecklace(player);
        });
        ItemItemAction.register(BONECRUSHER, HYDRA_TAIL, (player, primary, secondary) -> {
            makeNecklace(player);
        });
        ItemItemAction.register(HYDRA_TAIL, DRAGONBONE_NECKLACE, (player, primary, secondary) -> {
            makeNecklace(player);
        });
    }
}
