package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.Items;
import io.ruin.model.shop.ShopManager;
import io.ruin.services.Hiscores;
import io.ruin.utility.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Adam {

    private final static int ADAM = 311;

    private static String getGenderString(Player player, boolean plural) {
        String gender = player.getAppearance().isMale() ? "Man" : "Woman";
        String genderPlural = player.getAppearance().isMale() ? "Men" : "Women";
        return plural ? genderPlural : gender;
    }

    private static void options(Player player, NPC npc) {
        String gender = getGenderString(player, false);
        String genderPlural = getGenderString(player, true);
        player.dialogue(
                new OptionsDialogue(
                        new Option("Tell me about Iron " + genderPlural, () -> aboutIron(player, npc)),
                        new Option("Id like to review my Iron " + gender + " mode.", () -> review(player, npc)),
                        new Option("Have you any armour for me, please?", () -> giveArmour(player, npc)),
                        new Option("I'm fine, thanks.", new PlayerDialogue("I'm fine, thanks."))
                )
        );
    }

    private static void aboutIron(Player player, NPC npc) {
        String gender = getGenderString(player, false);
        String genderPlural = getGenderString(player, true);
            player.dialogue(
                    new PlayerDialogue("Tell me about Iron " + genderPlural + "."),
                    new NPCDialogue(npc, "When you play as an Iron " + gender + ", you do everything for yourself. You don't trade with other players, or take their items, or accept their help."),
                    new NPCDialogue(npc, "As an Iron " + gender + ", you choose to have these restrictions imposed on you, so everyone knows you're doing it properly."),
                    new NPCDialogue(npc, "If you think you have what it takes, you can choose to become a Hardcore Iron " + gender + "."),
                    new NPCDialogue(npc, "In addition to the standard restrictions, Hardcore Iron [Men/Women] only have one life. In the event of a dangerous death, your Hardcore Iron " + genderPlural + " status will be downgraded to that of a standard Iron " + gender + ", and your"),
                    new NPCDialogue(npc, "stats will be frozen on the Hardcore Iron " + gender + " hiscores."),
                    new NPCDialogue(npc, "For the ultimate challenge, you can choose to become an Ultimate Iron " + gender + "."),
                    new NPCDialogue(npc, "In addition to the standard restrictions, Ultimate Iron " + genderPlural + " are blocked from using the bank, and they drop all their items when they die."),
                    new NPCDialogue(npc, "I'll let you switch your Iron " + gender + " restrictions downwards only, so Hardcore/Ultimate to standard Iron " + gender + ", then standard to normal player."),
                    new ActionDialogue(() -> options(player, npc))
            );
    }

    private static void review(Player player, NPC npc) {
        if (player.getGameMode().isIronMan()) {
            List<Option> optionList = new ArrayList<>();
            optionList.add(new Option("Remove Ironman Restrictions", () -> actionConfirmation(player, npc, "remove your Ironman restrictions", Adam::removeIronmanMode)));
            if (player.getGameMode().isUltimateIronman() || player.getGameMode().isHardcoreIronman()) {
                optionList.add(new Option("Downgrade to Standard Ironman", () -> actionConfirmation(player, npc, "downgrade your Ironman restrictions", Adam::downgrade)));
                optionList.add(new Option("Make Ironman Restrictions Permanent", () -> actionConfirmation(player, npc, "make your Ironman status permanent", Adam::makePermanent)));
                optionList.add(new Option("Nothing"));
                player.dialogue(
                        new NPCDialogue(npc, "What would you like to do?"),
                        new OptionsDialogue(
                                optionList
                        )
                );
            } else {
                optionList.add(new Option("Make Ironman Restrictions Permanent", () -> actionConfirmation(player, npc, "make your Ironman status permanent", Adam::makePermanent)));
                optionList.add(new Option("Nothing"));
                player.dialogue(
                        new NPCDialogue(npc, "What would you like to do?"),
                        new OptionsDialogue(
                                optionList
                        )
                );
            }
        } else {
            player.dialogue(new NPCDialogue(npc, "You're not an ironman, get outta here!"));
        }
    }

    private static void actionConfirmation(Player player, NPC npc, String actionString, Consumer<Player> action) {
        player.dialogue(
                new NPCDialogue(npc, "Are you sure you would like to " + actionString + "?"),
                new NPCDialogue(npc, Color.RED.wrap("This process is irreversible.")),
                new OptionsDialogue(StringUtils.capitalizeFirst(actionString) + "?",
                        new Option("Yes, " + actionString, () -> action.accept(player)),
                        new Option("No, please do not")
                )
        );
    }

    private static void downgrade(Player player) {
        if (player.getGameMode() == GameMode.STANDARD) return;
        if (Config.IRONMAN_MODE_REMOVAL_REQUIREMENT.get(player) == 1) {
            if (player.getBankPin().requiresVerification(Adam::downgrade))
                return;
        } else {
            player.dialogue(new MessageDialogue("You have previously made your Ironman restrictions permanent, you cannot change your status."));
            return;
        }
        if (player.getGameMode().isHardcoreIronman()) {
            for (int index = 0; index < GameMode.HCIM_ARMOR.length; index++) {
                int finalIndex = index;
                player.forEachItemOwned(GameMode.HCIM_ARMOR[index], item -> item.setId(GameMode.NORMAL_ARMOR[finalIndex]));
            }
        } else if (player.getGameMode().isUltimateIronman()) {
            for (int index = 0; index < GameMode.ULTIMATE_ARMOR.length; index++) {
                int finalIndex = index;
                player.forEachItemOwned(GameMode.ULTIMATE_ARMOR[index], item -> item.setId(GameMode.NORMAL_ARMOR[finalIndex]));
            }
        }
        GameMode.changeForumsGroup(player, GameMode.IRONMAN.groupId);
        Config.IRONMAN_MODE.set(player, 1);
        player.dialogue(new MessageDialogue("You have successfully downgraded your Ironman restrictions."));
    }

    private static void makePermanent(Player player) {
        if (Config.IRONMAN_MODE_REMOVAL_REQUIREMENT.get(player) == 0) {
            player.dialogue(new MessageDialogue("You have already made your Ironman status permanent."));
            return;
        }
        if (player.getStats().totalLevel < 1000) {
            player.dialogue(new MessageDialogue("You need a total level of 1000 the make your Ironman status permanent."));
            return;
        }
        Config.IRONMAN_MODE_REMOVAL_REQUIREMENT.set(player, 0);
        player.sendMessage("You can no longer remove or change your Ironman status.");
    }

    private static void removeIronmanMode(Player player) {
        if (Config.IRONMAN_MODE_REMOVAL_REQUIREMENT.get(player) == 1) {
            if (player.getBankPin().requiresVerification(Adam::removeIronmanMode))
                return;
        } else {
            player.dialogue(new MessageDialogue("You have previously made your Ironman restrictions permanent, you cannot change your status."));
            return;
        }
        // Removes the players ironman armor
        Stream.of(GameMode.HCIM_ARMOR, GameMode.NORMAL_ARMOR, GameMode.ULTIMATE_ARMOR).forEach(array -> {
            for (int itemId : array) {
                player.forEachItemOwned(itemId, Item::remove);
            }
        });
        GameMode.changeForumsGroup(player, GameMode.STANDARD.groupId);
        Hiscores.archive(player);
        Config.IRONMAN_MODE.set(player, 0);
        player.dialogue(new MessageDialogue("You have successfully removed your Ironman restrictions."));
    }

    private static void giveArmour(Player player, NPC npc) {
        if (!player.getGameMode().isIronMan()) {
            player.dialogue(new NPCDialogue(npc, "You're not an Ironman, get outta here!"));
            return;
        }
        if (player.getInventory().getFreeSlots() < 3) {
            player.sendMessage("You need at least 3 inventory slots to collect your Ironman armour.");
            return;
        }
        switch (player.getGameMode()) {
            case IRONMAN:
                player.getInventory().add(12810, 1);
                player.getInventory().add(12811, 1);
                player.getInventory().add(12812, 1);
                break;
            case ULTIMATE_IRONMAN:
                player.getInventory().add(12813, 1);
                player.getInventory().add(12814, 1);
                player.getInventory().add(12815, 1);
                break;
            case HARDCORE_IRONMAN:
                player.getInventory().add(20792, 1);
                player.getInventory().add(20794, 1);
                player.getInventory().add(20796, 1);
                break;
        }
    }

    static {
        NPCAction.register(ADAM, "Talk-to", (player, npc) -> {
            String gender = getGenderString(player, false);
            if (player.getGameMode().isIronMan()) {
                player.dialogue(
                        new PlayerDialogue("Hey there Adam."),
                        new NPCDialogue(npc, "Hail, Iron " + gender + "! What can I do for you?"),
                        new ActionDialogue(() -> options(player, npc))
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc, "Hello, [player name]. I'm the Iron " + gender + " tutor. What can I do for you?"),
                        new ActionDialogue(() -> options(player, npc))
                );
            }
        });
        NPCAction.register(ADAM, "armour", Adam::giveArmour);
    }
}
