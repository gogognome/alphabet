package nl.gogognome.alphabet;

import static com.google.common.collect.Lists.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.io.Closeables;

public class WordsProvider {

	public List<String> getWords() throws IOException {
		List<String> words = newArrayList();
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/words.txt"));
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = reader.readLine();
			while (line != null) {
				words.add(line);
				line = reader.readLine();
			}
		} finally {
			Closeables.close(inputStreamReader, true);
		}
		return words;
	}

}
