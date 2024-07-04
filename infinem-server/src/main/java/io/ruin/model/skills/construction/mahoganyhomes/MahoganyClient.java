package io.ruin.model.skills.construction.mahoganyhomes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.Consumable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Color;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2024
 */
@Getter
public enum MahoganyClient {
    // East Ardy
    JESS(10421, "her",
			"go see " + Color.RED.wrap("Jess") + ", upstairs of the building south of the church in East " + Color.RED.wrap("Ardougne"),
            new MahoganyObject(40171, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40172, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40173, MahoganyHotspotType.CABINET),
            new MahoganyObject(40174, MahoganyHotspotType.CABINET),
            new MahoganyObject(40175, MahoganyHotspotType.BED),
            new MahoganyObject(40176, MahoganyHotspotType.TABLE),
            new MahoganyObject(40177, MahoganyHotspotType.GRANDFATHER_CLOCK),
            new MahoganyObject(40299, MahoganyHotspotType.BATH)
    ),
    NOELLA(10419, "her",
			"go see " + Color.RED.wrap("Noella") + ", north of East " + Color.RED.wrap("Ardougne market"),
            new MahoganyObject(40156, MahoganyHotspotType.DRESSER),
            new MahoganyObject(40157, MahoganyHotspotType.CUPBOARD),
            new MahoganyObject(40158, MahoganyHotspotType.HAT_STAND),
            new MahoganyObject(40159, MahoganyHotspotType.MIRROR),
            new MahoganyObject(40160, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40161, MahoganyHotspotType.TABLE),
            new MahoganyObject(40162, MahoganyHotspotType.TABLE),
            new MahoganyObject(40163, MahoganyHotspotType.GRANDFATHER_CLOCK)
    ),
    ROSS(10420, "him",
			"go see " + Color.RED.wrap("Ross") + ", north of the church in East " + Color.RED.wrap("Ardougne"),
            new MahoganyObject(40164, MahoganyHotspotType.RANGE),
            new MahoganyObject(40165, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40166, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40167, MahoganyHotspotType.BED),
            new MahoganyObject(40168, MahoganyHotspotType.HAT_STAND),
            new MahoganyObject(40169, MahoganyHotspotType.SMALL_BED),
            new MahoganyObject(40170, MahoganyHotspotType.MIRROR)
    ),

    // Falador
    LARRY(10418, "him",
			"go see " + Color.RED.wrap("Larry") + ", north of the fountain in " + Color.RED.wrap("Falador"),
            new MahoganyObject(40297, MahoganyHotspotType.RANGE),
            new MahoganyObject(40095, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40096, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40097, MahoganyHotspotType.TABLE),
            new MahoganyObject(40298, MahoganyHotspotType.HAT_STAND),
            new MahoganyObject(40098, MahoganyHotspotType.TABLE),
            new MahoganyObject(40099, MahoganyHotspotType.GRANDFATHER_CLOCK)
    ),
    NORMAN(3266, "him",
			"go see " + Color.RED.wrap("Norman") + ", south of the fountain in " + Color.RED.wrap("Falador"),
            new MahoganyObject(40296, MahoganyHotspotType.RANGE),
            new MahoganyObject(40089, MahoganyHotspotType.GRANDFATHER_CLOCK),
            new MahoganyObject(40090, MahoganyHotspotType.TABLE),
            new MahoganyObject(40091, MahoganyHotspotType.BED),
            new MahoganyObject(40092, MahoganyHotspotType.BOOKCASE),
            new MahoganyObject(40093, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40094, MahoganyHotspotType.SMALL_TABLE)
    ),
    TAU(10417, "her",
			"go see " + Color.RED.wrap("Tau") + ", south east of the fountain in " + Color.RED.wrap("Falador"),
            new MahoganyObject(40083, MahoganyHotspotType.SINK),
            new MahoganyObject(40084, MahoganyHotspotType.TABLE),
            new MahoganyObject(40085, MahoganyHotspotType.TABLE),
            new MahoganyObject(40086, MahoganyHotspotType.CUPBOARD),
            new MahoganyObject(40087, MahoganyHotspotType.SHELVES),
            new MahoganyObject(40088, MahoganyHotspotType.SHELVES),
            new MahoganyObject(40295, MahoganyHotspotType.HAT_STAND)
    ),

