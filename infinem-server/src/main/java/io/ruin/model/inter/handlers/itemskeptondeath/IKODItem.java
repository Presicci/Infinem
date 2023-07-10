package io.ruin.model.inter.handlers.itemskeptondeath;

import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/9/2023
 */
public class IKODItem {
    public final IKODKind kind;
    public final Item item;

    IKODItem(IKODKind kind, Item item) {
        this.kind = kind;
        this.item = item;
    }
}