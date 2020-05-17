import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double CONSTANT_95 = 1.96;
    private int totalTrials;
    private double size;
    private double meanVal, stdivVal, lowerConf, upperConf; // statistics
    private double[] trial_val; // count of steps/total nxn size for reach trial
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid inpus");
        trial_val = new double[trials];
        size = n * n;
        totalTrials = trials;
         for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            run(per, n, i);
        }
    }

    private void run(Percolation per, int n, int i) {
        int lower = 1;
        int upper = n + 1;
        while (!per.percolates()) {
            int row = StdRandom.uniform(lower, upper);
            int col = StdRandom.uniform(lower, upper);
            if (!per.isOpen(row, col)) per.open(row, col);
        }
        trial_val[i] = per.numberOfOpenSites()/size;
    }
    // sample mean of percolation threshold
    public double mean() {
        meanVal = StdStats.mean(trial_val);
        return meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stdivVal = StdStats.stddev(trial_val);
        return stdivVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        lowerConf = meanVal - (CONSTANT_95 * stdivVal / (Math.sqrt(totalTrials)));
        return lowerConf;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        upperConf = meanVal + (CONSTANT_95 * stdivVal / (Math.sqrt(totalTrials)));
        return upperConf;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                        = " + stats.mean());
        System.out.println("stddev                      = " + stats.stddev());
        System.out.println("95 % confidence interval    = " + stats.confidenceLo() + "," + stats.confidenceHi());
    }

}