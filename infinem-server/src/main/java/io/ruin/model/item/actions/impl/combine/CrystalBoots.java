package io.ruin.model.item.actions.impl.combine;


import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

public class CrystalBoots {

    private static final int DRAGON_BOOTS = 11840;
    private static final int RANGER_BOOTS = 2577;
    private static final int INFINITY_BOOTS = 6920;

    private static final int PRIMORDIAL_CRYSTAL = 13231;
    private static final int PRIMORDIAL_BOOTS = 13239;

    private static final int PEGASIAN_CRYSTAL = 13229;
    private static final int PEGASIAN_BOOTS = 13237;

    private static final int ETERNAL_CRYSTAL = 13227;
    private static final int ETERNAL_BOOTS = 13235;

    private static void makeBoots(Player player, Item primary, Item secondary, int result) {
        if(player.getStats().get(StatType.Runecrafting).currentLevel < 60 || player.getStats().get(StatType.Magic).currentLevel < 60) {
            player.dialogue(new ItemDialogue().two(primary.getId(), secondary.getId(), "You need level 60 Magic and level 60 Runecrafting to use the crystal."));
            return;
        }

        Item item = new Item(result, 1);
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "Infuse the " + primary.getDef().name + " and "+ secondary.getDef().name +" into the " + item.getDef().name + "?" , item, () -> {
                    player.animate(4462);
                    player.graphics(759);
                    primary.remove();
                    secondary.remove();
                    player.getInventory().add(result, 1);
                    player.getStats().addXp(StatType.Magic, 200.0, false);
                    player.getStats().addXp(StatType.Runecrafting, 200.0, false);
                    player.dialogue(new ItemDialogue().one(result, "You successfully infuse the " + primary.getDef().name + " and " + secondary.getDef().name +" to create " + item.getDef().name));
                })
        );
    }

    static {
        ItemItemAction.register(PRIMORDIAL_CRYSTAL, DRAGON_BOOTS, (player, primary, secondary) -> makeBoots(player, primary, secondary, PRIMORDIAL_BOOTS));
        ItemItemAction.register(PEGASIAN_CRYSTAL, RANGER_BOOTS, (player, primary, secondary) -> makeBoots(player, primary, secondary, PEGASIAN_BOOTS));
        ItemItemAction.register(ETERNAL_CRYSTAL, INFINITY_BOOTS, (player, primary, secondary) -> makeBoots(player, primary, secondary, ETERNAL_BOOTS));
    }

}