    // Hosidius
    BARBARA(10424, "her",
			"go see " + Color.RED.wrap("Barbara") + ", south of " + Color.RED.wrap("Hosidius") + ", near the mill",
            new MahoganyObject(40011, MahoganyHotspotType.GRANDFATHER_CLOCK),
            new MahoganyObject(40293, MahoganyHotspotType.RANGE),
            new MahoganyObject(40012, MahoganyHotspotType.TABLE),
            new MahoganyObject(40294, MahoganyHotspotType.DRAWER),
            new MahoganyObject(40013, MahoganyHotspotType.SMALL_BED),
            new MahoganyObject(40014, MahoganyHotspotType.CHAIR),
            new MahoganyObject(40015, MahoganyHotspotType.CHAIR)
    ),
    LEELA(10423, "her",
			"go see " + Color.RED.wrap("Leela") + ", east of the town market in " + Color.RED.wrap("Hosidius"),
            new MahoganyObject(40007, MahoganyHotspotType.SMALL_TABLE),
            new MahoganyObject(40008, MahoganyHotspotType.SMALL_TABLE),
            new MahoganyObject(40290, MahoganyHotspotType.SINK),
            new MahoganyObject(40291, MahoganyHotspotType.BED),
            new MahoganyObject(40009, MahoganyHotspotType.TABLE),
            new MahoganyObject(40010, MahoganyHotspotType.MIRROR),
            new MahoganyObject(40292, MahoganyHotspotType.CUPBOARD)
    ),
    MARIAH(10422, "her",
			"go see " + Color.RED.wrap("Mariah") + ", west of the estate agents in " + Color.RED.wrap("Hosidius"),
            new MahoganyObject(40002, MahoganyHotspotType.TABLE),
            new MahoganyObject(40287, MahoganyHotspotType.SINK),
            new MahoganyObject(40003, MahoganyHotspotType.SHELVES),
            new MahoganyObject(40288, MahoganyHotspotType.CUPBOARD),
            new MahoganyObject(40004, MahoganyHotspotType.SMALL_BED),
            new MahoganyObject(40005, MahoganyHotspotType.SMALL_TABLE),
            new MahoganyObject(40006, MahoganyHotspotType.SMALL_TABLE),
            new MahoganyObject(40289, MahoganyHotspotType.HAT_STAND)
    ),


    // Varrock
    BOB(10414, "him",
			"go see " + Color.RED.wrap("Bob") + ", in north-east " + Color.RED.wrap("Varrock") + ", opposite the church",
            new MahoganyObject(39981, MahoganyHotspotType.BIG_TABLE),
            new MahoganyObject(39982, MahoganyHotspotType.GRANDFATHER_CLOCK),
            new MahoganyObject(39983, MahoganyHotspotType.CABINET),
            new MahoganyObject(39984, MahoganyHotspotType.CABINET),
            new MahoganyObject(39985, MahoganyHotspotType.BOOKCASE),
            new MahoganyObject(39986, MahoganyHotspotType.BOOKCASE),
            new MahoganyObject(39987, MahoganyHotspotType.WARDROBE),
            new MahoganyObject(39988, MahoganyHotspotType.DRAWER)
    ),
    JEFF(10415, "him",
			"go see " + Color.RED.wrap("Jeff") + " in the middle of " + Color.RED.wrap("Varrock") + ", west of the museum",
            new MahoganyObject(39989, MahoganyHotspotType.TABLE),
            new MahoganyObject(39990, MahoganyHotspotType.BOOKCASE),
            new MahoganyObject(39991, MahoganyHotspotType.SHELVES),
            new MahoganyObject(39992, MahoganyHotspotType.BED),
            new MahoganyObject(39993, MahoganyHotspotType.DRAWER),
            new MahoganyObject(39994, MahoganyHotspotType.DRESSER),
            new MahoganyObject(39995, MahoganyHotspotType.MIRROR),
            new MahoganyObject(39996, MahoganyHotspotType.CHAIR)
    ),
    SARAH(10416, "her",
			"go see " + Color.RED.wrap("Sarah") + " along the south wall of " + Color.RED.wrap("Varrock"),
            new MahoganyObject(39997, MahoganyHotspotType.TABLE),
            new MahoganyObject(39998, MahoganyHotspotType.SMALL_BED),
            new MahoganyObject(39999, MahoganyHotspotType.DRESSER),
            new MahoganyObject(40000, MahoganyHotspotType.SMALL_TABLE),
            new MahoganyObject(40286, MahoganyHotspotType.RANGE),
            new MahoganyObject(40001, MahoganyHotspotType.SHELVES)
    );

