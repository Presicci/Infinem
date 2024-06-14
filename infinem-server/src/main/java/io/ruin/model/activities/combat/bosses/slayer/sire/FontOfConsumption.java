package io.ruin.model.activities.combat.bosses.slayer.sire;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.List;

public class FontOfConsumption {

    public static LootTable LOOT = new LootTable().addTable(1,
            new LootItem(Items.BLUDGEON_SPINE, 1, 1, 61),
            new LootItem(Items.ABYSSAL_DAGGER, 1, 1, 25),
            new LootItem(Items.ABYSSAL_WHIP, 1, 1, 12),
            new LootItem(Items.JAR_OF_MIASMA, 1, 1, 13),
            new LootItem(Items.ABYSSAL_HEAD, 1, 1, 10),
            new LootItem(Items.ABYSSAL_ORPHAN, 1, 1, 5)
    );

    private static int getNextBludgeonPiece(Player player) {
        int claws = player.getItemAmount(Items.BLUDGEON_CLAW);
        int spines = player.getItemAmount(Items.BLUDGEON_SPINE);
        int axons = player.getItemAmount(Items.BLUDGEON_AXON);;
        int lowest = Math.min(Math.min(claws, axons), spines);
        List<Integer> possible = new ArrayList<>();
        if (lowest == spines)
            possible.add(13274);
        if (lowest == claws)
            possible.add(13275);
        if (lowest == axons)
            possible.add(13276);
        return Random.get(possible);
    }

    static {
        ItemObjectAction.register(13273, 27029, (player, item, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.face(Direction.NORTH);
                event.delay(1);
                player.dialogue(new ItemDialogue().one(13273, "You place the Unsired into the Font of Consumption...").hideContinue());
                player.animate(827);
                World.sendGraphics(1276, 0, 0, player.getPosition().relative(0, 1));
                event.delay(2);
                Item reward = LOOT.rollItem();
                if (reward.getId() == Items.ABYSSAL_ORPHAN) {
                    Pet.ABYSSAL_ORPHAN.unlock(player);
                } else {
                    if (reward.getId() == Items.BLUDGEON_SPINE) {
                        reward.setId(getNextBludgeonPiece(player));
                    }
                    item.setId(reward.getId());
                    player.getCollectionLog().collect(reward);
                }

                player.dialogue(new ItemDialogue().one(reward.getId(), "The Font consumes the Unsired and returns you a<br>reward."));
                player.unlock();
            });
        });

        ObjectAction.register(27057, 1, (player, obj) -> { // TODO find chathead and full dialogue... @Nick :)
            List<Item> pieces = player.getInventory().collectOneOfEach(13274, 13275, 13276);
            if (pieces == null) {
                player.dialogue(new MessageDialogue("You'll need a bludgeon claw, axon and spine<br> to have the Overseer create a weapon for you."));
            } else {
                player.startEvent(event -> {
                    player.lock();
                    pieces.forEach(Item::remove);
                    player.dialogue(new ItemDialogue().two(13274, 13275, "You hand over the components to the Overseer.").hideContinue());
                    event.delay(2);
                    player.getInventory().add(13263, 1);
                    player.dialogue(new ItemDialogue().one(13263, "The Overseer presents you with an Abyssal Bludgeon."));
                    player.unlock();
                });
            }
        });
    }
}
