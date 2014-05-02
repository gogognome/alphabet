package nl.gogognome.alphabet;

import java.io.IOException;
import java.util.List;

public class MainGenetic {

	private final List<String> words;
	private int bestScore = Integer.MIN_VALUE;
	private Alphabet bestAlphabet;

	public static void main(String[] args) throws Exception {
		new MainGenetic().startThreads();
	}

	public MainGenetic() throws IOException {
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
			GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
			geneticAlgorithm.initGenePool(100, words);
			// geneticAlgorithm.addGene("JCMPBQWHFOAURVKXINGTZLEDYS");
			// geneticAlgorithm.addGene("JCMPBQWHFOAURVXINGTKZLEDYS");
			while (true) {
				for (int i = 0; i < 10; i++) {
					geneticAlgorithm.determineNextGeneration();
					Alphabet bestAlphabetInGenePool = geneticAlgorithm.removeUnfittestGenes(words);
					updateScoresForAlphabet(bestAlphabetInGenePool);
				}
				geneticAlgorithm.printPool();
			}
		}
	}

}
