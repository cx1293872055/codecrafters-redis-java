package commands;

import reply.Reply;

import java.util.List;

class Ping implements Command{

    @Override
    public Reply execute(List<String> inputs) {
        return "+PONG\r\n";
    }

    @Override
    public String name() {
        return PING;
    }
}