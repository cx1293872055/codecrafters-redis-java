package commands;

import reply.Reply;

import java.util.List;

class Echo implements Command{

    @Override
    public Reply execute(List<String> inputs) {
        return Command.warpRes(inputs.get(3));
    }

    @Override
    public String name() {
        return ECHO;
    }
}
