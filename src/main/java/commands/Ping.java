package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

class Ping implements Command{

    @Override
    public Reply execute(Server server, Client client, Request request) {
        return Reply.status("PONG");
    }

    @Override
    public String name() {
        return PING;
    }
}