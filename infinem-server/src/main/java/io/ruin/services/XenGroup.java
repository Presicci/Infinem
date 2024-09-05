package io.ruin.services;

import io.ruin.model.entity.npc.actions.edgeville.DonationManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;

public class XenGroup {

    public static void update(Player player) {
        PlayerGroup donationGroup = DonationManager.getGroup(player);
        if(donationGroup != null)
            donationGroup.sync(player);
        //todo ironman checks
    }

}
