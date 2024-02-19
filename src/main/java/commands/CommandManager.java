package commands;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 16:06
 */
public class CommandManager {

    static final Map<String, Command> commandMap = new HashMap<>();

    public static void loadCommand() {
        commandMap.put("ping", new Ping());
        commandMap.put("echo", new Echo());
        commandMap.put("get", new Get());
        commandMap.put("set", new SetKey());
    }

    public static Command ofInput(String input) {
        return commandMap.getOrDefault(input, Default.INSTANCE);
    }
}
