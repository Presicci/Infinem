package io.ruin.model.entity.shared.masks;

import com.google.gson.annotations.Expose;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.combat.WeaponType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import lombok.Getter;

public class Appearance extends UpdateMask {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Styles
     * 0=hair
     * 1=beard
     * 2=top
     * 3=arms
     * 4=wrists
     * 5=legs
     * 6=shoes
     */

    @Expose
    public int[] styles = {0, 10, 18, 26, 33, 36, 42};

    /**
     * Colors
     * 0=hair
     * 1=torso
     * 2=legs
     * 3=shoes
     * 4=skin
     */

    @Expose public int[] colors = new int[5];

    /**
     * Gender
     * 0=male
     * 1=female
     */

    @Expose @Getter private int gender = 0;

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderString() {
        return gender == 0 ? "sir" : "madam";
    }

    public boolean isMale() {
        return gender == 0;
    }

    public void modifyAppearance(byte index, short value) {
        styles[index] = value;
        update();
    }

    public void modifyColor(byte index, short value) {
        colors[index] = value;
        update();
    }

    /**
     * Skull icon
     */

    private int skullIcon = -1;

    public void setSkullIcon(int skullIcon) {
        this.skullIcon = skullIcon;
        update();
    }

    public int getSkullIcon() {
        return skullIcon;
    }

    /**
     * Prayer icon
     */

    private int prayerIcon = -1;

    public void setPrayerIcon(int prayerIcon) {
        this.prayerIcon = prayerIcon;
        update();
    }

    /**
     * Npc id
     */

    private int npcId = -1;

    public void setNpcId(int npcId) {
        this.npcId = npcId;
        if(npcId == -1) {
            removeCustomRenders();
            return;
        }
        NPCDefinition def = NPCDefinition.get(npcId);
        setCustomRenders(def.standAnimation, -1, def.walkAnimation, def.walkBackAnimation, def.walkLeftAnimation, def.walkRightAnimation, -1);
    }

    public int getNpcId() {
        return npcId;
    }

    /**
     * Custom renders
     */

    private int[] customRenders;

    public void restoreNPCRender(int index) {
        if (npcId == -1) {
            removeCustomRenders();
            return;
        }
        NPCDefinition def = NPCDefinition.get(npcId);
        int[] renders = { def.standAnimation, -1, def.walkAnimation, def.walkBackAnimation, def.walkLeftAnimation, def.walkRightAnimation, -1 };
        setCustomRender(index, renders[index]);
    }

    public void restoreNPCRenders() {
        if (npcId == -1) {
            removeCustomRenders();
            return;
        }
        NPCDefinition def = NPCDefinition.get(npcId);
        setCustomRenders(def.standAnimation, -1, def.walkAnimation, def.walkBackAnimation, def.walkLeftAnimation, def.walkRightAnimation, -1);
    }

    public void setCustomRender(int index, int anim) {
        if (customRenders.length <= index) return;
        this.customRenders[index] = anim;
        update();
    }

    public void setCustomRenders(int... renders) {
        this.customRenders = renders;
        update();
    }

    public void removeCustomRenders() {
        this.customRenders = null;
        update();
    }

    /**
     * Mask info
     */

    private boolean update = true;

    private final OutBuffer data = new OutBuffer(255);

    public void update() {
        update = true;
        data.position(0);
    }

