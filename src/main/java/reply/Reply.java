package reply;

import java.io.OutputStream;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:33
 */
public interface Reply {

    String MARKER = "$";

    void write(OutputStream outputStream);
}
