package io.ruin.services.discord.impl;

import io.ruin.content.activities.event.impl.eventboss.EventBossType;
import io.ruin.services.discord.DiscordConnection;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/13/2020
 */
public class EventBossEmbedMessage {
    public static void sendDiscordMessage(EventBossType boss, String location) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("An Event Boss Has Spawned!");
        eb.setDescription(location);
        eb.setImage(boss.getEmbedUrl());
        eb.setColor(new Color(0xD80066A8, true));
        DiscordConnection.post(842960546298855486L, eb.build());
        System.out.println("Embed sent!");
    }
}
