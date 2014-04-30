package nl.gogognome.alphabet;

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
		int previousIndex = -1;
		for (int i = 0; i < string.length(); i++) {
			int index = charToIndex[string.charAt(i) - 'A'];
			if (index >= 0) {
				if (previousIndex > index) {
					return false;
				}
				previousIndex = index;
			}
		}
		return true;
	}

}
