package commands;

import cache.RedisCache;
import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/28 星期三 15:00
 */
public class Keys implements Command{
    @Override
    public Reply execute(Server server, Client client, Request request) {
        return Reply.multiReply(RedisCache.getCache().keySet().stream().map(Reply::length).toList().toArray(new Reply[]{}));
    }

    @Override
    public String name() {
        return KEYS;
    }
}
