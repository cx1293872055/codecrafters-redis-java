package commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:27
 */
public interface Command {

    String OUTPUT_OK = "OK";


    default String execute(List<String> inputs) {
        return null;
    }

    Map<String, Command> commandMap = new HashMap<>();

    static Command ofInput(String input) {
        return commandMap.getOrDefault(input, Default.INSTANCE);
    }

    static String warpRes(String result) {
        return "+" + result + "\r\n";
    }
}
