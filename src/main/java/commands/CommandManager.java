package commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 16:06
 */
public class CommandManager {

    static final Map<String, Command> commandMap = new HashMap<>();

    public static void loadCommand() {
        commandMap.putAll(Stream.of(new Ping(),
                                    new Echo(),
                                    new Get(),
                                    new SetKey(),
                                    new Info()
                                )
                                .collect(Collectors.toMap(Command::name, Function.identity())));
    }

    public static Command ofInput(String input) {
        return commandMap.getOrDefault(input, Default.INSTANCE);
    }
}
