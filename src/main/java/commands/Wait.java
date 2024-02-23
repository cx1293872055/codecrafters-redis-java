package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 13:46
 */
public class Wait implements Command{
    @Override
    public Reply execute(Server server, Client client, Request request) {
        return Reply.num(server.getReplicas().size());
    }

    @Override
    public String name() {
        return wait;
    }
}
