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
        sendRequest(Reply.multiReply(Reply.info("REPLCONF"),
                                     Reply.info("listening-port"),
                                     Reply.info(Encoding.numToBytes(RedisConfig.port))));

        sendRequest(Reply.multiReply(Reply.info("REPLCONF"),
                                     Reply.info("capa"),
                                     Reply.info("eof"),
                                     Reply.info("capa"),
                                     Reply.info("psync2")));
    }

    @Override
    public void psync() {
        sendRequest(Reply.multiReply(Reply.info("PSYNC"),
                                     Reply.info("?"),
                                     Reply.info(Encoding.numToBytes(-1))));
    }
}
