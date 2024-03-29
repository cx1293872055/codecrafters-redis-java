package commands;

import client.Client;
import config.RedisConfig;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 16:43
 */
public class Info implements Command {
    @Override
    public Reply execute(Server server, Client client, Request request) {
        return Reply.length("role:" + getRole()
                                  + "\n" + "master_replid:" + RedisConfig.id
                                  + "\n" + "master_repl_offset:" + RedisConfig.offSet);
    }

    private String getRole() {
        return RedisConfig.isMaster ? "master" : "slave";
    }

    @Override
    public String name() {
        return INFO;
    }
}
