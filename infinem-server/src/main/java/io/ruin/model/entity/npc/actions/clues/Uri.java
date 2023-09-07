package io.ruin.model.entity.npc.actions.clues;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.impl.EmoteClue;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/21/2021
 */
public class Uri extends NPC {

    private static final int URI = 1774;

    public Uri(Player player, EmoteClue clue) {
        super(URI);
        ownerId = player.getUserId();
        putTemporaryAttribute(AttributeKey.URI_CLUE, clue);
        player.putTemporaryAttribute(AttributeKey.URI_SPAWNED, true);
        int x = player.getMovement().followX;
        int y = player.getMovement().followY;
        if (x == -1 || y == -1) {   // Just in case the player tries to do the emote after just logging in
            x = player.getAbsX() + 1;
            y = player.getAbsY();
        }
        spawn(x, y, player.getHeight());
        face(player);
        graphics(86);
    }

    @Override
    public void remove() {
        Optional<Player> player = World.getPlayerByUid(ownerId);
        player.ifPresent(value -> value.removeTemporaryAttribute(AttributeKey.URI_SPAWNED));
        graphics(86);
        super.remove();
    }

    /**
     * Array of dialogue lines that Uri will say to the player when they complete their clue.
     */
    private static final String[] dialogueLines = {
            /*
             * Current lines
             */
            "A great captain is always ready to change course.",
            "Actions have consequences.",
            "Can I have your teabag?",
            "Can you stand me?",
            "I am the egg man, are you one of the egg men?",
            "I believe that it is very rainy in Varrock.",
            "I have a story about that.",
            "I heard that the tall man fears only strong winds.",
            "I quite fancy an onion.",
            "In Canifis the men are known for eating much spam.",
            "In the end, only the three-legged will survive.",
            "It is possible to commit no mistakes and still lose.",
            "Loser says what.",
            "My magic carpet is full of eels.",
            "Once, I was a poor man, but then I found a party hat.",
            "Quickly! I've got a bee sticking out of my arm!",
            "The slowest of fishermen catch the swiftest of fish.",
            "*sneezes* What?",
            "The sudden appearance of a deaf squirrel is most puzzling, comrade.",
            "There were three goblins in a bar, which one left first?",
            "Up in the north, I hear they keep milk in bags rather than buckets.",
            "Want to see me bend a spoon?",
            "What's cheese?",
            "Would you like to buy a pewter spoon?",

            /*
             * Custom lines
             */
            "Have you heard? Hazelmere's lost his signet ring, whoever finds that is going to be rich!",    // Alluding to hazelmere's signet ring, a rare ass drop

            /*
             * Historical lines
             */
            "9 years my princess, forever my light.",
            "Ahhhhhh, leeches!",
            "The Ankou's are a lie.",
            "(Breathing intensifies)",
            "Brother, do you even lift?",
            "Connection lost. Please wait - attempting to reestablish.",
            "Do you want ants? Because that's how you get ants.",
            "Don't forget to find the jade monkey.",
            "Fancy a holiday? I heard there was a whole other world to the west.",
            "Guys, let's lake dive!",
            "I don't like pineapple, it has that bone in it.",
            "I gave you what you needed; not what you think you needed.",
            "I once named a duck after a girl. Big mistake, they all hated it.",
            "I told him not to go near that fence, and what did he do? Sheesh....",
            "I took a college course in bowling; still not any good.",
            "I'm going to get married, to the night.",
            "I'm looking for a girl named Molly. I can't seem to find her. any assistance?",
            "Init doe. Lyk, I hope yer reward iz goodd aye?",
            "Is that Deziree?",
            "It is quite easy being green.",
            "Mate, mate... I'm the best.",
            "Mother's parsnips tasted like onion.",
            "Oranges are the fruit of the vegetable.",
            "Some say...",
            "There's this guy I sit next to. Makes weird faces and sounds. Kind of an odd fellow.",
            "This is the last night you'll spend alone.",
            "Tonight we dine................quite nicely actually.",
            "Took a hair dryer to a party in my handbag. Ah, so fabulous!!"
    };

    static {
        NPCAction.register(URI, "talk-to", (player, npc) -> {
            EmoteClue clue = npc.getTemporaryAttributeOrDefault(AttributeKey.URI_CLUE, null);
            if(npc.ownerId != player.getUserId()
                    || clue == null || !clue.equipmentCheck(player)
                    || !clue.hasPerformedSecondEmote(player)
                    || !player.getInventory().contains(clue.type.clueId)) {
                player.dialogue(new NPCDialogue(URI, "I do not believe we have any business, Comrade."));
            } else {
                player.dialogue(
                        new NPCDialogue(URI, dialogueLines[Random.get(dialogueLines.length - 1)]),
                        new PlayerDialogue("What?"),
                        new ActionDialogue(() -> {
                            clue.advance(player);
                            /*if (clue.type.getSave(player).remaining == 0) {
                                new ItemDialogue().one(clue.type.casketId, "You've been given a casket!");
                            } else {
                                new ItemDialogue().one(clue.type.clueId, "You've been given a new clue!");
                            }*/
                            npc.remove();
                        }));
            }
        });
    }
}
