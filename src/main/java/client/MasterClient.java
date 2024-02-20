package client;

import codec.Encoding;
import config.RedisConfig;
import reply.Reply;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:24
 */

public class MasterClient extends BaseClient {

    public MasterClient(String host, int port) {
        super(host, port);
    }

    @Override
    public void ping() {
        Reply ping = Reply.multiReply(Reply.value("PING"));
        sendRequest(ping);
    }

    @Override
    public void replConf() {
        sendRequest(Reply.multiReply(Reply.value("REPLCONF"),
                                     Reply.value("listening-port"),
                                     Reply.value(Encoding.numToBytes(RedisConfig.port))));

        sendRequest(Reply.multiReply(Reply.value("REPLCONF"),
                                     Reply.value("capa"),
                                     Reply.value("psync2")));
    }

    @Override
    public void psync() {
        sendRequest(Reply.multiReply(Reply.value("PSYNC"),
                                     Reply.value("?"),
                                     Reply.value(Encoding.numToBytes(-1))));
    }
}
