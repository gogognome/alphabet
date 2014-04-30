package nl.gogognome.alphabet;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;

public class OptimalAlphabetFinderTest {

	@Ignore
	@Test
	public void asd() {
		Alphabet optimalAlphabet = new OptimalAlphabetFinder().findOptimalAlphabet(Lists.newArrayList("BOOK", "HOOD", "FOOD", "BLOOD"));
		assertEquals(new Alphabet("ABCD"), optimalAlphabet);
	}
}
