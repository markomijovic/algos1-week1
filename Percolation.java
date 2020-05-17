import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF arr;
    private boolean[][] sites;
    private int openCount = 0;
    private final int top, max;

    // constructor; creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("N <= 0");
        // imaginary top row = 0
        top = 0;
        max = n;
        arr = new WeightedQuickUnionUF((n * n) + 1); // +1 for the top
        sites = new boolean[n + 1][n + 1]; // col=0 is extra
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        inRange(row, col); // make sure within the grid
        if (!sites[row][col]) {
            sites[row][col] = true;
            openCount++;
            connectAdjacent(row, col);
        }
    }
    // checks if there are adjacent nodes open and connects them to the newly open node
    private void connectAdjacent(int row, int col) {
        // 1) Connect above node
        if (row != 1 && isOpen(row - 1, col)) arr.union(toArr(row, col), toArr(row - 1, col));
        // 2) Connect left node
        if (col != 1 && isOpen(row, col - 1)) arr.union(toArr(row, col), toArr(row, col - 1));
        // 3) Connect right node
        if (col != max && isOpen(row, col + 1)) arr.union(toArr(row, col), toArr(row, col + 1));
        // 4) Connect below node
        if (row != max && isOpen(row + 1, col)) arr.union(toArr(row, col), toArr(row + 1, col));
        // 5) Conect to the top node arr[0]
        if (row == 1) arr.union(toArr(row, col), 0);
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        inRange(row, col); // make sure within the grid
        return sites[row][col];
    }
    // is the site (row, col) full? aka connects to top row
    public boolean isFull(int row, int col) {
        inRange(row, col); // make sure within the grid
        if (isOpen(row, col)){
            return arr.connected(0,toArr(row, col));
        }
        return false;
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }
    // does the system percolate?
    public boolean percolates() {
        // go through each node at the last level and check if connected to top at any one
        for (int i = 1; i < max + 1; i++) {// max = n;
            if (isOpen(max, i)) {
                if (arr.connected(0, toArr(max, i))) {
                    return true;
                }
            }
        }
        return false;
    }
    // error check
    private void inRange(int row, int col) {
        if (row < 1 || row > max || col < 1 || col > max) throw new IllegalArgumentException(
                "Row, col value error: row < 1 || row > max || col < 1 || col > max");
    }
    // maps nxn to linear value for the array
    private int toArr(int row, int col) {
        return (row - 1) * max + col;
    }
}