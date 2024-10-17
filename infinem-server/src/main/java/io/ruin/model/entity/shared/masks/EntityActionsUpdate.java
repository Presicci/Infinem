package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/8/2024
 */
public class EntityActionsUpdate extends UpdateMask {

    private String[] actions = new String[]{
            "",
            "",
            ""
    };

    private boolean needsUpdate = false;

    public void set(String... actions) {
        for (int i = 0; i < (actions.length > 3 ? 3 : actions.length); i++) {
            this.actions[i] = actions[i];
        }

        needsUpdate = true;
    }

    @Override
    public void reset() {
        actions = new String[]{
                "",
                "",
                ""
        };

        needsUpdate = false;
    }

    @Override
    public boolean hasUpdate(boolean justAdded) {
        return needsUpdate;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        for (String s : actions) {
            out.addString(s);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return 32768;
    }
}
