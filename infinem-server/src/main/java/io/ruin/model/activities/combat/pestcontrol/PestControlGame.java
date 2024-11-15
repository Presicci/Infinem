package io.ruin.model.activities.combat.pestcontrol;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.World;
import io.ruin.model.combat.npc.PassiveCombat;
import io.ruin.model.content.ActivitySpotlight;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.activities.combat.pestcontrol.PestControl.SQUIRE_ID;
import static io.ruin.model.map.object.actions.impl.Ladder.climb;

/**
 * Represents a game of Pest Control.
 *
 * @author Heaven
 */
public class PestControlGame {

	/**
	 * The interface id accompanying the Pest Control game.
	 */
	private static final int OVERLAY = 408;

	/**
	 * The collection of players involved with this {@link PestControlGame} instance.
	 */
	private final ArrayList<Player> players = new ArrayList<>();

	/**
	 * The generated map for this Pest Control game.
	 */
	private DynamicMap map;

	/**
	 * The Pest Control settings for this game type.
	 */
	private PestControlGameSettings settings;

	/**
	 * The active Pest Portals within this game.
	 */
	private ArrayList<PestPortal> portals = new ArrayList<>();

	/**
	 * The {@link NPC} reference for the void knight within this Pest Control game.
	 */
	private NPC knight;

	/**
	 * The game cycle counter for this activity.
	 */
	private int cycles;

	/**
	 * The time remaining, in ticks, until the activity ends by default.
	 */
	private int lifespan = 2000;

	/**
	 * A flag indicating if the activity has ended safely.
	 */
	private boolean ended;

	/**
	 * A flag indicating if the Void knight has dropped below 150hp.
	 */
	private boolean voidKnightHealthTask = true;

	/**
	 * Constructs a {@link PestControlGame} instance with the specified mode.
	 *
	 * @param settings
	 */
	PestControlGame(PestControlGameSettings settings) {
		this.settings = settings;
	}

	/**
	 * Starts this Pest Control game by setting up the environment.
	 *
	 * @param participants
	 */
	public void start(ArrayList<Player> participants) {
		initMap();
		addNpc(SQUIRE_ID, new Position(2660, 2608, 0));
		NPCDefinition def = NPCDefinition.get(settings.voidKnightId());
		def.combatHandlerClass = PassiveCombat.class;
		def.ignoreMultiCheck = true;
		knight = addNpc(settings.voidKnightId(), new Position(2656, 2592, 0));
		knight.attackNpcListener = (player, npc, message) -> false;
		knight.deathStartListener = (___, ____, _____) -> end(true);
		knight.hitListener = new HitListener().postDefend(hit -> {
			if (knight.getHp() <= 150)
				voidKnightHealthTask = false;
		});
		players.addAll(participants);
		players.forEach(player -> {
			player.pestGame = this;
			player.openInterface(InterfaceType.PRIMARY_OVERLAY, OVERLAY);
			Config.PEST_CONTROL_ACTIVITY.set(player, 26);
			player.getMovement().teleport(map.convertX(2656) + Random.get(3), map.convertY(2610) + Random.get(4));
			player.teleportListener = p -> {
				p.sendMessage("Spells of this kind are not allowed within the battlegrounds.");
				return false;
			};
			player.deathEndListener = (entity, killer, hit) -> {
				if (entity instanceof Player) {
					Player plr = (Player) entity;
					if (plr.pestGame != null) {
						plr.getMovement().teleport(map.convertX(2656) + Random.get(3), map.convertY(2610) + Random.get(4));
					} else {
						plr.getMovement().teleport(settings.exitTile());
					}
				}
			};
			player.logoutListener = new LogoutListener().onLogout(this::leave);
			player.dialogue(new NPCDialogue(SQUIRE_ID,
					"You must defend the Void Knight while the portals are<br>unsummoned. " +
							"The ritual takes twenty minutes though,<br>so you can help out by destroying them yourselves!<br>" +
							"Now GO GO GO!"
			));
		});

		// Register our portals.
		registerPortal(new PestPortal(this, "Purple", 21, 1743, 1747), 2628, 2591);
		registerPortal(new PestPortal(this, "Blue", 22, 1744, 1748), 2680, 2588);
		registerPortal(new PestPortal(this, "Yellow", 23, 1745, 1749), 2669, 2570);
		registerPortal(new PestPortal(this, "Red", 24, 1746, 1750), 2645, 2569);

		//Register ladders
		//North-west tower
		ObjectAction.register(14296, map.convertX(2644), map.convertY(2601), 0, "climb", ((player, obj) -> {
			//North-west tower
			/*if (map.convertX(player.getAbsX()) == map.convertX(2643))
				climb(player, map.convertX(2645), map.convertY(2601), 0, false, true, false);
			else
				climb(player, map.convertX(2643), map.convertY(2601), 0, true, true, false);*/
		}));
		ObjectAction.register(14296, map.convertX(2669), map.convertY(2601), 0, "climb", ((player, obj) -> {
			//North-east tower
			/*if (map.convertX(player.getAbsX()) == map.convertX(2670))
				climb(player, map.convertX(2668), map.convertY(2601), 0, false, true, false);
			else
				climb(player, map.convertX(2670), map.convertY(2601), 0, true, true, false);*/
		}));
		ObjectAction.register(14296, map.convertX(2666), map.convertY(2586), 0, "climb", ((player, obj) -> {
			//South-east tower
			/*if (map.convertY(player.getAbsY()) == map.convertY(2585))
				climb(player, map.convertX(2666), map.convertY(2587), 0, true, true, false);
			else
				climb(player, map.convertX(2666), map.convertY(2585), 0, false, true, false);*/
		}));
		ObjectAction.register(14296, map.convertX(2647), map.convertY(2586), 0, "climb", ((player, obj) -> {
			//South-east tower
			/*if (map.convertY(player.getAbsX()) == map.convertY(2585))
				climb(player, map.convertX(2647), map.convertY(2587), 0, true, true, false);
			else
				climb(player, map.convertX(2647), map.convertY(2585), 0, false, true, false);*/
		}));


		// Force update overlay after initialization.
		updateOverlay();

		// Start the event processor for this activity.
		World.startEvent(e -> {
			while (!ended) {
				e.delay(2);
				cycles++;
				pulse();
			}
		});
	}

