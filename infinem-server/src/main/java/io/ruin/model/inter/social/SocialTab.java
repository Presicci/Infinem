package io.ruin.model.inter.social;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/20/2024
 */
@AllArgsConstructor
public enum SocialTab {
    FRIEND_CHAT(Interface.CLAN_CHAT),
    CLAN(701),
    OTHER_CLANS(702),
    GROUPING(76);

    private final int interfaceId;

    private void setTab(Player player) {
        TAB.set(player, ordinal());
        player.getPacketSender().sendInterface(interfaceId, Interface.SOCIAL_TAB, 7, 1);
    }

    private static final Config TAB = Config.varpbit(930, false);

    static {
        InterfaceHandler.register(Interface.SOCIAL_TAB, h -> {
            h.actions[3] = (SimpleAction) FRIEND_CHAT::setTab;
            h.actions[4] = (SimpleAction) CLAN::setTab;
            h.actions[5] = (SimpleAction) OTHER_CLANS::setTab;
            h.actions[6] = (SimpleAction) GROUPING::setTab;
        });
    }
}
