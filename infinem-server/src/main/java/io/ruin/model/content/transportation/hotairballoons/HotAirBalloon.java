package io.ruin.model.content.transportation.hotairballoons;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/13/2023
 */
@Getter
public enum HotAirBalloon {
    ENTRANA(4715, 19133, new Position(2809, 3356, 0), null, Config.varpbit(2867, true)),
    TAVERLY(4723, 19135, new Position(2938, 3421, 0), new Item(Items.LOGS, 10), Config.varpbit(2868, true)),
    CRAFTING_GUILD(4720, 19141, new Position(2925, 3300, 0), new Item(Items.OAK_LOGS, 10), Config.varpbit(2871, true)),
    VARROCK(4719, 19143, new Position(3298, 3481, 0), new Item(Items.WILLOW_LOGS, 10), Config.varpbit(2872, true)),
    CASTLE_WARS(4721, 19137, new Position(2460, 3109, 0), new Item(Items.YEW_LOGS, 10), Config.varpbit(2869, true)),
    GRAND_TREE(4722, 19139, new Position(2481, 3456, 0), new Item(Items.MAGIC_LOGS, 3), Config.varpbit(2870, true));


    private final int npcId, objectId;
    private final Position destination;
    private final Item unlockItem;
    private final Config config;

    HotAirBalloon(int npcId, int objectId, Position destination, Item unlockItem, Config config) {
        this.npcId = npcId;
        this.objectId = objectId;
        this.destination = destination;
        this.unlockItem = unlockItem;
        this.config = config;
    }
}
