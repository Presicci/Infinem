package io.ruin.network.incoming.handlers.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import io.ruin.PersistentData;
import io.ruin.api.utils.*;
import io.ruin.cache.def.*;
import io.ruin.content.activities.event.TimedEventManager;
import io.ruin.content.areas.wilderness.DeadmanChestEvent;
import io.ruin.data.DataFile;
import io.ruin.data.dumper.DropDumper;
import io.ruin.data.dumper.ItemInfoDumper;
import io.ruin.data.dumper.NPCInfoDumper;
import io.ruin.data.impl.dialogue.DialogueLoader;
import io.ruin.data.impl.Help;
import io.ruin.data.impl.items.item_info;
import io.ruin.data.impl.items.shield_types;
import io.ruin.data.impl.items.weapon_types;
import io.ruin.data.impl.login_set;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.data.impl.npcs.npc_drops;
import io.ruin.data.impl.npcs.npc_shops;
import io.ruin.data.impl.npcs.npc_spawns;
import io.ruin.data.impl.objects.object_examines;
import io.ruin.data.impl.objects.object_spawns;
import io.ruin.data.yaml.YamlLoader;
import io.ruin.data.yaml.impl.ShopLoader;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueEnemies;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.cluescrolls.StepType;
import io.ruin.model.activities.cluescrolls.impl.CoordinateClue;
import io.ruin.model.activities.combat.barrows.BarrowsBrother;
import io.ruin.model.activities.combat.barrows.BarrowsRewards;
import io.ruin.model.activities.combat.inferno.Inferno;
import io.ruin.model.activities.combat.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.combat.raids.xeric.XericRewards;
import io.ruin.model.activities.combat.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.combat.raids.xeric.chamber.ChamberDefinition;
import io.ruin.model.activities.combat.raids.xeric.party.Party;
import io.ruin.model.activities.wilderness.StaffBounty;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.content.ActivitySpotlight;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.content.tasksystem.relics.impl.fragments.RelicFragment;
import io.ruin.model.content.tasksystem.relics.impl.fragments.RelicFragmentManager;
import io.ruin.model.content.tasksystem.relics.inter.RelicInterface;
import io.ruin.model.content.tasksystem.tasks.inter.TaskInterface;
import io.ruin.model.content.transportation.relics.DungeonHub;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.*;
import io.ruin.model.entity.player.ai.AIPlayer;
import io.ruin.model.entity.player.ai.scripts.EmoteScript;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.content.transmog.TransmogInterface;
import io.ruin.model.inter.journal.dropviewer.DropViewer;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.GoldCasket;
import io.ruin.model.item.actions.impl.ImplingJar;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemImbue;
import io.ruin.model.item.actions.impl.tradepost.ExchangeNPCOffers;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.shop.Shop;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.skills.construction.*;
import io.ruin.model.skills.construction.actions.Costume;
import io.ruin.model.skills.construction.actions.CostumeStorage;
import io.ruin.model.skills.construction.mahoganyhomes.MahoganyHomes;
import io.ruin.model.skills.construction.mahoganyhomes.MahoganyHotspot;
import io.ruin.model.skills.construction.room.impl.PortalNexus;
import io.ruin.model.skills.farming.farming_contracts.SeedPack;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.hunter.Impling;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.mining.Mining;
import io.ruin.model.skills.mining.Pickaxe;
import io.ruin.model.skills.mining.Rock;
import io.ruin.model.skills.mining.ShootingStar;
import io.ruin.model.skills.slayer.KonarData;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.handlers.CommandHandler;
import io.ruin.services.LatestUpdate;
import io.ruin.services.Punishment;
import io.ruin.services.Store;
import io.ruin.utility.AttributePair;
import io.ruin.utility.CS2Script;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.ruin.model.entity.player.GameMode.*;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/27/2021
 */
public class Administrator {

    private static Position relativeBase;

    public static boolean handleAdmin(Player player, String query, String command, String[] args) throws IOException, UnsupportedFlavorException {
        if(!player.isAdmin()) {
            return false;
        }
        boolean isCommunityManager = player.getPrimaryGroup().equals(PlayerGroup.COMMUNITY_MANAGER);
        switch(command) {
            case "setlvls":
            case "setlvl":
            case "setlevels":
            case "setlevel": {
                int id = Integer.parseInt(args[0]);
                int level = Integer.parseInt(args[1]);
                player.getStats().set(StatType.values()[id], level);
                return true;
            }
            case "geo": {
                int stage = Integer.parseInt(args[0]);
                player.getPacketSender().sendClientScript(1119, "iiii", 8, 1942, stage, 48);
                return true;
            }
            case "checkclip": {
                Tile tile = Tile.get(player.getPosition(), true);
                int clipping = tile.clipping;
                player.sendMessage("Tile Free: " + tile.isTileFree());
                player.sendMessage("Wall Free: " + tile.isWallsFree());
                player.sendMessage("Floor Free: " + tile.isFloorFree());
                player.sendMessage("Decor Free: " + tile.isFloorFreeCheckDecor());
                player.sendMessage("Decor Free: " + tile.isTileFreeCheckDecor());
                player.sendMessage("Raw Clip: " + clipping);
                return true;
            }
            case "groups": {
                for (boolean bool : player.getGroups()) {
                    player.sendMessage(bool ? "true" : "false");
                }
                return true;
            }
            case "reloadbans": {
                CentralClient.reloadBans();
                return true;
            }

            case "iunderlay": {
                player.setInterfaceUnderlay(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                return true;
            }

            case "tobfade": {
                CS2Script.TOB_HUD_FADE.sendScript(player, 500, 10, "THIS IS TEXT");
                return true;
            }

            case "cleardialogue": {
                player.getSpokenToNPCSet().clear();
                return true;
            }

            case "addtocl": {
                if (args.length < 1) {
                    return false;
                }
                player.getCollectionLog().collect(Integer.parseInt(args[0]));
                return true;
            }
            case "reloadmutes": {
                IPMute.refreshMutes();
                return true;
            }

            case "altcheck": {
                CommandHandler.forPlayer(player, query, "::altcheck target", p2 -> {
                    List<Player> players = World.getPlayerStream().filter(plyr ->
                            plyr.getUUID().equalsIgnoreCase(p2.getUUID())
                    ).collect(Collectors.toList());
                    player.sendMessage("Found " + players.size() + " alt accounts...");
                    players.forEach(plyr -> player.sendMessage(plyr.getName()));
                });
                return true;
            }

            case "uuid": {
                CommandHandler.forPlayer(player, query, "::uuid target", p2 -> Punishment.uuidBan(player, p2));
                return true;
            }

            case "checkbank": {
                CommandHandler.forPlayer(player, query, "::checkbank name", (target) -> {
                    player.dialogue(new OptionsDialogue("Viewing a players bank will clear yours.",
                            new Option("View " + target.getName() + " bank.", () -> {
                                Bank targetBank = target.getBank();
                                player.getBank().clear();
                                for (BankItem item : targetBank.getItems()) {
                                    if (item == null)
                                        continue;
                                    player.getBank().add(item);
                                }
                                player.getBank().open();
                            }),
                            new Option("No, thanks."))
                    );
                });
                return true;
            }

            case "checkinventory": {
                CommandHandler.forPlayer(player, query, "::checkinventory name", (target) -> {
                    player.dialogue(new OptionsDialogue("Viewing a players inventory will clear yours.",
                            new Option("View " + target.getName() + " inventory.", () -> {
                                Inventory inventory = target.getInventory();
                                player.getInventory().clear();
                                for (Item item : inventory.getItems()) {
                                    if (item == null)
                                        continue;
                                    player.getInventory().add(item);
                                }
                            }),
                            new Option("No, thanks."))
                    );
                });
                return true;
            }

            case "checkequipment": {
                CommandHandler.forPlayer(player, query, "::checkequipment name", (target) -> {
                    player.dialogue(new OptionsDialogue("Viewing a players equipment will clear yours.",
                            new Option("View " + target.getName() + " equipment.", () -> {
                                Equipment equipment = target.getEquipment();
                                player.getEquipment().clear();
                                for (Item item : equipment.getItems()) {
                                    if (item == null)
                                        continue;
                                    player.getEquipment().equip(item.copy());
                                }
                            }),
                            new Option("No, thanks."))
                    );
                });
                return true;
            }

            case "pmod" : {
                int adder = Integer.parseInt(args[0]);
                World.playerModifier = adder;
                return true;
            }

            case "startevent":
                //EventBossType boss;
                //switch (args[0]) {
                    /*case "corrupted":
                    case "nech":
                    case "nechryarch":
                    case "corrupt":
                        boss = EventBossType.CORRUPTED_NECHRYARCH;
                        break;
                    case "lava":
                    case "brutal":
                    case "drag":
                        boss = EventBossType.BRUTAL_LAVA_DRAGON;
                        break;
                    default:
                        boss = EventBossType.CORRUPTED_NECHRYARCH;
                }
                TimedEventManager.INSTANCE.setEvent(new EventBoss(boss));*/
                return true;

            case "stopevent":
                TimedEventManager.INSTANCE.setEvent(null);
                return true;

            case "img": {
                int id = Integer.parseInt(args[0]);
                player.sendMessage("<img=" + id + ">");
                return true;
            }

            case "item":
            case "pickup": {
                int[] ids = NumberUtils.toIntArray(args[0]);
                int amount = args.length > 1 ? NumberUtils.intValue(args[1]) : 1;
                for(int id : ids) {
                    if(id != -1)
                        player.getInventory().add(id, amount);
                }
                return true;
            }
            case "citem": {
                int id = Integer.parseInt(args[0]);
                int charges = args.length > 1 ? NumberUtils.intValue(args[1]) : 1;
                if(id != -1) {
                    Item item = new Item(id, 1);
                    item.setCharges(charges);
                    player.getInventory().add(item);
                }
                return true;
            }
            case "fi":
            case "fitem": {
                int l = command.length() + 1;
                if(query.length() > l) {
                    String search = query.substring(l).toLowerCase();
                    //int found = 0;
                    ItemDefinition exactMatch = null;
                    for(ItemDefinition def : ItemDefinition.cached.values()) {
                        if(def == null || def.name == null)
                            continue;
                        if(def.isNote() || def.isPlaceholder())
                            continue;
                        String name = def.name.toLowerCase();
                        if(name.contains(search)) {
                            player.sendFilteredMessage("    " + def.id + ": " + def.name);
                        }
                        if(name.equals(search)) {
                            if(exactMatch == null)
                                exactMatch = def;
                        }
                    }
                    if(exactMatch != null) {
                        player.sendFilteredMessage("Most relevant result for '" + search + "':");
                        player.sendFilteredMessage("    " + exactMatch.id + ": " + exactMatch.name);
                        player.getInventory().add(exactMatch.id, 1);
                    }
                    return true;
                }
                player.itemSearch("Select an item to spawn:", true, itemId -> {
                    Item item = new Item(itemId, 1);
                    player.integerInput("How many would you like to spawn:", amt -> {
                        if(item.getDef().stackable)
                            player.getInventory().add(itemId, amt);
                        else if(item.getDef().notedId != -1 && amt > 1)
                            player.getInventory().add(item.getDef().notedId, amt);
                        else
                            player.getInventory().add(itemId, amt);
                        player.sendFilteredMessage("Spawned " + amt + "x " + item.getDef().name);
                    });
                });
                return true;
            }

            case "god": {
                if (!player.isInvincible()) {
                    player.setInvincible(true);
                    player.sendMessage("You have enabled God Mode");
                } else {
                    player.setInvincible(false);
                    player.sendMessage("You have disabled God Mode");
                }
                return true;
            }

            case "testdiscord": {
                /*EventBossEmbedMessage.sendDiscordMessage(EventBossType.CORRUPTED_NECHRYARCH, "The Corrupted Nechryarch has spawned south of Wintertodt.");
                ShutdownEmbedMessage.sendDiscordMessage("test");
                Item item = new Item(4151);
                NPC npc = new NPC(415);
                RareDropEmbedMessage.sendDiscordMessage(player.getName() + " just received " + item.getDef().descriptiveName, npc.getDef().descriptiveName, item.getId());*/
                return true;
            }

            case "testclue": {
                try {
                    player.getPacketSender().sendString(203, 2, Toolkit.getDefaultToolkit()
                            .getSystemClipboard()
                            .getData(DataFlavor.stringFlavor)
                            .toString());
                    player.openInterface(InterfaceType.MAIN, 203);
                } catch (Exception e) {}
                return true;
            }

            case "testcoordclue": {
                CoordinateClue.showClueForPos(player);
                return true;
            }

            case "testinter":
                player.openInterface(InterfaceType.MAIN, 718);
                player.getPacketSender().sendModel(718, 87, 38615);
                player.getPacketSender().sendModelInformation(718, 87, 1000, 0, 0);
                return true;

            case "testmodel":
                int testmodel = Integer.parseInt(args[0]);
                player.openInterface(InterfaceType.MAIN, 718);
                player.getPacketSender().sendModel(718, 87, testmodel);
                player.getPacketSender().sendModelInformation(718, 87, 1000, 0, 0);
                return true;

            case "testitem": {
                Item item = new Item(6623, 1);
                item.putAttributes(AttributeExtensions.attributeMapTypes(
                        new AttributePair<>(AttributeTypes.UPGRADE_2, ItemEffect.RECOIL)
                ));
                player.getInventory().add(item);
                return true;
            }

            case "setstr":{
                int str = Integer.parseInt(args[0]);
                player.setStrAdder(str);
                player.sendMessage("Your strength adder is now: "+player.getStrAdder());
                return true;
            }
            case "givehcim":
                CommandHandler.forPlayer(player, query, "::givehcim playerName", p2 -> {
                    Config.IRONMAN_MODE.set(p2, 3);
                    changeForumsGroup(p2, HARDCORE_IRONMAN.groupId);
                    player.sendMessage("Gave hardcore ironman to " + p2.getName() + ".");
                });
                return true;
            case "giveultimate":
                CommandHandler.forPlayer(player, query, "::giveultimate playerName", p2 -> {
                    Config.IRONMAN_MODE.set(p2, 2);
                    changeForumsGroup(p2, ULTIMATE_IRONMAN.groupId);
                    player.sendMessage("Gave ultimate ironman to " + p2.getName() + ".");
                });
                return true;
            case "giveironman":
                CommandHandler.forPlayer(player, query, "::giveironman playerName", p2 -> {
                    Config.IRONMAN_MODE.set(p2, 1);
                    changeForumsGroup(p2, IRONMAN.groupId);
                    player.sendMessage("Gave ironman to " + p2.getName() + ".");
                });
                return true;
            case "skins":
                player.toggleAttribute("GREEN_SKIN");
                player.toggleAttribute("BLUE_SKIN");
                player.toggleAttribute("PURPLE_SKIN");
                player.toggleAttribute("BLACK_SKIN");
                player.toggleAttribute("WHITE_SKIN");
                return true;

            case "skin":
                int newSkin = Integer.parseInt(args[0]);
                player.getAppearance().colors[4] = newSkin;
                player.getAppearance().update();
                return true;

            case "dialogueanim" :
                int animid = Integer.parseInt(args[0]);
                player.dialogue(new PlayerDialogue("Testing anim: "+animid).animate(animid));
                return true;
            case "resetshrinetimer" :
                player.lastSacrifice = 0;
                return true;

            case "titleunlock" :
                player.hasCustomTitle = !player.hasCustomTitle;
                player.sendMessage("You have " + (player.hasCustomTitle ? "enabled" : "disabled") + " access to custom titles" );
                return true;

            case "giveupgrades":
                if (isCommunityManager) {
                    return false;
                }
                for (ItemImbue upgrade : ItemImbue.values()) {
                    player.getInventory().add(upgrade.nmzImbue, 1);
                }
                return true;

            case "itemdef": {
                int itemId = Integer.parseInt(args[0]);
                ItemDefinition def = ItemDefinition.get(itemId);
                if (def == null) {
                    player.sendMessage("Invalid item definition for fileId "+ itemId +".");
                } else {
                    player.sendMessage(String.format("[ItemDef] id=%d, tradeable=%b", itemId, def.tradeable));
                }
                return true;
            }

            case "givebreakables":
                if (isCommunityManager) {
                    return false;
                }
                for (ItemBreaking breaking : ItemBreaking.values()) {
                    player.getInventory().add(breaking.fixedId, 1);
                }
                return true;

            case "fetchupdate":
                LatestUpdate.fetch();
                return true;

            case "controlnpc": {
                if (args == null || args.length == 0) {
                    player.removeTemporaryAttribute("CONTROLLING_NPC");
                    player.sendMessage("NPC control cleared.");
                } else {
                    int index = Integer.parseInt(args[0]);
                    NPC npc = World.getNpc(index);
                    if (npc == null) {
                        player.sendMessage("Invalid NPC. Use index");
                        return true;
                    } else {
                        player.putTemporaryAttribute("CONTROLLING_NPC", npc);
                        player.sendMessage("You're now controlling " + npc.getDef().name + ".");
                    }
                }
                return true;
            }

            case "tbuild": {
                for (int i = 0; i < 512; i++) {
                    AIPlayer aip = new AIPlayer("Test " + i, new Position(0, 0, 0));
                    aip.init();

//                   tournament2.join(aip);
                }

//                tournament2.join(player);
                return true;
            }

            case "ipban": {
                CommandHandler.forPlayer(player, query, "::ipban playerName", p2 -> Punishment.ipBan(player, p2));
                return true;
            }

            case "ipmute": {
                CommandHandler.forPlayer(player, query, "::ipmute playerName", p2 -> Punishment.ipMute(player, p2));
                return true;
            }

            case "macban": {
                CommandHandler.forPlayer(player, query, "::macban playerName", p2 -> Punishment.macBan(player, p2));
                return true;
            }

            case "inferno": {
                new Inferno(player, Integer.parseInt(args[0]), false).start(true);
                return true;
            }
            case "test2": {
                PlayerCounter.SLAYER_TASKS_COMPLETED.increment(player, 1);
                return true;
            }
            case "dailyreset": {
                player.dailyReset();
                return true;
            }

            case "staffbounty": {
                player.dialogue(new OptionsDialogue("Would you like to toggle the event on or off?",
                        new Option("Toggle on", () -> StaffBounty.startEvent(player)),
                        new Option("Toggle off", () -> StaffBounty.stopEvent(player))
                ));
                return true;
            }

            case "aiplayer":
                player.sendMessage("Spawning AI Player...");
                AIPlayer aip = new AIPlayer("Test AI", new Position(3087, 3507, 0));
                aip.init();
                EmoteScript script = new EmoteScript(aip);
                aip.runScript(script);
                return true;

            case "broadcast":
            case "bc": {
                String message = String.join(" ", args);
                World.players.forEach(p -> p.getPacketSender().sendMessage(message, "", 14));
                return true;
            }

            case "clearcostumeroom": {
                for (CostumeStorage s : CostumeStorage.values()) {
                    Map<Costume, Item[]> stored = s.getSets(player);
                    stored.clear();
                }
                return true;
            }

            case "fillcostumeroom": {
                int count = 0;
                for (CostumeStorage s : CostumeStorage.values()) {
                    s.getSets(player).clear();
                }
                for (CostumeStorage s : CostumeStorage.values()) {
                    Map<Costume, Item[]> stored = s.getSets(player);
                    for (Costume costume : s.getCostumes()) {
                        if (stored.get(costume) != null)
                            continue;
                        Item[] set = new Item[costume.getPieces().length];
                        for (int i = 0; i < costume.getPieces().length; i++) {
                            set[i] = new Item(costume.getPieces()[i][0].getId(), 1);
                        }
                        stored.put(costume, set);
                        count++;
                    }
                }
                player.sendMessage("Added " + count + " sets.");
                return true;
            }

            case "house": {
                player.house = new House();
                return true;
            }

            case "enum": {
                EnumDefinition map = EnumDefinition.get(Integer.parseInt(args[0]));
                System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(map));
                return true;
            }

            case "struct": {
                StructDefinition struct = StructDefinition.get(Integer.parseInt(args[0]));
                System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(struct));
                return true;
            }

            case "housestyle": {
                player.house.setStyle(HouseStyle.valueOf(String.join("_", args).toUpperCase()));
                player.sendMessage("set!");
                return true;
            }

            case "resethouse": {
                player.house = new House();
                player.sendMessage("House reset");
                return true;
            }

            case "refreshregion": {
                player.getUpdater().updateRegion = true;
                return true;
            }

            case "testbuild": {
                Buildable[] objs = new Buildable[] {Buildable.CRUDE_WOODEN_CHAIR, Buildable.CRUDE_WOODEN_CHAIR, Buildable.CRUDE_WOODEN_CHAIR, Buildable.CRUDE_WOODEN_CHAIR, Buildable.CRUDE_WOODEN_CHAIR, Buildable.CRUDE_WOODEN_CHAIR};
                int count = 1;
                for (Buildable b: objs) {
                    player.getPacketSender().sendClientScript(1404, "iiisi", count++, b.getItemId(), b.getLevelReq(), b.getCreationMenuString(), b.hasLevelAndMaterials(player) ? 1 : 0);
                }
                player.getPacketSender().sendClientScript(1406, "ii", count - 1, 0);
                player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_FURNITURE_CREATION);
                return true;
            }

