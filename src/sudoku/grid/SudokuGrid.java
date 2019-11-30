package sudoku.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Mostafa
 */
public class SudokuGrid {
// null values indicates it empty and expect user input
    private static final Integer[][] sudokuGrid = {
        {5, 3, null, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, null, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, null, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, null, null, null, 6, 3, 5},
        {3, 4, 5, null, 8, 6, 1, 7, 9}
    };
//4
//4
//8
//4
//1
//9
//2
    private static ArrayList<Integer[]> emptyIndexes = new ArrayList<>();

    private static int findNumberOfInputs() {
        int numberOfInputs = 0;
        for (int i = 0; i < sudokuGrid.length; i++) {
            for (int j = 0; j < sudokuGrid[i].length; j++) {
                if (sudokuGrid[i][j] == null) {
                    numberOfInputs++;
                    // save the empty indexes for later use
                    emptyIndexes.add(new Integer[]{i, j});
                }
            }
        }
        return numberOfInputs;
    }

    private static void insertNumbers(int[] numbers) {
        for (int i = 0; i < emptyIndexes.size() && numbers.length == emptyIndexes.size(); i++) {
            int row = emptyIndexes.get(i)[0];
            int column = emptyIndexes.get(i)[1];
            if (isColumnOrRowValid(column, numbers[i], false)) {
                if (isColumnOrRowValid(row, numbers[i], true)) {
                    if (isValidInGrid(row, column, numbers[i])) {
                        sudokuGrid[row][column] = numbers[i];

                    } else {
                        System.out.println("not grid valid");
                        return;
                    }
                } else {
                    System.out.println("not row valid");
                    return;
                }
            } else {
                System.out.println("not column valid");
                return;
            }
        }
    }

    private static boolean isColumnOrRowValid(int locationNumber, int insertValue, boolean isRow) {
        for (int i = 0; i < sudokuGrid.length; i++) {

            Integer locationValue;
            if (isRow) {
                locationValue = sudokuGrid[locationNumber][i];
            } else {
                locationValue = sudokuGrid[i][locationNumber];
            }

            if (locationValue != null && insertValue == locationValue) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidInGrid(int row, int column, int insertValue) {
        int[][] subGridsIndexes = {{0, 2}, {3, 5}, {6, 8}};
        int[][] acceptedIndexes = new int[2][2];

        boolean allIndexesReady = false;
        for (int[] rowIndexes : subGridsIndexes) {
            if (row >= rowIndexes[0] && row <= rowIndexes[1]) {
                // firts index indicates for row range 
                acceptedIndexes[0] = rowIndexes;

                for (int[] columnIndexes : subGridsIndexes) {
                    if (column >= columnIndexes[0] && column <= columnIndexes[1]) {
                        // second index indicates for column range 
                        acceptedIndexes[1] = columnIndexes;
                        allIndexesReady = true;
                        break;
                    }
                }
            }
            if (allIndexesReady) {
                break;
            }
        }

        // counter to check if insertValue is suiteable in sub array
        int counter = 0;
        for (int i = acceptedIndexes[0][0]; i <= acceptedIndexes[0][1]; i++) {
            for (int j = acceptedIndexes[1][0]; j <= acceptedIndexes[1][1]; j++) {
                if (row == i && j == column) {
                    continue;
                }

                if (sudokuGrid[i][j] != null && sudokuGrid[i][j] == insertValue) {
                    counter++;
                }
            }
        }
        return counter == 0;

    }

    public static void main(String[] args) {
        int numberOfAllowedInputs = findNumberOfInputs();

        if (numberOfAllowedInputs == 0) {
            System.out.println("There is no inputs available");
            return;
        }

        // get user inputs
        System.out.println("Enter only " + numberOfAllowedInputs + " number of inputs from 1 to 9: ");
        Scanner inputScanner = new Scanner(System.in);
        int[] userInputs = new int[numberOfAllowedInputs];
        for (int i = 0; i < userInputs.length; i++) {
            int input = inputScanner.nextInt();

            if (input >= 1 && input < 10) {
                userInputs[i] = input;
                continue;
            }
            System.out.println("Number must be from 1 to 9");
            userInputs[i] = inputScanner.nextInt();

        }

        insertNumbers(userInputs);
    }
}
