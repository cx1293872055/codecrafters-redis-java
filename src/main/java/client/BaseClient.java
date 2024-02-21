package client;

import reply.Reply;
import request.Request;

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

    protected String host;
    protected int port;
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

    public BaseClient(Socket socket) {
        try {
            this.socket = socket;
            socket.setReuseAddress(true);
            this.out = socket.getOutputStream();
            this.in = socket.getInputStream();
            System.out.println("Connected to master");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendRequest(Reply reply) {
        try {
            reply.write(out);
            reply.write(System.out);
        } catch (IOException ex) {
            System.out.println("Caught error while sending data to client");
        }
    }

    protected void readReply() throws IOException {
        byte[] b = new byte[64];
        while (in.available() > 0) {
            in.read(b);
            System.out.println(new String(b));
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public void propagation(Request request) {
        sendRequest(Reply.raw(request.rawCommand()));
    }
}