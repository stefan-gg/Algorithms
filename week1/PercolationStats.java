import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percolationTreshold;

    private final int trials;

    private final double constant = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.percolationTreshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int col = StdRandom.uniformInt(1, n + 1);
                int row = StdRandom.uniformInt(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }

            this.percolationTreshold[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {return StdStats.mean(this.percolationTreshold);}

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.percolationTreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {return this.mean() - ((this.constant * stddev()) / Math.sqrt(trials));}

    // high endpoint of 95% confidence interval
    public double confidenceHi() {return this.mean() + ((this.constant * stddev()) / Math.sqrt(trials));}

    // test client
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + "[" + ps.confidenceLo() +
                ", " + ps.confidenceHi() + "]");
    }
}