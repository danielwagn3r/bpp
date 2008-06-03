/*
 * JBpp - A Bin Packer in Java
 *
 * Copyright (C) 2008  Daniel Wagner <dwkwaxi@gmail.com>
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
