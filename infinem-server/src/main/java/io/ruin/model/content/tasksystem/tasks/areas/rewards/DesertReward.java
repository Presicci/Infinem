package io.ruin.model.content.tasksystem.tasks.areas.rewards;

import io.ruin.cache.Color;
import io.ruin.model.content.scroll.DiaryScroll;
import io.ruin.model.content.tasksystem.tasks.areas.AreaTaskTier;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/31/2023
 */
public enum DesertReward {
    DESERT_AMULET_1(AreaTaskTier.EASY, "Unlocks the Desert Amulet 1"),
    PHAROH_SCEPTRE_CHARGES_10(AreaTaskTier.EASY, "Pharaoh's sceptre can hold up to 10 charges"),
    NOTED_GOAT_HORN(AreaTaskTier.EASY, "Goats will always drop noted desert goat horn"),
    //Simon Templeton will now buy your noted artefacts too
    DESERT_AMULET_2(AreaTaskTier.MEDIUM, "Unlocks the Desert Amulet 2",
            "One teleport to Nardah per day"),
    PHAROH_SCEPTRE_CHARGES_25(AreaTaskTier.MEDIUM, "Pharaoh's sceptre can hold up to 25 charges"),
    DESERT_AMULET_3(AreaTaskTier.HARD, "Unlocks the Desert Amulet 3"),
    PHAROH_SCEPTRE_CHARGES_50(AreaTaskTier.HARD, "Pharaoh's sceptre can hold up to 50 charges"),
    AL_KHARID_PALACE_SHORTCUT(AreaTaskTier.HARD, "Access to the big window shortcut in Al Kharid Palace"),
    ZAHUR_UNFINISHED_POTIONS(AreaTaskTier.HARD, "Zahur will create unfinished potions for 200 coins per potion"),
    ZAHUR_CLEAN_HERB(AreaTaskTier.HARD, "Zahur will now clean noted grimy herbs for 200 coins each"),
    //All carpet rides are free.
    //Ropes placed at both the Kalphite Lair entrance and the Kalphite Queen tunnel entrance become permanent.
    DESERT_AMULET_4(AreaTaskTier.ELITE, "Unlocks the Desert Amulet 4",
            "Unlimited teleports to Nardah and the Kalphite Cave",
            "The Nardah teleport now takes players directly inside the Elidinis shrine",
            "100% protection against desert heat when worn"),
    PHAROH_SCEPTRE_CHARGES_100(AreaTaskTier.ELITE, "Pharaoh's sceptre can hold up to 100 charges"),
    //Free pass-through of the Shantay Pass and the Ruins of Unkah
    //Access to a crevice shortcut, requiring 86 Agility, in the Kalphite Lair from the entrance to the antechamber before the Kalphite Queen boss room.
            ;

    private final AreaTaskTier tier;
    private final String description;
    private final String[] additionalDescription;

    DesertReward(AreaTaskTier tier, String description, String... additionalDescription) {
        this.tier = tier;
        this.description = description;
        this.additionalDescription = additionalDescription;
    }

    public static void openRewards(Player player) {
        List<String> lines = new ArrayList<>();
        AreaTaskTier tier = null;
        for (DesertReward task : values()) {
            if (tier != task.tier) {
                tier = task.tier;
                lines.add(Color.YELLOW.wrap(tier.toString()));
            }
            lines.add(Color.DARK_RED.wrap(task.description));
            if (task.additionalDescription.length > 0) {
                lines.addAll(Arrays.asList(task.additionalDescription));
            }
        }
        DiaryScroll scroll = new DiaryScroll(Color.DARK_RED.wrap("Area Task Unlocks - Desert"), lines);
        scroll.open(player);
    }
}
