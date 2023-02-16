package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import lombok.Getter;


/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/26/2022
 */
public class FaloTheBardClue extends Clue {

    private final String clue;

    public FaloTheBardClue(String clue, ClueType type) {
        super(type);
        this.clue = clue;
    }

    @Override
    public void open(Player player) {
        if (player.hasAttribute(AttributeKey.FALO_SONG)) {
            player.getPacketSender().sendString(203, 2, clue);
            player.openInterface(InterfaceType.MAIN, 203);
        } else {    // If the player has yet to speek to falo
            player.getPacketSender().sendString(203, 2, "Falo the bard wants to see you.");
            player.openInterface(InterfaceType.MAIN, 203);
        }
    }

    private static void showItem(Player player, Item item, NPC npc, FaloTheBardClue clue) {
        if (player.masterClue != null && player.masterClue.id >= 0 && clue == Clue.CLUES[player.masterClue.id]
                && player.getInventory().contains(ClueType.MASTER.clueId)) {
            player.dialogue(
                    new ItemDialogue().one(item.getId(), "You show your object to Falo."),
                    new MessageDialogue("Falo's eyes light up."),
                    new NPCDialogue(FALO, "Yes yes yes! That is it, thank you so much " + player.getFremennikName() + ". The memories have come flooding back. I get why the lyrics are what they are now."),
                    new ActionDialogue(() -> {
                        player.removeAttribute(AttributeKey.FALO_SONG);
                        clue.advance(player);
                    })
            );
        }
    }

    private static final int FALO = 7306;

    private static void faloDialogue(Player player, NPC npc) {
        if (player.masterClue != null && player.masterClue.id >= 0 && player.getInventory().contains(ClueType.MASTER.clueId) &&
                Clue.CLUES[player.masterClue.id] instanceof FaloTheBardClue) {
            FaloTheBardClue clue = (FaloTheBardClue) Clue.CLUES[player.masterClue.id];
            if (player.hasAttribute(AttributeKey.FALO_SONG)) {
                // Every other time
                player.dialogue(
                        new NPCDialogue(FALO, "I really hope you can help me. Please show me the object my song is about!"),
                        new PlayerDialogue("Can you sing me the song again?"),
                        new NPCDialogue(FALO, "Okay, here goes... " + clue.clue),
                        new ActionDialogue(() -> {
                            player.putAttribute(AttributeKey.FALO_SONG, 1);
                        })
                );
            } else {
                // First time
                player.dialogue(
                        new NPCDialogue(FALO, "Thank you for coming " + player.getFremennikName() + ", I need your help."),
                        new NPCDialogue(FALO, "I have one of my old songs stuck in my head, but I cannot remember what the object it refers to is. I am so forgetful. I'll sing it to you."),
                        new NPCDialogue(FALO, "Here goes... " + clue.clue),
                        new ActionDialogue(() -> {
                            player.putAttribute(AttributeKey.FALO_SONG, 1);
                            player.dialogue(
                                    new PlayerDialogue("What a beautiful song!"),
                                    new NPCDialogue(FALO, "You have very kind words. I would be extremely grateful if you could remind me which object the song was about.")
                            );
                        })
                );
            }
        } else {
            player.dialogue(
                    new PlayerDialogue("Hi there! Who are you?"),
                    new NPCDialogue(FALO, "Hi there " + player.getFremennikName() + ". I am Falo the bard! Like my brother Olaf, who lives in Rellekka, I also write music."),
                    new PlayerDialogue("Do you write about anything in particular?"),
                    new NPCDialogue(FALO, "Oh most certainly! I write about all the objects in the lands. From weapons and armour to palm trees and sands. That's why I wander far from Rellekka to seek inspiration."),
                    new NPCDialogue(FALO, "I have one problem though. I am very forgetful, I can never remember which particular object a song was about."),
                    new PlayerDialogue("Surely the lyrics for such a song would be enough to help you remember?"),
                    new NPCDialogue(FALO, "I am afraid not. It saddens me deeply, so I often send out messages to adventurers to help me discreetly."),
                    new PlayerDialogue("I'll keep a look out for them! Goodbye Falo.")
            );
        }
    }

