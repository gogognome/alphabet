package nl.gogognome.alphabet;

import java.util.List;

public class OptimalAlphabetFinder {

	public Alphabet findOptimalAlphabet(List<String> words) {
		Alphabet alphabet = new Alphabet("");
		while (alphabet.toString().length() < 26) {
			Alphabet bestAlphabet = null;
			for (char letter = 'A'; letter <= 'Z'; letter++) {
				if (alphabet.containsLetter(letter))
					continue;

				for (Alphabet candidateAlphabet : alphabet.insertLetterAtAllPositions(letter)) {

				}
			}
		}
		return alphabet;
	}
}
