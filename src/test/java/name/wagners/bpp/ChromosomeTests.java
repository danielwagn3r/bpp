/*
 * Bpp - A Bin Packer in Java
 *
 * Copyright (C) 2012  Daniel Wagner <daniel@wagners.name>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 */
package name.wagners.bpp;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Daniel Wagner <daniel@wagners.name>
 *
 */
public class ChromosomeTests {

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#Chromosome()}.
	 */
	@Test
	public void testChromosome() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#add(int)}.
	 */
	@Test
	public void testAdd() {
		Chromosome chrom = new Chromosome();

		chrom.add(1);
		assertEquals(1, chrom.size());
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#clone()}.
	 */
	@Test
	public void testClone() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#get(int)}.
	 */
	@Test
	public void testGet() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#remove(int)}.
	 */
	@Test
	public void testRemove() {
		Chromosome chrom = new Chromosome();

		chrom.add(1);

		assertEquals(1, chrom.size());

		chrom.remove(1);

		assertEquals(0, chrom.size());
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#size()}.
	 */
	@Test
	public void testSize() {
		Chromosome chrom = new Chromosome();

		assertEquals(0, chrom.size());

		chrom.add(1);

		assertEquals(1, chrom.size());

		chrom.remove(1);

		assertEquals(0, chrom.size());
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#toArray()}.
	 */
	@Test
	public void testToArray() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link name.wagners.bpp.Chromosome#weight()}.
	 */
	@Test
	public void testWeight() {
		fail("Not yet implemented"); // TODO
	}

}
