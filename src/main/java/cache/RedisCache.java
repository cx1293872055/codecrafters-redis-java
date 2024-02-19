package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:50
 */
public class RedisCache {

    private static Map<String, String> cache;

    public static void initCache() {
        cache = new ConcurrentHashMap<>();
    }

    public static Map<String, String> getCache() {
        return cache;
    }
}
