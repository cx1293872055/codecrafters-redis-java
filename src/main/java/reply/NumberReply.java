package reply;

import codec.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 13:52
 */
public class NumberReply extends BaseReply{

    NumberReply(String context) {
        super(context);
    }

    NumberReply(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(colon);
        outputStream.write(bytes);
        outputStream.write(Encoding.CRLF);
    }

}
