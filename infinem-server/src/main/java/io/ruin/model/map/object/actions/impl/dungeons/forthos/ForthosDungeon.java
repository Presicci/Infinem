package io.ruin.model.map.object.actions.impl.dungeons.forthos;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.activities.combat.pvminstance.InstanceDialogue;
import io.ruin.model.activities.combat.pvminstance.InstanceType;
import io.ruin.model.activities.wilderness.Web;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.StatType;

import static io.ruin.model.map.object.actions.impl.Ladder.climb;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 1/18/2022
 */
public class ForthosDungeon {

    private static final int KNIFE = 946;

    private static void bonesOnBurner(Player player, Item item, Bone bone) {
        item.remove();
        player.animate(3705);
        player.sendFilteredMessage("The gods are pleased with your offerings.");
        player.getStats().addXp(StatType.Prayer, bone.exp * 3, true);
        World.sendGraphics(624, 0, 0, 1810, 9951, 0);
        bone.altarCounter.increment(player, 1);
    }

    static {
        ObjectAction enterCrypt = (player, obj) -> {
            if (player.getAbsY() == 9912) {
                player.dialogue(
                        new OptionsDialogue("<col=FF0000>You are about to enter a dangerous area",
                                new Option("Enter.", () -> enterSarachnisLair(player)),
                                new Option("Do not enter."))
                );
            } else {
                enterSarachnisLair(player);
            }
        };
        // Bone burner
        for(Bone bone : Bone.values()) {
            ItemObjectAction.register(bone.id, 34855, (player, item, obj) -> bonesOnBurner(player, item, bone));
        }
        // Sarachnis
        ObjectAction.register(34858, 1, (player, obj) -> {
            if (player.getAbsY() == obj.y)
                InstanceDialogue.open(player, InstanceType.SARACHNIS);
            else
                player.getMovement().teleport(1842, 9912, 0);
        });
        ObjectAction.register(34858, 2, (player, obj) -> {
            if (player.getAbsY() == obj.y)
                enterSarachnisLair(player);
            else
                player.getMovement().teleport(1842, 9912, 0);
        });
        ObjectAction.register(34898, "slash", Web::slashWeb);
        // Gates
        ObjectAction.register(34843, "open", (player, obj) -> PassableDoor.passDoor(player, obj, Direction.WEST));
        ObjectAction.register(34842, "open", (player, obj) -> PassableDoor.passDoor(player, obj, Direction.NORTH));
        // Stairs
        ObjectAction.register(34865, 1669, 3567, 0, "climb-down", (player, obj) -> climb(player, 1800, 9967, 0, false, true, false));
        ObjectAction.register(34864, 1798, 9967, 0, "climb-up", (player, obj) -> climb(player, 1670, 3569, 0, true, true, false));
        // Ladder
        ObjectAction.register(34863, 1830, 9974, 0, "climb-up", (player, obj) -> Ladder.climb(player, 1702, 3575, 0, true, true, false));
        ObjectAction.register(34862, 1702, 3574, 0, "climb-down", (player, obj) -> Ladder.climb(player, 1830, 9973, 0, false, true, false));
        // Agility shortcut
        ObjectAction.register(34834, 1820, 9946, 0, "jump-over", (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 75, "attempt this"))
                return;
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(839);
                if(player.getAbsY() > obj.y)
                    player.getMovement().force(0, -2, 0, 0, 0, 60, Direction.SOUTH);
                else
                    player.getMovement().force(0, 2, 0, 0, 0, 60, Direction.NORTH);
                e.delay(2);
                player.unlock();
            });
        });
    }


    private static void enterSarachnisLair(Player player) {
        ItemDefinition wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        boolean knife;
        if (wepDef != null && wepDef.sharpWeapon) {
            knife = false;
        } else {
            if (!player.getInventory().hasId(KNIFE)) {
                player.sendMessage("Only a sharp blade can cut through this sticky web.");
                return;
            }
            knife = true;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(knife ? 911 : player.getCombat().weaponType.attackAnimation);
            e.delay(1);
            if (player.getAbsY() == 9912) {
                player.stepAbs(player.getAbsX(), player.getAbsY() - 1, StepType.FORCE_WALK);
            } else {
                player.stepAbs(player.getAbsX(), player.getAbsY() + 1, StepType.FORCE_WALK);
            }
            player.unlock();
        });
    }
}
