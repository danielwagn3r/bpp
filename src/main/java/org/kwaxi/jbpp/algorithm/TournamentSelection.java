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

package org.kwaxi.jbpp.algorithm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kwaxi.jbpp.Dna;
import org.kwaxi.jbpp.Evolutionizer;
import org.kwaxi.jbpp.JBpp;

/**
 * Class for a tournament selection algorithm.
 *
 * @author Daniel
 */
public class TournamentSelection implements Selection {

	/**
	 * My Log class.
	 */
	private static Log log = LogFactory.getLog(TournamentSelection.class);

	/* (non-Javadoc)
	 * @see org.kwaxi.jbpp.algorithm.Selection#select(org.kwaxi.jbpp.Dna[])
	 */
	@Override
	public Dna[] select(final Dna[] pop) {
		Dna[] npop = new Dna[JBpp.ps];

		double fit = 0.0;
		double max = Double.MIN_VALUE;
		int best = 0, c;

		// einfache tournament selection
		for (int i = 0; i < JBpp.ps; ++i) {
			max = Double.MIN_VALUE;

			for (int j = 0; j < JBpp.sp; ++j) {
				c = JBpp.rand.nextInt(JBpp.ps);

				fit = pop[c].fitness();

				if (fit > max) {
					best = c;
					max = fit;
				}
			}

			try {
				npop[i] = (Dna) pop[best].clone();
			} catch (CloneNotSupportedException e) {
				log.fatal( "Could not clone an object.", e);
			}
		}

		return npop;
	}

}
