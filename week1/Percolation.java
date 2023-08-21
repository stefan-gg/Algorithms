import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
	Problem description : 
	https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
*/

public class Percolation {
    private WeightedQuickUnionUF wuf;
    private final int size;
    private int openSites = 0;

    private final int topRoot = 0;
    private final int bottomRoot;
    private final int[] openSitesIndexes;

    // We say the system percolates
    // if there is a full site in the bottom row. In other words,
    // a system percolates if we fill all open sites connected to
    // the top row and that process fills some open site on the bottom row.

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n > 0) {
            this.wuf = new WeightedQuickUnionUF((n * n) + 2);
            this.size = n;
            this.openSitesIndexes = new int[n * n + 2];
            this.openSitesIndexes[0] = 1;
            this.openSitesIndexes[n * n + 1] = 1;
            this.bottomRoot = n * n + 1;
        } else {
            throw new IllegalArgumentException("N cannot be less or equal 0");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int size = this.size;

        if (size >= row && size >= col && row > 0 && col > 0) {
            int index = (size * (row - 1)) + col;

            this.openSitesIndexes[index] = 1;
            this.openSites++;

            if (index <= size) {
                wuf.union(this.topRoot, index);
            } else if (index > (size * size) - size) {
                wuf.union(this.bottomRoot, index);
            }

            int fieldIndex = returnIndex(row, col);

            if (isValid(row, col + 1)) wuf.union(fieldIndex, returnIndex(row, col + 1));
            if (isValid(row, col - 1)) wuf.union(fieldIndex, returnIndex(row, col - 1));
            if (isValid(row + 1, col)) wuf.union(fieldIndex, returnIndex(row + 1, col));
            if (isValid(row - 1, col)) wuf.union(fieldIndex, returnIndex(row - 1, col));

        } else {
            throw new IllegalArgumentException("Row or Col cannot be less than or equal 0");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (this.size >= row && this.size >= col && row > 0 && col > 0) {
            return this.openSitesIndexes[(this.size * (row - 1)) + col] == 1;
        } else {
            throw new IllegalArgumentException("Row or Col cannot be less or equal 0");
        }
    }


    // A full site is an open site
    // that can be connected to an open
    // site in the top row via a chain of neighboring
    // (left, right, up, down) open sites.

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (this.size >= row && this.size >= col && row > 0 && col > 0) {
            return wuf.find(this.topRoot) == wuf.find(returnIndex(row, col));
        } else {
            throw new IllegalArgumentException("Row or Col cannot be less or equal 0");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {return this.openSites;}

    // does the system percolate?
    public boolean percolates() {return wuf.find(this.bottomRoot) == wuf.find(this.topRoot);}

    private boolean isValid(int row, int col) {
        return (col >= 1 && col <= this.size) && (row >= 1 && row <= this.size) && isOpen(row, col);
    }

    private int returnIndex(int row, int col) {
        return ((row - 1) * this.size) + col;
    }

    //    test client (optional)
    public static void main(String[] args) {
/*
        Percolation p = new Percolation(4);

        p.open(1,2);
        p.open(1,1);
        p.open(2,1);
        p.open(2, 2);
        p.open(4, 2);

        System.out.println(p.wuf.find(p.topRoot));
        System.out.println(p.wuf.find(p.bottomRoot));
        System.out.println(p.percolates());

        p.open(3, 2);
        p.open(2, 2);

        System.out.println(p.wuf.find(p.topRoot));
        System.out.println(p.wuf.find(p.bottomRoot));
        System.out.println(p.wuf.find(13));
        System.out.println(p.percolates());
*/


// Uncomment code below if you want to run whole program with the outputs
/*        Percolation p = new Percolation(4);
        while(!p.percolates()){
            int col = StdRandom.uniformInt(1, p.size + 1);
            int row = StdRandom.uniformInt(1, p.size + 1);

            System.out.println("***********************************");
            System.out.println("row " + row + " col " + col);
            if(!p.isOpen(row, col)){
                p.open(row, col);
            }

            System.out.println("***********************************");
            int j = 0;
            String s = "";
            for (int i = 1; i < p.size * p.size+1; i++) {
                if(j == p.size-1){
                    s += p.openSitesIndexes[i];
                    System.out.println(s);
                    s="";
                    j = 0;
                }
                else {
                    s += p.openSitesIndexes[i];
                    j++;
                }
            }

        }
        System.out.println("final ***********************************");

        int j = 0;
        String s = "";
        for (int i = 1; i < p.size * p.size+1; i++) {
            if(j == p.size-1){
                s += p.openSitesIndexes[i];
                System.out.println(s);
                s="";
                j = 0;
            }
            else {
                s += p.openSitesIndexes[i];
                j++;
            }
        }

        System.out.println(p.openSites);
*/

    }
}
