package com.thealgorithms.backtracking;
import java.util.*;
/**
 * The KnightsTour class is a backtracking algorithm solver for a classic chess
 * problem, aiming to find a path that visits every square on a board once and returns
 * to the starting position without stepping on any mines.
 */
public class KnightsTour {
/**
 * Returns the number of elements in the collection, handling cases where the collection
 * is empty or very large to prevent integer overflow.
 *
 * @returns the number of non-null elements in the collection.
 */
public int size() {
    restartFromHead: for (;;) {
        int count = 0;
        for (Node<E> p = first(); p != null;) {
            if (p.item != null)
                if (++count == Integer.MAX_VALUE)
                    break;  // @see Collection.size()
            if (p == (p = p.next))
                continue restartFromHead;
        }
        return count;
    }
}
    private static final int base = 12;
    private static final int[][] moves = {
        {1, -2},
        {2, -1},
        {2, 1},
        {1, 2},
        {-1, 2},
        {-2, 1},
        {-2, -1},
        {-1, -2},
    }; // Possible moves by knight on chess
    
    private static int[][] grid; // chess grid
    private static int total; // total squares in chess

    /**
     * Initializes a grid with a specific base size, creates a random empty space, solves
     * a puzzle starting from the empty space using the `solve` function, and prints the
     * result if successful, otherwise displays "no result".
     *
     * @param args command-line arguments passed to the program.
     *
     * Ignore `args` as it is not used.
     */
    public static void main(String[] args) {
        grid = new int[base][base];
        total = (base - 4) * (base - 4);

        for (int r = 0; r < base; r++) {
            for (int c = 0; c < base; c++) {
                if (r < 2 || r > base - 3 || c < 2 || c > base - 3) {
                    grid[r][c] = -1;
                }
            }
        }

        int row = 2 + (int) (Math.random() * (base - 4));
        int col = 2 + (int) (Math.random() * (base - 4));

        grid[row][col] = 1;

        if (solve(row, col, 2)) {
            printResult();
        } else {
            System.out.println("no result");
        }
    }


    /**
     * Performs a depth-first search to assign numbers to a grid, starting from a given
     * cell. It explores neighboring cells, assigns a number, and recursively calls itself
     * until a solution is found or no more numbers can be assigned.
     *
     * @param row current row being processed in the backtracking search.
     *
     * @param column current column index in the grid being processed.
     *
     * @param count current number being assigned to a cell in the grid.
     *
     * @returns a boolean indicating whether a solution is found for the given grid configuration.
     */
    private static boolean solve(int row, int column, int count) {
        if (count > total) {
            return true;
        }

        List<int[]> neighbor = neighbors(row, column);

        if (neighbor.isEmpty() && count != total) {
            return false;
        }

        neighbor.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] nb : neighbor) {
            row = nb[0];
            column = nb[1];
            grid[row][column] = count;
            if (!orphanDetected(count, row, column) && solve(row, column, count + 1)) {
                return true;
            }
            grid[row][column] = 0;
        }

        return false;
    }

    /**
     * Enumerates potential neighboring cells within a grid, based on predefined movement
     * directions. It checks if the neighboring cells are empty and counts their own
     * neighbors. The function returns a list of neighboring cells along with their
     * neighbor count.
     *
     * @param row current row index in a grid, used to calculate neighboring cell positions.
     *
     * @param column column position in the grid to which the neighboring cells are being
     * compared.
     *
     * @returns a list of arrays containing row, column, and neighbor count for each
     * identified empty grid cell.
     *
     * Consists of a list of arrays, each with three elements.
     * Each element is an integer representing a neighboring cell's row, column, and count
     * of neighboring cells.
     */
    private static List<int[]> neighbors(int row, int column) {
        List<int[]> neighbour = new ArrayList<>();

        for (int[] m : moves) {
            int x = m[0];
            int y = m[1];
            if (grid[row + y][column + x] == 0) {
                int num = countNeighbors(row + y, column + x);
                neighbour.add(new int[] {row + y, column + x, num});
            }
        }
        return neighbour;
    }


    /**
     * Counts the number of adjacent cells in a grid that contain a value of 0,
     * given the coordinates of a cell and a predefined set of possible moves.
     * It returns the count as an integer.
     *
     * @param row row index of a cell in a grid for which the number of neighboring zeros
     * is to be counted.
     *
     * @param column column position in the grid for which the number of neighboring zeros
     * is to be counted.
     *
     * @returns the number of neighboring cells in the grid that contain no value.
     */
    private static int countNeighbors(int row, int column) {
        int num = 0;
        for (int[] m : moves) {
            if (grid[row + m[1]][column + m[0]] == 0) {
                num++;
            }
        }
        return num;
    }


    /**
     * Determines whether a cell in a grid is an orphan by checking its neighbors. It
     * returns true if a neighbor with no connected cells is found, indicating an orphan,
     * otherwise it returns false.
     *
     * @param count number of cells in the given row and column that are empty or not
     * part of a group.
     *
     * @param row row index in a 2D grid, used to retrieve neighboring cells.
     *
     * @param column column index of the cell being checked for orphan status.
     *
     * @returns a boolean indicating whether an orphan cell is detected in the given count
     * and position.
     */
    private static boolean orphanDetected(int count, int row, int column) {
        if (count < total - 1) {
            List<int[]> neighbor = neighbors(row, column);
            for (int[] nb : neighbor) {
                if (countNeighbors(nb[0], nb[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Prints the contents of a 2D array called `grid` to the console. It skips printing
     * any cells with a value of -1, and aligns the printed values to take up two spaces
     * each.
     */
    private static void printResult() {
        for (int[] row : grid) {
            for (int i : row) {
                if (i == -1) {
                    continue;
                }
                System.out.printf("%2d ", i);
            }
            System.out.println();
        }
    }
}
