package nl.gogognome.alphabet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class WordsProviderTest {

	@Test
	public void readWordsFromFile() throws IOException {
		List<String> words = new WordsProvider().getWords();
		assertEquals(67230, words.size());
		assertEquals("AA", words.get(0));
		assertEquals("ZZZS", words.get(67229));
	}
}
