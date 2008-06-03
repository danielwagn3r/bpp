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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An instance of the Binpack-Problem.
 *
 * This class holds all instance data.
 *
 * @author Daniel
 */
public class Instance {

	/**
	 * My Log class.
	 */
	private static Log log = LogFactory.getLog(Instance.class);

	double[] data;

	void load(String fileName) {
		File file;
		BufferedReader fileReader;
		String line;

		try {
			file = new File(fileName);

			if (!file.exists()) {
				log.fatal("File '" + fileName + "' doesn't exist!");
			} else if (!file.canRead()) {
				log.fatal("File '" + fileName + "' couldn't be read!");
			} else {
				fileReader = new BufferedReader(new FileReader(file));

				line = fileReader.readLine();

				StringTokenizer tokenizer = new StringTokenizer(line);

				if (tokenizer.hasMoreTokens()) {
					JBpp.wmax = (new Double(tokenizer.nextToken()))
							.doubleValue();
				} else {
					log.fatal("File '" + fileName + "' currupted!");
					System.exit(2);
				}

				if (tokenizer.hasMoreTokens()) {
					JBpp.n = (Integer.valueOf(tokenizer.nextToken()))
							.intValue();
				} else {
					log.fatal("File '" + fileName + "' currupted!");
					System.exit(2);
				}

				log.info("File-Information");
				log.info("  Wmax:                      " + JBpp.wmax);
				log.info("  n:                         " + JBpp.n);

				data = new double[JBpp.n];

				for (int i = 0; i < JBpp.n; i++) {
					line = fileReader.readLine();

					tokenizer = new StringTokenizer(line);

					if (tokenizer.hasMoreTokens()) {
						data[i] = (new Double(tokenizer.nextToken()))
								.doubleValue();
					} else {
						log.fatal("File '" + fileName + "' currupted!");
						System.exit(2);
					}
				}
			}
		} catch (IOException e) {
			log.fatal(e.getMessage());

			// TODO: Tidy up exception handling.
			System.exit(2);
		}
	}
}