            case "materials": { // spawns materials for the given object
                Buildable b = Buildable.valueOf(String.join("_", args).toUpperCase());
                b.getMaterials().forEach(player.getInventory()::addOrDrop);
                return true;
            }

            case "allmats": { // spawns materials for all objects in the given room type (last entry in the list, typically the highest level object)
                RoomDefinition def = RoomDefinition.valueOf(String.join("_", args).toUpperCase());
                for (Hotspot hotspot : def.getHotspots()) {
                    if (hotspot != Hotspot.EMPTY)
                        hotspot.getBuildables()[hotspot.getBuildables().length - 1].getMaterials().forEach(player.getInventory()::addOrDrop);
                }
                return true;
            }

            case "conobj": {
                int id = Integer.parseInt(args[0]);
                ItemDefinition itemDefinition = ItemDefinition.get(id);
                player.sendMessage("Looking for objects for item " + itemDefinition.id + "...");
                List<ObjectDefinition> found = ObjectDefinition.LOADED.values().stream()
                        .filter(objectDef -> objectDef != null && objectDef.modelIds != null && Arrays.stream(objectDef.modelIds).anyMatch(model -> model == itemDefinition.inventoryModel))
                        .collect(Collectors.toList());
                if (found.size() == 0) {
                    player.sendMessage("No matches!");
                } else {
                    found.forEach(def -> {
                        player.sendMessage("Object[" + def.id +"]: \"" + def.name + "\"; Options=" + Arrays.toString(def.options));
                    });
                }
                return true;
            }

            case "fconobj": {
                String name = String.join(" ", args).toLowerCase();
                ObjectDefinition.forEach(def -> {
                    if (def != null && def.name != null && def.name.toLowerCase().contains(name) && def.options != null && def.options.length >= 5 && def.options[4] != null && def.options[4].equalsIgnoreCase("remove")) {
                        player.sendMessage(def.id + " - " + def.name + " " + Arrays.toString(def.options));
                    }
                });
                return true;
            }

            case "dbolts": {
                for (String gem: Arrays.asList("opal", "jade", "pearl", "topaz", "sapphire", "emerald", "ruby", "diamond", "dragonstone", "onyx")) {
                    String type = gem + " dragon bolts";
                    for (ItemDefinition def : ItemDefinition.cached.values()) {
                        if (def == null || def.name == null || def.isPlaceholder() || def.isNote()) continue;
                        if (def.name.toLowerCase().startsWith(type.toLowerCase())) {
                            boolean enchanted = def.name.contains("(e)");
                            item_info.Temp inf = new item_info.Temp();
                            inf.id = def.id;
                            inf.tradeable = true;
                            if (enchanted)
                                inf.examine = "Enchanted dragon crossbow bolts, tipped with " + gem + ".";
                            else
                                inf.examine = "Dragon crossbow bolts, tipped with " + gem + ".";
                            inf.ranged_strength_bonus = 122;
                            inf.equip_slot = Equipment.SLOT_AMMO;
                            inf.ranged_level = 64;
                            inf.protect_value = 600;
                            inf.ranged_ammo = enchanted ? "DRAGON_" + gem.toUpperCase() + "_BOLTS" : "DRAGON_BOLTS";
                            System.out.print(new GsonBuilder().setPrettyPrinting().create().toJson(inf));
                            System.out.println(", #" + def.name);
                        }
                    }
                }
                return true;
            }

            case "customtitle": {
                String[] parts = String.join(" ", args).split("\\|");
                player.title = new Title().prefix(parts[0]).suffix(parts[1]);
                player.getAppearance().update();
                return true;
            }

            case "title": {
                if (args == null || args.length == 0) {
                    Title.openSelection(player, true);
                    return true;
                }
                int id = Integer.parseInt(args[0]);
                player.titleId = id;
                player.title = Title.get(id); // bypasses requirements
                player.getAppearance().update();
                return true;
            }
            case "raidroom": {
                int rotation = 0;
                int layout = 0;
                if (args != null && args.length > 0)
                    rotation = Integer.parseInt(args[0]);
                if (args != null && args.length > 1)
                    layout = Integer.parseInt(args[1]);
                int finalRotation = rotation;
                int finalLayout = layout;
                Consumer<ChamberDefinition> run = definition -> {
                    Chamber chamber = definition.newChamber();
                    if (chamber == null) {
                        player.sendMessage("Failed to generate room");
                        return;
                    }
                    ChambersOfXeric raid = new ChambersOfXeric();
                    Party party = new Party(player);
                    player.raidsParty = party;
                    raid.setParty(party);
                    party.setRaid(raid);
                    chamber.setRaid(raid);
                    chamber.setRotation(finalRotation);
                    chamber.setLayout(finalLayout);
                    chamber.setLocation(0, 0, 0);
                    DynamicMap map = new DynamicMap().build(chamber.getChunks());
                    raid.setMap(map);
                    chamber.setBasePosition(new Position(map.swRegion.baseX, map.swRegion.baseY, 0));
                    chamber.onBuild();
                    chamber.onRaidStart();
                    player.getMovement().teleport(chamber.getPosition(15, 15));
                };
                OptionScroll.open(player, "Select a room type", true,
                        Arrays.stream(ChamberDefinition.values()).map(cd -> new Option(cd.getName(), () -> run.accept(cd))).collect(Collectors.toList()));
                return true;
            }

            case "preset": {
                try {
                    int id = Integer.parseInt(args[0]);
                    int index = id - 1;
                    PresetCustom preset;
                    if (index < 0 || index >= player.customPresets.length || (preset = player.customPresets[index]) == null)
                        player.sendMessage("Preset #" + id + " does not exist.");
                    else if (preset.allowSelect(player)) {
                        player.closeDialogue();
                        preset.selectFinish(player);
                    }
                } catch (Exception e) {
                    player.sendMessage("Invalid command usage. Example: [::preset 1]");
                }
                return true;
            }

            case "fonttest": {
                int childId = Integer.parseInt(args[0]);
                int fontId = Integer.parseInt(args[1]);
                player.getPacketSender().sendClientScript(135, "ii", 701 << 16 | childId, fontId);
                return true;
            }

            case "hit":
            case "hitme": {
                player.hit(new Hit().fixedDamage(Integer.parseInt(args[0])).delay(0));
                return true;
            }
            case "hittype": {
                player.hitsUpdate.add(Integer.parseInt(args[0]), player);
                //player.hit(new Hit(HitType.values()[Integer.parseInt(args[0])]).fixedDamage(1).delay(0));
                return true;
            }

            case "tutorial": {
                player.putAttribute("NEW_PLAYER", 1);
                return true;
            }

            case "debug": {
                player.sendMessage("Debug: " + ((player.debug = !player.debug) ? "ON" : "OFF"));
                return true;
            }

            case "update": {
                if (isCommunityManager) {
                    return false;
                }
                World.update(Integer.parseInt(args[0]));
                return true;
            }

