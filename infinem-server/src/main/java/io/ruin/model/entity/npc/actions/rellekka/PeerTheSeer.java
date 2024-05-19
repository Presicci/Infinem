package io.ruin.model.entity.npc.actions.rellekka;

import io.ruin.api.utils.Random;
import io.ruin.model.content.tasksystem.areas.AreaTaskTier;
import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;

import java.util.HashMap;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/19/2024
 */
public class PeerTheSeer {

    private static final String[] ENEMIES = { "fire giant", "ghost", "goblin", "green dragon", "hobgoblin", "lesser demon", "moss giant", "ogre", "skeleton", "zombie" };
    private static final String[] WEAPONS = { "battleaxe", "crossbow", "dagger", "javelin", "long sword", "mace", "scimitar", "short sword", "spear", "warhammer" };
    private static final String[] LOCATIONS = { "Ardougne", "Burthorpe", "Canifis", "Falador", "Karamja", "Varrock", "The Wilderness", "Yanille" };
    private static final String[] SKILLS = { "Agility", "Cooking", "Crafting", "Fishing", "Fletching", "Herblore", "Mining", "Runecrafting", "Smithing", "Thieving" };
    private static final String[] COLORS = { "black", "blue", "brown", "cyan", "green", "pink", "purple", "red", "white", "yellow" };
    private static final String[] NUMBERS = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" };

    private static final HashMap<String, String[]> DIALOGUES = new HashMap<String, String[]>() {{
        put("The enemy called * is your lucky totem this day.", ENEMIES);
        put("The stars tell me that you should use a * in combat today.", WEAPONS);
        put("The place called * will be worth your time to visit.", LOCATIONS);
        put("You would be wise to train the skill * today.", SKILLS);
        put("The colour * will bring you luck this day.", COLORS);
        put("You will find luck today with the number *.", NUMBERS);
    }};

    public static void tellFortune(Player player, NPC npc) {
        int index = Random.get(DIALOGUES.keySet().size() - 1);
        String dialogue = DIALOGUES.keySet().toArray(new String[0])[index];
        String[] options = DIALOGUES.get(dialogue);
        String randomOption = Random.get(options);
        player.dialogue(new NPCDialogue(npc, dialogue.replace("*", randomOption)));
    }

    public static void deposit(Player player) {
        if (!TaskArea.FREMENNIK.checkTierUnlock(player, AreaTaskTier.EASY, "deposit items with Peer the Seer.")) return;
        player.getBank().openDepositBox();
    }

    static {
        NPCAction.register(3895, "deposit-items", (player, npc) -> deposit(player));
    }
}
