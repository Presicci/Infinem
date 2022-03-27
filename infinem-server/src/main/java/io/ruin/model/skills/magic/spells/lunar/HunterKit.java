package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class HunterKit extends Spell {

    public HunterKit() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.EARTH.toItem(2)
        };
        registerClick(71, 70, true, runes, (p, i) -> {
            if (p.getInventory().hasId(11159)) {
                p.sendMessage("You can only carry one Hunter kit at a time.");
                return false;
            }
            if (p.getInventory().containsAll(false, KIT_ITEMS)) {
                p.sendMessage("You already have a full set of hunter equipment.");
                return false;
            }
            if (p.getInventory().getFreeSlots() < 1) {
                p.sendMessage("You need a free inventory space to cast this spell.");
                return false;
            }
            p.privateSound(2888);
            p.animate(6303);
            p.graphics(1074);
            p.getInventory().add(11159, 1);
            p.sendFilteredMessage("You conjure a hunter kit.");
            return true;
        });
    }

    private static final Item[] KIT_ITEMS = {
            new Item(Items.NOOSE_WAND, 1),
            new Item(Items.BUTTERFLY_NET, 1),
            new Item(Items.BIRD_SNARE, 1),
            new Item(Items.RABBIT_SNARE, 1),
            new Item(Items.TEASING_STICK, 1),
            new Item(Items.UNLIT_TORCH, 1),
            new Item(Items.BOX_TRAP, 1),
            new Item(Items.IMPLING_JAR, 1)
    };

    static {
        ItemAction.registerInventory(11159, "open", ((player, item) -> {
            if (!player.getInventory().hasFreeSlots(7)) {
                player.sendMessage("You need seven free inventory spaces to unpack the kit.");
                return;
            }
            player.getInventory().remove(11159, 1);
            for (Item kitItem : Arrays.asList(KIT_ITEMS)) {
                player.getInventory().add(kitItem);
            }
        }));
    }
}
