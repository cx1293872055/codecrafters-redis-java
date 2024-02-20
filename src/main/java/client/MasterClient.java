package client;

import codec.Encoding;
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

        Reply replConf = Reply.multiReply(Reply.value("REPLCONF"),
                                          Reply.value("listening-port"),
                                          Reply.value(Encoding.numToBytes(port)));

        sendRequest(replConf);

        Reply capa = Reply.multiReply(Reply.value("REPLECONF"),
                                       Reply.value("capa"),
                                       Reply.value("npsync2"));

        sendRequest(capa);
    }

}
