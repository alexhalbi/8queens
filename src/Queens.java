public class Queens {
	static public void main(String args[]) {
		int mut = 5;
		long zstVorher;
		long zstNachher;
		zstVorher = System.currentTimeMillis();
		Genetics g = new Genetics(4);
		System.out.println("---------G---------");
		g.generateGenerations();
		g.print();
		for (int i = 1; g.bestBoard().fitness() > 0; i++) {	//search until solution
		//for (int i = 1; i <= 10; i++) {		//search 10 times
			System.out.println("---------" + i + "---------");
			g.crossoverBretter();
			g.print();
			g.mutateBretter(mut);
		}
		zstNachher = System.currentTimeMillis();
		System.out.println("Zeit benötigt: " + ((zstNachher - zstVorher)) + " millisec");
	}
}