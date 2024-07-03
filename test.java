package com.thealgorithms.backtracking;
import java.util.*;
/**
 * Is used to solve Sudoku puzzles by exploring neighboring cells and recursively
 * solving the puzzle until a solution is found or none exists. The class uses a 2D
 * grid represented as an array of integers, with each cell value representing whether
 * it contains a mine or not. The solve method starts at a random location and
 * recursively solves the puzzle by exploring neighboring cells until a solution is
 * found or none exists.
 */
public class KnightsTour {
/**
 * Iterates over the elements of a linked list and returns the number of elements up
 * to the maximum allowed value.
 * 
 * @returns the number of nodes in the linked list, calculated by iterating over the
 * list and counting the number of non-null items.
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
     * Creates a grid with random values, checks if a specific cell can be filled based
     * on its row and column, and prints the result.
     * 
     * @param args 0 or more command-line arguments passed to the Java program, which are
     * ignored in this case since no action is taken on them.
     * 
     * * Length: `args.length` is equal to 0 or 1.
     * * Elements: `args[0]` is the single argument passed to the function, which is
     * typically the base value for the grid.
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
     * Determines if a given cell can be filled with a number, based on the values of its
     * neighbors and the current count. It iterates through neighboring cells, checking
     * if they can be filled and updating the grid accordingly, until a solution is found
     * or none remains.
     * 
     * @param row 2D coordinates of a cell in the grid that is being analyzed for orphan
     * detection.
     * 
     * @param column 2nd dimension of the grid, which is used to determine the neighbors
     * of a cell and to keep track of the current position in the grid during the algorithm's
     * execution.
     * 
     * @param count 1-based position of the current cell in the grid, which is used to
     * determine whether the cell has been visited before and to track the progress of
     * the algorithm.
     * 
     * @returns a boolean value indicating whether the Sudoku puzzle is solved or not.
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
     * on a set of predefined moves and a count of the number of neighbors at each location.
     * 
     * @param row 2D coordinate of the cell for which the neighbors are being computed.
     * 
     * @param column 2nd dimension of the grid, which is used to determine the coordinates
     * of the neighbors to count and return.
     * 
     * @returns a list of integer arrays containing information about the number of
     * neighbors for each cell in the grid.
     * 
     * 1/ The list of neighbors is stored in an instance of `List<int[]>`.
     * 2/ Each element of the list is represented by a 3-element array, where the first
     * two elements represent the coordinates of the neighboring cell, and the third
     * element represents the number of neighbors for that cell.
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
     * Counts the number of neighbors of a given cell in a grid based on the moves array.
     * 
     * @param row 2D coordinate of the cell being analyzed for neighbors.
     * 
     * @param column 2D coordinate of the cell being counted for its neighbors, along
     * with the row parameter representing the 2D coordinate of the cell itself.
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
     * Checks if a cell is an orphan by checking its neighbors and verifying that no other
     * cell in those neighbors has zero count. If so, it returns `true`.
     * 
     * @param count 2D cell's count of occupied cells adjacent to it, which is used to
     * determine whether an orphan cell exists.
     * 
     * @param row 1D coordinate of the current cell being evaluated for orphan status.
     * 
     * @param column 2nd dimension of the grid, which is used to determine the neighbors
     * of the given cell.
     * 
     * @returns a boolean value indicating whether an orphan node has been detected in
     * the graph.
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
     * Iterates over a 2D grid and prints each element in a column, skipping any elements
     * marked as -1.
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
