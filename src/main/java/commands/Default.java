package commands;

import reply.Reply;

import java.util.List;

class Default implements Command {

    static final Command INSTANCE = new Default();

    @Override
    public Reply execute(List<String> inputs) {
        return "Currently you have entered a not supported command, please wait for few days.";
    }
}