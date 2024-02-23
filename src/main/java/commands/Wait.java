package commands;

import codec.Encoding;
import reply.Reply;
import request.Request;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 13:46
 */
public class Wait implements Command{
    @Override
    public Reply execute(Request request) {
        return Reply.status(Encoding.numToBytes(0));
    }

    @Override
    public String name() {
        return wait;
    }
}
