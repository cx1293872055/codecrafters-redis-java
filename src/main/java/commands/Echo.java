package commands;

import reply.Reply;
import request.Request;

class Echo implements Command{

    @Override
    public Reply execute(Request request) {
        return Reply.status(request.one().orElse(ERROR));
    }

    @Override
    public String name() {
        return ECHO;
    }
}
