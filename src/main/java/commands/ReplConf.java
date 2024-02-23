package commands;

import codec.Encoding;
import config.RedisConfig;
import reply.Reply;
import request.Request;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 14:17
 */
public class ReplConf implements Command {


    @Override
    public Reply execute(Request request) {
        if (listeningPort.equalsIgnoreCase(request.one().get())) {
            Psync.replicaPort = Integer.parseInt(request.two().get());
        } else if (getAck.equalsIgnoreCase(request.one().get())) {
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

}
