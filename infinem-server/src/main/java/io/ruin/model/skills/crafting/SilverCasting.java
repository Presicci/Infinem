package io.ruin.model.skills.crafting;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.StatType;

public enum SilverCasting {

    /**
     * Right side
     */
    HOLY(16, -1, 50.0, 1714, 1, 23),
    UNHOLY(17, -1, 50.0, 1720, 1, 24),
    SILVER_SICKLE(18, -1, 50.0, 2961, 1, 25),
    SILVER_CROSSBOW_BOLT(21, -1, 50.0, 9382, 10, 27),
    TIARA(23, -1, 52.5, 5525, 1, 28),

    /**
     * Left side
     */
    OPAL_RING(1, 1609, 10.0, 21081, 1, 7),
    JADE_RING(13, 1611, 32.0, 21084, 1, 8),
    TOPAZ_RING(16, 1613, 35.0, 21087, 1, 9),

    OPAL_NECKLACE(16, 1609, 35.0, 21090, 1, 11),
    JADE_NECKLACE(25, 1611, 54.0, 21093, 1, 12),
    TOPAZ_NECKLACE(32, 1613, 75.0, 21096, 1, 13),

    OPAL_AMULET(27, 1609, 55.0, 21099, 1, 15),
    JADE_AMULET(34, 1611, 70.0, 21102, 1, 16),
    TOPAZ_AMULET(45, 1613, 80.0, 21105, 1, 17),

    OPAL_BRACELET(22, 1609, 45.0, 21117, 1, 19),
    JADE_BRACELET(29, 1611, 60.0, 21120, 1, 20),
    TOPAZ_BRACELET(38, 1613, 70.0, 21123, 1, 21);

    public final int levelReq, gem, result, amount, child;
    public final double exp;

    SilverCasting(int levelReq, int gem, double exp, int result, int amount, int child) {
        this.levelReq = levelReq;
        this.gem = gem;
        this.exp = exp;
        this.result = result;
        this.amount = amount;
        this.child = child;
    }

    private static final int SILVER_BAR = 2355;

    private static void craft(Player player, SilverCasting silverCasting) {
        player.closeInterfaces();
        int amount = Config.IQ.get(player);
        if (!player.getStats().check(StatType.Crafting, silverCasting.levelReq, "make this"))
            return;
        if(silverCasting.gem == -1) {
            player.startEvent(event -> {
                int amt = amount;
                while (amt-- > 0) {
                    Item silverBar = player.getInventory().findItem(SILVER_BAR);
                    if (silverBar == null)
                        return;
                    player.animate(899);
                    event.delay(3);
                    silverBar.remove();
                    player.getInventory().add(silverCasting.result, silverCasting.amount);
                    player.getStats().addXp(StatType.Crafting, silverCasting.exp, true);
                    if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                        event.delay(1);
                    }
                }
            });
        } else {
            player.startEvent(event -> {
                int amt = amount;
                while (amt-- > 0) {
                    Item gem = player.getInventory().findItem(silverCasting.gem);
                    if (gem == null)
                        return;
                    Item silverBar = player.getInventory().findItem(SILVER_BAR);
                    if (silverBar == null)
                        return;
                    player.animate(899);
                    event.delay(3);
                    gem.remove();
                    silverBar.remove();
                    player.getInventory().add(silverCasting.result, 1);
                    player.getStats().addXp(StatType.Crafting, silverCasting.exp, true);
                    if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                        event.delay(1);
                    }
                }
            });
        }
    }

    static {
        ItemObjectAction.register(SILVER_BAR, "furnace", (player, item, object) -> {
            player.openInterface(InterfaceType.MAIN, Interface.SILVER_CASTING);
            player.getPacketSender().sendAccessMask(Interface.SILVER_CASTING, 4, 0, 11, 62);
            player.getPacketSender().sendAccessMask(Interface.SILVER_CASTING, 6, 0, 7, 62);
        });

        InterfaceHandler.register(Interface.SILVER_CASTING, h -> {
            for (SilverCasting item : values()) {
                h.actions[item.child] = (DefaultAction) (p, option, slot, itemId) -> craft(p, item);
            }
            h.actions[32] = (DefaultAction) (p, option, slot, itemId) -> Config.IQ.set(p, 1);
            h.actions[33] = (DefaultAction) (p, option, slot, itemId) -> Config.IQ.set(p, 5);
            h.actions[34] = (DefaultAction) (p, option, slot, itemId) -> Config.IQ.set(p, 10);
            h.actions[35] = (DefaultAction) (p, option, slot, itemId) -> p.integerInput("Enter amount:", amt -> Config.IQ.set(p, amt));    // TODO find var
            h.actions[36] = (DefaultAction) (p, option, slot, itemId) -> Config.IQ.set(p, 28);
        });
    }
}
