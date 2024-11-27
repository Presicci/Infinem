package io.ruin.cache.def;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.api.utils.StringUtils;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.activities.cluescrolls.impl.AnagramClue;
import io.ruin.model.activities.cluescrolls.impl.CrypticClue;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.killcount.KillCounterType;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.shop.Shop;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.*;
import java.util.function.Consumer;

public class NPCDefinition {

    public static Map<Integer, NPCDefinition> cached = Maps.newConcurrentMap();

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int size = index.getLastFileId(9) + 1;
        for(int id = 0; id < size; id++) {
            byte[] data = index.getFile(9, id);
            if (data != null) {
                NPCDefinition def = new NPCDefinition();
                def.id = id;
                def.decode(new InBuffer(data));
                cached.put(id, def);
            }
        }
    }

    public static void forEach(Consumer<NPCDefinition> consumer) {
        for(NPCDefinition def : cached.values()) {
            if(def != null)
                consumer.accept(def);
        }
    }

    public static NPCDefinition get(int id) {
        return cached.get(id);
    }

    public static NPCDefinition getConfigDef(int id, Player player) {
        NPCDefinition def = NPCDefinition.cached.get(id);
        if (def.varpbitId != -1) {// || def.varpId != -1) {
            int value = Config.varpbit(def.varpbitId, false).get(player);
            def = NPCDefinition.get(def.showIds[value]);
        }
        return def;
    }


    /**
     * Stored data
     */
    public String examine;

    /**
     * Custom data
     */

    public String descriptiveName;

    public Class<? extends NPCCombat> combatHandlerClass;

    public LootTable lootTable;

    public String bestiaryEntry;

    public NPCAction[] defaultActions;

    public HashMap<Integer, ItemNPCAction> itemActions;

    public ItemNPCAction defaultItemAction;

    public AnagramClue anagram;

    public CrypticClue cryptic;

    public List<Shop> shops;

    public npc_combat.Info combatInfo;

    public int attackOption = -1;

    public boolean flightClipping, swimClipping;

    public boolean occupyTiles = true;

    public boolean ignoreOccupiedTiles;

    public double giantCasketChance; // only used for bosses atm, other npcs use a formula (see GoldCasket)

    public List<Dialogue> optionDialogues;

    public boolean ignoreMultiCheck = false;

    public KillCounterType killCounterType;

    public Map<String, Object> custom_values = new HashMap<>();

    /**
     * Cache data
     */

    public int id;
    public boolean isMinimapVisible = true;
    public int anInt3558;
    public int standAnimation = -1;
    public int size = 1;
    public int walkBackAnimation = -1;
    public String name = "null";
    public int anInt3564 = -1;
    public int anInt3565 = -1;
    public int walkAnimation = -1;
    public int walkLeftAnimation = -1;
    public int walkRightAnimation = -1;
    public boolean isFollower = false;
    public boolean isLowPriorityFollowerOps = false;
    public String[] options = new String[5];
    public int height = -1;
    public int[] stats = {1, 1, 1, 1, 1, 1};
    public int combatLevel = -1;
    public boolean hasRenderPriority = false;
    public int[] headIconArchiveIds;
    public short[] headIconSpriteIndex;
    public int rotation = 32;
    public int[] showIds;
    public boolean isClickable = true;
    public boolean aBool3588 = true;
    short[] retextureToReplace;
    short[] retextureToFind;
    public int varpId = -1;
    public int[] models;
    short[] recolorToFind;
    int[] models_2;
    public int varpbitId = -1;
    short[] recolorToReplace;
    int resizeX = 128;
    int resizeY = 128;
    int ambient = 0;
    int contrast = 0;
    int runSequence = -1;
    int runBackSequence = -1;
    int runLeftSequence = -1;
    int runRightSequence = -1;
    int crawlSequence = -1;
    int crawlBackSequence = -1;
    int crawlLeftSequence = -1;
    int crawlRightSequence = -1;
    public HashMap<Object, Object> attributes;

    void decode(InBuffer buffer) {
        List<Integer> processedOpcodes = new ArrayList<>();
        for(;;) {
            int opcode = buffer.readUnsignedByte();
            if(opcode == 0)
                break;
            processedOpcodes.add(opcode);
            decode(buffer, opcode, processedOpcodes.toArray(new Integer[0]));
        }
        /**
         * Keep updated with client lol
         */
        if(id == 5527) {
            /* Twiggy O'Korn */
            options[2] = "Rewards";
        } else if(id == 315) {
            options[0] = "Talk-to";
            options[1] = "Set-skull";
            options[2] = "Reset-kdr";
            options[3] = null;
            options[4] = null;
        } else if(id == 2108) {
            name = "Donation Manager";
            options[0] = "Open-Shop";
            options[2] = "Claim-purchases";
        } else if(id == 5442) {
            name = "Security Advisor";
            options[2] = "Check Pin Settings";
            options[3] = "Check 2FA Settings";
        } else if(id == 4398) {
            /* ECO Wizard */
            name = World.type.getWorldName() + " Wizard";
            options[0] = "Teleport";
            options[2] = "Teleport-previous";
        } else if(id == 4159) {
            name = World.type.getWorldName() + " Wizard";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
        } else if(id == 2462) {
            /* Shanomi */
            options[2] = "Trade";
        } else if(id == 6481) {
            combatLevel = 0;
        } else if(id == 3278) {
            name = "Construction Worker";
        } else if (id == 6118) {
            name = "Elvarg";
            combatLevel = 280;
        } else if(id == 5906) {
            options[2] = null;
        } else if("Pick-up".equals(options[0]) && "Talk-to".equals(options[2]) && "Chase".equals(options[3]) && "Interact".equals(options[4])) {
            options[3] = "Age";
            options[4] = null;
        } else if(id == 1849) {
            name = "Loyalty Fairy";
            options[0] = "About";
        } else if(id == 8407) {
            options[1] = "View Trading Post";
        } else if(id == 7759) {
            options[0] = options[2] = null;
        } else if(id == 7316) {
            name = "Tournament Manager";
            options[0] = "Sign-up";
        } else if(id == 7941) {
            options[2] = options[3] = options[4] = null;
        //} else if(id == 3080) { // man at home. remove attack option so people can't be assholes to newbies that are just starting out
        //    options[1] = null;
        //    combatLevel = 0;
        } else if (id == 307) { // second guide npc (with no options); originally dr jekyll
            name = World.type.getWorldName() + " Expert";
            options[0] = null;
            options[4] = null;
            standAnimation = 813;
            walkAnimation = 1205;
        } else if(id == 7951) {
            name = "PvP Event Manager";
            options[0] = "Join-event";
            options[1] = "Create-event";
        } else if(id == 8009) {
            options[3] = "Metamorphosis";
        } else if(id == 8507) {
            name = "Bloody Merchant";
            options[0] = "Trade";
        } else if(id == 7297) { // Skotizo (For eco world)
            // Replaces Mistag
            copy(7286);
        } else if(id == 6002) {
            name = "Caretaker";
            options[0] = "Trade";
        } else if(id == 119) {
            name = "Doomsayer";
        } else if(id == 8500) {
            name = "Old man";
            options[1] = "Trade";
        } else if(id == 8300) { //Ranalph Devere Melee
            copy(3966);
            //headIcon = 0;
        } else if(id == 8301) { //Ranalph Devere Range
            copy(3966);
            //headIcon = 1;
        } else if(id == 8302) { //Ranalph Devere Mage
            copy(3966);
            //headIcon = 2;
        } else if (id == 5314) {
            name = "Wizard Cromperty";
        } else if (id == 2897) {
            size = 3;
        }

        if(name != null) {
            if(name.isEmpty())
                descriptiveName = name;
            else if(name.equals("Kalphite Queen") || name.equals("Corporeal Beast") || name.equals("King Black Dragon")
                    || name.equals("Kraken") || name.equals("Thermonuclear Smoke Devil") || name.equalsIgnoreCase("Crazy archaeologist") || name.equalsIgnoreCase("Chaos Fanatic") || name.equalsIgnoreCase("Chaos Elemental"))
                descriptiveName = "the " + name;
            else if (name.toLowerCase().matches("dagannoth (rex|prime|supreme)")
                    || name.equals("Cerberus") || name.equals("Zulrah") || name.equals("Callisto") || name.equals("Venenatis") || name.equals("Vet'ion") || name.equals("Scorpia"))
                descriptiveName = name;
            else if(StringUtils.vowelStart(name))
                descriptiveName = "an " + name;
            else
                descriptiveName = "a " + name;
        }

        attackOption = getOption("attack", "fight");
        flightClipping = name.toLowerCase().contains("impling") || name.toLowerCase().contains("butterfly") || name.equalsIgnoreCase("gull")
                || id == 5317;  // Eagle
    }

    void decode(InBuffer var1, int var2, Integer[] opcodes) {
        if(var2 == 1) {
            int var4 = var1.readUnsignedByte();
            models = new int[var4];
            for(int var5 = 0; var5 < var4; var5++)
                models[var5] = var1.readUnsignedShort();
        } else if(var2 == 2)
            name = var1.readString();
        else if(var2 == 12)
            size = var1.readUnsignedByte();
        else if(var2 == 13)
            standAnimation = var1.readUnsignedShort();
        else if(var2 == 14)
            walkAnimation = var1.readUnsignedShort();
        else if(var2 == 15)
            anInt3564 = var1.readUnsignedShort();
        else if(var2 == 16)
            anInt3565 = var1.readUnsignedShort();
        else if(var2 == 17) {
            walkAnimation = var1.readUnsignedShort();
            walkBackAnimation = var1.readUnsignedShort();
            walkLeftAnimation = var1.readUnsignedShort();
            walkRightAnimation = var1.readUnsignedShort();
        }
        else if (var2 == 18) {
            var1.readUnsignedShort();
        } else if(var2 >= 30 && var2 < 35) {
            options[var2 - 30] = var1.readString();
            if(options[var2 - 30].equalsIgnoreCase("Hidden"))
                options[var2 - 30] = null;
        } else if(var2 == 40) {
            int var4 = var1.readUnsignedByte();
            recolorToFind = new short[var4];
            recolorToReplace = new short[var4];
            for(int var5 = 0; var5 < var4; var5++) {
                recolorToFind[var5] = (short) var1.readUnsignedShort();
                recolorToReplace[var5] = (short) var1.readUnsignedShort();
            }
        } else if(var2 == 41) {
            int var4 = var1.readUnsignedByte();
            retextureToFind = new short[var4];
            retextureToReplace = new short[var4];
            for(int var5 = 0; var5 < var4; var5++) {
                retextureToFind[var5] = (short) var1.readUnsignedShort();
                retextureToReplace[var5] = (short) var1.readUnsignedShort();
            }
        } else if(var2 == 60) {
            int var4 = var1.readUnsignedByte();
            models_2 = new int[var4];
            for (int var5 = 0; var5 < var4; var5++)
                models_2[var5] = var1.readUnsignedShort();
        } else if (var2 == 74) {
            stats[0] = var1.readUnsignedShort();
        } else if (var2 == 75) {
            stats[1] = var1.readUnsignedShort();
        } else if (var2 == 76) {
            stats[2] = var1.readUnsignedShort();
        } else if (var2 == 77) {
            stats[3] = var1.readUnsignedShort();
        } else if (var2 == 78) {
            stats[4] = var1.readUnsignedShort();
        } else if (var2 == 79) {
            stats[5] = var1.readUnsignedShort();
        } else if(var2 == 93)
            isMinimapVisible = false;
        else if(var2 == 95)
            combatLevel = var1.readUnsignedShort();
        else if(var2 == 97)
            resizeX = var1.readUnsignedShort();
        else if(var2 == 98)
            resizeY = var1.readUnsignedShort();
        else if(var2 == 99)
            hasRenderPriority = true;
        else if(var2 == 100)
            ambient = var1.readByte();
        else if(var2 == 101)
            contrast = var1.readByte() * 5;
        else if(var2 == 102) {
            int mask = var1.readUnsignedByte();
            int length = 0;

            int bit = mask;
            while (bit != 0) {
                length++;
                bit >>= 1;
            }

            this.headIconArchiveIds = new int[length];
            this.headIconSpriteIndex = new short[length];

            for (int id = 0; id < length; ++id) {
                if ((mask & 1 << id) == 0) {
                    this.headIconArchiveIds[id] = -1;
                    this.headIconSpriteIndex[id] = -1;
                } else {
                    this.headIconArchiveIds[id] = var1.readBigSmart();
                    this.headIconSpriteIndex[id] = (short) var1.readUnsignedSmartSub();
                }
            }
        } else if(var2 == 103)
            rotation = var1.readUnsignedShort();
        else if(var2 == 106 || var2 == 118) {
            varpbitId = var1.readUnsignedShort();
            if(varpbitId == 65535)
                varpbitId = -1;
            varpId = var1.readUnsignedShort();
            if(varpId == 65535)
                varpId = -1;
            int var4 = -1;
            if(var2 == 118) {
                var4 = var1.readUnsignedShort();
                if(var4 == 65535)
                    var4 = -1;
            }
            int var5 = var1.readUnsignedByte();
            showIds = new int[var5 + 2];
            for(int var6 = 0; var6 <= var5; var6++) {
                showIds[var6] = var1.readUnsignedShort();
                if(showIds[var6] == 65535)
                    showIds[var6] = -1;
            }
            showIds[var5 + 1] = var4;
        } else if (var2 == 107)
            isClickable = false;
        else if (var2 == 109)
            aBool3588 = false;
        else if (var2 == 111) {
            isFollower = true;
            isLowPriorityFollowerOps = true;
        } else if (var2 == 114) {
            runSequence = var1.readUnsignedShort();
        } else if (var2 == 115) {
            runSequence = var1.readUnsignedShort();
            runBackSequence = var1.readUnsignedShort();
            runLeftSequence = var1.readUnsignedShort();
            runRightSequence = var1.readUnsignedShort();
        } else if (var2 == 116) {
            crawlSequence = var1.readUnsignedShort();
        } else if (var2 == 117) {
            crawlSequence = var1.readUnsignedShort();
            crawlBackSequence = var1.readUnsignedShort();
            crawlLeftSequence = var1.readUnsignedShort();
            crawlRightSequence = var1.readUnsignedShort();
        } else if (var2 == 122)
            isFollower = true;
        else if (var2 == 123)
            isLowPriorityFollowerOps = true;
        else if (var2 == 124)
            height = var1.readUnsignedShort();
        else if (var2 == 249) {
            int size = var1.readUnsignedByte();
            if (attributes == null)
                attributes = new HashMap<>();
            for (int i = 0; i < size; i++) {
                boolean stringType = var1.readUnsignedByte() == 1;
                int key = var1.readMedium();
                if (stringType)
                    attributes.put(key, var1.readString());
                else
                    attributes.put(key, var1.readInt());
            }
        } else {
            System.err.println("MISSING NPC OPCODE " + var2 + " FOR ID " + id + " OPCODES: " + Arrays.toString(opcodes));
        }

    }

    void copy(int otherId) {
        NPCDefinition otherDef = cached.get(otherId);
        try {
            isMinimapVisible = otherDef.isMinimapVisible;
            anInt3558 = otherDef.anInt3558;
            standAnimation = otherDef.standAnimation;
            size = otherDef.size;
            walkBackAnimation = otherDef.walkBackAnimation;
            name = otherDef.name;
            anInt3564 = otherDef.anInt3564;
            anInt3565 = otherDef.anInt3565;
            walkAnimation = otherDef.walkAnimation;
            walkLeftAnimation = otherDef.walkLeftAnimation;
            walkRightAnimation = otherDef.walkRightAnimation;
            isFollower = otherDef.isFollower;
            isLowPriorityFollowerOps = otherDef.isLowPriorityFollowerOps;
            options = otherDef.options == null ? null : otherDef.options.clone();
            combatLevel = otherDef.combatLevel;
            hasRenderPriority = otherDef.hasRenderPriority;
            headIconArchiveIds = otherDef.headIconArchiveIds;
            headIconSpriteIndex = otherDef.headIconSpriteIndex;
            rotation = otherDef.rotation;
            showIds = otherDef.showIds == null ? null : otherDef.showIds.clone();
            isClickable = otherDef.isClickable;
            aBool3588 = otherDef.aBool3588;
            retextureToReplace = otherDef.retextureToReplace == null ? null : otherDef.retextureToReplace.clone();
            retextureToFind = otherDef.retextureToFind == null ? null : otherDef.retextureToFind.clone();
            varpId = otherDef.varpId;
            models = otherDef.models;
            recolorToFind = otherDef.recolorToFind == null ? null : otherDef.recolorToFind.clone();
            models_2 = otherDef.models_2 == null ? null : otherDef.models_2.clone();
            varpbitId = otherDef.varpbitId;
            recolorToReplace = otherDef.recolorToReplace == null ? null : otherDef.recolorToReplace.clone();
            resizeX = otherDef.resizeX;
            resizeY = otherDef.resizeY;
            ambient = otherDef.ambient;
            contrast = otherDef.contrast;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public boolean hasOption(String... searchOptions) {
        return getOption(searchOptions) != -1;
    }

    public int getOption(String... searchOptions) {
        if(options != null) {
            for(String s : searchOptions) {
                for(int i = 0; i < options.length; i++) {
                    String option = options[i];
                    if(s.equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

    public void examine(Player player) {
        if (examine == null)
            return;
        player.sendMessage(examine);
    }

    public boolean hasAttribute(String attribute) {
        if (combatInfo == null || combatInfo.attributes == null) return false;
        return Arrays.stream(combatInfo.attributes).anyMatch(att -> att.equalsIgnoreCase(attribute));
    }

    public boolean hasCustomValue(String key) {
        return custom_values.containsKey(key);
    }

    public <T> T getCustomValueOrDefault(String key, T defaultValue) {
        Object value = custom_values.get(key);
        return value == null ? defaultValue : (T) value;
    }
}