    static {
        for (FaloSong song : FaloSong.values()) {
            FaloTheBardClue clue = new FaloTheBardClue(song.song, ClueType.MASTER);
            for (int id : song.itemIds) {
                ItemNPCAction.register(id, FALO, ((player, item, npc) -> showItem(player, item, npc, clue)));
            }
        }
        NPCAction.register(FALO, "talk-to", FaloTheBardClue::faloDialogue);
    }

    @Getter
    private enum FaloSong {
        DRAGON_SCIMITAR("A blood red weapon, a strong curved sword, found on the island of primate lords.", Items.DRAGON_SCIMITAR),
        BOOK("A book that preaches of some great figure, lending strength, might and vigour.", Items.HOLY_BOOK, Items.BOOK_OF_BALANCE, Items.UNHOLY_BOOK, Items.BOOK_OF_LAW, Items.BOOK_OF_WAR, Items.BOOK_OF_DARKNESS),
        CRYSTAL_BOW("A bow of elven craft was made, it shimmers bright, but will soon fade.", Items.CRYSTAL_BOW, 24123),
        INFERNAL_AXE("A fiery axe of great inferno, when you use it, you'll wonder where the logs go.", Items.INFERNAL_AXE),
        MARK_OF_GRACE("A mark used to increase one's grace, found atop a seer's place.", Items.MARK_OF_GRACE),
        LAVA_DRAGON_BONES("A molten beast with fiery breath, you acquire these with its death.", Items.LAVA_DRAGON_BONES),
        ARMADYL_HELMET("A shiny helmet of flight, to obtain this with melee, struggle you might.", Items.ARMADYL_HELMET),
        DRAGON_DEFENDER("A sword held in the other hand, red its colour, Cyclops strength you must withstand.", Items.DRAGON_DEFENDER, Items.DRAGON_DEFENDER_T),
        WARRIOR_GUILD_TOKEN("A token used to kill mythical beasts, in hopes of a blade or just for an xp feast.", Items.WARRIOR_GUILD_TOKEN),
        //GREENMANS_ALE_M("Green is my favourite, mature ale I do love, this takes your herblore above.", 5743),
        //BARRELCHEST_ANCHOR("It can hold down a boat or crush a goat, this object, you see, is quite heavy.", Items.BARRELCHEST_ANCHOR),
        //BASALT("It comes from the ground, underneath the snowy plain. Trolls aplenty, with what looks like a mane.", 22603),
        TZHAARKETOM("No attack to wield, only strength is required, made of obsidian, but with no room for a shield.", 6528, 23235),
        //FIGHTER_TORSO("Penance healers runners and more, obtaining this body often gives much deplore.", Items.FIGHTER_TORSO),
        BARROWS_GLOVES("Strangely found in a chest, many believe these gloves are the best.", Items.BARROWS_GLOVES),
        //COOKING_GLOVES("These gloves of white won't help you fight, but aid in cooking, they just might.", Items.COOKING_GAUNTLETS),
        NUMULITE("They come from some time ago, from a land unto the east. Fossilised they have become, this small and gentle beast.", 21555),
        RUNE_PLATEBODY("To slay a dragon you must first do, before this chest piece can be put on you.", Items.RUNE_PLATEBODY),
        ROD_OF_INVADIS("Vampyres are agile opponents, damaged best with a weapon of many components.", Items.ROD_OF_IVANDIS_10, Items.ROD_OF_IVANDIS_2, Items.ROD_OF_IVANDIS_3, Items.ROD_OF_IVANDIS_4, Items.ROD_OF_IVANDIS_5, Items.ROD_OF_IVANDIS_6, Items.ROD_OF_IVANDIS_7, Items.ROD_OF_IVANDIS_8, Items.ROD_OF_IVANDIS_9, Items.ROD_OF_IVANDIS_1, 22398);

        private final String song;
        private final int[] itemIds;

        FaloSong(String song, int... itemIds) {
            this.song = song;
            this.itemIds = itemIds;
        }
    }
}
