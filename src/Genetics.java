import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Genetics {

	private LinkedList<Brett> bretter;
	private int gens;
	private int size = 8;

	public Genetics(int generations) {
		bretter = new LinkedList<>();
		gens = generations;
	}

	public void generateGenerations() {
		for (int i = 0; i < gens; i++) {
			int a;
			Brett b;
			do {
				b = new Brett(size);
				b.generateQueens();
				a = bretter.indexOf(b);
			} while (a != -1);
			bretter.add(b);
		}
	}

	public void print() {
		for (int i = 0; i < bretter.size(); i++) {
			System.out.println("Brett " + (i) + ":");
			Brett brett = bretter.get(i);
			brett.print();
			System.out.println("Fitness: " + brett.fitness() + "\n");
		}
	}

	public void rank() {
		Collections.sort(bretter, new Comparator<Brett>() {
			@Override
			public int compare(Brett o1, Brett o2) {
				int f2 = o2.fitness();
				int f1 = o1.fitness();
				if (f2 > f1) {
					return -1;
				} else if (f2 < f1) {
					return 1;
				}
				return 0;
			}
		});
	}

	public void crossoverBretter() {
		rank();
		Iterator<Brett> i = bretter.iterator();
		LinkedList<Brett> b = new LinkedList<>(bretter);
		Brett b1 = null;
		Brett b2 = null;
		for (int a = 0; a < gens; a++) {
			b2 = i.next();
			Brett n = crossover(b1, b2);
			b.set(a, n);
			b1 = b2;
		}
		bretter = b;
		rank();
	}

	public static Brett crossover(Brett b1, Brett b2) {
		if (b1 == null)
			return b2;
		if (b2 == null)
			return b1;
		if (b1.size() != b2.size())
			return null;
		Brett b = new Brett(b1.size());
		if(Math.random()>0.5) {
			Brett s = b1;
			b1 = b2;
			b2 = s;
		}
		for (int i = b.size() / 2; i < b.size(); i++) {
			if (i < b.size() / 2) {
				b.setQueen(i, b1.getQueen(i));
			} else {
				b.setQueen(i, b2.getQueen(i));
			}
		}
		b.repairBrett();
		return b;
	}

	public void mutateBretter(int mut) {
		int a = (bretter.size()-1) * mut / 100;
		if (mut > 0 && a < 1)
			a = 1;
		for (int i = 0; i < a; i++) {
			int pos = (int) (Math.random() * (bretter.size()-1));
			bretter.set(pos, mutateBrett(bretter.get(pos), mut));
			bretter.get(pos).repairBrett();
		}
	}

	public static Brett mutateBrett(Brett b, int mut) {
		Brett n = new Brett(b.size());
		int a = Math.round(n.size() ^ 2 * mut / 100);
		if (mut > 0 && a < 1)
			a = 1;
		for (int i = 0; i < a; i++) {
			int p1 = (int) (Math.random() * n.size());
			int p2 = (int) (Math.random() * n.size());
			byte by = b.getQueen(p1);
			n.setQueen(p1, b.getQueen(p2));
			n.setQueen(p2, by);
		}
		return n;
	}

	public Brett bestBoard() {
		return bretter.getFirst();
	}

}
