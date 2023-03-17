package io.ruin.model.content.bestiary;

import io.ruin.cache.NPCDef;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/17/2023
 */
public class BestiaryDef {

    private static final String[] CATEGORIES = {
            "goblin",
    };

    private static final String[] TRIM = {
            "superior"
    };

    static {
        NPCDef.forEach(e -> {
            String name = e.name.toLowerCase();
            for (String trim : TRIM) {
                name = name.replace(trim, "");
            }
            for (String category : CATEGORIES) {
                if (name.contains(category)) {
                    name = category;
                    break;
                }
            }
            e.bestiaryEntry = name;
        });
    }
}
