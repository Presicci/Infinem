package io.ruin.model.content.bestiary;

import io.ruin.model.content.bestiary.perks.DropPerk;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryEntry {

    private static final NavigableSet<BestiaryPerk> dropPerks = new TreeSet<>(Arrays.asList(
            new DropPerk(25, 0.1),
            new DropPerk(75, 0.2)
    ));

    protected static DropPerk getDropPerk(int killCount) {
        BestiaryPerk dropPerk = dropPerks.lower(new BestiaryPerk(killCount));
        return !(dropPerk instanceof DropPerk) ? new DropPerk(0, 0) : (DropPerk) dropPerk;
    }
}
