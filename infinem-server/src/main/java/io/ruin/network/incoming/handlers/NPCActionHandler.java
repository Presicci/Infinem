package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.construction.mahoganyhomes.MahoganyClient;
import io.ruin.model.skills.construction.mahoganyhomes.MahoganyHomes;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.ClientPacket;
import io.ruin.utility.ClientPacketHolder;
import io.ruin.utility.DebugMessage;

import java.util.Arrays;

@ClientPacketHolder(packets = {
        ClientPacket.OPNPC1, ClientPacket.OPNPC2, ClientPacket.OPNPC3,
        ClientPacket.OPNPC4, ClientPacket.OPNPC5, ClientPacket.OPNPC6})
public class NPCActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if (player.isLocked())
            return;
        player.resetIdle();
        player.removeTemporaryAttribute("TRICKSTER_AUTOPICKPOCKET");
        int option = OPTIONS[opcode];
        if (option != 6)
            player.resetActions(true, true, true);
        if (option == 1) {
            int targetIndex = in.readUnsignedShortAdd();
            int ctrlRun = in.readByteNeg();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 2) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readUnsignedLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 3) {
            int ctrlRun = in.readByteNeg();
            int targetIndex = in.readUnsignedShortAdd();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 4) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readUnsignedShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 5) {
            int targetIndex = in.readUnsignedLEShortAdd();
            int ctrlRun = in.readByteAdd();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if (option == 6) {
            int id = in.readUnsignedLEShort();
            NPCDefinition def = NPCDefinition.get(id);
            if (def == null)
                return;
            if (player.debug)
                player.sendFilteredMessage("[NpcAction] option: 6 id=" + id);
            def.examine(player);
            return;
        }
        player.sendFilteredMessage("Unhandled npc action: option=" + option + " opcode=" + opcode);
    }

    private static void handleAction(Player player, int option, int npcIndex, int ctrlRun) {
        NPC npc = World.getNpc(npcIndex);
        if (npc == null)
            return;
        NPCDefinition def = npc.getConfigDef(player);
        if (player.debug)
            debug(player, npc, def, option);
        player.face(npc);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        if (option == def.attackOption) {
            player.getCombat().setTarget(npc);
            return;
        }
        if (npc.skipMovementCheck) {
            player.face(npc);
            int i = option - 1;
            if (i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = def.varpbitId != -1 ? def.defaultActions : npc.actions;
            if (actions != null)
                action = actions[i];
            if (action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if (action != null) {
                action.handle(player, npc);
                return;
            }
            return;
        }
        TargetRoute.set(player, npc, () -> {
            int i = option - 1;
            if (i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = def.varpbitId != -1 ? def.defaultActions : npc.actions;
            if (actions != null)
                action = actions[i];
            if (option == 1 && dialogueOverride(player, npc, def)) {
                return;
            }
            if (action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if (action != null) {
                action.handle(player, npc);
                player.face(npc);
                return;
            }
            /* default to a dialogue */
            player.dialogue(
                    new NPCDialogue(npc, "Beautiful day today, isn't it?").onDialogueOpened(() -> npc.faceTemp(player)),
                    new PlayerDialogue("Uhh.. yeah I guess.")
            );
        });
    }

    private static boolean dialogueOverride(Player player, NPC npc, NPCDefinition def) {
        if (def.cryptic != null && def.cryptic.advance(player))
            return true;
        if (def.anagram != null) {
            if (def.anagram.type == ClueType.MASTER && def.anagram.canAdvance(player)) {
                def.anagram.puzzleDialogue(player, npc);
                return true;
            }
            if (def.anagram.hasChallenge() && def.anagram.canAdvance(player)) {
                def.anagram.challengeDialogue(player, npc);
                return true;
            }
            if (def.anagram.requiresPuzzleBox() && def.anagram.canAdvance(player)) {
                def.anagram.puzzleBoxDialogue(player, npc);
                return true;
            }
            if (def.anagram.advance(player)) return true;
        }
        MahoganyClient client = MahoganyHomes.getClient(player);
        if (client != null && client.getNpcId() == npc.getId()) {
            client.dialogue(player);
            return true;
        }
        return false;
    }

    private static void debug(Player player, NPC npc, NPCDefinition def, int option) {
        int configId = def.id;
        if (npc == null) {
            player.sendFilteredMessage("[NpcAction] option: " + option + " NULL NPC");
            return;
        }
        def = npc.getDef();
        DebugMessage debug = new DebugMessage();
        if (option != -1)
            debug.add("option", option);
        debug.add("id", def.id);
        debug.add("name", def.name);
        debug.add("index", npc.getIndex());
        debug.add("x", npc.getAbsX());
        debug.add("y", npc.getAbsY());
        debug.add("z", npc.getHeight());
        debug.add("options", Arrays.toString(def.options));
        debug.add("varpbitId", def.varpbitId);
        debug.add("varpId", def.varpId);
        if ((def.varpbitId != -1 || def.varpId != -1) && def.showIds != null) {
            StringBuilder variants = new StringBuilder("[");
            for (int id : def.showIds) {
                if (id == -1) continue;
                if (variants.length() > 1)
                    variants.append(", ");
                if (id == configId)
                    variants.append("C:");
                variants.append(id);
            }
            debug.add("variants", variants);
        }

        player.sendFilteredMessage("[NpcAction] " + debug.toString());
    }

}