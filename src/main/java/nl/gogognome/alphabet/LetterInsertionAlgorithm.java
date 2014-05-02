package nl.gogognome.alphabet;

import java.util.List;

public class LetterInsertionAlgorithm {

	public Alphabet findOptimalAlphabet(List<String> words) {
		char[] usedCharacters = new UsedCharactersFind().determineCharactersUsedInWords(words);
		new Shuffle().shuffleCharacters(usedCharacters);

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

}
