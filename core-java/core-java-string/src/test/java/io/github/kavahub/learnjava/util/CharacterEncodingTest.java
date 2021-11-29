package io.github.kavahub.learnjava.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.CharacterEncoding.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 
 * {@link CharacterEncoding} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CharacterEncodingTest {
  @Test
  public void givenTextFile_whenCalledWithEncodingASCII_thenProduceIncorrectResult() throws IOException {
    assertEquals(readFile("src/test/resources/encoding.txt", "US-ASCII"),
        "The fa��ade pattern is a software-design pattern commonly used with object-oriented programming.");
  }

  @Test
  public void givenTextFile_whenCalledWithEncodingUTF8_thenProduceCorrectResult() throws IOException {
    assertEquals(readFile("src/test/resources/encoding.txt", "UTF-8"),
        "The façade pattern is a software-design pattern commonly used with object-oriented programming.");
  }

  @Test
  public void givenCharacterA_whenConvertedtoBinaryWithEncodingASCII_thenProduceResult() throws IOException {
    assertEquals(convertToBinary("A", "US-ASCII"), "1000001 ");
  }

  @Test
  public void givenCharacterA_whenConvertedtoBinaryWithEncodingUTF8_thenProduceResult() throws IOException {
    assertEquals(convertToBinary("A", "UTF-8"), "1000001 ");
  }

  @Test
  public void givenCharacterCh_whenConvertedtoBinaryWithEncodingBig5_thenProduceResult() throws IOException {
    assertEquals(convertToBinary("語", "Big5"), "10111011 1111001 ");
  }

  @Test
  public void givenCharacterCh_whenConvertedtoBinaryWithEncodingUTF8_thenProduceResult() throws IOException {
    assertEquals(convertToBinary("語", "UTF-8"), "11101000 10101010 10011110 ");
  }

  @Test
  public void givenCharacterCh_whenConvertedtoBinaryWithEncodingUTF32_thenProduceResult() throws IOException {
    assertEquals(convertToBinary("語", "UTF-32"), "0 0 10001010 10011110 ");
  }

  @Test
  public void givenUTF8String_whenDecodeByUS_ASCII_thenIgnoreMalformedInputSequence() throws IOException {
    assertEquals("The faade pattern is a software design pattern.", decodeText(
        "The façade pattern is a software design pattern.", StandardCharsets.US_ASCII, CodingErrorAction.IGNORE));
  }

  @Test
  public void givenUTF8String_whenDecodeByUS_ASCII_thenReplaceMalformedInputSequence() throws IOException {
    assertEquals("The fa��ade pattern is a software design pattern.", decodeText(
        "The façade pattern is a software design pattern.", StandardCharsets.US_ASCII, CodingErrorAction.REPLACE));
  }

  @Test
  public void givenUTF8String_whenDecodeByUS_ASCII_thenReportMalformedInputSequence() {
    assertThrows(MalformedInputException.class,
        () -> decodeText("The façade pattern is a software design pattern.", StandardCharsets.US_ASCII,
            CodingErrorAction.REPORT));
  }

  /**
   * 确定匹配的字符集
   */
  @Test
  public void givenTextFile_whenLoopOverAllCandidateEncodings_thenProduceSuitableCandidateEncodings() {
    Path path = Paths.get("src/test/resources/encoding.txt");
    List<Charset> allCandidateCharSets = Arrays.asList(StandardCharsets.US_ASCII, StandardCharsets.UTF_8,
        StandardCharsets.ISO_8859_1);

    // 匹配的集合
    List<Charset> suitableCharsets = new ArrayList<>();
    allCandidateCharSets.forEach(charset -> {
      try {
        CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
        Reader reader = new InputStreamReader(Files.newInputStream(path), charsetDecoder);
        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.readLine();
        suitableCharsets.add(charset);
      } catch (MalformedInputException ignored) {
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    assertEquals(suitableCharsets, Arrays.asList(StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1));
  }
}
