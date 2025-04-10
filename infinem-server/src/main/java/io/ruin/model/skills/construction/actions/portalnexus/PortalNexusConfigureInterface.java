package io.ruin.model.skills.construction.actions.portalnexus;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.StructDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/2/2024
 */
public class PortalNexusConfigureInterface {
    /**
     * VB 6670 - type of nexus, 0/1 first, 2 second, 3 third
     *
     * 1    - 6654  - 6672
     * 2    - 6655  - 6673
     * 3    - 6656  - 6674
     * 4    - 6657  - 6675
     * 5    - 6658  - 6676
     * 6    - 6659  - 6677
     * 7    - 6660  - 6678
     * 8    - 6661  - 6679
     * 9    - 6662  - 6680
     * 10   - 6663  - 6681
     * 11   - 6664  - 6682
     * 12   - 6665  - 6683
     * 13   - 6666  - 6684
     * 14   - 6667  - 6685
     * 15   - 6668  - 6686
     * 16   - 6554  - 6568
     * 17   - 6555  - 6569
     * 18   - 6556  - 6582
     * 19   - 10080 - 10092
     * 20   - 10081 - 10093
     * 21   - 10082 - 10094
     * 22   - 10083 - 10095
     * 23   - 10084 - 10096
     * 24   - 10085 - 10097
     * 25   - 10086 - 10098
     * 26   - 10087 - 10099
     * 27   - 10088 - 10100
     * 28   - 10089 - 10101
     * 29   - 10090 - 10102
     * 30   - 10091 - 10103
     */

    private static final String KEY = "PN_REQ";

    private static final Config NEXUS_TYPE = Config.varpbit(6670, false);

    private static final Config[] INTERFACE_SLOTS = {
            Config.varpbit(6672, false),
            Config.varpbit(6673, false),
            Config.varpbit(6674, false),
            Config.varpbit(6675, false),
            Config.varpbit(6676, false),
            Config.varpbit(6677, false),
            Config.varpbit(6678, false),
            Config.varpbit(6679, false),
            Config.varpbit(6680, false),
            Config.varpbit(6681, false),
            Config.varpbit(6682, false),
            Config.varpbit(6683, false),
            Config.varpbit(6684, false),
            Config.varpbit(6685, false),
            Config.varpbit(6686, false),
            Config.varpbit(6568, false),
            Config.varpbit(6569, false),
            Config.varpbit(6582, false),
            Config.varpbit(10092, false),
            Config.varpbit(10093, false),
            Config.varpbit(10094, false),
            Config.varpbit(10095, false),
            Config.varpbit(10096, false),
            Config.varpbit(10097, false),
            Config.varpbit(10098, false),
            Config.varpbit(10099, false),
            Config.varpbit(10100, false),
            Config.varpbit(10101, false),
            Config.varpbit(10102, false),
            Config.varpbit(10103, false),
    };

    private static int getMaxTeleports(Player player) {
        switch (NEXUS_TYPE.get(player)) {
            case 2:
                return 8;
            case 3:
                return 31;
            default:
                return 4;
        }
    }

    private static int getCurrentTeleportAmount(Player player) {
        int count = 0;
        for (Config interfaceSlot : INTERFACE_SLOTS) {
            if (interfaceSlot.get(player) > 0) ++count;
        }
        return count;
    }

    /**
     * 664 - item 1
     * 665 - item 2
     * 666 - item 3
     * 667 - item 4
     * 668 - cost 1
     * 669 - cost 2
     * 670 - cost 3
     * 671 - cost 4
     */
    private static boolean hasRequirements(Player player, int teleportIndex) {
        EnumDefinition enumDef = EnumDefinition.get(1377);
        if (enumDef == null) return false;
        StructDefinition struct = StructDefinition.get(enumDef.getValuesAsInts().get(teleportIndex));
        Map<Object, Object> values = struct.getParams();
        List<Item> requiredItems = getRequiredItems(struct);
        ItemContainer container = getCurrentlyRequiredItems(player);
        for (Item item : requiredItems) {
            if (!player.getInventory().hasItem(item.getId(), item.getAmount())) {
                player.sendMessage("You can't afford that teleport cost.");
                return false;
            }
        }
        if (values.containsKey(673)) {
            int levelRequirement = struct.getInt(673);
            if (player.getStats().get(StatType.Magic).fixedLevel < levelRequirement) {
                player.sendMessage("You need a magic level of " + levelRequirement + " to add that teleport.");
                return false;
            }
        }
        for (Item item : requiredItems) {
            container.add(item);
        }
        player.putTemporaryAttribute(KEY, container);
        return true;
    }

