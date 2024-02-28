package cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:50
 */
public class RedisCache {

    private static Map<String, String> cache;
    private static Map<String, Long> expireMap;

    public static void initCache() {
        if (Objects.isNull(cache)) {
            cache = new ConcurrentHashMap<>();
        }
        if (Objects.isNull(expireMap)) {
            expireMap = new ConcurrentHashMap<>();
        }
    }

    public static void setKV(String key, String value) {
        initCache();
        cache.put(key, value);
    }

    public static void setExpireKV(String key, String value, long expire) {
        initCache();
        setKV(key, value);
        if (expire != -1) {
            expireMap.put(key, System.currentTimeMillis() + expire);
        }
    }

    public static String getValue(String key) {
        String value = cache.get(key);
        Long expireMills = expireMap.get(key);
        if (Objects.isNull(expireMills)) {
            return value;
        } else if (expireMills < System.currentTimeMillis()) {
            cache.remove(key);
            expireMap.remove(key);
            return null;
        } else {
            return value;
        }
    }

    public static Map<String, String> getCache() {
        return cache;
    }
}
