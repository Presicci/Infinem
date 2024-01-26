package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

import java.util.Arrays;
import java.util.HashSet;

@IdHolder(ids = {70, 88, 95, 6, 18, 81})//@IdHolder(ids = {89, 96, 35, 79, 37, 9})
public class NPCActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if(player.isLocked())
            return;
        int option = OPTIONS[opcode];
        if (option != 6)
            player.resetActions(true, true, true);
        if(option == 1) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readLEShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 2) {
            int targetIndex = in.readShort();
            int ctrlRun = in.readByteS();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 3) {
            int targetIndex = in.readLEShort();
            int ctrlRun = in.readByte();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 4) {
            int ctrlRun = in.readByteC();
            int targetIndex = in.readShort();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 5) {
            int ctrlRun = in.readByte();
            int targetIndex = in.readShortA();
            handleAction(player, option, targetIndex, ctrlRun);
            return;
        }
        if(option == 6) {
            int id = in.readLEShortA();
            NPCDef def = NPCDef.get(id);
            if(def == null)
                return;
            if(player.debug)
                player.sendFilteredMessage("[NpcAction] option: 6 id=" + id);
            def.examine(player);
            return;
        }
        player.sendFilteredMessage("Unhandled npc action: option=" + option + " opcode=" + opcode);
    }

    private static void handleAction(Player player, int option, int npcIndex, int ctrlRun) {
        NPC npc = World.getNpc(npcIndex);
        if(npc == null)
            return;
        NPCDef def = npc.getConfigDef(player);
        if(player.debug)
            debug(player, npc, def, option);
        player.face(npc);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        if(option == def.attackOption) {
            player.getCombat().setTarget(npc);
            return;
        }
        if(npc.skipMovementCheck) {
            player.face(npc);
            int i = option - 1;
            if(i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = def.varpbitId != -1 ? def.defaultActions : npc.actions;
            if(actions != null)
                action = actions[i];
            if(action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if(action != null) {
                action.handle(player, npc);
                return;
            }
            return;
        }
        TargetRoute.set(player, npc, () -> {
            int i = option - 1;
            if(i < 0 || i >= 5)
                return;
            NPCAction action = null;
            NPCAction[] actions = def.varpbitId != -1 ? def.defaultActions : npc.actions;
            if(actions != null)
                action = actions[i];
            if(def.cryptic != null && def.cryptic.advance(player))
                return;
            if(def.anagram != null && def.anagram.advance(player))
                return;
            if(action == null && (actions = def.defaultActions) != null)
                action = actions[i];
            if(action != null) {
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

    private static void debug(Player player, NPC npc, NPCDef def, int option) {
        int configId = def.id;
        if(npc == null) {
            player.sendFilteredMessage("[NpcAction] option: " + option + " NULL NPC");
            return;
        }
        def = npc.getDef();
        DebugMessage debug = new DebugMessage();
        if(option != -1)
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
            for(int id : def.showIds) {
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