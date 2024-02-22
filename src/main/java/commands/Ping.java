package commands;

import reply.Reply;
import request.Request;

class Ping implements Command{

    @Override
    public Reply execute(Request request) {
        return Reply.status("PONG");
    }

    @Override
    public String name() {
        return PING;
    }
}