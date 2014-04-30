package nl.gogognome.alphabet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class AlphabetTest {

	@Test
	public void normalAlphabet() {
		Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		assertTrue(alphabet.isInOrder("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		assertTrue(alphabet.isInOrder("EMW"));
		assertTrue(alphabet.isInOrder(""));
		assertTrue(alphabet.isInOrder("M"));
		assertTrue(alphabet.isInOrder("AZ"));
		assertFalse(alphabet.isInOrder("BA"));
		assertFalse(alphabet.isInOrder("ABCEDGH"));
	}

	@Test
	public void partialAlphabet() {
		Alphabet alphabet = new Alphabet("MLK");
		assertTrue(alphabet.isInOrder("MILK"));
		assertTrue(alphabet.isInOrder("AMOK"));
		assertTrue(alphabet.isInOrder(""));
		assertTrue(alphabet.isInOrder("M"));
		assertFalse(alphabet.isInOrder("KL"));
	}

	@Test
	public void countNrWordsInOrderWithNormalAlphabet() throws IOException {
		List<String> words = new WordsProvider().getWords();
		Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		int nrWordsInOrder = alphabet.countNrWordsInOrder(words);
		assertEquals(860, nrWordsInOrder);
	}

	@Test
	public void countNrWordsInOrderWithAlphabetWithAAndBChanged() throws IOException {
		List<String> words = new WordsProvider().getWords();
		Alphabet alphabet = new Alphabet("BACDEFGHIJKLMNOPQRSTUVWXYZ");
		int nrWordsInOrder = alphabet.countNrWordsInOrder(words);
		assertEquals(921, nrWordsInOrder);
	}

}
