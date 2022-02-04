package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Comparator;

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
    public boolean find(List x, List y) {

        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        boolean isPossible = false;

        List z = new ArrayList();
        int index = 0;

        for (Object o : x) {
            boolean shouldNotStop = false;

            for (int i = 0; i < y.size(); i++) {
                if (y.get(i).equals(o) && i >= index) {
                    z.add(y.get(i));
                    shouldNotStop = true;
                    index = i;
                }
            }

            if (!shouldNotStop) {
                return false;
            }
        }

        z.removeAll(x);

        if (z.size() == 0) {
            isPossible = true;
        }

        return isPossible;
    }
}
