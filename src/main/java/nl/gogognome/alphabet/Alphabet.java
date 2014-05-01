package nl.gogognome.alphabet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class Alphabet {

	private static final int UNDEFINED = -1;

	private final String alphabet;
	private final int[] charToIndex = new int[26];

	public Alphabet(String alphabet) {
		this.alphabet = alphabet;
		Arrays.fill(charToIndex, UNDEFINED);

		for (int i = 0; i < alphabet.length(); i++) {
			charToIndex[alphabet.charAt(i) - 'A'] = i;
		}
	}

	public boolean isInOrder(String string) {
		int previousIndex = UNDEFINED;
		for (int i = 0; i < string.length(); i++) {
			int index = charToIndex[string.charAt(i) - 'A'];
			if (index >= 0) {
				if (previousIndex != UNDEFINED && previousIndex > index) {
					return false;
				}
				previousIndex = index;
			}
		}
		return true;
	}

	public int countNrWordsInOrder(List<String> words) {
		int nrWordsInOrder = 0;
		for (String word : words) {
			if (isInOrder(word)) {
				nrWordsInOrder++;
			}
		}
		return nrWordsInOrder;
	}

	public List<String> getWordsInOrder(List<String> words) {
		List<String> wordsInOrder = Lists.newArrayList();
		for (String word : words) {
			if (isInOrder(word)) {
				wordsInOrder.add(word);
			}
		}
		return wordsInOrder;
	}

	public boolean containsLetter(char letter) {
		return charToIndex[letter - 'A'] != UNDEFINED;
	}

	@Override
	public String toString() {
		return alphabet;
	}

	public Iterable<Alphabet> insertLetterAtAllPositions(char letterToBeInserted) {
		if (containsLetter(letterToBeInserted)) {
			return Collections.emptyList();
		}
		List<Alphabet> alphabets = Lists.newArrayList();
		StringBuilder alphabetStringBuilder = new StringBuilder(alphabet);
		for (int position = 0; position < alphabetStringBuilder.length() + 1; position++) {
			alphabetStringBuilder.insert(position, letterToBeInserted);
			alphabets.add(new Alphabet(alphabetStringBuilder.toString()));
			alphabetStringBuilder.deleteCharAt(position);
		}
		return alphabets;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Alphabet) {
			Alphabet that = (Alphabet) obj;
			return this.alphabet.equals(that.alphabet);
		}
		return false;
	}

}
