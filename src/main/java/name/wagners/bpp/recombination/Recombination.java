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

package name.wagners.bpp.recombination;

import name.wagners.bpp.Dna;

/**
 * Interface for recombination algorithms.
 * 
 * @author Daniel Wagner <daniel@wagners.name>
 */
public interface Recombination {

	/**
	 * Recombines two Dna objects.
	 * 
	 * @param parentA
	 *            The first parent.
	 * @param parentB
	 *            The second parent.
	 * @return The recombined Dna.
	 */
	Dna recombine(final Dna parentA, final Dna parentB);

}
