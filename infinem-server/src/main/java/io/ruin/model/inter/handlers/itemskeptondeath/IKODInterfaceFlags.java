package io.ruin.model.inter.handlers.itemskeptondeath;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/9/2023
 */
public class IKODInterfaceFlags {
    public final boolean protect;
    public final boolean skull;
    public final boolean wilderness;
    public final boolean killedByAPlayer;

    IKODInterfaceFlags(boolean protect, boolean skull, boolean wilderness, boolean killedByAPlayer) {
        this.protect = protect;
        this.skull = skull;
        this.wilderness = wilderness;
        this.killedByAPlayer = killedByAPlayer;
    }
}
