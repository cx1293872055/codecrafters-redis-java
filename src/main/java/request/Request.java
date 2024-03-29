package request;

import java.util.List;
import java.util.Optional;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 11:02
 */
public interface Request {
    void printRaw(String title);

    String rawCommand();

    String commandName();

    Optional<String> one();

    Optional<String> two();

    Optional<String> three();

    Optional<String> four();

    Optional<String> five();

    Optional<String> six();

    Optional<String> seven();

    Optional<String> eight();

    Optional<String> nine();

    Optional<String> ten();

    static Request commonRequest(String rawCommand, List<String> commands) {
        return new CommonRequest(rawCommand, commands);
    }
}
