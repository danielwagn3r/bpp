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

import java.util.Random;

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

	/**
	 * My Log class.
	 */
	private static Log log = LogFactory.getLog(JBpp.class);

	private static final int DEFAULT_GEN = 50;
	private static final int DEFAULT_SP = 4;
	private static final int DEFAULT_PS = 20;
	private static final double DEFAULT_RP = 0.8;
	private static final double DEFAULT_MP = 0.5;
	private static final int DEFAULT_MR = 1;
	private static final char DEFAULT_SEL = 'a';
	private static final char DEFAULT_SELALG = 'a';
	private static final boolean DEFAULT_ELITISM = false;

	public static int gen = DEFAULT_GEN;
	public static int sp = DEFAULT_SP;
	public static int ps = DEFAULT_PS;
	public static double rp = DEFAULT_RP;
	public static double mp = DEFAULT_MP;
	public static int mr = DEFAULT_MR;
	public static char sel = DEFAULT_SEL;
	public static char selalg = DEFAULT_SELALG;
	public static boolean elitism = DEFAULT_ELITISM;
	static String fname;

	static double wmax;
	static int n;

	public static Instance instance;
	public static Random rand = new Random();

	public static void main(final String[] args) {

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

		options.addOption(OptionBuilder.hasArg().withArgName("a")
				.withLongOpt("selalg").withDescription(
						"Selection algorithm [default: a]").create());

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

			if (line.hasOption("selalg")) {
				selalg = line.getOptionValue("selalg").charAt(0);
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
		instance = new Instance();
		instance.load(fname);

		Evolutionizer e = new Evolutionizer(instance);

		e.run();
	}
}
