package commands;

import cache.RedisCache;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:59
 */
public class SetKey implements Command {

    private static final ExecutorService EXECUTOR_SERVICE =
            Executors.newSingleThreadExecutor();

    @Override
    public String execute(List<String> inputs) {
        String key = inputs.get(3);
        String value = inputs.get(5);

        if (inputs.size() > 6) {
            String option = inputs.get(7);
            switch (option.toLowerCase()) {
                case PX -> {
                    Duration duration = Duration.ofMillis(Long.parseLong(inputs.get(9)));
                    EXECUTOR_SERVICE.execute(() -> removeKey(key, duration));
                }
            }
        }
        RedisCache.getCache().put(key, value);

        return Command.warpRes(OUTPUT_OK);
    }

    private void removeKey(String key,
                           Duration duration) {

        try {
            long millis = duration.toMillis();
            Thread.sleep(millis);
            RedisCache.getCache().remove(key);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String name() {
        return SET;
    }
}
