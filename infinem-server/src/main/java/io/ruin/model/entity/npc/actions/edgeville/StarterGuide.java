package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.RespawnPoint;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.XpCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.locations.home.LocationGuide;
import io.ruin.model.map.Direction;
import io.ruin.network.central.CentralClient;
import io.ruin.utility.Broadcast;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.cache.ItemID.*;

@Slf4j
public class StarterGuide {

	static {
		NPCDefinition.get(307).ignoreOccupiedTiles = true;
        NPCAction.register(306, "talk-to", StarterGuide::optionsDialogue);
        NPCAction.register(306, "reclaim relic items", StarterGuide::reclaimRelicItems);


		LoginListener.register(player -> {
            if (player.hasAttribute("NEW_PLAYER")) {
                XpCounter.select(player, 1);
                CentralClient.sendClanRequest(player.getUserId(), "Help");
                tutorial(player);
            } else {
                //player.getPacketSender().sendMessage("Latest Update: " + LatestUpdate.LATEST_UPDATE_TITLE + "|" + LatestUpdate.LATEST_UPDATE_URL, "", 14);
            }
		});
        ItemAction.registerInventory(757, "read", (player, item) -> Help.open(player));
	}

    private static void moveRespawn(Player player, NPC npc, RespawnPoint respawnPoint) {
        int cost = respawnPoint.getCost();
        String name = StringUtils.getFormattedEnumName(respawnPoint);
        if (player.getInventory().getAmount(995) < cost) {
            player.dialogue(new NPCDialogue(npc, "You need " + NumberUtils.formatNumber(cost) + " coins to change your respawn location to " + name + "."));
            return;
        }
        player.dialogue(
                new NPCDialogue(npc, cost > 0 ? "Do you want to pay " + NumberUtils.formatNumber(cost) + " coins to change your respawn location to " + name + "?" : "Do you want to change your respawn location to " + name + "?"),
                new OptionsDialogue("Change respawn to " + name + "?",
                        new Option("Yes", () -> {
                            player.setRespawnPoint(respawnPoint);
                            if (cost > 0)
                                player.getInventory().remove(995, cost);
                            player.dialogue(
                                    new NPCDialogue(npc, "I've changed your respawn location to " + name + ".")
                            );
                        }),
                        new Option("No")
                )
        );
    }

    private static void respawnDialogue(Player player, NPC npc) {
        List<Option> options = new ArrayList<>();
        List<Option> secondOptions = new ArrayList<>();
        RespawnPoint currentRespawn = player.getRespawnPoint();
        for (RespawnPoint respawnPoint : RespawnPoint.values()) {
            if (respawnPoint != currentRespawn && respawnPoint.canChange(player)) {
                int cost = respawnPoint.getCost();
                if (options.size() >= 4) {
                    secondOptions.add(new Option(StringUtils.getFormattedEnumName(respawnPoint) + " (" + (cost > 0 ? ((cost/1000) + "K") : "Free") + ")", () -> moveRespawn(player, npc, respawnPoint)));
                } else {
                    options.add(new Option(StringUtils.getFormattedEnumName(respawnPoint) + " (" + (cost > 0 ? ((cost/1000) + "K") : "Free") + ")", () -> moveRespawn(player, npc, respawnPoint)));
                }
            }
        }
        secondOptions.add(new Option("Back", () -> respawnDialogue(player, npc)));
        options.add(4, new Option("More...", new OptionsDialogue("Change respawn location?", secondOptions)));
        player.dialogue(new OptionsDialogue("Change respawn location?", options));
    }

    private static void optionsDialogue(Player player, NPC npc) {
        player.dialogue(new NPCDialogue(npc, "Hello " + player.getName() + ", is there something I could assist you with?"),
                new OptionsDialogue(
                        new Option("Location guide", () -> LocationGuide.locate(player, npc)),
                        new Option("Change respawn point",
                                new NPCDialogue(npc, "I can move your respawn location if you would like. Some have a fee associated with them."),
                                new ActionDialogue(() -> respawnDialogue(player, npc))
                        ),
                        new Option("Can I reclaim missing Relic items?", () -> reclaimRelicItems(player, npc)),
                        new Option("Replay tutorial", () -> introCutscene(npc, player))
                )
        );
    }