            case "objanim": {
                int id = Integer.parseInt(args[0]);
                ObjectDefinition def = ObjectDefinition.get(id);
                if (def == null) {
                    player.sendMessage("Invalid id.");
                    return true;
                }
                player.sendMessage("Object uses animation " + def.unknownOpcode24);
                return true;
            }

            case "animateobj": {
                Tile.getObject(-1, player.getAbsX(), player.getAbsY(), player.getHeight(), 10, -1).animate(Integer.parseInt(args[0]));
                return true;
            }

            case "kill": {
                player.hit(new Hit().fixedDamage(player.getHp()));
                return true;
            }

            case "killnpcs": {
                for (NPC npc : player.localNpcs()) {
                    if (npc.getCombat() == null)
                        continue;
                    if (player.getCombat().canAttack(npc, true)) {
                        npc.hit(new Hit(player).fixedDamage(npc.getHp()).delay(0));
                    }
                }
                return true;
            }
            case "killplayers": {
                for (Player localPlayer : player.localPlayers()) {
                    if (localPlayer.getCombat() == null)
                        continue;
                    if (player.getCombat().canAttack(localPlayer, true)) {
                        localPlayer.hit(new Hit(player).fixedDamage(localPlayer.getHp()).delay(0));
                    }
                }
                return true;
            }

            case "pvpmagicaccuracy": {
                Hit.PVP_MAGIC_ACCURACY_MODIFIER = Double.valueOf(args[0]);
                player.sendMessage("PVP_MAGIC_ACCURACY_MODIFIER = " + Hit.PVP_MAGIC_ACCURACY_MODIFIER + ";");
                return true;
            }

            case "pvpmeleeaccuracy": {
                Hit.PVP_MELEE_ACCURACY_MODIFIER = Double.valueOf(args[0]);
                player.sendMessage("PVP_MELEE_ACCURACY_MODIFIER = " + Hit.PVP_MELEE_ACCURACY_MODIFIER + ";");
                return true;
            }

            case "settask": {
                /*if (args == null) {
                    OptionScroll.open(player, "Choose a task", SlayerTask.TASKS.entrySet().stream().map(e -> new Option(e.getKey(), () -> {
                        player.slayerTask = e.getValue();
                        player.slayerTaskRemaining = 100;
                        player.sendMessage("Task set to \"" + e.getKey() + "\"!");
                    })).sorted(Comparator.comparing(o -> o.name)).collect(Collectors.toList()));
                } else if (args[0].equals("amount")) {
                    player.slayerTaskRemaining = Integer.parseInt(args[1]);
                } else {
                    SlayerTask task = SlayerTask.TASKS.get(String.join(" ", args));
                    player.slayerTask = task;
                    player.slayerTaskRemaining = 100;
                    player.sendMessage("Task set to " + task.name + "!");
                }*/
                return true;
            }

            case "rune": {
                Rune r = Rune.valueOf(args[0].toUpperCase());
                player.getInventory().add(r.getId(), Integer.MAX_VALUE);
                return true;
            }

            case "setbase": {
                relativeBase = player.getPosition().copy();
                player.sendMessage("Base set to: " + relativeBase.toString());
                return true;
            }

            case "rel":
            case "relative": {
                int x = player.getAbsX() - relativeBase.getX();
                int y = player.getAbsY() - relativeBase.getY();
                System.out.println("{" + x + ", " + y + "},");
                return true;
            }

            case "shake": {
                player.getPacketSender().shakeCamera(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                return true;
            }

            case "zulrahdeath": {
                player.getPacketSender().sendItems(-1, 0, 525, new Item(4151, 1));
                Unlock unlock = new Unlock(602, 3, 0, 89);
                unlock.unlockRange(player, 0, 10);
                player.openInterface(InterfaceType.MAIN, 602);
                return true;
            }

            case "calcmining": {
                Pickaxe pick =  Pickaxe.find(player);
                if (pick == null) {
                    player.sendMessage("Equip a pickaxe!!");
                    return true;
                }
                for (Rock rock : Rock.values()) {
                    if (rock.multiOre != null)
                        continue;
                    double chance = Mining.chance(player, Mining.getEffectiveLevel(player), rock) / 100;
                    double oresPerTick = chance / 2;
                    double oresPerHour = oresPerTick * 60 * 100;
                    double xpPerHour = oresPerHour * rock.experience * StatType.Mining.defaultXpMultiplier;
                    System.out.println(rock + ": ores/h=" + NumberUtils.formatNumber((long) oresPerHour) + " xp/h=" + NumberUtils.formatNumber((long) xpPerHour) + " chance=" + NumberUtils.formatTwoPlaces(chance));
                }
                return true;
            }

            case "oldcasket": {
                NPCDefinition def = NPCDefinition.get(Integer.parseInt(args[0]));
                if (def == null) {
                    return true;
                }
                double baseChance = 0.3;
                double largeChance = Math.min(def.combatLevel, 100 * 2 * 2) / (250.0 * 2 * 2);
                double medChance = Math.min(def.combatLevel, 100 * 2) / (250.0 * 2) * (1 - largeChance);
                double smallChance = (1 - largeChance) * (1 - medChance);
                largeChance *= baseChance;
                medChance *= baseChance;
                smallChance *= baseChance;
                player.sendMessage("small=" + NumberUtils.formatTwoPlaces(smallChance) + ", medium=" + NumberUtils.formatTwoPlaces(medChance) + ", large=" + NumberUtils.formatTwoPlaces(largeChance));
                int smallReturn = (int) (((15000 + 35000) / 2) * smallChance);
                int mediumReturn = (int) (((35000 + 5000) / 2) * medChance);
                int largeReturn = (int) (((50000 + 75000) / 2) * largeChance);
                int perKill = (smallReturn + mediumReturn + largeReturn);
                player.sendMessage("smallgp=" + smallReturn + ", medgp=" + mediumReturn +", largegp=" + largeReturn);
                player.sendMessage("avg/kill=" + perKill + ", with wealth=" + (perKill * 1.5) + ", wealth+wild=" + (perKill * 2.25));
                return true;
            }

            case "newcasket": {
                NPCDefinition def = NPCDefinition.get(Integer.parseInt(args[0]));
                if (def == null) {
                    return true;
                }
                NPC npc = new NPC(def.id);
                GoldCasket casket = GoldCasket.getCasket(npc);
                if (casket == null) {
                    player.sendMessage("none");
                    return true;
                }
                int averageGold = (casket.getMinAmount() + casket.getMaxAmount()) / 2;
                double chance = casket.getDropChance().apply(npc);
                int goldPerKill = (int) (chance * averageGold);
                player.sendMessage("type=" + casket.toString() + ", chance=" + NumberUtils.formatTwoPlaces(chance) + ", perKill=" + goldPerKill);
                return true;
            }

            case "sproj": {
                new Projectile(Integer.parseInt(args[0]), 60, 60, 0, 300, 20, 55, 64).send(player.getAbsX(), player.getAbsY(), player.getAbsX() - 5, player.getAbsY());
                return true;
            }

            case "projloop": {
                player.startEvent(event -> {
                    int id = 0;
                    if (args.length > 0)
                        id = Integer.parseInt(args[0]);
                    while (id < GfxDefinition.LOADED.length) {
                        new Projectile(id, 60, 60, 0, 300, 20, 0, 64).send(player.getAbsX(), player.getAbsY(), player.getAbsX() - 5, player.getAbsY());
                        player.sendMessage("Sending: " + id);
                        id++;
                        event.delay(1);
                    }
                });
                return true;
            }

            case "removeplayers": {
                World.players.forEach(Player::forceLogout);
                return true;
            }

            case "checkclue": {
                Player p2 = World.getPlayer(String.join(" ", args));
                if(p2.easyClue != null)
                    player.sendMessage("Easy[" + p2.easyClue.id + "]");
                if(p2.medClue != null)
                    player.sendMessage("Med[" + p2.medClue.id + "]");
                if(p2.hardClue != null)
                    player.sendMessage("Hard[" + p2.hardClue.id + "]");
                if(p2.eliteClue != null)
                    player.sendMessage("Elite[" + p2.eliteClue.id + "]");
                if(p2.masterClue != null)
                    player.sendMessage("Master[" + p2.masterClue.id + "]");
                return true;
            }

            case "map": {
                player.getPacketSender().sendMapState(Integer.parseInt(args[0]));
                return true;
            }

            case "lms": {
                DynamicMap lmsMap = new DynamicMap()
                        .buildSw(13658, 1)
                        .buildNw(13659, 1)
                        .buildSe(13914, 1)
                        .buildNe(13915, 1);
                player.getMovement().teleport(lmsMap.swRegion.baseX, lmsMap.swRegion.baseY, 0);
                return true;
            }

            case "attribs": {
                int id = Integer.parseInt(args[0]);
                ItemDefinition def = ItemDefinition.get(id);
                if(def == null) {
                    player.sendMessage("Item " + id + " not found!");
                    return true;
                }
                if(def.attributes == null) {
                    player.sendMessage("Item " + id + " has no attributes!");
                    return true;
                }
                System.out.println("Attributes for item " + id + ":");
                def.attributes.forEach((key, value) -> System.out.println("    " + key + "=" + value));
                return true;
            }

            case "save": {
                player.sendMessage("Saving online players...");
                for(Player p : World.players)
                    PlayerFile.save(p, -1);
                player.sendMessage("DONE!");
                return true;
            }

            case "addbots": {
                int amount = Integer.parseInt(args[0]);
                int range = Integer.parseInt(args[1]);
                Bounds bounds = new Bounds(player.getPosition(), range);
                for(int i = 0; i < amount; i++)
                    PlayerBot.create(new Position(bounds.randomX(), bounds.randomY(), bounds.z), bot -> {});
                return true;
            }

            case "removebots": {
                int remove = args != null && args.length >= 1 ? Integer.parseInt(args[0]) : Integer.MAX_VALUE;
                for(Player p : World.players) {
                    if(p.getChannel().id() == null && remove-- > 0)
                        p.logoutStage = -1;
                }
                return true;
            }

            case "osw":
            case "oswiki": {
                player.getPacketSender().sendUrl("https://oldschool.runescape.wiki/?search=" + String.join("+", args), false);
                return true;
            }

            case "sound": {
                int id = Integer.parseInt(args[0]);
                int type = args.length >= 2 ? Integer.parseInt(args[1]) : 1;
                int delay = args.length >= 3 ? Integer.parseInt(args[2]) : 0;
                player.privateSound(id, type, delay);
                return true;
            }

            case "music": {
                int id = Integer.parseInt(args[0]);
                player.getPacketSender().sendMusic(id);
                return true;
            }

            /**
             * Interface commands
             */
            case "interface":
            case "inter": {
                int interfaceId = Integer.parseInt(args[0]);
                InterfaceType type = InterfaceType.MAIN;
                if(args.length == 2)
                    type = InterfaceType.valueOf(args[1].toUpperCase());
                player.putTemporaryAttribute("last_inter_cmd", interfaceId);
                player.openInterface(type, interfaceId);
                return true;
            }

            case "interloop": {
                int intId = Integer.parseInt(args[0]);
                InterfaceType type = InterfaceType.MAIN;
                player.startEvent(e -> {
                    int interfaceId = intId;
                    while (interfaceId < 650) {
                        player.putTemporaryAttribute("last_inter_cmd", interfaceId);
                        player.openInterface(type, interfaceId);
                        player.sendMessage("" + interfaceId);
                        interfaceId++;
                        e.delay(15);
                    }
                });
                return true;
            }

            case "inters": {
                InterfaceType type = InterfaceType.MAIN;
                if(args != null && args.length == 1)
                    type = InterfaceType.valueOf(args[0].toUpperCase());
                int interfaceId = (int) player.getTemporaryAttributeOrDefault("last_inter_cmd", 0);
                if(interfaceId == 548 || interfaceId == 161 || interfaceId == 164) //main screen
                    interfaceId++;
                if(interfaceId == Interface.CHAT_BAR) //chat box
                    interfaceId++;
                if(interfaceId == 156) //annoying
                    interfaceId++;
                player.putTemporaryAttribute("last_inter_cmd", interfaceId + 1);
                player.openInterface(type, interfaceId);
                player.sendFilteredMessage("Interface: " + interfaceId);
                return true;
            }

            case "ic":
            case "iconf": {
                int interfaceId = Integer.parseInt(args[0]);
                boolean recursiveSearch = args.length >= 2 && Integer.parseInt(args[1]) == 1;
                InterfaceDefinition.printConfigs(interfaceId, recursiveSearch);
                return true;
            }

            case "findinterscript": {
                int scriptId = Integer.parseInt(args[0]);
                boolean recursiveSearch = args.length >= 2 && Integer.parseInt(args[1]) == 1;
                for (int interId = 0; interId < InterfaceDefinition.COUNTS.length; interId++) {
                    Set<ScriptDefinition> s = InterfaceDefinition.getScripts(interId, recursiveSearch);
                    if (s != null && s.stream().anyMatch(def -> def.id == scriptId)) {
                        player.sendMessage("Inter " + interId + " uses script " + scriptId +"!");
                    }
                }
                return true;
            }

            case "bits": {
                int id = Integer.parseInt(args[0]);
                VarpDefinition varp = VarpDefinition.get(id);
                if(varp == null) {
                    player.sendFilteredMessage("Varp " + id + " has no bits!");
                    return true;
                }
                System.out.println("Varp: " + id);
                for(VarpbitDefinition bit : varp.bits)
                    System.out.println("    bit: " + bit.id + "  shift: " + bit.leastSigBit);
                return true;
            }

            case "v":
            case "varp": {
                int id = Integer.parseInt(args[0]);

                if(id < 0) {
                    player.sendFilteredMessage("Varp " + id + " does not exist.");
                    return true;
                }
                if (args.length < 2) {
                    player.sendFilteredMessage("Varp " + id + ": " + Config.create(id, null, false, false).get(player));
                    return true;
                }
                int value = Integer.parseInt(args[1]);
                Config.create(id, null, false, false).set(player, value);
                player.sendFilteredMessage("Updated varp " + id + "!");
                player.sendFilteredMessage("Value: " + Config.create(id, null, false, false).get(player));
                return true;
            }

            case "slaytest": {
                int UNLOCK_REWARDS_FIRST_VARP = 1076;
                int UNLOCK_REWARDS_SECOND_VARP = 1344;
                long bitpacked = 0;
                bitpacked |= 1 << 1L;
                bitpacked |= 1 << 2L;
                bitpacked |= 1 << 3L;
                bitpacked |= 1 << 4L;
                bitpacked |= 1 << 5L;
                bitpacked |= 1 << 43L;
                bitpacked |= 1 << 44L;
                bitpacked |= 1 << 45L;
                bitpacked |= 1 << 46L;
                bitpacked |= 1 << 47L;
                Config.create(UNLOCK_REWARDS_FIRST_VARP, null, false, false)
                        .set(player, (int) ((bitpacked >> 32) & 0xFFFFFFFFL));

                Config.create(UNLOCK_REWARDS_SECOND_VARP, null, false, false)
                        .set(player, (int) (bitpacked & 0xFFFFFFFFL));
                player.sendFilteredMessage("Updated slayer varps");
                return true;
            }

            case "vb":
            case "varpbit": {
                int id = Integer.parseInt(args[0]);
                if (args.length < 2) {
                    player.sendMessage("VB " + id + " value: " + Config.varpbit(id, false).get(player));
                    return true;
                }
                int value = Integer.parseInt(args[1]);
                VarpbitDefinition bit = VarpbitDefinition.get(id);
                if(bit == null) {
                    player.sendFilteredMessage("Varpbit " + id + " does not exist.");
                    return true;
                }
                Config.create(id, bit, false, false).set(player, value);
                player.sendFilteredMessage("Updated varp " + bit.varpId + " with varpbit " + id + "!");
                player.sendFilteredMessage("Value: " + Config.create(id, bit, false, false).get(player));
                return true;
            }

            case "vbloop": {
                int id = Integer.parseInt(args[0]);
                VarpbitDefinition bit = VarpbitDefinition.get(id);
                if(bit == null) {
                    player.sendFilteredMessage("Varpbit " + id + " does not exist.");
                    return true;
                }
                player.startEvent(e -> {
                    for (int index = 0; index < 300; index++) {
                        Config.create(id, bit, false, false).set(player, index);
                        player.forceText("" + index);
                        e.delay(2);
                    }
                });
                return true;
            }

            case "varbitdef": {
                int varpbit = Integer.parseInt(args[0]);
                VarpbitDefinition def = VarpbitDefinition.get(varpbit);
                if (def != null) {
                    player.sendMessage("[Varpbit Def] varp="+ def.varpId +", start="+ def.leastSigBit +", end="+ def.mostSigBit +", maxValue="+ Math.pow(2, (def.mostSigBit - def.leastSigBit)));
                } else {
                    player.sendMessage("No definition entry found for varpbit "+ varpbit +".");
                }
                return false;
            }

            case "vbs":
            case "varpbits": {
                int minId = Integer.parseInt(args[0]);
                int maxId = Integer.parseInt(args[1]);
                int value = Integer.parseInt(args[2]);
                if(minId < 0 || minId >= VarpbitDefinition.LOADED.length || maxId < 0 || maxId >= VarpbitDefinition.LOADED.length) {
                    player.sendFilteredMessage("Invalid values! Please use values between 0 and " + (VarpbitDefinition.LOADED.length - 1) + "!");
                    return true;
                }
                for(int i = minId; i <= maxId; i++) {
                    VarpbitDefinition bit = VarpbitDefinition.get(i);
                    if(bit == null)
                        continue;
                    Config.create(i, bit, false, false).set(player, value);
                }
                return true;
            }

            case "string": {
                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < args.length; i++)
                    sb.append(args[i]).append(" ");
                player.getPacketSender().sendString(Integer.parseInt(args[0]), Integer.parseInt(args[1]), sb.toString());
                return true;
            }

