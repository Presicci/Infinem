package io.ruin.model.inter.handlers;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/14/2024
 */
public class BattlestaffEnchanting {

    @AllArgsConstructor
    private enum Battlestaff {
        AIR_BATTLESTAFF(4, Items.AIR_BATTLESTAFF, Items.MYSTIC_AIR_STAFF),
        WATER_BATTLESTAFF(5, Items.WATER_BATTLESTAFF, Items.MYSTIC_WATER_STAFF),
        EARTH_BATTLESTAFF(6, Items.EARTH_BATTLESTAFF, Items.MYSTIC_EARTH_STAFF),
        FIRE_BATTLESTAFF(7, Items.FIRE_BATTLESTAFF, Items.MYSTIC_FIRE_STAFF),
        LAVA_BATTLESTAFF(8, Items.LAVA_BATTLESTAFF, Items.MYSTIC_LAVA_STAFF, 21198, 21200),
        MUD_BATTLESTAFF(9, Items.MUD_BATTLESTAFF, Items.MYSTIC_MUD_STAFF),
        STEAM_BATTLESTAFF(10, Items.STEAM_BATTLESTAFF, Items.MYSTIC_STEAM_STAFF, 12795, 12796),
        SMOKE_BATTLESTAFF(3, Items.SMOKE_BATTLESTAFF, Items.MYSTIC_SMOKE_STAFF),
        MIST_BATTLESTAFF(11, Items.MIST_BATTLESTAFF, Items.MYSTIC_MIST_STAFF),
        DUST_BATTLESTAFF(12, Items.DUST_BATTLESTAFF, Items.MYSTIC_DUST_STAFF);

        private final int childId, battleStaff, mysticStaff, battleStaffOr, mysticStaffOr;

        Battlestaff(int childId, int battleStaff, int mysticStaff) {
            this.childId = childId;
            this.battleStaff = battleStaff;
            this.mysticStaff = mysticStaff;
            this.battleStaffOr = -1;
            this.mysticStaffOr = -1;
        }


        private void enchant(Player player) {
            Item staff = player.getInventory().findFirst(battleStaff);
            if (staff == null) {
                if (mysticStaffOr != -1) staff = player.getInventory().findFirst(battleStaffOr);
                if (staff == null) {
                    player.sendMessage("You dont have " + ItemDefinition.get(battleStaff).descriptiveName + " to enchant.");
                    return;
                }
            }
            int cost = getCost(player);
            if (player.getInventory().getAmount(995) < cost) {
                player.sendMessage("You need at least " + NumberUtils.formatNumber(cost) + " coins to enchant that battlestaff.");
                return;
            }
            player.closeInterface(InterfaceType.MAIN);
            player.getInventory().remove(995, cost);
            staff.setId(staff.getId() == battleStaffOr ? mysticStaffOr : mysticStaff);
            player.sendMessage("You enchant the " + ItemDefinition.get(battleStaff).descriptiveName + ".");
            player.getTaskManager().doLookupByUUID(583);    // Enchant a Battlestaff
        }
    }

    private static int getCost(Player player) {
        return AreaReward.THORMAC_DISCOUNT_2.hasReward(player) ? 20000 : AreaReward.THORMAC_DISCOUNT_1.hasReward(player) ? 30000 : 40000;
    }

    static {
        InterfaceHandler.register(332, h -> {
            for (Battlestaff battlestaff : Battlestaff.values()) {
                h.actions[battlestaff.childId] = (SimpleAction) battlestaff::enchant;
            }
        });
        NPCAction.register(5232, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Hello, would you like me to enchant a battlestaff for you? It'll cost you " + NumberUtils.formatNumber(getCost(player)) + " coins."),
                    new OptionsDialogue(
                            new Option("Yes, please.", () -> player.openInterface(InterfaceType.MAIN, 332)),
                            new Option("No.")
                    )
            );
        });
    }
}
