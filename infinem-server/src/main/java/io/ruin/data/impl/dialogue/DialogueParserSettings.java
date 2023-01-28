package io.ruin.data.impl.dialogue;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/27/2023
 */
@Getter
public class DialogueParserSettings {
    private final DialogueLoaderSetting setting;
    @Setter private boolean consumerLine = false;
    private final int consumerValue;

    public DialogueParserSettings(DialogueLoaderSetting setting, int consumerValue) {
        this.setting = setting;
        this.consumerValue = consumerValue;
    }
}