            case "strings": {
                int interfaceId = Integer.parseInt(args[0]);
                for(int i = 0; i < InterfaceDefinition.COUNTS[interfaceId]; i++) {
                    player.getPacketSender().sendString(interfaceId, i, "" + i);
                    player.getPacketSender().setHidden(interfaceId, i, false);
                }
                return true;
            }

            case "ichide": {
                int parentId = Integer.parseInt(args[0]);
                int minChildId = Integer.parseInt(args[1]);
                int maxChildId = args.length > 2 ? Integer.parseInt(args[2]) : minChildId;
                for(int childId = minChildId; childId <= maxChildId; childId++)
                    player.getPacketSender().setHidden(parentId, childId, true);
                return true;
            }

            case "icshow": {
                int parentId = Integer.parseInt(args[0]);
                int minChildId = Integer.parseInt(args[1]);
                int maxChildId = args.length > 2 ? Integer.parseInt(args[2]) : minChildId;
                for(int childId = minChildId; childId <= maxChildId; childId++)
                    player.getPacketSender().setHidden(parentId, childId, false);
                return true;
            }

            case "si": {
                int itemId = Integer.parseInt(args[0]);
                SkillDialogue.make(player, new SkillItem(itemId).addReq(p -> false));
                return true;
            }

            case "script": {
                int id = Integer.parseInt(args[0]);
                ScriptDefinition def = ScriptDefinition.get(id);
                if(def == null) {
                    System.err.println("Script " + id + " does not exist!");
                    return true;
                }
                def.print(System.out);
                return true;
            }

            case "dumpscripts": {
                for(int i = 0; i < 65535; i++) {
                    ScriptDefinition def = ScriptDefinition.get(i);
                    if(def == null)
                        continue;
                    try(PrintStream ps = new PrintStream(System.getProperty("user.home") + "/Desktop/script_instructions/" + i + ".txt")) {
                        def.print(ps);
                        ps.flush();
                    } catch(Exception e) {
                        ServerWrapper.logError("Failed to dump script: " + i, e);
                    }
                }
                return true;
            }

            case "findintinscript": {
                int search = Integer.parseInt(args[0]);
                for (ScriptDefinition def : ScriptDefinition.LOADED) {
                    if (def == null)
                        continue;
                    if (def.intArgs == null)
                        continue;
                    for (int i : def.intArgs)
                        if (i == search)
                            System.out.println("Found in " + def.id);
                }
                return true;
            }

            case "findstringinscript": {
                String search = String.join(" ", args).toLowerCase();
                for (ScriptDefinition def : ScriptDefinition.LOADED) {
                    if (def == null)
                        continue;
                    if (def.stringArgs == null)
                        continue;
                    for (String s : def.stringArgs) {
                        if (s == null)
                            continue;
                        if (s.toLowerCase().contains(search))
                            System.out.println("Found " + s + " in " + def.id);
                    }
                }
                return true;
            }

            case "findvarcinscript": {
                int id = Integer.parseInt(args[0]);
                for (ScriptDefinition def : ScriptDefinition.LOADED) {
                    if (def == null)
                        continue;
                    if (def.intArgs == null)
                        continue;
                    for (int i = 0; i < def.instructions.length; i++) {
                        if (def.instructions[i] == 43 && def.intArgs[i] == id) {
                            player.sendMessage("Script " + def.id + " sets varc " + id);
                        }
                    }
                }
                return true;
            }

            case "giverelic": {
                int index = Integer.parseInt(args[0]);
                player.getRelicManager().takeRelic(Relic.values()[index]);
                return true;
            }
            case "removerelic": {
                if (args == null || args.length < 1) {
                    player.sendMessage("Syntax: ::removerelic [?playername] [relic tier]");
                    return true;
                }
                Player target = args.length >= 2 ? World.getPlayer(args[0]) : player;
                if (target == null) {
                    player.sendMessage("That player doesn't exist.");
                    return true;
                }
                int index = args.length >= 2 ? Integer.parseInt(args[1]) : Integer.parseInt(args[0]);
                target.getRelicManager().removeRelic(index);
                if (target != player) {
                    player.sendMessage("You've reset " + target.getName() + "'s tier " + index + " relic.");
                    target.sendMessage("Your tier " + index + " relic has been reset.");
                }
                return true;
            }
            case "testfilter": {
                String[] filters = { "Easy", "General", "Completed" };
                String sort = "Difficulty";
                player.openInterface(InterfaceType.MAIN, 383);
                player.getPacketSender().sendTaskFilterInterface(filters, sort);
                return true;
            }

