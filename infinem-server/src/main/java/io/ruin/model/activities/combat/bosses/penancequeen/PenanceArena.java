package io.ruin.model.activities.combat.bosses.penancequeen;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.process.tickevent.TickEvent;
import io.ruin.process.tickevent.TickEventType;
import io.ruin.utility.TickDelay;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/27/2024
 */
public class PenanceArena {

    private static void poisonEgg(Player player) {
        Item egg = player.getInventory().findItem(Items.YELLOW_EGG);
        if (egg == null) {
            player.sendMessage("You don't have any yellow eggs to poison.");
            return;
        }
        player.animate(832);
        player.getStats().addXp(StatType.Herblore, 5, true);
        egg.setId(Items.POISONED_EGG);
    }

    private static void spikeEgg(Player player) {
        Item egg = player.getInventory().findItem(Items.POISONED_EGG);
        if (egg == null) {
            player.sendMessage("You don't have any poisoned eggs to add spikes to.");
            return;
        }
        player.animate(832);
        player.getStats().addXp(StatType.Fletching, 10, true);
        egg.setId(Items.SPIKEDPOIS_EGG);
    }

    private static void heatEgg(Player player) {
        Item egg = player.getInventory().findItem(Items.SPIKEDPOIS_EGG);
        if (egg == null) {
            player.sendMessage("You don't have any spiked, poisoned eggs to heat up.");
            return;
        }
        player.animate(832);
        player.getStats().addXp(StatType.Cooking, 25, true);
        egg.setId(Items.OMEGA_EGG);
    }

    private static final TickDelay CANNON_COOLDOWN = new TickDelay();
    private static final Projectile PROJECTILE = new Projectile(980, 0, 31, 0, 56, 10, 16, 64);
    private static final Position CANNON_POSITION = new Position(1877, 5410, 0);

    private static void shootEgg(Player player, GameObject object) {
        Item egg = player.getInventory().findItem(Items.OMEGA_EGG);
        if (egg == null) {
            player.sendMessage("You don't have any omega eggs to shoot.");
            return;
        }
        if (CANNON_COOLDOWN.isDelayed()) {
            player.sendMessage("The cannon is cooling down.");
            return;
        }
        Optional<NPC> queenOptional = player.localNpcs().stream().filter(npc -> npc.getId() == 5775).findFirst();
        if (!queenOptional.isPresent()) {
            player.sendMessage("There is no queen to attack.");
            return;
        }
        NPC queen = queenOptional.get();
        player.animate(832);
        egg.remove();
        // Send attack
        int delay = PROJECTILE.send(CANNON_POSITION, queen);
        Hit hit = new Hit(player, AttackStyle.CANNON).randDamage(100).ignoreDefence().clientDelay(delay);
        queen.hit(hit);
        CANNON_COOLDOWN.delay(8);
    }

    private static void healPool(Player player) {
        if (player.isTickEventActive(TickEventType.PENANCE_QUEEN_POOL)) {
            player.sendFilteredMessage("You cannot use this pool yet!");
            return;
        }
        player.animate(645);
        player.addTickEvent(new TickEvent(TickEventType.PENANCE_QUEEN_POOL, 1000));
        player.sendMessage("You drink from the holy pool.");
        player.getStats().get(StatType.Prayer).restore();
        player.getStats().get(StatType.Hitpoints).restore();
    }

    static {
        // Entrance
        ObjectAction.register(20226, 2534, 3572, 0, "climb-down", (player, obj) -> {
            player.animate(827);
            Traveling.fadeTravel(player, new Position(1877, 5393, 0));
        });
        // Exit
        ObjectAction.register(20194, "climb-up", (player, obj) -> {
            player.animate(828);
            Traveling.fadeTravel(player, new Position(2535, 3572, 0));
            for (int itemId : Arrays.asList(10531, 10532, 10533, 10534, 10535, 10536, 10537)) {
                player.getInventory().remove(itemId, 28);
            }
        });
        // Horn
        ObjectAction.register(20247, "call", (player, obj) -> player.dialogue(
                new MessageDialogue("You blow into the horn..."),
                new MessageDialogue("...No one seems to care.")
        ));
        ObjectAction.register(20235, "dunk", (player, obj) -> poisonEgg(player));
        ObjectAction.register(20233, "dunk", (player, obj) -> poisonEgg(player));
        ObjectAction.register(20250, "get-spikes", (player, obj) -> spikeEgg(player));
        ObjectAction.register(20232, "dunk", (player, obj) -> heatEgg(player));
        ObjectAction.register(20234, "dunk", (player, obj) -> heatEgg(player));
        ObjectAction.register(20133, "shoot", PenanceArena::shootEgg);
        ObjectAction.register(20267, "load", PenanceArena::shootEgg);
        ObjectAction.register(20267, "look-in", (player, obj) -> player.sendMessage("I should load some omega eggs in here."));
        ObjectAction.register(20150, "drink-from", (player, obj) -> healPool(player));
        ObjectAction.register(20150, "take-from", (player, obj) -> healPool(player));
        NPCAction.register(1657, "tutorial", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Kill monsters in basement. That's it."), new PlayerDialogue("Okay.")));
        NPCAction.register(1656, "get-rewards", (player, npc) -> player.dialogue(new NPCDialogue(npc, "REWARDS!?!? NO! GET KILLING!"), new PlayerDialogue("...")));
    }
}
