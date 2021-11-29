package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * 16进制与文本互转
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HexToAsciiTest {
    @Test
    public void whenHexToAscii() {
        String asciiString = "https://gitee.com/yangyunjiao/learn-java";
        String hexEquivalent = "68747470733a2f2f67697465652e636f6d2f79616e6779756e6a69616f2f6c6561726e2d6a617661";

        assertEquals(asciiString, hexToAscii(hexEquivalent));
    }

    @Test
    public void whenAsciiToHex() {
        String asciiString = "https://gitee.com/yangyunjiao/learn-java";
        String hexEquivalent = "68747470733a2f2f67697465652e636f6d2f79616e6779756e6a69616f2f6c6561726e2d6a617661";

        assertEquals(hexEquivalent, asciiToHex(asciiString));
    }

    //

    private String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString((int) ch));
        }

        return hex.toString();
    }

    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }   
}
