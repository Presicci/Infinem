package io.ruin.model.content.books.impl;

import io.ruin.model.content.books.Book;
import io.ruin.model.content.books.ChapteredBook;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/3/2024
 */
public class GianneCookBook extends ChapteredBook {

    GianneCookBook(final Player player) {
        super(player);
    }

    @Override protected boolean skipFirstPage() {
        return true;
    }

    @Override
    public String[] getChapters() {
        return new String[] {
                "<col=000080>Chocolate Bomb",
                "<col=000080>Tangled Toad's Legs",
                "<col=000080>Worm Hole",
                "<col=000080>Veg Ball",
                "<col=000080>Worm Crunchies",
                "<col=000080>Choc Chip Crunchies",
                "<col=000080>Spicy Crunchies",
                "<col=000080>Toad Crunchies",
                "<col=000080>Worm Batta",
                "<col=000080>Toad Batta",
                "<col=000080>Cheese & Tomato Batta",
                "<col=000080>Fruit Battas",
                "<col=000080>Vegetable Batta"
        };
    }

    @Override
    public String[] getContent() {
        return new String[]{
                "<col=000080> Chocolate Bomb </col> <br> " +
                        "<br> Knead a ball of Gianne dough into a gnomebowl mould. Bake this briefly. Decadently add four bars of chocolate to the bowl and top with one sprig " +
                        "of equa leaves. Bake the bowl in the oven to melt the chocolate. Then mix in two big dollops of cream and finally sprinkle chocolate dust all over." +
                        " <br> <br> " +
                        "Chocolate is a relatively recent cooking ingredients for gnomes, having been imported from human lands.",

                "<col=000080> Tangled Toad's Legs </col> <br> " +
                        "<br> Shape a portion of fresh Gianne dough into a gnomebowl mould. Bake this until it is slightly springy. Add to the bowl... 4 pairs of toad's legs 2 " +
                        "portions of cheese 2 sprigs of equa leaves 2 dashes of gnome spice 1 bunch of dwellberries Bake the dish in the oven once more prior to serving." +
                        " <br> <br> " +
                        "Tangled Toads Legs was a special dish created by gnome chef Deelie to celebrate the first Healthorg the Great Day.",

                "<col=000080> Worm Hole </col> <br> " +
                        "<br> Starting with a gnomebowl mould, shape a portion of fresh Gianne dough into a rough bowl. Bake this until it is firm to the touch. Add to the bowl " +
                        "four king worms, two onions and a dash of gnome spices. Bake the bowl in the oven once more. To finish the dish simply add a topping of equa leaves." +
                        " <br> <br> " +
                        "Worms are specially favoured by gnomes as they purportedly aid virility.",

                "<col=000080> Veg Ball </col> <br> " +
                        "<br> As with all gnomebowl dishes, throw a ball of fresh Gianne dough into a mould. Bake this as usual. Bake this briefly. Add to the bowl two onions, " +
                        "two potatoes and a dash of gnome spices. Bake the bowl in the oven once more. To finish simply top with equa leaves." +
                        " <br> <br> " +
                        "Vegetable dishes are seen as luxurious food, since for most of gnome history growing vegetables was harder than finding toads and worms.",

                "<col=000080> Worm Crunchies </col> <br> " +
                        "<br> Using a crunchy tray, form a portion of Gianne dough into small evenly sized balls. Heat these briefly in an oven. Mix into the dough balls two king worms, " +
                        "one sprig of equa leaves and a shake of gnome spices. Bake the crunchies for a short time in the oven. Sprinkle generously with gnome spices to finish." +
                        " <br> <br> " +
                        "Crunchies were invented accidentally by Pukkamay, who was Deelie's assistant before his sacking. He started a successful crunchy making business before dying " +
                        "in a bizarre Terrorbird accident.",

                "<col=000080> Choc Chip Crunchies </col> <br> " +
                        "<br> Fill up a crunchy tray with balls of Gianne dough. Heat these briefly in a warm oven. Break up two bars of chocolate and mix these with the balls of dough. Next " +
                        "add a little gnome spice to each ball. Bake the crunchies for a short time in the oven. Top the crunchies with a sprinkling of chocolate dust to finish." +
                        " <br> <br> " +
                        "(A large chocolate stain covers the rest of the page.)",
                "<col=000080> Spicy Crunchies </col> <br> " +
                        "<br> Using a crunchy tray, form a portion of Gianne dough into small evenly sized balls. Heat these briefly in a warm oven. Add a generous shake of spice and two sprigs " +
                        "of equa leaves to the dough balls. Bake the crunchies for a short time in the oven. Sprinkle a load more gnome spice over the cookies to finish." +
                        " <br> <br> " +
                        "The special mix of herbs and spices in gnome spice is a closely guarded secret. It is rumoured to contain (the rest is scribbled out)",
                "<col=000080> Toad Crunchies </col> <br> " +
                        "<br> Fill a crunchy tray with Gianne dough as normal. Heat these briefly in a warm oven. Mix into the dough balls two pairs of toad's legs and a shake of gnome spices. " +
                        "Bake the crunchies for a short time in the oven. Finish the crunchies with a sprinkling of equa leaves." +
                        " <br> <br> " +
                        "When Pukkamay first made toad crunchies, everyone thought he was mad. 'Chewy toads, in crunchies? It'll never work' they said - how wrong they were...",
                "<col=000080> Worm Batta </col> <br> " +
                        "<br> First take some fresh Gianne dough and place it in a batta tin. Bake the dough until it is lightly browned. Take one king worm, some gnome spice and a little cheese. " +
                        "Add these to the batta before briefly baking it in the oven once more. Finish the batta with a topping of equa leaves." +
                        " <br> <br> " +
                        "Battas are usually cooked by gnome mothers during the cold winter months.",
                "<col=000080> Toad Batta </col> <br> " +
                        "<br> Mould some Gianne dough into a batta tin. Bake the tin until it is almost cooked. Next add some prime toad's legs, a sprig of equa leaves and some spice along with " +
                        "some cheese to the batta. Bake the batta in the oven once more and serve hot." +
                        " <br> <br> " +
                        "Toad battas are sometimes called Toad in the Hole. Apparently there is a similar human dish that uses sausages. How odd.",
                "<col=000080> Cheese & Tomato Batta </col> <br> " +
                        "Place some Gianne dough in a batta tin. Bake it as normal. Top the plain batta with equal quantities of cheese and tomato. Place the batta in the oven once more until " +
                        "all the cheese has melted. Finish the dish with a sprinkling of equa leaves." +
                        " <br> <br> " +
                        "The combination of cheese and tomato was discovered by the explorer Wingstone while he was visiting the human lands. Apparently it's used a lot in a strange " +
                        "flat human dish called a pizza.",
                "<col=000080> Fruit Batta </col> <br> " +
                        "<br> Prepare Gianne dough in a batta tin as normal. Bake the dough for a short while. Top the batta with chunks of pineapple, orange and lime. Lay four sprigs of " +
                        "equa leaves on top of the batta before baking it in the oven once more. Finish the batta with a sprinkling of gnome spices." +
                        " <br> <br> " +
                        "Battas are normally savoury dish, and the fruit batta is definitely an acquired taste.",
                "<col=000080> Vegetable Batta </col> <br> " +
                        "<br> Place some Gianne dough in a batta tin and bake as normal. Add to the plain batta two tomatoes, one onion, one cabbage and some dwellberries. Top the batta " +
                        "with cheese and briefly place it in the oven once more. Finish the dish with a sprinkling of equa leaves." +
                        " <br> <br> " +
                        "There's no better batta than a vegetable batta."
        };
    }

    @Override
    public String getTitle() {
        return "Gianne's Cook Book";
    }

    static {
        ItemAction.registerInventory(2167, "read", ((p, item) -> Book.openBook(new GianneCookBook(p))));
    }
}
