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

		for (int j = 0; j < bpp.n; j++) {
			if (c.weight() + bpp.data[a[j]] > bpp.wmax) {
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
			g += Math.pow(dna.get(j).weight() / bpp.wmax, 2);
		}

		g = g / dna.size();

		return (g);
	}

	public void mutate() {
		/**
		 * ZufÃ¤llig zwei Chromosome auswÃ¤hlen, von jedem Gen zufÃ¤llig ein
		 * Chromosm auswÃ¤hlen falls die ausgewÃ¤hlten Gene im anderen Chromosom
		 * noch Platz haben werden diese vertauscht
		 */

		Chromosom ca = dna.get(bpp.rand.nextInt(dna.size()));
		Chromosom cb = dna.get(bpp.rand.nextInt(dna.size()));

		if (ca != cb) {
			// Nur bei verschiedenen Chromosomen mutieren

			// Jetzt zwei zufÃ¤llige Gene auswÃ¤hlen
			int ga = ca.get(bpp.rand.nextInt(ca.size()));
			int gb = cb.get(bpp.rand.nextInt(cb.size()));

			if ((bpp.data[ga] + cb.weight() - bpp.data[gb] <= bpp.wmax)
					&& (bpp.data[gb] + ca.weight() - bpp.data[ga] <= bpp.wmax)) {
				ca.remove(ga);
				ca.add(gb);

				cb.remove(gb);
				cb.add(ga);
			}
		}
	}

	public void init() {
		Pool pool = new Pool(bpp.n);
		Chromosom c = new Chromosom();
		int i;

		dna.clear();

		for (int j = 0; j < bpp.n; j++) {
			i = pool.nextInt();

			if (c.weight() + bpp.data[i] > bpp.wmax) {
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
		int a[] = new int[bpp.n];
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
		boolean f[] = new boolean[bpp.n];
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

		// fehlende wieder einfÃ¼gen
		for (i = 0; i < bpp.n; ++i) {
			if (!f[i]) {
				c = null;

				for (j = 0; j < dna.size(); ++j) {
					c = dna.get(j);

					if (c.weight() + bpp.data[i] <= bpp.wmax) {
						break;
					}
				}

				if ((c == null) || (c.weight() + bpp.data[i] > bpp.wmax)) {
					c = new Chromosom();
					dna.add(c);
				}

				c.add(i);
			}
		}
	}

	static public Dna recombine_a(Dna a, Dna b) {
		Dna c;
		int n[] = new int[bpp.n];
		int as[] = a.toArray(), bs[] = b.toArray();

		int x = bpp.rand.nextInt(bpp.n);
		int y = bpp.rand.nextInt(bpp.n);
		int i;

		if (x > y) {
			int h = x;
			x = y;
			y = h;
		}

		// Chromosome vor dem 1. Crossover-Punkt von a nach c Ã¼bernehmen
		for (i = 0; i < x; ++i) {
			n[i] = as[i];
		}

		// Im Crossover-Bereich von b nach c Ã¼bernehmen
		for (; i < y; ++i) {
			n[i] = bs[i];
		}

		// Restliche Chromosome aus a nach c übernehmen
		for (; i < bpp.n; ++i) {
			n[i] = as[i];
		}

		// Die so erzeugte Lösung wird jetzt wieder in eine Dna verpackt
		c = new Dna(n);

		// Lösung könnte ungültig sein
		c.repair();

		return (c);
	}

	static public Dna recombine_b(Dna a, Dna b) {
		Dna c = new Dna(false);

		int i, as, bs;

		as = a.dna.size();
		bs = b.dna.size();

		// für jedes Chromosom entscheiden von welcher Dna es übernommen wird

		i = 0;
		while ((i < as) && (i < bs)) {
			if (bpp.rand.nextDouble() < 0.5) {
				c.dna.add((Chromosom) a.dna.get(i).clone());
			} else {
				c.dna.add((Chromosom) b.dna.get(i).clone());
			}
		}

		// Lösung reparieren

		c.repair();
		return (c);
	}
}
