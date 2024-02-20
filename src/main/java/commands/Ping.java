package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

class Ping implements Command{

    @Override
    public Reply execute(Request request) {
        return Reply.status("PONG");
    }

    @Override
    public String name() {
        return PING;
    }

    @Override
    public void postExecute(Server server, Client client, Request request) {
        server.setReplica(client);
    }

    @Override
    public void afterExecute(Server server, Client client, Request request) {

    }
}