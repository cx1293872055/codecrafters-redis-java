package commands;

import cache.RedisCache;
import reply.Reply;

import java.util.List;
import java.util.Objects;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:55
 */
public class Get implements Command{
    @Override
    public Reply execute(List<String> inputs) {
        String key = inputs.get(3);
        String value = RedisCache.getCache().getOrDefault(key, null);
        return Objects.isNull(value) ? "$-1\r\n" : Command.warpRes(value);
    }

    @Override
    public String name() {
        return GET;
    }
}

