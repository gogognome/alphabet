package nl.gogognome.alphabet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class OptimalAlphabetFinderTest {

	@Test
	public void alphabetsExistSoThatAllWordsAreInOrder_findOptimalAlphabet() {
		Alphabet optimalAlphabet = new OptimalAlphabetFinder().findOptimalAlphabet(Lists.newArrayList("BOOK", "HOOD", "FOOD", "BLOOD"));
		String letters = optimalAlphabet.toString();
		assertTrue(letters.indexOf("H") < letters.indexOf("O"));
		assertTrue(letters.indexOf("B") < letters.indexOf("O"));
		assertTrue(letters.indexOf("F") < letters.indexOf("O"));
		assertTrue(letters.indexOf("O") < letters.indexOf("K"));
		assertTrue(letters.indexOf("O") < letters.indexOf("D"));
	}

	@Test
	public void alphabetDoesNotExistSoThatAllWordsAreInOrder_findOptimalAlphabet() {
		Alphabet optimalAlphabet = new OptimalAlphabetFinder().findOptimalAlphabet(Lists.newArrayList("ABCD", "ADC", "CDA", "BD", "CA", "BCDA"));
		assertEquals(new Alphabet("BCDA"), optimalAlphabet);
	}

	@Test
	public void allWordsFromFile_findOptimalAlphabet() throws IOException {
		int bestScore = -1;
		Alphabet bestAlphabet = null;
		List<String> words = new WordsProvider().getWords();
		while (true) {
			Alphabet alphabet = new OptimalAlphabetFinder().findOptimalAlphabet(words);
			int score = alphabet.countNrWordsInOrder(words);
			System.out.println("Score: " + score + ", alphabeet: " + alphabet);
			if (score > bestScore) {
				bestAlphabet = alphabet;
				bestScore = score;
			}
			System.out.println(">> Best score: " + bestScore + ", alphabet: " + bestAlphabet);
		}
	}
}
