package commands;

import client.Client;
import config.RedisConfig;
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
    public void afterExecute(Server server, Client client, Request request) {
        Command.super.afterExecute(server, client, request);
        RedisConfig.increaseOffSet(request);
    }
}