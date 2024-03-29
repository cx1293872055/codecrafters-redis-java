package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:41
 */
public class BulkReply extends BaseReply {

    BulkReply(String context) {
        super(context);
    }

    BulkReply(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void write(OutputStream os) throws IOException {
        os.write(DOLLAR);
        os.write(Encoding.numToBytes(capacity, true));
        os.write(bytes);
        os.write(Encoding.CRLF);
    }
}
