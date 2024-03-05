package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/5/2024
 */
@AllArgsConstructor
public enum MonkeyBackpack {
    ORIGINAL_MONKEY(19556, 0),
    KARAMJA_MONKEY(24862, 100),
    ZOMBIE_MONKEY(24863, 250),
    MANIACAL_MONKEY(24864, 500),
    SKELETON_MONKEY(24865, 1000),
    KRUK_JR(24866, 1500),
    PRINCELY_MONKEY(24867, 2000);

    private final int itemId, lapCount;

    private void transform(Player player, Item item) {
        int laps = PlayerCounter.APE_ATOLL_COURSE.get(player);
        if (laps < lapCount) {
            player.dialogue(new MessageDialogue("You need to complete " + lapCount + " laps of the Ape Atoll Agility Course to unlock that transformation<br><br>" +
                    "You have currently completed " + laps + " laps."));
            return;
        }
        item.setId(itemId);
        player.getCollectionLog().collectIfNotCollected(item);
    }

    private void transformScroll(Player player, Item item) {
        int laps = PlayerCounter.APE_ATOLL_COURSE.get(player);
        List<Option> options = new ArrayList<>();
        for (MonkeyBackpack backpack : values()) {
            if (backpack == this) continue;
            boolean canTransform = laps >= backpack.lapCount;
            options.add(new Option((canTransform ? "" : "<str>") + StringUtils.getFormattedEnumName(backpack), () -> backpack.transform(player, item)));
        }
        OptionScroll.open(player, "Select a Monkey Transformation:", true, options);
    }

    private static void check(Player player) {
        player.dialogue(new MessageDialogue("Your Ape Atoll Agility lap count is: " + Color.RED.wrap(PlayerCounter.APE_ATOLL_COURSE.get(player) + "") + "."));
    }

    static {
        for (MonkeyBackpack backpack : values()) {
            ItemAction.registerInventory(backpack.itemId, "lap-count", (player, item) -> check(player));
            ItemAction.registerInventory(backpack.itemId, "transform", backpack::transformScroll);
        }
        // Crate to get monkey
        ObjectAction.register(27256, 2030, 5609, 0, "search", (player, obj) -> {
            Item cape = player.getEquipment().get(Equipment.SLOT_CAPE);
            for (MonkeyBackpack backpack : values()) {
                if (cape.getId() == backpack.itemId || player.getInventory().hasId(backpack.itemId)) {
                    player.sendMessage("The crate is empty.");
                    return;
                }
                if (player.getInventory().freeSlot() < 1) {
                    player.dialogue(new MessageDialogue("You do not have enough inventory space for a monkey."));
                    return;
                }
                player.startEvent(e -> {
                    player.lock();
                    player.animate(827);
                    e.delay(1);
                    player.getInventory().add(19556);
                    player.dialogue(new ItemDialogue().one(19556, "There's a sweet little monkey hiding in the warm crate. You gently pick it up."));
                    player.unlock();
                });
            }
        });
    }
}
