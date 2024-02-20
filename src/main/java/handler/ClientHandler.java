package handler;

import commands.Command;
import commands.CommandManager;
import reply.Reply;
import request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String clientInput;
            while ((clientInput = in.readLine()) != null) {
                if (clientInput.startsWith("*")) {
                    int numberOfItems = Integer.parseInt(clientInput.substring(1));
                    List<String> commands = new ArrayList<>(numberOfItems * 2);
                    for (int i = 0; i < numberOfItems * 2; i++) {
                        commands.add(in.readLine());
                    }
                    Request request = Request.commonRequest(commands);
                    Command command = CommandManager.ofInput(request.commandName());
                    sendResponse(command.execute(request));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendResponse(Reply reply) {
        try {
            reply.write(clientSocket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Caught error while sending data to client");
        }
    }
}
