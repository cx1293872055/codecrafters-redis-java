package commands;

import java.util.List;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:27
 */
public interface Command {

    String OUTPUT_OK = "OK";


    default String execute(List<String> inputs) {
        return null;
    }

    static String warpRes(String result) {
        return "+" + result + "\r\n";
    }
}
