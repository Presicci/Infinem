package io.ruin.model.skills.magic.rune;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.chargable.BryophytasStaff;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.containers.Equipment;

import java.util.ArrayList;

public class RuneRemoval {

    private ArrayList<Removal> toRemove;
    private boolean keep;
    private Player player;

    private RuneRemoval(ArrayList<Removal> toRemove, Player player) {
        this.toRemove = toRemove;
        this.player = player;
    }

    public void remove() {
        if (!keep) {
            for (Removal r : toRemove) {
                if (r.item.getId() == 22370) {
                    BryophytasStaff.removeCharges(player, r.amount);
                    continue;
                }
                r.remove();
            }
        }
        toRemove.clear();
    }

    /**
     * Separator
     */

    public static RuneRemoval get(Player player, Item... runeItems) {
        ItemDefinition wepDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        Rune staffRune = wepDef == null ? null : wepDef.staffRune;

        ArrayList<Item> pItems = new ArrayList<>();
        for (Item item : player.getInventory().getItems()) {
            if (item != null)
                pItems.add(item);
        }
        if (player.getInventory().hasId(RunePouch.RUNE_POUCH)) {
            for (Item item : player.getRunePouch().getItems()) {
                if (item != null)
                    pItems.add(item);
            }
            for (Item item : player.getTournamentRunePouch().getItems()) {
                if (item != null)
                    pItems.add(item);
            }
        }
        ArrayList<Removal> toRemove = new ArrayList<>();
        for (Item reqItem : runeItems) {
            int reqId = reqItem.getId();
            int reqAmount = reqItem.getAmount();
            Rune reqRune = reqItem.getDef().rune;
            if (Config.FOUNTAIN_OF_RUNE.get(player) == 1)
                continue;
            if (reqRune != null && reqRune.accept(staffRune)) {
                /**
                 * Use staff
                 */
                continue;
            }
            if (reqRune == Rune.FIRE && player.getEquipment().hasId(20714)) {
                /**
                 * Use tome of fire
                 */
                continue;
            }
            if (reqRune == Rune.WATER && (player.getEquipment().hasId(21006))) {
                /**
                 * Kodai wand
                 */
                continue;
            }
            if (reqRune == Rune.NATURE && player.getEquipment().hasId(22370)) {
                int charges = BryophytasStaff.getCharges(player);
                if (charges >= reqAmount) {
                    toRemove.add(new Removal(new Item(22370), reqAmount));
                    continue;
                }
                toRemove.add(new Removal(new Item(22370), charges));
                reqAmount -= charges;
            }
            for (Item item : pItems) {
                ItemDefinition def = item.getDef();
                if (reqRune != null) {
                    if (!reqRune.accept(def.rune))
                        continue;
                } else {
                    if (item.getId() != reqId)
                        continue;
                }
                int amount = item.getAmount();
                if (amount >= reqAmount) {
                    toRemove.add(new Removal(item, reqAmount));
                    reqAmount = 0;
                    break;
                }
                toRemove.add(new Removal(item, amount));
                reqAmount -= amount;
            }
            if (reqAmount > 0) {
                /**
                 * Not enough runes
                 */
                return null;
            }
        }
        RuneRemoval removal = new RuneRemoval(toRemove, player);
        //if (wepDef != null && (wepDef.id == 11791 || wepDef.id == 12902 || wepDef.id == 12904 || wepDef.id == 21006 || wepDef.id == 30181))
        //    removal.keep = Random.get() <= 0.125;
        return removal;
    }

    private static final class Removal {

        private final Item item;

        private final int amount;

        public Removal(Item item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        private void remove() {
            item.incrementAmount(-amount);
        }

    }

}