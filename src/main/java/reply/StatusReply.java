package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 10:47
 */
public class StatusReply extends BaseReply {

    StatusReply(String context) {
        super(context);
    }

    StatusReply(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void write(OutputStream os) throws IOException {
        os.write(ADD);
        os.write(bytes);
        os.write(Encoding.CRLF);
    }
}
