import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:27
 */
public interface Command {

    default String execute(List<String> inputs) {
        return null;
    }

    Map<String, Command> commandMap = new HashMap<>();

    static Command ofInput(String input) {
        return commandMap.getOrDefault(input, Default.INSTANCE);
    }

    class Default implements Command{

        static final Command INSTANCE = new Default();

        static {
            commandMap.put("ping", new Ping());
            commandMap.put("echo", new Echo());
        }
        @Override
        public String execute(List<String> inputs) {
            return "Currently you have entered a not supported command, please wait for few days.";
        }
    }
    class Ping implements Command {

        @Override
        public String execute(List<String> inputs) {
            return "+PONG\r\n";
        }
    }

    class Echo implements Command {

        @Override
        public String execute(List<String> inputs) {
            return "+" + inputs.get(3) + "\r\n";
        }
    }
}