            /**
             * Npc commands
             */
            case "npc": {
                int npcId = Integer.parseInt(args[0]);
                int walkRange = 0;
                if (args.length > 1) {
                    walkRange = Integer.parseInt(args[1]);
                }
                NPCDefinition def = NPCDefinition.get(npcId);
                if(def == null) {
                    player.sendMessage("Invalid npc id: " + npcId);
                    return true;
                }
                new NPC(npcId).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), walkRange).getCombat().setAllowRespawn(false);
                return true;
            }

            case "npcme": {
                int npcId = Integer.parseInt(args[0]);
                int walkRange = 0;
                if (args.length > 1) {
                    walkRange = Integer.parseInt(args[1]);
                }
                NPCDefinition def = NPCDefinition.get(npcId);
                if(def == null) {
                    player.sendMessage("Invalid npc id: " + npcId);
                    return true;
                }
                NPC npc = new NPC(npcId);
                npc.spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), walkRange);
                npc.setPlayerSpecific(player);
                return true;
            }
            /*
             * Command that spawns an npc and writes that spawn to a file
             * Args [id, walkRange]
             */
            case "spawn": {
                int npcId = Integer.parseInt(args[0]);
                int walkRange = args.length > 1 ? Integer.parseInt(args[1]) : 0;
                NPCDefinition def = NPCDefinition.get(npcId);
                if(def == null) {
                    player.sendMessage("Invalid npc id: " + npcId);
                    return true;
                }
                new NPC(npcId).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), walkRange);
                try {
                    List<String> list = Files.readAllLines(Paths.get("data/npcs/spawns/command_spawns.json"));
                    // If a prior entry exists, remove its trailing }
                    list.remove(list.size() - 1);
                    // Remove the ] and the end of file
                    list.remove(list.size() - 1);
                    // Replace the old } with a },
                    list.add(list.size(), "\t},");
                    // Start writing our new entry
                    list.add(list.size(), "\t{");
                    list.add(list.size(), "\t\t\"name\": \"" + def.name + "\",");
                    list.add(list.size(), "\t\t\"id\": " + def.id + ",");
                    list.add(list.size(), "\t\t\"x\": " + player.getPosition().getX() + ",");
                    list.add(list.size(), "\t\t\"y\": " + player.getPosition().getY() + ",");
                    list.add(list.size(), "\t\t\"z\": " + player.getPosition().getZ() + ",");
                    list.add(list.size(), "\t\t\"direction\": \"S\",");
                    list.add(list.size(), "\t\t\"walkRange\": " + walkRange + "");
                    list.add(list.size(), "\t}");
                    // Write the closing ]
                    list.add(list.size(), "]");
                    Files.write(Paths.get("data/npcs/spawns/command_spawns.json"), list);
                } catch(IOException e) {
                    player.sendMessage("Could not write spawn to file.");
                }
                return true;
            }
            case "fn":
            case "fnpc": {
                String search = command.equals("fn") ? query.substring(3) : query.substring(5);
                int combat = -1;
                if(search.contains(":")) {
                    String[] s = search.split(":");
                    search = s[0];
                    combat = Integer.parseInt(s[1]);
                }
                for(NPCDefinition def : NPCDefinition.cached.values()) {
                    if(def != null && def.name.toLowerCase().contains(search.toLowerCase()) && (combat == -1 || def.combatLevel == combat))
                        player.sendMessage(def.id + " (" + def.name + "): combat=" + def.combatLevel + " options=" + Arrays.toString(def.options) +" size=" + def.size);
                }
                return true;
            }

            case "fvn":
            case "fvnpc": {
                int id = Integer.parseInt(args[0]);
                for (NPCDefinition def : NPCDefinition.cached.values()) {
                    if (def != null && def.id == id) {
                        player.sendMessage("Main ID: " + def.id + " (" + def.name + "): combat=" + def.combatLevel + " options=" + Arrays.toString(def.options) +" size=" + def.size);
                        break;
                    }
                }
                for (NPCDefinition def : NPCDefinition.cached.values()) {
                    if (def.showIds == null || def.showIds.length == 0)
                        continue;
                    for (int vId : def.showIds) {
                        if (vId == id) {
                            player.sendMessage("varp ID: " + def.id + " (" + def.name + "): combat=" + def.combatLevel + " options=" + Arrays.toString(def.options) +" size=" + def.size);
                        }
                    }
                }
                return true;
            }

            case "pnpc": {
                int npcId = Integer.parseInt(args[0]);
                if(npcId > 0) {
                    NPCDefinition def = NPCDefinition.get(npcId);
                    if(def == null) {
                        player.sendMessage("Invalid npc id: " + npcId);
                        return true;
                    }
                    player.putTemporaryAttribute("LAST_PNPC", npcId);
                    player.getAppearance().setNpcId(npcId);
                    player.sendMessage(def.name + " " + def.size);
                } else {
                    player.getAppearance().setNpcId(-1);
                }
                player.getAppearance().update();
                return true;
            }

            case "pnpcs": {
                Integer lastId = (Integer) player.getTemporaryAttribute("LAST_PNPC");
                if(lastId == null)
                    lastId = 0;
                NPCDefinition def = NPCDefinition.get(lastId);
                if(def == null) {
                    player.sendMessage("Invalid npc id: " + lastId);
                    return true;
                }
                player.getAppearance().setNpcId(lastId);
                player.sendMessage("pnpc: " + lastId);
                player.putTemporaryAttribute("LAST_PNPC", lastId + 1);
                player.getAppearance().update();
                return true;
            }

            case "removenpc": {
                int id = Integer.parseInt(args[0]);
                int count = 0;
                for (NPC npc : player.localNpcs()) {
                    if (npc.getId() == id && !npc.defaultSpawn) {
                        npc.remove();
                        count++;
                    }
                }
                player.sendMessage("Removed " + count + " NPCs with id " + id + ".");
                return true;
            }

            case "calc":
            case "calculate": {
                int id = Integer.parseInt(args[0]);
                NPCDefinition def = NPCDefinition.get(id);
                if(def == null) {
                    player.sendMessage("Invalid npc id: " + id);
                    return true;
                }
                if(def.lootTable == null) {
                    player.sendMessage(def.name + " doesn't have a loot table.");
                    return true;
                }
                def.lootTable.calculate(def.name + " Loot Probability Table");
                return true;
            }
            /*
             * Command that spawns an npc and writes that spawn to a file
             * Args [id, walkRange]
             */
            case "ispawn": {
                int itemId = Integer.parseInt(args[0]);
                int amt = args.length > 1 ? Integer.parseInt(args[1]) : 0;
                ItemDefinition def = ItemDefinition.get(itemId);
                if(def == null) {
                    player.sendMessage("Invalid item id: " + itemId);
                    return true;
                }
                new GroundItem(new Item(itemId, amt)).position(player.getPosition()).spawnWithRespawn(120);
                try {
                    List<String> list = Files.readAllLines(Paths.get("data/items/spawns/command_item_spawns.json"));
                    // If a prior entry exists, remove its trailing }
                    list.remove(list.size() - 1);
                    // Remove the ] and the end of file
                    list.remove(list.size() - 1);
                    // Replace the old } with a },
                    list.add(list.size(), "\t},");
                    // Start writing our new entry
                    list.add(list.size(), "\t{");
                    list.add(list.size(), "\t\t\"itemName\": \"" + def.name + "\",");
                    list.add(list.size(), "\t\t\"amount\": " + amt + ",");
                    list.add(list.size(), "\t\t\"id\": " + def.id + ",");
                    list.add(list.size(), "\t\t\"x\": " + player.getPosition().getX() + ",");
                    list.add(list.size(), "\t\t\"y\": " + player.getPosition().getY() + ",");
                    list.add(list.size(), "\t\t\"z\": " + player.getPosition().getZ() + ",");
                    list.add(list.size(), "\t\t\"respawnSeconds\": 60");
                    list.add(list.size(), "\t}");
                    // Write the closing ]
                    list.add(list.size(), "]");
                    Files.write(Paths.get("data/items/spawns/command_item_spawns.json"), list);
                } catch(IOException e) {
                    player.sendMessage("Could not write spawn to file.");
                }
                return true;
            }

            case "addnpc": { // TODO support more options
                int id = Integer.parseInt(args[0]);
                NPCDefinition def = NPCDefinition.get(id);
                if(def == null) {
                    player.sendMessage("Invalid npc id: " + id);
                    return true;
                }
                int range = args.length > 1 ? Integer.parseInt(args[1]) : 3;
                System.out.println("{\"id\": " + id + ", \"x\": " + player.getAbsX() + ", \"y\": " + player.getAbsY() + ", \"z\": " + player.getHeight() +", \"walkRange\": " + range + "}, // " + def.name);
                new NPC(id).spawn(player.getPosition());
                return true;
            }

            case "findspawnednpc": {
                int id = Integer.parseInt(args[0]);
                World.npcs.forEach(npc -> {
                    if(npc.getId() == id) {
                        player.sendMessage("Found at " + npc.getPosition());
                    }
                });
                return true;
            }

            case "npcanims": {
                int sourceId = Integer.parseInt(args[0]);
                NPCDefinition sourceDef = NPCDefinition.get(sourceId);
                if(sourceDef == null) {
                    player.sendMessage("Invalid NPC!");
                    return true;
                }
                player.sendMessage("Stand: " + sourceDef.standAnimation + " Walk: " + sourceDef.walkAnimation);
                SortedSet<Integer> results = AnimationDefinition.findAnimationsWithSameRigging(sourceDef.walkAnimation, sourceDef.standAnimation, sourceDef.walkBackAnimation, sourceDef.walkLeftAnimation, sourceDef.walkRightAnimation);
                if(results == null) {
                    player.sendMessage("Nothing found!");
                    return true;
                }
                System.out.println(Arrays.toString(results.toArray()));
                return true;
            }

            case "similaranims": {
                int sourceId = Integer.parseInt(args[0]);
                AnimationDefinition source = AnimationDefinition.LOADED[sourceId];
                SortedSet<Integer> results = AnimationDefinition.findAnimationsWithSameRigging(sourceId);
                if(results == null) {
                    player.sendMessage("Nothing found!");
                    return true;
                }
                System.out.println("Same rigging: " + Arrays.toString(results.toArray()));
                results.clear();
                for (int id = 0; id < AnimationDefinition.LOADED.length; id++) {
                    AnimationDefinition def = AnimationDefinition.LOADED[id];
                    if (def == null || def.frameData == null) continue;
                    if (def.frameData[0] == source.frameData[0]) { // TODO consider checking other frames and outputting a % match?
                        results.add(id);
                    }
                }
                System.out.println("Similar frames: " + Arrays.toString(results.toArray()));
                return true;
            }

            case "dumpnpcanims": {
                try(BufferedWriter bw = new BufferedWriter(new FileWriter("npcanims.txt"))) {
                    bw.write("id\tname\tanims");
                    bw.newLine();
                    for(NPCDefinition def : NPCDefinition.cached.values()) {
                        bw.write(String.valueOf(def.id));
                        bw.write("\t");
                        bw.write(def.name);
                        bw.write("\t");
                        SortedSet<Integer> anims = AnimationDefinition.findAnimationsWithSameRigging(def.walkAnimation, def.standAnimation, def.walkBackAnimation, def.walkLeftAnimation, def.walkRightAnimation);
                        if(anims == null)
                            bw.write("[none found]");
                        else
                            bw.write(Arrays.toString(anims.toArray()));
                        bw.newLine();
                        bw.flush();
                    }
                } catch(IOException e) {
                    ServerWrapper.logError("Failed to dump NPCAnims", e);
                }
                player.sendMessage("Done");
            }

            case "resettasks": {
                player.getTaskManager().resetTasks();
                return true;
            }

            case "cyclespotlight": {
                ActivitySpotlight.cycleSpotlight();
                return true;
            }

            case "currentspotlight": {
                player.sendMessage(ActivitySpotlight.activeSpotlight.toString().toLowerCase());
                return true;
            }

            case "reloadnpcs": {
                World.npcs.forEach(NPC::remove); //todo fix this
                DataFile.reload(player, npc_spawns.class);
                return true;
            }
            case "reloadobjects": {
                DataFile.reload(player, object_spawns.class);
                return true;
            }
            case "reloadobjectinfo": {
                DataFile.reload(player, object_examines.class);
                return true;
            }
            case "reloaditeminfo": {
                item_info.loadFromDatabase();
                return true;
            }

            case "randomitems": {
                List<Item> randomItems = Lists.newArrayList();
                while(randomItems.size() < 28){
                    Item item = new Item(RandomUtils.nextInt(0, 20000), 1000);
                    if(item.getDef().stackable && item.getDef().tradeable && !item.getDef().free){
                        randomItems.add(item);
                    }
                }
                randomItems.forEach(player.getInventory()::add);

                return true;
            }

            case "cluedrops": {
                if(args == null || args.length > 0) {
                    switch (args[0]) {
                        case "beginner":
                            ClueType.showClueDrops(player, ClueType.BEGINNER);
                            break;
                        case "easy":
                            ClueType.showClueDrops(player, ClueType.EASY);
                            break;
                        case "medium":
                            ClueType.showClueDrops(player, ClueType.MEDIUM);
                            break;
                        case "hard":
                            ClueType.showClueDrops(player, ClueType.HARD);
                            break;
                        case "elite":
                            ClueType.showClueDrops(player, ClueType.ELITE);
                            break;
                        default:
                            ClueType.showClueDrops(player, ClueType.MASTER);
                            break;
                    }
                    return true;
                }
            }

            case "impdrops": {
                ImplingJar.BABY_IMPLING_JAR.getLootTable().showDrops(player, "Baby Impling");
                return true;
            }

            /**
             * Wiki dumping
             */
            case "dumpitem": {
                ItemInfoDumper.dump(Integer.parseInt(args[0]));
                return true;
            }
            case "dumpitems": {
                ItemInfoDumper.dump(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                return true;
            }
            case "dumpnpc": {
                NPCInfoDumper.dump(Integer.parseInt(args[0]));
                return true;
            }
            case "dumpnpcs": {
                NPCInfoDumper.dump(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                return true;
            }

            /**
             * Drop commands
             */
            case "dumpdrops": {
                DropDumper.dump(String.join("_", args));
                return true;
            }

            case "reloaddrops": {
                NPCDefinition.forEach(def -> def.lootTable = null);
                DataFile.reload(player, npc_drops.class);
                return true;
            }

            /**
             * Item commands
             */
            case "clear":
            case "empty": {
                player.dialogue(
                        new MessageDialogue("Warning! This removes all items from your inventory"),
                        new OptionsDialogue("Are you sure you wish to preform this action?",
                                new Option("Yes", () -> player.getInventory().clear()),
                                new Option("No", () -> player.sendFilteredMessage("You did not empty your inventory.")))
                );
                return true;
            }


            case "b":
            case "bank":
            case "openbank": {
                player.getBank().open();
                return true;
            }

            case "reloaditems": {
                new Thread(() -> {
                    player.sendMessage("Reloading item info...");
                    DataFile.reload(player, shield_types.class);
                    DataFile.reload(player, weapon_types.class);
                    item_info.loadFromDatabase();
                    player.sendMessage("Done!");
                }).start();
                return true;
            }

            case "reloadshops": {
                DataFile.reload(player, npc_shops.class);
                YamlLoader.load(Arrays.asList(new ShopLoader()));
                return true;
            }

            case "convertshops": {


                ShopManager.getShops().values().stream().filter(shop -> !shop.generatedByBuilder).forEach(shop -> {
                    String fileName = shop.title.replace(" ", "_") + ".yaml";
                    try(FileWriter fw = new FileWriter(new File("F:/convshops/" + fileName))) {

                        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

                        objectMapper.writeValue(fw, shop);

                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                });
                return true;
            }

            case "namespawns": {
                npc_spawns.allSpawns.forEach((file, spawns) -> {
                    spawns.forEach(spawn -> spawn.name = NPCDefinition.get(spawn.id).name);
                    try {
                        JsonUtils.toFile(new File(file), JsonUtils.toPrettyJson(spawns));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return true;
            }

            case "pinv": {
                StringBuilder sb = new StringBuilder();
                for(Item item : player.getInventory().getItems()) {
                    if(item != null)
                        sb.append(item.getId()).append(",");
                }
                System.out.println(sb.substring(0, sb.length() - 1));
                return true;
            }

            case "wipe": {
                String name = query.substring(command.length() + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null) {
                    player.sendMessage("Could not find player: " + name);
                    return true;
                }
                player.dialogue(
                        new MessageDialogue("Are you sure you want to wipe player: " + p2.getName() + "?"),
                        new OptionsDialogue(
                                new Option("Yes", () -> { //todo log this
                                    if(!p2.isOnline()) {
                                        player.sendMessage(p2.getName() + " is no longer online!");
                                        return;
                                    }
                                    p2.getInventory().clear();
                                    p2.getEquipment().clear();
                                    p2.getBank().clear();
                                }),
                                new Option("No", player::closeDialogue)
                        )
                );
                return true;
            }

            /**
             * Map commands
             */
            case "c":
            case "pos":
            case "loc":
            case "coords": {
                player.sendMessage("Abs: " + player.getPosition().getX() + ", " + player.getPosition().getY() + ", " + player.getPosition().getZ());
                StringSelection stringSelection = new StringSelection(player.getPosition().getX() + ", " + player.getPosition().getY() + ", " + player.getPosition().getZ());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                return true;
            }

            case "chunk": {
                int chunkX = player.getPosition().getChunkX();
                int chunkY = player.getPosition().getChunkY();
                int chunkAbsX = chunkX << 3;
                int chunkAbsY = chunkY << 3;
                int localX = player.getPosition().getX() - chunkAbsX;
                int localY = player.getPosition().getY() - chunkAbsY;
                Region region = Region.get(chunkAbsX, chunkAbsY);
                int pointX = (player.getPosition().getX() - region.baseX) / 8;
                int pointY = (player.getPosition().getY() - region.baseY) / 8;
                player.sendMessage("Chunk: " + chunkX + ", " + chunkY);
                player.sendMessage("    abs = " + chunkAbsX + ", " + chunkAbsY);
                player.sendMessage("    local = " + localX + ", " + localY);
                player.sendMessage("    points =  " + pointX + ", " + pointY);
                player.sendMessage("    hash =  " + (((chunkAbsX >> 3) << 16) + chunkY));
                return true;
            }

            case "region": {
                Region region;
                if(args == null || args.length == 0)
                    region = player.getPosition().getRegion();
                else
                    region = Region.get(Integer.parseInt(args[0]));
                player.sendMessage("Region: " + region.id);
                player.sendMessage("    base = " + region.baseX + "," + region.baseY);
                player.sendMessage("    empty = " + region.empty);
                return true;
            }

            case "toregion": {
                int region = (Integer.parseInt(args[0]));
                int x = ((region << 6) >> 8);
                int y = (region << 6);

                player.getMovement().teleport(x, y, player.getHeight());
                return true;
            }

            case "clipping": {
                Tile tile = Tile.get(player.getAbsX(), player.getAbsY(), player.getHeight(), false);
                player.sendMessage("Clipping: " + (tile == null ? -1 : tile.clipping));
                System.out.println(tile.clipping & ~RouteFinder.UNMOVABLE_MASK);
                return true;
            }

            case "tp":
            case "tele":
            case "teleport": {
                int x, y, z;
                try {
                    x = Integer.parseInt(args[0]);
                    y = Integer.parseInt(args[1]);
                    if(args.length > 2)
                        z = Math.max(0, Math.min(3, Integer.parseInt(args[2])));
                    else
                        z = player.getPosition().getZ();
                } catch(Exception e) {
                    int l = command.length() + 1;
                    if(query.length() <= l)
                        return true;
                    String loc = query.substring(l).trim();
                    Location location = Location.find(loc);
                    if(location == null) {
                        player.sendMessage("Invalid teleport location: " + loc);
                        return true;
                    }
                    x = location.x;
                    y = location.y;
                    z = location.z;
                }
                int regionId = Region.getId(x, y);
                if(regionId < 0 || regionId >= Region.LOADED.length) {
                    player.sendMessage("Invalid teleport coordinates: " + x + ", " + y + ", " + z);
                    return true;
                }
                player.getMovement().teleport(x, y, z);
                return true;
            }

            case "height": {
                int z = Integer.parseInt(args[0]);
                if(z < 0)
                    z = 0;
                else if(z > 3)
                    z = 3;
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), z);
                return true;
            }

            case "down": {
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), Math.max(0, player.getHeight() - 1));
                return true;
            }

            case "up": {
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), Math.min(3, player.getHeight() + 1));
                return true;
            }

            case "ix": {
                int increment = Integer.parseInt(args[0]);
                int x = player.getPosition().getX() + increment;
                int y = player.getPosition().getY();
                int z = player.getPosition().getZ();
                player.getMovement().teleport(x, y, z);
                return true;
            }

            case "iy": {
                int increment = Integer.parseInt(args[0]);
                int x = player.getPosition().getX();
                int y = player.getPosition().getY() + increment;
                int z = player.getPosition().getZ();
                player.getMovement().teleport(x, y, z);
                return true;
            }

            case "iz": {
                int increment = Integer.parseInt(args[0]);
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                int z = player.getPosition().getZ() + increment;
                player.getMovement().teleport(x, y, z);
                return true;
            }

            case "todung": {
                player.getMovement().teleport(player.getAbsX(), player.getAbsY() + 6400, player.getHeight());
                return true;
            }

            case "fromdung": {
                player.getMovement().teleport(player.getAbsX(), player.getAbsY() - 6400, player.getHeight());
                return true;
            }

            /**
             * Object commands
             */
            case "obj": {
                int id = Integer.parseInt(args[0]);
                int type = 10;
                if(args.length > 1)
                    type = Integer.parseInt(args[1]);
                int direction = 0;
                if(args.length > 2)
                    direction = Integer.parseInt(args[2]);
                player.getPacketSender().sendCreateObject(id, player.getAbsX(), player.getAbsY(), player.getHeight(), type, direction);
                return true;
            }

            case "addobj": {
                int id = Integer.parseInt(args[0]);
                int type = 10;
                if(args.length > 1)
                    type = Integer.parseInt(args[1]);
                int direction = 0;
                if(args.length > 2)
                    direction = Integer.parseInt(args[2]);
                GameObject.spawn(id, player.getPosition(), type, direction);
                return true;
            }

            case "objs": {
                Tile tile = Tile.get(player.getAbsX(), player.getAbsY(), player.getHeight(), false);
                if(tile == null || tile.gameObjects == null) {
                    player.sendMessage("No objects.");
                    return true;
                }
                if(tile.gameObjects.isEmpty()) {
                    player.sendMessage("No objects?");
                    return true;
                }
                tile.checkActive();
                player.sendMessage("Tile active: " + tile.isActive());
                for(GameObject object : tile.gameObjects) {
                    int varpId;
                    int varpbitId;
                    if(object.id == -1) {
                        varpId = -1;
                        varpbitId = -1;
                    } else {
                        varpId = object.getDef().varpId;
                        varpbitId = object.getDef().varpBitId;
                    }
                    player.sendMessage("id=" + object.id + "  x=" + object.x + "  y=" + object.y + "  z=" + object.z + "  type=" + object.type + "  dir=" + object.direction + " varpbitId=" + varpbitId + " varpId=" + varpId + " clipType="+ object.getDef().clipType);
                    //System.out.println("{" + object.id + ", " + object.x + ", " + object.y + ", " + object.z + ", " + object.type + ", " + object.direction + "},");
                    System.out.println("obj(" + object.id + ", " + object.x + ", " + object.y + ", " + object.z + ", " + object.type + ", " + object.direction + ").remove();");
                }
                return true;
            }

            case "fobj": {
                String search = query.substring(5);
                int number = -1;
                try {
                    number = Integer.parseInt(search);
                } catch (Exception e) {

                }
                int finalNumber = number;
                ObjectDefinition.forEach(def -> {
                    if(def != null && def.name != null && def.name.toLowerCase().contains(search)) {
                        player.sendMessage(def.id + " (" + def.name + ") options=" + Arrays.toString(def.options));
                        System.out.println(def.id + " (" + def.name + ") options=" + Arrays.toString(def.options));
                    }
                    if(finalNumber != -1 && def != null && def.unknownOpcode24 == finalNumber)
                        player.sendMessage(def.id + " uses anim " + search);
                    if (finalNumber != -1 && def.modelIds != null && Arrays.stream(def.modelIds).anyMatch(i -> finalNumber == i))
                        player.sendMessage(def.id + " uses model " + search);
                });
                return true;
            }

            case "findinregion": {
                int id = Integer.parseInt(args[0]);
                for (Region region : player.getRegions())
                    for(int x = 0; x < 64; x++)
                        for(int y = 0; y < 64; y++)
                            for(int z = 0; z < 4; z++) {
                                Tile t = region.getTile(region.baseX + x, region.baseY + y, z, false);
                                if(t == null)
                                    continue;
                                if(t.gameObjects != null && t.gameObjects.stream().anyMatch(o -> o.id == id)) {
                                    player.sendMessage("Found at " + (region.baseX + x) + "," + (region.baseY + y) + "," + z);
                                }
                                if(t.gameObjects != null && t.gameObjects.stream().anyMatch(o -> o.id != -1 && o.getDef().showIds != null && Arrays.stream(o.getDef().showIds).anyMatch(i -> i == id))) {
                                    player.sendMessage("Found <col=ff0000>in container</col> at " + (region.baseX + x) + "," + (region.baseY + y) +"," + z);
                                }
                            }
                return true;
            }

            case "findinmap": {
                int id = Integer.parseInt(args[0]);
                CompletableFuture.runAsync(() -> {
                    for(Region region : Region.LOADED) {
                        if(region == null)
                            continue;
                        for(int x = 0; x < 64; x++)
                            for(int y = 0; y < 64; y++)
                                for(int z = 0; z < 4; z++) {
                                    Tile t = region.getTile(region.baseX + x, region.baseY + y, z, false);
                                    if(t == null)
                                        continue;
                                    if(t.gameObjects != null && t.gameObjects.stream().anyMatch(o -> o.id == id)) {
                                        player.sendMessage("Found at " + (region.baseX + x) + "," + (region.baseY + y) + "," + z);
                                    }
                                    if(t.gameObjects != null && t.gameObjects.stream().anyMatch(o -> o.getDef().showIds != null && Arrays.stream(o.getDef().showIds).anyMatch(i -> i == id))) {
                                        player.sendMessage("Found <col=ff0000>in container</col> at " + (region.baseX + x) + "," + (region.baseY + y) +"," + z);
                                    }
                                }
                    }
                    player.sendMessage("Finished.");
                });
                return true;
            }
            case "upgrade": {
                player.getUpgradeMachine().sendInterface();
                return true;
            }
            case "maxplayer": {
                String name = query.substring(command.length() + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null)
                    player.sendMessage("Could not find player: " + name);
                int xp = Stat.xpForLevel(99);
                for (int i = 0; i < StatType.values().length; i ++) {
                    Stat stat = p2.getStats().get(i);
                    stat.currentLevel = stat.fixedLevel = 99;
                    stat.experience = xp;
                    stat.updated = true;
                }
                p2.getCombat().updateLevel();
                p2.getAppearance().update();
                player.sendMessage("Maxed player: " + p2.getName());
                return true;
            }

            case "containerobjs": {
                ObjectDefinition def = ObjectDefinition.get(Integer.parseInt(args[0]));
                if(def == null)
                    return true;
                for(int i = 0; i < def.showIds.length; i++) {
                    int id = def.showIds[i];
                    ObjectDefinition obj = ObjectDefinition.get(id);
                    if(obj == null)
                        continue;
                    System.out.println("[" + i + "]: \"" + obj.name + "\" #" + id + "; options=" + Arrays.toString(obj.options));
                }
                return true;
            }

            /*
             * Stat commands
             */
            case "master": {
                if (isCommunityManager) {
                    return false;
                }
                int xp = Stat.xpForLevel(99);
                for (int i = 0; i < StatType.values().length; i ++) {
                    Stat stat = player.getStats().get(i);
                    stat.currentLevel = stat.fixedLevel = 99;
                    stat.experience = xp;
                    stat.updated = true;
                }

                player.getCombat().updateLevel();
                player.getAppearance().update();
                return true;
            }

            case "resetlevels": {
                if (isCommunityManager) {
                    return false;
                }
                int xp = Stat.xpForLevel(1);
                for (int i = 0; i < StatType.values().length; i ++) {
                    Stat stat = player.getStats().get(i);
                    stat.currentLevel = stat.fixedLevel = 1;
                    stat.experience = xp;
                    stat.updated = true;
                }

                player.getCombat().updateLevel();
                player.getAppearance().update();
                return true;
            }

            case "lvl": {
                if (isCommunityManager) {
                    return false;
                }
                StatType type = StatType.get(args[0]);
                int id = type == null ? Integer.parseInt(args[0]) : type.ordinal();
                int level = Integer.parseInt(args[1]);
                if(level < 1 || level > 255 || (id == 3 && level < 10)) {
                    player.sendMessage("Invalid level!");
                    return true;
                }
                Stat stat = player.getStats().get(id);
                stat.currentLevel = level;
                stat.fixedLevel = Math.min(99, level);
                stat.experience = Stat.xpForLevel(Math.min(99, level));
                stat.updated = true;
                if(id == 5)
                    player.getPrayer().deactivateAll();
                player.getCombat().updateLevel();
                //not needed? Item wep = player.getEquipment().get(3);
                //not needed? if(wep != null)
                //not needed?     wep.update();
                return true;
            }

            case "poison": {
                player.poison(6);
                return true;
            }

            /**
             * Player updating commands
             */
            case "anim":
            case "emote": {
                int id = Integer.parseInt(args[0]);
                //if(id != -1 && AnimationDefinition.get(id) == null) {
                //    player.sendMessage("Invalid Animation: " + id);
                //    return true;
                //}
                int delay = 0;
                if(args.length > 1)
                    delay = Integer.parseInt(args[1]);
                player.animate(id, delay);
                return true;
            }

            case "animloop": {
                player.startEvent(event -> {
                    int id = 0;
                    if (args.length > 0)
                        id = Integer.parseInt(args[0]);
                    while (id < AnimationDefinition.LOADED.length) {
                        player.animate(id);
                        player.sendMessage("Sending: " + id);
                        id++;
                        event.delay(2);
                        player.resetAnimation();
                        event.delay(1);
                    }
                });
                return true;
            }

            case "sgfx":
            case "gfx":
            case "graphics": {
                int id = Integer.parseInt(args[0]);
                //if(id != -1 && GfxDefinition.get(id) == null) {
                //    player.sendMessage("Invalid Graphics: " + id + ". max valid: " + (GfxDefinition.LOADED.length - 1));
                //    return true;
                //}
                int height = 0;
                if(args.length > 2)
                    height = Integer.parseInt(args[1]);
                int delay = 0;
                if(args.length > 1)
                    delay = Integer.parseInt(args[2]);
                if (command.startsWith("s"))
                    World.sendGraphics(id, height, delay, player.getPosition());
                else
                    player.graphics(id, height, delay);
                return true;
            }

            case "iteminfo": {
                ItemDefinition def = ItemDefinition.get(Integer.parseInt(args[0]));
                if (def == null) {
                    player.sendMessage("Invalid id!");
                    return true;
                }
                player.sendMessage("inventory=" +def.inventoryModel);
                player.sendMessage("origcolors=" + Arrays.toString(def.colorFind));
                player.sendMessage("replacecolors=" + Arrays.toString(def.colorReplace));
                player.sendMessage("model=" + def.anInt1504);
                return true;
            }

            case "gfxanim": {
                GfxDefinition def = GfxDefinition.get(Integer.parseInt(args[0]));
                if(def == null) {
                    player.sendMessage("Invalid id.");
                    return true;
                }
                player.sendMessage("Gfx " + def.id + " uses animation " + def.animationId);
                return true;
            }

            case "gfxmodel": {
                GfxDefinition def = GfxDefinition.get(Integer.parseInt(args[0]));
                if(def == null) {
                    player.sendMessage("Invalid id.");
                    return true;
                }
                player.sendMessage("Gfx " + def.id + " uses model " + def.modelId);
                return true;
            }

            case "findgfxa": {
                int animId = Integer.parseInt(args[0]);
                player.sendMessage("Finding gfx using anim " + animId + "...");
                Arrays.stream(GfxDefinition.LOADED)
                        .filter(Objects::nonNull)
                        .filter(def -> def.animationId == animId)
                        .forEachOrdered(def -> player.sendMessage("Found: " + def.id));
                return true;
            }

            case "findgfxm": {
                int model = Integer.parseInt(args[0]);
                player.sendMessage("Finding gfx using model " + model + "...");
                Arrays.stream(GfxDefinition.LOADED)
                        .filter(Objects::nonNull)
                        .filter(def -> def.modelId == model)
                        .forEachOrdered(def -> player.sendMessage("Found: " + def.id));
                return true;
            }

            case "objmodels": {
                ObjectDefinition obj = ObjectDefinition.get(Integer.parseInt(args[0]));
                if (obj == null) {
                    player.sendMessage("Invalid id!");
                    return true;
                }
                player.sendMessage(Arrays.toString(obj.modelIds));
                return true;
            }
            case "fobjmodel": {
                int modelId = Integer.parseInt(args[0]);
                player.sendMessage("Searching for objs that use model: " + modelId);
                ObjectDefinition.forEach(d -> {
                    if (d.modelIds == null || d.modelIds.length <= 0) return;
                    if (Arrays.stream(d.modelIds).anyMatch(mid -> mid == modelId))
                        player.sendMessage(d.id + ":" + d.name);
                });
            }

            case "findo":
            case "findobj": {
                ObjectDefinition.LOADED.values().stream()
                        .filter(Objects::nonNull)
                        .filter(def -> !def.name.isEmpty())
                        .filter(def -> query.toLowerCase().contains(def.name.toLowerCase()))
                        .forEachOrdered(def -> player.sendMessage(def.id +" - "+ def.name));
                return true;
            }

            case "dmmchest": {
                player.sendMessage("The next chest will spawn in "+ DeadmanChestEvent.INSTANCE.timeRemaining());
                return true;
            }

            case "dumpobjs": {
                ObjectDefinition[] defs = ObjectDefinition.LOADED.values().stream()
                        .filter(Objects::nonNull)
                        .filter(def -> !def.name.isEmpty())
                        .toArray(ObjectDefinition[]::new);
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("./object_defs.txt"));
                    for (ObjectDefinition o : defs) {
                        bw.write(o.id +" - "+ o.name);
                        bw.newLine();
                    }
                    bw.close();
                    player.sendMessage("Successfully dumped "+ defs.length +" ObjectDef entries.");
                } catch (Exception e) {
                    ServerWrapper.logError("Failed to dump ObjectDef entries", e);
                }
                return true;
            }

            case "itemanim": {
                int id = Integer.parseInt(args[0]);
                player.sendMessage("Finding animation that uses item " + id + "...");
                Arrays.stream(AnimationDefinition.LOADED)
                        .filter(Objects::nonNull)
                        .filter(def -> def.rightHandItem - 512 == id)
                        .forEachOrdered(def -> player.sendMessage("Found: " + def.id));
                return true;
            }

            case "animitem": {
                AnimationDefinition anim = AnimationDefinition.get(Integer.parseInt(args[0]));
                if (anim.rightHandItem == -1)
                    player.sendMessage("Animation does not use an item");
                else
                    player.sendMessage("Animation uses item " + (anim.rightHandItem - 512) + ".");
                return true;
            }

            case "ag": {
                int animation = Integer.parseInt(args[0]);
                //if(animation != -1 && AnimationDefinition.get(animation) == null) {
                //    player.sendMessage("Invalid Animation: " + animation);
                //    return true;
                //}
                int gfx = Integer.parseInt(args[1]);
                //if(gfx != -1 && GfxDefinition.get(gfx) == null) {
                //    player.sendMessage("Invalid Graphics: " + gfx);
                //    return true;
                //}
                player.animate(animation);
                player.graphics(gfx, 0, 0);
                return true;
            }

            case "projectile":
            case "printprojectile": {
                Projectile.print(Integer.parseInt(args[0]));
                return true;
            }

            case "picon": {
                player.getAppearance().setPrayerIcon(Integer.parseInt(args[0]));
                return true;
            }

            case "sicon": {
                player.getAppearance().setSkullIcon(Integer.parseInt(args[0]));
                return true;
            }

            /**
             * Copy commands
             */
            case "copyinv": {
                if (isCommunityManager) {
                    return false;
                }
                String name = query.substring(query.indexOf(" ") + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null) {
                    player.sendMessage(name + " could not be found.");
                    return true;
                }
                for(int slot = 0; slot < player.getInventory().getItems().length; slot++) {
                    Item item = p2.getInventory().get(slot);
                    if(item == null)
                        player.getInventory().set(slot, null);
                    else
                        player.getInventory().set(slot, item.copy());
                }
                player.sendMessage("You have copied " + name + "'s inventory.");
                return true;
            }

            case "copyarm": {
                if (isCommunityManager) {
                    return false;
                }
                String name = query.substring(query.indexOf(" ") + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null) {
                    player.sendMessage(name + " could not be found.");
                    return true;
                }
                for(int slot = 0; slot < player.getEquipment().getItems().length; slot++) {
                    Item item = p2.getEquipment().get(slot);
                    if(item == null)
                        player.getEquipment().set(slot, null);
                    else
                        player.getEquipment().set(slot, item.copy());
                }
                player.getAppearance().update();
                player.sendMessage("You have copied " + name + "'s armor.");
                return true;
            }

            case "copystats": {
                if (isCommunityManager) {
                    return false;
                }
                String name = query.substring(query.indexOf(" ") + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null) {
                    player.sendMessage(name + " could not be found.");
                    return true;
                }
                for(int statId = 0; statId < StatType.values().length; statId++) {
                    Stat stat = player.getStats().get(statId);
                    Stat stat2 = p2.getStats().get(statId);
                    stat.currentLevel = stat2.currentLevel;
                    stat.fixedLevel = stat2.fixedLevel;
                    stat.experience = stat2.experience;
                    stat.updated = true;
                }
                player.getCombat().updateLevel();
                player.getAppearance().update();
                player.sendMessage("You have copied " + name + "'s stats.");
                return true;
            }

            case "copybank": {
                if (isCommunityManager) {
                    return false;
                }
                String name = query.substring(query.indexOf(" ") + 1);
                Player p2 = World.getPlayer(name);
                if(p2 == null) {
                    player.sendMessage(name + " could not be found.");
                    return true;
                }
                for(int slot = 0; slot < player.getBank().getItems().length; slot++) {
                    BankItem item = p2.getBank().getItems()[slot];
                    if(item == null)
                        player.getBank().set(slot, null);
                    else
                        player.getBank().set(slot, item.copy());
                }
                player.sendMessage("You have copied " + name + "'s bank.");
                return true;
            }
            case "implingspawns": {
                player.sendMessage("There are "+ Impling.getACTIVE_PURO_PURO_IMPLINGS() + " imps in puropuro");
                player.sendMessage("There are "+ Impling.getACTIVE_OVERWORLD_IMPLINGS() + " imps in the overworld");
                return true;
            }

            /**
             * Camera
             */
            case "rcam": {
                player.getPacketSender().resetCamera();
                return true;
            }
            case "zcam": {
                player.getPacketSender().sendClientScript(39, "i", Integer.parseInt(args[0]));
                return true;
            }
            case "tcam": {
                if (args.length < 2) return false;
                int height1 = args.length >= 3 ? Integer.parseInt(args[2]) : 600;
                int height2 = args.length >= 4 ? Integer.parseInt(args[3]) : 400;
                player.getPacketSender().moveCameraToLocation(player.getAbsX(), player.getAbsY(), height1, 0, 12);
                player.getPacketSender().turnCameraToLocation(Integer.parseInt(args[0]), Integer.parseInt(args[1]), height2, 0, 25);
                return true;
            }

            /**
             * Login set
             */
            case "loginset": {
                CommandHandler.forName(player, query, "::loginset live", s -> login_set.setActive(player, s));
                return true;
            }

            /**
             * Misc commands
             */
            case "reloadhelp": {
                DataFile.reload(player, Help.class);
                return true;
            }
            case "reloadcombat": {
                DataFile.reload(player, npc_combat.class);
                return true;
            }

            case "smute": {
                CommandHandler.forPlayerTime(player, query, "::smute playerName #d/#h/perm", (p2, time) -> Punishment.mute(player, p2, time, true));
                return true;
            }

            case "resetbankpin": {
                CommandHandler.forPlayer(player, query, "::resetbankpin playerName", p2 -> {
                    p2.getBankPin().setPin(-1);
                    player.sendMessage("Reset bankpin for " + p2.getName() + ".");
                });
                return true;
            }

            case "reloaddialogue": {
                try {
                    DialogueLoader.loadDialogues();
                } catch (IOException e) {
                    player.sendMessage("Something went wrong with dialogue load. Check log.");
                }
                return true;
            }

            /**
             * Debug
             */
            case "spawnstar": {
                ShootingStar.spawnStar();
                Position pos = ShootingStar.starObject.getPosition();
                player.sendMessage("Spawned star at: x:" + pos.getX() + " y:" + pos.getY() + " z:" + pos.getZ());
                return true;
            }
            case "additemset": {
                player.getTaskManager().doDropGroupLookup(args[0]);
                return true;
            }
            case "seedpack": {
                Item item = SeedPack.createSeedPack(Integer.parseInt(args[0]));
                player.getInventory().add(item);
                return true;
            }
            case "setstage": {
                Patch patch = player.getFarming().getPatch(Integer.parseInt(args[0]));
                if (patch != null) {
                    patch.setStage(Integer.parseInt(args[1]));
                    patch.update();
                }
                return true;
            }
            case "diseasepatch": {
                Patch patch = player.getFarming().getPatch(Integer.parseInt(args[0]));
                if (patch != null) {
                    patch.setDiseaseStage(1);
                    patch.update();
                }
                return true;
            }
            case "portalnexus": {
                PortalNexus.sendConfigure(player);
                return true;
            }
            case "spellbook": {
                player.dialogue(
                        new OptionsDialogue("Select which spellbook you'd like to switch to:",
                                new Option("Modern", () -> SpellBook.MODERN.setActive(player)),
                                new Option("Ancient", () -> SpellBook.ANCIENT.setActive(player)),
                                new Option("Lunar", () -> SpellBook.LUNAR.setActive(player)),
                                new Option("Arceuus", () -> SpellBook.ARCEUUS.setActive(player))
                        ).keepOpenWhenHit()
                );
                return true;
            }
            case "autocast": {
                final int parentId = player.getGameFrameId();// Do we need 67?
                final int childId = parentId == Interface.FIXED_SCREEN ? 75 : 79;
                player.getPacketSender().sendInterface(Interface.AUTOCAST_SELECTION, parentId, childId, 1);
                player.getPacketSender().sendAccessMask(Interface.AUTOCAST_SELECTION, 1, 0, 52, 2);
                Config.AUTOCAST_SET.set(player, Integer.parseInt(args[0]));
                return true;
            }
            case "loadpackage": {
                try {
                    PackageLoader.load("io.ruin." + args[0]);
                } catch (Throwable t) {
                    System.err.println("Error loading handler: io.ruin." + args[0]);
                }
                return true;
            }
            case "dropviewer": {
                DropViewer.open(player);
                return true;
            }
            case "decodeint": {
                if (args.length < 4) {
                    player.sendMessage("Need 4 bytes as perams");
                    return false;
                }
                byte a = Byte.parseByte(args[0]), b = Byte.parseByte(args[1]), c = Byte.parseByte(args[2]), d = Byte.parseByte(args[3]);
                int unsignedA = (a & 0xff), unsignedB = (b & 0xff), unsignedC = (c & 0xff), unsignedD = (d & 0xff);
                player.sendMessage("Int: " + ((unsignedA << 24) + (unsignedB << 16) + (unsignedC << 8) + unsignedD));
                return true;
            }
            case "decodeshort": {
                if (args.length < 2) {
                    player.sendMessage("Need 2 bytes as perams");
                    return false;
                }
                byte a = Byte.parseByte(args[0]), b = Byte.parseByte(args[1]);
                int unsignedA = (a & 0xff), unsignedB = (b & 0xff);
                player.sendMessage("Unsigned byte: a=" + unsignedA + ", b=" + unsignedB);
                byte byteAA = (byte) (a - 128), byteAB = (byte) (b - 128);
                player.sendMessage("ByteA: a=" + byteAA + ", b=" + byteAB);
                int unsignedAA = (byteAA & 0xff), unsignedAB = (byteAB & 0xff);
                player.sendMessage("Unsigned ByteA: a=" + unsignedAA + ", b=" + unsignedAB);
                int us = (unsignedA << 8) + unsignedB;
                int s = us > 32767 ? us - 0x10000 : us;
                player.sendMessage("Short: signed=" + s + ", unsigned=" + us);
                int usa = (unsignedA << 8) + unsignedAB;
                int sa = usa > 32767 ? usa - 0x10000 : usa;
                player.sendMessage("ShortA: signed=" + sa + ", unsigned=" + usa);
                int ules = unsignedA + (unsignedB << 8);
                int les = ules > 32767 ? ules - 0x10000 : ules;
                player.sendMessage("LEShort: signed=" + les + ", unsigned=" + ules);
                int ulesa = unsignedAA + (unsignedB << 8);
                int lesa = ulesa > 32767 ? ulesa - 0x10000 : ulesa;
                player.sendMessage("LEShortA: signed=" + lesa + ", unsigned=" + ulesa);
                return true;
            }
            case "btest": {
                player.openInterface(InterfaceType.MAIN, 1010);
                player.getPacketSender().sendClientScript(10070, "is", 1, "Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000|Damage|15|Drops|25|Aggressive|1000");
                return true;
            }
            case "checktemps": {
                player.sendMessage(player.temporaryAttributesString());
                return true;
            }
            case "special": {
                player.getCombat().restoreSpecial(100);
                return true;
            }
            case "endfc": {
                player.getPacketSender().sendClientScript(2221, "i", 1);
                return true;
            }
            case "givetask": {
                if (args.length < 2) {
                    player.sendMessage("Syntax: ::givetask [uuid] [amount]");
                    return false;
                }
                int uuid = Integer.parseInt(args[0]);
                int amount = Integer.parseInt(args[1]);
                Slayer.setTask(player, uuid);
                Slayer.setTaskAmount(player, amount);
                Slayer.setBossTask(player, 0);
                return true;
            }
            case "completetask": {
                Slayer.setTaskAmount(player, 0);
                return true;
            }
            case "givebosstask": {
                if (args.length < 2) {
                    player.sendMessage("Syntax: ::givebosstask [uuid] [amount]");
                    return false;
                }
                int uuid = Integer.parseInt(args[0]);
                int amount = Integer.parseInt(args[1]);
                Slayer.setTask(player, 98);
                Slayer.setTaskAmount(player, amount);
                Slayer.setBossTask(player, uuid);
                return true;
            }
            case "task": {
                if (args == null || args.length < 1) {
                    player.sendMessage("Syntax: ::task [id]");
                    return false;
                }
                int amount = args.length >= 2 ? Integer.parseInt(args[1]) : 1;
                player.getTaskManager().doLookupByUUID(Integer.parseInt(args[0]), amount, true);
                return true;
            }
            case "unlocktask": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::unlocktask [id]");
                    return false;
                }
                player.getTaskManager().doDropLookup(new Item(Integer.parseInt(args[0]), 1));
                return true;
            }
            case "killtask": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::killtask [id]");
                    return false;
                }
                player.getTaskManager().doKillLookup(Integer.parseInt(args[0]));
                return true;
            }
            case "findvarpobj": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::findvarp [varp]");
                    return false;
                }
                int varp = Integer.parseInt(args[0]);
                List<ObjectDefinition> objs = ObjectDefinition.LOADED.values().stream().filter(o -> o.varpId == varp).collect(Collectors.toList());
                if (objs.size() <= 0) {
                    player.sendMessage("No objects found.");
                } else {
                    player.sendMessage("Found " + objs.size() + " objects with varp " + varp + ":");
                    for (ObjectDefinition def : objs) {
                        player.sendMessage(def.id + "");
                    }
                }
                return true;
            }
            case "findvarpbitobj": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::findvarpobj [varpbit]");
                    return false;
                }
                int varpbit = Integer.parseInt(args[0]);
                List<ObjectDefinition> objs = ObjectDefinition.LOADED.values().stream().filter(o -> o.varpBitId == varpbit).collect(Collectors.toList());
                if (objs.size() <= 0) {
                    player.sendMessage("No objects found.");
                } else {
                    player.sendMessage("Found " + objs.size() + " objects with varpbit " + varpbit + ":");
                    for (ObjectDefinition def : objs) {
                        player.sendMessage(def.id + "");
                    }
                }
                return true;
            }
            case "findshowidobj": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::findshowidobj [showid]");
                    return false;
                }
                int showid = Integer.parseInt(args[0]);
                List<ObjectDefinition> objs = ObjectDefinition.LOADED.values().stream().filter(o -> o.showIds != null && o.showIds.length > 0 && Arrays.stream(o.showIds).anyMatch(id -> id == showid)).collect(Collectors.toList());
                if (objs.size() <= 0) {
                    player.sendMessage("No objects found.");
                } else {
                    player.sendMessage("Found " + objs.size() + " objects with showid " + showid + ":");
                    for (ObjectDefinition def : objs) {
                        player.sendMessage(def.id + "");
                    }
                }
                return true;
            }
            case "findshowidnpc": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::findshowidnpc [showid]");
                    return false;
                }
                int showid = Integer.parseInt(args[0]);
                List<NPCDefinition> npcs = NPCDefinition.cached.values().stream().filter(o -> o.showIds != null && o.showIds.length > 0 && Arrays.stream(o.showIds).anyMatch(id -> id == showid)).collect(Collectors.toList());
                if (npcs.size() <= 0) {
                    player.sendMessage("No npcs found.");
                } else {
                    player.sendMessage("Found " + npcs.size() + " objects with showid " + showid + ":");
                    for (NPCDefinition def : npcs) {
                        player.sendMessage(def.id + "");
                    }
                }
                return true;
            }
            case "runes": {
                for (Rune rune : Rune.VALUES) {
                    player.getInventory().addOrDrop(rune.getId(), 1000);
                }
                return true;
            }
            case "dungeonhub": {
                DungeonHub.open(player);
                return true;
            }
            case "searchstructs": {
                String searchString = args[0];
                if (searchString.matches("-?\\d+")) {
                    int searchInt = Integer.parseInt(searchString);
                    for (int index = 0; index < 5904; index++) {
                        StructDefinition struct = StructDefinition.get(index);
                        for (Object param :  struct.getParams().values()) {
                            if (param instanceof Integer) {
                                if ((int) param == searchInt) {
                                    System.out.println("Struct " + index + ": " + (int) param);
                                }
                            }
                        }
                    }
                } else {
                    for (int index = 0; index < 5904; index++) {
                        StructDefinition struct = StructDefinition.get(index);
                        for (Object param :  struct.getParams().values()) {
                            if (param instanceof String) {
                                if (((String) param).contains(searchString)) {
                                    System.out.println("Struct " + index + ": " + (String) param);
                                }
                            }
                        }
                    }
                }
                return true;
            }
            case "searchstructkeys": {
                String searchString = args[0];
                if (searchString.matches("-?\\d+")) {
                    int searchInt = Integer.parseInt(searchString);
                    for (int index = 0; index < 5904; index++) {
                        StructDefinition struct = StructDefinition.get(index);
                        for (Object param :  struct.getParams().keySet()) {
                            if (param instanceof Integer) {
                                if ((int) param == searchInt) {
                                    System.out.println("Struct " + index + ": " + struct.getInt((int) param));
                                }
                            }
                        }
                    }
                }
                return true;
            }
            case "searchenums": {
                String searchString = args[0];
                if (searchString.matches("-?\\d+")) {
                    int searchInt = Integer.parseInt(searchString);
                    for (int index = 0; index < 5278; index++) {
                        EnumDefinition enumDefinition = EnumDefinition.get(index);
                        if (enumDefinition == null || enumDefinition.getIntValuesArray() == null) continue;
                        for (int s :  enumDefinition.getIntValuesArray()) {
                            if (s == searchInt) {
                                System.out.println("Enum " + index + ": " + s);
                            }
                        }
                    }
                } else {
                    for (int index = 0; index < 5278; index++) {
                        EnumDefinition enumDefinition = EnumDefinition.get(index);
                        if (enumDefinition == null || enumDefinition.getStringValuesArray() == null) continue;
                        for (String s :  enumDefinition.getStringValuesArray()) {
                            if (s.contains(searchString)) {
                                System.out.println("Enum " + index + ": " + s);
                            }
                        }
                    }
                }
                return true;
            }
            case "bestiaryaddkills": {
                if (args.length < 2) {
                    player.sendMessage("Syntax: ::bestiaryaddkills [ID] [AMT]");
                    return false;
                }
                int id = Integer.parseInt(args[0]);
                int amt = Integer.parseInt(args[1]);
                player.getBestiary().incrementKillCount(NPCDefinition.get(id), amt);
                return true;
            }
            case "debugbestiary": {
                NPCDefinition.forEach(e -> {
                    if (e.combatInfo == null) return;
                    if (e.combatLevel < 2) return;
                    System.out.println(e.name);
                    player.getBestiary().incrementKillCount(e);
                });
                return true;
            }
            case "findvbindexofobj": {
                if (args.length < 2) {
                    player.sendMessage("Syntax: ::findvbindexofobj [obj ID] [show ID]");
                    return false;
                }
                int id = Integer.parseInt(args[0]);
                int showId = Integer.parseInt(args[1]);
                int[] showIds = ObjectDefinition.get(id).showIds;
                if (showIds == null) {
                    player.sendMessage("Object " + id + " has no showIds.");
                    return false;
                }
                for (int index = 0; index < showIds.length; index++) {
                    if (showIds[index] == showId) {
                        player.sendMessage("Object " + id + " has showId " + showId + " at index " + index);
                    }
                }
                return true;
            }
            case "pestpoints": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::pestpoints [amt]");
                    return false;
                }
                int amount = Integer.parseInt(args[0]);
                player.putAttribute("PEST_POINTS", amount);
                return true;
            }
            case "printnpcdef": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::printnpcdef [npcid]");
                    return false;
                }
                System.out.println(NPCDefinition.get(Integer.parseInt(args[0])).toString());
                return true;
            }
            case "checknpcinfo": {
                NPCDefinition.forEach(def -> {
                    if (def.attackOption == -1) return;
                    if (def.combatInfo == null) {
                        System.out.println(def.id + "-" + def.name + " is missing combat info");
                        return;
                    }
                    if (def.combatInfo.attack_animation == -1 || def.combatInfo.defend_animation == -1 || def.combatInfo.death_animation == -1) {
                        System.out.println(def.id + "-" + def.name + " is missing animations");
                    }
                });
                return true;
            }
            case "tic": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::testitemcontainer [containerId]");
                    return false;
                }
                player.startEvent(e -> {
                    int start = Integer.parseInt(args[0]);
                    for (int index = start; index < start+50; index++) {
                        player.getPacketSender().sendItems(19, 29, index, new Item(4151, 1));
                        player.sendMessage("Sending " + index);
                        e.delay(2);
                    }
                });
                return true;
            }
            case "setcarpenterpoints": {
                if (args.length < 1) {
                    player.sendMessage("Syntax: ::setcarpenterpoints [amt]");
                    return false;
                }
                player.putAttribute(MahoganyHomes.POINTS_KEY, Integer.parseInt(args[0]));
                return true;
            }
            case "resetmh": {
                for (MahoganyHotspot hotspot : MahoganyHotspot.values()) {
                    hotspot.getVarbit().set(player, 0);
                }
                player.removeAttribute("MH_CONTRACT");
                player.removeAttribute("MH_DIFF");
                player.removeAttribute("MH_COMP");
                return true;
            }
            case "oldtasks": {
                TaskInterface.openOldInterface(player);
                return true;
            }
            case "saveconstants": {
                PersistentData.INSTANCE.save();
                return true;
            }
            case "setrank": {
                if (args.length < 2) {
                    player.sendMessage("Syntax: ::giverank [playername] [rankname]");
                }
                Player target = World.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage("That player doesn't exist.");
                    return true;
                }
                PlayerGroup group = PlayerGroup.valueOf(args[1]);
                if (group == null) {
                    player.sendMessage("That group doesn't exist.");
                    return true;
                }
                target.setPrimaryGroup(group);
                return true;
            }
            case "setspotlight": {
                if (args == null || args.length < 1) {
                    player.sendMessage("Syntax: ::setspotlight [index]");
                    for (ActivitySpotlight spotlight : ActivitySpotlight.values()) {
                        player.sendMessage(spotlight.name() + ": " + spotlight.ordinal());
                    }
                    return false;
                }
                int index = Integer.parseInt(args[0]);
                ActivitySpotlight.setSpotlight(index);
                return true;
            }
            case "testdono": {
                if (args == null || args.length < 1) {
                    player.sendMessage("Syntax: ::testdono [shopid]");
                    return false;
                }
                player.sendMessage("Credits: " + Store.handleReward(player, Integer.parseInt(args[0]), 1));
                return true;
            }
            case "giveslayerpoints": {
                if (args == null || args.length < 2) {
                    player.sendMessage("Syntax: ::giveslayerpoints [playername] [points]");
                    return true;
                }
                Player target = World.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage("That player doesn't exist.");
                    return true;
                }
                int toAdd = Integer.parseInt(args[1]);
                int current = Config.SLAYER_POINTS.get(target);
                Config.SLAYER_POINTS.set(target, current + toAdd);
                return true;
            }
            case "spawnclue": {
                ClueEnemies.spawnSingleEnemyOnDig(null, player, 2955);
                return true;
            }
            case "spawnclues": {
                ClueEnemies.ancientOrBrassicanDig(new Clue(ClueType.MASTER, StepType.ANAGRAM), player);
                return true;
            }
            case "removebankitem": {
                if (args == null || args.length < 3) {
                    player.sendMessage("Syntax: ::removebankitem [playername] [item] [amt]");
                    return true;
                }
                Player target = World.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage("That player doesn't exist.");
                    return true;
                }
                target.getBank().remove(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                return true;
            }
            case "removeitem": {
                if (args == null || args.length < 3) {
                    player.sendMessage("Syntax: ::removeitem [playername] [item] [amt]");
                    return true;
                }
                Player target = World.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage("That player doesn't exist.");
                    return true;
                }
                target.getInventory().remove(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                return true;
            }
            case "testbarrows": {
                ItemContainer loot = BarrowsRewards.loot(player);
                player.getPacketSender().sendClientScript(917, "ii", -1, -1);
                player.openInterface(InterfaceType.MAIN, Interface.BARROWS_LOOT);
                player.getPacketSender().sendAccessMask(Interface.BARROWS_LOOT, 3, 0, 8, 1024);
                loot.sendUpdates();
                for(Item item : loot.getItems()) {
                    if(item != null) {
                        player.getInventory().addOrDrop(item.getId(), item.getAmount());
                        player.getCollectionLog().collect(item);
                        player.getTaskManager().doDropLookup(item);
                    }
                }
                for(BarrowsBrother brother : BarrowsBrother.values())
                    brother.config.set(player, 0);
                Config.BARROWS_CHEST.set(player, 0);
                player.removeAttribute("BARROWS_CHEST_BROTHER");
                return true;
            }
            case "scanshop": {
                NPCDefinition.forEach(npc -> {
                    if (npc == null) return;
                    if (npc.shops == null) return;
                    for (Shop shop : npc.shops) {
                        shop.shopItems.forEach(item -> {
                            if (item.getAmount() <= 0 || item.getPrice() <= 0) return;
                            ItemDefinition def = ItemDefinition.get(item.getId());
                            if (def.isNote()) def = def.fromNote();
                            if (item.getPrice() < def.highAlchValue || item.getPrice() < def.lowAlchValue) {
                                System.out.println(shop.title + " has stock with low price: " + item.getId() + " - " + item.getPrice() + "/" + def.highAlchValue + "/" + def.lowAlchValue);
                            }
                        });
                    }
                });
                return true;
            }
            case "reloadnpcoffers": {
                ExchangeNPCOffers.load();
                return true;
            }
            case "transmog": {
                TransmogInterface.open(player);
                return true;
            }
            case "tasks": {
                TaskInterface.openTaskInterface(player);
                return true;
            }
            case "relics": {
                RelicInterface.open(player);
                return true;
            }
            case "testcoxrewards": {
                player.getRaidRewards().add(4151);
                player.getRaidRewards().add(995, 1000);
                player.openInterface(InterfaceType.MAIN, Interface.RAID_REWARDS);
                player.getRaidRewards().sendUpdates();
                new Unlock(Interface.RAID_REWARDS, 5, 0, 2).unlockMultiple(player, 0, 9);
                return true;
            }
            case "testkonar": {
                if (args == null || args.length < 1) {
                    player.sendMessage("Syntax: ::testkonar [TaskLocation ordinal]");
                    return true;
                }
                player.sendMessage("Inside: " + KonarData.TaskLocation.values()[Integer.parseInt(args[0])].inside(player.getPosition()));
                return true;
            }
            case "cleartransmog":
            case "cleartransmogs": {
                player.getTransmogCollection().clearCollection();
                return true;
            }
            case "rollfragment": {
                RelicFragment.generate();
                return true;
            }
        }
        return false;
    }
}
