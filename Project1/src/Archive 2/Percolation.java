public class Percolation {
	private boolean[] grid;
	private WeightedQuickUnionUF sets;
	private int N;
	private int virtualTop;
	private int virtualBot;

	public Percolation(int N) {// create N-by-N grid, with all sites blocked
		if (N < 0) {
			throw new IllegalArgumentException();
		}
		this.N = N;
		grid = new boolean[N * N];
		sets = new WeightedQuickUnionUF(N * N + 2);
		virtualTop = N * N;
		virtualBot = N * N + 1;
		init();
	}

	private void init() {
		for (int j = 1; j <= N; j++) {
			sets.union(getPos(1, j), virtualTop);
			sets.union(getPos(N, j), virtualBot);
		}
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not already
		grid[getPos(i, j)] = true;
		unionNeighbors(i, j);

	}

	private int getPos(int i, int j) {
		check(i, j);
		return N * (i - 1) + j - 1;
	}

	private void check(int i, int j) {
		if (!isValid(i, j)) {
			throw new IndexOutOfBoundsException();
		}
	}

	private boolean isValid(int i, int j) {
		if (i < 1 || j < 1 || i > N || j > N) {
			return false;
		}
		return true;
	}

	private void unionNeighbors(int i, int j) {
		if (isValid(i + 1, j) && isOpen(i + 1, j)) {
			sets.union(getPos(i, j), getPos(i + 1, j));
		}
		if (isValid(i - 1, j) && isOpen(i - 1, j)) {
			sets.union(getPos(i, j), getPos(i - 1, j));
		}
		if (isValid(i, j + 1) && isOpen(i, j + 1)) {
			sets.union(getPos(i, j), getPos(i, j + 1));
		}
		if (isValid(i, j - 1) && isOpen(i, j - 1)) {
			sets.union(getPos(i, j), getPos(i, j - 1));
		}

	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		check(i, j);
		return grid[getPos(i, j)] == true;
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		return isOpen(i, j) && sets.connected(getPos(i, j), virtualTop);
	}

	public boolean percolates() {
		// does the system percolate?
		if (N == 1)
			return isFull(1, 1);
		return sets.connected(virtualTop, virtualBot);
	}

	public static void main(String[] args) {
		Percolation per = new Percolation(1);
		// per.open(1, 1);
		System.out.println(per.percolates());

		per = new Percolation(2);
		System.out.println(per.isFull(2, 2));
		per.open(1, 1);
		System.out.println(per.percolates());
		per.open(1, 2);
		per.open(2, 2);
		System.out.println(per.isFull(1, 1));
		System.out.println(per.percolates());
	}
}
