/**
 *
 */
package org.kwaxi.jbpp;

import junit.framework.*;

/**
 * @author Daniel
 *
 */
public class SimpleTestCase extends TestCase {

	public void testChromosomAddAndGetSize() {
		Chromosome chrom = new Chromosome();

		chrom.add(1);
		assertEquals(1, chrom.size());
	}

	public void testChromosomAddAndRemoveAndGetSize() {
		Chromosome chrom = new Chromosome();

		chrom.add(1);

		assertEquals(1, chrom.size());

		chrom.remove(1);

		assertEquals(0, chrom.size());
	}
}
