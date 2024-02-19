package server;

import client.Client;
import config.RedisConfig;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:02
 */
public interface Server {

    static Server loadRedis(String[] args) {
        RedisConfig.loadConfig(args);
        return RedisConfig.isMaster ? new Master() : new Slave();
    }

    Client getMasterClient();

    void initial();

    void start();

    void ping();
}
