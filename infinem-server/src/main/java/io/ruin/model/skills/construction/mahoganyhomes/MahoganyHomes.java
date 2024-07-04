package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public class MahoganyHomes {

    protected static final String CLIENT_KEY = "MH_CONTRACT";
    protected static final String DIFFICULTY_KEY = "MH_DIFF";
    protected static final String COMPLETION_KEY = "MH_COMP";
    public static final String POINTS_KEY = "CARPENTER_POINTS";

    protected static boolean hasContract(Player player) {
        return player.hasAttribute(CLIENT_KEY) && player.hasAttribute(DIFFICULTY_KEY);
    }

    public static MahoganyClient getClient(Player player) {
        if (!player.hasAttribute(CLIENT_KEY)) return null;
        return MahoganyClient.valueOf(player.getAttribute(CLIENT_KEY));
    }

    protected static MahoganyDifficulty getDifficulty(Player player) {
        if (!player.hasAttribute(DIFFICULTY_KEY)) return null;
        return MahoganyDifficulty.valueOf(player.getAttribute(DIFFICULTY_KEY));
    }

    public static MahoganyClient getCompletedClient(Player player) {
        if (!player.hasAttribute(COMPLETION_KEY)) return null;
        return MahoganyClient.valueOf(player.getAttribute(COMPLETION_KEY));
    }

    public static int getPoints(Player player) {
        return player.getAttributeIntOrZero(POINTS_KEY);
    }

    protected static void assignContract(Player player, MahoganyDifficulty difficulty) {
        // Reset
        for (MahoganyHotspot hotspot : MahoganyHotspot.values()) {
            hotspot.getVarbit().set(player, 0);
        }
        MahoganyClient client = Random.get(MahoganyClient.values());
        int removedObjects = Random.get(difficulty.getMinRemovedObjects(), difficulty.getMaxRemovedObjects());
        List<MahoganyObject> possibleObjects = Arrays.asList(client.getObjects().clone());
        Collections.shuffle(possibleObjects);
        List<MahoganyObject> objects = possibleObjects.subList(removedObjects, possibleObjects.size());
        for (MahoganyObject object : objects) {
            object.getVarbit().set(player, object.isRepairable() ? 1 : 3);
        }
        player.putAttribute(CLIENT_KEY, client.name());
        player.putAttribute(DIFFICULTY_KEY, difficulty.name());
        player.dialogue(
                new NPCDialogue(7417, "Please could you " + client.getLocation() + " for us? You can get another job once you have furnished " + client.getPronoun() + " home.")
        );
        player.sendMessage("Assigned " + objects.size() + " objects");
        player.sendMessage(StringUtils.capitalizeFirst(client.getLocation()) + ".");
    }

    protected static void checkCompletion(Player player) {
        MahoganyClient client = getClient(player);
        assert client != null;
        for (MahoganyObject object : client.getObjects()) {
            Config varbit = object.getVarbit();
            int value = varbit.get(player);
            if (value == 1 || value == 3 || value == 4) return;
        }
        player.sendMessage(Color.GREEN.wrap(StringUtils.capitalizeFirst(client.toString().toLowerCase()) + " seems happy with your work. Talk to " + client.getPronoun() + " for your reward."));
        player.putAttribute(COMPLETION_KEY, client.name());
    }

    protected static void complete(Player player) {
        MahoganyDifficulty difficulty = getDifficulty(player);
        player.removeAttribute(CLIENT_KEY);
        player.removeAttribute(DIFFICULTY_KEY);
        player.removeAttribute(COMPLETION_KEY);
        player.getStats().addXp(StatType.Construction, difficulty == null ? 500 : difficulty.getExperienceReward(), true);
        int completed = PlayerCounter.MAHOGANY_HOMES_CONTRACTS.increment(player, 1);
        int points = player.incrementNumericAttribute(POINTS_KEY, difficulty == null ? 2 : difficulty.getPointReward());
        player.sendMessage("You have completed " + Color.RED.wrap(completed + "") + " contracts with a total of " + Color.RED.wrap(points + "") + " points.");
    }
}
