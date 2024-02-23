package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

class Default implements Command {

    static final Command INSTANCE = new Default();

    @Override
    public Reply execute(Server server, Client client, Request request) {
        return Reply.raw("Currently you have entered a not supported command, please wait for few days.\r\n");
    }
}