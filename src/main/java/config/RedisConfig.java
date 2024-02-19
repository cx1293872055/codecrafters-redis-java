package config;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 17:27
 */
public class RedisConfig {

    public static int port = 6397;
    public static boolean isMaster = true;

    public static String masterHost = null;
    public static int masterPort = 6397;

    public static void loadConfig(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("--port".equalsIgnoreCase(args[i])
                    || "-p".equalsIgnoreCase(args[i])) {
                port = Integer.parseInt(args[++i]);
            }
            if ("--replicaof".equalsIgnoreCase(args[i])) {
                masterHost = args[++i];
                masterPort = Integer.parseInt(args[++i]);
                isMaster = false;
            }
        }
    }
}
