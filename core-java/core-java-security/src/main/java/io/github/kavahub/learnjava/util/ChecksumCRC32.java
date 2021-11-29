package io.github.kavahub.learnjava.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

import lombok.experimental.UtilityClass;

/**
 * 
 * 校验码生成，计算一个字符串的 crc32 多项式
 * 
 * <p>
 * CRC（Cyclic Redundancy Check）循环冗余校验
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class ChecksumCRC32 {
    public long getChecksumCRC32(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    public long getChecksumCRC32(InputStream stream, int bufferSize) throws IOException {
        CheckedInputStream checkedInputStream = new CheckedInputStream(stream, new CRC32());
        byte[] buffer = new byte[bufferSize];
        while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {}
        return checkedInputStream.getChecksum().getValue();
    }   
}
