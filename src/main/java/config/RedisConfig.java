package config;

import request.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 17:27
 */
public class RedisConfig {

    public static int port = 6379;
    public static boolean isMaster = true;

    public static String id = "8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb";
    public static int offSet = 0;
    private static boolean startAck = false;

    public static String masterHost = null;
    public static int masterPort = 6379;

    private static Map<String, String> config = new HashMap<>();

    public static String getConfig(String key, String defaultValue) {
        return config.getOrDefault(key, defaultValue);
    }

    public static String getDbFileName() {
        return RedisConfig.getConfig("dir", "") + "/" + RedisConfig.getConfig("dbfilename", "");
    }

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
            if ("--dir".equalsIgnoreCase(args[i])) {
                config.put("dir", args[++i]);
            }
            if ("--dbfilename".equalsIgnoreCase(args[i])) {
                config.put("dbfilename", args[++i]);
            }
        }
    }

    public static void startAck() {
        if (!startAck) {
            startAck = true;
            System.out.println("slave start ack ---------");
        }
    }

    public static void increaseOffSet(Request request) {
        if (startAck) {
            offSet += request.rawCommand().length();
        }
    }
}
