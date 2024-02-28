package commands;

import cache.RedisCache;
import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

import java.time.Duration;
import java.util.Optional;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:59
 */
public class SetKey implements Command {

    @Override
    public Reply execute(Server server, Client client, Request request) {
        String key = request.one().get();
        String value = request.two().get();

        long expireMills = -1;

        Optional<String> three = request.three();
        if (three.isPresent()) {
            String option = three.get();
            switch (option.toLowerCase()) {
                case PX -> expireMills = Long.parseLong(request.four().get());
                default -> {
                }
            }
        }
        RedisCache.setExpireKV(key, value, expireMills);

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
