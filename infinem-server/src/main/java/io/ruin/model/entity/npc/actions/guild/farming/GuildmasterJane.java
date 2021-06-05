package io.ruin.model.entity.npc.actions.guild.farming;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.farming.farming_contracts.FarmingContracts;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/4/2021
 */
public class GuildmasterJane {

    public static void aboutGuild(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "After the Shayzien army cleared out the Lizardmen in this area, I founded " +
                        "this guild with some fellow Hosidius farmers.").animate(569),
                new PlayerDialogue("Why here? Why not within the safety of Kourend?").animate(567),
                new NPCDialogue(npc, "The heat of the volcano allows us to grow things here that would never survive " +
                        "in the climate of Kourend. The discoveries we have made are worth the extra risk.").animate(568),
                new PlayerDialogue("Fair enough, anything else I should know about the guild?").animate(567),
                new NPCDialogue(npc, "The guild is split into three tiers, with only advanced farmers being allowed access " +
                        "to the upper echelons.").animate(569),
                new NPCDialogue(npc, "Would you like to hear more about one of the tiers?").animate(569),
                //new NPCDialogue(npc, "").onDialogueOpened(() -> aboutGuild(player, npc)),
                new OptionsDialogue(
                        new Option("Tell me about the beginner tier.", () ->
                                player.dialogue(
                                        new PlayerDialogue("Tell me about the beginner tier.").animate(567),
                                        new NPCDialogue(npc, "In the beginner tier you can access farming patches for cacti, allotments, " +
                                                "bushes and flowers. You'll also find some shops with useful farming supplies.").animate(570),
                                        new NPCDialogue(npc, "Would you like to hear more about another tier?").animate(570),
                                        new NPCDialogue(npc, "").animate(570).onDialogueOpened(() -> aboutGuildTiers(player, npc))

                                )),
                        new Option("Tell me about the intermediate tier.", () ->
                                player.dialogue(
                                        new PlayerDialogue("Tell me about the intermediate tier.").animate(567),
                                        new NPCDialogue(npc, "In the intermediate tier you can access farming patches for herbs and trees. " +
                                                "You'll also find the anima and hespori patches which are unique to this guild.").animate(570),
                                        new NPCDialogue(npc, "Would you like to hear more about another tier?").animate(570),
                                        new NPCDialogue(npc, "").animate(570).onDialogueOpened(() -> aboutGuildTiers(player, npc))

                                )),
                        new Option("Tell me about the advanced tier.", () ->
                                player.dialogue(
                                        new PlayerDialogue("Tell me about the advanced tier.").animate(567),
                                        new NPCDialogue(npc, "In the advanced tier you can access farming patches for fruit trees and spirit trees. " +
                                                "You'll also find the redwood and celastrus patches which are unique to this guild.").animate(570),
                                        new NPCDialogue(npc, "Would you like to hear more about another tier?").animate(570),
                                        new NPCDialogue(npc, "").animate(570).onDialogueOpened(() -> aboutGuildTiers(player, npc))

                                )),
                        new Option("No thanks.")
                )
        );
    }

    public static void aboutGuildTiers(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Tell me about the beginner tier.", () ->
                                player.dialogue(
                                        new PlayerDialogue("Tell me about the beginner tier.").animate(567),
                                        new NPCDialogue(npc, "In the beginner tier you can access farming patches for cacti, allotments, " +
                                                "bushes and flowers. You'll also find some shops with useful farming supplies.").animate(570),
                                        new NPCDialogue(npc, "Would you like to hear more about another tier?").animate(570),
                                        new NPCDialogue(npc, "").animate(570).onDialogueOpened(() -> aboutGuildTiers(player, npc))

                                )),
                        new Option("Tell me about the intermediate tier.", () ->
                                player.dialogue(
                                        new PlayerDialogue("Tell me about the intermediate tier.").animate(567),
                                        new NPCDialogue(npc, "In the intermediate tier you can access farming patches for herbs and trees. " +
                                                "You'll also find the anima and hespori patches which are unique to this guild.").animate(570),
                                        new NPCDialogue(npc, "Would you like to hear more about another tier?").animate(570),
                                        new NPCDialogue(npc, "").animate(570).onDialogueOpened(() -> aboutGuildTiers(player, npc))

                                )),
                        new Option("Tell me about the advanced tier.", () ->
                                player.dialogue(
                                        new PlayerDialogue("Tell me about the advanced tier.").animate(567),
                                        new NPCDialogue(npc, "In the advanced tier you can access farming patches for fruit trees and spirit trees. " +
                                                "You'll also find the redwood and celastrus patches which are unique to this guild.").animate(570),
                                        new NPCDialogue(npc, "Would you like to hear more about another tier?").animate(570),
                                        new NPCDialogue(npc, "").animate(570).onDialogueOpened(() -> aboutGuildTiers(player, npc))

                                )),
                        new Option("No thanks.")
                )
        );
    }

    public static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "My name is Jane, and I am the founder of the Farming Guild.").animate(569),
                new NPCDialogue(npc, "Is there anything else I can help you with?"),
                new OptionsDialogue(
                        new Option("Tell me about the guild.", () -> aboutGuild(player, npc)),
                        (player.farmingContract == null ?
                                new Option("Do you have any jobs for me?", () -> farmingContractPre(player, npc)) :
                                new Option("What's my current farming contract?", () -> farmingContractCheck(player, npc))
                        ),
                        new Option("That's all, thanks.", () -> player.dialogue(new PlayerDialogue("That's all, thanks.").animate(567)))
                )
        );
    }

    public static void farmingContractCheck(Player player, NPC npc) {
        FarmingContracts contract = player.farmingContract;
        player.dialogue(
                new PlayerDialogue("What's my current farming contract?"),
                (contract != null ?
                        (new NPCDialogue(npc, "We need you to grow a " + ItemDef.get(contract.crop.getSeed()).name.toLowerCase()
                                + ".  I'll reward you once " + (contract.checkHealth ? "you have checked its health." : "it has been fully harvested.")).animate(569)) :
                        (new NPCDialogue(npc, "It seems you don't have a contract.").animate(569))    // Shouldn't happen
                ),
                new OptionsDialogue(
                        new Option("Do you have anything easier?", () -> farmingContractEasier(player, npc, contract)),
                        new Option("Thank you.")));
    }

    public static void farmingContractEasier(Player player, NPC npc, FarmingContracts contract) {
        player.dialogue(
                new PlayerDialogue("Do you have anything easier?").animate(569),
                new NPCDialogue(npc, "I suppose I could give you a different contract, if you're sure.").animate(569),
                new OptionsDialogue(
                        new Option("Yes please.", () -> {
                            if (contract.difficulty == 0) {
                                player.dialogue(
                                        new NPCDialogue(npc, "You already have an easy contract, surely that's easy enough?").animate(569)
                                );
                            } else {
                                farmingContractGet(player, npc, contract.difficulty - 1);
                            }
                        }),
                        new Option("No thanks.")
                )
        );
    }

    public static void farmingContractGet(Player player, NPC npc, int difficulty) {
        if (player.getStats().get(StatType.Farming).fixedLevel < ((difficulty * 20) + 45)) {
            player.dialogue(
                    new NPCDialogue(npc, "You don't have a high enough farming level for that difficulty of contract.").animate(569),
                    new NPCDialogue(npc, "").onDialogueOpened(() -> farmingContractSelect(player, npc)));
        } else {
            String difficultyName = difficulty == 2 ? "hard" : difficulty == 1 ? "medium" : "easy";
            FarmingContracts contract = FarmingContracts.generateContract(player, difficulty);
            player.dialogue(
                    new PlayerDialogue("Could I have an " + difficultyName + " contract please?"),
                    new NPCDialogue(npc, "Please could you grow a " + ItemDef.get(contract.crop.getSeed()).name.toLowerCase()
                            + " for us?  I'll reward you once " + (contract.checkHealth ? "you have checked its health." : "it has been fully harvested.")).animate(569)
            );
        }
    }

    public static void farmingContractSelect(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Easy Contract (Requires 45 Farming)", () -> farmingContractGet(player, npc, 0)),
                        new Option("Medium Contract (Requires 65 Farming)", () -> farmingContractGet(player, npc, 1)),
                        new Option("Hard Contract (Requires 85 Farming)", () -> farmingContractGet(player, npc, 2))
                )
        );
    }

    public static void farmingContractPre(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Do you have any jobs for me?"),
                new NPCDialogue(npc, "Well we always need to rotate the crops we're growing to stop the soil from tiring due to lack " +
                        "of nutrients. It would be great if you could help out with that.").animate(569),
                new NPCDialogue(npc, "If you're interested, I can give you a farming contract which tells you what to plant within the guild. " +
                        "Complete the contract, and I'll give you some seeds as a thank you.").animate(569),
                new OptionsDialogue(
                        new Option("I'd like a farming contract.", () -> farmingContractSelect(player, npc)),
                        new Option("Thanks for the information.", () -> player.dialogue(new PlayerDialogue("That's all, thanks.").animate(567)))
                )
        );
    }

    public static void farmingContractsCompleted(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("How many farming contracts have I completed?"),
                new NPCDialogue(npc, "You've completed " + PlayerCounter.FARMING_CONTRACTS_COMPLETED.get(player) + " farming contracts.").animate(569),
                new NPCDialogue(npc, "Is there anything else I can help you with?").animate(569),
                new OptionsDialogue(
                        new Option("Tell me about the guild.", () -> aboutGuild(player, npc)),
                        (player.farmingContract == null ?
                                new Option("Do you have any jobs for me?", () -> farmingContractPre(player, npc)) :
                                new Option("What's my current farming contract?", () -> farmingContractCheck(player, npc))
                        ),
                        new Option("That's all, thanks.", () -> player.dialogue(new PlayerDialogue("That's all, thanks.").animate(567)))
                )
        );
    }

    public static void completeContract(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Welcome to the farming guild. How may I help you?").animate(569),
                new PlayerDialogue("I've completed my farming contract."),
                new NPCDialogue(npc, "You'll be wanting a reward then. Here you go.").animate(569),
                new ActionDialogue(() -> {
                    if (player.farmingContract.rewardContract(player)) {
                        player.farmingContract = null;
                        player.contractCompleted = false;
                        player.dialogue(
                                new ItemDialogue().one(22993, "Guildmaster Jane has given you a seed pack."),
                                new NPCDialogue(npc, "Now, would you like another contract?").animate(569),
                                new OptionsDialogue(
                                        new Option("Yes please.", () -> farmingContractSelect(player, npc)),
                                        new Option("No thanks.")
                                )
                        );
                    } else {
                        player.dialogue(
                                new NPCDialogue(npc, "Looks like your inventory is full, come back to me once you have space.").animate(569)
                        );
                    }
                })
        );
    }

    static {
        NPCAction.register(8587, "talk-to", (player, npc) -> {
            if (player.contractCompleted) {
                completeContract(player, npc);
            } else {
                player.dialogue(
                        new NPCDialogue(npc, "Welcome to the farming guild. How may I help you?").animate(569),
                        new OptionsDialogue(
                                new Option("Tell me about the guild.", () -> aboutGuild(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                (player.farmingContract == null ?
                                        new Option("Do you have any jobs for me?", () -> farmingContractPre(player, npc)) :
                                        new Option("What's my current farming contract?", () -> farmingContractCheck(player, npc))
                                ),
                                new Option("How many farming contracts have I completed?", () -> farmingContractsCompleted(player, npc)),
                                new Option("I'm just passing by.")
                        )
                );
            }
        });

        NPCAction.register(8587, "contract", (player, npc) -> {
            if (player.farmingContract == null) {
                farmingContractSelect(player, npc);
            } else {
                if (player.contractCompleted) {
                    completeContract(player, npc);
                } else {
                    farmingContractCheck(player, npc);
                }
            }
        });
    }
}
