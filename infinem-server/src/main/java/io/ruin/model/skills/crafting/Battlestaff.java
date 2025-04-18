package io.ruin.model.skills.crafting;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.ProductionMaster;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;

public enum Battlestaff {

    WATER(571, 1395, 100.0, 54),
    EARTH(575, 1399, 112.5, 58),
    FIRE(569, 1393, 125.0, 62),
    AIR(573, 1397, 137.5, 66);

    public final int orbId, staffId, levelReq;
    public final double exp;

    Battlestaff(int orbId, int staffId, double exp, int levelReq) {
        this.orbId = orbId;
        this.staffId = staffId;
        this.exp = exp;
        this.levelReq = levelReq;
    }

    private void make(Player player, Item staff, Item orb) {
        RandomEvent.attemptTrigger(player);
        staff.setId(staffId);
        orb.remove();
        player.getStats().addXp(StatType.Crafting, exp, true);
    }

    private static final int BATTLE_STAFF = 1391;

    static {
        for (Battlestaff battlestaff : values()) {
            SkillItem item = new SkillItem(battlestaff.staffId).addAction((player, amount, event) -> {
                int prodCount = 0;
                while (amount-- > 0) {
                    Item staff = player.getInventory().findItem(BATTLE_STAFF);
                    if (staff == null)
                        return;
                    Item orb = player.getInventory().findItem(battlestaff.orbId);
                    if (orb == null)
                        return;
                    if (ProductionMaster.roll(player))
                        prodCount++;
                    if (player.getInventory().hasMultiple(staff.getId(), orb.getId())) {
                        battlestaff.make(player, staff, orb);
                        if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                            event.delay(3);
                        }
                        continue;
                    }
                    battlestaff.make(player, staff, orb);
                    break;
                }
                ProductionMaster.extra(player, prodCount, battlestaff.staffId, StatType.Crafting, battlestaff.exp * prodCount);
            });
            ItemItemAction.register(battlestaff.orbId, BATTLE_STAFF, (player, orb, staff) -> {
                if(!player.getStats().check(StatType.Crafting, battlestaff.levelReq, "do craft this"))
                    return;
                if(player.getInventory().hasMultiple(orb.getId(), staff.getId())) {
                    SkillDialogue.make(player, item);
                    return;
                }
                battlestaff.make(player, orb, staff);
            });
        }
    }
}
