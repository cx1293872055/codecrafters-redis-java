package server;

import client.Client;
import client.MasterClient;
import config.RedisConfig;

import java.util.Objects;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:01
 */
public class Slave extends BaseServer {
    private String masterHost;
    private int masterPort;

    private Client master = null;

    @Override
    public Client getMasterClient() {
        if (Objects.isNull(this.master))
            this.master = new MasterClient(this.masterHost, this.masterPort);
        return this.master;
    }

    @Override
    public void initial() {
        System.out.println("slave initial finish");
        this.port = RedisConfig.port;
        this.masterHost = RedisConfig.masterHost;
        this.masterPort = RedisConfig.masterPort;

        handleClient(new MasterHandler(this, getMasterClient()));
        getMasterClient().handshake();
    }
}
