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


/**
 * Interface for mutation algorithms.
 *
 * @author Daniel
 */
public interface Mutation {

	/**
	 * Mutates the given Dna object.
	 *
	 * @param dna
	 *            The object to mutate.
	 * @return The mutated object.
	 */
	Dna mutate(final Dna dna);

}
