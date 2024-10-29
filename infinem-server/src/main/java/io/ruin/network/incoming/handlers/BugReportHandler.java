package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.services.discord.impl.BugReportEmbedMessage;
import io.ruin.utility.ClientPacketHolder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
@ClientPacketHolder(packets = {ClientPacket.BUG_REPORT})
public class BugReportHandler implements Incoming {
    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String first = in.readString();
        String second = in.readString();
        in.readByteAdd();
        BugReportEmbedMessage.sendDiscordMessage(player, first, second);
    }
}
