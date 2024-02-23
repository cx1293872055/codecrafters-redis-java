package utils;

/**
 * @author chenxin
 * @since 2024/2/23 星期五 17:08
 */
public class Sleeper {
    public static void sleep(long mills) {
        try {
            // reason: ensure master propagation and client request is ordered
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
