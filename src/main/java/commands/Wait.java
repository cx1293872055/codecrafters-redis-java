package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 13:46
 */
public class Wait implements Command {
    @Override
    public Reply execute(Server server, Client client, Request request) {
        String propagatedCount = request.one().get();
        long currentMills = System.currentTimeMillis();
        long lastPropagationMills = server.lastPropagationTime();

        if (server.propagatedCount() >= Integer.parseInt(propagatedCount)
                || (lastPropagationMills + 500L) < currentMills) {
            return Reply.num(server.getReplicas().size());
        } else {
            try {
                Thread.sleep(500L - (currentMills - lastPropagationMills));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Reply.num(server.getReplicas().size());
        }
    }

    @Override
    public String name() {
        return wait;
    }
}