    private static void reclaimRelicItems(Player player, NPC npc) {
        int result = player.getRelicManager().reclaimRelicItems();
        if (result < 0) {
            player.dialogue(new NPCDialogue(npc, "You need " + Math.abs(result) + " free inventory slots to collect your items."));
        } else if (result == 0) {
            player.dialogue(new NPCDialogue(npc, "You have nothing to collect."));
        } else {
            player.dialogue(new NPCDialogue(npc, "Here you are, try not to lose them this time."));
        }
    }

	@SneakyThrows
	private static void ecoTutorial(Player player) {
		boolean actuallyNew = player.hasAttribute("NEW_PLAYER");
		player.putTemporaryAttribute("TUTORIAL", 1);
		player.startEvent(event -> {
            player.lock(LockType.FULL_ALLOW_LOGOUT);
			player.getMovement().teleport(3237, 3220, 0);
			if (actuallyNew) {
				player.openInterface(InterfaceType.MAIN, Interface.APPEARANCE_CUSTOMIZATION);
				while (player.isVisibleInterface(Interface.APPEARANCE_CUSTOMIZATION)) {
					event.delay(1);
				}
			}
			NPC guide = LocationGuide.GUIDE;
			player.getPacketSender().sendHintIcon(guide);
			player.face(guide);
			if (actuallyNew) {
			    event.waitForDialogue(player);
                player.dialogue(
                        new NPCDialogue(guide, "Before I let you go, let's talk about your game mode."),
                        new NPCDialogue(guide, "Do you want to see the options for Iron Man modes?"),
                        new OptionsDialogue("View Iron Man options?",
                                new Option("Yes", () -> {
                                    GameMode.openSelection(player);
                                    player.unsafeDialogue(new MessageDialogue("Close the interface once you're happy with your selection." +
                                            "<br><br><col=ff0000>WARNING:</col> This is the ONLY chance to choose your Iron Man mode.").hideContinue());
                                }), new Option("No", player::closeDialogue)));
                event.waitForDialogue(player);
                String text = "You want to be a part of the economy, then? Great!";
                if (player.getGameMode() == GameMode.IRONMAN) {
                    text = "Iron Man, huh? Self-sufficiency is quite a challenge, good luck!";
                } else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                    text = "Hardcore?! You only live once... make it count!";
                } else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                    text = "Ultimate Iron Man... Up for quite the challenge, aren't you?";
                }
                player.dialogue(new NPCDialogue(guide, text),
                        new NPCDialogue(guide, "I'll give you a few items to help get you started..."),
                        new NPCDialogue(guide, "There you go, some basic stuff. If you need anything else, remember to check the shops.") {
                            @Override
                            public void open(Player player) {
                                giveEcoStarter(player);
                                player.removeAttribute("NEW_PLAYER");
                                super.open(player);
                            }
                        });
                event.waitForDialogue(player);
                player.getPacketSender().resetHintIcon(true);
                Broadcast.NEW_PLAYER.sendNews(player.getName() + " has just joined " + World.type.getWorldName() + "!");
            }
            player.dialogue(new NPCDialogue(guide,
            "Greetings, " + player.getName() + "! Welcome to " + World.type.getWorldName() + ".<br>" +
            "Would you like me to show you around Lumbridge?"),
            new OptionsDialogue("Play the tutorial?", new Option("Yes!", () -> introCutscene(guide, player)), new Option("No, I know what I'm doing!", () -> {
                player.closeDialogue();
                player.removeTemporaryAttribute("TUTORIAL");
                player.logoutListener = null;
                player.unlock();
            })));
		});
	}

    private static void introCutscene(NPC guide, Player player) {
        guide.startEvent((e) -> {
            player.getPacketSender().sendClientScript(39, "i", 50);
            Config.LOCK_CAMERA.set(player, 2);
            player.getPacketSender().moveCameraToLocation(3234, 3220, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(3238, 3222, 250, 0, 25);
            player.dialogue(new NPCDialogue(guide,
                    "Waystones are located at most major cities in Infinem. " +
                    "You can travel from a waystone to any other waystone that you have unlocked. " +
                    "To unlock a waystone all you have to do is find it and channel it."));
            e.waitForDialogue(player);

            player.getMovement().teleport(3230, 3241, 0);
            player.face(Direction.EAST);
            player.getPacketSender().moveCameraToLocation(3223, 3239, 400, 0, 12);
            player.getPacketSender().turnCameraToLocation(3218, 3237, 200, 0, 30);
            player.dialogue(new NPCDialogue(guide,
                    "Around Lumbridge you will find tutors that offer advice and have shops relating to their skill of expertise. " +
                            "These are the combat tutors who will supply you with any combat equipment you might need starting out."));
            e.waitForDialogue(player);

            player.getMovement().teleport(3211, 3220, 0);
            player.face(Direction.SOUTH);
            player.getPacketSender().moveCameraToLocation(3209, 3223, 400, 0, 12);
            player.getPacketSender().turnCameraToLocation(3210, 3218, 0, 0, 18);
            player.dialogue(new NPCDialogue(guide, "In this room you'll find a bank and a poll booth..."));
            e.waitForDialogue(player);
            player.getPacketSender().moveCameraToLocation(3210, 3220, 400, 0, 12);
            player.getPacketSender().turnCameraToLocation(3211, 3228, 0, 0, 18);
            player.dialogue(new NPCDialogue(guide, "...as well as the Vote and Donation Managers. They will explain the process of voting and using the store and help you claim your rewards."));
            e.waitForDialogue(player);
            if (!player.getGameMode().isIronMan()) {
                player.dialogue(new NPCDialogue(guide, "If you're interested in trading with other players, you can access the Grand Exchange through these Trading posts. They can be found in some other major cities as well."));
                e.waitForDialogue(player);
            }
            player.getPacketSender().moveCameraToLocation(3210, 3221, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(3215, 3220, 0, 0, 18);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "This is Death's Chest, it will hold your items in the event that you die horribly. Hopefully you wont be using it much."));
            e.waitForDialogue(player);
            player.getPacketSender().moveCameraToLocation(3244, 3194, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(3249, 3196, 0, 0, 18);
            e.delay(1);
            player.face(Direction.EAST);
            player.getMovement().teleport(3237, 3220, 0);
            e.delay(2);
            player.getMovement().teleport(3245, 3193, 0);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "In the graveyard, you will find Turael and The Dungeon Hub. The Dungeon Hub can be accessed once you claim any tier 3 relic, and Turael will get you started on your slayer journey."));
            e.waitForDialogue(player);


            Config.LOCK_CAMERA.set(player, 0);
            player.getMovement().teleport(3237, 3220, 0);
            player.getPacketSender().resetCamera();
            player.face(guide);
            player.dialogue(
                    new NPCDialogue(guide, "If you need help finding something, talk to me and I'll point you in the right direction."),
                    new NPCDialogue(guide, "If you have any other questions, there are always helpful users in the help clan chat")
            );
            e.waitForDialogue(player);
            guide.animate(863);
            player.removeTemporaryAttribute("TUTORIAL");
            player.unlock();
            player.clearHintArrow();
        });
    }

    private static void giveEcoStarter(Player player) {
        player.getInventory().add(COINS_995, 1000); // gp
        player.getInventory().add(558, 250); // Mind Rune
        player.getInventory().add(556, 500); // Air Rune
        player.getInventory().add(554, 500); // Fire Rune
        player.getInventory().add(555, 500); // Water Rune
        player.getInventory().add(557, 500); // Earth Rune
        player.getInventory().add(362, 50); // Tuna
        Item bow = new Item(28555);
        bow.setCharges(2000);
        player.getInventory().add(bow); // Starter bow
        Item staff = new Item(28557);
        staff.setCharges(500);
        player.getInventory().add(staff); // Starter staff
        player.getInventory().add(28559, 1); // Starter sword
        player.getInventory().add(Items.BRONZE_AXE, 1);
        player.getInventory().add(Items.BRONZE_PICKAXE, 1);
        player.getInventory().add(Items.TINDERBOX, 1);
        switch (player.getGameMode()) {
            case IRONMAN:
                player.getInventory().add(12810, 1);
                player.getInventory().add(12811, 1);
                player.getInventory().add(12812, 1);
                break;
            case ULTIMATE_IRONMAN:
                player.getInventory().add(12813, 1);
                player.getInventory().add(12814, 1);
                player.getInventory().add(12815, 1);
                break;
            case HARDCORE_IRONMAN:
                player.getInventory().add(20792, 1);
                player.getInventory().add(20794, 1);
                player.getInventory().add(20796, 1);
                break;
            case STANDARD:
                player.getInventory().add(COINS_995, 24000);
                break;
        }
    }

	private static void setDrag(Player player, int drag) {
		player.dragSetting = drag;
	}

	private static void tutorial(Player player) {
        ecoTutorial(player);
	}
}
