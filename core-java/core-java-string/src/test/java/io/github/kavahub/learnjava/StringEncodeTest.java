package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * 字符串加解码
 * 
 */
public class StringEncodeTest {
    @Test
    public void givenGermanAsciiString_whenComparing_thenCompareNotEquals() {
        String germanString = "Entwickeln Sie mit Vergnügen";
        byte[] germanBytes = germanString.getBytes();

        String asciiEncodedString = new String(germanBytes, StandardCharsets.US_ASCII);

        assertNotEquals(germanString, asciiEncodedString);
    }

    @Test
    public void givenUsAsciiString_whenComparing_thenCompareNotEquals() {
        String englishString = "Develop with pleasure";
        byte[] englishBytes = englishString.getBytes();

        String asciiEncondedEnglishString = new String(englishBytes, StandardCharsets.US_ASCII);

        assertEquals(englishString, asciiEncondedEnglishString);
    }

    /*
     * ApacheCommonsCodecEncode
     */
    @Test
    public void givenSomeUnencodedString_whenApacheCommonsCodecEncode_thenCompareEquals() {
        String rawString = "Entwickeln Sie mit Vergnügen";
        byte[] bytes = StringUtils.getBytesUtf8(rawString);

        String utf8EncodedString = StringUtils.newStringUtf8(bytes);

        assertEquals(rawString, utf8EncodedString);
    }

    /*
     * CoreJavaEncode
     */
    @Test
    public void givenSomeUnencodedString_whenCoreJavaEncode_thenCompareEquals() {
        String rawString = "Entwickeln Sie mit Vergnügen";
        byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);

        String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);

        assertEquals(rawString, utf8EncodedString);
    }

    /*
     * Java7StandardCharsetsEncode
     */
    @Test
    public void givenSomeUnencodedString_whenJava7StandardCharsetsEncode_thenCompareEquals() {
        String rawString = "Entwickeln Sie mit Vergnügen";
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString);

        String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer)
            .toString();

        assertEquals(rawString, utf8EncodedString);
    }    
}
