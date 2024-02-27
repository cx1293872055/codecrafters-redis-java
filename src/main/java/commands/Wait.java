package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;
import utils.Sleeper;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 13:46
 */
public class Wait implements Command {
    @Override
    public Reply execute(Server server, Client client, Request request) {
        String replicaReceiveCount = request.one().get();
        int replicaReceiveCountNum = Integer.parseInt(replicaReceiveCount);

        if (replicaReceiveCountNum == 0) {
            return Reply.num(0L);
        }

        String waitMills = request.two().get();
        int waitMillsInt = Integer.parseInt(waitMills);

        long currentMills = System.currentTimeMillis();
        long count= 0;
        while (currentMills + waitMillsInt > System.currentTimeMillis()) {
            Sleeper.sleep(100L);
            count = server.getReplicas().stream()
                               .filter(Client::isReceivedPropagatedReply)
                               .count();
            if (count >= replicaReceiveCountNum) {
                return Reply.num(count);
            }
        }
        return Reply.num(count);
    }

    @Override
    public String name() {
        return wait;
    }

    @Override
    public void clientPostExecute(Server server, Client client, Request request) {
        Command.super.clientPostExecute(server, client, request);

        server.getReplicas().forEach(c ->{
            if (!c.isReceivedPropagatedReply()) {
                c.getAck();
            }
        });
    }
}
