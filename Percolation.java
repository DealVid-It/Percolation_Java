import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int counter = 0;
    private final int n;
    private boolean[] arr;
    private final WeightedQuickUnionUF unionFindGrid, isFullGrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int x) {
        n = x;
        if (n <= 0) throw new IllegalArgumentException("Choose a valid value");

        unionFindGrid = new WeightedQuickUnionUF(n * n);
        isFullGrid = new WeightedQuickUnionUF(n * n);
        for (int i = 1; i < n; i++) {
            unionFindGrid.union(0, i);
            unionFindGrid.union(n * (n - 1), (n * n) - i);

            isFullGrid.union(0, i);
        }

        arr = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            arr[i] = false;
        }
    }

    private int convertToCurrent(int row, int col) {
        return ((n * (row - 1)) + col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!((row > 0 && row <= n) && (col > 0 && col <= n)))
            throw new IllegalArgumentException("Choose a valid value");


        if (!arr[convertToCurrent(row, col)]) {

            arr[convertToCurrent(row, col)] = true;
            counter++;

            if ((convertToCurrent(row, col) + 1 < n * n) && (arr[convertToCurrent(row, col) + 1])
                    && ((convertToCurrent(row, col) + 1) % n != 0)) {
                unionFindGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) + 1);
                isFullGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) + 1);
            }

            if ((convertToCurrent(row, col) - 1 >= 0) && (convertToCurrent(row, col) - 1 < n * n)
                    && (arr[convertToCurrent(row, col)
                    - 1]) && (convertToCurrent(row, col) % n != 0)) {
                unionFindGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) - 1);
                isFullGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) - 1);
            }

            if ((convertToCurrent(row, col) + n >= 0) && (convertToCurrent(row, col) + n < n * n)
                    && (arr[convertToCurrent(row, col)
                    + n])) {
                unionFindGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) + n);
                isFullGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) + n);
            }

            if ((convertToCurrent(row, col) - n >= 0) && (convertToCurrent(row, col) - n < n * n)
                    && (arr[convertToCurrent(row, col)
                    - n])) {
                unionFindGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) - n);
                isFullGrid.union(convertToCurrent(row, col), convertToCurrent(row, col) - n);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (!((row > 0 && row <= n) && (col > 0 && col <= n)))
            throw new IllegalArgumentException("Choose a valid value");

        return arr[convertToCurrent(row, col)];

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= n) && (col > 0 && col <= n)) {
            return (isFullGrid.find(0) == isFullGrid.find(convertToCurrent(row, col))
                    && arr[convertToCurrent(row, col)]);
        }
        else throw new IllegalArgumentException("Choose a valid value");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return (unionFindGrid.find(0) == unionFindGrid.find(n * n - 1));
    }

    // test client (optional)
    public static void main(String[] args) {
        // empty body
    }
}
