import java.util.Arrays;

public class PercolationUF implements IPercolate{

    private boolean[][] myGrid;
    private int myOpenCount;
    private IUnionFind myFinder;
    private int VTop ;
    private int VBottom;

    public PercolationUF(IUnionFind finder, int size) {
        finder.initialize(size * size + 2);
        myFinder = finder;
        VTop = size * size;
        VBottom = size * size + 1;
        myOpenCount = 0;
        myGrid= new boolean[size][size];
        for (boolean[] row : myGrid) {
            Arrays.fill(row, false);
            }
        }
    @Override
    public void open(int row, int col) {
        if (row <0 || row >= myGrid.length || col < 0 || col >= myGrid.length) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        if (!myGrid[row][col]) {
            myGrid[row][col] = true;
            myOpenCount += 1;
            if (row < myGrid.length - 1 && myGrid[row + 1][col]) {
                myFinder.union(row * myGrid.length + col, (row + 1) * myGrid.length + col);
            }
            if (row > 0 && myGrid[row - 1][col]) {
                myFinder.union(row * myGrid.length + col, (row - 1) * myGrid.length + col);
            }
            if (col < myGrid.length - 1 && myGrid[row][col + 1]) {
                myFinder.union(row * myGrid.length + (col), (row) * myGrid.length + (col + 1));
            }
            if (col > 0 && myGrid[row][col - 1]) {
                myFinder.union(row * myGrid.length + (col), (row) * myGrid.length + (col - 1));
            }
            if (row == 0) {
                myFinder.union(row * myGrid.length + col, VTop);
            }
            if (row == myGrid.length - 1) {
                myFinder.union(row * myGrid.length + col, VBottom);
            }
        }
    }

    @Override
    public boolean isOpen(int row, int col) {
        if (row <0 || row >= myGrid.length || col < 0 || col >= myGrid.length) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myGrid[row][col];
    }

    @Override
    public boolean isFull(int row, int col) {
        if (row <0 || row >= myGrid.length || col < 0 || col >= myGrid.length) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myFinder.connected(VTop, (row*myGrid.length+col));
    }

    @Override
    public boolean percolates() {
        return myFinder.connected(VTop, VBottom);
    }

    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }
}

