package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;


public class PyramidBuilder {

    public int[][] buildPyramid(List<Integer> inputNumbers) {


        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();   //Here I'm checking if the input is empty
        }

        int[][] pyramid;                               //"Template" for getting the result ready

        int size = inputNumbers.size();

        int amountOfNumbersInRow = 0;
        int rows = 1;
        int columns = 1;

        while (amountOfNumbersInRow < size) {         //pure math, the value of number of a row equals to the number of numbers are put in it,
                                                      //also here I get the number of values needed to fill up the pyramid with specified parameters
            amountOfNumbersInRow += rows;
            rows++;
            columns += 2;
        }
        rows--;                                       // these actions are needed to work with indexes
        columns -= 2;

        if (size != amountOfNumbersInRow) {           // math again, a pyramid can be built if a number of values can be expressed using the formula "number=n(n+1)/2" 
            throw new CannotBuildPyramidException();
        }

        try{
        Collections.sort(inputNumbers);               //using collections to sort
        } catch (OutOfMemoryError error) {
            throw new CannotBuildPyramidException();  //catch OutOfMemoryError and substitute it with CannotBuildPyramidException
            }
            
            
        pyramid = new int[rows][columns];
        for (int[] row : pyramid) {                   //fill the "template" up with zeros
            Arrays.fill(row, 0);
        }

        int middle = (columns / 2);                  //because top row has only one "block" we need to somehow make the jvm fill the middle of the row,
        amountOfNumbersInRow = 1;                    //we come to this while we have shifter == 0 and currentRowIndex == 0.            
        int index = 0;

        for (int i = 0, shifter = 0; i < rows; i++, shifter++, amountOfNumbersInRow++) {
            int start = middle - shifter;
            for (int currentRowIndex = 0; currentRowIndex < amountOfNumbersInRow * 2; currentRowIndex += 2, index++) {
                pyramid[i][start + currentRowIndex] = inputNumbers.get(index);
            }
        }

        return pyramid;

    }


}
