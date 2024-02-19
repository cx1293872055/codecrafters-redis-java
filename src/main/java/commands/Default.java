package commands;

import java.util.List;

class Default implements Command {

    static final Command INSTANCE = new Default();

    static {
        commandMap.put("ping", new Ping());
        commandMap.put("echo", new Echo());
        commandMap.put("get", new Get());
    }

    @Override
    public String execute(List<String> inputs) {
        return "Currently you have entered a not supported command, please wait for few days.";
    }
}