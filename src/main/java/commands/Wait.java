package commands;

import client.Client;
import reply.Reply;
import request.Request;
import server.Server;

import java.util.HashSet;
import java.util.Set;

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
            return Reply.num(0);
        }

        String waitMills = request.two().get();
        int waitMillsInt = Integer.parseInt(waitMills);

        Set<Client> counted = new HashSet<>();
        long currentMills = System.currentTimeMillis();
        while (currentMills + waitMillsInt > System.currentTimeMillis()) {
            for (Client replica : server.getReplicas()) {
                if (replica.isReceivedPropagatedReply()) {
                    counted.add(replica);
                }
            }
            if (counted.size() >= replicaReceiveCountNum) {
                return Reply.num(counted.size());
            }
        }
        return Reply.num(counted.size());
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
