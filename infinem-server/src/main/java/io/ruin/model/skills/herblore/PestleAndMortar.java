package io.ruin.model.skills.herblore;

import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;

public enum PestleAndMortar {

    UNICORN_HORN(237, 235, "You grind the unicorn horn to dust."),
    CHOCOLATE_BAR(1973, 1975, "You grind the chocolate to dust."),
    KEBBIT_TEETH(10109, 10111, "You grind the kebbit teeth to dust."),
    BIRD_NEST(5075, 6693, "You grind the bird's nest down."),
    DESERT_GOAT_HORN(9735, 9736, "You grind the goat's horn to dust."),
    CHARCOAL(973, 704, "You grind the charcoal to a powder."),
    ASHES(592, 8865, "You grind down the ashes."),
    BLUE_DRAGON_SCALE(243, 241, "You grind the dragon scale to dust."),
    LAVA_SCALE(11992, 11994, null),
    SUPERIOR_DRAGON_BONES(22124, 21975, "You grind the superior bones to dust."),
    COD(Items.RAW_COD, Items.GROUND_COD, "You grind the raw cod."),
    CRAB_MEAT(Items.CRAB_MEAT, Items.GROUND_CRAB_MEAT, "You grind the crab meat."),
    KELP(Items.KELP, Items.GROUND_KELP, "You grind the kelp."),
    CRYSTAL_DUST(23962, 23867, "You grind the shards to dust."),
    KARAMBWAN_PASTE_R(Items.RAW_KARAMBWAN, 3152, "You grind the karambwan to a paste."),
    KARAMBWAN_PASTE_P(Items.POISON_KARAMBWAN, 3153, "You grind the karambwan to a paste."),
    KARAMBWAN_PASTE_C(Items.COOKED_KARAMBWAN, 3154, "You grind the karambwan to a paste."),
    ANCHOVY_PASTE(Items.ANCHOVIES, Items.ANCHOVY_PASTE, "You grind the anchovies to a paste.");

    public final int before, after;

    public final String message;

    PestleAndMortar(int before, int after, String message) {
        this.before = before;
        this.after = after;
        this.message = message;
    }

    static {
        for (PestleAndMortar item : values()) {
            ItemItemAction.register(item.before, Tool.PESTLE_AND_MORTAR, (player, before, pestleAndMortar) -> player.startEvent(event -> {
                for (Item resource : player.getInventory().getItems()) {
                    if (resource == null || resource.getId() != item.before)
                        continue;
                    if (item == LAVA_SCALE) {
                        int amountOfShards = Random.get(3, 6);
                        resource.remove();
                        player.getInventory().add(item.after, amountOfShards);
                        player.sendMessage("You grind the lava dragon scale into " + amountOfShards + " shards.");
                    } else if (item == CRYSTAL_DUST) {
                        resource.remove(1);
                        player.getInventory().add(item.after, 10);
                        player.sendMessage(item.message);
                    } else {
                        resource.setStackableId(item.after);
                        player.sendMessage(item.message);
                    }
                    player.animate(364);
                    event.delay(2);
                }
            }));
        }
    }
}
