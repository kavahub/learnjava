package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.Tokenizer.*;

public class TokenizerTest {
	private final List<String> expectedTokensForString = Arrays.asList("Welcome", "to", "learnjava.net");
	private final List<String> expectedTokensForFile = Arrays.asList("1", "IND", "India", "2", "MY", "Malaysia", "3", "AU", "Australia");

	@Test
	public void givenString_thenGetListOfString() {
		String str = "Welcome,to,learnjava.net";
		List<String> actualTokens = getTokens(str);
		assertEquals(expectedTokensForString, actualTokens);
	}

	@Test
	public void givenFile_thenGetListOfString() {
		List<String> actualTokens = getTokensFromFile("data.csv", "|");
		assertEquals(expectedTokensForFile, actualTokens);
	}    
}
