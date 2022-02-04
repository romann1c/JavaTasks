package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Comparator;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {


        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        }

        int[][] pyramid;

        int size = inputNumbers.size();

        int amountOfNumbersInRow = 0;
        int rows = 1;
        int columns = 1;

        while (amountOfNumbersInRow < size) {
            amountOfNumbersInRow += rows;
            rows++;
            columns += 2;
        }
        rows--;
        columns -= 2;

        if (size != amountOfNumbersInRow) {
            throw new CannotBuildPyramidException();
        }

        try {
        inputNumbers.sort(Comparator.naturalOrder());
        }

        catch (OutOfMemoryError error) {
            throw new CannotBuildPyramidException();
        }

        pyramid = new int[rows][columns];
        for (int[] row : pyramid) {
            Arrays.fill(row, 0);
        }

        int middle = (columns / 2);
        amountOfNumbersInRow = 1;
        int index = 0;

        for (int i = 0, offset = 0; i < rows; i++, offset++, amountOfNumbersInRow++) {
            int start = middle - offset;
            for (int j = 0; j < amountOfNumbersInRow * 2; j += 2, index++) {
                pyramid[i][start + j] = inputNumbers.get(index);
            }
        }

        return pyramid;

    }


}
