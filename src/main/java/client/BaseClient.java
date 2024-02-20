package client;

import reply.Reply;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:27
 */
public abstract class BaseClient implements Client {

    protected final String host;
    protected final int port;
    protected final Socket socket;
    protected final OutputStream out;
    protected final InputStream in;

    public BaseClient(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.socket = new Socket(InetAddress.getByName(host), port);
            socket.setReuseAddress(true);
            this.out = socket.getOutputStream();
            this.in = socket.getInputStream();
            System.out.println("Connected to master");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendRequest(Reply reply) {
        try {
            reply.write(out);
        } catch (IOException ex) {
            throw new RuntimeException("Caught error while sending data to client");
        }
    }
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}