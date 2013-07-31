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

package name.wagners.bpp.mutation;

import name.wagners.bpp.Bpp;
import name.wagners.bpp.Chromosome;
import name.wagners.bpp.Dna;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Daniel Wagner <daniel@wagners.name>
 */
@Slf4j
public class SimpleMutation implements Mutation {

	/*
	 * (non-Javadoc)
	 *
	 * @see name.wagners.bpp.algorithm.Mutation#mutate(name.wagners.bpp.Dna)
	 */
	@Override
	public Dna mutate(Dna a) {

		/**
		 * Zuf�llig zwei Chromosome ausw�hlen, von jedem Gen zuf�llig ein
		 * Chromosm ausw�hlen falls die ausgew�hlten Gene im anderen Chromosome
		 * noch Platz haben werden diese vertauscht
		 */
		Chromosome ca = a.dna.get(Bpp.rand.nextInt(a.dna.size()));
		Chromosome cb = a.dna.get(Bpp.rand.nextInt(a.dna.size()));

		if (ca != cb) {
			// Nur bei verschiedenen Chromosomen mutieren

			// Jetzt zwei zuf�llige Gene ausw�hlen
			int ga = ca.get(Bpp.rand.nextInt(ca.size()));
			int gb = cb.get(Bpp.rand.nextInt(cb.size()));

			if ((Bpp.instance.data[ga] + cb.weight() - Bpp.instance.data[gb] <= Bpp.wmax)
					&& (Bpp.instance.data[gb] + ca.weight()
							- Bpp.instance.data[ga] <= Bpp.wmax)) {
				ca.remove(ga);
				ca.add(gb);

				cb.remove(gb);
				cb.add(ga);
			}
		}

		return a;
	}
}
