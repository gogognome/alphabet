package nl.gogognome.alphabet;

import java.util.Random;

public class Shuffle {

	public void shuffleCharacters(char[] characters) {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int index1 = random.nextInt(characters.length);
			int index2 = random.nextInt(characters.length);
			char temp = characters[index1];
			characters[index1] = characters[index2];
			characters[index2] = temp;
		}
	}
}
