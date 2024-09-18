package io.ruin.model.skills.smithing.blastfurnace;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.smithing.SmithBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2024
 */
public class BlastFurnaceDispenser {

    private static void withdraw(Player player, Item item, int amount) {
        if (item == null) return;
        int inventorySpace = player.getInventory().getFreeSlots();
        SmithBar bar = SmithBar.getDataByBar(item.getId());
        if (bar == null) return;
        int amt = BlastFurnace.getBar(player, bar);
        amt = amount > amt ? Math.min(amt, inventorySpace) : Math.min(amount, inventorySpace);
        BlastFurnace.removeBar(player, bar, amt);
        player.getInventory().add(item.getId(), amt);
        if (!BlastFurnace.hasBars(player)) {
            BlastFurnace.DISPENSER.set(player, 0);
        }
        // processvarbits
        player.dialogue(new MessageDialogue("You take " + amt + " " + item.getDef().name.toLowerCase() + (amt > 1 ? "s" : "") + " from the dispenser."));
        BlastFurnace.processBars(player);
    }

    private static void take(Player player) {
        Item[] bars = BlastFurnace.constructBarArray(player);
        if (bars.length != 0) {
            // Withdraw
            if (player.getInventory().hasFreeSlots(1)) {
                List<SkillItem> skillItems = new ArrayList<>();
                for (Item bar : bars) {
                    skillItems.add(new SkillItem(bar.getId()).addAction((p, amt, e) -> withdraw(p, bar, amt)));
                }
                SkillDialogue.make(player, skillItems.toArray(new SkillItem[0]));
            } else {
                player.sendMessage("You don't have any inventory space to grab your bars.");
            }
        } else {
            BlastFurnace.DISPENSER.set(player, 0);
            player.sendMessage("You have no bars.");
        }
    }

    protected static final List<SmithBar> DISPENSER_BARS = Arrays.asList(
            SmithBar.BRONZE, SmithBar.IRON, SmithBar.STEEL, SmithBar.SILVER, SmithBar.GOLD, SmithBar.MITHRIL, SmithBar.ADAMANT, SmithBar.RUNITE
    );

    private static void check(Player player) {
        int counter = 0;
        StringBuilder page1 = new StringBuilder();
        StringBuilder page2 = new StringBuilder();
        for(final SmithBar bar : DISPENSER_BARS) {
            String string = StringUtils.capitalizeFirst(bar.toString().toLowerCase().replace("_", " ")) + ": " + BlastFurnace.getBar(player, bar) + "<br>";
            (counter < 5 ? page1 : page2).append(string);
            counter++;
        }
        List<Dialogue> dialogues = new ArrayList<>();
        if (page1.length() > 0) dialogues.add(new MessageDialogue(page1.toString()));
        if (page2.length() > 0) dialogues.add(new MessageDialogue(page2.toString()));
        if (dialogues.isEmpty()) dialogues.add(new MessageDialogue("You don't have any bars in the dispenser."));
        player.dialogue(dialogues.toArray(new Dialogue[0]));
    }

    private static void click(Player player) {
        switch (BlastFurnace.DISPENSER.get(player)) {
            case 0: // No ore
                player.dialogue(new MessageDialogue("You don't have any bars in the dispenser."));
                break;
            case 1: // Pouring
                break;
            case 2: // Cooling
                if (!player.getEquipment().hasId(Items.ICE_GLOVES)) {
                    player.dialogue(new MessageDialogue("The bars are still molten! You need to cool them down."));
                    return;
                }
                player.putTemporaryAttribute("BF_EARLY_COOL", 1);
                take(player);
                break;
            case 3: // Cooled
                take(player);
                break;
        }
    }

    static {
        ItemObjectAction.register(Items.BUCKET_OF_WATER, 9092, (player, item, obj) -> {
            switch (BlastFurnace.DISPENSER.get(player)) {
                case 1:
                    player.sendMessage("You can't do that yet.");
                    break;
                case 2:
                    player.animate(832);
                    item.setId(Items.BUCKET);
                    player.putTemporaryAttribute("BF_EARLY_COOL", 1);
                    BlastFurnace.DISPENSER.set(player, 3);
                    player.sendMessage("You pour water over the dispenser, cooling the bars.");
                    break;
                case 3:
                    player.sendMessage("It has already cooled.");
                    break;
            }
        });
        ObjectAction.register(9092, 1, (player, obj) -> click(player));
        ObjectAction.register(9092, 2, (player, obj) -> check(player));
    }
}
