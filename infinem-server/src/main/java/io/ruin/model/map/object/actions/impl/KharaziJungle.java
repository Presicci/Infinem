package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.woodcutting.Hatchet;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/16/2023
 */
public class KharaziJungle {

    private static void chop(Player player, GameObject object) {
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
            player.privateSound(2277);
            return;
        }
        int targetX = player.getPosition().getX() == 2862 ? 2863 : player.getPosition().getX();
        int targetY = player.getPosition().getY() > 2938 ? 2934 : 2941;
        player.startEvent(e -> {
            player.animate(hatchet.animationId);
            e.delay(1);
            player.lock();
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.dialogue(new MessageDialogue("You hack your way through the dense jungle."));
            player.getMovement().teleport(targetX, targetY);
            player.resetAnimation();
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    static {
        ObjectAction.register(2889, "chop-down", KharaziJungle::chop);
        ObjectAction.register(2890, "chop-down", KharaziJungle::chop);
        ObjectAction.register(2892, "chop-down", KharaziJungle::chop);
        ObjectAction.register(2893, "chop-down", KharaziJungle::chop);
    }
}
