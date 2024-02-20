package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 11:52
 */
public class MultiReply implements Reply{

    private final Reply[] replies;

    public MultiReply(Reply[] replies) {
        this.replies = replies;
    }

    @Override
    public void write(OutputStream os) throws IOException {
        os.write(STAR);
        if (replies == null) {
            os.write(Encoding.NEG_ONE_WITH_CRLF);
        } else {
            os.write(Encoding.numToBytes(replies.length, true));
            for (Reply reply : replies) {
                reply.write(os);
            }
        }
    }
}
