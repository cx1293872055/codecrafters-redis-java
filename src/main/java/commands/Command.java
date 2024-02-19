package commands;

import java.util.List;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:27
 */
public interface Command {

    String PING = "ping";
    String ECHO = "echo";
    String GET = "get";
    String SET = "set";
    String PX = "px";

    String INFO = "info";
    String OUTPUT_OK = "OK";



    default String execute(List<String> inputs) {
        return null;
    }

    default String name() {
        return null;
    }

    static String warpRes(String result) {
        return "+" + result + "\r\n";
    }

    static String decode(String response) {
        int length = response.length();
        return "$" + length + "\r\n" + response + "\r\n";
    }
}
