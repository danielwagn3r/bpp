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
 * An evolutionary algorithm.
 *
 * Performs recombinations, mutations and selections.
 *
 * @author Daniel Wagner <daniel@wagners.name>
 */
@Slf4j
public class Evolutionizer {

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
		for (int i = 0; i < Bpp.gen; i++) {
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

		pop = new Dna[Bpp.ps];

		for (int i = 0; i < Bpp.ps; ++i) {
			pop[i] = new Dna(true);
		}
	}

	/**
	 * Mutation phase of the evolutionizer.
	 */
	void mutation() {
		// In jeder Generation 'mr' Mutationen versuchen

		for (int i = 0; i < Bpp.mr; ++i) {
			// Mit Wahrscheinlichkeit 'mp' an einer zuf�lligen Dna eine
			// Mutation durchf�hren

			if (Bpp.rand.nextDouble() < Bpp.mp) {
				pop[Bpp.rand.nextInt(Bpp.ps)].mutate();
			}
		}
	}

	/**
	 * Recombination phase of the evolutionizer.
	 */
	void recombination() {
		Dna[] npop = new Dna[Bpp.ps];
		int i = 0;

		while (i < Bpp.ps) {
			int parentAidx = Bpp.rand.nextInt(Bpp.ps);
			int parentBidx = Bpp.rand.nextInt(Bpp.ps);

			if (Bpp.rand.nextDouble() < Bpp.rp) {
				if (Bpp.sel == 'a') {
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

		for (int i = 0; i < Bpp.ps; i++) {
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

		avg = sum / Bpp.ps;

		if (!all) {
			log.info("  " + pop[best].toString());
		}

		log.info("  Maximum: " + max);
		log.info("  Average: " + avg);
		log.info("");
	}
}
