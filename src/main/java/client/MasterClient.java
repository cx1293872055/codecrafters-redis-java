package client;

import reply.Reply;

import java.io.IOException;

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
        try {
            ping.write(out);
        } catch (IOException ex) {
            throw new RuntimeException("Caught error while sending data to client");
        }
    }
}