	/**
	 * Ends this Pest Control game and disposes of resources respectfully.
	 *
	 * @param failed A flag indicating if the participants of this game failed to complete the activity.
	 */
	private void end(boolean failed) {
		players.forEach(p -> {
			int rewardedPoints = settings.points() * 2;
			if (World.doublePest) {
				rewardedPoints *= 4;
			} else if (ActivitySpotlight.isActive(ActivitySpotlight.DOUBLE_PEST_CONTROL_POINTS)) {
				rewardedPoints *= 2;
			}
			p.lock();
			p.clearHits();
			Position tile = settings.exitTile();
			p.getMovement().teleport(tile.getX(), tile.getY());
			p.unlock();
			if (failed) {
				p.dialogue(new NPCDialogue(SQUIRE_ID,
						"The void knight has fallen therefore you were not</br> " +
								"presented with any points."));
			} else {
				if (Config.PEST_CONTROL_ACTIVITY.get(p) <= 12) {
					p.dialogue(new NPCDialogue(SQUIRE_ID,
							"The knights noticed your lack of zeal in that battle and have not</br>" +
									"presented you with any points."));
				} else {
				    switch(settings) {
                        case NOVICE:
							p.incrementNumericAttribute("PEST_NOVICE_WINS", 1);
							p.getTaskManager().doLookupByUUID(440, 1);	// Complete a Game of Novice Pest Control
                            break;
                        case INTERMEDIATE:
							p.incrementNumericAttribute("PEST_INTERMEDIATE_WINS", 1);
							p.getTaskManager().doLookupByUUID(454, 1);	// Complete a Game of Intermediate Pest Control
                            break;
                        case VETERAN:
							p.incrementNumericAttribute("PEST_VETERAN_WINS", 1);
							p.getTaskManager().doLookupByUUID(471, 1);	// Complete a Game of Veteran Pest Control
							if (voidKnightHealthTask)
								p.getTaskManager().doLookupByUUID(470, 1);	// Keep the Veteran Void Knight Above 150 Hitpoints
                            break;
                    }
					p.incrementNumericAttribute("PEST_POINTS", rewardedPoints);
					p.getInventory().addOrDrop(new Item(COINS_995, Random.get(3000, 7000)));
					p.dialogue(new NPCDialogue(SQUIRE_ID,
							"Congratulations! You managed to destroy all the portals!<br>" +
									"We've awarded you " + rewardedPoints + " Void Knight Commendation<br>points." +
									"Please also accept these coins as a reward."));
				}
			}

			clearActivityOf(p);
		});

		cycles = 0;
		knight = null;
		portals.clear();
		portals = null;
		players.clear();
		map.destroy();
		map = null;
		ended = true;
	}

	/**
	 * A method called every update at the rate defined in our event processor.
	 */
	private void pulse() {
		lifespan -= 2;
		updateOverlay();
		if ((players.isEmpty() || lifespan == 0) && !ended) {
			end(true);
			return;
		}

		if (cycles % 6 == 0) {
			players.forEach(p -> {
				if (Config.PEST_CONTROL_ACTIVITY.get(p) > 0)
					Config.PEST_CONTROL_ACTIVITY.decrement(p, 1);
			});
		}
		int portalSpawnFrequency = players.size() < 4 ? ((4 - players.size()) * 3) + 18 : 18;
		if (cycles % portalSpawnFrequency == 0) {
			portals.stream().filter(PestPortal::alive).forEach(PestPortal::spawnPests);
			//portals.stream().filter(PestPortal::alive).filter(PestPortal::shieldDropped).forEach(PestPortal::spawnPests);
		}

		int knightSpawnFrequency = players.size() < 4 ? ((4 - players.size()) * 6) + 14 : 14;
		if (cycles % knightSpawnFrequency == 0) {
			spawnKnightPests();
		}

		if (cycles == 12 || (cycles - 12) % 25 == 0) {
			dropRandomShield();
		}
	}

