package io.ruin.model.skills.runecrafting;

import com.google.common.collect.Lists;
import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Item;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ReverendDread on 3/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class OuraniaAltar {

    private static final HashMap<Altars, Double> ARDOUGNE_DIARY_DOUBLE_CHANCES = new HashMap<Altars, Double>() {{
        put(Altars.AIR, .25);
        put(Altars.MIND, .25);
        put(Altars.WATER, .25);
        put(Altars.EARTH, .25);
        put(Altars.FIRE, .25);
        put(Altars.BODY, .25);
        put(Altars.COSMIC, .25);
        put(Altars.CHAOS, .25);
        put(Altars.ASTRAL, .25);
        put(Altars.NATURE, .225);
        put(Altars.LAW, .2);
        put(Altars.DEATH, .175);
        put(Altars.BLOOD, .15);
        put(Altars.SOUL, .1);
    }};

    //Need to take another look at a way to do this better.
    static {
        ObjectAction.register(29631, "Craft-rune", (player, obj) -> {
            RandomEvent.attemptTrigger(player);
            player.startEvent(event -> {
                List<Altars> altars = Arrays.stream(Altars.values()).filter(a -> a != Altars.WRATH).collect(Collectors.toList());
                int essenceCount = 0, fromPouches = 0;
                ArrayList<Item> essence = player.getInventory().collectItems(Essence.PURE.id);
                essenceCount += fromPouches = Altars.essenceFromPouches(player);
                if (essence != null) {
                    essenceCount += essence.size();
                }
                if (fromPouches > 0) {
                    Altars.clearPouches(player);
                }
                if (essenceCount > 0) {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    player.animate(791);
                    player.graphics(186, 100, 0);
                    event.delay(4);
                    IntStream.range(0, essenceCount).forEach(i -> {
                        Altars altar = Utils.randomTypeOfList(altars);
                        int amount = 1;
                        if (AreaReward.OURANIA_RUNES.hasReward(player) && Random.get() < ARDOUGNE_DIARY_DOUBLE_CHANCES.get(altar)) {
                            amount += 1;
                        }
                        player.getInventory().add(altar.runeID, amount);
                        player.getInventory().remove(Essence.PURE.id, 1);
                        player.getStats().addXp(StatType.Runecrafting, altar.experience, true);
                        if (Random.rollDie(1487213 - (player.getStats().get(StatType.Runecrafting).currentLevel * 25)))
                            Pet.RIFT_GUARDIAN_AIR.unlock(player);
                       PlayerCounter.CRAFTED_OURANIA.increment(player, amount);
                    });
                    player.unlock();
                } else {
                    player.sendMessage("You don't have any essence to craft.");
                }
            });
        });
        ObjectAction.register(29636, "Climb", ((player, obj) -> {
            player.startEvent((e) -> {
                e.path(player, new Position(3015, 5629, 0));
                player.getMovement().teleport(2453, 3231, 0);
            });
        }));
        ObjectAction.register(29635, "Climb", ((player, obj) -> {
            player.startEvent((e) -> {
                e.path(player, new Position(2453, 3231, 0));
                player.getMovement().teleport(3015, 5629, 0);
            });
        }));
    }

}
