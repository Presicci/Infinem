package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Punishment;
import io.ruin.services.discord.impl.AbuseReportEmbedMessage;
import io.ruin.utility.IdHolder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
@IdHolder(ids = {94})
public class AbuseReportHandler implements Incoming {
    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String playerName = in.readString();
        byte reportType = in.readByte();
        byte mute = in.readByte();
        Player reportedPlayer = World.getPlayer(playerName);
        if (mute == 1 && player.isStaff()) {
            Punishment.mute(player, reportedPlayer, System.currentTimeMillis() + TimeUtils.getHoursToMillis(4), false);
        }
        AbuseReportEmbedMessage.sendDiscordMessage(player, reportedPlayer, reportType);
    }
}
