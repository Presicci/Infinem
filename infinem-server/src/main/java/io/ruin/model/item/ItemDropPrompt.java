package io.ruin.model.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/26/2024
 */
@Getter
@AllArgsConstructor
public class ItemDropPrompt {
    private String message;
    private Consumer<Item> action;
}
