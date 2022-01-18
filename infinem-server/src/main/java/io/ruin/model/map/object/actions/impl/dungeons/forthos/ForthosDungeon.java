package io.ruin.model.map.object.actions.impl.dungeons.forthos;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.wilderness.Web;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.map.object.actions.impl.PassableDoor;
import io.ruin.model.skills.agility.shortcut.CrumblingWall;
import io.ruin.model.stat.StatType;

import static io.ruin.model.map.object.actions.impl.Ladder.climb;

public class ForthosDungeon {

    private static final Bounds SARACHNIS = new Bounds(1829, 9888, 1851, 9911, 0);
    public static int KNIFE = 946;

    static {
        ObjectAction peekAction = (player, obj) -> {
            int regularCount = Region.get(7322).players.size() - 1;
            if (regularCount <= 0)
                player.sendMessage("You listen and hear no adventurers inside the crypt.");
            else
                player.sendMessage("You listen and hear " + regularCount + " adventurer" + (regularCount > 1 ? "s" : "") + " inside the crypt.");
        };
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
        // Sarachnis
        ObjectAction.register(34898, 2, peekAction);
        ObjectAction.register(34858, 1, enterCrypt);
        ObjectAction.register(34858, 2, (player, obj) -> enterSarachnisLair(player));
        ObjectAction.register(34898, "slash", Web::slashWeb);
        // Gates
        ObjectAction.register(34843, "open", (player, obj) -> PassableDoor.passDoor(player, obj, Direction.WEST));
        ObjectAction.register(34842, "open", (player, obj) -> PassableDoor.passDoor(player, obj, Direction.NORTH));
        // Grubby chest gate
        ObjectAction.register(34840, "pick-lock", (player, obj) -> {
            if (player.getAbsX() >= obj.getPosition().getX()) {
                if (player.getStats().get(StatType.Thieving).currentLevel > 57) {
                    //player.animate(1); TODO
                    player.getStats().addXp(StatType.Thieving, 1.0, true);
                } else {
                    player.sendMessage("You need a thieving level of 57 to pick the lock.");
                    return;
                }
            }
            PassableDoor.passDoor(player, obj, Direction.WEST);
        });
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
        ItemDef wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
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