	private final int npcId;
	private final String pronoun, location;
    private final MahoganyObject[] objects;

    MahoganyClient(int npcId, String pronoun, String location, MahoganyObject... objects) {
		this.npcId = npcId;
		this.pronoun = pronoun;
		this.location = location;
        this.objects = objects;
    }

	public void dialogue(Player player) {
		MahoganyClient completedClient = MahoganyHomes.getCompletedClient(player);
		if (completedClient == this) {
			player.dialogue(
					new PlayerDialogue("I've finished with the work you wanted."),
					new ActionDialogue(() -> {
						MahoganyHomes.complete(player);
						player.dialogue(
								new NPCDialogue(npcId, "Thank you so much! Would you like a cup of tea before you go?"),
								new OptionsDialogue("Take tea?",
										new Option("Yes, I'd love a cuppa.", new PlayerDialogue("Yes, I'd love a cuppa."), new ActionDialogue(() -> {
											Consumable.animEat(player);
											player.getMovement().restoreEnergy(100);
											player.forceText("Aaah, nothing like a nice cupppa tea!");
										})),
										new Option("No thanks.", new PlayerDialogue("No thanks, I better be off."))
								)
						);
					})
			);
		} else {
			MahoganyDifficulty difficulty = MahoganyHomes.getDifficulty(player);
			List<Dialogue> dialogueList = new ArrayList<>();
			dialogueList.add(new PlayerDialogue("Hey, I'm your contractor from Mahogany Homes, what did you want done?"));
			Map<MahoganyHotspotType, Integer> typeCount = new HashMap<>();
			for (MahoganyObject object : objects) {
				if (!object.isBuilt(player)) {
					typeCount.merge(object.getType(), 1, Integer::sum);
				}
			}
			for (MahoganyHotspotType type : typeCount.keySet()) {
				int count = typeCount.get(type);
				if (type.isRepairable()) {
					dialogueList.add(new NPCDialogue(npcId, "I'd like my "
							+ type.getName()
							+ " fixed."));
				} else {
					dialogueList.add(new NPCDialogue(npcId, "I'd like "
							+ (count == 2 ? "two " : count == 3 ? "three " : (type == MahoganyHotspotType.SHELVES ? "some " : "a "))
							+ (difficulty == null ? "" : difficulty.getPlankName())
							+ " "
							+ type.getName()
							+ (count > 1 && type != MahoganyHotspotType.SHELVES ? "s" : "")
							+ " built."));
				}
			}
			player.dialogue(dialogueList.toArray(new Dialogue[0]));
		}
	}

	private void objectInteract(Player player, MahoganyObject object, int option) {
		if (!player.hasAttribute(MahoganyHomes.CLIENT_KEY) || !player.getAttributeOrDefault(MahoganyHomes.CLIENT_KEY, "").equals(name())) {
			player.dialogue(new NPCDialogue(npcId, "I don't need any work done right now, thank you."));
			return;
		}
		MahoganyHotspot hotspot = MahoganyHotspot.getByObjectId(object.getObjectId());
		Config hotspotVarbit = hotspot.getVarbit();
		switch (hotspotVarbit.get(player)) {
			case 1:		// Repair
				if (option != 3) {
					player.sendMessage("Error?");
					return;
				}
				object.repair(player);
				break;
			case 3:		// Remove
				if (option != 2) {
					player.sendMessage("Error?");
					return;
				}
				object.remove(player);
				break;
			case 4:		// Build
				if (option != 4) {
					player.sendMessage("Error?");
					return;
				}
				player.putTemporaryAttribute("BUILD_INTER_MH", object);
				object.sendBuildInterface(player);
				break;
		}
	}

	static {
		for (MahoganyClient client : values()) {
			for (MahoganyObject object : client.objects) {
				ObjectAction.register(object.getObjectId(), 2, (player, obj) -> client.objectInteract(player, object, 2));
				ObjectAction.register(object.getObjectId(), 3, (player, obj) -> client.objectInteract(player, object, 3));
				ObjectAction.register(object.getObjectId(), 4, (player, obj) -> client.objectInteract(player, object, 4));
			}
		}
	}
}
