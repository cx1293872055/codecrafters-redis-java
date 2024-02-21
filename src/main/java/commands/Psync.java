package commands;

import client.Client;
import client.SlaveClient;
import config.RedisConfig;
import reply.Reply;
import request.Request;
import server.Server;

import java.util.Base64;
import java.util.Objects;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 15:57
 */
public class Psync implements Command {

    public static Integer replicaPort = 0;
    private static final String EMPTY_DB = "UkVESVMwMDEx+glyZWRpcy12ZXIFNy4yLjD6CnJlZGlzLWJpdHPAQPoFY3RpbWXCbQi8ZfoIdXNlZC1tZW3CsMQQAPoIYW9mLWJhc2XAAP/wbjv+wP9aog==";

    @Override
    public Reply execute(Request request) {
        return Reply.multiNoStarReply(Reply.status("FULLRESYNC " + RedisConfig.id + " " + RedisConfig.offSet),
                                      Reply.sink(Base64.getDecoder().decode(EMPTY_DB)));
    }

    @Override
    public String name() {
        return PSYNC;
    }

    @Override
    public void postExecute(Server server, Client client, Request request) {
        if (replicaPort != 0) {
            Client replica = null;
            try {
                replica = new SlaveClient("localhost", replicaPort);
            } catch (Exception exception) {
                System.out.println("replica not available");
            }

            if (Objects.nonNull(replica)) {
                server.setReplica(replica);
            }
        }
    }
}
