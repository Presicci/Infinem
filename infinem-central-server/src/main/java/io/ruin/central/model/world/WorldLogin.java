package io.ruin.central.model.world;

import com.google.gson.JsonObject;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.protocol.login.LoginRequest;
import io.ruin.api.utils.*;
import io.ruin.central.Server;
import io.ruin.central.model.Player;
import io.ruin.central.utility.XenforoUtils;
import io.ruin.model.entity.player.PlayerGroup;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class WorldLogin extends LoginRequest {

    private World world;

    public static String capitalize(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("_", " ");
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i+1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i+2));
                }
            }
        }
        return s;
    }

    public WorldLogin(World world, LoginInfo info) {
        super(info);
        this.world = world;

        if (UUIDBan.isUUIDBanned(info.uuid) || IPBans.isIPBanned(info.ipAddress) || MACBan.isMACBanned(info.macAddress)) {
            this.deny(Response.DISABLED_ACCOUNT);
            return;
        }

      /*  CompletableFuture.runAsync(() -> {

                System.err.println(info.name + " attempting to login.");

                JSONObject result = XenforoUtils.attemptLogin(info.name, info.password, info.ipAddress);

                //Should never happen unless website is down
                if (result == null) {
                    this.deny(Response.LOGIN_SERVER_NO_REPLY);
                    return;
                }

                String login_code = result.getString("login_code");
                System.err.println(info.name + " : " + login_code);

                if (login_code.endsWith("could not be found.")) {
                    this.deny(Response.EMAIL_REQUIRED);
                    return;
                }
                switch (login_code) {
                    case "Unregistered account.": {
                        this.deny(Response.UNREGISTERED_ACCOUNT);
                        return;
                    }
                    case "Email required.": {
                        this.deny(Response.EMAIL_REQUIRED);
                        return;
                    }
                    case "Email in use.": {
                        this.deny(Response.EMAIL_IN_USE);
                        return;
                    }
                    case "Username too long.": {
                        this.deny(Response.USERNAME_TOO_LONG);
                        return;
                    }
                    case "Username contained disallowed words.": {
                        this.deny(Response.USERNAME_BAD_WORDS);
                        return;
                    }
                    case "Username contains incorrect characters.": {
                        this.deny(Response.USERNAME_BAD_LETTERS);
                        return;
                    }
                    case "Incorrect password. Please try again.": {
                        this.deny(Response.INVALID_LOGIN);
                        return;
                    }
                    case "Your account has temporarily been locked due to failed login attempts.": {
                        this.deny(Response.LOGIN_LIMIT);
                        return;
                    }
                    case "Two-factor authentication required.": {
                        this.deny(Response.TWO_FACTOR);
                        return;
                    }
                    case "Two-factor authentication limit reached!": {
                        this.deny(Response.LOGIN_LIMIT);
                        return;
                    }
                    case "Two-factor authentication invalid!": {
                        this.deny(Response.TWO_FACTOR_RETRY);
                        return;
                    }
                    case "Banned": {
                        this.deny(Response.DISABLED_ACCOUNT);
                        return;
                    }
                    case "IP Banned": {
                        this.deny(Response.DISABLED_ACCOUNT);
                        return;
                    }
                    case "Username on hold": {
                        this.deny(Response.USERNAME_ON_HOLD);
                        return;
                    }
                    case "Proxy login attempt": {
                        this.deny(Response.PROXY_LOGIN_ATTEMPT);
                        return;
                    }
                }
                try {
                    JSONObject user = result.getJSONObject("user");
                    int userId = user.getInt("user_id");
                    String username = user.getString("username");
                    int primaryGroupId = user.getInt("user_group_id");
                    String secondaryGroupIds = String.valueOf(user.getJSONArray("secondary_group_ids"));
                    //int unreadPms = user.get("conversations_unread").getAsInt();
                    if (Protocol.method360(username) == null) {
                        this.deny(Response.CHANGE_DISPLAY_NAME);
                        return;
                    }
                    ArrayList<Integer> groupIds = new ArrayList<Integer>();
                    groupIds.add(primaryGroupId);
                    for (String id : secondaryGroupIds.replace("[", "").replace("]", "").replace("\"", "").split(",")) {  //["10", "10"]
                        try {
                            groupIds.add(Integer.valueOf(id));
                        } catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    for (int i : groupIds) {
                        if (i == 18) {
                            this.deny(Response.DISABLED_ACCOUNT);
                            return;
                        }
                    }*/
        try {
            String username = capitalize(info.name);
            int index = -1;
            for(int i = 1; i < 2048; i++) {
                Player player = Server.getPlayer(i);
                if(player == null) {
                    index = i;
                    break;
                }
            }
            if(index != -1) {
                String saved = Player.load(username, world);
                info.update(index, username, saved, ListUtils.toList(PlayerGroup.ADMINISTRATOR.id), 0);
                this.success();
            } else {
                this.deny(Response.WORLD_FULL);
            }
        } catch (Exception e) {
            Server.logError(e.getMessage());
            this.deny(Response.ERROR_LOADING_ACCOUNT);
        }
        //  }
        //  );
    }

    @Override
    public void success() {
        this.world.logins.offer(this.info);
    }

    @Override
    public void deny(Response response) {
        this.world.sendLoginFailed(this.info.name, response);
        super.deny(response);
    }
}

