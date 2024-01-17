package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.discord.impl.BugReportEmbedMessage;
import io.ruin.utility.IdHolder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
@IdHolder(ids = {71})
public class BugReportHandler implements Incoming {
    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String first = in.readString();
        String second = in.readString();
        in.readByte();
        BugReportEmbedMessage.sendDiscordMessage(player, first, second);
    }
}
