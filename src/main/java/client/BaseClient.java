package client;

import reply.Reply;
import request.Request;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:27
 */
public abstract class BaseClient implements Client {

    protected String host;
    protected int port;
    protected final Socket socket;

    protected final OutputStream out;
    protected final BufferedWriter writer;
    protected final InputStream in;
    protected final BufferedReader reader;

    protected boolean receivePropagationReply = false;

    public BaseClient(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            this.socket = new Socket(InetAddress.getByName(host), port);
            socket.setReuseAddress(true);
            this.out = socket.getOutputStream();
            this.writer = new BufferedWriter(new OutputStreamWriter(this.out));
            this.in = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseClient(Socket socket) {
        try {
            this.socket = socket;
            socket.setReuseAddress(true);
            this.out = socket.getOutputStream();
            this.writer = new BufferedWriter(new OutputStreamWriter(this.out));
            this.in = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public InputStream getInputStream() {
        return this.in;
    }

    @Override
    public BufferedReader getReader() {
        return this.reader;
    }

    @Override
    public BufferedWriter getWriter() {
        return this.writer;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.out;
    }

    @Override
    public void sendRequest(Reply reply) {
        if (Objects.isNull(reply)) {
            return;
        }

        try {
            reply.write(out);
            out.flush();
        } catch (IOException ex) {
            System.out.println("Caught error while sending data to client");
            try {
                reply.write(System.out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        writer.close();
        reader.close();
        socket.close();
    }


    @Override
    public void propagation(Request request) {
        sendRequest(Reply.raw(request.rawCommand()));

        receivePropagationReply = false;

        System.out.println(socket.getRemoteSocketAddress());

        request.printRaw("propagation");
        // getAck();
    }

    @Override
    public void receivedPropagatedReply() {
        this.receivePropagationReply = true;
    }

    @Override
    public boolean isReceivedPropagatedReply() {
        return this.receivePropagationReply;
    }
}