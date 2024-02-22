package commands;

import codec.Encoding;
import config.RedisConfig;
import reply.Reply;
import request.Request;

import java.io.IOException;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 14:17
 */
public class ReplConf implements Command {


    @Override
    public Reply execute(Request request) {
        if ("listening-port".equalsIgnoreCase(request.one().get())) {
            Psync.replicaPort = Integer.parseInt(request.two().get());
        } else if ("getack".equalsIgnoreCase(request.one().get())) {
            RedisConfig.startAck();
            Reply reply = Reply.multiReply(Reply.length("REPLCONF"),
                                           Reply.length("ACK"),
                                           Reply.length(Encoding.numToBytes(RedisConfig.offSet)));
            try {
                reply.write(System.out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return reply;
        }

        // ready
        return Reply.status(OK);
    }

    @Override
    public String name() {
        return REPLCONF;
    }

    @Override
    public boolean offSet() {
        return true;
    }
}
