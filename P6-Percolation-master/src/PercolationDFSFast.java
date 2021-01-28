public class PercolationDFSFast extends PercolationDFS {
//    protected int[][] myGrid;
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationDFSFast(int n) {
        super(n);
    }

    @Override
    public void updateOnOpen(int row, int col) {
        myGrid[row][col] = OPEN;
        if ((row == 0) || (( inBounds(row - 1, col) && myGrid[row-1][col] == FULL)  ||
                (inBounds(row, col-1)) && myGrid[row][col-1] == FULL) || ( inBounds(row, col+1) && myGrid[row][col+1] == FULL)
                || ( inBounds(row+1, col) && myGrid[row + 1][col] == FULL)) {
            dfs(row,col);
        }

    }
    }
