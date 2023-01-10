package edu.netcracker.small_learning_things.median_of_two_sorted_arrays;

/**
 * @author svku0919
 * @version 10.01.2023-14:24
 */

class Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int startI = 0;
        int endI = nums1.length - 1;
        int i = (endI - startI) / 2;

        int startJ = 0;
        int endJ = nums2.length - 1;
        int j = (endJ - startJ) / 2;

        boolean twoMedians = (nums1.length + nums2.length) % 2 == 0;
        boolean notFound = true;
        double median = 0;
        while (notFound) {
            int elemI = nums1[i];
            int elemJ = nums2[j];
            if (elemI > elemJ) {
                endI = i;
                i = startI + (endI - startI) / 2;
                startJ = j;
                j = startJ + (endJ - startJ) / 2;
            } else {
                endJ = j;
                j = startJ + (endJ - startJ) / 2;
                startI = i;
                i = startI + (endI - startI) / 2;
            }

            if (endI - startI < 1 && !twoMedians) {
                notFound = false;
                median = nums1[startI];
            } else if (endJ - startJ < 1 && !twoMedians) {
                notFound = false;
                median = nums2[startJ];
            } else if (endI - startI < 1 && endJ - startJ < 1) {
                notFound = false;
                median = (nums1[startI] + nums2[startJ]) / 2.0;
            }

        }

        return median;
    }

    public static int findMiddleIndex(int startIndex, int endIndex) {
        if (endIndex - startIndex > 1) {
            return (endIndex - startIndex) / 2;
        } else if (endIndex == startIndex) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        double medianSortedArrays = findMedianSortedArrays(new int[]{1, 2, 3, 9, 10, 11, 15}, new int[]{4, 5, 6, 7, 12, 16, 17});
        System.out.println(medianSortedArrays);
    }
}
