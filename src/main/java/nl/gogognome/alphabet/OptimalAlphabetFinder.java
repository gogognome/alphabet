package nl.gogognome.alphabet;

import java.util.List;
import java.util.Random;

public class OptimalAlphabetFinder {

	public Alphabet findOptimalAlphabet(List<String> words) {
		char[] usedCharacters = determineCharactersUsedInWords(words);
		shuffleCharacters(usedCharacters);

		Alphabet alphabet = new Alphabet("");
		alphabet = findOptimalAlphabet(words, usedCharacters, alphabet);

		Alphabet bestAlphabet = tryFittingTwoLettersAgain(words, usedCharacters, alphabet);
		return bestAlphabet;
	}

	private Alphabet findOptimalAlphabet(List<String> words, char[] usedCharacters, Alphabet alphabet) {
		while (alphabet.toString().length() < usedCharacters.length) {
			Alphabet bestAlphabet = null;
			int bestScore = -1;
			for (char letter : usedCharacters) {
				if (alphabet.containsLetter(letter))
					continue;

				for (Alphabet candidateAlphabet : alphabet.insertLetterAtAllPositions(letter)) {
					int nrWordsInOrder = candidateAlphabet.countNrWordsInOrder(words);
					if (nrWordsInOrder > bestScore) {
						bestScore = nrWordsInOrder;
						bestAlphabet = candidateAlphabet;
					}
				}
			}
			alphabet = bestAlphabet;
		}
		return alphabet;
	}

	private Alphabet tryFittingTwoLettersAgain(List<String> words, char[] usedCharacters, Alphabet alphabet) {
		Alphabet bestAlphabet = null;
		int bestScore = -1;
		for (int i = 1; i < alphabet.toString().length(); i++) {
			for (int j = 0; j < i; j++) {
				StringBuilder stringBuilder = new StringBuilder(alphabet.toString());
				stringBuilder.deleteCharAt(i);
				stringBuilder.deleteCharAt(j);
				Alphabet candidateAlphabet = findOptimalAlphabet(words, usedCharacters, new Alphabet(stringBuilder.toString()));
				int nrWordsInOrder = candidateAlphabet.countNrWordsInOrder(words);
				if (nrWordsInOrder > bestScore) {
					bestScore = nrWordsInOrder;
					bestAlphabet = candidateAlphabet;
				}
			}
		}
		return bestAlphabet;
	}

	private void shuffleCharacters(char[] usedCharacters) {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int index1 = random.nextInt(usedCharacters.length);
			int index2 = random.nextInt(usedCharacters.length);
			char temp = usedCharacters[index1];
			usedCharacters[index1] = usedCharacters[index2];
			usedCharacters[index2] = temp;
		}
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
