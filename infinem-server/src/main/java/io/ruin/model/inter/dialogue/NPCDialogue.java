package io.ruin.model.inter.dialogue;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NPCDialogue extends Dialogue {

    private NPCDefinition npcDefinition;

    private String message;

    private Runnable onDialogueOpened;

    private int animationId = 554;

    private int lineHeight;

    private boolean hideContinue;

    public NPCDialogue(int npcId, String message) {
        this(NPCDefinition.get(npcId), message);
    }

    public NPCDialogue(NPC npc, String message) {
        this(npc.getDef(), message);
    }

    private NPCDialogue(NPCDefinition npcDefinition, String message) {
        this.npcDefinition = npcDefinition;
        this.message = message;
    }

    public NPCDialogue onDialogueOpened(Runnable openDialogueOpened) {
        this.onDialogueOpened = openDialogueOpened;
        return this;
    }

    public NPCDialogue animate(int animationId) {
        this.animationId = animationId;
        return this;
    }

    public NPCDialogue lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public NPCDialogue hideContinue() {
        this.hideContinue = true;
        return this;
    }

    @Override
    public void open(Player player) {
        npcDefinition = NPCDefinition.getConfigDef(npcDefinition.id, player);
        message = message.replace("[player name]", player.getName());
        message = message.replace("[madam/sir]", player.getAppearance().getGenderString());
        message = message.replace("[brother/sister]", player.getAppearance().isMale() ? "brother" : "sister");
        message = message.replace("[monsieur/mademoiselle]", player.getAppearance().isMale() ? "monsieur" : "mademoiselle");
        message = message.replace("[Fremennik name]", player.getFremennikName());
        message = message.replace("[lad/lass]", player.getAppearance().isMale() ? "lad" : "lass");
        message = message.replace("[craftsman/craftswoman]", player.getAppearance().isMale() ? "craftsman" : "craftswoman");
        player.openInterface(InterfaceType.CHATBOX, Interface.NPC_DIALOGUE);
        player.getPacketSender().sendNpcHead(Interface.NPC_DIALOGUE, 2, npcDefinition.id);
        player.getPacketSender().animateInterface(Interface.NPC_DIALOGUE, 2, animationId);
        player.getPacketSender().sendString(Interface.NPC_DIALOGUE, 4, npcDefinition.name);
        player.getPacketSender().sendString(Interface.NPC_DIALOGUE, 6, message);
        player.getPacketSender().setTextStyle(Interface.NPC_DIALOGUE, 6, 1, 1, lineHeight);
        player.getPacketSender().sendAccessMask(Interface.NPC_DIALOGUE, 5, -1, -1, 1);
        if (hideContinue)
            player.getPacketSender().setHidden(Interface.NPC_DIALOGUE, 5, true);
        else
            player.getPacketSender().sendString(Interface.NPC_DIALOGUE, 5, "Click here to continue");
        if (onDialogueOpened != null)
            onDialogueOpened.run();
    }

    static {
        InterfaceHandler.register(Interface.NPC_DIALOGUE, h -> h.actions[5] = (SimpleAction) player -> {
            player.continueDialogue();
            player.onDialogueContinued();
        });
    }

}
