package nl.gogognome.alphabet;

import static com.google.common.collect.Lists.*;
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
	public void getWordsInOrder() throws IOException {
		assertEquals("[ABC, BCD]", new Alphabet("ABCD").getWordsInOrder(newArrayList("ABC", "BDA", "BCD")).toString());
	}

	@Test
	public void countNrWordsInOrderWithAlphabetWithAAndBChanged() throws IOException {
		List<String> words = new WordsProvider().getWords();
		Alphabet alphabet = new Alphabet("BACDEFGHIJKLMNOPQRSTUVWXYZ");
		int nrWordsInOrder = alphabet.countNrWordsInOrder(words);
		assertEquals(921, nrWordsInOrder);
	}

	@Test
	public void testToString() {
		assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toString());
		assertEquals("CDE", new Alphabet("CDE").toString());
		assertEquals("", new Alphabet("").toString());
	}

	@Test
	public void testContainsLetter() {
		assertTrue(new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ").containsLetter('X'));
		assertTrue(new Alphabet("ABCD").containsLetter('D'));
		assertFalse(new Alphabet("ABCD").containsLetter('E'));
	}

	@Test
	public void testInsertLetterAtAllPositions() {
		assertEquals("[]", new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ").insertLetterAtAllPositions('X').toString());
		assertEquals("[XABC, AXBC, ABXC, ABCX]", new Alphabet("ABC").insertLetterAtAllPositions('X').toString());
	}

	@Test
	public void testEquals() {
		assertEquals(new Alphabet("ABC"), new Alphabet("ABC"));
		assertFalse(new Alphabet("ABC").equals(new Alphabet("CBA")));
	}
}
