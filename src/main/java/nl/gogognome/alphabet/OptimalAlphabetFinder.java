package nl.gogognome.alphabet;

import static com.google.common.collect.Lists.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OptimalAlphabetFinder {

	public Alphabet findOptimalAlphabet(List<String> words) {
		char[] usedCharacters = determineCharactersUsedInWords(words);
		shuffleCharacters(usedCharacters);

		Alphabet alphabet = new Alphabet("");
		while (alphabet.toString().length() < usedCharacters.length) {
			List<ObjectWithCount<Alphabet>> alphabetsWithCounts = newArrayList();
			for (char letter : usedCharacters) {
				if (alphabet.containsLetter(letter))
					continue;

				for (Alphabet candidateAlphabet : alphabet.insertLetterAtAllPositions(letter)) {
					int nrWordsInOrder = candidateAlphabet.countNrWordsInOrder(words);
					alphabetsWithCounts.add(new ObjectWithCount<Alphabet>(candidateAlphabet, nrWordsInOrder));
				}
			}
			Collections.sort(alphabetsWithCounts);
			alphabet = alphabetsWithCounts.get(0).getObject();
			System.out.println("alphabet so far: " + alphabet + " (" + alphabetsWithCounts.get(0).getCount() + " words in order)");
		}
		return alphabet;
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

	private static class ObjectWithCount<T> implements Comparable<ObjectWithCount> {
		private final T object;
		private final int count;

		public ObjectWithCount(T object, int count) {
			this.object = object;
			this.count = count;
		}

		public T getObject() {
			return object;
		}

		public int getCount() {
			return count;
		}

		@Override
		public int compareTo(ObjectWithCount that) {
			return that.count - this.count; // sorts on count descendingly
		}

		@Override
		public String toString() {
			return count + "x" + object;
		}
	}
}
