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

import java.util.*;

class Chromosom extends Object implements Cloneable {
	Vector<Integer> gens;

	public Chromosom() {
		gens = new Vector<Integer>();
	}

	public double weight() {
		double g = 0.0;

		for (int j = 0; j < gens.size(); ++j) {
			g += JBpp.data[(gens.get(j)).intValue()];
		}

		return (g);
	}

	public void add(int i) {
		gens.add(new Integer(i));
	}

	public void remove(int i) {
		Integer g;

		for (int j = 0; j < gens.size(); ++j) {
			g = gens.get(j);

			if (g.intValue() == i) {
				gens.remove(g);
			}
		}
	}

	public int get(int i) {
		return (gens.get(i)).intValue();
	}

	public int size() {
		return (gens.size());
	}

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

	@Override
	public Object clone() {
		Chromosom c = new Chromosom();

		for (int j = 0; j < gens.size(); ++j) {
			c.add(get(j));
		}

		return (c);
	}

	public int[] toArray() {
		int a[] = new int[gens.size()];

		for (int i = 0; i < gens.size(); ++i) {
			a[i] = get(i);
		}

		return a;
	}
}