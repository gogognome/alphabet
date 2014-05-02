package nl.gogognome.alphabet;

import static com.google.common.collect.Lists.*;

import java.util.*;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;

public class GeneticAlgorithm {

	private final List<char[]> genePool = newArrayList();
	private final Random random = new Random();
	private int poolSize;

	public void initGenePool(int poolSize, List<String> words) {
		this.poolSize = poolSize;
		genePool.clear();
		char[] usedCharacters = new UsedCharactersFind().determineCharactersUsedInWords(words);
		for (int i = 0; i < poolSize; i++) {
			createGene(usedCharacters);
		}
	}

	private void createGene(char[] usedCharacters) {
		Shuffle shuffle = new Shuffle();
		char[] gene = usedCharacters.clone();
		shuffle.shuffleCharacters(gene);
		genePool.add(gene);
	}

	@VisibleForTesting
	void printPool() {
		List<String> genesAsString = newArrayList();
		for (char[] gene : genePool) {
			genesAsString.add(new String(gene));
		}
		System.out.println(genesAsString);
	}

	public void determineNextGeneration() {
		int nrMutations = genePool.size() / 4;
		for (int i = 0; i < nrMutations; i++) {
			int index1;
			int index2;
			do {
				index1 = random.nextInt(genePool.size());
				index2 = random.nextInt(genePool.size());
			} while (index1 == index2);
			genePool.add(createMutation(genePool.get(index1), genePool.get(index2)));
		}
		Shuffle shuffle = new Shuffle();
		for (int i = 0; i < nrMutations; i++) {
			int index = random.nextInt(genePool.size());
			char[] gene = genePool.get(index).clone();
			shuffle.shuffleCharacters(gene);
			genePool.add(gene);
		}

		while (genePool.size() < 2 * poolSize) {
			createGene(genePool.get(0));
		}
	}

	@VisibleForTesting
	char[] createMutation(char[] gene1, char[] gene2) {
		char[] mutatedGene = gene1.clone();
		int nrChars;
		switch (random.nextInt(3)) {
		case 0:
			nrChars = 2;
			break;
		case 1:
			nrChars = 3;
			break;
		case 2:
			nrChars = 4 * mutatedGene.length / 5;
			break;
		default:
			nrChars = 3;
		}
		int[] charIndices = chooseRandomCharIndices(mutatedGene, nrChars);
		Set<Character> charsToBeReplaced = Sets.newHashSet();
		for (int i = 0; i < nrChars; i++) {
			charsToBeReplaced.add(mutatedGene[charIndices[i]]);
		}

		int charIndicesIndex = 0;
		for (int index = 0; index < gene2.length; index++) {
			if (charsToBeReplaced.contains(gene2[index])) {
				mutatedGene[charIndices[charIndicesIndex]] = gene2[index];
				charIndicesIndex++;
			}
		}
		return mutatedGene;
	}

	private int[] chooseRandomCharIndices(char[] gene1, int nrChars) {
		int[] charIndices = new int[nrChars];
		for (int i = 0; i < nrChars; i++) {
			boolean newIndex;
			int index;
			do {
				newIndex = true;
				index = random.nextInt(gene1.length);
				for (int j = 0; j < i; j++) {
					newIndex &= charIndices[j] == index;
				}
			} while (!newIndex);
			charIndices[i] = index;
		}
		return charIndices;
	}

	public Alphabet removeUnfittestGenes(List<String> words) {
		Collections.sort(genePool, new GenesComparator(words));
		removeDuplicatesFromSortedGenePool();
		while (genePool.size() > poolSize) {
			genePool.remove(genePool.size() - 1);
		}
		return new Alphabet(new String(genePool.get(0)));
	}

	private void removeDuplicatesFromSortedGenePool() {
		int index = 1;
		while (index < genePool.size()) {
			if (Arrays.equals(genePool.get(index - 1), genePool.get(index))) {
				genePool.remove(index);
			} else {
				index++;
			}
		}
	}

	public void addGene(String gene) {
		genePool.add(gene.toCharArray());
	}

}

class GenesComparator implements Comparator<char[]> {

	private final List<String> words;

	public GenesComparator(List<String> words) {
		this.words = words;
	}

	@Override
	public int compare(char[] gene1, char[] gene2) {
		Alphabet alphabet1 = new Alphabet(new String(gene1));
		Alphabet alphabet2 = new Alphabet(new String(gene2));

		return alphabet2.countNrWordsInOrder(words) - alphabet1.countNrWordsInOrder(words); // sorts descendingly on number of words in order
	}

}