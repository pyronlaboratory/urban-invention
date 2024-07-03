package com.thealgorithms.backtracking;
import java.util.*;
/**
 * Is a program that solves Sudoku puzzles by iterating over a 2D grid and filling
 * in the missing values based on a set of rules. The class has several methods for
 * solving Sudoku puzzles, including `solve`, `neighbors`, `countNeighbors`, and
 * `orphanDetected`. These methods use a recursive approach to solve the puzzle by
 * exploring the neighboring cells and determining if they can be filled with a
 * specific number. The program also has a `printResult` method that prints out the
 * solved puzzle.
 */
public class KnightsTour {
/**
 * Calculates the number of elements in a linked list by iterating through the list
 * and counting the number of non-null items.
 * 
 * @returns the number of nodes in the linked list.
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
     * Generates a grid of size base x base and initializes it with -1 values, except for
     * a random row and column where a 1 is placed. It then uses a recursive function
     * `solve` to check if there exists a path from the random row and column to the
     * top-left corner of the grid, and prints "result" if such a path exists or "no
     * result" otherwise.
     * 
     * @param args 0 or more command-line arguments passed to the program, which are
     * ignored in this case and have no effect on the function's behavior.
     * 
     * * Length: `args.length` is equal to 0 or 1, indicating whether any arguments were
     * passed to the program.
     * * Elements: If `args.length > 0`, then `args[0]` is the only argument, which is a
     * string representing the command-line option or value.
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
     * 1) checks if the count is greater than the total number of stones, 2) checks if
     * there are no neighbors, 3) sorts the neighbors by position, and 4) iterates through
     * the neighbors to solve the Sudoku puzzle by updating the grid values and checking
     * for orphan detection.
     * 
     * @param row 2D coordinate of the cell in the grid that is being checked for orphaned
     * tiles.
     * 
     * @param column 2D position of the cell to be checked for orphans in the grid, and
     * is used to determine the corresponding cell value in the `grid` array.
     * 
     * @param count 2D position in the grid where the algorithm should search for an
     * orphan cell, and it is used to determine the number of neighbors to consider for
     * the sort and check for orphan detection.
     * 
     * @returns a boolean value indicating whether the Sudoku puzzle has been solved.
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
     * Computes and returns a list of neighboring cells for a given cell in a grid, based
     * on the movement rules provided in the function's parameters.
     * 
     * @param row 2D coordinate of the cell for which the neighbors are being calculated.
     * 
     * @param column 2nd dimension of the grid in the neighborhood search, used to determine
     * the surrounding cells for each cell in the grid.
     * 
     * @returns a list of neighboring cells and their corresponding number of neighbors.
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
     * Counts the number of neighbors of a cell in a grid that are empty.
     * 
     * @param row 2D coordinate of the current cell being analyzed in the grid.
     * 
     * @param column 2D coordinate of the grid cell being analyzed for neighbors.
     * 
     * @returns the number of neighbors of a given cell in the grid.
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
     * Checks if a given cell is an orphan (i.e., it has no horizontally or vertically
     * adjacent cells with a count of 1). It does so by iterating over the neighboring
     * cells and checking if any have a count of 0.
     * 
     * @param count number of orphan nodes in the graph being analyzed.
     * 
     * @param row 2D coordinate of the cell being analyzed in the grid.
     * 
     * @param column 2D coordinate of the cell being checked for orphans, and is used to
     * determine the set of neighboring cells to check for orphan status in the `neighbors()`
     * method call.
     * 
     * @returns a boolean value indicating whether an orphan cell has been detected in
     * the grid.
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
     * Iterates over a 2D grid, printing each element on a new line. If an element is -1,
     * it is skipped.
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
