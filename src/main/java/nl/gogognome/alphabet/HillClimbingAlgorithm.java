package nl.gogognome.alphabet;

import java.util.List;
import java.util.Random;

public class HillClimbingAlgorithm {

	private final Random random = new Random();
	private final int bestScore = -1;
	private Alphabet bestAlphabet;
	private final String allLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public Alphabet findOptimalAlphabet(List<String> words) {
		Alphabet alphabet = generateRandomAlphabet();
		alphabet = climbToTopOfHill(alphabet, words);
		return alphabet;
	}

	private Alphabet generateRandomAlphabet() {
		StringBuilder remainingLetters = new StringBuilder(allLetters);
		StringBuilder randomLetters = new StringBuilder(allLetters.length());
		while (remainingLetters.length() > 0) {
			int index = random.nextInt(remainingLetters.length());
			randomLetters.append(remainingLetters.charAt(index));
			remainingLetters.deleteCharAt(index);
		}

		return new Alphabet(randomLetters.toString());
	}

	private Alphabet climbToTopOfHill(Alphabet alphabet, List<String> words) {
		int nrIterationsWithoutImprovement = 0;
		int currentScore = alphabet.countNrWordsInOrder(words);
		Alphabet topAlphabet = alphabet;
		boolean foundImprovement;
		do {
			foundImprovement = false;
			for (int letterIndex = 0; letterIndex < allLetters.length() - 1; letterIndex++) {
				StringBuilder letters = new StringBuilder(topAlphabet.toString());
				char letter = letters.charAt(letterIndex);
				letters.insert(letterIndex + 1, letter);
				Alphabet newAlphabet = new Alphabet(letters.toString());
				int score = newAlphabet.countNrWordsInOrder(words);
				if (score > currentScore) {
					// System.out.println("Climed from " + currentScore + " to " + score);
					foundImprovement = true;
					currentScore = score;
					topAlphabet = newAlphabet;
				}
			}
		} while (foundImprovement);

		while (nrIterationsWithoutImprovement < 4 << (currentScore / 1000)) {
			foundImprovement = false;
			for (int i = 0; i < 2 * (currentScore / 1000); i++) {
				for (int letterIndex = 0; letterIndex < allLetters.length(); letterIndex++) {
					StringBuilder letters = new StringBuilder(topAlphabet.toString());
					char letter = letters.charAt(letterIndex);
					int randomIndex = random.nextInt(allLetters.length());
					letters.insert(randomIndex, letter);
					Alphabet newAlphabet = new Alphabet(letters.toString());
					int score = newAlphabet.countNrWordsInOrder(words);
					if (score > currentScore) {
						// System.out.println("Climed from " + currentScore + " to " + score);
						foundImprovement = true;
						currentScore = score;
						topAlphabet = newAlphabet;
					}
				}
			}

			nrIterationsWithoutImprovement = foundImprovement ? 0 : nrIterationsWithoutImprovement + 1;
		}

		return topAlphabet;
	}
}
