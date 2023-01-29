package io.ruin.model.skills.cooking;

import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/29/2023
 */
@Getter
@AllArgsConstructor
public enum CuttableFruit {
    ORANGE(Items.ORANGE, Arrays.asList(new Item(Items.ORANGE_SLICES), new Item(Items.ORANGE_CHUNKS))),
    LIME(Items.LIME, Arrays.asList(new Item(Items.LIME_SLICES), new Item(Items.LIME_CHUNKS))),
    BANANA(Items.BANANA, Collections.singletonList(new Item(Items.SLICED_BANANA))),
    LEMON(Items.LEMON, Arrays.asList(new Item(Items.LEMON_SLICES), new Item(Items.LEMON_CHUNKS))),
    PINEAPPLE(Items.PINEAPPLE, Arrays.asList(new Item(Items.PINEAPPLE_RING, 4), new Item(Items.PINEAPPLE_CHUNKS)));

    private final int itemId;
    private final List<Item> products;
}
