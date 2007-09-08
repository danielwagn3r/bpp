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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JBpp {

	private static Log log = LogFactory.getLog(JBpp.class);

	static int gen = 50;
	static int sp = 4;
	static int ps = 20;
	static double rp = 0.8;
	static double mp = 0.5;
	static int mr = 1;
	static int sel = 'a';
	static boolean elitism = false;
	static String fname;

	static double wmax;
	static int n;

	static double[] data;
	static Random rand;
	static Dna[] pop;

	static void init() {
		log.debug("Initialisation");

		pop = new Dna[ps];

		for (int i = 0; i < ps; ++i) {
			pop[i] = new Dna(true);
		}
	}

	static void load() {
		File file;
		BufferedReader fileReader;
		String line;

		try {
			file = new File(fname);

			if (!file.exists()) {
				log.fatal("File '" + fname + "' doesn't exist!");
			} else if (!file.canRead()) {
				log.fatal("File '" + fname + "' couldn't be read!");
			} else {
				fileReader = new BufferedReader(new FileReader(file));

				line = fileReader.readLine();

				StringTokenizer tokenizer = new StringTokenizer(line);

				if (tokenizer.hasMoreTokens()) {
					wmax = (new Double(tokenizer.nextToken())).doubleValue();
				} else {
					log.fatal("File '" + fname + "' currupted!");
					System.exit(2);
				}

				if (tokenizer.hasMoreTokens()) {
					n = (new Integer(tokenizer.nextToken())).intValue();
				} else {
					log.fatal("File '" + fname + "' currupted!");
					System.exit(2);
				}

				log.info("File-Information");
				log.info("  Wmax:                      " + wmax);
				log.info("  n:                         " + n);

				data = new double[n];

				for (int i = 0; i < n; i++) {
					line = fileReader.readLine();

					tokenizer = new StringTokenizer(line);

					if (tokenizer.hasMoreTokens()) {
						data[i] = (new Double(tokenizer.nextToken()))
								.doubleValue();
					} else {
						log.fatal("File '" + fname + "' currupted!");
						System.exit(2);
					}
				}
			}
		} catch (IOException e) {
			log.fatal(e.getMessage());
			System.exit(2);
		}
	}

	public static void main(String[] args) {
		rand = new Random();

		// create the command line parser
		CommandLineParser parser = new PosixParser();

		// create the Options
		Options options = new Options();

		options.addOption(OptionBuilder.hasArg().withArgName("int")
				.withLongOpt("generations").withDescription(
						"Number of generations [default: 50]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("int")
				.withLongOpt("mutrate").withDescription(
						"Mutation rate [default: 1]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("double")
				.withLongOpt("mutprop").withDescription(
						"Mutation propability [default: 0.5]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("int")
				.withLongOpt("populationsize").withDescription(
						"Size of population [default: 20]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("a|b")
				.withLongOpt("recombalg").withDescription(
						"Recombination algorithm [default: a]").create());

		// options.addOption(OptionBuilder
		// .hasArg()
		// .withArgName("int")
		// .withLongOpt("recombrate")
		// .withDescription("Recombination rate [default: 1]")
		// .create());

		options.addOption(OptionBuilder.hasArg().withArgName("double")
				.withLongOpt("recombprop").withDescription(
						"Recombination propability [default: 0.8]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("int")
				.withLongOpt("selectionpressure").withDescription(
						"Selection pressure [default: 4]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("bool")
				.withLongOpt("elitism").withDescription(
						"Enable Elitism [default: 1]").create());

		options.addOption(OptionBuilder.hasArg().withArgName("file")
				// .isRequired()
				.withLongOpt("datafile").withDescription(
						"Problem data file [default: \"binpack.txt\"]")
				.create());

		options.addOptionGroup(new OptionGroup().addOption(
				OptionBuilder.withLongOpt("verbose").withDescription(
						"be extra verbose").create()).addOption(
				OptionBuilder.withLongOpt("quiet").withDescription(
						"be extra quiet").create()));

		options.addOption(OptionBuilder.withLongOpt("version").withDescription(
				"print the version information and exit").create());

		options.addOption(OptionBuilder.withLongOpt("help").withDescription(
				"print this message").create());

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// validate that block-size has been set
			if (line.hasOption("help")) {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("JBpp", options);

				System.exit(0);
			}

			if (line.hasOption("version")) {
				log.info("JBpp 0.1 (c) 2007 by Daniel Wagner");
			}

			if (line.hasOption("datafile")) {
				fname = line.getOptionValue("datafile");
			}

			if (line.hasOption("elitism")) {
				elitism = Boolean.parseBoolean(line.getOptionValue("elitism"));
			}

			if (line.hasOption("generations")) {
				gen = Integer.parseInt(line.getOptionValue("generations"));
			}

			if (line.hasOption("mutprop")) {
				mp = Double.parseDouble(line.getOptionValue("mutprop"));
			}

			if (line.hasOption("mutrate")) {
				mr = Integer.parseInt(line.getOptionValue("mutrate"));
			}

			if (line.hasOption("populationsize")) {
				ps = Integer.parseInt(line.getOptionValue("populationsize"));
			}

			if (line.hasOption("recombalg")) {
				sel = line.getOptionValue("recombalg").charAt(0);
			}

			if (line.hasOption("recombprop")) {
				rp = Double.parseDouble(line.getOptionValue("recombprop"));
			}

			if (line.hasOption("selectionpressure")) {
				sp = Integer.parseInt(line.getOptionValue("selectionpressure"));
			}

		} catch (ParseException exp) {
			log.info("Unexpected exception:" + exp.getMessage(), exp);

			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("JBpp", options);

			System.exit(1);
		}

		// Ausgabe der eingestellten Optionen

		log.info("Configuration");
		log.info("  Datafile:                  " + fname);
		log.info("  Generations:               " + gen);
		log.info("  Population size:           " + ps);
		log.info("  Elitism:                   " + elitism);
		log.info("  Mutation propapility:      " + mp);
		log.info("  Mutation rate:             " + mr);
		log.info("  Recombination algorithm    " + (char) sel);
		log.info("  Recombination propapility: " + rp);
		log.info("  Selection pressure:        " + sp);

		// Daten laden
		load();

		// Population initialisieren
		init();

		// Aktuelle Population ausgeben
		population(true);

		// Generationen durchlaufen
		for (int i = 0; i < gen; i++) {
			log.info("Generation " + i);

			// Selektion
			selection();

			// Rekombination
			recombination();

			// Mutation
			mutation();

			// Aktuelle Population ausgeben
			population(false);
		}
	}

	static void mutation() {
		// In jeder Generation 'mr' Mutationen versuchen

		for (int i = 0; i < mr; ++i) {
			// Mit Wahrscheinlichkeit 'mp' an einer zufälligen Dna eine Mutation
			// durchführen

			if (rand.nextDouble() < mp) {
				pop[rand.nextInt(ps)].mutate();
			}
		}
	}

	static void population(boolean all) {
		double fit = 0.0, avg = 0.0, sum = 0.0;
		double max = Double.MIN_VALUE;
		int best = 0;

		log.info("Population");

		for (int i = 0; i < ps; i++) {
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

		avg = sum / ps;

		if (!all) {
			log.info("  " + pop[best].toString());
		}

		log.info("  Maximum: " + max);
		log.info("  Average: " + avg);
		log.info("");
	}

	static void recombination() {
		Dna npop[] = new Dna[ps];
		int a, b;
		int i = 0;

		while (i < ps) {
			a = rand.nextInt(ps);
			b = rand.nextInt(ps);

			if (rand.nextDouble() < rp) {
				if (sel == 'a') {
					npop[i] = Dna.recombine_a(pop[a], pop[b]);
				} else {
					npop[i] = Dna.recombine_b(pop[a], pop[b]);
				}
				++i;
			}
		}

		pop = npop;
	}

	static void selection() {
		Dna npop[] = new Dna[ps];
		double fit = 0.0;
		double max = Double.MIN_VALUE;
		int best = 0, c;

		// einfache tournament selection
		for (int i = 0; i < ps; ++i) {
			max = Double.MIN_VALUE;

			for (int j = 0; j < sp; ++j) {
				c = rand.nextInt(ps);

				fit = pop[c].fitness();

				if (fit > max) {
					best = c;
					max = fit;
				}
			}

			npop[i] = (Dna) pop[best].clone();
		}

		pop = npop;
	}
}
