import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean, stddev, confidence95 = 1.96;
    private final int t;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        t = trials;
        if ((n <= 0) || (t <= 0)) throw new IllegalArgumentException("Choose a positive value");

        Percolation percolatingTrials = new Percolation(n);
        double[] data = new double[trials];

        for (int i = 0; i < trials; i++) {
            int randomRow = (StdRandom.uniform(n) + 1);
            int randomCol = (StdRandom.uniform(n) + 1);

            while (!percolatingTrials.percolates()) {
                while (percolatingTrials.isOpen(randomRow, randomCol)) {
                    randomRow = (StdRandom.uniform(n) + 1);
                    randomCol = (StdRandom.uniform(n) + 1);
                }
                percolatingTrials.open(randomRow, randomCol);
            }
            data[i] = (double) percolatingTrials.numberOfOpenSites() / (n * n);

        }
        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (confidence95 * stddev()) / (Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (confidence95 * stddev()) / (Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats obj = new PercolationStats(n, trials);
        String a = "mean = " + obj.mean() + "\n";
        String b = "stddev = " + obj.stddev() + "\n";
        String c = "95% confidence interval = [" + obj.confidenceLo() + ", " + obj.confidenceHi()
                + "]";
        System.out.println(a + b + c);
    }

}
