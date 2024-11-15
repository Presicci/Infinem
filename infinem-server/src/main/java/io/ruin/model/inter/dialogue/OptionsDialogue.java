package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsDialogue extends Dialogue {

    private final Object[] params;

    private final Option[] options;

    private boolean resend;
    private int page;

    public OptionsDialogue(List<Option> options) {
        this(options.toArray(new Option[options.size()]));
    }

    public OptionsDialogue(Option... options) {
        this("Select an Option", options);
    }

    public OptionsDialogue(String title, List<Option> options) {
        this(title, options.toArray(new Option[options.size()]));
    }

    public OptionsDialogue(String title, Option... options) {
        params = new Object[]{title, ""};
        for(int i = 0; i < options.length; i++) {
            params[1] += options[i].name;
            if (i == 3 && options.length > 5) {
                params[1] += "|More...";
            }
            if(i != options.length - 1)
                params[1] += "|";
        }
        if (options.length > 5) {
            params[1] += "|Back...";
        }
        this.options = options;
    }

    @Override
    public void open(Player player) {
        player.optionsDialogue = this;
        player.openInterface(InterfaceType.CHATBOX, Interface.OPTIONS_DIALOGUE);
        player.getPacketSender().setAlignment(Interface.OPTIONS_DIALOGUE, 1, 0, -10);
        if (page == 0) {
            player.getPacketSender().sendClientScript(58, "ss", params);
        } else {
            List<String> paramElements = Arrays.asList(((String) params[1]).split("\\|"));
            String optionString = String.join("|", paramElements.subList(page * 5, paramElements.size()));
            Object[] pageParams = new Object[]{params[0], optionString};
            player.getPacketSender().sendClientScript(58, "ss", pageParams);
        }
        new Unlock(Interface.OPTIONS_DIALOGUE, 1).children(1, 5).unlockSingle(player);
    }

    public void resend() {
        resend = true;
    }

    private void handle(Player player, int slot) {
        int index = slot - 1 + (4 * page);
        if (index == options.length && page > 0) {
            page = 0;
            open(player);
            return;
        }
        if(index < 0 || index >= options.length) {
            player.closeDialogue();
            return;
        }
        if (index % 5 == 4 && options.length > (page + 1) * 5) {
            ++page;
            open(player);
            return;
        }
        options[index].select(player);
        if(resend) {
            resend = false;
            open(player);
        } else if(player.lastDialogue == this) {
            /* dialogue was not continued */
            player.closeDialogue();
        }
    }

    static {
        InterfaceHandler.register(Interface.OPTIONS_DIALOGUE, h -> {
            h.actions[1] = (SlotAction) (p, slot) -> {
                if (p.optionsDialogue != null) {
                    p.optionsDialogue.handle(p, slot);
                }
            };
        });
    }

}