public class Queens {
	
	/**
	 * The main method to generate the Solution
	 *
	 * @param args There are no arguments!
	 */
	static public void main(String args[]) {
		int mut = 5; //mutation rate
		long finishT;
		long startT;
		finishT = System.currentTimeMillis();
		Genetics g = new Genetics(4,8);	//Number of initial Population, Size of board
		System.out.println("---------G---------");
		g.generatePopulation();
		g.print();
		int i = 0;
		for (; g.bestBoard().fitness() > 0; i++) {	//search until solution
			System.out.println("---------" + i + "---------");
			g.crossoverBoards();
			g.print();
			g.mutateBoards(mut);
		}
		startT = System.currentTimeMillis();
		System.out.println("------------------");
		System.out.println("Time: " + ((startT - finishT)) + " millisec");
		System.out.println(i+" Generations\nSolution:");
		g.bestBoard().print();
	}
}