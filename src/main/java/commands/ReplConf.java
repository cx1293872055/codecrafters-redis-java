package commands;

import reply.Reply;
import request.Request;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 14:17
 */
public class ReplConf implements Command {


    @Override
    public Reply execute(Request request) {
        if ("listening-port".equalsIgnoreCase(request.one().get())) {
            Psync.replicaPort = Integer.parseInt(request.two().get());
        } else if ("ack".equalsIgnoreCase(request.one().get())) {
            if (0 == Integer.parseInt(request.two().get())) {
            }
        }

        // ready
        return Reply.status(OK);
    }

    @Override
    public String name() {
        return REPLCONF;
    }
}
