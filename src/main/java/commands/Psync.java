package commands;

import config.RedisConfig;
import reply.Reply;
import request.Request;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 15:57
 */
public class Psync implements Command{
    @Override
    public Reply execute(Request request) {
        return Reply.value("FULLRESYNC " + RedisConfig.id + " " + RedisConfig.offSet);
    }

    @Override
    public String name() {
        return PSYNC;
    }
}
