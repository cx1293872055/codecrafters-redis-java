package reply;

import java.nio.charset.StandardCharsets;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 10:48
 */
public abstract class BaseReply implements Reply{

    protected final byte[] bytes;
    protected final int capacity;

    BaseReply(String context) {
        this(context.getBytes(StandardCharsets.UTF_8));
    }

    BaseReply(byte[] bytes) {
        this.bytes = bytes;
        this.capacity = bytes.length;
    }
}
