package nl.gogognome.alphabet;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class GeneticAlgorithmTest {

	private final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

	@Test
	public void testInitPool() {
		geneticAlgorithm.initGenePool(4, newArrayList("ABCD"));
		geneticAlgorithm.printPool();
	}

	@Test
	public void testCreateMutation() {
		String mutation = new String(geneticAlgorithm.createMutation("ABCD".toCharArray(), "BCDA".toCharArray()));
		assertEquals(4, mutation.length());
		for (char c = 'A'; c <= 'D'; c++) {
			assertTrue(mutation.indexOf(c) >= 0);
		}
	}

	@Test
	public void testDetermineNextGeneration() {
		System.out.println("determine next generation");
		geneticAlgorithm.initGenePool(4, newArrayList("ABCD"));
		geneticAlgorithm.printPool();
		geneticAlgorithm.determineNextGeneration();
		geneticAlgorithm.printPool();
	}

	@Test
	public void testRemoveUnfittestGenes() {
		System.out.println("remove unfittest genes");
		geneticAlgorithm.initGenePool(4, newArrayList("ABCD"));
		geneticAlgorithm.determineNextGeneration();
		geneticAlgorithm.printPool();
		geneticAlgorithm.removeUnfittestGenes(newArrayList("ABC"));
		geneticAlgorithm.printPool();
	}

}
