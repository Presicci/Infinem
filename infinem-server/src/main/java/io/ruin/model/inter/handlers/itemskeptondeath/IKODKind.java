package io.ruin.model.inter.handlers.itemskeptondeath;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/9/2023
 */
public enum IKODKind {
    Deleted(-1),
    OtherKept(343),
    OtherKeptDowngraded(323),
    Lost(367),
    LostDowngraded(369),
    LostGraveyardCoins(7531),
    LostToThePlayerWhoKillsYou(1991);

    public final int configItem;

    IKODKind(int configItem) {
        this.configItem = configItem;
    }
}

