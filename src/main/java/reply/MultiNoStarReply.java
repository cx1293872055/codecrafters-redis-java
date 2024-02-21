package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 16:24
 */
public class MultiNoStarReply implements Reply {
    private final Reply[] replies;

    public MultiNoStarReply(Reply[] replies) {
        this.replies = replies;
    }

    @Override
    public void write(OutputStream os) throws IOException {
        if (this.replies == null) {
            os.write(Encoding.NEG_ONE_WITH_CRLF);
        } else {
            for (Reply reply : this.replies) {
                reply.write(os);
            }
        }
    }
}
