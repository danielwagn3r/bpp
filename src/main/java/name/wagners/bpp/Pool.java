/*
 * Bpp - A Bin Packer in Java
 *
 * Copyright (C) 2014  Daniel Wagner
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
 */
package name.wagners.bpp;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides a pool of integers, which can be take in a random
 * sequence.
 */
@Slf4j
class Pool {

	/**
	 * The pool.
	 */
	boolean[] pool;

	/**
	 * Starting value of the integer sequence.
	 */
	int startValue;

	/**
	 * Number of already taken integers from the pool.
	 */
	int taken;

	/**
	 * Simplest constructor.
	 *
	 * @param sizeOfPool
	 *            Size of the sequence in the pool.
	 */
	public Pool(final int sizeOfPool) {
		startValue = 0;
		setSize(sizeOfPool);
	}

	/**
	 * Simple constructor.
	 *
	 * @param offset
	 *            An offset that shifts the start of sequence from zero.
	 * @param sizeOfPool
	 *            Size of the sequence in the pool.
	 */
	public Pool(final int offset, final int sizeOfPool) {
		this.startValue = offset;

		setSize(sizeOfPool);
	}

	/**
	 * Reset the pool.
	 *
	 * A new sequence of integers from the same pool is available.
	 */
	public void reset() {
		taken = 0;

		for (int i = 0; i < pool.length; ++i) {
			pool[i] = false;
		}
	}

	/**
	 * Adjusts the size of the pool.
	 *
	 * @param sizeOfPool
	 *            Size of the sequence of integers in the pool.
	 */
	private void setSize(final int sizeOfPool) {
		pool = new boolean[sizeOfPool];

		taken = 0;

		for (int i = 0; i < pool.length; ++i) {
			pool[i] = false;
		}
	}

	/**
	 * Choose the next integer from the pool for the sequence.
	 *
	 * @return Random integer not already chosen from the pool.
	 */
	public int nextInt() throws NoNextIntAvailableException,
			PoolNotAvailableException {

		if (pool == null) {
			throw new PoolNotAvailableException();
		}

		if (taken >= pool.length) {
			throw new NoNextIntAvailableException();
		}

		int i = Bpp.rand.nextInt(pool.length);

		while (pool[i]) {
			i = Bpp.rand.nextInt(pool.length);
		}

		pool[i] = true;
		taken++;

		return i;
	}
}
