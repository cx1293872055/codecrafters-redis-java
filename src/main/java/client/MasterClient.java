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
        Reply ping = Reply.multiReply(Reply.status("PING"));
        sendRequest(ping);
    }

    @Override
    public void replConf() {
        sendRequest(Reply.multiReply(Reply.length("REPLCONF"),
                                     Reply.length("listening-port"),
                                     Reply.length(Encoding.numToBytes(RedisConfig.port))));

        sendRequest(Reply.multiReply(Reply.length("REPLCONF"),
                                     Reply.length("capa"),
                                     Reply.length("eof"),
                                     Reply.length("capa"),
                                     Reply.length("psync2")));
    }

    @Override
    public void psync() {
        sendRequest(Reply.multiReply(Reply.length("PSYNC"),
                                     Reply.length("?"),
                                     Reply.length(Encoding.numToBytes(-1))));
    }

}
