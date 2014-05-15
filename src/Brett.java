public class Brett {
	private int n;
	private byte queens[];

	public Brett(int n) {
		this.n = n;
		queens = new byte[n];
	}
	
	public int size() {
		return n;
	}

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

	public int fitness() {		//|q[i]-q[j]|=|i-j|
		int f = 0;
		for(int i = 0; i<n;i++) {
			for(int j = 0; j<n;j++) {
				if(Math.abs(queens[i]-queens[j])==Math.abs(i-j)) f++;
			}
		}
	/*	for (int a = 0; a < n; a++) {
			for (int i = 0; i < n; i++) {
				if (queens[a] == queens[i])
					f+=1000;
			}
		}*/
		return f/2-4;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Brett b = (Brett) obj;
			return b.queens.equals(queens);
		} catch (Exception e) {
			return super.equals(obj);
		}
	}
	
	public void setQueen(int i, byte val) {
		queens[i] = val;
	}
	
	public byte getQueen(int i) {
		return queens[i];
	}
	
	public void repairBrett() {
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