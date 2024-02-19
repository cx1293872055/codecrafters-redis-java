package commands;

import config.RedisConfig;

import java.util.List;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 16:43
 */
public class Info implements Command {
    @Override
    public String execute(List<String> inputs) {
        return Command.decode("role:" + getRole())
                + "\n" + Command.decode("master_replid:" + RedisConfig.id)
                + "\n" + Command.decode("master_repl_offset:" + RedisConfig.offSet);
    }

    private String getRole() {
        return RedisConfig.isMaster ? "master" : "slave";
    }

    @Override
    public String name() {
        return INFO;
    }
}
