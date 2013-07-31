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

import lombok.extern.slf4j.Slf4j;

/**
 * Class for a tournament selection algorithm.
 *
 * @author Daniel Wagner <daniel@wagners.name>
 */
@Slf4j
public class TournamentSelection implements Selection {

	/*
	 * (non-Javadoc)
	 *
	 * @see name.wagners.bpp.algorithm.Selection#select(name.wagners.bpp.Dna[])
	 */
	@Override
	public Dna[] select(final Dna[] pop) {
		Dna[] npop = new Dna[Bpp.ps];

		double fit = 0.0;
		double max = Double.MIN_VALUE;
		int best = 0, c;

		// einfache tournament selection
		for (int i = 0; i < Bpp.ps; ++i) {
			max = Double.MIN_VALUE;

			for (int j = 0; j < Bpp.sp; ++j) {
				c = Bpp.rand.nextInt(Bpp.ps);

				fit = pop[c].fitness();

				if (fit > max) {
					best = c;
					max = fit;
				}
			}

			try {
				npop[i] = (Dna) pop[best].clone();
			} catch (CloneNotSupportedException e) {
				log.error("Could not clone an object.", e);
			}
		}

		return npop;
	}

}
