package io.ruin.model.content.tasksystem.relics.impl.fragments;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/30/2024
 */
@Getter
@AllArgsConstructor
public class FragmentModRoll {
    private final FragmentModifier modifier;
    private final double value;

    @Override
    public String toString() {
        return modifier.name() + ":" + value;
    }
}
