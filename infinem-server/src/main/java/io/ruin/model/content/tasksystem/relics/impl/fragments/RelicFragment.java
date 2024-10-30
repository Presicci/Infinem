package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
public class RelicFragment {

    public static FragmentItem generate() {
        FragmentItem fragment = new FragmentItem(26544, FragmentType.FISHING);
        fragment.rollMod();

        AttributeExtensions.printAttributes(fragment);
        return fragment;
    }

    static {
        ItemAction.registerInventory(32041, "identify", (player, item) -> generate());
    }
}
