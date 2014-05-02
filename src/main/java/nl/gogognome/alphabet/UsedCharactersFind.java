package nl.gogognome.alphabet;

import java.util.List;

public class UsedCharactersFind {

	public char[] determineCharactersUsedInWords(List<String> words) {
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
