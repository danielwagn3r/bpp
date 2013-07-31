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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import lombok.extern.slf4j.Slf4j;

/**
 * An instance of the Binpack-Problem.
 *
 * This class holds all instance data.
 *
 * @author Daniel Wagner <daniel@wagners.name>
 */
@Slf4j
public class Instance {

	public double[] data;

	void load(String fileName) {
		File file;
		BufferedReader fileReader;
		String line;

		try {
			file = new File(fileName);

			if (!file.exists()) {
				log.error("File '" + fileName + "' doesn't exist!");
			} else if (!file.canRead()) {
				log.error("File '" + fileName + "' couldn't be read!");
			} else {
				fileReader = new BufferedReader(new FileReader(file));

				line = fileReader.readLine();

				StringTokenizer tokenizer = new StringTokenizer(line);

				if (tokenizer.hasMoreTokens()) {
					Bpp.wmax = (new Double(tokenizer.nextToken()))
							.doubleValue();
				} else {
					log.error("File '" + fileName + "' currupted!");
					System.exit(2);
				}

				if (tokenizer.hasMoreTokens()) {
					Bpp.n = (Integer.valueOf(tokenizer.nextToken())).intValue();
				} else {
					log.error("File '" + fileName + "' currupted!");
					System.exit(2);
				}

				log.info("File-Information");
				log.info("  Wmax:                      " + Bpp.wmax);
				log.info("  n:                         " + Bpp.n);

				data = new double[Bpp.n];

				for (int i = 0; i < Bpp.n; i++) {
					line = fileReader.readLine();

					tokenizer = new StringTokenizer(line);

					if (tokenizer.hasMoreTokens()) {
						data[i] = (new Double(tokenizer.nextToken()))
								.doubleValue();
					} else {
						log.error("File '" + fileName + "' currupted!");
						System.exit(2);
					}
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());

			// TODO: Tidy up exception handling.
			System.exit(2);
		}
	}
}
