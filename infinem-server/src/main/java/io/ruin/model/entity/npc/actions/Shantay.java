package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Misc;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/26/2022
 */
public class Shantay {

    private static final int SHANTAY = 4642;

    private static void talk(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(SHANTAY, "Hello friend. Please read the billboard poster before going into the desert. It'll give yer details on the dangers you can face."),
                new OptionsDialogue(
                        new Option("What is this place?", () -> player.dialogue(
                                new PlayerDialogue("What is this place?"),
                                new NPCDialogue(SHANTAY, "This is the pass of Shantay. I guard this area with my men. I am responsible for keeping this pass open and repaired."),
                                new NPCDialogue(SHANTAY, "My men and I prevent outlaws from getting out of the desert. And we stop the inexperienced from a dry death in the sands. Which would you say you were?"),
                                new OptionsDialogue(
                                        new Option("I am definitely an outlaw, prepare to die!", () -> player.dialogue(
                                                new PlayerDialogue("I am definitely an outlaw, prepare to die!"),
                                                new NPCDialogue(SHANTAY, "Ha, very funny....."),
                                                new NPCDialogue(SHANTAY, "Guards arrest " + Misc.getGenderPronoun(player) + "!"),
                                                new ActionDialogue(() -> {
                                                    Traveling.fadeTravel(player, new Position(3296, 3124, 0));
                                                    leavePrison(player);
                                                })
                                        )),
                                        new Option("I am a little inexperienced.", () -> player.dialogue(
                                                new PlayerDialogue("I am a little inexperienced."),
                                                new NPCDialogue(SHANTAY, "Can I recommend that you purchase a full waterskin and a knife! These items will no doubt save your life. A waterskin will keep water from evaporating in the desert."),
                                                new NPCDialogue(SHANTAY, "And a keen woodsman with a knife can extract the juice from a cactus. Before you go into the desert, it's advisable to wear desert clothes. It's very hot in the desert and you'll surely cook if you wear armour."),
                                                new NPCDialogue(SHANTAY, "To keep the pass bandit free, we charge a small toll of five gold pieces. You can buy a desert pass from me, just ask me to open the shop. You can also use our free banking services by clicking on the chest.")
                                        )),
                                        new Option("Er, neither, I'm an adventurer.", () -> player.dialogue(
                                                new PlayerDialogue("Er, neither, I'm an adventurer."),
                                                new NPCDialogue(SHANTAY, "Great, I have just the thing for the desert adventurer. I sell desert clothes which will keep you cool in the heat of the desert. I also sell waterskins so that you won't die in the desert."),
                                                new NPCDialogue(SHANTAY, "A waterskin and a knife help you survive from the juice of a cactus. Use the chest to store your items, we'll take them to the bank. It's hot in the desert, you'll bake in all that armour."),
                                                new NPCDialogue(SHANTAY, "To keep the pass open we ask for 5 gold pieces. And we give you a Shantay Pass, just ask to see what I sell to buy one.")
                                        ))
                                )
                        )),
                        new Option("Can I see what you have to sell please?", () -> player.dialogue(
                                new PlayerDialogue("Can I see what you have to sell please?"),
                                new NPCDialogue(SHANTAY, "Absolutely Effendi!"),
                                new ActionDialogue(() -> npc.getDef().shops.get(0).open(player))
                        )),
                        new Option("I must be going.", () -> player.dialogue(
                                new PlayerDialogue("I must be going."),
                                new NPCDialogue(SHANTAY, "So long...")
                        ))
                )
        );
    }

    private static void leavePrison(Player player) {
        player.dialogue(
                new NPCDialogue(SHANTAY, "You'll have to stay in there until you pay the fine of five gold pieces. Do you want to pay now?"),
                new OptionsDialogue(
                        new Option("Yes, okay.", () -> {
                            if (player.getInventory().getAmount(995) < 5) {
                                player.dialogue(
                                        new PlayerDialogue("I don't have five gold pieces."),
                                        new NPCDialogue(SHANTAY, "You are to be transported to a maximum security prison in Port Sarim. I hope you've learnt an important lesson from this."),
                                        new ActionDialogue(() -> imprison(player))
                                );
                            } else {
                                pay(player);
                            }
                        }),
                        new Option("No thanks, you're not having my money.", () -> player.dialogue(
                                new PlayerDialogue("No thanks, you're not having my money."),
                                new NPCDialogue(SHANTAY, "You have a choice. You can either pay five gold pieces or... You can be transported to a maximum security prison in Port Sarim."),
                                new NPCDialogue(SHANTAY, "Will you pay the five gold pieces?"),
                                new OptionsDialogue(
                                        new Option("Yes, okay.", () -> {
                                            if (player.getInventory().getAmount(995) < 5) {
                                                player.dialogue(
                                                        new PlayerDialogue("I don't have five gold pieces."),
                                                        new NPCDialogue(SHANTAY, "You are to be transported to a maximum security prison in Port Sarim. I hope you've learnt an important lesson from this."),
                                                        new ActionDialogue(() -> imprison(player))
                                                );
                                            } else {
                                                pay(player);
                                            }
                                        }),
                                        new Option("No, do your worst!", () -> player.dialogue(
                                                new PlayerDialogue("No, do your worst!"),
                                                new NPCDialogue(SHANTAY, "You are to be transported to a maximum security prison in Port Sarim. I hope you've learnt an important lesson from this."),
                                                new ActionDialogue(() -> imprison(player))
                                        ))
                                )
                        ))
                )
        );
    }

    private static void imprison(Player player) {
        player.getTaskManager().doLookupByUUID(904, 1);
        Traveling.fadeTravel(player, new Position(3014, 3180, 0));
    }

    private static void pay(Player player) {
        player.dialogue(
                new PlayerDialogue("Yes, okay."),
                new NPCDialogue(SHANTAY, "Good, I see that you have come to your senses."),
                new ActionDialogue(() -> {
                    player.getInventory().remove(995, 5);
                    Traveling.fadeTravel(player, new Position(3304, 3124, 0));
                    player.dialogue(
                            new NPCDialogue(SHANTAY, "Great Effendi, now please try to keep the peace.")
                    );
                })

        );
    }

    static {
        NPCAction.register(SHANTAY, "talk-to", Shantay::talk);
        ObjectAction.register(2692, 3297, 3124, 0, "open", ((player, obj) -> {
            if (player.getPosition().getX() <= obj.getPosition().getX())
                leavePrison(player);
        }));
    }
}
