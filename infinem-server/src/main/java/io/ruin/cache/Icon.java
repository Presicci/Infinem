package io.ruin.cache;

public enum Icon {
    /*
     * 1 Admin
     * 2 Ironman
     * 3 Ultimate
     * 4-9 Skulls
     * 10 Hardcore
     * 11 Bank filler icon
     * 12 Grey link symbol
     * 13 Silver link symbol
     * 14 Silver pearl?
     * 15 Help/Info symbol
     * 16 Woodcutting
     * 17 Mining
     * 18 Fishing
     * 19 Chat bubble
     * 20 Wilderness emblem
     * 21 Gravestone
     * 22 Leagues symbol
     * 23 Bond
     * 24 Bronze chalice
     * 25 Iron chalice
     * 26 Steel chalice
     * 27 Mithril chalice
     * 28 Addy chalice
     * 29 Rune chalice
     * 30 Dragon chalice
     * 31 Multicombat swords
     * 32 Smily face
     * 33-35 Basic clan rank wings
     * 36 Orange star
     * 37 Silver star
     * 38 Gold star
     * 39 Gold key
     * 40 Different admin symbol
     * 41-49 Nothing
     * 50 Smily with outline
     * 51-53 Clan rank wings with outline
     * 54 Orange star with outline
     * 55 Silver star with outline
     * 56 Gold star with outline
     * 57 Gold key with outline
     * 58 Different admin symbol with outline
     * 59 Green smily
     * 60 Orange key
     * 61 Silver key
     * 62 Gold key with shading
     * 63 Ugly admin crown
     * 64 Red X
     * 65 Star?
     * 66 Cat face
     * 67 Cat face w/orange nose
     * 68 Orange cat face
     * 69 Hellcat face
     * 70 Detailed skull
     * 71 Alien face?
     * 72 Gold skull
     * 73-75 Gnome head
     * 76 Demon
     * 77 Dead tree
     * 78 Evergreen tree
     * 79 Decorated evergreen tree
     * 80 Oak tree
     * 81 Willow tree
     * 82 Maple tree
     * 83 Yew tree
     * 84 Blisterwood tree
     * 85 Magic tree
     * 86-87 Orange rank symbols
     * 88-110 Clan rank symbols
     * 111 Wind rune symbol
     * 112 Mind rune symbol
     * 113 Water rune symbol
     * 114 Earth rune symbol
     * 115 fire rune symbol
     * 116 Body rune symbol
     * 117 Cosmic rune symbol
     * 118 Chaos rune symbol
     * 119 Nature rune symbol
     * 120 Law rune symbol
     * 121 Death rune symbol
     * 122 Astral rune symbol
     * 123 Blood rune symbol
     * 124 Soul rune symbol
     * 125 Wrath rune symbol
     * 126 Opal
     * 127 Jade
     * 128 Red topaz
     * 129 Sapphire
     * 130 Emerald
     * 131 Ruby
     * 132 Diamond
     * 133 Dragonstone
     * 134 Onyx
     * 135 Zenyte
     * 136-143 Metal swords
     * 144-151 Metal picks
     * 152-159 Metal bars
     * 160-167 Metal shields
     * 168-175 Metal scimitars
     * 176-183 Metal hammers
     * 184 Attack
     * 185 Strength
     * 186 Defence
     * 187 Ranged
     * 188 Prayer
     * 189 Magic
     * 190 Runecrafting
     * 191 Constitution
     * 192 Agility
     * 193 Herblore
     * 194 Thieving
     * 195 Crafting
     * 196 Fletching
     * 197 Mining
     * 198 Smithing
     * 199 Fishing
     * 200 Cooking
     * 201 Firemaking
     * 202 Woodcutting
     * 203 Slayer
     * 204 Farming
     * 205 Construction
     * 206 Hunter
     * 207 Total level
     * 208 Smaller total level
     * 209 Lumbridge symbol
     * 210 Karamja symbol
     * 211 Misthalin symbol
     * 212 Al-kharid symbol
     * 213 Morytania symbol
     * 214 Wilderness symbol
     * 215 Kandarin symbol
     * 216 Rellekka symbol
     * 217 Seren symbol
     * 218 Brassica
     * 219 Saradomin
     * 220 Guthix
     * 221 Zamorak
     * 222 Seren
     * 223 Bandos
     * 224 Zaros
     * 225 some god
     * 226 some other god
     * 227 Port sarim anchor
     * 228 Potion
     * 229 Chest
     * And theres more
     */
    RED_INFO_BADGE(83),
    YELLOW_INFO_BADGE(84),
    MYSTERY_BOX(33),
    BLUE_INFO_BADGE(85),
    GREEN_INFO_BADGE(86),
    WILDERNESS(46),
    ANNOUNCEMENT(55),
    HCIM_DEATH(10),//32
    DONATOR(19),//142
    INFO(15),
    RED_STAR(30),
    GOLD_STAR(32),
    SILVER_STAR(31),
    SKULL(9)
    ;

    public final int imgId;

    Icon(int imgId) {
        this.imgId = imgId;
    }


    public String tag() {
        return "<img=" + imgId + ">";
    }

}
