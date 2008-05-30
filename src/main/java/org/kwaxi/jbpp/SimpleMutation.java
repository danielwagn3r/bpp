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


/**
 * @author Daniel
 *
 */
public class SimpleMutation implements Mutation {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.kwaxi.jbpp.algorithm.Mutation#mutate(org.kwaxi.jbpp.Dna)
	 */
	@Override
	public Dna mutate(Dna a) {

		/**
		 * Zufällig zwei Chromosome auswählen, von jedem Gen zufällig ein
		 * Chromosm auswählen falls die ausgewählten Gene im anderen Chromosome
		 * noch Platz haben werden diese vertauscht
		 */
		Chromosome ca = a.dna.get(JBpp.rand.nextInt(a.dna.size()));
		Chromosome cb = a.dna.get(JBpp.rand.nextInt(a.dna.size()));

		if (ca != cb) {
			// Nur bei verschiedenen Chromosomen mutieren

			// Jetzt zwei zufï¿½llige Gene auswï¿½hlen
			int ga = ca.get(JBpp.rand.nextInt(ca.size()));
			int gb = cb.get(JBpp.rand.nextInt(cb.size()));

			if ((JBpp.instance.data[ga] + cb.weight() - JBpp.instance.data[gb] <= JBpp.wmax)
					&& (JBpp.instance.data[gb] + ca.weight()
							- JBpp.instance.data[ga] <= JBpp.wmax)) {
				ca.remove(ga);
				ca.add(gb);

				cb.remove(gb);
				cb.add(ga);
			}
		}

		return a;
	}
}
