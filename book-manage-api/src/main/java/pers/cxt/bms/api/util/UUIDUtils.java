package pers.cxt.bms.api.util;

import java.util.UUID;

public class UUIDUtils {
    /**
     * uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
