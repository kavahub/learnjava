package io.github.kavahub.learnjava.common.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.google.common.io.CountingOutputStream;

import org.junit.jupiter.api.Test;

public class CountingOutputStreamTest {
    public static final int MAX = 5;

    @Test
    public void givenData_whenCountReachesLimit_thenThrowException() throws Exception {
        assertThrows(RuntimeException.class, () -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 返回写入的字节,因此取决于编码
            try (CountingOutputStream cos = new CountingOutputStream(out)) {

                byte[] data = new byte[1024];
                ByteArrayInputStream in = new ByteArrayInputStream(data);

                int b;
                while ((b = in.read()) != -1) {
                    cos.write(b);
                    if (cos.getCount() >= MAX) {
                        throw new RuntimeException("Write limit reached");
                    }
                }
            }
        });

    }
}
