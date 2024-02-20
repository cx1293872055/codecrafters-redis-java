package server;

import client.Client;
import client.MasterClient;
import config.RedisConfig;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:01
 */
public class Master extends BaseServer{
    @Override
    public Client getMasterClient() {
        return new MasterClient("localhost", port);
    }

    @Override
    public void initial() {
        this.port = RedisConfig.port;
    }
}
