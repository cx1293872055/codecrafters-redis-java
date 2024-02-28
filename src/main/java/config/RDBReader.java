package config;

import cache.RedisCache;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author chenxin
 * @since 2024/2/27 星期二 17:49
 */
public class RDBReader {

    private final Path path;
    private boolean readFileSuccess = false;
    private byte[] rdbDatas;

    private String redisMagic;
    private String rdbVersion;

    // 11111010
    private static final int AUXILIARY_START = 0xFA;
    // 11111110
    private static final int SELECTOR_START = 0xFE;
    // 11111011
    private static final int SIZE_START = 0xFB;
    // 11111101
    private static final int KV_SEC_START = 0xFD;
    // 11111100
    private static final int KV_MILL_START = 0xFC;
    private static final int RDB_END = 0xFF;

    private final List<KV> rdbKvs = new ArrayList<>();

    public RDBReader() {
        this.path = Path.of(RedisConfig.getDbFileName());
        try {
            this.rdbDatas = Files.readAllBytes(this.path);
            for (byte b : this.rdbDatas) {
                // 将每个字节转为十六进制字符串
                String hexString = String.format("%02X", b & 0xFF);
                System.out.print("0x" + hexString + ",");
            }
            readFileSuccess = true;
        } catch (IOException e) {
            System.out.println("open file error");
        }
    }

    public RDBReader loadData() {

        if (!readFileSuccess) {
            System.out.println("open file failure");
            return this;
        }
        try {
            return getRdbReader(Files.newInputStream(this.path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RDBReader getRdbReader(InputStream in) {
        try {
            // Magic String and Version
            readRedisInfo(in);

            // Auxiliary field
            readAuxiliaryInfo(in);

            // dataBase selector
            // db number
            // hash table size
            // expire hash size
            readTableInfo(in);

            // Key-value data
            readTable(in);

        } catch (IOException e) {
            System.out.println("open file error");
        }
        return this;
    }

    private void readRedisInfo(InputStream in) throws IOException {
        redisMagic = new String(in.readNBytes(5));
        rdbVersion = new String(in.readNBytes(4));
    }

    private static void readAuxiliaryInfo(InputStream in) throws IOException {
        int fa = in.read();
        // skip auxiliary field
    }

    private static void readTableInfo(InputStream in) throws IOException {
        int current = 0;
        while ((current = in.read()) != SELECTOR_START) {
        }
        int dbNumber = in.read();
        int fb = in.read();

        int hashTableSize = getStringAndLength(in).getRight();
        int expireTableSixe = getStringAndLength(in).getRight();
    }

    private void readTable(InputStream in) throws IOException {
        int current;

        // ready
        while ((current = in.read()) != RDB_END) {
            rdbKvs.add(getKV(current, in));
        }
    }

    public void exchangeToLocalCache() {

        System.out.println(this.rdbKvs);

        for (KV rdbKv : this.rdbKvs) {
            if (rdbKv.timeStamp != -1 && rdbKv.timeStamp < System.currentTimeMillis()) {
                continue;
            }
            RedisCache.setExpireKV(rdbKv.key, rdbKv.value, rdbKv.timeStamp);
        }
    }


    private static KV getKV(int current, InputStream in) throws IOException {
        if (current == KV_SEC_START || current == KV_MILL_START) {

            long expireMills;

            if (current == KV_SEC_START) {
                expireMills = readLengthToInt(in, 4) * 1000L;
            } else {
                expireMills = readLengthToLong(in, 8);
            }

            int valueType = in.read();

            Pair<String, String> kvPair = readKV(in);
            return new KV(expireMills, kvPair.getLeft(), kvPair.getRight());
        } else {
            int valueType = current;

            Pair<String, String> kvPair = readKV(in);
            return new KV(-1, kvPair.getLeft(), kvPair.getRight());
        }
    }

    private static Pair<Function<Integer, String>, Integer> getStringAndLength(InputStream in) {
        try {
            int prefix = in.read();

            final int sixBit = prefix & 0B00111111;

            return switch (prefix >> 6) {
                case 0B00 -> Pair.of(length -> readLengthToString(in, length), sixBit);

                case 0B01 -> Pair.of(length -> readLengthToString(in, length),
                                     ((sixBit) << 8) | in.read());

                case 0B10 -> Pair.of(length -> readLengthToString(in, length),
                                     in.read() << 24 & in.read() << 16 & in.read() << 8 & in.read());

                case 0B11 -> switch (sixBit) {
                    case 0, 1, 2 -> Pair.of(length -> readLengthToIntString(in, length / 8),
                                            0B10 << (2 + sixBit));
                    // case 3 -> ;
                    default -> Pair.of(String::valueOf, -1);
                };

                default -> Pair.of(String::valueOf, -1);
            };

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Pair<String, String> readKV(InputStream in) {
        Pair<Function<Integer, String>, Integer> keyStringAndLength = getStringAndLength(in);
        String key = keyStringAndLength.getLeft().apply(keyStringAndLength.getRight());

        Pair<Function<Integer, String>, Integer> valueStringAndLength = getStringAndLength(in);
        String value = valueStringAndLength.getLeft().apply(valueStringAndLength.getRight());
        return Pair.of(key, value);
    }

    record KV(
            long timeStamp,
            String key,
            String value
    ) {}

    private static String readLengthToString(InputStream in, int byteSize) {
        try {
            return new String(in.readNBytes(byteSize));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readLengthToIntString(InputStream in, int byteSize) {
        byte[] bytes;
        try {
            bytes = in.readNBytes(byteSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int result = 0;
        for (byte aByte : bytes) {
            result = (result << 8) | (aByte & 0xFF);
        }
        return String.valueOf(result);
    }

    private static int readLengthToInt(InputStream in, int byteSize) {
        try {
            int result = 0;
            for (int i = byteSize - 1; i >= 0; i--) {
                result |= in.read() << (8 * i);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long readLengthToLong(InputStream in, int byteSize) {
        try {
            long result = 0;
            for (int i = byteSize - 1; i >= 0; i--) {
                result |= (long) in.read() << (8 * i);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
