package commands;

import reply.Reply;
import request.Request;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 14:17
 */
public class ReplConf implements Command{

    @Override
    public Reply execute(Request request) {
        // ready
        return Reply.status(OK);
    }

    @Override
    public String name() {
        return REPLCONF;
    }

    @Override
    public boolean propagation() {
        return false;
    }
}