	/**
	 * Actions invoked upon a player leaving this Pest Control game.
	 *
	 * @param player
	 */
	public void leave(Player player) {
		clearActivityOf(player);
		players.remove(player);
		player.getMovement().teleport(settings.exitTile());
	}

	/**
	 * Updates the game interface with all information relevant to this Pest Control game.
	 */
	private void updateOverlay() {
		players.forEach(p -> {
			int knightHp = knight.getHp();
			p.getPacketSender().sendString(OVERLAY, 5, displayTimeFor(lifespan));
			p.getPacketSender().sendString(OVERLAY, 6, knightHp > 70 ? "<col=00ff00>" + knightHp : "<col=ff0000>" + knightHp);
			p.getPacketSender().sendString(OVERLAY, 7, p.pestActivityScore + "");
			portals.forEach(portal -> {
				if (portal.dead) {
					p.getPacketSender().sendString(OVERLAY, portal.widgetIdx, "<col=ff0000>0");
				} else {
					p.getPacketSender().sendString(OVERLAY, portal.widgetIdx, "<col=00ff00>" + portal.link().getHp());
				}
				p.getPacketSender().setHidden(OVERLAY, ((portal.widgetIdx - 21) * 2) + 25, portal.shieldDropped);
			});
		});
	}

	/**
	 * Resets the players state by removing all listeners relevant to this activity.
	 */
	private void clearActivityOf(Player player) {
		player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
		Config.PEST_CONTROL_ACTIVITY.set(player, 0);
		player.pestGame = null;
		player.pestActivityScore = 0;
		player.teleportListener = null;
		player.deathEndListener = null;
		player.logoutListener = null;
		player.getCombat().restore();
	}

	/**
	 * Initializes & creates the dynamic map for this Pest Control game.
	 */
	private void initMap() {
		map = new DynamicMap().build(10536, 3);
	}

	/**
	 * Registers a {@link PestPortal} within this game.
	 *
	 * @param portal The pest portal instance.
	 * @param x      The x coordinate for this spawn point.
	 * @param y      The y coordinate for this spawn point.
	 */
	private void registerPortal(PestPortal portal, int x, int y) {
		portal.spawn(x, y);
		portal.link().deathEndListener = (entity, killer, killHit) -> {
			portal.link().remove();
			portal.dead = true;
			updateOverlay();
			if (portals.stream().allMatch(p -> p.dead)) {
				end(false);
			}
		};
	}

	/**
	 * Randomly chooses a portal out of the collection of portals that are alive and drops its shield.
	 */
	private void dropRandomShield() {
		Collections.shuffle(portals);
		PestPortal portal = portals.stream().filter(PestPortal::alive).filter(PestPortal::shieldActive).findAny().orElse(null);
		if (portal != null) {
			portal.dropShield();
			knight.forceText("The " + portal.name() + " Portal's Shield, has fallen.");
			updateOverlay();
		}
	}

	/**
	 * Spawns pests near the void knight to add an extra layer of difficulty.
	 */
	private void spawnKnightPests() {
		int[] pests = settings.pests();
		Position knightPos = knight.getPosition();
		Bounds knightBounds = new Bounds(knightPos.getX() - 2, knightPos.getY() - 2, knightPos.getX() + 3, knightPos.getY() + 3, 0);
		List<Position> tiles = new ArrayList<>();
		knightBounds.forEachPos(tiles::add);
		Collections.shuffle(tiles);
		NPC pest = new NPC(pests[Random.get(pests.length - 1)]).spawn(Random.get(tiles));
		pest.getCombat().setTarget(knight);
		pest.getCombat().faceTarget();
	}

	protected void healKnight() {
		knight.incrementHp(50);
		updateOverlay();
	}

	/**
	 * Adds a {@link NPC} to this Pest Control game.
	 *
	 * @param id
	 * @param originalTile
	 * @return
	 */
	private NPC addNpc(int id, Position originalTile) {
		NPC npc = new NPC(id).spawn(map.convertX(originalTile.getX()), map.convertY(originalTile.getY()), 0);
		map.addNpc(npc);
		return npc;
	}

	/**
	 * Displays the time left for this activity.
	 *
	 * @param ticks
	 * @return
	 */
	private String displayTimeFor(int ticks) {
		int mins = ticks / 100;
		if (mins > 0) {
			return mins + " min";
		}

		int secs = (int) (ticks * 0.6);
		return secs + " sec";
	}

	/**
	 * Supplies the {@link DynamicMap} instance for this game.
	 *
	 * @return
	 */
	public DynamicMap map() {
		return map;
	}

	/**
	 * Supplies the {@link PestPortal}s active within this game.
	 *
	 * @return
	 */
	public ArrayList<PestPortal> portals() {
		return portals;
	}

	/**
	 * Supplies if this activity has ended.
	 *
	 * @return
	 */
	public boolean ended() {
		return ended;
	}

	/**
	 * Supplies the collection of players within this activity.
	 *
	 * @return
	 */
	public ArrayList<Player> players() {
		return players;
	}

	/**
	 * Supplies the settings for this game.
	 *
	 * @return
	 */
	public PestControlGameSettings settings() {
		return settings;
	}
}
