// File: Board.java
// Author: Patrick Smith
// Date: Aug 21, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package gameTest;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 21, 2016
 */
public class Board {
    int SIZE = 4;
    Integer[][] grid = new Integer[4][4];
    Random rand;

    /**
     * 
     */
    public Board() {
        // TODO Auto-generated constructor stub
        int start = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                // System.out.println(grid[row][col]);
                grid[row][col] = Integer.valueOf(0);

                // System.out.println("Now " + grid[row][col]);
            }
        }
        rand = new Random();
        // display();

        while (start < 2) {
            int randX = rand.nextInt(4);
            int randY = rand.nextInt(4);
            if (grid[randX][randY].equals(0)) {
                grid[randX][randY] = 2;
                start++;
            }
        }
        // grid[0][0] = 0;
        // grid[0][1] = 2;
        // grid[0][2] = 2;
        // grid[0][3] = 2;
        //
        // grid[1][3] = 2;
    }

    private void addTile() {
        int chance = rand.nextInt(11);
        int tile;
        if (chance >= 9) {
            tile = 4;
        } else {
            tile = 2;
        }
        List<Entry<Integer, Integer>> list = getBlanks();
        int index = rand.nextInt(list.size());
        Entry<Integer, Integer> newTile = list.get(index);
        grid[newTile.getKey()][newTile.getValue()] = tile;
    }

    private List<Entry<Integer, Integer>> getBlanks() {
        List<Entry<Integer, Integer>> list = new ArrayList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    list.add(new SimpleEntry<>(row, col));
                }
            }
        }
        return list;
    }

    public void display() {
        for (Integer[] row : grid) {
            System.out.print("|");
            for (Integer j : row) {
                System.out.print(j);
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public void moveLeft() {
        shiftLeft();
        combineLeft();
        addTile();
    }

    private void pseudoMoveLeft() {
        shiftLeft();
        combineLeft();
    }

    private void shiftLeft() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length - 1; col++) { // only check
                                                                   // col 0-2
                                                                   // (col 3 has
                                                                   // nothing to
                                                                   // the left)
                // int item = grid[row][col];
                // System.out.println("row " + row + " = " +
                // Arrays.toString(grid[row]));
                // System.out.println(Arrays.asList(grid[row]));
                // System.out.println("Sublist " + " from " + col + " to " +
                // grid[row].length + " "
                // + Arrays.asList(grid[row]).subList(col, grid[row].length));
                int max = Arrays.asList(grid[row]).subList(col, grid[row].length).stream().mapToInt(e -> e).max()
                        .getAsInt();
                int safety = 0;
                while (grid[row][col] == 0 && max > 0) {
                    // System.out.println("row is " + Arrays.toString(grid[row])
                    // + " max " + max);
                    // move everything to the left of this point left
                    for (int start = col; start < grid[row].length - 1; start++) {
                        // System.out.println("Swapping left at " + start);
                        grid[row][start] = grid[row][start + 1]; // swap to the
                                                                 // left
                        // System.out.println("after swap " +
                        // Arrays.toString(grid[row]));
                    }

                    grid[row][grid[row].length - 1] = 0; // add a zero to the
                                                         // end
                    // update max
                    max = Arrays.asList(grid[row]).subList(col, grid[row].length).stream().mapToInt(e -> e).max()
                            .getAsInt();
                    safety++;
                    if (safety > 4) {
                        System.out.println("Breaking");
                        break;
                    }
                }
                // System.out.println("after row is " +
                // Arrays.toString(grid[row]));
                if (col > 0 && grid[row][col - 1] == 0) {

                }
            }
        } // end for

    }

    private void combineLeft() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length - 1; col++) {
                if (grid[row][col].equals(grid[row][col + 1])) {
                    grid[row][col] = 2 * grid[row][col];
                    grid[row][col + 1] = 0;
                    shiftLeft();
                } // end if
            } // end for
        } // end for
    } // end method

    private void transpose() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = i + 1; j < SIZE; j++) {
                int temp = grid[i][j];
                grid[i][j] = grid[j][i];
                grid[j][i] = temp;
            }
        }
    }

    private void reverse() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length / 2; col++) {
                Integer temp = grid[row][col];
                grid[row][col] = grid[row][grid[row].length - 1 - col];
                grid[row][grid[row].length - 1 - col] = temp;
            }
        }
    }

    public void moveRight() {
        rotate180();
        pseudoMoveLeft();
        rotate180();
        addTile();

    } // end method

    public void moveUp() {
        rotate90();
        rotate180();
        pseudoMoveLeft();
        rotate90();
        addTile();
    }

    public void moveDown() {
        rotate90();
        pseudoMoveLeft();
        rotate90();
        rotate180();
        addTile();
    }

    private void rotate180() {
        rotate90();
        rotate90();
    }

    private void rotate90() {
        transpose();
        reverse();
    }

    public boolean winCheck() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean loseCheck() {
        if (getBlanks().isEmpty() && !moveCheck()) {
            return true;
        }
        return false;
    }

    public boolean moveCheck() {
        if (checkLeft() || checkRight() || checkUp() || checkDown()) {
            return true;
        }
        return false;
    }

    private boolean checkLeft() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length - 1; col++) {
                if ((grid[row][col].equals(grid[row][col + 1]) && !grid[row][col].equals(0))
                        || (grid[row][col].equals(0) && !grid[row][col + 1].equals(0))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRight() {
        rotate180();
        boolean check = checkLeft();
        rotate180();
        return check;
    }

    private boolean checkUp() {
        rotate90();
        rotate180();
        boolean check = checkLeft();
        rotate90();
        return check;
    }

    private boolean checkDown() {
        rotate90();
        boolean check = checkLeft();
        rotate90();
        rotate180();
        return check;
    }

    public static void main(String[] args) {
        Board test = new Board();
        // test.display();
        // test.moveLeft();
        // test.display();
        // System.out.println(test.checkLeft());
        // test.moveLeft();
        // test.display();
        // test.moveDown();
        // test.display();
        // System.out.println(test.getBlanks());
        // test.addTile();
        // test.display();
        while (!test.winCheck() || !test.loseCheck() || test.moveCheck()) {
            test.display();
            System.out.println("Move: ");
            Scanner stdin = new Scanner(System.in);
            boolean validEntry = false;
            while (!validEntry) {
                System.out.print("l, r, u, d: ");
                if (stdin.hasNext()) {
                    String move = stdin.next();
                    switch (move) {
                    case "l":
                        if (test.checkLeft()) {
                            test.moveLeft();
                        }
                        break;
                    case "r":
                        if (test.checkRight()) {
                            test.moveRight();
                        }
                        break;
                    case "u":
                        if (test.checkUp()) {
                            test.moveUp();
                        }
                        break;
                    case "d":
                        if (test.checkDown()) {
                            test.moveDown();
                        }
                        break;
                    }
                    test.display();
                }
            }

        }
    }
}
