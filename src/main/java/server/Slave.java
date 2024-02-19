package server;

import client.Client;
import config.RedisConfig;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:01
 */
public class Slave extends BaseServer{
    private String masterHost;
    private int masterPort;

    private Server master;

    @Override
    public Client getMasterClient() {
        return null;
    }

    @Override
    public void initial() {
        masterHost = RedisConfig.masterHost;
        masterPort = RedisConfig.masterPort;


    }

    @Override
    public void ping() {

    }

}
