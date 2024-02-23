package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

class Echo implements Command{

    @Override
    public Reply execute(Server server, Client client, Request request) {
        return Reply.status(request.one().orElse(ERROR));
    }

    @Override
    public String name() {
        return ECHO;
    }

}
