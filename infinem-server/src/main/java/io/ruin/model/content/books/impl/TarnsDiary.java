package io.ruin.model.content.books.impl;

import io.ruin.model.content.books.Book;
import io.ruin.model.content.books.ChapteredBook;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/7/2024
 */
public class TarnsDiary extends ChapteredBook {

    TarnsDiary(final Player player) {
        super(player);
    }

    @Override
    public String[] getChapters() {
        return new String[] {
                "<col=000080>4th of Bennath",
                "<col=000080>18th of Raktuber",
                "<col=000080>29th of Raktuber",
                "<col=000080>8th of Pentember",
                "<col=000080>10th of Pentember",
                "<col=000080>11th of Pentember",
                "<col=000080>12th of Pentember",
                "<col=000080>16th of Pentember",
                "<col=000080>17th of Pentember",
                "<col=000080>18th of Pentember",
                "<col=000080>19th of Pentember",
                "<col=000080>20th of Pentember"
        };
    }

    @Override
    public String[] getContent() {
        return new String[]{
                "<col=000080> 4th of Bennath </col> <br> " +
                        "All my long hours of research have finally paid off! Today I discovered an ancient journal in the great library, which detailed a ceremony " +
                        "for giving life back to greater beasts after they have died! I shall finally be able to unravel the secrets of the Skeletal Wyverns! ",

                "<col=000080> 18th of Raktuber </col> <br> " +
                        "I have spent over a month studying the spell of Greater Animation now. My studies have led me to believe that although this spell is more powerful " +
                        "than any I have ever encountered, and will indeed reanimate the skeletons of long dead dragons and such creatures, it does not provide any way of " +
                        "controlling them. Whoever devised this spell must have been insane, because without somebody to control them, these undead monsters would have been " +
                        "free to destroy everything that they encountered! No wonder the spell was hidden away in the great library. There is, however, a reference to an amulet " +
                        "of such power that any undead creature would tremble before someone wielding it. I must return to the library and see if I can find any further reference " +
                        "to this magical amulet. Once I have it and can create and control undead dragons, even Lord Drakan and his minions shall tremble before my might! ",

                "<col=000080> 29th of Raktuber </col> <br> " +
                        "I have spent nearly 2 full weeks searching the great library for a reference to the writings that I need, with no success. Soon I shall be forced to start searching " +
                        "through the tombs and grimoires page by page. ",

                "<col=000080> 8th of Pentember </col> <br> " +
                        "I have found a reference to the crystal! This morning, while reading the histories of Morytania, I happened upon this extract: 'And so our mighty Ruler ordered the mines " +
                        "to be sealed forever and all those who slaved in the mines to be slain. Only by doing this could we seal away the threat of the Accursed Shard for all time!' I suspect this " +
                        "Accursed Shard to be the crystal that I seek! My next course of action must be to discover where these mines that are mentioned can be found. ",

                "<col=000080> 10th of Pentember </col> <br> " +
                        "Once again, the great library has provided the answer. I have found an ancient map that shows a series of mines in south-western Morytania, on the River Salve. These mines are " +
                        "not shown on modern maps. They must be the place I seek. Tomorrow I shall go and find them! ",

                "<col=000080> 11th of Pentember </col> <br> " +
                        "Today has been a hard day. We travelled south through desolate swamps, which were infested with ghasts. Many of my servants and soldiers fell to these creatures, but I have granted " +
                        "them eternal life as the undead, so that they may continue to serve me. ",

                "<col=000080> 12th of Pentember </col> <br> " +
                        "This morning we found out that all our food had spoiled over-night. We will need to hunt for food today, as we continue our trek southward, to the mines. I will be only too pleased " +
                        "to see the end of these accursed swamps. ",
                "<col=000080> 16th of Pentember </col> <br> " +
                        "At last we have reached the mines! All my human companions have perished, but I have animated their corpses and now have servants aplenty to do my bidding. I just wish they weren't " +
                        "all so stupid! This afternoon we shall search for the entrance to the mines. ",
                "<col=000080> 17th of Pentember </col> <br> " +
                        "It has been an eventful morning. The day started with us finding a madman wandering in the area, also searching for an entrance to the mine. I tricked him into telling me where the " +
                        "original entrance was, and from there my zombie minions soon found a way into the mines themselves. I can feel very powerful magic at work here, possibly even stronger than my own! " +
                        "In an attempt to find the source of this magic, I shall spend this afternoon preparing and casting spells of location and scrying. ",
                "<col=000080> 18th of Pentember </col> <br> " +
                        "I have seen the crystal that I seek, however it is guarded by a spirit of extreme power! I shall have my undead create a small dungeon, where I will be safe from attack and then I shall " +
                        "send them forth to slay this fell spirit. I'm sure even one as powerful as that will fall to the numbers of undead that I can summon. ",
                "<col=000080> 19th of Pentember </col> <br> " +
                        "Work began on the passage to my suite of rooms. My slaves work hard when the work is as simple as this, and we made excellent progress. The only thing of interest is that we found some " +
                        "strange stone blocks, of a design that I have not seen before. I may study them further when I have more time available. ",
                "<col=000080> 20th of Pentember </col> <br> " +
                        "Today we excavated the entrance to a building! I do not recognise the style of masonry used, but the creators must have been about the same height and girth as I am, judging by the size " +
                        "of doorways and stair- cases. The temple is fraught with traps, but will make a fine base for me to wait whilst my army of undead retrieve a piece of the Salve Crystal. I have several " +
                        "theories pertaining to the origin of this strange temple. If I am correct we are deep underground, directly below the village of Burgh de Rott. It may be that this temple was built as a " +
                        "fortification against an invasion from the desert nomads, who live to the west of here. Alternatively, perhaps it was built at the bequest of one of the lesser vampyre lords who ruled " +
                        "this area when Drakan came to power. However, I believe that it was probably built to guard an ancient treasure. My reasoning is thus; there doesn't seem to be any type of altar that we " +
                        "have found yet and the temple is littered with traps. Further research may reveal an answer. In the meantime I have enchanted this diary of mine with the spell needed to enhance the powers" +
                        " of the crystal. I await my minions' return. "
        };
    }

    @Override
    public String getTitle() {
        return "The Diary of Tarn Razorlor";
    }

    private static void enchantAmulet(Player player, Item diary, Item amulet, int newAmuletId) {
        amulet.setId(newAmuletId);
        // TODO anim, gfx
    }

    static {
        ItemAction.registerInventory(Items.TARNS_DIARY, "read", (p, item) -> Book.openBook(new TarnsDiary(p)));
        ItemItemAction.register(Items.TARNS_DIARY, Items.SALVE_AMULET, (p, diary, amulet) -> enchantAmulet(p, diary, amulet, Items.SALVE_AMULET_E));
        ItemItemAction.register(Items.TARNS_DIARY, Items.SALVE_AMULET_I, (p, diary, amulet) -> enchantAmulet(p, diary, amulet, Items.SALVE_AMULET_EI));
    }
}
