package io.ruin.model.skills.farming;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.skills.herblore.Herb;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.List;

import static io.ruin.model.skills.farming.patch.impl.AllotmentPatch.WATERING_CAN_IDS;

public class ToolStorage {

    private static final int SEED_VAULT_COST = 100_000;

    enum Tool {//order should be the same as on the interface
        RAKE(5341, Config.STORAGE_RAKE) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_RAKE_2.get(player) << 1) | Config.STORAGE_RAKE.get(player);
            }
            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_RAKE.set(player, newValue & 1);
                Config.STORAGE_RAKE_2.set(player, newValue >> 1);
            }
        },
        SEED_DIBBER(5343, Config.STORAGE_SEED_DIBBER) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_SEED_DIBBER_2.get(player) << 1) | Config.STORAGE_SEED_DIBBER.get(player);
            }
            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_SEED_DIBBER.set(player, newValue & 1);
                Config.STORAGE_SEED_DIBBER_2.set(player, newValue >> 1);
            }
        },
        SPADE(952, Config.STORAGE_SPADE) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_SPADE_2.get(player) << 1) | Config.STORAGE_SPADE.get(player);
            }
            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_SPADE.set(player, newValue & 1);
                Config.STORAGE_SPADE_2.set(player, newValue >> 1);
            }
        },
        SECATEURS(5329, Config.STORAGE_SECATEURS) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_SECATEURS_2.get(player) << 1) | Config.STORAGE_SECATEURS.get(player);
            }
            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_SECATEURS.set(player, newValue & 1);
                Config.STORAGE_SECATEURS_2.set(player, newValue >> 1);
            }
            @Override
            public boolean accept(int item) {
                return item == 5329 || item == 7409;
            }

            @Override
            public void onDeposit(Player player, Item item) {
                Config.STORAGE_SECATEURS_TYPE.set(player, item.getId() == 7409 ? 1 : 0);
            }

            @Override
            public int addItem(Player player, int amount, boolean noted) {
                int id = Config.STORAGE_SECATEURS_TYPE.get(player) == 1 ? 7409 : 5329;
                if (noted) {
                    return player.getInventory().add(ItemDefinition.get(id).notedId, amount);
                }
                int added = player.getInventory().add(id, amount);
                if ((get(player) - added) <= 0)
                    Config.STORAGE_SECATEURS_TYPE.set(player, 0);
                return added;
            }
        },
        WATERING_CAN(5331, Config.STORAGE_WATERING_CAN) {
            @Override
            public boolean accept(int item) {
                return WATERING_CAN_IDS.contains(item);
            }

            @Override
            public void onDeposit(Player player, Item item) {
                player.getFarming().getStorage().wateringCanCharges = WATERING_CAN_IDS.indexOf(item.getId());
            }

            @Override
            public int addItem(Player player, int amount, boolean noted) {
                return player.getInventory().add(WATERING_CAN_IDS.get(player.getFarming().getStorage().wateringCanCharges), amount);
            }
        },
        GARDENING_TROWEL(5325, Config.STORAGE_TROWEL) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_TROWEL_2.get(player) << 1) | Config.STORAGE_TROWEL.get(player);
            }
            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_TROWEL.set(player, newValue & 1);
                Config.STORAGE_TROWEL_2.set(player, newValue >> 1);
            }
        },
        PLANT_CURE(6036, Config.STORAGE_PLANT_CURE),
        BOTTOMLESS_BUCKET(22997, Config.STORAGE_BOTTOMLESS_COMPOST) {
            @Override
            public int get(Player player) {
                int stored = super.get(player);
                if (stored == 0)
                    return 0;
                return player.getFarming().getStorage().bottomlessBucket.getId() == 22994 ? 1 : 2;
            }

            @Override
            public boolean accept(int item) {
                return item == 22994 || item == 22997;
            }

            @Override
            public void onDeposit(Player player, Item item) {
                player.getFarming().getStorage().bottomlessBucket = item;
            }

            @Override
            public void update(Player player, int amount) {
                if (amount < 0) Config.STORAGE_BOTTOMLESS_COMPOST.set(player, 0);
                else {
                    if (player.getFarming().getStorage().bottomlessBucket.getId() == 22997) {
                        Config.STORAGE_BOTTOMLESS_COMPOST.set(player, 2);
                    } else {
                        Config.STORAGE_BOTTOMLESS_COMPOST.set(player, 1);
                    }
                }
            }

            @Override
            public int addItem(Player player, int amount, boolean noted) {
                return player.getInventory().add(player.getFarming().getStorage().bottomlessBucket);
            }
        },
        EMPTY_BUCKET(1925, null) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_EMPTY_BUCKET_3.get(player) << 8) | (Config.STORAGE_EMPTY_BUCKET_2.get(player) << 5) | Config.STORAGE_EMPTY_BUCKET_1.get(player);
            }

            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_EMPTY_BUCKET_1.set(player, newValue & 31); // 5 bit | 31 max
                Config.STORAGE_EMPTY_BUCKET_2.set(player, (newValue >> 5) & 7); // 3 bit | 7 max
                Config.STORAGE_EMPTY_BUCKET_3.set(player, newValue >> 8); // 3 bit | 7 max
            }
        },
        COMPOST(6032, null) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_COMPOST_2.get(player) << 8) | Config.STORAGE_COMPOST_1.get(player);
            }

            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_COMPOST_1.set(player, newValue & 255); // 8 bit | 255 max
                Config.STORAGE_COMPOST_2.set(player, (newValue >> 8) & 3); // 2 bit | 3 max
            }
        },
        SUPERCOMPOST(6034, null) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_SUPERCOMPOST_2.get(player) << 8) | Config.STORAGE_SUPERCOMPOST_1.get(player);
            }

            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_SUPERCOMPOST_1.set(player, newValue & 255); // 8 bit | 255 max
                Config.STORAGE_SUPERCOMPOST_2.set(player, (newValue >> 8) & 3); // 2 bit | 3 max
            }
        },
        ULTRACOMPOST(21483, Config.STORAGE_ULTRACOMPOST);

        Tool(int itemId, Config config) {
            this.config = config;
            this.itemId = itemId;
            name = this.toString().toLowerCase().replace("_", " ");
        }

        private Config config;
        private int itemId;
        private String name;

        public boolean accept(int item) {
            return item == itemId;
        }

        public void update(Player player, int amount) {
            config.set(player, get(player) + amount);
        }

        public int get(Player player) {
            return config.get(player);
        }

        public int addItem(Player player, int amount, boolean noted) {
            if (noted) {
                return player.getInventory().add(ItemDefinition.get(itemId).notedId, amount);
            }
            return player.getInventory().add(itemId, amount);
        }

        public void onDeposit(Player player, Item item) {
            /* memes */
        }

    }

    void setPlayer(Player player) {
        this.player = player;
    }

    public void open(Player player) {
        if (player.getBankPin().requiresVerification(this::open))
            return;
        player.openInterface(InterfaceType.MAIN, 125);
        player.openInterface(InterfaceType.INVENTORY, 126);
        // Unlock quantity buttons
        player.getPacketSender().sendAccessMask(125, 4, -1, -1, 1 << 1);
        player.getPacketSender().sendAccessMask(125, 5, -1, -1, 1 << 1);
        player.getPacketSender().sendAccessMask(125, 6, -1, -1, 1 << 1);
        player.getPacketSender().sendAccessMask(125, 7, -1, -1, 1 << 1);
    }

    public void withdraw(Tool tool, int amount, boolean noted) {
        amount = Math.min(amount, tool.get(player));
        if (amount == 0) {
            player.sendMessage("Your " + tool.name + " storage is empty.");
            return;
        }
        int added = tool.addItem(player, amount, noted);
        if (added == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        tool.update(player, -added);
    }

    public void deposit(Tool tool, int amount) {
        int maxAmount = getMaxAmount(tool);
        if (tool.get(player) >= maxAmount) {
            player.sendMessage("Your " + tool.name + " storage is full.");
            return;
        }
        if (maxAmount == 1) {
            for (Item item : player.getInventory().getItems()) {
                if (item == null)
                    continue;
                if (tool.accept(item.getId())) {
                    tool.onDeposit(player, item);
                    item.remove();
                    tool.update(player, 1);
                    return;
                }
            }
            player.sendMessage("You don't have any of that to deposit.");
        } else {
            int itemId = tool == Tool.SECATEURS ? (player.getInventory().getAmount(7409) > 0 ? 7409 : tool.itemId) : tool.itemId;
            if ((itemId == 5329 && Config.STORAGE_SECATEURS_TYPE.get(player) == 1) || (itemId == 7409 && Config.STORAGE_SECATEURS_TYPE.get(player) == 0 && tool.get(player) != 0)) {
                player.sendMessage("You cannot mix different types of secateurs in the storage.");
                return;
            }
            int notedId = ItemDefinition.get(itemId).notedId;
            int unnotedAmt = player.getInventory().getAmount(itemId);
            int notedAmt = notedId == -1 ? 0 : player.getInventory().getAmount(notedId);
            amount = Math.min(amount, Math.min(unnotedAmt + notedAmt, maxAmount - tool.get(player)));
            if (amount == 0) {
                player.sendMessage("You don't have any of that to deposit.");
                return;
            }
            player.getInventory().remove(itemId, amount, true);
            tool.update(player, amount);
            tool.onDeposit(player, new Item(itemId)); // just in case
        }
    }

    private int getMaxAmount(Tool tool) {
        if (tool == Tool.EMPTY_BUCKET || tool == Tool.COMPOST || tool == Tool.SUPERCOMPOST || tool == Tool.ULTRACOMPOST || tool == Tool.PLANT_CURE)
            return 1000;
        if (tool == Tool.RAKE || tool == Tool.SPADE || tool == Tool.SEED_DIBBER || tool == Tool.SECATEURS || tool == Tool.GARDENING_TROWEL)
            return 100;
        return 1;
    }

    private static final int[] STATIC_OPTIONS = { 1, 2, 3, 4 };

    private void getOption(int interfaceOption, int slot, boolean deposit) {
        if ((slot == 4 || slot == 7) && interfaceOption != 10) {   // Bottomless bucket and Watering can, can only store 1 of each
            if (deposit)
                player.getFarming().getStorage().deposit(Tool.values()[slot], 1);
            else
                player.getFarming().getStorage().withdraw(Tool.values()[slot], 1, false);
            return;
        }
        if (interfaceOption == 9 && !deposit) { // Banknote
            player.getFarming().getStorage().withdraw(Tool.values()[slot], Integer.MAX_VALUE, true);
            return;
        }
        if (interfaceOption == 10) { // Examine
            new Item(Tool.values()[slot].itemId).examine(player);
            return;
        }
        List<Integer> options = new ArrayList<>(6);
        int defaultOption = Config.TOOL_STORAGE_QUANTITY.get(player);
        switch(defaultOption) {
            case 0:
                options.add(1);
                break;
            case 1:
                options.add(2);
                break;
            case 2:
                options.add(4);
                break;
            case 3:
                options.add(3);
                break;
            default:
                throw new IllegalStateException();
        }
        for (int o : STATIC_OPTIONS) {
            if (!options.contains(o))
                options.add(o);
        }
        int option = options.get(interfaceOption-1);
        switch (option) {
            case 1:     // Remove-1
                if (deposit)
                    player.getFarming().getStorage().deposit(Tool.values()[slot], 1);
                else
                    player.getFarming().getStorage().withdraw(Tool.values()[slot], 1, false);
                break;
            case 2:     // Remove-5
                if (deposit)
                    player.getFarming().getStorage().deposit(Tool.values()[slot], 5);
                else
                    player.getFarming().getStorage().withdraw(Tool.values()[slot], 5, false);
                break;
            case 3:     // Remove-X
                if (deposit)
                    player.integerInput("Enter amount:", amt -> player.getFarming().getStorage().deposit(Tool.values()[slot], amt));
                else
                    player.integerInput("Enter amount:", amt -> player.getFarming().getStorage().withdraw(Tool.values()[slot], amt, false));
                break;
            case 4:     // Remove-All
                if (deposit)
                    player.getFarming().getStorage().deposit(Tool.values()[slot], Integer.MAX_VALUE);
                else
                    player.getFarming().getStorage().withdraw(Tool.values()[slot], Integer.MAX_VALUE, false);
                break;
        }
    }

    private static void inquireTools(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What do you do with the tools you're storing?<br>" +
                        "They can't possibly all fit in your pockets!"),
                new NPCDialogue(npc, "We leprechauns have a shed where we keep 'em. It's a magic shed, so ye can get yer items back from any of us leprechauns whenever ye want. " +
                        "Saves ye havin' to carry loads of stuff around the country!"),
                new NPCDialogue(npc, "So... do ye want to be using the store?"),
                new OptionsDialogue(
                        new Option("Yes please.", () -> player.getFarming().getStorage().open(player)),
                        new Option("What can you store?", () -> inquireStorage(player, npc)),
                        new Option("No thanks, I'll keep hold of my stuff.", () -> farewell(player, npc))

                )
        );
    }

    private static void inquireStorage(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What can you store?"),
                new NPCDialogue(npc, "We'll hold onto yer rake, yer seed dibber, yer spade,<br>" +
                        "yer secateurs, yer waterin' can and yer trowel - but<br>" +
                        "mind it's not oen of them fancy trowels only<br>" +
                        "archaeologists use!"),
                new NPCDialogue(npc, "We'll take a few buckets off yer hands too, and even<br>" +
                        "yer compost, supercompost an' ultracompost! Also plant<br>" +
                        "cure vials."),
                new NPCDialogue(npc, "Aside from that, if ye hands me yer farming produce, I<br>" +
                        "can mebbe change it into banknotes for ye."),
                new NPCDialogue(npc, "So... do ye want to be using the store?"),
                new OptionsDialogue(
                        new Option("Yes please.", () -> player.getFarming().getStorage().open(player)),
                        new Option("What do you do with the tools you're storing?", () -> inquireTools(player, npc)),
                        new Option("No thanks, I'll keep hold of my stuff.", () -> farewell(player, npc))
                )
        );
    }

    private static void farewell(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("No thanks, I'll keep hold of my stuff."),
                new NPCDialogue(npc, "Ye must be dafter than ye look if ye likes luggin' yer tools everywhere ye goes!")
        );
    }

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Ah, 'tis a foine day to be sure! Were yez wantin' me to<br>" +
                        "store yer tools, or maybe ye might be wantin' yer stuff<br>back from me?"),
                new OptionsDialogue(
                        new Option("Yes please.", () -> player.getFarming().getStorage().open(player)),
                        new Option("What can you store?", () -> inquireStorage(player, npc)),
                        new Option("What do you do with the tools you're storing?", () -> inquireTools(player, npc)),
                        new Option("No thanks, I'll keep hold of my stuff.", () -> farewell(player, npc))
                )
        );
    }

    private static void seedVault(Player player, NPC npc) {
        if (player.hasAttribute(AttributeKey.LEP_SEED_VAULT))
            player.getSeedVault().sendVault();
        else if (player.getStats().get(StatType.Farming).fixedLevel < 45) {
            player.dialogue(
                    new NPCDialogue(npc, "Yer gonna wants ta be more experienced in farmin an' pay a wee fee if yer wantin' ta access yer seed vault."),
                    new MessageDialogue("Accessing your seed vault through the leprechaun requires level 45 farming and a one time payment of " + NumberUtils.formatNumber(SEED_VAULT_COST) + " coins.")
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Yer gonna needs ta pay a wee fee if yer wantin' ta access yer seed vault."),
                    new PlayerDialogue("How much?"),
                    new NPCDialogue(npc, "Me back be hurtin' from all yer tools. " + NumberUtils.formatNumber(SEED_VAULT_COST) + " gold pieces should do."),
                    new OptionsDialogue("Pay " + NumberUtils.formatNumber(SEED_VAULT_COST) + " to unlock seed vault access through leprechauns?",
                            new Option("Sounds fair, I'll pay up.", new PlayerDialogue("Sounds fair, I'll pay up."), new ActionDialogue(() -> {
                                if (player.getInventory().getAmount(995) < SEED_VAULT_COST) {
                                    player.dialogue(new PlayerDialogue("I haven't enough coin..."), new NPCDialogue(npc, "Well get to farmin' so ya can pay me."));
                                    return;
                                }
                                player.getInventory().remove(995, SEED_VAULT_COST);
                                player.putAttribute(AttributeKey.LEP_SEED_VAULT, 1);
                                player.getTaskManager().doLookupByUUID(922);    // Purchase Leprechaun Seed Vault Access
                                player.dialogue(
                                        new MessageDialogue("The leprechaun collects your coins."),
                                        new NPCDialogue(npc, "Thank yee for yer service. Ye can now access yer seeds from me at anytime."),
                                        new ActionDialogue(() -> player.getSeedVault().sendVault())
                                );
                            })),
                            new Option("That's absurd, I can bring my own seeds.", new PlayerDialogue("That's absurd, I can bring my own seeds."), new NPCDialogue(npc, "Suit yer self."))
                    )
            );
        }
    }

    static {
        InterfaceHandler.register(125, h -> {
            for (int i = 8; i < 20; i++) {
                final int slot = i - 8;
                h.actions[i] = (OptionAction) (player, option) -> player.getFarming().getStorage().getOption(option, slot, false);
            }
            h.actions[4] = (OptionAction) (player, option) -> Config.TOOL_STORAGE_QUANTITY.set(player, 0);
            h.actions[5] = (OptionAction) (player, option) -> Config.TOOL_STORAGE_QUANTITY.set(player, 1);
            h.actions[6] = (OptionAction) (player, option) -> Config.TOOL_STORAGE_QUANTITY.set(player, 3);
            h.actions[7] = (OptionAction) (player, option) -> Config.TOOL_STORAGE_QUANTITY.set(player, 2);
        });

        InterfaceHandler.register(126, h -> {
            for (int i = 1; i < 13; i++) {
                final int slot = i - 1;
                h.actions[i] = (OptionAction) (player, option) -> player.getFarming().getStorage().getOption(option, slot, true);
            }
        });
        NPCAction.register("tool leprechaun", "talk-to", ToolStorage::dialogue);
        NPCAction.register("tool leprechaun", "exchange", (player, npc) -> player.getFarming().getStorage().open(player));
        NPCAction.register("tool leprechaun", "seed vault", ToolStorage::seedVault);
        ItemNPCAction.register("tool leprechaun", (player, item, npc) -> {
            if (item.getDef().notedId > 0 && (item.getDef().produceOf != null || Herb.get(item.getId()) != null)) {
                int amount = player.getInventory().getAmount(item.getId());
                player.getInventory().remove(item.getId(), amount);
                player.getInventory().add(item.getDef().notedId, amount);
                player.dialogue(new ItemDialogue().one(item.getId(), "The leprechaun exchanges your item for bank notes."));
            } else {
                player.dialogue(new MessageDialogue("The leprechaun only has bank notes for farming produce."));
            }
        });
        LoginListener.register(p -> {
            if (p.getFarming().getStorage().bottomlessBucket == null && Tool.BOTTOMLESS_BUCKET.config.get(p) > 0) {
                p.getFarming().getStorage().bottomlessBucket = new Item(22994);
                Tool.BOTTOMLESS_BUCKET.config.set(p, 1);
            }
        });
    }

    @Expose private int wateringCanCharges;
    @Expose private Item bottomlessBucket;

    private Player player;
}
