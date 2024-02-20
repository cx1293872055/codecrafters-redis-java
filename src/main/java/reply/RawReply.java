package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/20 星期二 10:54
 */
public class RawReply extends BaseReply {

    RawReply(String context) {
        super(context);
    }

    RawReply(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void write(OutputStream os) throws IOException {
        os.write(bytes);
        os.write(Encoding.CRLF);
    }
}
