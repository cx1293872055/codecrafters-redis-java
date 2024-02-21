package client;

import java.net.Socket;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 17:44
 */
public class SlaveClient extends BaseClient{

    public SlaveClient(String host, int port) {
        super(host, port);
    }

    public SlaveClient(Socket socket) {
        super(socket);
    }

    @Override
    public void ping() {

    }

    @Override
    public void replConf() {

    }

    @Override
    public void psync() {

    }
}
