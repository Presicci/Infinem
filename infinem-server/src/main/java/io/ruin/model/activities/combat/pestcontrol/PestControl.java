package io.ruin.model.activities.combat.pestcontrol;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;

import static io.ruin.model.inter.Interface.PEST_CONTROL_REWARDS;
import static io.ruin.model.inter.Interface.PEST_CONTROL_SCOREBOARD;

/**
 * The manager for all things relevant to Pest Control.
 * @author Heaven
 */
public class PestControl {

	/**
	 * The {@link NPC} id for the Squire within the battlegrounds.
	 */
	public static final int SQUIRE_ID = 2949;

	/**
	 * The novice {@link PestControlBoat} instance.
	 */
	public static final PestControlBoat NOVICE_LANDER = new PestControlBoat(PestControlGameSettings.NOVICE);

	/**
	 * The intermediate {@link PestControlBoat} instance.
	 */
	private static final PestControlBoat INTERMEDIATE_LANDER = new PestControlBoat(PestControlGameSettings.INTERMEDIATE);

	/**
	 * The veteran {@link PestControlBoat} instance.
	 */
	private static final PestControlBoat VETERAN_LANDER = new PestControlBoat(PestControlGameSettings.VETERAN);

	static {
        ObjectAction.register(27780, 1, (player, __) -> displayScoreBoard(player));
		//NPCAction.register(1755, 3, (player, npc) -> displayRewardsShop(player));
		ObjectAction.register(14315, 1, NOVICE_LANDER::join);
		ObjectAction.register(25631, 1, INTERMEDIATE_LANDER::join);
		ObjectAction.register(25632, 1, VETERAN_LANDER::join);
		ObjectAction.register(14314, 1, (p, __) -> {
			NOVICE_LANDER.leave(p);
			p.getMovement().teleport(NOVICE_LANDER.settings().exitTile());
		});
		ObjectAction.register(25629, 1, (p, __) -> {
			INTERMEDIATE_LANDER.leave(p);
			p.getMovement().teleport(INTERMEDIATE_LANDER.settings().exitTile());
		});
		ObjectAction.register(25630, 1, (p, __) -> {
			VETERAN_LANDER.leave(p);
			p.getMovement().teleport(VETERAN_LANDER.settings().exitTile());
		});
		NPCAction.register(2949, 1, (player, squire) -> {
			if (player.pestGame == null)
				return;

			player.dialogue(
					new NPCDialogue(squire.getId(), "Be quick, we're under attack!"),
					new OptionsDialogue("Select an Option", new Option("I'd like to leave.", () -> player.pestGame.leave(player)), new Option("Nevermind."))
			);
		});
		NPCAction.register(2949, 3, (player, squire) -> {
			if (player.pestGame == null)
				return;

			player.pestGame.leave(player);
		});

	}

    /**
     * Opens the scoreboard and displays the players novice, intermediate, and novice win amounts
     * @param player The player being shown the scoreboard
     */
    private static void displayScoreBoard(Player player) {
        player.getPacketSender().sendString(PEST_CONTROL_SCOREBOARD, 8, String.valueOf(player.getAttributeIntOrZero("PEST_NOVICE_WINS")));
        player.getPacketSender().sendString(PEST_CONTROL_SCOREBOARD, 9, String.valueOf(player.getAttributeIntOrZero("PEST_INTERMEDIATE_WINS")));
        player.getPacketSender().sendString(PEST_CONTROL_SCOREBOARD, 10, String.valueOf(player.getAttributeIntOrZero("PEST_VETERAN_WINS")));
        player.openInterface(InterfaceType.MAIN, PEST_CONTROL_SCOREBOARD);
    }
}
