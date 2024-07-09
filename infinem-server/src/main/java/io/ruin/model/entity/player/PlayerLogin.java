package io.ruin.model.entity.player;

import io.ruin.Server;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.protocol.login.LoginRequest;
import io.ruin.data.impl.login_set;
import io.ruin.model.World;
import io.ruin.network.central.CentralClient;
import io.ruin.utility.OfflineMode;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

@Slf4j
public class PlayerLogin extends LoginRequest {

    private static final boolean[] LOADING = new boolean[World.players.entityList.length];

    public PlayerLogin(LoginInfo info) {
        super(info);
        send();
    }

    private void send() {
        info.name = info.name.replaceAll("_", " ");
        if(OfflineMode.loadPlayer(this))
            return;
        CentralClient.sendLogin(info.ipAddress, info.name, info.password, info.email, info.macAddress, info.uuid, info.tfaCode, info.tfaTrust, info.tfaTrustValue & 0xff);
    }

    @Override
    public void success() {
        if(login_set.deny(this))
            return;
        Server.worker.execute(this::accept);
    }

    private void accept() {
        if(World.updating) {
            deny(Response.UPDATING);
            return;
        }
        int index = -1;
        for(int i = 1; i < World.players.entityList.length; i++) {
            Player player = World.players.entityList[i];
            if(player == null) {
                if(index == -1 && !LOADING[i])
                    index = i;
                continue;
            }
            if(player.getUserId() == info.userId) {
                super.deny(Response.ALREADY_LOGGED_IN);
                Server.logWarning(player.getName() + " is already online!");
                return;
            }
        }
        if(index == -1) {
            deny(Response.WORLD_FULL);
            return;
        }
        load(index);
    }

    private void load(int index) {
        LOADING[index] = true;
        Server.worker.execute(() -> PlayerFile.load(this), player -> {
            if (player == null) {
                deny(Response.ERROR_LOADING_ACCOUNT);
                return;
            }
            int returnCode = phpbbAuth();
            if (returnCode == -1) {
                if (player.getPassword() != null && player.getPassword().contains(":") && !info.password.equalsIgnoreCase(decrypt("6YUYrFblfufvV0m9", player.getPassword()))) {
                    deny(Response.INVALID_LOGIN);
                    return;
                }
            } else if (returnCode == 0) {
                deny(Response.INVALID_LOGIN);
                return;
            }
            player.setIndex(index);
            player.init(info);
            World.players.set(index, player);
            LOADING[index] = false;
            player.getPacketSender().sendLogin(info);
            player.getChannel().pipeline().replace("decoder", "decoder", player.getDecoder());
        });
    }

    private static final BigInteger AUTH_KEY = new BigInteger("104669708125332383643049895335043994044443794620533303994927");

    private int phpbbAuth() {
        try {
            String urlString = "https://www.infinem.net/client_auth.php?auth_key="+AUTH_KEY+"&name="+info.name.toLowerCase().replace(" ","_")+"&pass="+encrypt("6YUYrFblfufvV0m9", "o3ZXgtKvts1YRYQT", info.password);
            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = in.readLine().trim();
            return Integer.parseInt(line);
        } catch (Exception e) {
            return -1;
        }
    }

    private static final String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static final int CIPHER_KEY_LEN = 16; //128 bits

    public static String encrypt(String key, String iv, String data) {
        try {
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();

                StringBuilder keyBuilder = new StringBuilder(key);
                for(int i = 0; i < numPad; i++){
                    keyBuilder.append("0"); //0 pad to len 16 bytes
                }
                key = keyBuilder.toString();

            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }


            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));

            String base64_EncryptedData = Base64.getEncoder().encodeToString(encryptedData);
            String base64_IV = Base64.getEncoder().encodeToString(iv.getBytes("UTF-8"));

            return base64_EncryptedData + ":" + base64_IV;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String data) {
        try {
            String[] parts = data.split(":");

            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);

            byte[] original = cipher.doFinal(decodedEncryptedData);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void deny(Response response) {
        super.deny(response);
        CentralClient.sendLogout(info.userId);
    }

}