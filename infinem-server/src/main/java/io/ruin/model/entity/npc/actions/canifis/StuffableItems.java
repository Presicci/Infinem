package io.ruin.model.entity.npc.actions.canifis;

import io.ruin.model.entity.player.Player;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum StuffableItems {

    BIG_BASS(7989, 7990,"big bass"),
    CRAWLING_HAND(7975,7982, "crawling hand"),
    COCKATRICE_HEAD(7978, 7983, "cockatirce head"),
    BIG_SWORDFISH(7991,7992, "big swordfish"),
    BASILISK_HEAD(7977,7984, "basilisk head"),
    BIG_SHARK(7993,7994,"big shark"),
    KURASK_HEAD(7978,7985,"kurask head"),
    ABYSSAL_HEAD(7979, 7986, "abyssal demon head"),
    KBD_HEAD(7980,7987,"king black dragon heads"),
    KQ_HEAD(7981, 7988, "kalphite queen head"),
    VORKATH_HEAD(21907,21909, "vorkath head"),
    ALCHEMICAL_HYDRA_HEAD(23077, 23079, "alchemical hydra heads")
    ;

    public final int itemID, stuffedItemID;
    public final String nameOfItem;

    public static Optional<StuffableItems> findStuffable(Player player){
        return  Stream.of(values()).filter(stuffable -> player.getInventory().getAmount(stuffable.itemID) > 0).findFirst();
    }

    public String getStuffName(){
        return nameOfItem;
    }
}
