package commands;

import client.Client;
import config.RedisConfig;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/27 星期二 17:29
 */
public class Config implements Command{
    @Override
    public Reply execute(Server server, Client client, Request request) {
        String one = request.one().get();
        String key = request.two().get();

        if (GET.equalsIgnoreCase(one)) {
            return Reply.multiReply(Reply.length(key),
                                    Reply.length(RedisConfig.getConfig(key, key)));
        }
        return null;
    }

    @Override
    public String name() {
        return CONFIG;
    }
}
