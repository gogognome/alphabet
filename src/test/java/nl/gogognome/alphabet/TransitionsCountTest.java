package nl.gogognome.alphabet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class TransitionsCountTest {

	private final TransitionsCount transitionsCount = new TransitionsCount();

	@Test
	public void testIntTransitions() {
		transitionsCount.intTransitions(Lists.newArrayList("BOOK", "HOOD", "FOOD", "BLOOD"));
		assertEquals(1, transitionsCount.getNrTransitions('B', 'O'));
		assertEquals(3, transitionsCount.getNrTransitions('O', 'D'));
		assertEquals(0, transitionsCount.getNrTransitions('O', 'B'));
	}

	@Test
	public void testMergeTransitions() {
		transitionsCount.intTransitions(Lists.newArrayList("BOOK", "HOOD", "FOOD", "BLOOD"));
		transitionsCount.mergeTransitions();
		Alphabet optimalAlphabet = transitionsCount.getAlphabet();
		String letters = optimalAlphabet.toString();
		assertTrue(letters.indexOf("H") < letters.indexOf("O"));
		assertTrue(letters.indexOf("B") < letters.indexOf("O"));
		assertTrue(letters.indexOf("F") < letters.indexOf("O"));
		assertTrue(letters.indexOf("O") < letters.indexOf("K"));
		assertTrue(letters.indexOf("O") < letters.indexOf("D"));
	}

	@Test
	public void testMergeTransitionsWithAllWords() throws IOException {
		List<String> words = new WordsProvider().getWords();
		transitionsCount.intTransitions(words);
		transitionsCount.mergeTransitions();
		Alphabet optimalAlphabet = transitionsCount.getAlphabet();
		System.out.println("Score: " + optimalAlphabet.countNrWordsInOrder(words) + ", alphabet: " + optimalAlphabet);
	}

}
