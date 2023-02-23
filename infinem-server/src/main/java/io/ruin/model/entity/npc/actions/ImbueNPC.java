package io.ruin.model.entity.npc.actions;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/22/2023
 */
public class ImbueNPC {
    static {
        /*List<String> upgradeInfo = new ArrayList<>();
        upgradeInfo.add("To upgrade/imbue an item, simply use the required item on");
        upgradeInfo.add("Bob. These are the items he's able to assist you with:");
        upgradeInfo.add("");
        for(ItemUpgrading upgrade : ItemUpgrading.values()) {
            String currencyName = "Coins";
            int cost = upgrade.coinUpgradeCost;
            int currencyId = COINS_995;
            upgradeInfo.add(ItemDef.get(upgrade.regularId).name + " -> " + ItemDef.get(upgrade.upgradeId).name + "  <col=800000> " + NumberUtils.formatNumber(cost));
            ItemAction.registerInventory(upgrade.upgradeId, "uncharge", (player, item) -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "You will NOT get your " + currencyName + " back.", item, () -> {
                    item.setId(upgrade.regularId);
                    player.dialogue(new ItemDialogue().one(upgrade.regularId, "You release the energy from the item."));
                }));
            });
            ItemNPCAction.register(upgrade.regularId, BOB_2812, (player, item, npc) -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Upgrade your " + item.getDef().name + " for " + cost + " " + currencyName + "?", new Item(upgrade.upgradeId, 1), () -> {
                    Item bloodMoney = player.getInventory().findItem(currencyId);
                    if(bloodMoney == null || bloodMoney.getAmount() < cost) {
                        player.dialogue(new NPCDialogue(npc, "You don't have enough " + currencyName + " for me to upgrade that."));
                        return;
                    }
                    bloodMoney.incrementAmount(-cost);
                    item.setId(upgrade.upgradeId);
                    player.dialogue(new ItemDialogue().one(upgrade.upgradeId, "Your " + item.getDef().name + " has been upgraded."));
                }));
            });
        }
        String[] upgradeInfoArray = upgradeInfo.toArray(new String[0]);
        NPCAction.register(BOB_2812, "imbue", (player, item) -> player.sendScroll("<col=800000>Item Imbues", upgradeInfoArray));*/
    }
}
