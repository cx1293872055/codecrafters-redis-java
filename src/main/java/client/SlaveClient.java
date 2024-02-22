package client;

import java.net.Socket;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 17:44
 */
public class SlaveClient extends BaseClient{

    public SlaveClient(String host, int port) {
        super(host, port);
        System.out.println("Connected to slave: " + this.getSocket().getRemoteSocketAddress());
    }

    public SlaveClient(Socket socket) {
        super(socket);
    }
}
