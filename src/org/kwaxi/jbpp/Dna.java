package org.kwaxi.jbpp;

import java.util.*;

class Dna extends Object implements Cloneable {
	Vector<Chromosom> dna;

	public Dna(boolean init) {
		dna = new Vector<Chromosom>();

		if (init) {
			init();
		}
	}

	public Dna(int[] a) {
		Chromosom c = new Chromosom();
		dna = new Vector<Chromosom>();

		for (int j = 0; j < JBpp.n; j++) {
			if (c.weight() + JBpp.data[a[j]] > JBpp.wmax) {
				dna.add(c);
				c = new Chromosom();
			}

			c.add(a[j]);
		}

		dna.add(c);
	}

	public double weight() {
		double g = 0.0;

		for (int j = 0; j < dna.size(); j++) {
			g += (dna.get(j)).weight();
		}

		return (g);
	}

	public double fitness() {
		double g = 0.0;

		for (int j = 0; j < dna.size(); j++) {
			g += Math.pow(dna.get(j).weight() / JBpp.wmax, 2);
		}

		g = g / dna.size();

		return (g);
	}

	public void mutate() {
		/**
		 * Zufällig zwei Chromosome auswählen, von jedem Gen zufällig ein
		 * Chromosm auswählen falls die ausgewählten Gene im anderen Chromosom
		 * noch Platz haben werden diese vertauscht
		 */

		Chromosom ca = dna.get(JBpp.rand.nextInt(dna.size()));
		Chromosom cb = dna.get(JBpp.rand.nextInt(dna.size()));

		if (ca != cb) {
			// Nur bei verschiedenen Chromosomen mutieren

			// Jetzt zwei zufällige Gene auswählen
			int ga = ca.get(JBpp.rand.nextInt(ca.size()));
			int gb = cb.get(JBpp.rand.nextInt(cb.size()));

			if ((JBpp.data[ga] + cb.weight() - JBpp.data[gb] <= JBpp.wmax)
					&& (JBpp.data[gb] + ca.weight() - JBpp.data[ga] <= JBpp.wmax)) {
				ca.remove(ga);
				ca.add(gb);

				cb.remove(gb);
				cb.add(ga);
			}
		}
	}

	public void init() {
		Pool pool = new Pool(JBpp.n);
		Chromosom c = new Chromosom();
		int i;

		dna.clear();

		for (int j = 0; j < JBpp.n; j++) {
			i = pool.nextInt();

			if (c.weight() + JBpp.data[i] > JBpp.wmax) {
				dna.add(c);
				c = new Chromosom();
			}

			c.add(i);
		}

		dna.add(c);
	}

	@Override
	public String toString() {
		String out;
		Chromosom c;

		out = "Fitness: " + fitness() + ", Size: " + dna.size() + ", Dna: ";

		for (int j = 0; j < dna.size(); ++j) {
			c = dna.get(j);

			if (j > 0) {
				out += " | " + c.toString();
			} else {
				out += c.toString();
			}
		}

		return (out);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		Dna d = new Dna(false);

		for (int j = 0; j < dna.size(); ++j) {
			d.dna = (Vector<Chromosom>) dna.clone();
		}

		return (d);
	}

	public int[] toArray() {
		int a[] = new int[JBpp.n];
		int b[];
		int l = 0;

		for (int j = 0; j < dna.size(); ++j) {
			b = dna.get(j).toArray();

			for (int i = 0; i < b.length; ++i) {
				a[l] = b[i];
				++l;
			}
		}

		return a;
	}

	public void repair() {
		Chromosom c;
		boolean f[] = new boolean[JBpp.n];
		int i, j, l;

		// doppelte entfernen
		for (i = 0; i < dna.size(); ++i) {
			c = dna.get(i);

			j = 0;
			while (j < c.size()) {
				l = c.get(j);

				if (f[l]) {
					c.remove(l);
				} else {
					j++;
				}

				f[l] = true;
			}
		}

		// leere chromosome entfernen
		i = 0;
		while (i < dna.size()) {
			c = dna.get(i);

			if (c.size() == 0) {
				dna.remove(c);
			} else {
				i++;
			}
		}

		// fehlende wieder einfügen
		for (i = 0; i < JBpp.n; ++i) {
			if (!f[i]) {
				c = null;

				for (j = 0; j < dna.size(); ++j) {
					c = dna.get(j);

					if (c.weight() + JBpp.data[i] <= JBpp.wmax) {
						break;
					}
				}

				if ((c == null) || (c.weight() + JBpp.data[i] > JBpp.wmax)) {
					c = new Chromosom();
					dna.add(c);
				}

				c.add(i);
			}
		}
	}

	static public Dna recombine_a(Dna a, Dna b) {
		Dna c;
		int n[] = new int[JBpp.n];
		int as[] = a.toArray(), bs[] = b.toArray();

		int x = JBpp.rand.nextInt(JBpp.n);
		int y = JBpp.rand.nextInt(JBpp.n);
		int i;

		if (x > y) {
			int h = x;
			x = y;
			y = h;
		}

		// Chromosome vor dem 1. Crossover-Punkt von a nach c übernehmen
		for (i = 0; i < x; ++i) {
			n[i] = as[i];
		}

		// Im Crossover-Bereich von b nach c übernehmen
		for (; i < y; ++i) {
			n[i] = bs[i];
		}

		// Restliche Chromosome aus a nach c �bernehmen
		for (; i < JBpp.n; ++i) {
			n[i] = as[i];
		}

		// Die so erzeugte L�sung wird jetzt wieder in eine Dna verpackt
		c = new Dna(n);

		// L�sung k�nnte ung�ltig sein
		c.repair();

		return (c);
	}

	static public Dna recombine_b(Dna a, Dna b) {
		Dna c = new Dna(false);

		int i, as, bs;

		as = a.dna.size();
		bs = b.dna.size();

		// f�r jedes Chromosom entscheiden von welcher Dna es �bernommen wird

		i = 0;
		while ((i < as) && (i < bs)) {
			if (JBpp.rand.nextDouble() < 0.5) {
				c.dna.add((Chromosom) a.dna.get(i).clone());
			} else {
				c.dna.add((Chromosom) b.dna.get(i).clone());
			}
		}

		// L�sung reparieren

		c.repair();
		return (c);
	}
}
