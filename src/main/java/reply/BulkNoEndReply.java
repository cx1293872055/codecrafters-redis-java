package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 16:09
 */
public class BulkNoEndReply extends BaseReply{


    BulkNoEndReply(String context) {
        super(context);
    }

    BulkNoEndReply(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void write(OutputStream os) throws IOException {
        os.write(DOLLAR);
        os.write(Encoding.numToBytes(capacity, true));
        os.write(bytes);
    }
}
