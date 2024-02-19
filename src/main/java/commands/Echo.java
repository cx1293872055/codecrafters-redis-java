package commands;

import java.util.List;

class Echo implements Command{

    @Override
    public String execute(List<String> inputs) {
        return Command.warpRes(inputs.get(3));
    }

    @Override
    public String name() {
        return ECHO;
    }
}
