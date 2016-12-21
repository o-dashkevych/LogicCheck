package org.logic.anagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Oleh Dashkevych
 */
public class PrimeFactor {

    private static final String EXCEPTION_MESSAGE_PATTERN = "Input number %d for factor finding cannot be negative";


    // Integer.MAX_VALUE + 1, Integer.MIN_VALUE, 0, +1, 10;


    public static List<Integer> primeFactors(int number) {
        if (isNegative(number)) {
            throw new IllegalArgumentException(String.format(EXCEPTION_MESSAGE_PATTERN, number));
        }
        if (number == 1) {
            return Collections.singletonList(number);
        }
        return countFactor(number);
    }

    private static List<Integer> countFactor(int rest) {
        List<Integer> factors = new ArrayList<>();
        for (int divider = 2; divider <= rest; divider++) {
            while (rest % divider == 0) {
                factors.add(divider);
                rest /= divider;
            }
        }
        return factors;
    }

    private static boolean isNegative(int number) {
        return number < 0;
    }



}
