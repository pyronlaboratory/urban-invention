package com.thealgorithms.backtracking;
import java.util.*;
/**
 * Is a solver for the classic problem of finding a path that visits every square on
 * a board once and returns to the starting position without stepping on any mines.
 * The class takes in the dimensions of the grid and initial row and column as inputs,
 * and then uses a recursive or iterative approach to find the solution. The class
 * also includes methods for neighbors and countNeighbors, which are used to determine
 * the number of neighbors for each position reached by moving from the current
 * position to the target position.
 */
public class KnightsTour {
/**
 * Calculates the number of nodes in a linked list, iterating through the list from
 * the starting node until reaching the end.
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
     * This function generates a random minefield grid with random mines and tries to
     * find a path of live cells (i.e., non-mine) from the top left corner to the bottom
     * right corner. If a solution is found (a path without stepping on any mines), it
     * prints the solution; otherwise it just says "no result".
     * 
     * @param args There are no arguments passed to the main function here so there is
     * nothing for the 'args' parameter to do. In a normal circumstance there should be
     * an array of string inputs called 'args'.
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
     * Determines whether a given cell can be filled with a specific number of mines by
     * exploring its neighboring cells and recursively calling itself until reaching a
     * solution or determining that no such solution exists.
     * 
     * @param row 2D coordinate of a cell in the grid that is being analyzed and updated
     * during the algorithm's execution.
     * 
     * @param column 2D coordinate of the cell in the grid, which is used to determine
     * the neighboring cells and check for orphans in the grid.
     * 
     * @param count 2D coordinate's neighbor grid count, which is used to determine whether
     * an orphan exists at that location and to recursively solve the puzzle.
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
     * This function `neighbors(int row++, int column++)` returns a list of integer tuples
     * representing the neighbors of the current position (row and column) on the game
     * grid. It does so by iterating over a list of valid moves `moves` and counting the
     * number of neighbors for each position reached by moving from the current position
     * to the target position.
     * 
     * @param row The `row` input parameter specifies the row number of the current cell
     * being processed. It is used to index into the `grid` array to determine the values
     * of cells located above or to the left of the current cell.
     * 
     * @param column The `column` input parameter specifies the current position on the
     * grid that is being analyzed for possible moves.
     * 
     * @returns This function returns a List of integer arrays representing the neighbors
     * of a given row and column position on a grid. Each integer array has three elements:
     * the y-coordinate of the neighboring cell (second element), the x-coordinate of the
     * neighboring cell (first element), and the number of neurons that cell contains
     * (third element).
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
     * Counts the number of neighbors of a given cell in a grid, based on the values of
     * other cells in the grid.
     * 
     * @param row 2D coordinate of the current cell in the grid, which is used to determine
     * the neighbors of that cell.
     * 
     * @param column 2D position of the cell in the grid, which is used to determine which
     * cells are neighbors of the specified row and column.
     * 
     * @returns the number of adjacent cells in the grid that are marked as 0.
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
     * Determines if a given cell is an orphan based on the number of neighbors it has.
     * If the cell has fewer than the total number of cells minus one neighbor, it is an
     * orphan and the function returns `true`. Otherwise, it returns `false`.
     * 
     * @param count 2D grid position's cell value count in the total grid size.
     * 
     * @param row 2D coordinate of the current pixel being evaluated for orphan status.
     * 
     * @param column 2D coordinate of the current cell being evaluated for orphan status.
     * 
     * @returns a boolean value indicating whether an orphan is detected at the specified
     * row and column.
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
     * Iterates over a 2D grid represented by an array of integers, printing each element
     * on a new line with a space preceding it.
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
