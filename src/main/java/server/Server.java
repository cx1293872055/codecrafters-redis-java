package server;

import client.Client;
import config.RDBReader;
import config.RedisConfig;
import request.Request;

import java.util.Collection;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:02
 */
public interface Server {

    static Server loadRedis(String[] args) {
        RedisConfig.loadConfig(args);
        new RDBReader().loadData()
                       .exchangeToLocalCache();
        return RedisConfig.isMaster ? new Master() : new Slave();
        // RedisConfig.loadConfig(args);
        // int[] datas = {0x52 ,0x45 ,0x44 ,0x49 ,0x53 ,0x30 ,0x30 ,0x30 ,0x33 ,0xFA ,0x09 ,0x72 ,0x65 ,0x64 ,0x69 ,0x73 ,
        //                0x2D ,0x76 ,0x65 ,0x72 ,0x05 ,0x37 ,0x2E ,0x32 ,0x2E ,0x30 ,0xFA ,0x0A ,0x72 ,0x65 ,0x64 ,0x69 ,
        //                0x73 ,0x2D ,0x62 ,0x69 ,0x74 ,0x73 ,0xC0 ,0x40 ,0xFE ,0x00 ,0xFB ,0x01 ,0x00 ,0x00 ,0x05 ,0x61 ,
        //                0x70 ,0x70 ,0x6C ,0x65 ,0x06 ,0x6F ,0x72 ,0x61 ,0x6E ,0x67 ,0x65 ,0xFF ,0x19 ,0xFE ,0xBF ,0x87 ,
        //                0x75 ,0xD2 ,0x84 ,0x8F ,0xAA};
        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        // for (int data : datas) {
        //     out.write(data);
        // }
        //
        // ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        // new RDBReader().getRdbReader(in)
        //                .exchangeToLocalCache();
        // return RedisConfig.isMaster ? new Master() : new Slave();
    }

    void handleClient(Runnable runnable);

    Client getMasterClient();

    void setReplica(Client replica);

    Collection<Client> getReplicas();

    void propagation(Request request);

    void initial();

    void start();

    void setReplicaOffSet(Client client, int offSet);

}
