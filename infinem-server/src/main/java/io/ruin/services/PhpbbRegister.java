package io.ruin.services;

import io.ruin.api.utils.XenPost;
import io.ruin.model.entity.player.Player;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/1/2024
 */
public class PhpbbRegister {

    public static void register(Player player) {
        CompletableFuture.runAsync(() -> { // PostWorker uses blocking I/O and this method is called from the main thread, so we should run it on another thread
            // check if forum acc is linked, if not, just rename character
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put("name", player.getName());
            map.put("email", player.getName() + "@infinem.net");
            map.put("password", player.getPassword());
            String result = XenPost.post("register_account", map);
        });
    }
}
