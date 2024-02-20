package request;

import java.util.List;
import java.util.Optional;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 11:02
 */
public class CommonRequest implements Request {

    private final List<String> commands;

    public CommonRequest(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public String commandName() {
        return this.commands.get(1).toLowerCase();
    }

    @Override
    public Optional<String> one() {
        if (commands.size() > 2)
            return Optional.ofNullable(commands.get(3));
        return Optional.empty();
    }

    @Override
    public Optional<String> two() {
        if (commands.size() > 4)
            return Optional.ofNullable(commands.get(5));
        return Optional.empty();
    }

    @Override
    public Optional<String> three() {
        if (commands.size() > 6)
            return Optional.ofNullable(commands.get(7));
        return Optional.empty();
    }

    @Override
    public Optional<String> four() {
        if (commands.size() > 8)
            return Optional.ofNullable(commands.get(9));
        return Optional.empty();
    }

    @Override
    public Optional<String> five() {
        if (commands.size() > 10)
            return Optional.ofNullable(commands.get(11));
        return Optional.empty();
    }

    @Override
    public Optional<String> six() {
        if (commands.size() > 12)
            return Optional.ofNullable(commands.get(13));
        return Optional.empty();
    }

    @Override
    public Optional<String> seven() {
        if (commands.size() > 14)
            return Optional.ofNullable(commands.get(15));
        return Optional.empty();
    }

    @Override
    public Optional<String> eight() {
        if (commands.size() > 16)
            return Optional.ofNullable(commands.get(17));
        return Optional.empty();
    }

    @Override
    public Optional<String> nine() {
        if (commands.size() > 18)
            return Optional.ofNullable(commands.get(19));
        return Optional.empty();
    }

    @Override
    public Optional<String> ten() {
        if (commands.size() > 20)
            return Optional.ofNullable(commands.get(21));
        return Optional.empty();
    }
}