    private static List<Item> getRequiredItems(StructDefinition teleportStruct) {
        List<Item> requiredItems = new ArrayList<>();
        Map<Object, Object> values = teleportStruct.getParams();
        for (int key : Arrays.asList(664, 665, 666, 667)) {
            if (values.containsKey(key)) {
                requiredItems.add(new Item(teleportStruct.getInt(key), teleportStruct.getInt(key + 4)));
            }
        }
        return requiredItems;
    }

    private static ItemContainer getCurrentlyRequiredItems(Player player) {
        if (!player.hasTemporaryAttribute(KEY)) {
            ItemContainer container = new ItemContainer();
            container.init(player, 28, 19, 29, 619, true);
            return container;
        } else {
            return player.getTemporaryAttribute(KEY);
        }
    }

    private static void addTeleport(Player player, int teleportIndex) {
        int maxSlots = getMaxTeleports(player);
        int currentTeleports = getCurrentTeleportAmount(player);
        if (currentTeleports >= maxSlots) {
            player.sendMessage("You already have the maximum possible teleports for this type of nexus.");
            return;
        }
        ;
        if (currentTeleports == -1) {
            player.sendMessage("Error slot==-1");
            return;
        }
        EnumDefinition enumDef = EnumDefinition.get(1375);
        if (enumDef == null) return;
        int enumValue = enumDef.getValuesAsInts().get(teleportIndex);
        if (!hasRequirements(player, enumValue)) return;
        INTERFACE_SLOTS[currentTeleports].set(player, enumValue);
    }

    private static void removeTeleport(Player player, int index) {
        INTERFACE_SLOTS[index - 1].set(player, 0);
    }

    private static void reorderTeleports(Player player, int from, int to) {
        int fromValue = INTERFACE_SLOTS[from].get(player);
        int toValue = INTERFACE_SLOTS[to].get(player);
        INTERFACE_SLOTS[to].set(player, fromValue);
        INTERFACE_SLOTS[from].set(player, toValue);
    }

    static {
        InterfaceHandler.register(Interface.PORTAL_NEXUS_CONFIGURE, h -> {
            h.actions[9] = (SimpleAction) player -> {
                getCurrentlyRequiredItems(player).send(player);
            };
            h.actions[30] = (SimpleAction) player -> {
                getCurrentlyRequiredItems(player).sendUpdates();
            };
            h.actions[22] = new InterfaceAction() {
                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    System.out.println("Drag: " + fromSlot + ", " + fromItemId + ", " + toInterfaceId + ", " + toChildId + ", " + toSlot + ", " + toItemId);
                    if (toChildId == 18) {
                        addTeleport(player, fromSlot);
                    } else if (toChildId == 39) {
                        addTeleport(player, fromSlot);
                    } else if (toChildId == 3) {
                        player.sendMessage("configure " + fromSlot);
                    }
                }
            };
            h.actions[39] = new InterfaceAction() {
                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    System.out.println("Drag: " + fromSlot + ", " + fromItemId + ", " + toInterfaceId + ", " + toChildId + ", " + toSlot + ", " + toItemId);
                    if (toChildId == 22) {
                        removeTeleport(player, fromSlot);
                    } else if (toChildId == 39) {
                        reorderTeleports(player, fromSlot - 1, toSlot - 1);
                    } else if (toChildId == 3) {
                        player.sendMessage("configure " + fromSlot);
                    }
                }
            };
        });
    }
}
