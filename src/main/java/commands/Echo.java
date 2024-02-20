package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

class Echo implements Command{

    @Override
    public Reply execute(Request request) {
        return Reply.status(request.one().orElse(ERROR));
    }

    @Override
    public String name() {
        return ECHO;
    }

    @Override
    public void postExecute(Server server, Client client, Request request) {
        server.setReplica(client);
    }

    @Override
    public void afterExecute(Server server, Client client, Request request) {

    }
}
