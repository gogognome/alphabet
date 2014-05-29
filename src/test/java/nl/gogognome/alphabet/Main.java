package nl.gogognome.alphabet;

import java.io.IOException;
import java.util.List;

public class Main {

	private final List<String> words;
	private int bestScore = Integer.MIN_VALUE;
	private Alphabet bestAlphabet;

	public static void main(String[] args) throws Exception {
		new Main().startThreads();
	}

	public Main() throws IOException {
		words = new WordsProvider().getWords();
	}

	private void startThreads() throws Exception {
		int nrThreads = Runtime.getRuntime().availableProcessors() / 2;
		System.out.println("Starting " + nrThreads + " threads");
		for (int i = 0; i < nrThreads; i++) {
			new OptimalAlphabetFindThread(words).start();
			Thread.sleep(1000); // pause between starting threads for better random seeds
		}
	}

	private synchronized void updateScoresForAlphabet(Alphabet alphabet) {
		int score = alphabet.countNrWordsInOrder(words);
		System.out.println("Score: " + score + ", alphabet: " + alphabet);
		if (score > bestScore) {
			bestAlphabet = alphabet;
			bestScore = score;
		}
		System.out.println(">> Best score: " + bestScore + ", alphabet: " + bestAlphabet);
	}

	private class OptimalAlphabetFindThread extends Thread {

		private final List<String> words;

		public OptimalAlphabetFindThread(List<String> words) {
			this.words = words;
		}

		@Override
		public void run() {
			while (true) {
				Alphabet alphabet = new HillClimbingAlgorithm().findOptimalAlphabet(words);
				updateScoresForAlphabet(alphabet);
			}
		}
	}

}
