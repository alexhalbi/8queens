/**
 * The Class Board.
 * 1 quadratic board with queens
 */
public class Board {
	
	/** The size of the board n*n */
	private int n;
	
	/** The queens Array which saves the positions of the queens
	 * Array Index = Y Coordinate
	 * Array Value = X Coordinate */
	private byte queens[];

	/**
	 * Instantiates a new board.
	 *
	 * @param n The size of the board n*n
	 */
	public Board(int n) {
		this.n = n;
		queens = new byte[n];
	}
	
	/**
	 * @return The size of the board n*n
	 */
	public int size() {
		return n;
	}

	/**
	 * Populate Board with random Queens.
	 * No Queen can be in the same row or column than another queen!
	 */
	public void generateQueens() {
		for (int i = 0; i < n; i++) {
			byte r = (byte) (Math.random() * n);
			for(int j = 0; j < i; j++){
				if(queens[j] == r)
				{
					j = -1;
					r = (byte) (Math.random() * n);
				}
			}
			queens[i] = r; 
		}
	}

	/**
	 * Prints the board in the Console.
	 */
	public void print() {
		for (int i = 0; i < n; i++) {
			int a = 0;
			for (; a < queens[i]; a++) {
				System.out.print(". ");
			}
			System.out.print("Q ");
			a++;
			for (; a < n; a++) {
				System.out.print(". ");
			}
			System.out.println();
		}
	}

	/**
	 * The Fitness of the board.
	 * Lower is better!
	 * 0 is the solution of the Problem
	 *
	 * @return the Fitness
	 */
	public int fitness() {		//|q[i]-q[j]|=|i-j|
		int f = 0;
		for(int i = 0; i<n;i++) {
			for(int j = 0; j<n;j++) {
				if(Math.abs(queens[i]-queens[j])==Math.abs(i-j)) f++;
			}
		}
		return f/2-4;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Returns if one Board equals another.
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			Board b = (Board) obj;
			return b.queens.equals(queens);
		} catch (Exception e) {
			return super.equals(obj);
		}
	}
	
	/**
	 * Sets one queen at a position
	 *
	 * @param i The Index of the Queen (Y-Coordinate)
	 * @param val The Value to set (X-Coordinate)
	 */
	public void setQueen(int i, byte val) {
		queens[i] = val;
	}
	
	/**
	 * Returns the Value in the Queen Array.
	 * Y-Coordinate gives back X-Coordinate
	 *
	 * @param i The Index to return (Y-Coordinate)
	 * @return The Value of the specific Index (X-Coordinate)
	 */
	public byte getQueen(int i) {
		return queens[i];
	}
	
	/**
	 * Repairs the board.
	 * Resolves wrong values in the Array 
	 * (Only one Queen per Row and Column!)
	 */
	public void repairBoard() {
		for (int a = 0; a < size(); a++) {
			for (int ab = 0; ab < size(); ab++) {
				if (a==ab)  {
					ab++;
					if(ab>=size()) {
						break;
					}
				}
				if (getQueen(a) == getQueen(ab)) {
					int i;
					if (Math.random() > 0.5)
						i = a;
					else
						i = ab;
					byte r = (byte) (Math.random() * size());
					for (int j = 0; j < size(); j++) {
						if (getQueen(j) == r) {
							j = -1;
							r = (byte) (Math.random() * size());
						}
					}
					setQueen(i, r);
				}
			}
		}
	}
}