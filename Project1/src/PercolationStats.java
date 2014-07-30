public class PercolationStats {
	private int T;
	private int N;
	private double[] array;

	public PercolationStats(int N, int T) {
		
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		Percolation per = new Percolation(N);
		this.T = T;
		this.N = N;
		array = new double[T];
		simulate(per);
		// perform T independent computational experiments on an N-by-N grid
	}

	private void simulate(Percolation per) {
		
		for (int k = 0; k < T; k++) {
			double count = 0;
			per = new Percolation(N);
			
			while (!per.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				
				while(per.isOpen(i, j)) {
					i = StdRandom.uniform(1, N + 1);
					j = StdRandom.uniform(1, N + 1);
				}
				
				per.open(i, j);
				count++;
			}
			array[k] = count / (N * N);
		}
	}

	public double mean() {
		// sample mean of percolation threshold
		return StdStats.mean(array);
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(array);
	}

	public double confidenceLo() {
		// returns lower bound of the 95% confidence interval
		double mean = mean();
		double dev = stddev();
		return mean - 1.96*dev/(Math.sqrt(T));
	}

	public double confidenceHi() {
		// returns upper bound of the 95% confidence interval
		double mean = mean();
		double dev = stddev();
		return mean + 1.96*dev/(Math.sqrt(T));
	}

	public static void main(String[] args) {
		// test client, described below
		int N;
		int T;
		if (args.length == 0) {
			N = 200;
			T = 100;
		} else {
			N = Integer.parseInt(args[0]);
			T = Integer.parseInt(args[1]);
		}
		
		PercolationStats per = new PercolationStats(N, T);
	    System.out.println("mean                    = "  + per.mean()); 
        System.out.println("stddev                  = "  + per.stddev());
        System.out.println("95% confidence interval = "  + per.confidenceLo()
                               + ", " + per.confidenceHi());
	}
}
