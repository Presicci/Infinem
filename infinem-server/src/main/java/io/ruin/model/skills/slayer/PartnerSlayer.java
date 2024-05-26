package io.ruin.model.skills.slayer;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Color;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/17/2024
 */
public class PartnerSlayer {

    protected static final int TILE_RANGE = 20;
    protected static final String PARTNER_KEY = "SLAY_PARTNER";

    public static void attemptToPartnerPlayer(Player player, Player target) {
        Player currentPartner = getPartner(player);
        if (currentPartner != null) {
            if (currentPartner == target) {
                player.dialogueKeepInterfaces(new MessageDialogue("That player is already your slayer partner."));
            } else {
                player.dialogueKeepInterfaces(new MessageDialogue("You already have a slayer partner."));
            }
            return;
        }
        if (target.getGameMode().isIronMan()) {
            player.dialogueKeepInterfaces(new MessageDialogue("That player is an ironman and cannot be your slayer partner."));
            return;
        }
        if (Config.ACCEPT_AID.get(player) == 0) {
            player.dialogueKeepInterfaces(new MessageDialogue("You both need accept aid enabled to be slayer partners."));
            return;
        }
        if (Config.ACCEPT_AID.get(target) == 0) {
            player.dialogueKeepInterfaces(new MessageDialogue("That player needs accept aid enabled to be your slayer partner."));
            return;
        }
        if (target.isLocked() || target.getCombat().isDefending(5)
                || target.hasOpenInterface(InterfaceType.MAIN)
                || target.hasOpenInterface(InterfaceType.MAIN_STRETCHED)
                || target.hasOpenInterface(InterfaceType.CHATBOX)) {
            player.dialogueKeepInterfaces(new MessageDialogue("That player is currently busy."));
            return;
        }
        int task = Slayer.getTask(player);
        int otherTask = Slayer.getTask(target);
        if (task != otherTask) {
            player.dialogueKeepInterfaces(new MessageDialogue("To partner with this player you both need the same or no current task."));
            return;
        }
        if (task == SlayerCreature.BOSSES.getUid()) {
            player.dialogueKeepInterfaces(new MessageDialogue("Boss tasks cannot be done with a partner."));
            return;
        }
        target.dialogue(false,
                new OptionsDialogue(player.getName() + " has requested to be your slayer partner.",
                        new Option("Accept", () -> {
                            player.putTemporaryAttribute(PARTNER_KEY, target);
                            target.putTemporaryAttribute(PARTNER_KEY, player);
                            player.sendMessage("You are now slayer partner's with " + target.getName() + ".");
                            target.sendMessage("You are now slayer partner's with " + player.getName() + ".");
                            updateInterface(player);
                            updateInterface(target);
                        }),
                        new Option("Decline", () -> player.sendMessage(target.getName() + " has declined your slayer partner invite."))
                )
        );
    }

    private static void updateInterface(Player player) {
        if (!player.isVisibleInterface(68)) return;
        openPartnerInterface(player);
    }

    public static void removePartner(Player player) {
        Player partner = player.getTemporaryAttribute(PARTNER_KEY);
        if (partner == null) return;
        player.removeTemporaryAttribute(PARTNER_KEY);
        partner.removeTemporaryAttribute(PARTNER_KEY);
        updateInterface(player);
        updateInterface(partner);
        player.sendMessage(partner.getName() + " is no longer your slayer partner.");
        partner.sendMessage(player.getName() + " is no longer your slayer partner.");
    }

    public static void openPartnerInterface(Player player) {
        if (player.getGameMode().isIronMan()) {
            player.sendMessage("Ironmen can not have slayer partners.");
            return;
        }
        Player currentPartner = getPartner(player);
        player.openInterface(InterfaceType.MAIN, 68);
        if (currentPartner == null) {
            player.getPacketSender().sendString(68, 4, "Current partner: " + Color.RED.wrap("(none)"));
            player.getPacketSender().sendString(68, 5,
                    "Use the button to set yourself a Slayer Partner."
                    + "<br><br>"
                    + "Tasks will be assigned based on the " + Color.WHITE.wrap("lower slayer level") + " between you and your partner."
                    + "<br><br>"
                    + "You can dismiss a partner at anytime, but can only partner with someone who either has the "
                            + Color.WHITE.wrap("same task as you") + ", or you " + Color.WHITE.wrap("both have no task") + "."
            );
            player.getPacketSender().sendClientScript(746, "s", "New partner");
        } else {
            int slayerLevel = player.getStats().get(StatType.Slayer).fixedLevel;
            int partnerSlayerLevel = currentPartner.getStats().get(StatType.Slayer).fixedLevel;
            player.getPacketSender().sendString(68, 4, "Current partner: " + Color.WHITE.wrap(currentPartner.getName()) + " (" + partnerSlayerLevel + ")");
            player.getPacketSender().sendString(68, 5,
                    "Your Slayer level is " + Color.WHITE.wrap(slayerLevel > partnerSlayerLevel ? "higher than" : slayerLevel < partnerSlayerLevel ? "lower than" : "equal to")
                            + " your partner's."
                    + "<br><br>"
                    + "When a new task is assigned to either of you, it will be assigned as if you were slayer level " + Color.WHITE.wrap("" + Math.min(slayerLevel, partnerSlayerLevel)) + "."
                    + "<br><br>"
                    + "When a task monster is killed, you and your partner need to be within " + Color.WHITE.wrap(TILE_RANGE + " tiles") + " of it to get credit."

            );
            player.getPacketSender().sendClientScript(746, "s", "Dismiss partner");
        }
    }

    public static int getSlayerLevel(Player player) {
        Player partner = getPartner(player);
        int playerSlayerLevel = player.getStats().get(StatType.Slayer).fixedLevel;
        return partner == null ? playerSlayerLevel : Math.min(playerSlayerLevel, partner.getStats().get(StatType.Slayer).fixedLevel);
    }

    public static int getCombatLevel(Player player) {
        Player partner = getPartner(player);
        int playerCombatLevel = player.getCombat().getLevel();
        return partner == null ? playerCombatLevel : Math.min(playerCombatLevel, partner.getCombat().getLevel());
    }

    public static boolean hasPartner(Player player) {
        return player.hasTemporaryAttribute(PARTNER_KEY);
    }

    public static Player getPartner(Player player) {
        return player.getTemporaryAttribute(PARTNER_KEY);
    }

    static {
        InterfaceHandler.register(68, h -> {
            h.actions[7] = (SimpleAction) player -> {
                if (player.hasTemporaryAttribute(PARTNER_KEY)) {
                    player.dialogue(false,
                            new OptionsDialogue("Are you sure you want to dismiss your partner?",
                                    new Option("Yes, I'm sure", () -> removePartner(player)),
                                    new Option("No")
                            )
                    );
                } else {
                    player.nameInput("Who would you like as your partner?", input -> {
                        for (Player worldPlayer : World.players) {
                            if (worldPlayer.getName().equalsIgnoreCase(input)) {
                                attemptToPartnerPlayer(player, worldPlayer);
                                return;
                            }
                        }
                        player.dialogue(false, new MessageDialogue("That player is not currently online."));
                    });
                }
            };
        });
    }
}