    @Override
    public void reset() {
        update = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return update || added;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate, Player receivingPlayer) {
        if(data.position() == 0) {
            data.addByte(gender);
            data.addByte(skullIcon);
            data.addByte(prayerIcon);
            if(npcId != -1) {
                data.addShort(-1);
                data.addShort(npcId);
            } else {
                append(player, data, Equipment.SLOT_HAT, -1);
                append(player, data, Equipment.SLOT_CAPE, -1);
                append(player, data, Equipment.SLOT_AMULET, -1);
                append(player, data, Equipment.SLOT_WEAPON, -1);

                append(player, data, Equipment.SLOT_CHEST, 2);
                append(player, data, Equipment.SLOT_SHIELD, -1);
                append(player, data, Equipment.SLOT_CHEST, 3);
                append(player, data, Equipment.SLOT_LEGS, 5);

                append(player, data, Equipment.SLOT_HAT, 0);
                append(player, data, Equipment.SLOT_HANDS, 4);
                append(player, data, Equipment.SLOT_FEET, 6);
                append(player, data, Equipment.SLOT_HAT, 1);

            }

            // write the base appearance with no items worn
            data.addByte(0);
            data.addByte(0);
            data.addByte(0);
            data.addByte(0);
            data.addShort(styles[2] | 0x100);
            data.addShort(styles[3] | 0x100);
            data.addShort(styles[5] | 0x100);
            data.addShort(styles[0] | 0x100);
            data.addByte(0);
            data.addShort(styles[4] | 0x100);
            data.addShort(styles[6] | 0x100);
            data.addShort(styles[1] | 0x100);

            for(int color : colors)
                data.addByte(color);
            int[] renders;
            if(customRenders != null) {
                renders = customRenders;
            } else {
                ItemDefinition def = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
                if(def == null || def.weaponType == null)
                    renders = WeaponType.UNARMED.renderAnimations;
                else
                    renders = def.weaponType.renderAnimations;
            }
            for(int id : renders)
                data.addShort(id);
            data.addString(player.getName());
           /* if (player.getTitle() == null) { //TODO: Custom Edits
                data.addString("");
                data.addString("");
            } else {
                String title = "";
                if (player.titleId != -1 && player.titleId < Title.PRESET_TITLES.length) { //normal titles
                    title = Title.PRESET_TITLES[player.titleId].getPrefix();
                    if (player.titleId == 22) { //custom title
                        title = player.customTitle;
                    }
                }
                data.addString(title);
                data.addString(player.getTitle().getSuffix() == null ? "" : player.getTitle().getSuffix());
            }*/
            data.addByte(player.getCombat().getLevel());
            data.addShort(0); // skillLevel
            data.addByte(0); // isHidden if this == 1
            data.addShort(0); // Vex (clan hub) shit.

            for (int i = 0; i < 3; i++) {
                data.addString("");
            }

            data.addByte(gender);
        }
        out.addByte(data.position());
        out.addBytesReversed(data.payload(), 0, data.position());
    }

    private void append(Player player, OutBuffer out, int slot, int styleIndex) {
        if(styleIndex == 0 || styleIndex == 1 || styleIndex == 3) {
            Item item = player.getEquipment().get(slot);
            // Transmogrification overrides
            if (player.wildernessLevel <= 0) {
                boolean finalItem = false;
                if (player.isVisibleInterface(1011)) {
                    int previewId = player.getTransmogCollection().getPreviewIdForSlot(slot);
                    if (previewId != -2) {
                        finalItem = true;
                        if (previewId != -1) item = new Item(previewId);
                    }
                }
                int transmogId = player.getTransmogCollection().getTransmogIdForSlot(slot);
                if (!finalItem && transmogId != -1) {
                    item = new Item(transmogId);
                }
            }
            // Hide during a makeover
            if (player.isVisibleInterface(516)) {
                item = null;
            }
            boolean hide = false;
            if(item != null) {
                if(styleIndex == 0)
                    hide = item.getDef().hideHair;
                else if(styleIndex == 1)
                    hide = item.getDef().hideBeard;
                else
                    hide = item.getDef().hideArms;
            }
            if(hide)
                out.addByte(0);
            else
                out.addShort(256 + styles[styleIndex]);
        } else {
            int itemId = player.getEquipment().getId(slot);
            // Transmogrification overrides
            if (player.wildernessLevel <= 0) {
                boolean finalItem = false;
                if (player.isVisibleInterface(1011)) {
                    int previewId = player.getTransmogCollection().getPreviewIdForSlot(slot);
                    if (previewId != -2) {
                        finalItem = true;
                        if (previewId != -1) itemId = previewId;
                    }
                }
                int transmogId = player.getTransmogCollection().getTransmogIdForSlot(slot);
                if (!finalItem && transmogId != -1) {
                    ItemDefinition def = ItemDefinition.get(transmogId);
                    Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
                    ItemDefinition weaponDef = weapon == null ? null : ItemDefinition.get(weapon.getId());
                    if (slot == Equipment.SLOT_SHIELD) {
                        if (weaponDef == null || !weaponDef.twoHanded) itemId = transmogId;
                    } else if (slot == Equipment.SLOT_WEAPON) {
                        if (weaponDef == null || weaponDef.weaponType == def.weaponType) itemId = transmogId;
                    } else {
                        itemId = transmogId;
                    }
                }
            }
            // Hide during a makeover
            if (player.isVisibleInterface(516)) {
                itemId = -1;
            }
            if(itemId == -1) {
                if(styleIndex == -1)
                    out.addByte(0);
                else
                    out.addShort(256 + styles[styleIndex]);
                return;
            }
            out.addShort(2048 + itemId);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return 128;
    }

}