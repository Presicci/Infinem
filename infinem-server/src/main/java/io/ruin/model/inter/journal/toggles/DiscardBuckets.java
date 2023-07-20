package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DiscardBuckets extends JournalEntry {

    @Override
    public void send(Player player) {
        if(player.hasAttribute(AttributeKey.DISCARD_BUCKETS))
            send(player, "Discard Buckets", "Enabled", Color.GREEN);
        else
            send(player, "Discard Buckets", "Disabled", Color.RED);
    }

    @Override
    public void select(Player player) {
        if (player.hasAttribute(AttributeKey.DISCARD_BUCKETS))
            player.removeAttribute(AttributeKey.DISCARD_BUCKETS);
        else
            player.putAttribute(AttributeKey.DISCARD_BUCKETS, 1);
        send(player);
    }

}
