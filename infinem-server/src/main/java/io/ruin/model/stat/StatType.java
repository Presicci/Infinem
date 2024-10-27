package io.ruin.model.stat;

import io.ruin.api.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public enum StatType {

    Attack(1,
            60.0,10.0,
            9747, 9748, 9749, 32007,
            4959
    ),
    Defence(5,
            60.0, 10.0,
            9753, 9754, 9755, 32011,
            4961
    ),
    Strength(2,
            60.0, 10.0,
            9750, 9751, 9752, 32026,
            4981
    ),
    Hitpoints(6,
            60.0, 10.0,
            9768, 9769, 9770, 32020,
            4971
    ),
    Ranged(3,
            60.0, 10.0,
            9756, 9757, 9758, 32022,
            4973
    ),
    Prayer(7,
            60.0, 10.0,
            9759, 9760, 9761, 32021,
            4979
    ),
    Magic(4,
            60.0, 10.0,
            9762, 9763, 9764, 32019,
            4939
    ),
    Cooking(16,
            15.0, 10.0,
            9801, 9802, 9803, 32009,
            4955
    ),
    Woodcutting(18,
            15.0, 10.0,
            9807, 9808, 9809, 32028,
            4957
    ),
    Fletching(19,
            15.0, 10.0,
            9783, 9784, 9785, 32015,
            4937
    ),
    Fishing(15,
            15.0, 10.0,
            9798, 9799, 9800, 32014,
            4951
    ),
    Firemaking(17,
            15.0, 10.0,
            9804, 9805, 9806, 32013,
            4975
    ),
    Crafting(11,
            15.0, 10.0,
            9780, 9781, 9782, 32010,
            4949
    ),
    Smithing(14,
            15.0, 10.0,
            9795, 9796, 9797, 32025,
            4943
    ),
    Mining(13,
            15.0, 10.0,
            9792, 9793, 9794, 32020,
            4941
    ),
    Herblore(9,
            15.0, 10.0,
            9774, 9775, 9776, 32016,
            4969
    ),
    Agility(8,
            15.0, 10.0,
            9771, 9772, 9773, 32006,
            4977
    ),
    Thieving(10,
            15.0, 10.0,
            9777, 9778, 9779, 32027,
            4965
    ),
    Slayer(20,
            20.0, 10.0,
            9786, 9787, 9788, 32024,
            4967
    ),
    Farming(21,
            15.0, 10.0,
            9810, 9811, 9812, 32012,
            4963
    ),
    Runecrafting(12,
            15.0, 10.0,
            9765, 9766, 9767, 32023,
            4947
    ),
    Hunter(23,
            15.0, 10.0,
            9948, 9949, 9950, 32018,
            5158
    ),
    Construction(22,
            15.0, 10.0,
            9789, 9790, 9791, 32008,
            4953
    );

    public final int clientId;

    public final double defaultXpMultiplier, after99XpMultiplier;

    public final int regularCapeId, trimmedCapeId, hoodId, masterCapeId, emoteId;

    public final String descriptiveName;

    public boolean isCombat() {
        if (this.ordinal() <= 6) {
            return true;
        } else {
            return false;
        }
    }
    StatType(int clientId, double defaultXpMultiplier, double after99XpMultiplier, int regularCapeId, int trimmedCapeId, int hoodId, int masterCapeId, int emoteId) {
        this.clientId = clientId;
        this.defaultXpMultiplier = defaultXpMultiplier;
        this.after99XpMultiplier = after99XpMultiplier;
        this.regularCapeId = regularCapeId;
        this.trimmedCapeId = trimmedCapeId;
        this.hoodId = hoodId;
        this.masterCapeId = masterCapeId;
        this.emoteId = emoteId;

        String name = name();
        if(StringUtils.vowelStart(name))
            descriptiveName = "an " + name;
        else
            descriptiveName = "a " + name;
    }

    /**
     * Get by id
     */

    public static StatType get(int id) {
        return values()[id];
    }

    /**
     * Get by name
     */

    public static StatType get(String name) {
        for(StatType type : values()) {
            if(name.equalsIgnoreCase(type.name()))
                return type;
        }
        switch(name.toLowerCase()) {
            case "att":
            case "atk":
                return Attack;
            case "str":
                return Strength;
            case "def":
                return Defence;
            case "hp":
                return Hitpoints;
            case "wc":
                return Woodcutting;
            case "rc":
                return Runecrafting;
            case "fm":
                return Firemaking;
            case "con":
                return Construction;
        }
        return null;
    }

    /**
     * Client order
     */

    public static final StatType[] CLIENT_ORDER;

    static {
        ArrayList<StatType> list = new ArrayList<>(Arrays.asList(values()));
        list.sort(Comparator.comparingInt(s -> s.clientId));
        CLIENT_ORDER = list.toArray(new StatType[list.size()]);
    }

}
