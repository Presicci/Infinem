package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/31/2023
 */
public abstract class DropViewerResult {
    protected int chance;
    public abstract String get();
    public abstract Item getItem();
}