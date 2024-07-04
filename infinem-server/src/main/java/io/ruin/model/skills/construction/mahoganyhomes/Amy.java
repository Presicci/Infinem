package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/3/2024
 */
public class Amy {

    protected static final Config TALKED_TO = Config.varpbit(10562, true);

    private static void firstTimeDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hey I'm Amy, how may I help you?"),
                new PlayerDialogue("What do you do here?"),
                new NPCDialogue(npc, "I'm the founder of Mahogany Homes, Gielinor's first Construction company!"),
                new NPCDialogue(npc, "We've currently got branches in Falador, Varrock, Hosidius and Ardougne."),
                new PlayerDialogue("So people contact you for help with their homes?"),
                new NPCDialogue(npc, "Yep! They'll get in contact if they want assistance, whether it's fixing parts of their house or requesting nicer furniture, and then we will go and help them out."),
                new PlayerDialogue("That sounds quite enjoyable and satisfying to do, are you hiring at all?"),
                new NPCDialogue(npc, "If you have the supplies, I can tell you where one of our clients is that needs some work doing."),
                new NPCDialogue(npc, "I'll keep track of the work you do as well and give you some points for our company reward shop."),
                new NPCDialogue(npc, "Also there are assistants in Varrock, Ardougne, and Hosidius that can give you work, if Falador is inconvienent for you."),
                new NPCDialogue(npc, "The clients that I would offer you to go help could be in any of those cities, it might be a bit of a journey to get there!"),
                new ActionDialogue(() -> {
                    TALKED_TO.set(player, 1);
                    player.dialogue(
                            new OptionsDialogue(
                                    new Option("I'd like a construction contract.", () -> contractDialogue(player, npc)),
                                    new Option("Thanks for the information.", new PlayerDialogue("Thanks for the information."))
                            )
                    );
                })
        );
    }

    private static void howCompanyDoing(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("How is the company doing?"),
                new NPCDialogue(npc, "Pretty good, we've only opened up recently and already have 12 clients!"),
                new NPCDialogue(npc, "These clients are quite indecisive about what they want their houses to look like though, I'm not complaining."),
                new ActionDialogue(() -> {
                    if (PlayerCounter.MAHOGANY_HOMES_CONTRACTS.get(player) > 0) {
                        player.dialogue(
                                new PlayerDialogue("Yeah I've noticed that they seem to keep breaking hatstands all the time too, I'm not sure how easy it is to break a hatstand..."),
                                new NPCDialogue(npc, "Very bizarre."),
                                new PlayerDialogue("I'm glad you're doing well!"),
                                new NPCDialogue(npc, "Thanks.")
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("I'm glad you're doing well!"),
                                new NPCDialogue(npc, "Thanks.")
                        );
                    }
                })
        );
    }

    private static void checkContract(Player player, NPC npc) {
        MahoganyClient client = MahoganyHomes.getClient(player);
        if (client == null) return;
        player.dialogue(
                new PlayerDialogue("What's my current construction contract?"),
                new NPCDialogue(npc, StringUtils.capitalizeFirst(client.getLocation()) + ". You can get another job once you have furnished " + client.getPronoun() + " home.")
        );
    }

    private static void contractDialogue(Player player, NPC npc) {
        int constructionLevel = player.getStats().get(StatType.Construction).fixedLevel;
        player.dialogue(
                new PlayerDialogue("I'd like a construction contract."),
                new NPCDialogue(npc, "What kind of contract would you like?"),
                new OptionsDialogue(
                        new Option("Beginner Contract", () -> MahoganyHomes.assignContract(player, MahoganyDifficulty.BEGINNER)),
                        new Option((constructionLevel < 20 ? "<str>" : "") + "Novice Contract (Requires 20 Construction)",
                                () -> MahoganyHomes.assignContract(player, MahoganyDifficulty.NOVICE)),
                        new Option((constructionLevel < 20 ? "<str>" : "") + "Adept Contract (Requires 50 Construction)",
                                () -> MahoganyHomes.assignContract(player, MahoganyDifficulty.ADEPT)),
                        new Option((constructionLevel < 20 ? "<str>" : "") + "Expert Contract (Requires 70 Construction)",
                                () -> MahoganyHomes.assignContract(player, MahoganyDifficulty.EXPERT)),
                        new Option("I'll pass actually.", new PlayerDialogue("I'll pass actually."))
                )
        );
    }

    private static void dialogue(Player player, NPC npc) {
        if (TALKED_TO.get(player) == 1) {
            List<Option> options = new ArrayList<>();
            if (MahoganyHomes.hasContract(player)) {
                options.add(new Option("What's my current contract?", () -> checkContract(player, npc)));
            } else {
                options.add(new Option("I'd like a construction contract.", () -> contractDialogue(player, npc)));
            }
            options.add(new Option("Could I see the reward shop?", () -> MahoganyHomesShop.openShop(player)));
            options.add(new Option("How is the company doing?", () -> howCompanyDoing(player, npc)));
            if (player.getEquipment().hasAtLeastOneOf(Items.CONSTRUCT_CAPE, Items.CONSTRUCT_CAPE_T)) {
                options.add(new Option("Why don't you have a construction cape?",
                        new PlayerDialogue("Why don't you have a construction cape if you're a master builder?"),
                        new NPCDialogue(npc, "Well why do you have one?"),
                        new PlayerDialogue("The teleports on it are really handy."),
                        new NPCDialogue(npc, "Why would a cape for construction use magic? That seems a bit illogical doesn't it?"),
                        new NPCDialogue(npc, "We are people of hard work, not ones that rely on wizardry!"),
                        new PlayerDialogue("I suppose you're right.")
                ));
            }
            options.add(new Option("Nothing, never mind.", new PlayerDialogue("Nothing, never mind.")));
            player.dialogue(
                    new NPCDialogue(npc, "Hey I'm Amy, how may I help you?"),
                    new OptionsDialogue(options)
            );
        } else {
            firstTimeDialogue(player, npc);
        }
    }

    static {
        NPCAction.register(7417, "talk-to", Amy::dialogue);
        NPCAction.register(7417, "rewards", (player, npc) -> MahoganyHomesShop.openShop(player));
        NPCAction.register(7417, "contract", (player, npc) -> {
            if (MahoganyHomes.hasContract(player)) {
                checkContract(player, npc);
            } else {
                contractDialogue(player, npc);
            }
        });
    }

    // Other contractors
    static {
        for (int id : Arrays.asList(10429, 10430, 10431)) {
            NPCAction.registerIncludeVariants(id, 1, (player, npc) -> {
                if (TALKED_TO.get(player) == 0) {
                    player.dialogue(
                            new NPCDialogue(npc, "Hey, how can I help you?"),
                            new PlayerDialogue("What do you do here?"),
                            new NPCDialogue(npc, "This is one of the branches for Mahogany Homes, Gielinor's first construction company."),
                            new PlayerDialogue("Oh wow, how can I get involved?"),
                            new NPCDialogue(npc, "If you go see Amy in the Falador branch, she'll fill you in. Her office is just east of the Estate Agent in Falador."),
                            new PlayerDialogue("Nice, thank you.")
                    );
                } else {
                    if (MahoganyHomes.hasContract(player)) {
                        checkContract(player, npc);
                    } else {
                        contractDialogue(player, npc);
                    }
                }
            });
            NPCAction.registerIncludeVariants(id, 3, (player, npc) -> {
                if (MahoganyHomes.hasContract(player)) {
                    checkContract(player, npc);
                } else {
                    contractDialogue(player, npc);
                }
            });
        }
    }
}
