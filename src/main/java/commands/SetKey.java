package commands;

import cache.RedisCache;

import java.util.List;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:59
 */
public class SetKey implements Command{
    @Override
    public String execute(List<String> inputs) {
        String key = inputs.get(3);
        String value = inputs.get(5);
        RedisCache.getCache().put(key, value);

        return Command.warpRes(OUTPUT_OK);
    }
}
