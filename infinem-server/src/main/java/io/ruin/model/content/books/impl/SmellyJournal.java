package io.ruin.model.content.books.impl;

import io.ruin.model.content.books.Book;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/27/2024
 */
public class SmellyJournal extends Book {

    public SmellyJournal(final Player player) {
        super(player);
    }

    @Override
    public String getString() {
        return "Raktuber 31st, 166 <br> " +
                "Dearest Lensa <br> " +
                "Brother Rakkar found this old empty diary on board, I'll fill it with entries of our travels for you to read upon my arrival!" +
                " <br> " +
                "Pentember 1st, 166 <br> " +
                "Dearest Lensa <br> " +
                "The storms have been rougher than the seers foretold, we've had to take shelter on Lunar Isle and the Moonclan are not impressed. They say of our voyage west will find nothing but a watery grave, their Kosmos be damned!" +
                " <br> " +
                "Pentember 23rd, 166 <br> " +
                "Dearest Lensa <br> " +
                "We've been sailing for weeks now my love and the crew has become weary, supplies run low and Jaldur has no explanation for the lack of fish in these strange waters. Land is on the horizon, hope is not lost." +
                " <br> " +
                "Fentuary 5th, 166 <br> " +
                "Dearest Lensa <br> " +
                "Great news! We've discovered a fishing hamlet in the cold tundra on the coast of this new continent. The locals are quiet... but friendly enough, they've allowed us hospitality providing we help out catching fish." +
                " <br> " +
                "Fentuary 12th, 166 <br> " +
                "Dearest Lensa <br> " +
                "The fish in the waters here are magnificent! We'll have to bring some back for all of you to try, along with the ale! I'm not sure how they're making it but it's strong and makes for great merriment! Jardor found a large snakestone in the recent catch, the locals seem weary of it and demand we take it with us when we leave." +
                " <br> " +
                "Septober 17th, 166 <br> " +
                "It's been awhile since my last entry, I apologize my dear, but things have taken a turn for the worst. We still remain at the fishing hamlet for brother Barrak and Jardor have a sickness, they mutter words to the snakestone, they claim it whispers back..." +
                " <br> " +
                "Septober 19th, 166 <br> " +
                "Jardor has turned to drinking the ocean, he says it'll bring him closer to the gods... for who he's referring to I do not know. The snakestone lies." +
                " <br> " +
                "Septober 21st, 166 <br> " +
                "The villagers are gone, something took them in the night, trails lead to an ominous crevasse... I must convince the others that we must flee now, before it's too late." +
                " <br> " +
                "Septober 23rd, 166 <br> " +
                "It spoke to me, I'm going to stay now." +
                " <br> " +
                "Septober 25th, 166 <br> " +
                "Rakkar tried to throw the snakestone back into the ocean, Jardor and Barrak made an example of him, he'll be troubling us no more." +
                " <br> " +
                "Ire of Phyrrys, ??, ??? <br> " +
                "Praise him." +
                "Praise him." +
                "Praise him." +
                " <br> " +
                "Tonight we ascend.";
    }

    @Override
    public String getTitle() {
        return "Entomologist's Diary";
    }

    static {
        ItemAction.registerInventory(Items.SMELLY_JOURNAL, "read", ((p, item) -> Book.openBook(new SmellyJournal(p))));
    }
}
