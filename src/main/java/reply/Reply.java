package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:33
 */
public interface Reply {
    /**
     * 标记
     */
    char DOLLAR = '$';
    char ADD = '+';
    char STAR = '*';

    Reply errorReply = os -> {
        os.write(DOLLAR);
        os.write(Encoding.numToBytes(-1, true));
    };

    void write(OutputStream outputStream) throws IOException;

    // static Reply orError(String context, Function<String, Reply> builder) {
    //     if (Objects.isNull(context)) {
    //         return errorReply;
    //     }
    //     return builder.apply(context);
    // }

    static Reply raw(String context) {
        return new RawReply(context);
    }

    static Reply raw(byte[] bytes) {
        return new RawReply(bytes);
    }

    static Reply length(String context) {
        return new BulkReply(context);
    }

    static Reply length(byte[] bytes) {
        return new BulkReply(bytes);
    }

    static Reply status(String context) {
        return new StatusReply(context);
    }

    static Reply status(byte[] bytes) {
        return new StatusReply(bytes);
    }

    static Reply multiReply(Reply... replies) {
        return new MultiReply(replies);
    }

    static Reply multiNoStarReply(Reply... replies) {
        return new MultiNoStarReply(replies);
    }

    static Reply sink(String context) {
        return new BulkNoEndReply(context);
    }

    static Reply sink(byte[] bytes) {
        return new BulkNoEndReply(bytes);
    }
}
