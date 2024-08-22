package io.ruin.api.utils;

import java.math.BigInteger;
import java.util.Map;

public class XenPost {

    private static final String URL = "https://www.infinem.net/integration/index.php";

    private static final BigInteger AUTH_KEY = new BigInteger("104669708125332383643049895335043994044443794620533303994927");

    public static String post(String operation, Map<Object, Object> map) {
        map.put("auth", AUTH_KEY);
        map.put("operation", operation);
        return PostWorker.post(URL, map);
    }

}
