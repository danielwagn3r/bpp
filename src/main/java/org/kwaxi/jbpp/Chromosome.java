/*
 *  JBpp - A Bin Packer in Java
 *
 *  Copyright (C) 2007  Daniel Wagner <dwkwaxi@gmail.com>
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  $Id$
 */

package org.kwaxi.jbpp;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a bin.
 *
 * @author Daniel
 */
class Chromosome extends Object implements Cloneable {

	/**
	 * The genes of the chromosome.
	 */
	private List<Integer> gens;


	/**
	 *
	 */
	public Chromosome() {
		super();

		gens = new ArrayList<Integer>();
	}


	/**
	 * @param i
	 */
	public void add(int i) {
		gens.add(Integer.valueOf(i));
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();

		/**
		 * old version Chromosome c = new Chromosome();
		 *
		 * for (int j = 0; j < gens.size(); ++j) { c.add(get(j)); }
		 *
		 * return (c);
		 */
	}


	/**
	 * @param i
	 * @return
	 */
	public int get(int i) {
		return (gens.get(i)).intValue();
	}


	/**
	 * @param i
	 */
	public void remove(int i) {
		Integer g;

		for (int j = 0; j < gens.size(); ++j) {
			g = gens.get(j);

			if (g.intValue() == i) {
				gens.remove(g);
			}
		}
	}


	/**
	 * @return
	 */
	public int size() {
		return (gens.size());
	}


	/**
	 * @return
	 */
	public int[] toArray() {
		int[] a = new int[gens.size()];

		for (int i = 0; i < gens.size(); ++i) {
			a[i] = get(i);
		}

		return a;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String out = "";

		for (int j = 0; j < gens.size(); ++j) {
			if (j > 0) {
				out += " " + gens.get(j);
			} else {
				out += gens.get(j);
			}
		}

		return (out);
	}


	/**
	 * @return Weight of the Chromosome.
	 */
	public double weight() {
		double g = 0.0;

		for (int j = 0; j < gens.size(); ++j) {
			g += JBpp.instance.data[(gens.get(j)).intValue()];
		}

		return (g);
	}
}
