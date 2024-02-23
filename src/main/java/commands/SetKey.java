package commands;

import cache.RedisCache;
import reply.Reply;
import request.Request;

import java.time.Duration;
import java.util.Optional;
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
    public Reply execute(Request request) {
        String key = request.one().get();
        String value = request.two().get();

        Optional<String> three = request.three();
        if (three.isPresent()) {
            String option = three.get();
            switch (option.toLowerCase()) {
                case PX -> {
                    Duration duration = Duration.ofMillis(Long.parseLong(request.four().get()));
                    EXECUTOR_SERVICE.execute(() -> removeKey(key, duration));
                }
            }
        }
        RedisCache.getCache().put(key, value);

        return Reply.status(OK);
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

    @Override
    public boolean propagation() {
        return true;
    }

}
