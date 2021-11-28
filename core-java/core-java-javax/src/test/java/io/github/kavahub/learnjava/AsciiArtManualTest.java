package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.AsciiArt.Settings;

import java.awt.*;

/**
 * 
 * {@link AsciiArt} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class AsciiArtManualTest {
    @Test
    public void givenTextWithAsciiCharacterAndSettings_shouldPrintAsciiArt() {
        AsciiArt asciiArt = new AsciiArt();
        String text = "LEARNJAVA";
        Settings settings = asciiArt.new Settings(new Font("SansSerif", Font.BOLD, 24), text.length() * 30, 30); // 30 pixel width per character
        
        asciiArt.drawString(text, "#", settings);
    }
}
