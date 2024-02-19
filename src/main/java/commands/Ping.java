package commands;

import java.util.List;

class Ping implements Command{

    @Override
    public String execute(List<String> inputs) {
        return "+PONG\r\n";
    }
}