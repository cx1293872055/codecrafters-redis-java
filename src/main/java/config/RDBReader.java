package config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author chenxin
 * @since 2024/2/27 星期二 17:49
 */
public class RDBReader {

    private final Path path;

    public RDBReader() {
        this.path = Path.of(RedisConfig.getDbFileName());
    }

    public void loadData() {
        try (InputStream in = Files.newInputStream(path)){
            int current = 0;
            while ((current = in.read()) != 0xFE) {

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
