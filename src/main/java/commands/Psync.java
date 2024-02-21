package commands;

import config.RedisConfig;
import reply.Reply;
import request.Request;

import java.io.IOException;
import java.util.Base64;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 15:57
 */
public class Psync implements Command {

    private static final String EMPTY_DB = "UkVESVMwMDEx+glyZWRpcy12ZXIFNy4yLjD6CnJlZGlzLWJpdHPAQPoFY3RpbWXCbQi8ZfoIdXNlZC1tZW3CsMQQAPoIYW9mLWJhc2XAAP/wbjv+wP9aog==";

    @Override
    public Reply execute(Request request) {
        // Reply status = Reply.status("FULLRESYNC " + RedisConfig.id + " " + RedisConfig.offSet);
        // try {
        //     status.write(System.out);
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
        // return status;
        Reply reply = Reply.multiNoStarReply(Reply.status("FULLRESYNC " + RedisConfig.id + " " + RedisConfig.offSet),
                                             Reply.sink(Base64.getDecoder().decode(EMPTY_DB)));
        try {
            reply.write(System.out);
            System.out.println("-----------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reply;
    }

    @Override
    public String name() {
        return PSYNC;
    }
}
