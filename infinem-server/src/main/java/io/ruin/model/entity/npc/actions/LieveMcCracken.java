package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/26/2023
 */
public class LieveMcCracken {

    private static void handleUseItem(Player player, Item item, NPC npc) {
        switch (item.getId()) {
            case Items.TRIDENT_OF_THE_SEAS:
            case Items.TRIDENT_OF_THE_SEAS_FULL:
                upgradeTrident(player, item, npc, 22288);
                break;
            case Items.UNCHARGED_TRIDENT:
                upgradeTrident(player, item, npc, 22290);
                break;
            case Items.TRIDENT_OF_THE_SWAMP:
                upgradeTrident(player, item, npc, 22292);
                break;
            case Items.UNCHARGED_TOXIC_TRIDENT:
                upgradeTrident(player, item, npc, 22294);
                break;
            case 22288: // Trident of the seas (e)
            case 22290: // Uncharged trident (e)
            case 22292: // Trident of the swamp (e)
            case 22294: // Uncharged toxic trident (e)
                break;
            case Items.KRAKEN_TENTACLE:
            case Items.KRAKEN_TENTACLE_NOTE:
                storeTentacles(player, item, npc);
                break;
            case Items.ABYSSAL_TENTACLE:
                player.dialogue(new NPCDialogue(npc, "Ugh - what's that? It's got a whip stuck in it! I don't want that! Take it away!"));
                break;
            default:
                player.dialogue(new NPCDialogue(npc, "No thanks."));
                break;
        }
    }

    private static void upgradeTrident(Player player, Item item, NPC npc, int newId) {
        int tentacles = player.getAttributeIntOrZero(AttributeKey.LIEVE_KRAKEN_TENTACLES);
        if (tentacles < 10) {
            player.dialogue(new NPCDialogue(npc, "Ya gotta bring me 10 tentacles before I'll enhance yer tridents for ya. "
                    + (tentacles == 0 ? "Just hand 'em to me when ye've got 'em."
                    : tentacles == 1 ? "So far ya brought me one."
                    : "So far ya brought me " + tentacles + ".")));
            return;
        }
        int newTentacles = player.incrementNumericAttribute(AttributeKey.LIEVE_KRAKEN_TENTACLES, -10);
        item.setId(newId);
        player.dialogue(new ItemDialogue().one(newId, "Lieve enhances your trident. She has "
                + (newTentacles == 0 ? "no more tentacles stored for you."
                : newTentacles == 1 ? "one more tentacle stored for you."
                : newTentacles + " more tentacles stored for you.")));
    }

    private static void storeTentacles(Player player, Item item, NPC npc) {
        int amount = player.getInventory().getAmount(item.getId());
        int storedAmount = player.getAttributeIntOrZero(AttributeKey.LIEVE_KRAKEN_TENTACLES);
        player.dialogue(
                new NPCDialogue(npc, "I see ya got "
                        + (amount > 1 ? amount + " tentacles" : "a tentacle")
                        + " there. Do ya want me to hold onto it for ya? For every 10 ya gives me, I'll enhance a trident for ya."),
                new NPCDialogue(npc, "Remember, I won't give 'em back. Once ya gives me a tentacle, I keep it."
                        + (storedAmount > 0 ? " Ya already gave me " + storedAmount + "." : "")),
                new OptionsDialogue("Lieve will not return your tentacle" + (amount > 1 ? "s." : "."),
                        new Option("Give " + (amount > 1 ? "them" : "it") + " to her.", () -> {
                            player.getInventory().remove(item.getId(), amount);
                            int newAmount = player.incrementNumericAttribute(AttributeKey.LIEVE_KRAKEN_TENTACLES, amount);
                            player.dialogue(new NPCDialogue(npc, "Thanks. I've got "
                                    + (newAmount > 1 ? newAmount + " tentacles" : "one tentacle") + " stored for ya now. "
                                    + (newAmount >= 10 ? "For every 10, ya can hand me a trident, an' I'll enhance it."
                                    : "Once ye've brought me 10, ya can hand me a trident, an' I'll enhance it.")));
                        }),
                        new Option("Do not give " + (amount > 1 ? "them" : "it") + " to her.")
                )
        );
    }

    static {
        ItemNPCAction.register(7412, LieveMcCracken::handleUseItem);
    }
}
