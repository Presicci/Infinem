package io.ruin.model.skills.cooking.gnome;

import lombok.Getter;

public enum RestaurantCustomer {
    // Easy
    BURKOR(2575, "in the tower south-east of the Gnome Ball field."),
    BRIMSTAIL(4913, "in a cave in the south-west of the stronghold."),
    CAPTAIN_ERDO(10467, "our glider pilot at the top of the tree."),
    GNOME_COACH(3142, "at the Gnome Ball field."),
    DALILA(2563, "in the restaurant."),
    DAMWIN(2559, "in the pub east of the Gnome Ball field."),
    EEBEL(2566, "in his house in the south-west of the stronghold."),
    ERMIN(2567, "in his house in the south-east of the stronghold."),
    FEMI(1431, "just outside the gate to the stronghold."),
    FROONO(2576, "in a house on the south side of the stronghold."),
    GUARD_VEMMELDO(2574, "in the bank west of the agility course."),
    GULLUCK(15, "our armourer on the third floor."),
    HECKEL_FUNCH(16, "selling drinks on the east side of the tree."),
    HIS_ROYAL_HIGHNESS_KING_NARNODE(8020, "at the base of the tree."),
    MEEGLE(2571, "a terrorbird breeder in the south-west of the stronghold."),
    PERRDUR(2562, "in the restaurant."),
    ROMETTI(14, "our tailor to the east."),
    SARBLE(2573, "in the swamp field to the west of the tree."),
    TRAINER_NACKLEPEN(6101, "in the tortoise pen to the west of the tree."),
    WURBEL(2572, "in the stands south of the Gnome Ball field."),

    // Hard
    AMBASSADOR_FERRNOOK(2557, "on the second floor of Varrock palace."),
    AMBASSADOR_GIMBLEWAP(2555, "on the second floor of Ardougne castle."),
    AMBASSADOR_SPANFIPPLE(2556, "on the second floor of the White Knights' Castle."),
    BRAMBICKLE(2551, "south of the Relekka hunter area, near the fairy ring."),
    CAPTAIN_BLEEMADGE(10459, "a glider pilot at the top of White Wolf Mountain."),
    CAPTAIN_DAERKIN(2570, "watching fights at the Duel Arena."),
    CAPTAIN_DALBUR(10452, "a glider pilot in Al Kharid."),
    CAPTAIN_KLEMFOODLE(10479, "a glider pilot in south-east Karamja."),
    CAPTAIN_NINTO(2569, "beneath White Wolf Mountain."),
    GLO_CARANOCK(1460, "at the Karamja shipyard."),
    GARKOR(7158, "spying on Awowogei on Ape Atoll."),
    GNORMADIUM_AVLAFRIM(7517, "a glider pilon in Feldip Hills."),
    HAZELMERE(1422, "on an island east of Yanille."),
    KING_BOLREN(4963, "in Tree Gnome Village."),
    LIEUTENANT_SCHEPBUR(6100, "on the battlefield, north of Tree Gnome Village."),
    PENWIE(2553, "just west of the glider on Karamja."),
    PROFESSOR_IMBLEWYN(2561, "in the Wziards' Guild in Yanille."),
    PROFESSOR_MANGLETHORP(2558, "west side of the main Keldagrim hall."),
    PROFESSOR_ONGLEWIP(2560, "at the Wizards' Tower."),
    WINGSTONE(2552, "west of Nardah.");

    @Getter private final int npcId;
    @Getter private final String locationString;

    RestaurantCustomer(int npcId, String locationString) {
        this.npcId = npcId;
        this.locationString = locationString;
    }
}