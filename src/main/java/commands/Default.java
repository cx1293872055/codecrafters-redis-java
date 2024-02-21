package commands;

import reply.Reply;
import request.Request;

class Default implements Command {

    static final Command INSTANCE = new Default();

    @Override
    public Reply execute(Request request) {
        return Reply.raw("Currently you have entered a not supported command, please wait for few days.\r\n");
    }
}