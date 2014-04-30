package nl.gogognome.alphabet;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class Alphabet {

	private static final int UNDEFINED = -1;

	private final int[] charToIndex = new int[26];

	public Alphabet(String alphabet) {
		for (int i = 0; i < charToIndex.length; i++) {
			charToIndex[i] = UNDEFINED;
		}

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

	public boolean containsLetter(char letter) {
		return charToIndex[letter - 'A'] != UNDEFINED;
	}

	@Override
	public String toString() {
		return getStringBuilderWithAlphabet().toString();
	}

	private StringBuilder getStringBuilderWithAlphabet() {
		StringBuilder sb = new StringBuilder();
		for (int indexToFind = 0; indexToFind < charToIndex.length; indexToFind++) {
			for (int charIndex = 0; charIndex < charToIndex.length; charIndex++) {
				if (charToIndex[charIndex] == indexToFind) {
					sb.append((char) ('A' + charIndex));
				}
			}
		}
		return sb;
	}

	public Iterable<Alphabet> insertLetterAtAllPositions(char letterToBeInserted) {
		if (containsLetter(letterToBeInserted)) {
			return Collections.emptyList();
		}
		List<Alphabet> alphabets = Lists.newArrayList();
		StringBuilder alphabet = getStringBuilderWithAlphabet();
		for (int position = 0; position < alphabet.length() + 1; position++) {
			alphabet.insert(position, letterToBeInserted);
			alphabets.add(new Alphabet(alphabet.toString()));
			alphabet.deleteCharAt(position);
		}
		return alphabets;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Alphabet) {
			Alphabet that = (Alphabet) obj;
			return this.getStringBuilderWithAlphabet().toString().equals(that.getStringBuilderWithAlphabet().toString());
		}
		return false;
	}

}
