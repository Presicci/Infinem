package io.ruin.model.content.books.impl;

import io.ruin.model.content.books.Book;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/26/2023
 */
public class EntomologistsDiary extends Book {

    public EntomologistsDiary(final Player player) {
        super(player);
    }

    @Override
    public String getString() {
        return "They've laughed at me for long enough. I'm tired of just being Buggie the Bug-hunter." +
                " <br> " +
                "Entomology is a serious science. Yet those fools at the zoo wouldn't give me my own display, oh no." +
                " <br> " +
                "I could have created a highly educational exhibit, teaching the public about the wonders of the insect world, but the idiots who run the zoo just laughed at me! They like wolves, bears, cyclopes - big creatures with claws and teeth. They just don't understand how insects are the truly interesting class." +
                " <br> " +
                "Well, if they think insects are boring little pests, they're in for a surprise. I've found some insects that'll scare the pants off 'em!" +
                " <br> " +
                "It wasn't easy, of course. I got really thirsty trying to find their hive in the desert, and it was almost impossible to steal a sack of eggs without the Queen seeing me." +
                " <br> " +
                "After another parched journey through the desert, I brought the eggs here to my quiet little cavern." +
                " <br> " +
                "Of course, I couldn't try to raise kalphites in just any rocky cave. They expect sand. I'd spent weeks preparing the cave, dragging in sacks of sand to cover the rocky floor. My back still hurts." +
                " <br> " +
                "I thought I'd have to light lots of fires too, since this cave isn't as warm as their hive, but they seem to have natural resilience to the cold. They're tough little blighters - when the zoo sees my kalphites, they'll finally show some proper respect, both for me and for the insect class!" +
                " <br> " +
                "Actually, my kalphites are a bit tougher than I'd expected. As they grow larger, they're becoming quite aggressive." +
                " <br> " +
                "One of them even bit me today. It didn't hurt much at the time, but now I'm feeling a bit dizzy. I hope the kalphites aren't poisonous - my etymology[sic] books didn't say anything about them being poisonous." +
                " <br> " +
                "The dizziness is getting worse... I don't know... ... those zoo keepers will show me some proper respect..." +
                " <br> " +
                "rargh, I'm a lava monster..." +
                " <br> " +
                "I've lost my dolphin.";
    }

    @Override
    public String getTitle() {
        return "Entomologist's Diary";
    }

    static {
        ItemAction.registerInventory(Items.ENTOMOLOGISTS_DIARY, "read", ((p, item) -> Book.openBook(new EntomologistsDiary(p))));
        NPCAction.register(491, "search", ((p, obj) -> {
            if (p.getInventory().contains(Items.ENTOMOLOGISTS_DIARY)) {
                p.sendMessage("You don't find anything.");
                return;
            }
            p.sendMessage("You rummage around in the dead entomologist's bag.....");
            if (p.getInventory().getFreeSlots() < 1) {
                p.sendMessage("...and find a book, but your inventory is full.");
                return;
            }
            p.sendMessage("...and find a collection of notes.");
            p.getInventory().add(Items.ENTOMOLOGISTS_DIARY);
        }));
    }
}
