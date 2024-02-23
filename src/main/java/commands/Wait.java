package commands;

import reply.Reply;
import request.Request;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 13:46
 */
public class Wait implements Command{
    @Override
    public Reply execute(Request request) {
        String count = request.one().get();
        if ("0".equals(count)) {
            return Reply.num(0);
        } else {
            return Reply.num(7);

        }
    }

    @Override
    public String name() {
        return wait;
    }
}
