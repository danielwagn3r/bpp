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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An evolutionary algorithm.
 *
 * Performs recombinations, mutations and selections.
 *
 * @author Daniel
 */
public class Evolutionizer {

	/**
	 * My Log class.
	 */
	private static Log log = LogFactory.getLog(Evolutionizer.class);

	/**
	 * The instance which is handled.
	 */
	private Instance instance;

	/**
	 * The population of the evolutionizer.
	 */
	private Dna[] pop;

	/**
	 * Simple constructor.
	 *
	 * @param i
	 *            Instance of the bin packing problem.
	 */
	public Evolutionizer(Instance i) {
		instance = i;

		initInstance();
	}

	/**
	 * Run the algorithm.
	 */
	public void run() {
		// Print current population
		population(true);

		// Process generations
		for (int i = 0; i < JBpp.gen; i++) {
			log.info("Generation " + i);

			// Selection
			selection();

			// Recombination
			recombination();

			// Mutation
			mutation();

			// Print current population
			population(false);
		}
	}

	/**
	 * Initialize the population of the evolutionizer.
	 */
	private void initInstance() {
		log.debug("Initialisation");

		pop = new Dna[JBpp.ps];

		for (int i = 0; i < JBpp.ps; ++i) {
			pop[i] = new Dna(true);
		}
	}

	/**
	 * Mutation phase of the evolutionizer.
	 */
	void mutation() {
		// In jeder Generation 'mr' Mutationen versuchen

		for (int i = 0; i < JBpp.mr; ++i) {
			// Mit Wahrscheinlichkeit 'mp' an einer zuf�lligen Dna eine
			// Mutation durchf�hren

			if (JBpp.rand.nextDouble() < JBpp.mp) {
				pop[JBpp.rand.nextInt(JBpp.ps)].mutate();
			}
		}
	}

	/**
	 * Recombination phase of the evolutionizer.
	 */
	void recombination() {
		Dna[] npop = new Dna[JBpp.ps];
		int i = 0;

		while (i < JBpp.ps) {
			int parentAidx = JBpp.rand.nextInt(JBpp.ps);
			int parentBidx = JBpp.rand.nextInt(JBpp.ps);

			if (JBpp.rand.nextDouble() < JBpp.rp) {
				if (JBpp.sel == 'a') {
					npop[i] = Dna.recombineVariantA(pop[parentAidx],
							pop[parentBidx]);
				} else {
					npop[i] = Dna.recombineVariantB(pop[parentAidx],
							pop[parentBidx]);
				}
				++i;
			}
		}

		pop = npop;
	}

	/**
	 * Selection phase of the evolutionizer.
	 */
	void selection() {
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

		pop = npop;
	}

	/**
	 * Output the population.
	 *
	 * @param all
	 *            Enable of all members.
	 */
	void population(boolean all) {
		double fit = 0.0, avg = 0.0, sum = 0.0;
		double max = Double.MIN_VALUE;
		int best = 0;

		log.info("Population");

		for (int i = 0; i < JBpp.ps; i++) {
			if (all) {
				log.info(pop[i].toString());
			}

			fit = pop[i].fitness();
			sum += fit;

			if (fit > max) {
				max = fit;
				best = i;
			}
		}

		avg = sum / JBpp.ps;

		if (!all) {
			log.info("  " + pop[best].toString());
		}

		log.info("  Maximum: " + max);
		log.info("  Average: " + avg);
		log.info("");
	}
}