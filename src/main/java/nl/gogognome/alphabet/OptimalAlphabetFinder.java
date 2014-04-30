package nl.gogognome.alphabet;

import java.util.List;

public class OptimalAlphabetFinder {

	public Alphabet findOptimalAlphabet(List<String> words) {
		char[] usedCharacters = determineCharactersUsedInWords(words);

		Alphabet alphabet = new Alphabet("");
		while (alphabet.toString().length() < usedCharacters.length) {
			Alphabet bestAlphabet = null;
			int bestNrWordsInOrder = -1;
			for (char letter : usedCharacters) {
				if (alphabet.containsLetter(letter))
					continue;

				for (Alphabet candidateAlphabet : alphabet.insertLetterAtAllPositions(letter)) {
					int nrWordsInOrder = candidateAlphabet.countNrWordsInOrder(words);
					if (nrWordsInOrder > bestNrWordsInOrder) {
						bestAlphabet = candidateAlphabet;
						bestNrWordsInOrder = nrWordsInOrder;
					}
				}
			}
			alphabet = bestAlphabet;
			System.out.println("alphabet so far: " + alphabet + " (" + bestNrWordsInOrder + " words in order)");
		}
		return alphabet;
	}

	private char[] determineCharactersUsedInWords(List<String> words) {
		boolean[] lettersPresent = new boolean[26];
		char[] usedCharacters = new char[26];
		int nrLettersPresent = 0;
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				int index = word.charAt(i) - 'A';
				if (!lettersPresent[index]) {
					lettersPresent[index] = true;
					usedCharacters[nrLettersPresent] = word.charAt(i);
					nrLettersPresent++;
				}
			}
		}

		char[] trimmedUsedCharacters = new char[nrLettersPresent];
		System.arraycopy(usedCharacters, 0, trimmedUsedCharacters, 0, nrLettersPresent);
		return trimmedUsedCharacters;
	}
}
