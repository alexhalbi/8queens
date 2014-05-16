import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class with the Genetic Functions. 
 */
public class Genetics {

	/** The Collection of the Population */
	private LinkedList<Board> bretter;
	
	/** The number of the initial population */
	private int population;
	
	/** The size of the Boards n*n */
	private int size = 8;

	/**
	 * Instantiates a new genetics to access the Mathods for generating the solution.
	 *
	 * @param The number of the initial population
	 */
	public Genetics(int generations) {
		bretter = new LinkedList<>();
		population = generations;
	}

	/**
	 * Generates population.
	 */
	public void generatePopulation() {
		for (int i = 0; i < population; i++) {
			int a;
			Board b;
			do {
				b = new Board(size);
				b.generateQueens();
				a = bretter.indexOf(b);
			} while (a != -1);
			bretter.add(b);
		}
	}

	/**
	 * Prints the whole Population.
	 */
	public void print() {
		for (int i = 0; i < bretter.size(); i++) {
			System.out.println("Brett " + (i) + ":");
			Board brett = bretter.get(i);
			brett.print();
			System.out.println("Fitness: " + brett.fitness() + "\n");
		}
	}

	/**
	 * Rank the population with the fitness (less fitness first!).
	 */
	public void rank() {
		Collections.sort(bretter, new Comparator<Board>() {
			@Override
			public int compare(Board o1, Board o2) {
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

	/**
	 * Crossover the whole Population.
	 */
	public void crossoverBoards() {
		rank();
		Iterator<Board> i = bretter.iterator();
		LinkedList<Board> b = new LinkedList<>(bretter);
		Board b1 = null;
		Board b2 = null;
		for (int a = 0; a < population; a++) {
			b2 = i.next();
			Board n = crossover(b1, b2);
			b.set(a, n);
			b1 = b2;
		}
		bretter = b;
		rank();
	}

	/**
	 * Crossover 2 Boards.
	 * split in half, then randomly 1 half with another half
	 *
	 * After that correction of wrong queens! 
	 *
	 * @param b1 first board
	 * @param b2 second board
	 * @return the crossover board
	 */
	public static Board crossover(Board b1, Board b2) {
		if (b1 == null)
			return b2;
		if (b2 == null)
			return b1;
		if (b1.size() != b2.size())
			return null;
		Board b = new Board(b1.size());
		if(Math.random()>0.5) {
			Board s = b1;
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
		b.repairBoard();
		return b;
	}

	/**
	 * Mutate all Boards
	 *
	 * @param mut the mutation rate in percent
	 */
	public void mutateBoards(int mut) {
		int a = (bretter.size()-1) * mut / 100;
		if (mut > 0 && a < 1)
			a = 1;
		for (int i = 0; i < a; i++) {
			int pos = (int) (Math.random() * (bretter.size()-1));
			bretter.set(pos, mutateBrett(bretter.get(pos), mut));
			bretter.get(pos).repairBoard();
		}
	}

	/**
	 * Mutate one board.
	 *
	 * @param b the board to mutate
	 * @param mut the mutation rate
	 * @return the mutated board
	 */
	public static Board mutateBrett(Board b, int mut) {
		Board n = new Board(b.size());
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

	/**
	 * Best board.
	 *
	 * @return the brett
	 */
	public Board bestBoard() {
		return bretter.getFirst();
	}

}
