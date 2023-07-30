package io.ruin.model.content.scroll;

import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/30/2023
 */
public class ScrollLine {
    @Getter private final int lineNumber;
    @Getter private final String line;

    public ScrollLine(int lineNumber, String line) {
        this.lineNumber = lineNumber;
        this.line = line;
    }
}