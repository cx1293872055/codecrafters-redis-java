package commands;

import cache.RedisCache;
import reply.Reply;
import request.Request;

import java.util.Objects;

/**
 * ready
 *
 * @author chenxin
 * @date 2024/02/21 17:09:01
 * @since 2024/2/19 星期一 15:55
 */
public class Get implements Command{
    @Override
    public Reply execute(Request request) {
        String key = request.one().orElse(ERROR);
        String value = RedisCache.getCache().getOrDefault(key, null);
        return Objects.isNull(value) ? Reply.errorReply : Reply.length(value);
    }

    @Override
    public String name() {
        return GET;
    }
}

