package commands;

import client.Client;
import codec.Encoding;
import config.RedisConfig;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 14:17
 */
public class ReplConf implements Command {

    @Override
    public Reply execute(Server server, Client client, Request request) {
        String subCommand = request.one().get();
        String subArg = request.two().get();

        if (listeningPort.equalsIgnoreCase(subCommand)) {
            Psync.replicaPort = Integer.parseInt(subArg);
        } else if (ack.equalsIgnoreCase(subCommand)) {
            int offSet = Integer.parseInt(subArg);
            server.setReplicaOffSet(client, offSet);
        } else if (getAck.equalsIgnoreCase(subCommand)) {
            RedisConfig.startAck();
            return Reply.multiReply(Reply.length("REPLCONF"),
                                    Reply.length("ACK"),
                                    Reply.length(Encoding.numToBytes(RedisConfig.offSet)));
        }
        // ready
        return Reply.status(OK);
    }

    @Override
    public String name() {
        return REPLCONF;
    }

    /**
     * ready
     *
     * @return boolean
     */
    @Override
    public boolean propagation() {
        return true;
    }
}
