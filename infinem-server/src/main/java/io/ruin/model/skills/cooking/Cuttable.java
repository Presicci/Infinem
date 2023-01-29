package io.ruin.model.skills.cooking;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2023
 */
@Getter
@AllArgsConstructor
public enum Cuttable {
    PINEAPPLE(2114, Arrays.asList(2116, 2118));

    private final int itemId;
    private final List<Integer> products;
}
