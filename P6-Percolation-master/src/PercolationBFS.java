import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast {
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationBFS(int n) {
        super(n);
    }

    @Override
    protected void dfs(int row, int col) {
        Queue<int[]> q = new LinkedList<>();
        myGrid[row][col] = FULL;
        q.add(new int[]{row, col});
        while (q.size() != 0) {
            int[] poked = q.remove();
            if (inBounds(poked[0]-1, poked[1]) && myGrid[poked[0] - 1][poked[1]] == OPEN) {
                myGrid[poked[0] - 1][poked[1]] = FULL;
                q.add(new int[]{poked[0] - 1, poked[1]});
            }
            if ((inBounds(poked[0]+1,poked[1] )) && myGrid[poked[0] + 1][poked[1]] == OPEN) {
                myGrid[poked[0] + 1][poked[1]] = FULL;
                q.add(new int[]{poked[0] + 1, poked[1]});
            }

            if ((inBounds(poked[0], poked[1]-1)) && myGrid[poked[0]][poked[1] - 1] == OPEN) {
                myGrid[poked[0]][poked[1] - 1] = FULL;
                q.add(new int[]{poked[0], poked[1] - 1});
            }

            if ((inBounds(poked[0], poked[1]+1 )) && myGrid[poked[0]][poked[1] + 1] == OPEN) {
                myGrid[poked[0]][poked[1] + 1] = FULL;
                q.add(new int[]{poked[0], poked[1] + 1});
            }
        }
        }
    }


