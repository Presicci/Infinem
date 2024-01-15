package io.ruin.model.item.actions.impl.godbooks;

import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/14/2024
 */
@AllArgsConstructor
public enum GodBookTranscript {
    SARADOMIN(
            new String[]{
                    "In the name of Saradomin,",
                    "Protector of us all,",
                    "I now join you in the eyes of Saradomin."
            },
            new String[]{
                    "Thy cause was false,",
                    "Thy skills did lack;",
                    "See you in Lumbridge when you get back."
            },
            new String[]{
                    "Go in peace in the name of Saradomin;",
                    "May his glory shine upon you like the sun.",
                    "Like the sun."
            },
            new String[][]{
                    new String[]{
                            "Protect your self, protect your friends.",
                            "Mine is the glory that never ends.",
                            "This is Saradomin's wisdom.",
                    },
                    new String[]{
                            "The darkness in life may be avoided,",
                            "By the light of wisdom shining.",
                            "This is Saradomin's wisdom.",
                    },
                    new String[]{
                            "Show love to your friends, and mercy to your enemies,",
                            "And know that the wisdom of Saradomin will follow.",
                            "This is Saradomin's wisdom.",
                    },
                    new String[]{
                            "A fight begun, when the cause is just,",
                            "Will prevail over all others.",
                            "This is Saradomin's wisdom.",
                    },
                    new String[]{
                            "The currency of goodness is honour;",
                            "It retains its value through scarcity.",
                            "This is Saradomin's wisdom.",
                    }
            }
    ),
    GUTHIX(
            new String[]{
                    "Light and dark, day and night,",
                    "Balance arises from contrast.",
                    "I unify thee in the name of Guthix.",
            },
            new String[]{
                    "Thy death was not in vain,",
                    "For it brought some balance to the world.",
                    "May Guthix bring you rest.",
            },
            new String[]{
                    "May you walk the path, and never fall,",
                    "For Guthix walks beside thee on thy journey.",
                    "May Guthix bring you peace.",
            },
            new String[][]{
                    new String[]{
                            "The trees, the earth, the sky, the waters;",
                            "All play their part upon this land.",
                            "May Guthix bring you balance.",
                    }

            }
    ),

    ZAMORAK(
            new String[]{
                    "Two great warriors, joined by hand,",
                    "To spread destruction across the land. ",
                    "In Zamorak's name, now two are one. ",
            },
            new String[]{
                    "The weak deserve to die,",
                    "So the strong may flourish.",
                    "This is the creed of Zamorak.",
            },
            new String[]{
                    "May your bloodthirst never be sated,",
                    "And may all your battles be glorious.",
                    "Zamorak bring you strength.",
            },
            new String[][]{
                    new String[]{
                            "There is no opinion that cannot be proven true...",
                            "By crushing those who choose to disagree with it.",
                            "Zamorak give me strength!",
                    },
                    new String[]{
                            "Battles are not lost and won;",
                            "They simply remove the weak from the equation.",
                            "Zamorak give me strength!",
                    },
                    new String[]{
                            "Those who fight, then run away,",
                            "Shame Zamorak with their cowardice.",
                            "Zamorak give me strength!",
                    },
                    new String[]{
                            "Battle is by those",
                            "Who choose to disagree with it.",
                            "Zamorak give me strength!",
                    },
                    new String[]{
                            "Strike fast, strike hard, strike true",
                            "The strength of Zamorak will be with you.",
                            "Zamorak give me strength!",
                    }
            }
    ),

    ARMADYL(
            new String[]{
                    "As ye vow to be at peace with each other...",
                    "And to uphold high values of morality and friendship...",
                    "I now pronounce you united in the law of Armadyl.",
            },
            new String[]{
                    "Thou didst fight true...",
                    "But the foe was too great.",
                    "May thy return be as swift as the flight of Armadyl.",
            },
            new String[]{
                    "For thy task is lawful...",
                    "May the blessing of Armadyl",
                    "Be upon thee.",
            },
            new String[][]{
                    new String[]{
                            "Peace shall bring thee wisdom;",
                            "Wisdom shall bring thee peace.",
                            "This is the law of Armadyl.",
                    }

            }
    ),
    BANDOS(
            new String[]{
                    "Big High War God want great warriors.",
                    "Because you can make more...",
                    "I bind you in Big High War God name.",
            },
            new String[]{
                    "You not worthy of",
                    "Big High War God;",
                    "You die too easy.",
            },
            new String[]{
                    "Big High War God",
                    "Make you strong...",
                    "So you smash enemies.",
            },
            new String[][]{
                    new String[]{
                            "War is best, Peace is for weak.",
                            "If you not worthy of Big High War God...",
                            "You get made dead soon.",
                    }

            }
    ),
    ZAROS(
            new String[]{
                    "Ye faithful and loyal to the Great Lord...",
                    "May ye together succeed in your deeds.",
                    "Ye are now joined by the greatest power.",
            },
            new String[]{
                    "Thy faith faltered,",
                    "No power could save thee.",
                    "Like the Great Lord, one day you shall rise again.",
            },
            new String[]{
                    "By day or night,",
                    "In defeat or victory...",
                    "The power of the Great Lord be with thee.",
            },
            new String[][]{
                    new String[]{
                            "Though your enemies wish to silence thee,",
                            "Do not falter, defy them to the end.",
                            "Power to the Great Lord!",
                    },
                    new String[]{
                            "The followers of the Great Lord are few,",
                            "But they are powerful and mighty.",
                            "Power to the Great Lord!",
                    },
                    new String[]{
                            "Follower of the Great Lord be relieved ",
                            "One day your loyalty will be rewarded.",
                            "Power to the Great Lord!",
                    },
                    new String[]{
                            "Pray for the day that the Great Lord rises; ",
                            "It is that day thou shalt be rewarded.",
                            "Power to the Great Lord!",

                    },
                    new String[]{
                            "Oppressed thou art, but fear not",
                            "The day will come when the Great Lord rises.",
                            "Power to the Great Lord!",
                    },
                    new String[]{
                            "Fighting oppression is the wisest way,",
                            "To prove your worth to the Great Lord.",
                            "Power to the Great Lord!",
                    },
            }
    );

    protected final String[] weddingCeremony, lastRites, blessings;
    protected final String[][] preach;
}
