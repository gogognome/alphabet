package nl.gogognome.alphabet;

import java.util.List;

import com.google.common.annotations.VisibleForTesting;

public class TransitionsCount {

	private final int[][] nrTransitionsFromTo = new int[26][26];

	private final String[] symbols = new String[26];

	public void intTransitions(List<String> words) {
		for (int i = 0; i < symbols.length; i++) {
			symbols[i] = Character.toString((char) ('A' + i));
		}

		for (String word : words) {
			countTransitions(word);
		}
	}

	private void countTransitions(String word) {
		for (int index = 1; index < word.length(); index++) {
			int from = word.charAt(index - 1) - 'A';
			int to = word.charAt(index) - 'A';
			nrTransitionsFromTo[from][to]++;
		}
	}

	public void mergeTransitions() {
		while (true) {
			int bestFrom = -1;
			int bestTo = -1;
			int maxCount = -1;
			for (int from = 0; from < 26; from++) {
				if (symbols[from] != null) {
					for (int to = 0; to < 26; to++) {
						if (symbols[to] != null && from != to && nrTransitionsFromTo[from][to] > maxCount) {
							maxCount = nrTransitionsFromTo[from][to];
							bestFrom = from;
							bestTo = to;
						}
					}
				}
			}

			if (maxCount < 0) {
				return;
			}

			merge(bestFrom, bestTo);
		}
	}

	private void merge(int from, int to) {
		symbols[from] += symbols[to];
		symbols[to] = null;
		for (int i = 0; i < 26; i++) {
			nrTransitionsFromTo[from][i] += nrTransitionsFromTo[to][i];
			nrTransitionsFromTo[i][from] += nrTransitionsFromTo[i][to];
		}
	}

	@VisibleForTesting
	int getNrTransitions(char from, char to) {
		return nrTransitionsFromTo[from - 'A'][to - 'A'];
	}

	public Alphabet getAlphabet() {
		StringBuilder sb = new StringBuilder();
		for (String symbol : symbols) {
			if (symbol != null) {
				sb.append(symbol);
			}
		}
		return new Alphabet(sb.toString());
	}
}
