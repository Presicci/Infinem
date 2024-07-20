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
import io.ruin.model.entity.player.XpMode;
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
import io.ruin.model.locations.home.NPCLocator;
import io.ruin.model.map.Direction;
import io.ruin.model.stat.StatType;
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


		LoginListener.register(player -> {
            if (player.newPlayer) {
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
                        new Option("Replay tutorial", () -> introCutscene(npc, player)),
                        new Option("Change respawn point",
                                new NPCDialogue(npc, "I can move your respawn location if you would like. Some have a fee associated with them."),
                                new ActionDialogue(() -> respawnDialogue(player, npc))
                        )
                )
        );
    }

	@SneakyThrows
	private static void ecoTutorial(Player player) {
		boolean actuallyNew = player.newPlayer;
		player.inTutorial = true;
		player.startEvent(event -> {
            player.lock(LockType.FULL_ALLOW_LOGOUT);
			player.getMovement().teleport(3237, 3220, 0);
			if (actuallyNew) {
				player.openInterface(InterfaceType.MAIN, Interface.APPEARANCE_CUSTOMIZATION);
				while (player.isVisibleInterface(Interface.APPEARANCE_CUSTOMIZATION)) {
					event.delay(1);
				}
			}
			NPC guide = NPCLocator.GUIDE;
			player.getPacketSender().sendHintIcon(guide);
			player.face(guide);
			boolean startTutorial = false;
			if (actuallyNew) {
                XpMode.setXpMode(player, XpMode.NORMAL);
			    event.waitForDialogue(player);
			    if (player.xpMode.equals(XpMode.NORMAL)) {
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
                }
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
                                player.newPlayer = false;
                                super.open(player);
                            }
                        });
                event.waitForDialogue(player);
                player.getPacketSender().resetHintIcon(true);
                Broadcast.WORLD.sendNews(player.getName() + " has just joined " + World.type.getWorldName() + "!");
                startTutorial = true;
            } else {
                startTutorial = true;
            }
			if (startTutorial) {

                player.dialogue(new NPCDialogue(guide,
                "Greetings, " + player.getName() + "! Welcome to " + World.type.getWorldName() + ".<br>" +
                "Would you like to a run down on how the server works?"),
                new OptionsDialogue("Play the tutorial?", new Option("Yes!", () -> introCutscene(guide, player)), new Option("No, I know what I'm doing!", () -> {
                    player.closeDialogue();
                    player.inTutorial = false;
                    player.logoutListener = null;
                    player.setTutorialStage(0);
                    player.unlock();
                })));
            }
		});
	}

    private static void introCutscene(NPC guide, Player player) {
        guide.startEvent((e) -> {
            player.getPacketSender().sendClientScript(39, "i", 100);
            Config.LOCK_CAMERA.set(player, 1);
            player.getPacketSender().moveCameraToLocation(3234, 3220, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(3238, 3222, 250, 0, 25);
            player.dialogue(new NPCDialogue(guide,
                    "Waystones are located at most major cities in Infinem. " +
                    "When you channel a waystone, you can travel to any other waystone that you have unlocked. " +
                    "To unlock a waystone all you have to do is find it and channel it."));
            e.waitForDialogue(player);

            player.getMovement().teleport(3230, 3241, 0);
            player.face(Direction.EAST);
            player.getPacketSender().moveCameraToLocation(3223, 3239, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(3218, 3238, 400, 0, 30);
            player.dialogue(new NPCDialogue(guide,
                    "Around Lumbridge you will find tutors that offer advice and have shops relating to their skill of expertise. " +
                            "These are the combat tutors who will supply you with any combat equipment you might need starting out."));
            e.waitForDialogue(player);

            player.getMovement().teleport(3211, 3220, 0);
            player.face(Direction.SOUTH);
            player.getPacketSender().moveCameraToLocation(3214, 3221, 800, 0, 12);
            player.getPacketSender().turnCameraToLocation(3209, 3222, 0, 0, 18);
            if (!player.getGameMode().isIronMan()) {
                player.dialogue(
                        new NPCDialogue(guide, "In this room you'll find a bank and a poll booth. You'll also find the Wise Old Man who will help you vote and claim voting rewards, and __ who will help you visit the shop and claim anything you might have purchased."),
                        new NPCDialogue(guide, "If you're interested in trading with other players the clerks here will help you access the Trading Post."));
            } else {
                player.dialogue(
                        new NPCDialogue(guide, "In this room you'll find a bank and a poll booth. You'll also find the Wise Old Man who will help you vote and claim voting rewards, and __ who will help you visit the shop and claim anything you might have purchased."
                ));
            }
            e.waitForDialogue(player);
            player.getPacketSender().moveCameraToLocation(3213, 3221, 800, 0, 12);
            player.getPacketSender().turnCameraToLocation(3216, 3221, 0, 0, 18);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "This is Death's Chest, it will hold your items in the event that you die horribly. Hopefully you wont be using it much."));
            e.waitForDialogue(player);
            Config.LOCK_CAMERA.set(player, 0);
            player.getMovement().teleport(3237, 3220, 0);
            player.getPacketSender().resetCamera();
            player.setTutorialStage(1);
            player.face(guide);
            player.dialogue(new NPCDialogue(guide,
                    "If you have any other questions, there are always helpful users in the help clan chat"));
            e.waitForDialogue(player);
            guide.animate(863);
            player.inTutorial = false;
            player.unlock();
            player.setTutorialStage(0);
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

	private static NPC find(Player player, int id) {
		for (NPC n : player.localNpcs()) {
			if (n.getId() == id)
				return n;
		}
		throw new IllegalArgumentException();
	}

	private static void setDrag(Player player) {
		player.dialogue(
				new OptionsDialogue("What drag setting would you like to use?",
						new Option("5 (OSRS) (2007) Drag", () -> setDrag(player, 5)),
						new Option("10 (Pre-EoC) (2011) Drag", () -> setDrag(player, 10))
				)
		);
	}

	private static void setDrag(Player player, int drag) {
		player.dragSetting = drag;
	}

	private static void tutorial(Player player) {
        ecoTutorial(player);
	}

	private static void addPKModeItemToBank(Player player) {
        player.getBank().add(19625, 5); // Home teleport
        player.getBank().add(2550, 3); // Recoils
        player.getBank().add(385, 125); // Sharks
        player.getBank().add(3144, 50); // Karambwans
        player.getBank().add(2436, 5); // attk
        player.getBank().add(2440, 5); // str
        player.getBank().add(2444, 5); // range
        player.getBank().add(3024, 5); // restore
//Next Line
        player.getBank().add(6685, 10); // brew
        player.getBank().add(560, 2250); // Death runes
        player.getBank().add(565, 1000); // Blood runes
        player.getBank().add(561, 300); // Nature runes
        player.getBank().add(145, 1); // atk
        player.getBank().add(157, 1); // str
        player.getBank().add(169, 1); // range
        player.getBank().add(3026, 1); // restore
//Next Line
        player.getBank().add(6687, 1); // brew
        player.getBank().add(9075, 400); // Astral runes
        player.getBank().add(555, 6000); // Water runes
        player.getBank().add(557, 1000); // Earth runes
        player.getBank().add(147, 1); // atk
        player.getBank().add(159, 1); // str
        player.getBank().add(171, 1); // range
        player.getBank().add(3028, 1); // restore
//Next Line
        player.getBank().add(6689, 1); // brew
        player.getBank().add(7458, 100); // mithril gloves for pures
        player.getBank().add(7462, 100); // gloves
        player.getBank().add(3842, 100); // god book
        player.getBank().add(149, 1); // atk
        player.getBank().add(161, 1); // str
        player.getBank().add(173, 1); // range
        player.getBank().add(3030, 1); // restore
//Next Line
        player.getBank().add(6691, 1); // brew
        player.getBank().add(9144, 500); // bolts
        player.getBank().add(2503, 5); // hides
        player.getBank().add(4099, 5); // Mystic
        player.getBank().add(2414, 100); // zamy god cape
        player.getBank().add(10828, 5); // neit helm
        player.getBank().add(4587, 5); // Scim
        player.getBank().add(1163, 3); // rune full helm
//Next Line
        player.getBank().add(562, 50); // Chaos rune
        player.getBank().add(892, 400); // rune arrows
        player.getBank().add(2497, 5); // hides
        player.getBank().add(4101, 5); // Mystic
        player.getBank().add(4675, 5); // ancient staff
        player.getBank().add(1201, 5); // rune
        player.getBank().add(5698, 5); // dagger
        player.getBank().add(1127, 3); // rune pl8
//Next Line
        player.getBank().add(563, 50); // law rune
        player.getBank().add(9185, 5); // crossbow
        player.getBank().add(10499, 100); // avas
        player.getBank().add(4103, 5); // Mystic
        player.getBank().add(4107, 5); // Mystic
        player.getBank().add(3105, 5); // climbers
        player.getBank().add(11978, 3); // glory(6)
        player.getBank().add(1079, 3); // rune legs
//Next Line
        player.getBank().add(1215, 2); // dagger unpoisoned
        player.getBank().add(3751, 2); // zerker helm
        player.getBank().add(1093, 2); // rune


        // Give the players PK stats
        player.getStats().get(StatType.Attack).set(99);
        player.getStats().get(StatType.Strength).set(99);
        player.getStats().get(StatType.Defence).set(99);
        player.getStats().get(StatType.Hitpoints).set(99);
        player.getStats().get(StatType.Magic).set(99);
        player.getStats().get(StatType.Ranged).set(99);
        player.getStats().get(StatType.Prayer).set(99);
        player.getCombat().updateLevel();
    }

}
