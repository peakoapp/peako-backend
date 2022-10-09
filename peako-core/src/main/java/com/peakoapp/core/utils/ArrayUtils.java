package com.peakoapp.core.utils;

import java.util.Arrays;

/**
 * The {@code ArrayUtils} class implements array-based custom utility methods.
 *
 * @version 0.1.0
 */
public final class ArrayUtils {
    /**
     * Concatenates two non-primitive arrays.
     *
     * @param arr1 The first array, also the array in the front of the resultant array.
     * @param arr2 The Second array, also the array at the back of the resultant array.
     * @param <T> The type of the array.
     * @return A combined array.
     */
    public static <T> T[] concat(T[] arr1, T[] arr2) {
        T[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}
