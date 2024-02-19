package commands;

import java.util.List;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 16:43
 */
public class Info implements Command{
    @Override
    public String execute(List<String> inputs) {
        return Command.warpRes("# Replication" +
                                       "\nrole:master");
    }

    @Override
    public String name() {
        return INFO;
    }
}
