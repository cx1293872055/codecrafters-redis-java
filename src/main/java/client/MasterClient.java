package client;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:24
 */

public class MasterClient extends BaseClient {

    public MasterClient(String host, int port) {
        super(host, port);
        System.out.println("Connected to master: " + this.getSocket().getRemoteSocketAddress());
    }

    @Override
    public void handshake() {
        ping();
        replConf();
        psync();
    }
}
