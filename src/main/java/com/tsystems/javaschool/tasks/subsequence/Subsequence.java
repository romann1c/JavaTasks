package com.tsystems.javaschool.tasks.subsequence;


import java.util.List;


public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
        public boolean find(List<Object> a, List<Object> b) throws IllegalArgumentException {
        // here I wanted to check that neither of lists is empty, using list.isEmpty(),
        // not a must, but imho that makes no sense if we execute the following code over empty lists,
        // but the tests want me to make it so.
        if (a == null || b == null )
            throw new IllegalArgumentException();
        int counter = 0; // counts the number of values in the second list meeting requirements
        int subIndex = 0;
        for (Object fromFirstList : a) {                            //here we go through the first list;
            for (int index = subIndex; index < b.size(); index++) { //here we compare every value from the second list
                if (fromFirstList.equals(b.get(index))) {           //with a value from the first list;
                    counter++;                                      //if there's a match, counter increases by 1;
                    subIndex = index + 1;                           //to keep the correct order, if we did it otherwise
                    break;                                          //each time the program would go from the beginning
                }                                                   //of the second list;
            }
        }

        if (counter == a.size()) return true;  //if quantity of numbers meeting the requirements
        return false;                          //is equal to the first list's size then the whole second list
    }                                          // may become so by cutting a number of values out
}
