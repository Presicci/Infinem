package io.ruin.model.entity.player.killcount;

import io.ruin.cache.NPCDef;

import java.util.Objects;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/10/2023
 */
public enum BossKillCounter {
    ABYSSAL_SIRE("Abyssal Sire"),
    ALCHEMICAL_HYDRA("Alchemical Hydra"),
    //Artio
    BARROWS("Barrows Chests"),
    BRYOPHYTA("Bryophyta"),
    CALLISTO("Callisto"),
    //Calvar'ion
    CERBERUS("Cerberus"),
    COX("Chambers of Xeric"),
    //COX_CHALLENGE("Chambers of Xeric: Challenge Mode"),
    CHAOS_ELE("Chaos Elemental"),
    CHAOS_FANATIC("Chaos Fanatic"),
    COMMANDER_ZILYANA("Commander Zilyana"),
    CORP_BEAST("Corporeal Beast"),
    CRAZY_ARCHAEOLOGIST("Crazy Archaeologist"),
    //ELVARG("Elvarg"),
    DAG_PRIME("Dagannoth Prime"),
    DAG_REX("Dagannoth Rex"),
    DAG_SUPREME("Dagannoth Supreme"),
    //DERANGED_ARCHAEOLOGIST("Deranged Archaeologist"),
    GENERAL_GRAARDOR("General Graardor"),
    GIANT_MOLE("Giant Mole"),
    GROTESQUE_GUARDIANS("Grotesque Guardians"),
    HESPORI("Hespori"),
    KRIL_TSUTSAROTH("K'ril Tsutsaroth"),
    KALPHITE_QUEEN("Kalphite Queen"),
    KING_BLACK_DRAGON("King Black Dragon"),
    KRAKEN("Kraken"),
    KREEARRA("Kree'Arra"),
    //MIMIC("Mimic"),
    //NEX("Nex"),
    OBOR("Obor"),
    //PHANTOM_MUSPAH("Phantom Muspah"),
    //PHOSANIS_NIGHTMARE("Phosani's Nightmare"),
    SARACHNIS("Sarachnis"),
    SCORPIA("Scorpia"),
    SKOTIZO("Skotizo"),
    //SPINDEL
    //TEMPEROSS("Tempeross"),
    //THE_CORRUPTED_GAUNTLET("The Corrupted Gauntlet"),
    //THE_GAUNTLET("The Gauntlet"),
    //THE_NIGHTMARE("The Nightmare"),
    TOB("Theatre of Blood"),
    //TOB_ENTRY("Theatre of Blood: Entry Mode"),
    //TOB_HARD("Theatre of Blood: Hard Mode"),
    THERMONUCLEAR_SMOKE_DEVIL("Thermonuclear Smoke Devil"),
    //TOA("Tombs of Amascut"),
    //TOA("Tombs of Amascut: Entry Mode"),
    //TOA("Tombs of Amascut: Expert Mode"),
    ZUK("TzKal-Zuk"),
    JAD("TzTok-Jad"),
    VENENATIS("Venenatis"),
    VETION("Vet'ion"),
    VORKATH("Vorkath"),
    WINTERTODT("Wintertodt"),
    //ZALCANO("Zalcano"),
    ZULRAH("Zulrah");

    public final String name;

    BossKillCounter(String name) {
        this.name = name;
        NPCDef.cached.values().stream().filter(Objects::nonNull)
                .filter(def -> def.name.toLowerCase().contains(name.toLowerCase()))
                .forEach(def -> def.killCounterType = new KillCounterType(this));
    }
}
